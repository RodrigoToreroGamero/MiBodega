package utp.mdw.mibodega.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mibodega")
public class LogoutControlador {
    
    @GetMapping("/cerrarsesion")
    public String logout() {
        return "miBodega_logout";
    }
}
