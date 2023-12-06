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
@Table(name = "roles_usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor





public class RolesUsuariosEntity {
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column (name = "rol_usuario_id")
    private int rolUsuarioId;
   
}
