package BDA.grupo1.repository;

import BDA.grupo1.model.Orden_repartidor;

import java.util.List;

public interface Orden_repartidorRepository {
    public Orden_repartidor crear(Orden_repartidor ordenZonaRepartidor);
    public List<Orden_repartidor> getAll();
    public String update(Orden_repartidor ordenZonaRepartidor, Integer id);
    public void delete(Integer id);
}
