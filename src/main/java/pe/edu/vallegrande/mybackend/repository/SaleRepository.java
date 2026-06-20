package pe.edu.vallegrande.mybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.mybackend.model.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

}
