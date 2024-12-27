package BDA.grupo1.repository;


import BDA.grupo1.model.Empresa;
import BDA.grupo1.model.Empresa_zona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class Empresa_zonaRepositoryImp implements Empresa_zonaRepository{

    @Autowired
    private Sql2o sql2o;

    public Empresa_zona crear(Empresa_zona empresaZona){
        try(Connection con = sql2o.open()){
            String sql = "INSERT INTO empresa_zona (id_empresa,id_zona)" +
                    "VALUES (:id_empresa,:id_zona)";
            con.createQuery(sql)
                    .addParameter("id_empresa",empresaZona.getId_empresa())
                    .addParameter("id_zona", empresaZona.getId_zona())
                    .executeUpdate();
            return empresaZona;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Empresa_zona> getAll(){
        try (Connection con = sql2o.open()){
            String sql = "SELECT * FROM empresa_zona";
            return con.createQuery(sql).executeAndFetch(Empresa_zona.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String update(Empresa_zona empresaZona, Integer id){
        try ( Connection con = sql2o.open()){
            String sql = "UPDATE empresa_zona SET id_empresa =:id_empresa, id_zona =:id_zona" +
                    "WHERE id_empresa_zona =:id_empresa_zona";
            con.createQuery(sql)
                    .addParameter("id_zona",empresaZona.getId_zona())
                    .addParameter("id_empresa",empresaZona.getId_empresa())
                    .addParameter("id_empresa_zona", id)
                    .executeUpdate();
            return "Se actualizo la zona de la empresa correctamente";
        } catch (Exception e){
            System.out.println(e.getMessage());
            return "Fallo al actualizar la zpna de la empresa";
        }
    }

    public void delete(Integer id_empresa_zona){
        try(Connection con = sql2o.open()) {
            String sql = "DELETE FROM empresa_zona WHERE id_empresa_zona =:id_empresa_zona";
            con.createQuery(sql)
                    .addParameter("id_empresa_zona",id_empresa_zona)
                    .executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}

