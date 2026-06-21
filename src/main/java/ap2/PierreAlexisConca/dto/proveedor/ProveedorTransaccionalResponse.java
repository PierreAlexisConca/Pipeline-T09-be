package ap2.PierreAlexisConca.dto.proveedor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProveedorTransaccionalResponse {
    private Long proveedorId;
    private String ruc;
    private String companyName;
    private String contactName;
    private String address;
    private String email;
    private String state;
    private Long contactoId;
    private String contactoNombre;
    private String contactoTelefono;
}