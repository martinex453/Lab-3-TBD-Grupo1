package BDA.grupo1.controller;

import BDA.grupo1.model.Empresa_zona;
import BDA.grupo1.service.ClienteSesionService;
import BDA.grupo1.service.EmpresaService;
import BDA.grupo1.service.Empresa_zonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class Empresa_zonaController {
    @Autowired
    private Empresa_zonaService empresaZonaService;

    @Autowired
    private ClienteSesionService clienteSesionService;

    @PostMapping("/empresaZona/crear")
    public Empresa_zona crearEmpresa_Zona(@RequestBody Empresa_zona empresaZona, Integer id){
        clienteSesionService.crear(id);
        return empresaZonaService.crear(empresaZona);
    }

    @GetMapping("/empresaZona/All")
    public List<Empresa_zona> getAllEmpresaZona(){return empresaZonaService.findAll();}

    @PostMapping("/empresaZona/update/{id}")
    public String updateEmpresaZona(@RequestBody Empresa_zona empresaZona, @PathVariable Integer id, @RequestParam Integer id_cliente){
        clienteSesionService.crear(id_cliente);
        return empresaZonaService.update(empresaZona,id);
    }

    @DeleteMapping("/empresaZona/delete/{id}")
    public void deleteEmpresaZona(@PathVariable Integer id, @RequestParam Integer id_cliente){
        clienteSesionService.crear(id_cliente);
        empresaZonaService.delete(id);
    }
}
