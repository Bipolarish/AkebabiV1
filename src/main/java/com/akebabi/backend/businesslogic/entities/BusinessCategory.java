package com.akebabi.backend.businesslogic.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BUSINESS_CATEGORY")
public class BusinessCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Integer id;

    @Column(name = "CATEGORY")
    private String category;

//    @JsonIgnore
//    @OneToMany(mappedBy="category",cascade = CascadeType.ALL)
//    List<BusinessInfo> businesses;

}
