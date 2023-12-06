package ipn.esimecu.labscan.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "cursos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class HorariosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column (name = "horario_id")
    private int horarioId;
    //@Column (name = "dia")
   // private enum dia {};

   @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name ="asignatura_id")
    private AsignaturasEntity asignaturaIde; 
}