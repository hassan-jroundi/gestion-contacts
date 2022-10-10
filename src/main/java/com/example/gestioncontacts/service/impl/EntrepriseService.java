package com.example.gestioncontacts.service.impl;

import com.example.gestioncontacts.model.Contact;
import com.example.gestioncontacts.model.ContactEntreprise;
import com.example.gestioncontacts.model.Entreprise;
import com.example.gestioncontacts.repository.IContactEntrepriseRepository;
import com.example.gestioncontacts.repository.IEntrepriseRepository;
import com.example.gestioncontacts.service.IEntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EntrepriseService implements IEntrepriseService {

    @Autowired
    IEntrepriseRepository entrepriseRepository;

    @Autowired
    IContactEntrepriseRepository contactEntrepriseRepository;

    @Override
    public List<Entreprise> listAll() {
        List<Entreprise> entreprises = entrepriseRepository.findAll();
        for (Entreprise entreprise : entreprises) {
            entreprise = ajouterContactsEntreprise(entreprise);
        }
        return entreprises;
    }

    @Override
    public Entreprise save(Entreprise entreprise) {
        return entrepriseRepository.save(entreprise);
    }

    @Override
    public void update(Entreprise entreprise) {
        entrepriseRepository.updateEntrepriseById(entreprise.getAdresse(), entreprise.getNumeroTva(), entreprise.getId());
    }

    @Override
    public Entreprise get(Long id) {
        Entreprise entreprise = entrepriseRepository.findById(id).orElse(null);
        if (entreprise != null) {
            entreprise = ajouterContactsEntreprise(entreprise);
            return entreprise;
        } else {
            return null;
        }
    }

    @Override
    public Entreprise findEntrepriseByNumeroTva(String numeroTva) {
        return entrepriseRepository.getByNumeroTvaIgnoreCase(numeroTva);
    }

    public Entreprise ajouterContactsEntreprise(Entreprise entreprise) {
        List<Contact> contacts = new ArrayList<>();
        List<ContactEntreprise> contactEntreprises = contactEntrepriseRepository.findAllByEntreprise(entreprise);
        for (ContactEntreprise contactEntreprise : contactEntreprises) {
            contacts.add(contactEntreprise.getContact());
        }
        entreprise.setContacts(contacts);
        return entreprise;
    }
}
