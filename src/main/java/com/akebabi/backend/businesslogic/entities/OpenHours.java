package com.akebabi.backend.businesslogic.entities;

import com.akebabi.backend.security.enums.DAY_OF_WEEK;
import com.akebabi.backend.security.enums.SOCIAL_LINK_TYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OPEN_HOURS")
public class OpenHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Basic
    private int intDayOfWeek;
    @Transient
    private DAY_OF_WEEK enumDayOfWeek;
    @PostLoad
    void fillTransient() {
        this.enumDayOfWeek = DAY_OF_WEEK.of(this.intDayOfWeek);
    }
    @PrePersist
    void fillSocialLinkTypeValue() {
        if (enumDayOfWeek != null) {
            this.intDayOfWeek = enumDayOfWeek.getIntDayOfWeek();
        }
    }

    @Column(name = "STARTING_HOUR")
    String startingHour;
    @Column(name = "ENDING_HOUR")
    String endingHour;

}
