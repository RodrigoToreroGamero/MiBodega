package utp.mdw.mibodega.dto;

public class LoginPeticionDTO {
    private String correo;
    private String contrasenia;

    public LoginPeticionDTO() {
    }

    public LoginPeticionDTO(String correo, String contrasenia) {
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
    
}
