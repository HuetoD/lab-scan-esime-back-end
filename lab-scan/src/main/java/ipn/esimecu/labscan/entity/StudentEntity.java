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
    private int studentId;

    @Column(name = "identificacion")
    private String identification;

    @Column(name = "nombre_completo")
    private String fullName;

    @Column(name = "numero_pc")
    private String pcNumber;

    @Column(name = "codigo_qr")
    private String qrCode;

    @Column(name = "creacion_sacadem")
    private LocalDate sacademDate;

    @Column(name = "foto")
    private byte[] photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_identificacion")
    private IdentificationTypeEntity identificationType;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<StudentSubjectEntity> studentSubjects;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<AttendanceEntity> attendances;

}
