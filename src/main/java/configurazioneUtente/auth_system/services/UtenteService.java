package configurazioneUtente.auth_system.services;

import configurazioneUtente.auth_system.dto.request.UtenteRegisterRequest;
import configurazioneUtente.auth_system.dto.response.EntityIdResponse;
import configurazioneUtente.auth_system.entity.Utente;
import configurazioneUtente.auth_system.exceptions.MyEntityNotFoundException;
import configurazioneUtente.auth_system.mappers.UtenteMapper;
import configurazioneUtente.auth_system.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private UtenteMapper utenteMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Metodo per ottenere un utente tramite il suo ID
    public Utente getById(Long id) {
        return utenteRepository.findById(id).orElseThrow(
                () -> new MyEntityNotFoundException("Utente con id: " + id + " non trovato!"));
    }

    //Metodo per ottenere tutti gli utenti
    public List<Utente> getAll() {
        return utenteRepository.findAll();
    }

    //Metodo per creare un utente
    public EntityIdResponse createUtente(UtenteRegisterRequest request) {
        Utente utente = utenteRepository.save(utenteMapper.fromCreateUtenteRequest(request, passwordEncoder.encode(request.password())));
        return new EntityIdResponse(utente.getId());
    }

    //Metodo per inserire un utente
    public void insertUtente(Utente utente) {
        utenteRepository.save(utente);
    }

    //Metodo per aggiornare un utente
    public EntityIdResponse updateUtente(UtenteRegisterRequest request, Long id) {
        Utente utente = getById(id);
        utente.setNome(request.nome());
        utente.setCognome(request.cognome());
        utente.setEmail(request.email());
        utenteRepository.save(utente);
        return new EntityIdResponse(utente.getId());
    }

    //Metodo per ottenere un utente tramite la sua email
    public Utente getByEmail(String email) {
        return utenteRepository.findByEmail(email)
                .orElseThrow(
                () -> new MyEntityNotFoundException("Utente con email: " + email + " non trovato!"));
    }

    //Metodo per eliminare un utente
    public void deleteUtente(Long id) {
        utenteRepository.deleteById(id);
    }

    // Metodo per registrare l'utente
    public Utente registraUtente(Utente utente) {
        // Controlla che la email non sia già registrata
        Optional<Utente> utenteEsistente = utenteRepository.findByEmail(utente.getEmail());
        if (utenteEsistente.isPresent()) {
            throw new RuntimeException("Email già registrata!");
        }
        // Cripta la password
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        return utenteRepository.save(utente);
    }

    // Metodo per autenticare l'utente
    public void autenticaUtente(String email, String password) {
        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new MyEntityNotFoundException("Invalid email or password!"));

        if (!passwordEncoder.matches(password, utente.getPassword())) {
            throw new RuntimeException("Wrong password!");
        }

    }


}
