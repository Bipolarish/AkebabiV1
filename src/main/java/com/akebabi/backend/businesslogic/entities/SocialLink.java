package com.akebabi.backend.businesslogic.entities;

import com.akebabi.backend.security.enums.SOCIAL_LINK_TYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "SOCIAL_LINK")
@AllArgsConstructor
@NoArgsConstructor
public class SocialLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "URL")
    private String url;

    @Basic
    private int intSocialLinkType;
    @Transient
    private SOCIAL_LINK_TYPE enumSocialLinkType;

    @PostLoad
    void fillTransient() {
        this.enumSocialLinkType = SOCIAL_LINK_TYPE.of(this.intSocialLinkType);
    }

    @PrePersist
    void fillSocialLinkTypeValue() {
        if (enumSocialLinkType != null) {
            this.intSocialLinkType = enumSocialLinkType.getSocialLinkType();
        }
    }
}
