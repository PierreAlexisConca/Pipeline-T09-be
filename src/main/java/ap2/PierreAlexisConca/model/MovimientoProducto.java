package ap2.PierreAlexisConca.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "movimiento_producto")
public class MovimientoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "tipo_movimiento", nullable = false)
    private String tipoMovimiento; // "ENTRADA" o "SALIDA"

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;
}
