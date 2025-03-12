package configurazioneUtente.auth_system.controllers;

import configurazioneUtente.auth_system.dto.request.UtenteLoginRequest;
import configurazioneUtente.auth_system.dto.request.UtenteRegisterRequest;
import configurazioneUtente.auth_system.dto.response.EntityIdResponse;
import configurazioneUtente.auth_system.dto.response.GenericResponse;
import configurazioneUtente.auth_system.entity.Utente;
import configurazioneUtente.auth_system.services.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mioUtente")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    //Metodo per ottenere un utente tramite il suo ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Utente> getById(@PathVariable Long id) {
       return new ResponseEntity<>(utenteService.getById(id), HttpStatus.OK);
    }

    //Metodo per ottenere tutti gli utenti
    @GetMapping("/get/all")
    public ResponseEntity<List<Utente>> getAll() {
        return new ResponseEntity<>(utenteService.getAll(), HttpStatus.OK);
    }

    //Metodo per creare un utente
    @PostMapping("/create")
    public ResponseEntity<EntityIdResponse> create(@RequestBody UtenteRegisterRequest request) {
        return new ResponseEntity<>(utenteService.createUtente(request), HttpStatus.CREATED);
    }

    //Metodo per aggiornare un utente
    @PostMapping("/update/{id}")
    public ResponseEntity<EntityIdResponse> update(@RequestBody UtenteRegisterRequest request, @PathVariable Long id) {
        return new ResponseEntity<>(utenteService.updateUtente(request, id), HttpStatus.CREATED);
    }

    //Metodo per eliminare un utente
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable Long id) {
        utenteService.deleteUtente(id);
        return new ResponseEntity<>(new GenericResponse("Utente con " + id + " eliminato con successo!"), HttpStatus.OK);
    }

    //Metodo per la registrazione di un utente
    @PostMapping("/register")
    public ResponseEntity<?> registrazione(@Valid @RequestBody Utente request) {
        try {
            Utente response = utenteService.registraUtente(request);
            return ResponseEntity.ok( "Registrazione effettuata!" );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Metodo per il login di un utente
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UtenteLoginRequest request) {
        try {
            utenteService.autenticaUtente(request.email(), request.password());
            return ResponseEntity.ok("Login riuscito!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
