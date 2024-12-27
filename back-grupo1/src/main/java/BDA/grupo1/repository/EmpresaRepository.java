package BDA.grupo1.repository;


import BDA.grupo1.model.Empresa;

import java.util.List;

public interface EmpresaRepository {
    public Empresa crear(Empresa empresa);
    public List<Empresa> getAll();
    public String update(Empresa empresa, Integer id);
    public void delete(Integer id);
    public Double areaZona(Long id_empresa);
}
