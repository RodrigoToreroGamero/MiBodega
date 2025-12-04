package utp.mdw.mibodega.controlador;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import utp.mdw.mibodega.dto.ResumenVentaDTO;
import utp.mdw.mibodega.servicio.DashboardServicio;
import utp.mdw.mibodega.servicio.VentaServicio;

@Controller
@RequestMapping("/mibodega")
public class VentasControlador {

    @Autowired
    private VentaServicio ventaServicio;

    @Autowired
    private DashboardServicio dashboardServicio;

    @GetMapping("/ventas")
    public String mostrarVentas(
            @RequestParam(value = "filtro", defaultValue = "todos") String filtro,
            Model model) {

        // Fecha y hora actual
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yy"));
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        model.addAttribute("fecha", fecha);
        model.addAttribute("hora", hora);

        // Métricas con filtro
        long ventasTotales = dashboardServicio.totalVentas(filtro);
        long pedidosPendientes = dashboardServicio.pedidosPendientes();
        String productoMasVendido = ventaServicio.obtenerProductoMasVendido(filtro);
        List<ResumenVentaDTO> resumenVentas = ventaServicio.obtenerResumenVentas(filtro);

        model.addAttribute("ventasTotales", ventasTotales);
        model.addAttribute("pedidosPendientes", pedidosPendientes);
        model.addAttribute("productoMasVendido", productoMasVendido != null ? productoMasVendido : "N/A");
        model.addAttribute("resumenVentas", resumenVentas);
        model.addAttribute("filtroActual", filtro);

        return "miBodega_ventas";
    }
}
