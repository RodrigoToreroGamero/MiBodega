package utp.mdw.mibodega.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import utp.mdw.mibodega.servicio.DashboardServicio;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/mibodega")
public class DashboardControlador {

    @Autowired
    private DashboardServicio dashboardService;

    @GetMapping("/dashboard")
    public String mostrarDashboard(@RequestParam(value = "filtro", defaultValue = "todos") String filtro,
        Model model) {

        // Fecha y hora actual
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yy"));
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        model.addAttribute("fecha", fecha);
        model.addAttribute("hora", hora);

        // Métricas del dashboard
        //Map<String, Long> metricas = dashboardService.obtenerMetricas();
        long tVentas = dashboardService.totalVentas(filtro);
        long pPendientes = dashboardService.pedidosPendientes();
        long pAgotados = dashboardService.productosAgotados();
        long nClientes = dashboardService.nuevosClientes();
        
        model.addAttribute("ventasTotales",tVentas);
        model.addAttribute("pedidosPendientes",pPendientes);
        model.addAttribute("productosAgotados",pAgotados);
        model.addAttribute("nuevosClientes",nClientes);
        model.addAttribute("filtroActual", filtro);
        

        return "miBodega_dashboard"; // plantilla Thymeleaf
    }
}
