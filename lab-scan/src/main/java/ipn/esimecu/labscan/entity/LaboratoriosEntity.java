package ipn.esimecu.labscan.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
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
}
