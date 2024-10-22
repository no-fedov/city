package org.javaacademy;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
public class CivilActionRecord {
    @NonNull
    private Date actionDate;

    @NonNull
    private TypeCivilAction actionType;

    @NonNull
    private List<Citizen> involvedCitizens;

    public CivilActionRecord(@NonNull Date actionDate,
                             @NonNull TypeCivilAction actionType,
                             @NonNull List<Citizen> involvedCitizens) {
        this.actionDate = actionDate;
        this.actionType = actionType;
        this.involvedCitizens = involvedCitizens;
    }
}
