package configurazioneUtente.auth_system.mappers;

import configurazioneUtente.auth_system.dto.request.UtenteRegisterRequest;
import configurazioneUtente.auth_system.entity.Utente;
import org.springframework.stereotype.Service;

@Service
public class UtenteMapper {

        // Metodo per creare un utente da una richiesta di registrazione
        public Utente fromCreateUtenteRequest(UtenteRegisterRequest request, String passwordCriptata) {
        return Utente
                .builder()
                .nome(request.nome())
                .cognome(request.cognome())
                .email(request.email())
                .password(passwordCriptata) // Usando la password criptata passata
                .build();
    }
}
