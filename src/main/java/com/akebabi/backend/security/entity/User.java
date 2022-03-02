package com.akebabi.backend.security.entity;

import com.akebabi.backend.businesslogic.entities.BusinessInfo;
import com.akebabi.backend.security.model.UserProfileModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    Integer id;
    @Column(name = "USER_NAME")
    String userName;
    @Column(name = "PASSWORD")
    String passWord;
    @Column(name = "IS_LOCKED")
    boolean isLocked;
    @Column(name = "IS_ENABLED")
    boolean isEnabled;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="ROLE_USER",joinColumns=@JoinColumn(name="USER_ID"),inverseJoinColumns=@JoinColumn(name="ROLE_ID"))
    @JsonIgnoreProperties("users")
    private Set<Role> roles=new HashSet<>();

    @Column(name = "CREATED_ON")
    Date createdOn;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "USER_PUBLIC_ID")
    private String userPublicId;
    @Transient
    private String activationUrl;

    @JsonIgnore
    @OneToMany(mappedBy="postedBy",cascade = CascadeType.ALL)
    private List<BusinessInfo> businesses;

//    @OneToMany(fetch =FetchType.LAZY, mappedBy = "postedBy")
////    @JsonBackReference(value = "user-car-back-reference")
////    @JsonManagedReference(value = "user-car-back-reference")
//    private List<Car> postCars;

    public User fromUserProfile(UserProfileModel userProfileModel){
        return this.builder()
                .userName(userProfileModel.getEmail())
                .passWord(userProfileModel.getUserPassword())
                .firstName(userProfileModel.getFirstName())
                .lastName(userProfileModel.getLastName())
                .userPublicId(userProfileModel.getUserPublicId())
                .build();
    }
}
