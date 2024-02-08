package ipn.esimecu.labscan.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface SuperMapper<E, DTO extends Serializable> {

    E map(DTO dto);

    default E map(DTO dto, E source) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    DTO map(E entity);

    default List<E> mapAllEntities(List<DTO> dtos) {
        return map(dtos, this::map);
    }

    default  List<DTO> mapAllDTOs(List<E> entities) {
        return map(entities, this::map);
    }

    private <T, R> List<R> map(List<T> source, Function<T, R> mapper) {
        return source.stream()
                    .map(mapper)
                    .collect(Collectors.toList());
    }

}
