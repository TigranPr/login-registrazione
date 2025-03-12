package configurazioneUtente.auth_system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// Classe per la richiesta di login
public record UtenteLoginRequest(
        @Email(message = "Email non valida")
        String email,
        @NotBlank(message = "La password non può essere vuota")
        String password // Aggiungi il campo password
) {
}
