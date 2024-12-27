package BDA.grupo1.service;

import BDA.grupo1.model.Orden_repartidor;
import BDA.grupo1.repository.Orden_repartidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Orden_repartidorService {
    @Autowired
    private Orden_repartidorRepository orden__repartidorRepository;

    public Orden_repartidor crear(Orden_repartidor ordenZonaRepartidor) {
        return orden__repartidorRepository.crear(ordenZonaRepartidor);
    }

    public List<Orden_repartidor> findAll() {
        return orden__repartidorRepository.getAll();
    }

    public String update(Orden_repartidor ordenZonaRepartidor, Integer id) {
        return orden__repartidorRepository.update(ordenZonaRepartidor, id);
    }

    public void delete(Integer id) {
        orden__repartidorRepository.delete(id);
    }
}
