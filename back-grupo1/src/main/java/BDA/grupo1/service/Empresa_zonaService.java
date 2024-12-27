package BDA.grupo1.service;

import BDA.grupo1.model.Empresa_zona;
import BDA.grupo1.repository.Empresa_zonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Empresa_zonaService {
    @Autowired
    private Empresa_zonaRepository empresaZonaRepository;

    public Empresa_zona crear(Empresa_zona empresaZona){return empresaZonaRepository.crear(empresaZona);}

    public List<Empresa_zona> findAll(){return empresaZonaRepository.getAll();}

    public String update(Empresa_zona empresaZona, Integer id){return empresaZonaRepository.update(empresaZona,id);}

    public void delete(Integer id){empresaZonaRepository.delete(id);}
}
