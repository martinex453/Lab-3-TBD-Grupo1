package BDA.grupo1.repository;

import BDA.grupo1.model.Orden_repartidor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class Orden_repartidorRepositoryImp implements Orden_repartidorRepository {

    @Autowired
    private Sql2o sql2o;

    public Orden_repartidor crear(Orden_repartidor ordenZonaRepartidor) {
        try (Connection con = sql2o.open()) {
            // query para crear una orden
            String sql = "INSERT INTO orden_repartidor (id_orden,id_repartidor) " +
                    "VALUES (:id_orden, :id_repartidor)";
            con.createQuery(sql)
                    .addParameter("id_orden", ordenZonaRepartidor.getId_orden())
                    .addParameter("id_repartidor", ordenZonaRepartidor.getId_repartidor())
                    .executeUpdate(); // ejecución de la query
            return ordenZonaRepartidor;
        } catch (Exception e) {
            System.out.println(e.getMessage()); // mensaje en caso de error
            return null;
        }
    }

    public List<Orden_repartidor> getAll() {
        try (Connection con = sql2o.open()) {
            // query para obtener todas las ordenes de la tabla
            String sql = "SELECT * FROM orden_repartidor";
            return con.createQuery(sql).executeAndFetch(Orden_repartidor.class);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // mensaje en caso de error
            return null;
        }
    }

    public String update(Orden_repartidor ordenZonaRepartidor, Integer id) {
        try (Connection con = sql2o.open()) {
            // query para actualizar los datos de una orden
            String sql = "UPDATE orden_repartidor SET id_orden = :id_orden, id_repartidor = :id_repartidor" +
                    "WHERE id = :id";
            con.createQuery(sql)
                    .addParameter("id_orden", ordenZonaRepartidor.getId_orden())
                    .addParameter("id_repartidor", ordenZonaRepartidor.getId_repartidor())
                    .addParameter("id", id)
                    .executeUpdate(); // ejecución de la query
            return "Se actualizó la orden con éxito";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Fallo al actualizar la orden";
        }
    }

    public void delete(Integer id) {
        try (Connection con = sql2o.open()) {
            // query para eliminar una orden según su identificador
            String sql = "DELETE FROM orden_repartidor WHERE id = :id";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage()); // mensaje en caso de error
        }
    }
}
