package BDA.grupo1.controller;

import BDA.grupo1.model.Orden_repartidor;
import BDA.grupo1.service.ClienteSesionService;
import BDA.grupo1.service.Orden_repartidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class Orden_repartidorController {
    @Autowired
    private Orden_repartidorService orden__repartidorService;

    @Autowired
    private ClienteSesionService clienteSesionService;

    @PostMapping("/OrdenZonaRepartidor/crear")
    public Orden_repartidor crearOrden_zona_repartidor(@RequestBody Orden_repartidor ordenZonaRepartidor, @RequestParam Integer id_cliente){
        clienteSesionService.crear(id_cliente);
        return orden__repartidorService.crear(ordenZonaRepartidor);
    }

    @GetMapping("/OrdenZonaRepartidor/All")
    public List<Orden_repartidor> getAllOrden_zona_repartidor(){ return orden__repartidorService.findAll();}

    @PostMapping("/OrdenZonaRepartidor/update/{id}")
    public String updateOrden_zona_repartidor(@RequestBody Orden_repartidor ordenZonaRepartidor, @PathVariable Integer id, @RequestParam Integer id_cliente){
        clienteSesionService.crear(id_cliente);
        return orden__repartidorService.update(ordenZonaRepartidor,id);
    }

    @DeleteMapping("/OrdenZonaRepartidor/delete/{id}")
    public void deleteOrden_zona_repartidor(@PathVariable Integer id, @RequestParam Integer id_cliente){
        clienteSesionService.crear(id_cliente);
        orden__repartidorService.delete(id);
    }
}
