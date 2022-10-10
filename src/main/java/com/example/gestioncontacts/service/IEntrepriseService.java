package com.example.gestioncontacts.service;

import com.example.gestioncontacts.model.Contact;
import com.example.gestioncontacts.model.Entreprise;

import java.util.List;

public interface IEntrepriseService {

    List<Entreprise> listAll();

    Entreprise save(Entreprise entreprise);

    void update(Entreprise entreprise);

    Entreprise get(Long id);

    Entreprise findEntrepriseByNumeroTva(String numeroTva);
}
