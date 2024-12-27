package BDA.grupo1.repository;

import BDA.grupo1.model.Zona;
import java.util.List;

public interface ZonaRepository {
    public Zona crear(Zona zona);
    public List<Zona> getAll();
    public String update(Zona zona, Integer id);
    public void delete(Integer id);
    public List<Zona> zonas_disponibles(String tipo);
}
