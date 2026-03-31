package org.example.levelup.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "El nombre completo es obligatorio")
        @Size(max = 120, message = "El nombre completo no puede superar 120 caracteres")
        String fullName,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene un formato valido")
        @Size(max = 120, message = "El correo no puede superar 120 caracteres")
        String email,

        @NotBlank(message = "El tipo de documento es obligatorio")
        @Size(max = 20, message = "El tipo de documento no puede superar 20 caracteres")
        String documentType,

        @NotBlank(message = "El numero de documento es obligatorio")
        @Size(max = 30, message = "El numero de documento no puede superar 30 caracteres")
        String documentNumber
) {
}
