package org.javaacademy.registry_office;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.javaacademy.citizen.Citizen;

import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
@Setter
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
