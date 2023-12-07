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
@Table(name = "asignaturas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grupo_id")
    private int groupId;

    @Column(name = "nombre")
    private String name;

    @Column(name = "turno")
    private char shift;

    @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY)
    private List<SubjectEntity> subjects;
}