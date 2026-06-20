package pe.edu.vallegrande.mybackend.model;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Data                      // ✅ genera los getters y setters
@Table(name = "customer")  // ✅ nombre de la tabla en la base de datos
public class Customer {

    @Id
    @Column(name = "id")          // 🗄️ nombre del campo en la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ✅ para que el id se genere automaticamente
    private Long id;              // ☕️ nombre del atributo en la clase java

    @Column(name = "dni")         // 🗄️ nombre del campo en la base de datos
    private String dni;           // ☕️ nombre del atributo en la clase java

    @Column(name = "cellphone")   // 🗄️ nombre del campo en la base de datos
    private String cellPhone;     // ☕️ nombre del atributo en la clase java

    @Column(name = "first_name")  // 🗄️ nombre del campo en la base de datos
    private String firstName;     // ☕️ nombre del atributo en la clase java

    @Column(name = "last_name")   // 🗄️ nombre del campo en la base de datos
    private String lastName;      // ☕️ nombre del atributo en la clase java

    @Column(name = "state")       // 🗄️ nombre del campo en la base de datos
    private String state;         // ☕️ nombre del atributo en la clase java

    //CAMPOS DE AUDITORIA

    @Column(name = "created_at")       // 🗄️ nombre del campo en la base de datos
    private LocalDateTime createdAt;   // ☕️ nombre del atributo en la clase java

    @Column(name = "updated_at")       // 🗄️ nombre del campo en la base de datos
    private LocalDateTime updatedAt;   // ☕️ nombre del atributo en la clase java

    @Column(name = "deleted_at")       // 🗄️ nombre del campo en la base de datos
    private LocalDateTime deletedAt;   // ☕️ nombre del atributo en la clase java

    @Column(name = "restored_at")       // 🗄️ nombre del campo en la base de datos
    private LocalDateTime restoredAt;   // ☕️ nombre del atributo en la clase java

}