package ap2.PierreAlexisConca.repository;

import ap2.PierreAlexisConca.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {
    Optional<Contacto> findFirstByEmail(String email);

    boolean existsByEmail(String email);
}
