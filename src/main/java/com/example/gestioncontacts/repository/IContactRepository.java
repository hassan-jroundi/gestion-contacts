package com.example.gestioncontacts.repository;

import com.example.gestioncontacts.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IContactRepository extends JpaRepository<Contact, Long> {

    @Modifying
    @Query("UPDATE Contact c set c.nom = ?1, c.prenom = ?2, c.numeroTva = ?3 where c.id = ?4")
    void updateContactById(String nom, String prenom, String numeroTva, Long id);
}
