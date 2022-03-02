package com.akebabi.backend.businesslogic.entities;

import com.akebabi.backend.security.enums.SOCIAL_LINK_TYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "BUSINESS_IMAGE")
@AllArgsConstructor
@NoArgsConstructor
public class BusinessImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "URL")
    private String url;
    @Column(name = "IS_PRIMARY")
    private Boolean isPrimary;

}
