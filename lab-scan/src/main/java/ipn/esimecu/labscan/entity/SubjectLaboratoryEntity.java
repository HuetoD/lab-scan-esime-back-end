package ipn.esimecu.labscan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "asignaturas_laboratorios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectLaboratoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asignaturas_labs_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "asignatura_id")
    private SubjectEntity subject;

    @ManyToOne
    @JoinColumn(name = "lab_id")
    private LaboratoryEntity laboratory;

}
