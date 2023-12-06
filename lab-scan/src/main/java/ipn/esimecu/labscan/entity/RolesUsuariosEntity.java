package ipn.esimecu.labscan.entity;

import jakarta.persistence.*;
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

      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "usuario_id")
    private UsuariosEntity user;

      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name ="rol_id")
    private RolesEntity rol;   
}
