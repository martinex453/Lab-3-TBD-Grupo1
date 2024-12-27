package BDA.grupo1.repository;

import BDA.grupo1.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class CategoriaRepositoryImp implements CategoriaRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public Categoria crear(Categoria categoria) {
        try (Connection conn = sql2o.open()) {
            // SQL para insertar una nueva categoría en la base de datos.
            String sql = "INSERT INTO categoria (nombre) VALUES (:nombre)";
            // Ejecutar la consulta, vinculando el parámetro 'nombre' al valor de la categoría.
            conn.createQuery(sql)
                    .addParameter("nombre", categoria.getNombre())
                    .executeUpdate();
            return categoria;  // Retorna la categoría que fue insertada en la base de datos.
        } catch (Exception e) {
            // En caso de error, imprime el mensaje de error y retorna null.
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Categoria> getAll() {
        try (Connection conn = sql2o.open()) {
            // SQL para seleccionar todas las categorías de la base de datos.
            String sql = "SELECT * FROM categoria";
            // Ejecuta la consulta y mapea el resultado a objetos de tipo Categoria.
            return conn.createQuery(sql).executeAndFetch(Categoria.class);
        } catch (Exception e) {
            // En caso de error, imprime el mensaje de error y retorna null.
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public String update(Categoria categoria, Integer id) {
        try (Connection conn = sql2o.open()) {
            // SQL para actualizar el nombre de la categoría en la base de datos.
            String updatesql = "update categoria set nombre = :nombre where id_categoria = :id_categoria";
            // Ejecuta la consulta, vinculando los parámetros 'id_categoria' y 'nombre'.
            conn.createQuery(updatesql)
                    .addParameter("id_categoria", id)
                    .addParameter("nombre", categoria.getNombre())
                    .executeUpdate();
            return "Categoria actualizada";  // Retorna un mensaje indicando que la categoría fue actualizada.
        } catch (Exception e) {
            // En caso de error, imprime el mensaje de error y retorna null.
            System.out.println(e.getMessage());
        }
        return null;  // Si ocurre un error, retorna null.
    }

    @Override
    public void delete(Integer id) {
        try (Connection conn = sql2o.open()) {
            // SQL para eliminar la categoría con el ID especificado de la base de datos.
            String sql = "DELETE FROM categoria WHERE id_categoria = :id_categoria";
            // Ejecuta la consulta, vinculando el parámetro 'id_categoria'.
            conn.createQuery(sql)
                    .addParameter("id_categoria", id)
                    .executeUpdate();
        } catch (Exception e) {
            // En caso de error, imprime el mensaje de error.
            System.out.println(e.getMessage());
        }
    }
}
