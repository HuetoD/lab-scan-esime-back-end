package ipn.esimecu.labscan.entity;


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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "asignaturas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor



public class AsignaturasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column (name = "asignatura_id")
    private int asignaturaId;   

    @OneToMany(mappedBy = "asignaturaIde", fetch = FetchType.LAZY)
    private List<EstudiantesAsignaturasEntity> asignaturaIdeEntities;

    @OneToMany(mappedBy = "asignaturasIde", fetch = FetchType.LAZY)
    private List<AsistenciaEntity> asignaturasIdeEntities;

    @OneToMany(mappedBy = "asignaturaIde", fetch = FetchType.LAZY)
    private List<HorariosEntity> asignaturaIdEntities;
    
     @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name ="curso_id")
    private CursosEntity cursoIde;   

    @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name ="profesor_id")
    private ProfesoresEntity profesorIde;  

    @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name ="grupo")
    private GruposEntity grupo; 

     @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name ="semestre_id")
    private SemestresEntity semestreIde; 

    @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name ="laboratorio_id")
    private LaboratoriosEntity laboratorioIde; 
}
