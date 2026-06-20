package pe.edu.vallegrande.mybackend.service;

import java.util.List;

import pe.edu.vallegrande.mybackend.dto.SaleRequest;
import pe.edu.vallegrande.mybackend.dto.SaleResponse;

public interface SaleService {

    SaleResponse save(SaleRequest saleRequest);

    List<SaleResponse> findAll();

}
