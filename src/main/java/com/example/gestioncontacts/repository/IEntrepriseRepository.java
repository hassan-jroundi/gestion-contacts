package com.example.gestioncontacts.repository;

import com.example.gestioncontacts.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEntrepriseRepository extends JpaRepository<Entreprise, Long> {

    @Query(value = "SELECT e FROM Entreprise e where e.numeroTva = ?1")
    Entreprise getByNumeroTvaIgnoreCase(String numeroTva);

    @Modifying
    @Query("UPDATE Entreprise e set e.adresse = ?1, e.numeroTva = ?2 where e.id = ?3")
    void updateEntrepriseById(String adresse, String numeroTva, Long id);
}
