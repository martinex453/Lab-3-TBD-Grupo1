package BDA.grupo1.repository;

import BDA.grupo1.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class ProductoRepositoryImp implements ProductoRepository{

    @Autowired
    private Sql2o sql2o;

    public Producto crear(Producto producto) {
        try (Connection con = sql2o.open()) {
            // query para crear un producto
            String sql = "INSERT INTO producto ( nombre, descripcion, precio, stock, estado, id_categoria) " +
                    "VALUES (:nombre, :descripcion, :precio, :stock, :estado, :id_categoria)";
            con.createQuery(sql)
                    .addParameter("nombre", producto.getNombre())
                    .addParameter("descripcion", producto.getDescripcion())
                    .addParameter("precio", producto.getPrecio())
                    .addParameter("stock", producto.getStock())
                    .addParameter("estado", producto.getEstado())
                    .addParameter("id_categoria", producto.getId_categoria())
                    .executeUpdate(); // ejecución de la query
            return producto;
        } catch (Exception e) {
            System.out.println(e.getMessage()); // mensaje en caso de error
            return null;
        }
    }

    public List<Producto> getAll() {
        try (Connection con = sql2o.open()) {
            // query para obtener todos los productos de la tabla
            String sql = "SELECT * FROM producto";
            return con.createQuery(sql).executeAndFetch(Producto.class);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // mensaje en caso de error
            return null;
        }
    }

    public List<Producto> getProductoPage(int page, int pageSize){
        try (Connection con = sql2o.open()) {
            // query para obtener los productos según el tamaño de la página
            String sql = "SELECT * FROM producto ORDER BY id_producto LIMIT :pageSize OFFSET :offset";
            int offset = (page - 1) * pageSize;

            try(Connection con2 = sql2o.open()) {
                return con.createQuery(sql)
                        .addParameter("pageSize",pageSize)
                        .addParameter("offset",offset)
                        .executeAndFetch(Producto.class); // ejecución de la query
            }
        }
    }

    public String update(Producto producto, Integer id_producto) {
        try (Connection con = sql2o.open()) {
            // query para actualizar los datos de un producto
            String sql = "UPDATE producto SET nombre = :nombre, descripcion = :descripcion, precio = :precio, " +
                    "stock = :stock, estado = :estado, id_categoria = :id_categoria " +
                    "WHERE id_producto = :id_producto";
            con.createQuery(sql)
                    .addParameter("id_producto", id_producto)
                    .addParameter("nombre", producto.getNombre())
                    .addParameter("descripcion", producto.getDescripcion())
                    .addParameter("precio", producto.getPrecio())
                    .addParameter("stock", producto.getStock())
                    .addParameter("estado", producto.getEstado())
                    .addParameter("id_categoria", producto.getId_categoria())
                    .executeUpdate(); // ejecución de la query
            return "Se actualizó el producto con éxito";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Fallo al actualizar el producto";
        }
    }

    public void delete(Integer id_producto) {
        try (Connection con = sql2o.open()) {
            // query para eliminar un producto según su identificador
            String sql = "DELETE FROM producto WHERE id_producto = :id_producto";
            con.createQuery(sql)
                    .addParameter("id_producto", id_producto)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage()); // mensaje en caso de error
        }
    }

    public Producto getproductoById(Integer id_producto) {
        try (Connection con = sql2o.open()) {
            // query para obtener un producto según su identificador
            String sql = "SELECT * FROM producto WHERE id_producto = :id_producto";
            return con.createQuery(sql)
                    .addParameter("id_producto", id_producto)
                    .executeAndFetchFirst(Producto.class);
        } catch (Exception e) {
            System.out.println("Error al obtener el producto: " + e.getMessage()); // mensaje en caso de error
            return null;
        }
    }

    public void updateProductoStock(Integer id_producto, Integer stock){
        try (Connection con = sql2o.open()) {
            // query para actualizar el stock de un producto
            String sql = "UPDATE producto SET stock = :stock WHERE id_producto = :id_producto";
            con.createQuery(sql)
                    .addParameter("id_producto",id_producto)
                    .addParameter("stock",stock)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage()); // mensaje en caso de error
        }
    }

    public Integer getTotalPages(Integer pageSize){
        try (Connection con = sql2o.open()) {
            String sql = "SELECT CEILING(count(*)*1.0/:pageSize) AS CANTIDAD FROM producto";
            return  con.createQuery(sql)
                    .addParameter("pageSize", pageSize)
                    .executeScalar(Integer.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public List<Producto> getProductosAleatoreosByCategoria(Integer id_categoria){
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM producto WHERE id_categoria = :id_categoria AND estado = :estado ORDER BY RANDOM() LIMIT 2";
            return con.createQuery(sql)
                    .addParameter("id_categoria", id_categoria)
                    .addParameter("estado", "disponible")
                    .executeAndFetch(Producto.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
