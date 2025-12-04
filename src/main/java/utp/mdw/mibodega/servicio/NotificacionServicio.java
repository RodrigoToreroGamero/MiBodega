package utp.mdw.mibodega.servicio;

import java.util.List;
import org.springframework.stereotype.Service;
import utp.mdw.mibodega.modelo.Notificacion;
import utp.mdw.mibodega.repositorio.NotificacionRepositorio;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import utp.mdw.mibodega.modelo.Venta;
import utp.mdw.mibodega.modelo.Producto;
import utp.mdw.mibodega.modelo.Notificacion.TipoNotificacion;
import utp.mdw.mibodega.modelo.Notificacion.EstadoNotificacion;
import utp.mdw.mibodega.modelo.Usuario;

@Service
public class NotificacionServicio {

    private final NotificacionRepositorio notificacionRepositorio;

    public NotificacionServicio(NotificacionRepositorio notificacionRepositorio) {
        this.notificacionRepositorio = notificacionRepositorio;
    }

    public List<Notificacion> obtenerUltimasNotificaciones(int limite) {
        return notificacionRepositorio.findTopNByOrderByFechaCreacionDesc(limite);
    }

    /**
     * Crea una notificación genérica
     */
    @Transactional
    public Notificacion crearNotificacion(String mensaje, TipoNotificacion tipo, Usuario usuario) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(mensaje);
        notificacion.setTipo(tipo);
        notificacion.setUsuario(usuario);
        notificacion.setFechaCreacion(LocalDateTime.now());
        notificacion.setEstado(EstadoNotificacion.enviada);

        return notificacionRepositorio.save(notificacion);
    }

    /**
     * Crea notificación cuando se registra una venta pendiente
     */
    @Transactional
    public void notificarVentaPendiente(Venta venta) {
        String mensaje = String.format("Nueva venta pendiente #%d por S/ %.2f",
                venta.getId(),
                venta.getPrecioTotal());

        crearNotificacion(mensaje, TipoNotificacion.info, venta.getUsuario());
    }

    /**
     * Crea notificación cuando un cliente nuevo se registra
     */
    @Transactional
    public void notificarNuevoCliente(String nombreCliente, Usuario usuario) {
        String mensaje = String.format("Se acaba de registrar un cliente nuevo: %s", nombreCliente);

        crearNotificacion(mensaje, TipoNotificacion.info, usuario);
    }

    /**
     * Marca una notificación como leída
     */
    @Transactional
    public void marcarComoLeida(Long idNotificacion) {
        Notificacion notificacion = notificacionRepositorio.findById(idNotificacion)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        notificacion.setEstado(EstadoNotificacion.leida);
        notificacionRepositorio.save(notificacion);
    }
    
}
