package utp.mdw.mibodega.modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "detalles_ventas")

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DetalleVenta {
    
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta venta;
    
    @NotNull
    @Min(1)
    @Max(9999)
    private Integer cantidad;
    
    @NotNull
    @DecimalMin(value = "0.00", inclusive = true)
    @Column(name = "precio_unitario", precision = 10, scale = 2)
    private BigDecimal precioUnitario;
    
    @NotNull
    @DecimalMin(value = "0.00", inclusive = true)
    @Column(name = "precio_total", precision = 10, scale = 2)
    private BigDecimal precioTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

   
}
