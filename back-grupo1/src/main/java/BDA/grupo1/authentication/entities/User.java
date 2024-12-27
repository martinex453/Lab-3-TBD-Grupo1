package BDA.grupo1.authentication.entities;

import BDA.grupo1.model.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representa al usuario autenticado en el sistema, implementando la interfaz UserDetails de Spring Security.
 *
 * Esta clase se utiliza para adaptar la información del usuario a un formato que Spring Security pueda manejar
 * en su proceso de autenticación y autorización.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    private Long id;
    private String nombre;
    private String email;
    private String contrasena;
    private static String ROLE = "CLIENTE";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(ROLE));
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static User clienteToUser(Cliente cliente){
        return new User(cliente.getId_cliente(), cliente.getNombre(), cliente.getEmail(), cliente.getContrasena());
    }

    public Map<String, Object> generateExtraClaims() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("nombre", nombre);
        return claims;
    }
}

