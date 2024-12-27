package BDA.grupo1.service;

import BDA.grupo1.model.Cliente;
import BDA.grupo1.repository.ClienteRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // servicio para crear un cliente
    public Cliente crear(Cliente cliente) {
        return clienteRepository.crear(cliente);
    }

    // servicio para obtener todos los clientes
    public List<Cliente> findAll(){
        return clienteRepository.getAll();
    }

    // servicio para actualizar los datos de un cliente
    public String update(Cliente cliente, Integer id) {
        return clienteRepository.update(cliente, id);
    }

    // servicio para eliminar un cliente según su id
    public void delete(Integer id) {
        clienteRepository.delete(id);
    }

    // servicio para buscar un cliente
    public Cliente findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    // servicio para obtener el rol de un usuario
    public String get_rol_cliente(Integer id_cliente) {
        Cliente c = clienteRepository.get_rol_usuario(id_cliente);
        return c.getRol();
    }

    // servicio encargado de crear una cuenta
    public Cliente crear_cuenta(Cliente cliente) {
        String correo = cliente.getEmail();
        Cliente buscar_cliente = clienteRepository.findByEmail(correo);
        String encodedPasssword = generateEncodedPassword(cliente.getContrasena()); // encriptación de la contraseña

        Cliente NuevoCliente =  new Cliente(cliente.getId_cliente(), cliente.getNombre(), cliente.getDireccion(),
                cliente.getEmail(), cliente.getTelefono(), encodedPasssword, cliente.getRol());

        if (buscar_cliente == null) { // si el cliente no existe se crea
            return clienteRepository.crear(NuevoCliente);
        } else { // si el cliente ya existe no se crea
            return null;
        }
    }

    // servicio para encriptar la contraseña de un usuario
    private String generateEncodedPassword(String passsword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(passsword);
    }

    // Relacionado al Authentication
    public Cliente getClienteByCorreo(@NonNull String email){
        return clienteRepository
                .getClienteByCorreo(email)
                .orElseThrow(() -> new UsernameNotFoundException("No existe el cliente"));
    }

    // servicio para llamar a la query top5Clientes
    public String top5Clientes() { return clienteRepository.top5Clientes(); }
}
