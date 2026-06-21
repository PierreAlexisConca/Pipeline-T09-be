package ap2.PierreAlexisConca.dto.producto;

import lombok.Data;

@Data
public class MovimientoStockRequest {

    private Long productoId;
    private Integer cantidad;
    private String tipoMovimiento; // "ENTRADA" o "SALIDA"
    private String motivo;
}
