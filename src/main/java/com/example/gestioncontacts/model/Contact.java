package com.example.gestioncontacts.model;

import com.example.gestioncontacts.enums.TypeContact;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contacts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Contact {

    @Id
    @Column(name = "id")
    Long id;

    @Column(name = "nom")
    String nom;

    @Column(name = "prenom")
    String prenom;

    @Column(name = "adresse")
    String adresse;

    @Column(name = "numero_tva")
    String numeroTva;

    @Enumerated(value=EnumType.STRING)
    @Column(name="type_contact")
    TypeContact typeContact;

    @Transient
    List<Entreprise> entreprises;

    @Transient
    @JsonIgnore
    List<Long> idEntreprises;

}
