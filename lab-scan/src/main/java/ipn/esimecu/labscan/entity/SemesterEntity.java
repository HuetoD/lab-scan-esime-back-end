package ipn.esimecu.labscan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "semestres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SemesterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "semestre_id")
    private int semesterId;

    @Column (name = "fecha_inicio")
    private LocalDate startDate;

    @Column (name = "fecha_fin")
    private LocalDate endDate;

    @OneToMany(mappedBy = "semestreIde", fetch = FetchType.LAZY)
    private List<SubjectEntity> subjects;
    
}
