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
@Table(name = "entreprises")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Entreprise {

    @Id
    @Column(name = "id")
    Long id;

    @Column(name = "adresse")
    String adresse;

    @Column(name = "numero_tva")
    String numeroTva;

    @Transient
    List<Contact> contacts;

}
