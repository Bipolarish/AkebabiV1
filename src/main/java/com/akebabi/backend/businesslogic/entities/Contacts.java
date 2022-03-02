package com.akebabi.backend.businesslogic.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "BUSINESS_CONTACTS")
@AllArgsConstructor
@NoArgsConstructor
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "IS_PRIMARY")
    private Boolean isPrimary;
    @Column(name = "NAME")
    private String name;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "AVAILABILITY")
    private String availability;
    @Column(name = "PROFILE_IMAGE")
    private String profileImage;
}
