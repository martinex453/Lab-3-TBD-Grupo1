package BDA.grupo1.authentication.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa la respuesta de autenticación que contiene el token JWT generado
 * tras un inicio de sesión exitoso.
 *
 * Esta clase se utiliza para devolver al cliente el JWT como parte de la respuesta
 * a la solicitud de autenticación. El token JWT es esencial para validar las
 * solicitudes subsiguientes a la API y mantener al usuario autenticado en el sistema.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token; // El token JWT generado para el usuario autenticado.
}
