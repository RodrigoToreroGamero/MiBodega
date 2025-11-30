package utp.mdw.mibodega.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "movimientos_inventario")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum TipoMovimiento {
        entrada,
        salida;
    }

    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipo;

    private Integer cantidad;

    private String referencia;

    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;
}
