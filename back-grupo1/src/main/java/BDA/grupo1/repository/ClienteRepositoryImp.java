package BDA.grupo1.repository;

import BDA.grupo1.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class ClienteRepositoryImp implements ClienteRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public Cliente crear(Cliente cliente) {
        try (Connection con = sql2o.open()) {
            // query para crear un cliente
            String sql = "INSERT INTO cliente (nombre,direccion,email,telefono,contrasena,rol)"
                    + "VALUES (:nombre,:direccion,:email,:telefono,:contrasena,:rol)";
            con.createQuery(sql)
                    .addParameter("nombre", cliente.getNombre())
                    .addParameter("direccion", cliente.getDireccion())
                    .addParameter("email", cliente.getEmail())
                    .addParameter("telefono", cliente.getTelefono())
                    .addParameter("contrasena", cliente.getContrasena())
                    .addParameter("rol", cliente.getRol())
                    .executeUpdate(); // ejecucion de la query
            return cliente;
        } catch (Exception e) {
            System.out.println(e.getMessage()); // mensaje en caso de error
            return null;
        }
    }

    @Override
    public List<Cliente> getAll(){
        try (Connection con = sql2o.open()) {
            // query para obtener todos los clientes de la base de datos
            String sql = "SELECT * FROM cliente";
            return con.createQuery(sql).executeAndFetch(Cliente.class);
        } catch (Exception e){
            System.out.println(e.getMessage()); // mensaje en caso de error
            return null;
        }
    }


    @Override
    public String update(Cliente cliente, Integer id_cliente) {
        try (Connection con = sql2o.open()) {
            // query para actualizar los datos de un cliente
            String sql = "update categoria set nombre = :nombre, direccion = :direccion, email = :email, telefono = :telefono, contrasena = :contrasena"
                    + " WHERE id_cliente = :id_cliente";
            con.createQuery(sql)
                    .addParameter("id_cliente", id_cliente)
                    .addParameter("nombre", cliente.getNombre())
                    .addParameter("direccion", cliente.getDireccion())
                    .addParameter("email", cliente.getEmail())
                    .addParameter("telefono", cliente.getTelefono())
                    .addParameter("contrasena", cliente.getContrasena())
                    .executeUpdate();
            return "Se actualizo el cliente con exito";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Fallo al actualizar el cliente";
        }
    }

    @Override
    public void delete(Integer id_cliente){
        try (Connection con = sql2o.open()) {
            // query para eliminar un cliente según su id
            String sql = "DELETE FROM cliente WHERE id_cliente = :id_cliente";
            con.createQuery(sql)
                    .addParameter("id_cliente", id_cliente)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage()); // mensaje en caso de error
        }
    }

    // método asociado a la autenticación por jwt
    @Override
    public Optional<Cliente> getClienteByCorreo(String email) {
        try (Connection con = sql2o.open()) {
            // query para obtener un cliente según su correo
            String sql = "SELECT * FROM cliente WHERE email = :email";
            return Optional.ofNullable(con.createQuery(sql)
                    .addParameter("email", email)
                    .executeAndFetchFirst(Cliente.class));
        } catch (Exception e) {
            System.out.println("Ocurrió un error al obtener el cliente: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Cliente findByEmail(String email) {
        try (Connection con = sql2o.open()) {
            // query para obtener un cliente según su correo
            String sql = "SELECT * FROM cliente WHERE email = :email";
            return con.createQuery(sql)
                    .addParameter("email", email)
                    .executeAndFetchFirst(Cliente.class);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // mensaje en caso de error
            return null;
        }
    }

    @Override
    public Cliente get_rol_usuario(Integer id_cliente) {
        try (Connection con = sql2o.open()) {
            // query para obtener el rol de un usuario
            String sql = "SELECT rol FROM cliente WHERE id_cliente = :id_cliente";
            return con.createQuery(sql)
                    .addParameter("id_cliente", id_cliente)
                    .executeAndFetchFirst(Cliente.class);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // mensaje en caso de error
            return null;
        }
    }

    // query asignada por la rubica: obtiene el top 5 de clientes que más gastaron en la categoría de tecnología
    @Override
    public String top5Clientes() {
        try (Connection con = sql2o.open()) {
            // creación de la query
            String sql = "SELECT c.nombre AS cliente, SUM(o.total) AS totalGastado " +
                    "FROM cliente c JOIN orden o ON c.id_cliente = o.id_cliente " +
                    "WHERE o.id_orden IN ( " +
                    "  SELECT DISTINCT o2.id_orden " +
                    "  FROM orden o2 " +
                    "  JOIN detalle_orden d ON o2.id_orden = d.id_orden " +
                    "  JOIN producto p ON d.id_producto = p.id_producto " +
                    "  JOIN categoria cat ON p.id_categoria = cat.id_categoria " +
                    "  WHERE cat.nombre = 'Tecnologia' " +
                    "  AND o2.fecha_orden >= NOW() - INTERVAL '1 year' " +
                    ") " +
                    "GROUP BY c.id_cliente, c.nombre " +
                    "ORDER BY totalGastado DESC " +
                    "LIMIT 5";
            List<Map<String, Object>> clientes = con.createQuery(sql).executeAndFetchTable().asList();

            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(clientes);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // mensaje en caso de error
            return null;
        }
    }
}
