package org.javaacademy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
public class CivilActionRecord {
    private Date actionDate;
    private String actionType;
    private List<Citizen> involvedCitizens;

    public CivilActionRecord(Date actionDate, String actionType, List<Citizen> involvedCitizens) {
        this.actionDate = actionDate;
        this.actionType = actionType;
        this.involvedCitizens = involvedCitizens;
    }
}
