package BDA.grupo1.service;

import BDA.grupo1.model.Empresa;
import BDA.grupo1.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    public Empresa crear(Empresa empresa){return empresaRepository.crear(empresa);}
    public List<Empresa> findAll(){return empresaRepository.getAll();}

    public String update(Empresa empresa, Integer id){return empresaRepository.update(empresa,id);}

    public void delete(Integer id){empresaRepository.delete(id);}

    public Double areaZona(Long id_empresa) { return empresaRepository.areaZona(id_empresa); }
}
