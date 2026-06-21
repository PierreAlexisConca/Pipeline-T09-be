package ap2.PierreAlexisConca.service.impl;

import ap2.PierreAlexisConca.dto.proveedor.ProveedorTransaccionalRequest;
import ap2.PierreAlexisConca.dto.proveedor.ProveedorTransaccionalResponse;
import ap2.PierreAlexisConca.model.Contacto;
import ap2.PierreAlexisConca.model.Supplier;
import ap2.PierreAlexisConca.repository.ContactoRepository;
import ap2.PierreAlexisConca.repository.SupplierRepository;
import ap2.PierreAlexisConca.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ContactoRepository contactoRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository,
                               ContactoRepository contactoRepository) {
        this.supplierRepository = supplierRepository;
        this.contactoRepository = contactoRepository;
    }

    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public List<Supplier> findByState(String state) {
        return supplierRepository.findByState(state);
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return supplierRepository.findById(Objects.requireNonNull(id, "id no puede ser null"));
    }

    @Override
    public Supplier save(Supplier supplier) {
        supplier.setState("A");
        supplier.setCreatedAt(LocalDateTime.now());
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier update(Supplier supplier) {
        Long supplierId = Objects.requireNonNull(supplier.getId(), "supplier.id no puede ser null");
        Supplier existing = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplier.setCreatedAt(existing.getCreatedAt());
        supplier.setUpdatedAt(LocalDateTime.now());
        supplier.setState("A");

        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier delete(Long id) {
        Supplier supplier = supplierRepository.findById(Objects.requireNonNull(id, "id no puede ser null"))
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplier.setState("I");
        supplier.setDeletedAt(LocalDateTime.now());

        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier restore(Long id) {
        Supplier supplier = supplierRepository.findById(Objects.requireNonNull(id, "id no puede ser null"))
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplier.setState("A");
        supplier.setRestoredAt(LocalDateTime.now());

        return supplierRepository.save(supplier);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorTransaccionalResponse> findAllTransaccional() {
        return supplierRepository.findAll().stream()
                .map(this::toTransaccionalResponse)
                .toList();
    }

    @Override
    @Transactional
    public ProveedorTransaccionalResponse saveTransaccional(ProveedorTransaccionalRequest request) {
        Objects.requireNonNull(request, "request no puede ser null");

        if (supplierRepository.existsByRuc(request.getRuc())) {
            throw new RuntimeException("Ya existe un proveedor con ese RUC");
        }
        if (contactoRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe un contacto con ese email");
        }

        Supplier supplier = new Supplier();
        supplier.setRuc(request.getRuc());
        supplier.setCellPhone(request.getCellphone());
        supplier.setCompanyName(request.getCompanyName());
        supplier.setContactName(request.getContactName());
        supplier.setAddress(request.getAddress());
        supplier.setEmail(request.getEmail());
        supplier.setState("A");
        supplier.setCreatedAt(LocalDateTime.now());

        Supplier savedSupplier = supplierRepository.save(supplier);

        Contacto contacto = new Contacto();
        contacto.setNombre(request.getContactoNombre());
        contacto.setTelefono(request.getContactoTelefono());
        contacto.setEmail(request.getEmail());

        Contacto savedContacto = contactoRepository.save(contacto);

        return ProveedorTransaccionalResponse.builder()
                .proveedorId(savedSupplier.getId())
                .ruc(savedSupplier.getRuc())
                .companyName(savedSupplier.getCompanyName())
                .contactName(savedSupplier.getContactName())
                .address(savedSupplier.getAddress())
                .email(savedSupplier.getEmail())
                .state(savedSupplier.getState())
                .contactoId(savedContacto.getId())
                .contactoNombre(savedContacto.getNombre())
                .contactoTelefono(savedContacto.getTelefono())
                .build();
    }

    private ProveedorTransaccionalResponse toTransaccionalResponse(Supplier supplier) {
        Optional<Contacto> contacto = supplier.getEmail() == null
                ? Optional.empty()
                : contactoRepository.findFirstByEmail(supplier.getEmail());

        return ProveedorTransaccionalResponse.builder()
                .proveedorId(supplier.getId())
                .ruc(supplier.getRuc())
                .companyName(supplier.getCompanyName())
                .contactName(supplier.getContactName())
                .address(supplier.getAddress())
                .email(supplier.getEmail())
                .state(supplier.getState())
                .contactoId(contacto.map(Contacto::getId).orElse(null))
                .contactoNombre(contacto.map(Contacto::getNombre).orElse(null))
                .contactoTelefono(contacto.map(Contacto::getTelefono).orElse(null))
                .build();
    }
}