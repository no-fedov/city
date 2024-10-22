package org.javaacademy;

import java.util.*;

import static java.util.Objects.compare;


/**
 * ЗАГС
 */
public class CivilRegistry {
    private String registryOfficeName;
    private Set<CivilActionRecord> listCivilActionRecordsForDates;

    public CivilRegistry(String registryOfficeName) {
        this.registryOfficeName = registryOfficeName;
        this.listCivilActionRecordsForDates = new TreeSet<>(Comparator.comparing(CivilActionRecord::getActionDate));
    }
}
