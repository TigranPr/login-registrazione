package configurazioneUtente.auth_system.dto.response;

import lombok.Builder;

@Builder
public record UtenteResponse(
        Long id,
        String nome,
        String cognome,
        String email
) {
}
