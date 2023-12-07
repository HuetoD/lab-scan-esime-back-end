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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "tipo_identificacion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IdentificationTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "identificacion_id")
    private int id;

    @Column (name = "tipo_identificacion")
    private String identificationType;

    @OneToMany(mappedBy = "tipoIde", fetch = FetchType.LAZY)
    private List<StudentEntity> students;
}
