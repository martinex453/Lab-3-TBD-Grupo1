package BDA.grupo1.repository;

import BDA.grupo1.model.Zona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class ZonaRepositoryImp implements ZonaRepository{

    @Autowired
    private Sql2o sql2o;

    public Zona crear(Zona zona){
        try(Connection con = sql2o.open()){
            String sql = "INSERT INTO zona (nombre,tipo,zona_geom)" +
                    "VALUES(:nombre, :tipo, :zona_geom)";
            con.createQuery(sql)
                    .addParameter("nombre",zona.getNombre())
                    .addParameter("tipo", zona.getTipo())
                    .addParameter("zona_geom",zona.getZona_geom())
                    .executeUpdate();
            return zona;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Zona> getAll(){
        try(Connection con = sql2o.open()){
            String sql = "SELECT id_zona, nombre, tipo FROM zona";
             return con.createQuery(sql).executeAndFetch(Zona.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String update(Zona zona, Integer id_zona){
        try(Connection con = sql2o.open()){
            String sql = "UPDATE zona SET nombre = :nombre, tipo = :tipo, zona_geom =:zona_geom WHERE id_zona = :id_zona";
            con.createQuery(sql)
                    .addParameter("nombre", zona.getNombre())
                    .addParameter("tipo",zona.getTipo())
                    .addParameter("zona_geom", zona.getZona_geom())
                    .addParameter("id_zona",id_zona)
                    .executeUpdate();
            return "Se actualizo la Zona con exito";
        } catch (Exception e){
            System.out.println(e.getMessage());
            return "Fallo al actualizar la zona";
        }
    }

    public void delete(Integer id_zona){
        try(Connection con = sql2o.open()){
            String sql = "DELETE FROM zona WHERE id_zona = :id_zona";
            con.createQuery(sql)
                    .addParameter("id_zona",id_zona)
                    .executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public List<Zona> zonas_disponibles(String tipo) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT id_zona, nombre FROM zona WHERE tipo = :tipo";
            return con.createQuery(sql)
                    .addParameter("tipo", tipo) // Asigna el valor al parámetro
                    .executeAndFetch(Zona.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
