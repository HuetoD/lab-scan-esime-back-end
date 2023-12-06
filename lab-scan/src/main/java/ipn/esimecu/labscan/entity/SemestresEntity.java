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




public class SemestresEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "semestre_id")
    private int semesteId;
    @Column (name = "fecha_inicio")
    private LocalDate fechaInicio;
    @Column (name = "fecha_fin")
    private LocalDate fechaFin;

    @OneToMany(mappedBy = "semestreIde", fetch = FetchType.LAZY)
    private List<AsignaturasEntity> semestreIdeEntities;
    
}
