package com.example.gestioncontacts.repository;

import com.example.gestioncontacts.model.Contact;
import com.example.gestioncontacts.model.ContactEntreprise;
import com.example.gestioncontacts.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IContactEntrepriseRepository extends JpaRepository<ContactEntreprise, Long> {

    List<ContactEntreprise> findAllByContact(Contact contact);

    List<ContactEntreprise> findAllByContactId(Long id);

    List<ContactEntreprise> findAllByEntreprise(Entreprise entreprise);

    ContactEntreprise findFirstByEntrepriseAndContact(Entreprise entreprise, Contact contact);

    @Query("SELECT coalesce(max(id), 0) FROM ContactEntreprise")
    public Long getMaxId();
}
