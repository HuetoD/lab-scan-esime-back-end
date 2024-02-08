package ipn.esimecu.labscan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "asignaturas_laboratorios")
@EqualsAndHashCode(exclude = {"studentSubjects", "subject"})
@ToString(exclude = {"studentSubjects"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectLaboratoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asignatura_lab_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "asignatura_id")
    private SubjectEntity subject;

    @ManyToOne
    @JoinColumn(name = "lab_id")
    private LaboratoryEntity laboratory;

    @OneToMany(mappedBy = "subjectLab")
    private List<StudentSubjectEntity> studentSubjects;

    @OneToMany(mappedBy = "subjectLab")
    private List<AttendanceEntity> attendances;

}
