package BDA.grupo1.repository;

import BDA.grupo1.model.Empresa;
import BDA.grupo1.model.Empresa_zona;

import java.util.List;

public interface Empresa_zonaRepository {
    public Empresa_zona crear(Empresa_zona empresaZona);
    public List<Empresa_zona> getAll();
    public String update(Empresa_zona empresaZona, Integer id);
    public void delete(Integer id);
}
