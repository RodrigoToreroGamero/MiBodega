package utp.mdw.mibodega.dto;

import java.math.BigDecimal;

public class ResumenVentaDTO {
    
    private String nombreProducto;
    private Long cantidadVendida;
    private BigDecimal ingresoTotal;
    
    // Constructor que usará la query JPQL
    public ResumenVentaDTO(String nombreProducto, Long cantidadVendida, BigDecimal ingresoTotal) {
        this.nombreProducto = nombreProducto;
        this.cantidadVendida = cantidadVendida;
        this.ingresoTotal = ingresoTotal;
    }
    
    // Getters y Setters
    public String getNombreProducto() {
        return nombreProducto;
    }
    
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
    
    public Long getCantidadVendida() {
        return cantidadVendida;
    }
    
    public void setCantidadVendida(Long cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }
    
    public BigDecimal getIngresoTotal() {
        return ingresoTotal;
    }
    
    public void setIngresoTotal(BigDecimal ingresoTotal) {
        this.ingresoTotal = ingresoTotal;
    }
}