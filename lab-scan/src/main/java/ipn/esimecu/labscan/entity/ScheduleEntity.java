package ipn.esimecu.labscan.entity;

import ipn.esimecu.labscan.entity.constant.Day;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Basic;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cursos")
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "horario_id")
    private int scheduleId;

    @Basic
    @Column(name = "day")
    private int dayValue;

    @Transient
    private Day day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="asignatura_id")
    private SubjectEntity subject;

    @PostLoad
    private void postLoad() {
        day = Day.of(dayValue);
    }

    @PrePersist
    private void prePersist() {
        dayValue = day.value();
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }
}
