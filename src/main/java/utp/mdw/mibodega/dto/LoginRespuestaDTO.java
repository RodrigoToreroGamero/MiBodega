package utp.mdw.mibodega.dto;

public class LoginRespuestaDTO {
    private String jwt;

    public LoginRespuestaDTO(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
    
    
}
