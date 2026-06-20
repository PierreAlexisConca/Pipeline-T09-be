package pe.edu.vallegrande.mybackend.repository;

import pe.edu.vallegrande.mybackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}