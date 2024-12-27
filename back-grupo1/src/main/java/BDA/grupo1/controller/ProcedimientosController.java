package BDA.grupo1.controller;

import BDA.grupo1.model.DetalleOrden;
import BDA.grupo1.service.ClienteSesionService;
import BDA.grupo1.service.ProcedimientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class ProcedimientosController {

    @Autowired
    private ProcedimientosService procedimientoService;
    @Autowired
    private ClienteSesionService clienteSesionService;

    @GetMapping("/top_usuarios")
    public ResponseEntity<String> obtenerReporteTopUsuarios() {
        try {
            String reporte = procedimientoService.generarReporteAgrupado(); // llamar al servicio para ejecutar el procedimiento
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar el reporte: " + e.getMessage());
        }
    }

    @GetMapping("/aplicar_descuento")
    public String aplicarDescuento(@RequestParam int idCategoria, @RequestParam float descuento) {
        try {
            procedimientoService.aplicarDescuentoACategoria(idCategoria, descuento); // llamar al servicio para ejecutar el procedimiento
            return "Descuento aplicado correctamente.";
        } catch (Exception e) {
            return "Error al aplicar el descuento: " + e.getMessage();
        }
    }

    @PostMapping("/crearOrdenCompra/{id_cliente}")
    public String crearOrden(
            @PathVariable int id_cliente,
            @RequestBody List<DetalleOrden> detalles,
            @RequestParam Double latitud,
            @RequestParam Double longitud) {
        try{
           clienteSesionService.crear(id_cliente); // Registra la sesión del cliente para que sea usado por el trigger de registro de querys
           procedimientoService.crearOrdenCompra(id_cliente, detalles, latitud, longitud); // llamar al servicio para ejecutar el procedimiento
           return "Orden de compra realizada con exito.";
       } catch (Exception e) {
           return "Error al crear la orden de compra: " + e.getMessage();
       }
    }
}