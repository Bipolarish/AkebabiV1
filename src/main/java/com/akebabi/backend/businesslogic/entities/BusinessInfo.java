package com.akebabi.backend.businesslogic.entities;

import com.akebabi.backend.security.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BUSINESS_INFO")
@Builder
public class BusinessInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Integer id;
    @Column(name = "BUSINESS_PUBLIC_ID")
    String businessPublicId;
    @Column(name = "NAME")
    String name;
    @Column(name = "PROFILE_NAME")
    String profileUrlName;
    @Column(name = "DESCRIPTION")
    @Lob
    String description;
    @Column(name = "EMAIL")
    String email;
    @Column(name = "WEBSITE")
    String website;
    @Column(name = "PRIMARY_PHONE")
    String primaryPhone;
    @Column(name = "TAGS")
    String tags;
    @Column(name = "STREET_ADDRESS")
    String streetAddress;
    @Column(name = "CITY")
    String city;
    @Column(name = "POBOX")
    String pobox;
    @Column(name = "LATITUDE")
    String latitude;
    @Column(name = "LONGITUDE")
    String longitude;

    @ManyToOne
    @JoinColumn(name="CATEGORY_ID", nullable=false)
    BusinessCategory category;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "BUSINESS_ID")
    Set<SocialLink> socialLinks = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "BUSINESS_ID")
    Set<OpenHours> openHours = new HashSet<>();;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "BUSINESS_ID")
    Set<BusinessImage> images = new HashSet<>();;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "BUSINESS_ID")
    Set<Contacts> contacts = new HashSet<>();;

//    @JsonBackReference(value = "business-postedBy")
    @ManyToOne
    @JoinColumn(name="POSTER_ID", nullable=false)
    private User postedBy;



//
//

}
