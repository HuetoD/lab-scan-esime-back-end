package ipn.esimecu.labscan.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "estudiantes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estudiante_id")
    private int estudianteId;
    @Column(name = "identificacion")
    private String identificacion;
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    @Column(name = "numero_pc")
    private String numeroPc;
    @Column(name = "codigo_qr")
    private String codigoQr;
    @Column(name = "creacion_sacadem")
    private LocalDate creacionSacadem;
    @Column (name = "foto")
    private byte[] foto;

     @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name ="tipo_identificacion")
    private TipoIdentificacionesEntity tipoIde;   

    @OneToMany(mappedBy = "estudianteIde", fetch = FetchType.LAZY)
    private List<EstudiantesAsignaturasEntity> EstudianteIdEntities;

     @OneToMany(mappedBy = "estudiantesIde", fetch = FetchType.LAZY)
    private List<AsistenciaEntity> EstudiantesIdEntities;

}
