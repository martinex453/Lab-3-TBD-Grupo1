package BDA.grupo1.repository;

import BDA.grupo1.model.ClienteSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

@Repository
public class ClienteSesionRepositoryImp implements ClienteSesionRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public Integer crear(Integer cliente_id) {
        try (Connection con = sql2o.open()) {
            // query para insertar el id del cliente en la tabla cliente_sesion
            String sql = "INSERT INTO cliente_sesion (cliente_id)"
                    + "VALUES (:cliente_id)";
            con.createQuery(sql)
                    .addParameter("cliente_id", cliente_id)
                    .executeUpdate();
            return cliente_id;
        } catch (Exception e) {
            System.out.println(e.getMessage()); // mensaje en caso de error
            return null;
        }
    }

    @Override
    public void deleteAll(){
        try (Connection con = sql2o.open()) {
            // query para eliminar todos los elementos de la tabla cliente_sesion
            String sql = "DELETE FROM cliente_sesion";
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage()); // mensaje en caso de error
        }
    }
}
