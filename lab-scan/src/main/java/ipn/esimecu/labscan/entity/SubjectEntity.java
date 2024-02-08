package ipn.esimecu.labscan.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "asignaturas")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"schedules"})
@ToString(exclude = {"schedules", "subjectLaboratories"})
@AllArgsConstructor
@NoArgsConstructor
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asignatura_id")
    private int subjectId;

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<ScheduleEntity> schedules;

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private List<SubjectLaboratoryEntity> subjectLaboratories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private CourseEntity course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id")
    private TeacherEntity teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo")
    private GroupEntity group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semestre_id")
    private SemesterEntity semester;
}
