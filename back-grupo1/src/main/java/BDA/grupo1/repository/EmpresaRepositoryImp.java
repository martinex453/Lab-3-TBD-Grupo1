package BDA.grupo1.repository;

import BDA.grupo1.model.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;
@Repository
public class EmpresaRepositoryImp implements EmpresaRepository{

    @Autowired
    private Sql2o sql2o;

    public Empresa crear(Empresa empresa){
        try(Connection con = sql2o.open()){
            String sql = "INSERT INTO empresa (rut,nombre)" +
                    "VALUES (:rut,:nombre)";
            con.createQuery(sql)
                    .addParameter("rut", empresa.getRut())
                    .addParameter("nombre", empresa.getNombre())
                    .executeUpdate();
            return empresa;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Empresa> getAll(){
        try(Connection con = sql2o.open()){
            String sql = "SELECT * FROM empresa";
            return con.createQuery(sql).executeAndFetch(Empresa.class);

        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String update(Empresa empresa, Integer id){
        try(Connection con = sql2o.open()){
            String sql = "UPDATE empresa SET nombre =:nombre, rut =:rut"
                    +"WHERE id_empresa =:id_empresa";
            con.createQuery(sql)
                    .addParameter("id_empresa",id)
                    .addParameter("rut", empresa.getRut())
                    .addParameter("nombre",empresa.getNombre())
                    .executeUpdate();
            return "Se actualizo la empresa con exito";
        } catch (Exception e){
            System.out.println(e.getMessage());
            return "Fallo al actualizar empresa";
        }
    }

    public void delete(Integer id){
        try (Connection con = sql2o.open()){
            String sql = "DELETE FROM empresa WHERE id_empresa =: id";
            con.createQuery(sql)
                    .addParameter("id_empresa",id)
                    .executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Double areaZona(Long id_empresa){
        try(Connection con = sql2o.open()){
            String sql = "SELECT ST_Area(ST_Transform(ST_Union(z.zona_geom), 3857)) " +
                    "FROM empresa e " +
                    "JOIN empresa_zona ez ON e.id_empresa = ez.id_empresa " +
                    "JOIN zona z ON ez.id_zona = z.id_zona " +
                    "WHERE z.tipo = 'reparto' AND e.id_empresa = :id_empresa " +
                    "GROUP BY e.nombre ";
            Double resultado = con.createQuery(sql)
                    .addParameter("id_empresa",id_empresa)
                    .executeScalar(Double.class);
            System.out.println(resultado);
            return resultado;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return 0.0;
        }
    }
}
