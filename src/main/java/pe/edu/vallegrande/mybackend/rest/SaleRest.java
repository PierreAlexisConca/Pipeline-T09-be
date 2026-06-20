package pe.edu.vallegrande.mybackend.rest;

import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.mybackend.dto.SaleRequest;
import pe.edu.vallegrande.mybackend.dto.SaleResponse;
import pe.edu.vallegrande.mybackend.service.SaleService;
import java.util.List;

@CrossOrigin(origins = "*")          // ✅ Permitir Conexión con Angular
@RestController
@RequestMapping("/v1/api/sale")
public class SaleRest {

    private final SaleService saleService;

    public SaleRest(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public List<SaleResponse> findAll() {
        return saleService.findAll();
    }

    @PostMapping("/save")
    public SaleResponse save(@RequestBody SaleRequest saleRequest) {
        return saleService.save(saleRequest);
    }

}
