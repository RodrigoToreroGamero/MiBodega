package utp.mdw.mibodega.servicio;

import java.util.List;
import org.springframework.stereotype.Service;
import utp.mdw.mibodega.modelo.Notificacion;
import utp.mdw.mibodega.repositorio.NotificacionRepositorio;

@Service
public class NotificacionServicio {

    private final NotificacionRepositorio notificacionRepositorio;

    public NotificacionServicio(NotificacionRepositorio notificacionRepositorio) {
        this.notificacionRepositorio = notificacionRepositorio;
    }

    public List<Notificacion> obtenerUltimasNotificaciones(int limite) {
        return notificacionRepositorio.findTopNByOrderByFechaCreacionDesc(limite);
    }
}
