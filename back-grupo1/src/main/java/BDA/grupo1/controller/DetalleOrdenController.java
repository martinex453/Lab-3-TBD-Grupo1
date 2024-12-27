package BDA.grupo1.controller;

import BDA.grupo1.model.DetalleOrden;
import BDA.grupo1.service.ClienteSesionService;
import BDA.grupo1.service.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@CrossOrigin
public class DetalleOrdenController {

    @Autowired
    private DetalleOrdenService detalleOrdenService;

    @Autowired
    private ClienteSesionService clienteSesionService;

    @PostMapping("/detalleOrden/crear")
    public DetalleOrden crearDetalleOrden(@RequestBody DetalleOrden detalleOrden, @RequestParam Integer id_cliente) {
        clienteSesionService.crear(id_cliente); // Registra la sesión del cliente para que sea usado por el trigger de registro de querys
        return detalleOrdenService.crear(detalleOrden);
    }

    @GetMapping("/detalleOrden/All")
    public List<DetalleOrden> getAllDetalleOrden() {
        return detalleOrdenService.findAll();
    }

    @GetMapping("/detalleOrden/getbyOrdenid/{id}")
    public List<DetalleOrden> getdetalleOrdenByOrdenId(@PathVariable Integer id){
        return detalleOrdenService.getdetalleOrdenByOrdenId(id);
    }

    @PostMapping("/detalleOrden/update/{id}")
    public String updateDetalleOrden(@RequestBody DetalleOrden detalleOrden, @PathVariable Integer id, @RequestParam Integer id_cliente) {
        clienteSesionService.crear(id_cliente); // Registra la sesión del cliente para que sea usado por el trigger de registro de querys
        return detalleOrdenService.update(detalleOrden, id);
    }

    @DeleteMapping("/detalleOrden/delete/{id}")
    public void deleteDetalleOrden(@PathVariable Integer id, @RequestParam Integer id_cliente) {
        clienteSesionService.crear(id_cliente); // Registra la sesión del cliente para que sea usado por el trigger de registro de querys
        detalleOrdenService.delete(id);
    }


}
