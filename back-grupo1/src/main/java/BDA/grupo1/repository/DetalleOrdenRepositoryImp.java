package BDA.grupo1.repository;

import BDA.grupo1.model.DetalleOrden;
import BDA.grupo1.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class DetalleOrdenRepositoryImp implements DetalleOrdenRepository{

    @Autowired
    private Sql2o sql2o;

    public DetalleOrden crear(DetalleOrden detalleOrden) {
        try (Connection con = sql2o.open()) {
            // query para crear un detalle de orden
            String sql = "INSERT INTO detalle_orden (id_orden, id_producto, cantidad, precio_unitario) " +
                    "VALUES (:id_orden, :id_producto, :cantidad, :precio_unitario)";
            con.createQuery(sql)
                    .addParameter("id_orden", detalleOrden.getId_orden())
                    .addParameter("id_producto", detalleOrden.getId_producto())
                    .addParameter("cantidad", detalleOrden.getCantidad())
                    .addParameter("precio_unitario", detalleOrden.getPrecio_unitario())
                    .executeUpdate(); // ejecución de la query
            return detalleOrden;
        } catch (Exception e) {
            System.out.println(e.getMessage()); // mensaje en caso de error
            return null;
        }
    }

    public List<DetalleOrden> getAll() {
        try (Connection con = sql2o.open()) {
            // query para obtener todos los detalles de orden de la tabla
            String sql = "SELECT * FROM detalle_orden";
            return con.createQuery(sql).executeAndFetch(DetalleOrden.class);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // mensaje en caso de error
            return null;
        }
    }

    public String update(DetalleOrden detalleOrden, Integer id_detalle) {
        try (Connection con = sql2o.open()) {
            // query para actualizar los datos del detalle de una orden
            String sql = "UPDATE detalle_orden SET id_orden = :id_orden, id_producto = :id_producto, " +
                    "cantidad = :cantidad, precio_unitario = :precio_unitario WHERE id_detalle = :id_detalle";
            con.createQuery(sql)
                    .addParameter("id_detalle", id_detalle)
                    .addParameter("id_orden", detalleOrden.getId_orden())
                    .addParameter("id_producto", detalleOrden.getId_producto())
                    .addParameter("cantidad", detalleOrden.getCantidad())
                    .addParameter("precio_unitario", detalleOrden.getPrecio_unitario())
                    .executeUpdate(); // ejecución de la query
            return "Se actualizó el detalle de la orden con éxito";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Fallo al actualizar el detalle de la orden";
        }
    }

    public void delete(Integer id_detalle) {
        try (Connection con = sql2o.open()) {
            // query para eliminar un detalle de orden según su id
            String sql = "DELETE FROM detalle_orden WHERE id_detalle = :id_detalle";
            con.createQuery(sql)
                    .addParameter("id_detalle", id_detalle)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<DetalleOrden> getdetalleOrdenByOrdenId(Integer id_orden) {
        try (Connection con = sql2o.open()) {
            // query para obtener los detalles de orden ordenados según su identificador
            String sql = "SELECT * FROM detalle_orden WHERE id_orden = :id_orden";
            return con.createQuery(sql)
                    .addParameter("id_orden", id_orden)
                    .executeAndFetch(DetalleOrden.class);
        } catch (Exception e) {
            System.out.println("Error al obtener los detalles de la orden: " + e.getMessage());
            return null;
        }
    }
}
