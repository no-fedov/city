package org.javaacademy;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
public class CivilActionRecord {
    @NonNull
    private LocalDate actionDate;

    @NonNull
    private TypeCivilAction actionType;

    @NonNull
    private List<Citizen> involvedCitizens;

    public CivilActionRecord(@NonNull LocalDate actionDate,
                             @NonNull TypeCivilAction actionType,
                             @NonNull List<Citizen> involvedCitizens) {
        this.actionDate = actionDate;
        this.actionType = actionType;
        this.involvedCitizens = involvedCitizens;
    }
}
