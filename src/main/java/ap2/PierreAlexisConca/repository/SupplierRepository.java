package ap2.PierreAlexisConca.repository;

import ap2.PierreAlexisConca.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findByState(String state);

    Optional<Supplier> findByEmail(String email);

    boolean existsByRuc(String ruc);
}