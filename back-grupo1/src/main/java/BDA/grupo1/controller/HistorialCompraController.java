package BDA.grupo1.controller;

import BDA.grupo1.model.HistorialCompra;
import BDA.grupo1.model.Producto;
import BDA.grupo1.service.HistorialCompraService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/historialCompras")
public class HistorialCompraController {
    @Autowired
    private HistorialCompraService historialCompraService;

    @PostMapping("/{idUsuario}")
    public HistorialCompra agregarCompra(@PathVariable int idUsuario, @RequestBody HistorialCompra.Compra compra) {
        return historialCompraService.guardarHistorialCompra(idUsuario,compra);
    }

    @GetMapping("/historial/{idUsuario}")
    public HistorialCompra obtenerHistorial(@PathVariable int idUsuario) {
        return historialCompraService.buscarHistorialCompra(idUsuario);
    }

    @PostMapping("/updateCompra/{idUsuario}/{idOrden}/{estado}")
    public HistorialCompra updateCompra(@PathVariable int idUsuario, @PathVariable int idOrden, @PathVariable String estado) {
        return historialCompraService.actualizarEstadoCompra(idUsuario, idOrden, estado);
    }

    @GetMapping("/historial/recomendaciones/{idUsuario}")
    public List<Producto> obtenerRecomendaciones(@PathVariable Integer idUsuario) {
        List<Producto> recomendaciones = historialCompraService.obtenerCategoriasMasFrecuentes(idUsuario);
        return recomendaciones;
    }
}
