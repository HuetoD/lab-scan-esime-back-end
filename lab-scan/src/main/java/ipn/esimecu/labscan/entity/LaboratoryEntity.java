package ipn.esimecu.labscan.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
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
@Table(name = "laboratorios")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"subjectLaboratories"})
@ToString(exclude = {"subjectLaboratories"})
@AllArgsConstructor
@NoArgsConstructor
public class LaboratoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "laboratorio_id")
    private int laboratoryId;

    @Column(name = "nombre")
    private String name;

    @OneToMany(mappedBy = "laboratory", fetch = FetchType.LAZY)
    private List<SubjectLaboratoryEntity> subjectLaboratories;
}
