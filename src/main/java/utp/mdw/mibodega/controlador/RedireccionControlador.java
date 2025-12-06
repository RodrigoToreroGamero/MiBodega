package utp.mdw.mibodega.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedireccionControlador {
    
    @GetMapping("/")
    public String redirigir() {
        return "redirect:/mibodega/login";
    }
}
