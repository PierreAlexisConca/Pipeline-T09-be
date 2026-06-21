package ap2.PierreAlexisConca.repository;

import ap2.PierreAlexisConca.model.MovimientoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoProductoRepository
        extends JpaRepository<MovimientoProducto, Long> {
}