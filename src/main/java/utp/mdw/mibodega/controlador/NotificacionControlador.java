package utp.mdw.mibodega.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import utp.mdw.mibodega.modelo.Notificacion;
import utp.mdw.mibodega.servicio.NotificacionServicio;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionControlador {

    private final NotificacionServicio notificacionServicio;

    public NotificacionControlador(NotificacionServicio notificacionServicio) {
        this.notificacionServicio = notificacionServicio;
    }

    /**
     * Devuelve las últimas notificaciones en formato JSON
     */
    @GetMapping
    public List<Notificacion> obtenerNotificacionesRecientes() {
        return notificacionServicio.obtenerUltimasNotificaciones(5);
    }
    
}
