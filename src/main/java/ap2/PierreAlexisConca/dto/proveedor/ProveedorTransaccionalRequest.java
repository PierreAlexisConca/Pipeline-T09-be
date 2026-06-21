package ap2.PierreAlexisConca.dto.proveedor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProveedorTransaccionalRequest {

    @NotBlank(message = "El RUC es obligatorio")
    @Pattern(regexp = "^[0-9]{11}$", message = "El RUC debe tener exactamente 11 digitos numericos")
    private String ruc;

    @NotBlank(message = "El telefono del proveedor es obligatorio")
    private String cellphone;

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    private String companyName;

    @NotBlank(message = "El nombre del contacto del proveedor es obligatorio")
    private String contactName;

    private String address;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato valido")
    private String email;

    @NotBlank(message = "El nombre del contacto es obligatorio")
    private String contactoNombre;

    @NotBlank(message = "El telefono del contacto es obligatorio")
    private String contactoTelefono;
}