package com.example.gestioncontacts.service;

import com.example.gestioncontacts.model.Contact;

import java.util.List;

public interface IContactService {

    List<Contact> listAll();

    Contact save(Contact contact);

    void update(Contact contact);

    Contact get(Long id);

    void delete(Long id);
}
