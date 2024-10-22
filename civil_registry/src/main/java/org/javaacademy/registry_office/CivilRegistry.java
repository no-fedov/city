package org.javaacademy.registry_office;


import lombok.NonNull;
import org.javaacademy.citizen.Citizen;
import org.javaacademy.citizen.MaritalStatus;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.javaacademy.citizen.MaritalStatus.DIVORCED;
import static org.javaacademy.citizen.MaritalStatus.MARRIED;
import static org.javaacademy.registry_office.TypeCivilAction.*;

public class CivilRegistry {
    private String registryOfficeName;
    private Set<CivilActionRecord> listCivilActionRecordsSortedByDate;

    public CivilRegistry(String registryOfficeName) {
        this.registryOfficeName = registryOfficeName;
        this.listCivilActionRecordsSortedByDate = new TreeSet<>(Comparator.comparing(CivilActionRecord::getActionDate)
                .thenComparing(CivilActionRecord::getActionType, Enum::compareTo)
                .thenComparing(CivilActionRecord::getInvolvedCitizens, Comparator.comparingInt(List::hashCode)));
    }

    // TODO: нужна ли нам тут варификация что это их ребенок?
    @NonNull
    public void birthChild(Citizen child, Citizen mather, Citizen father, LocalDate date) {
        genderVerification(father, mather);
        makeCivilActionRecord(LocalDate.now(), BIRTH_REGISTRATION, mather, father, child);
    }

    @NonNull
    public void registrationWedding(Citizen man, Citizen woman, LocalDate date) {
        if (man.getSpouse() != null && woman.getSpouse() != null) {
            throw new RuntimeException("Нельзя дважды зарегестрировать брак");
        }
        genderVerification(man, woman);
        manageMaritalRelations(man, woman, MARRIED);
        makeCivilActionRecord(date, WEDDING_REGISTRATION, man, woman);
    }

    @NonNull
    public void registrationDivorce(Citizen man, Citizen woman, LocalDate date) {
        if (!man.getSpouse().equals(woman)) {
            throw new RuntimeException("Нельзя развеститсь людям которые не состоят в браке");
        }
        genderVerification(man, woman);
        manageMaritalRelations(man, woman, DIVORCED);
        makeCivilActionRecord(date, DIVORCE_REGISTRATION, man, woman);
    }

    private void manageMaritalRelations(Citizen man,
                                        Citizen woman,
                                        MaritalStatus maritalStatus) {
        man.setSpouse(woman);
        man.setMaritalStatus(maritalStatus);
        woman.setSpouse(woman);
        woman.setMaritalStatus(maritalStatus);
    }

    private void makeCivilActionRecord(LocalDate date,
                                       TypeCivilAction civilAction,
                                       Citizen... citizens) {
        CivilActionRecord civilActionRecord = new CivilActionRecord(date, civilAction, List.of(citizens));
        listCivilActionRecordsSortedByDate.add(civilActionRecord);
    }

    // TODO: подумать куда вынести и как оформить
    private void genderVerification(Citizen man, Citizen woman) {
        if (!man.isMale()) {
            throw new RuntimeException("Мужчина - не мужчина");
        }

        if (woman.isMale()) {
            throw new RuntimeException("Женщина - не женщина");
        }
    }

    public void getStatistic() {
        // тут работа со стримами и полем listCivilActionRecordsSortedByDate
    }
}