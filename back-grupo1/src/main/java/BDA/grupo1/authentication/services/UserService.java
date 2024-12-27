package BDA.grupo1.authentication.services;


import BDA.grupo1.authentication.entities.User;
import BDA.grupo1.model.Cliente;
import BDA.grupo1.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    ClienteService ClienteService;

    // Metodo que obtiene el email del usuario a partir de su username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByEmail(username);
    }

    // Metodo que obtiene el email del usuario
    public User getUserByEmail(String email) {
        Cliente cliente = ClienteService.getClienteByCorreo(email);
        if (cliente == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }
        System.out.println(cliente);
        return User.clienteToUser(cliente);
    }
}

