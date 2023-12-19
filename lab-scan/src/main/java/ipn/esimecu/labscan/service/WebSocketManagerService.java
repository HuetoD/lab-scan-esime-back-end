package ipn.esimecu.labscan.service;

import ipn.esimecu.labscan.dto.AttendanceBaseDTO;
import ipn.esimecu.labscan.entity.SubjectEntity;
import ipn.esimecu.labscan.repository.util.Util;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

@Service
public class WebSocketManagerService {

    public final Map<RoomMetadata, Set<AttendanceBaseDTO>> metadata = new HashMap<>();

    private static int sequence = 0;

    public String addAttendance(SubjectEntity subject, Collection<AttendanceBaseDTO> attendances) {
        final Map<RoomMetadata, Set<AttendanceBaseDTO>> rollBackMetadata = new HashMap<>(metadata);
        final AtomicReference<String> lastRoom = new AtomicReference<>("");
        long rooms = attendances.stream()
                                .map(attendance -> addAttendance(subject, attendance))
                                .distinct()
                                .peek(lastRoom::set)
                                .count();
        if(rooms > 1) {
            metadata.clear();
            metadata.putAll(rollBackMetadata);
            throw new RuntimeException("Inconsistente");
        }
        return lastRoom.get();
    }

    public String addAttendance(SubjectEntity subject, AttendanceBaseDTO attendance) {
        AtomicReference<RoomMetadata> currentMetadata = new AtomicReference<>(null);
        this.findMetadataBySubject(subject)
                .ifPresentOrElse(currentMetadata::set, () -> {
                    RoomMetadata newMetadata = new RoomMetadata(this.generateNewRoom(), subject);
                    metadata.put(newMetadata, new HashSet<>());
                    currentMetadata.set(newMetadata);
                });

        Optional.ofNullable(attendance)
                .ifPresent(a -> metadata.get(currentMetadata.get()).add(a));

        return currentMetadata.get().room;
    }

    public void updateChanges(SubjectEntity subject, AttendanceBaseDTO updated) {
        this.findMetadataBySubject(subject)
            .ifPresent(foundMetadata ->  metadata.get(foundMetadata)
                                                .stream()
                                                .filter(dto -> dto.equals(updated))
                                                .findFirst()
                                                .ifPresent(found -> {
                                                    found.setAttendanceId(updated.getAttendanceId());
                                                    found.setAttendance(updated.isAttendance());
                                                    found.setObservations(updated.getObservations());
                                                }));
    }

    public void destroyRoom(int subjectId) {

    }

    public Optional<RoomMetadata> findMetadataByRoom(String room) {
        return findMetadataBy(roomMetadata -> roomMetadata.room.equals(room));
    }

    private Optional<RoomMetadata> findMetadataBySubject(SubjectEntity subject) {
        return findMetadataBy(roomMetadata -> roomMetadata.subject.equals(subject));
    }

    private Optional<RoomMetadata> findMetadataBy(Predicate<? super RoomMetadata> predicate) {
        return metadata.keySet()
                    .stream()
                    .filter(predicate)
                    .findFirst();
    }

    private String generateNewRoom() {
        return Util.randomString(4)
                .toUpperCase()
                .concat(String.valueOf(++sequence));
    }


    record RoomMetadata(String room, SubjectEntity subject) { }

}
