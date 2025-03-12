package configurazioneUtente.auth_system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// Classe per la richiesta di registrazione
public record UtenteRegisterRequest(
        @NotBlank(message = "Il nome non può essere blank o null")
        String nome,
        @NotBlank(message = "Il cognome non può essere blank o null")
        String cognome,
        @Email(message = "Email non valida")
        String email,
        @NotBlank(message = "La password non può essere vuota")
        String password // Aggiungi il campo password
) {
}
