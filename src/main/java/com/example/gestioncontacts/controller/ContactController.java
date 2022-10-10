package com.example.gestioncontacts.controller;

import com.example.gestioncontacts.enums.TypeContact;
import com.example.gestioncontacts.model.Contact;
import com.example.gestioncontacts.service.IContactService;
import io.swagger.v3.oas.annotations.Operation;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    IContactService contactService;

    @Operation(summary = "Retrouver un contact avec son ID")
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity get(@PathVariable("id") String id) throws ObjectNotFoundException {

        Long idL = getIdLong(id);
        Contact entity = contactService.get(idL);
        if (entity != null) {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun contact avec l'identifiant " + id + " n'a pas été trouvé");
        }
    }

    @Operation(summary = "Lister touts les contacts")
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Contact>> getAll() {
        return new ResponseEntity<>(contactService.listAll(), HttpStatus.OK);
    }

    @Operation(summary = "Ajouter un contact")
    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity save(@RequestBody Contact contact) {
        if (contact.getNom() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Merci de renseigner le nom du contact");
        } else if (contact.getPrenom() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Merci de renseigner le prénom du contact");
        } else if (contact.getAdresse() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Merci de renseigner l'adresse du contact");
        } else if (contact.getTypeContact().equals(TypeContact.F) && (contact.getNumeroTva() == null || contact.getNumeroTva().length() == 0)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Un contact de type Freelance doit avoir un numéro de TVA renseigné");
        } else {
            Contact idExistant = contactService.get(contact.getId());
            if (idExistant != null) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Un contact existe déjà avec cet identifiant");
            } else {
                contact = contactService.save(contact);
                return new ResponseEntity(contact, HttpStatus.CREATED);
            }
        }
    }

    @Operation(summary = "Mettre à jour un contact")
    @PutMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity update(@PathVariable("id") String id, @RequestBody Contact contact) {
        Contact entity = contactService.get(new Long(id));
        if (entity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun contact avec l'identifiant " + id + " n'a pas été trouvé");
        } else {
            if (contact.getTypeContact().equals(TypeContact.F) && (contact.getNumeroTva() == null || contact.getNumeroTva().length() == 0)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Un contact de type Freelance doit avoir un numéro de TVA renseigné");
            } else {
                entity.setNom(contact.getNom());
                entity.setPrenom(contact.getPrenom());
                entity.setAdresse(contact.getAdresse());
                entity.setTypeContact(contact.getTypeContact());
                entity.setNumeroTva(contact.getNumeroTva() != null ? contact.getNumeroTva() : null);
                contactService.update(entity);
                return ResponseEntity.status(HttpStatus.OK).body("Mise à jour effectuée");
            }
        }
    }

    @Operation(summary = "Supprimer un contact")
    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") String id) {

        Long idL = getIdLong(id);
        contactService.delete(idL);
        return ResponseEntity.status(HttpStatus.OK).body("Suppression effectuée");
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
