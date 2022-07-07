package com.akebabi.backend.businesslogic.services;

import com.akebabi.backend.businesslogic.entities.OpenHours;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface OpenHoursService {
    Set<OpenHours> save(OpenHours openHours, String businessPublicID);
    Set<OpenHours> update(OpenHours openHours, String businessPublicId);
    Set<OpenHours> delete(OpenHours openHours, String businessPublicID);

    Set<OpenHours> delete(Integer openHoursId, String businessPublicID);
}
