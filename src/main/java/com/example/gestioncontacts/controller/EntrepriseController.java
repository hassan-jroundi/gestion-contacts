package com.example.gestioncontacts.controller;

import com.example.gestioncontacts.model.Contact;
import com.example.gestioncontacts.model.ContactEntreprise;
import com.example.gestioncontacts.model.Entreprise;
import com.example.gestioncontacts.repository.IContactEntrepriseRepository;
import com.example.gestioncontacts.service.IContactService;
import com.example.gestioncontacts.service.IEntrepriseService;
import io.swagger.v3.oas.annotations.Operation;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entreprises")
public class EntrepriseController {

    @Autowired
    IEntrepriseService entrepriseService;

    @Autowired
    IContactService contactService;

    @Autowired
    IContactEntrepriseRepository contactEntrepriseRepository;

    @Operation(summary = "Retrouver une entreprise avec son ID")
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity get(@PathVariable("id") String id) throws ObjectNotFoundException {

        Long idL = getIdLong(id);
        Entreprise entity = entrepriseService.get(idL);
        if (entity != null) {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune entreprise avec l'identifiant " + id + " n'a pas été trouvée");
        }
    }

    @Operation(summary = "Retrouver une entreprise avec son numéro de TVA")
    @GetMapping(value = "/numero-tva/{numeroTva}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getByNumeroTva(@PathVariable("numeroTva") String numeroTva) throws ObjectNotFoundException {

        Entreprise entity = entrepriseService.findEntrepriseByNumeroTva(numeroTva);
        if (entity != null) {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune entreprise avec le numéro de TVA " + numeroTva + " n'a été trouvée");
        }
    }

    @Operation(summary = "Lister toutes les entreprises")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Entreprise>> getAll() {
        return new ResponseEntity<>(entrepriseService.listAll(), HttpStatus.OK);
    }

    @Operation(summary = "Ajouter une entreprise")
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity save(@RequestBody Entreprise entreprise) {
        if (entreprise.getAdresse() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Merci de renseigner l'adresse de l'entreprise");
        } else if (entreprise.getNumeroTva() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Merci de renseigner le numéro de TVA de l'entreprise");
        } else {
            Entreprise numeroTvaexistant = entrepriseService.findEntrepriseByNumeroTva(entreprise.getNumeroTva());
            Entreprise idExistant = entrepriseService.get(entreprise.getId());
            if (numeroTvaexistant != null) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Une entreprise existe déjà avec ce numéro de TVA");
            } else if (idExistant != null) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Une entreprise existe déjà avec cet identifiant");
            } else {
                entreprise = entrepriseService.save(entreprise);
                return new ResponseEntity(entreprise, HttpStatus.CREATED);
            }
        }
    }

    @Operation(summary = "Mettre à jour une entreprise")
    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity update(@PathVariable("id") String id, @RequestBody Entreprise entreprise) {
        Entreprise entity = entrepriseService.get(new Long(id));
        if (entity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune entreprise avec l'identifiant " + id + " n'a pas été trouvée");
        } else {
            entity.setAdresse(entreprise.getAdresse());
            entity.setNumeroTva(entreprise.getNumeroTva());
            entrepriseService.update(entity);
            return ResponseEntity.status(HttpStatus.OK).body("Mise à jour effectuée");
        }
    }

    @Operation(summary = "Ajouter un contact à une entreprise")
    @PostMapping(value = "/add-contact/{idContact}/entreprise/{idEntreprise}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity addContactToEntreprise(@PathVariable("idContact") String idContact, @PathVariable("idEntreprise") String idEntreprise) {
        Entreprise entreprise = entrepriseService.get(getIdLong(idEntreprise));
        Contact contact = contactService.get(getIdLong(idContact));
        Long maxId = contactEntrepriseRepository.getMaxId();
        ContactEntreprise existant = contactEntrepriseRepository.findFirstByEntrepriseAndContact(entreprise, contact);
        if (contact == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le contact avec l'identifiant " + idContact + " n'a pas été trouvé");
        } else if (entreprise == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'entreprise avec l'identifiant " + idEntreprise + " n'a pas été trouvée");
        } else if (existant != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le contact avec l'identifiant " + idContact + " est déjà associé à l'entreprise avec l'identifiant " + idEntreprise);
        } else {
            contactEntrepriseRepository.save(new ContactEntreprise(maxId + 1, contact, entreprise));
            return ResponseEntity.status(HttpStatus.OK).body("Mise à jour effectuée");
        }
    }

    public static Long getIdLong(String id) {
        Long idL = null;
        try {
            idL = Long.valueOf(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Identifiant non valide.");
        }
        return idL;
    }
}
