package BDA.grupo1.controller;

import BDA.grupo1.model.Zona;
import BDA.grupo1.service.ClienteSesionService;
import BDA.grupo1.service.ZonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ZonaController {

    @Autowired
    private ZonaService zonaService;

    @Autowired
    private ClienteSesionService clienteSesionService;

    @PostMapping("/zona/crear")
    public Zona crear(@RequestBody Zona zona, @RequestParam Integer id_cliente){
        clienteSesionService.crear(id_cliente);
        return zonaService.crear(zona);
    }

    @GetMapping("/zona/All")
    public List<Zona> getAll() {
        return zonaService.findAll();
    }



    @PostMapping("/zona/update/{id_zona}")
    public String update(@RequestBody Zona zona, @PathVariable Integer id_zona, @RequestParam Integer id_cliente) {
        clienteSesionService.crear(id_cliente);
        return zonaService.update(zona, id_zona);
    }

    @DeleteMapping("/zona/delete/{id_zona}")
    public void delete(@PathVariable Integer id_zona, @RequestParam Integer id_cliente) {
        clienteSesionService.crear(id_cliente);
        zonaService.delete(id_zona);
    }

    @GetMapping("/zona/getZonasValidas")
    public List<Zona> getZonasValidas(@RequestParam String tipo) {
        return zonaService.zonas_disponibles(tipo);
    }
}
