package pe.edu.vallegrande.mybackend.repository;

import pe.edu.vallegrande.mybackend.model.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // ⚙️🔍 Definir método Listar por Estado
    List<Customer> findByState(String state);
    
}
