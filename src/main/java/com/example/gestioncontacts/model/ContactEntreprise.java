package com.example.gestioncontacts.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contact_entreprise")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContactEntreprise {

    @Id
    @Column(name = "id")
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_contact")
    Contact contact;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_entreprise")
    Entreprise entreprise;

}
