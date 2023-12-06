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
@Table(name = "laboratorios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class LaboratoriosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column (name = "laboratorio_id")
    private int laboratorioId;
    @Column (name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "laboratorioIde", fetch = FetchType.LAZY)
    private List<AsignaturasEntity> laboratorioIdeEntities;
}
