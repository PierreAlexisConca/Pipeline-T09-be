package pe.edu.vallegrande.mybackend.service;

import pe.edu.vallegrande.mybackend.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    Product update(Product product);

}

