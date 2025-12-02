package utp.mdw.mibodega.configuracion;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UtilidadJwt {
    
    private final SecretKey llaveSecreta;
    
    public UtilidadJwt(@Value("${jwt.secret")String secreto) {
        this.llaveSecreta = Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8));
    }
    
    private final long expiracion = 1000 * 60 * 60 * 10; // 10 horas
    
    public String generarToken(UserDetails detallesUsuario) {
        return Jwts.builder()
                .setSubject(detallesUsuario.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + this.expiracion))
                .signWith(this.llaveSecreta)
                .compact();
    }
    
    private<T> T extraerClaim(String token, Function<Claims, T> resolverClaims) {
        final Claims claims = Jwts.parser()
                .verifyWith(llaveSecreta)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolverClaims.apply(claims);
    }
    
    public String extraerNombreUsuario(String token) {
        return extraerClaim(token, Claims::getSubject);
    }
    
    public Date extraerExpiracion(String token) {
        return extraerClaim(token, Claims::getExpiration);
    }
    
    public Boolean expirado(String token) {
        return extraerExpiracion(token).before(new Date());
    }
    
    public Boolean validarToken(String token, UserDetails detallesUsuario) {
        final String nombreUsuario = extraerNombreUsuario(token);
        return (nombreUsuario.equals(detallesUsuario.getUsername()) && !expirado(token));
    }
}
