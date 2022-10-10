package com.example.gestioncontacts.service.impl;

import com.example.gestioncontacts.model.Contact;
import com.example.gestioncontacts.model.ContactEntreprise;
import com.example.gestioncontacts.model.Entreprise;
import com.example.gestioncontacts.repository.IContactEntrepriseRepository;
import com.example.gestioncontacts.repository.IContactRepository;
import com.example.gestioncontacts.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class ContactService implements IContactService {

    @Autowired
    IContactRepository contactRepository;

    @Autowired
    IContactEntrepriseRepository contactEntrepriseRepository;

    @Override
    public List<Contact> listAll() {
        List<Contact> contacts = contactRepository.findAll();
        for (Contact contact : contacts) {
            contact = ajouterEntreprisesContact(contact);
        }
        return contacts;
    }

    @Override
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public void update(Contact contact) {
        contactRepository.updateContactById(contact.getNom(), contact.getPrenom(), contact.getNumeroTva(), contact.getId());
    }

    @Override
    public Contact get(Long id) {
        Contact contact = contactRepository.findById(id).orElse(null);
        if (contact != null) {
            contact = ajouterEntreprisesContact(contact);
            return contact;
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        List<ContactEntreprise> contactEntreprises = contactEntrepriseRepository.findAllByContactId(id);
        for (ContactEntreprise contactEntreprise : contactEntreprises) {
            contactEntrepriseRepository.deleteById(contactEntreprise.getId());
        }
        contactRepository.deleteById(id);
    }

    public Contact ajouterEntreprisesContact(Contact contact) {
        List<Entreprise> entreprises = new ArrayList<>();
        List<ContactEntreprise> contactEntreprises = contactEntrepriseRepository.findAllByContact(contact);
        for (ContactEntreprise contactEntreprise : contactEntreprises) {
            entreprises.add(contactEntreprise.getEntreprise());
        }
        contact.setEntreprises(entreprises);
        return contact;
    }
}
