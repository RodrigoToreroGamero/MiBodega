package utp.mdw.mibodega.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "notificaciones")

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Notificacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    
    private String mensaje;
    
    public enum TipoNotificacion {
        info,
        advertencia,
        error;
    }
    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoNotificacion tipo;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    public enum EstadoNotificacion {        
        enviada,
        leida,
        archivada;        
    }
    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoNotificacion estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public TipoNotificacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoNotificacion tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public EstadoNotificacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoNotificacion estado) {
        this.estado = estado;
    }

    
}
