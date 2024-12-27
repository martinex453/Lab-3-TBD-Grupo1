package BDA.grupo1.service;

import BDA.grupo1.model.Zona;
import BDA.grupo1.repository.RepartidorRepository;
import BDA.grupo1.model.Repartidor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepartidorService {

    @Autowired
    private RepartidorRepository repartidorRepository;

    public Repartidor crear(Repartidor repartidor){return repartidorRepository.crear(repartidor);}

    public List<Repartidor> getAll(){return repartidorRepository.getAll();}

    public String update(Repartidor repartidor, Integer id){ return repartidorRepository.update(repartidor,id);}

    public void delete(Integer id) {repartidorRepository.delete(id);}

    public List<Repartidor> getRepartidorByZona(Long id){return repartidorRepository.getRepartidorByZone(id);}

    public Integer getRandom(){return repartidorRepository.getRandom();}
}
