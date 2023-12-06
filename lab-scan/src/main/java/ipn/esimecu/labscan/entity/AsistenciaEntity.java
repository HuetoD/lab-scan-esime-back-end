package ipn.esimecu.labscan.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "asistencias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AsistenciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column (name = "asistencia_id")
    private int asistenciaId;   
    @Column (name = "fecha")
    private LocalDate fecha;
    @Column (name = "asistencia")
    private byte asistencia;
    @Column (name = "observacion")
    private String observacion;
    @Column (name = "eliminado")
    private LocalDate eliminado;
}
