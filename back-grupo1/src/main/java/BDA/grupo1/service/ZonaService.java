package BDA.grupo1.service;

import BDA.grupo1.model.Zona;
import BDA.grupo1.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZonaService {
    @Autowired
    private ZonaRepository zonaRepository;

    public Zona crear(Zona zona) {
        return zonaRepository.crear(zona);
    }

    public List<Zona> findAll() {
        return zonaRepository.getAll();
    }

    public String update(Zona zona, Integer id_zona) {
        return zonaRepository.update(zona, id_zona);
    }

    public void delete(Integer id_zona) {
        zonaRepository.delete(id_zona);
    }

    public List<Zona> zonas_disponibles(String tipo) {return zonaRepository.zonas_disponibles(tipo);}
}
