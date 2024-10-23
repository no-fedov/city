package org.javaacademy.registry_office;

import lombok.NonNull;
import org.javaacademy.citizen.Citizen;
import org.javaacademy.citizen.MaritalStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.javaacademy.citizen.MaritalStatus.DIVORCED;
import static org.javaacademy.citizen.MaritalStatus.MARRIED;
import static org.javaacademy.registry_office.TypeCivilAction.*;
import static org.javaacademy.util.CivilUtil.countType;

public class CivilRegistry {
    private static final String statisticPattern = "\"Статистика по ЗАГС: %s\n" +
            "\"Дата %s: количество свадеб - %d, количество разводов - %d, количество рождений - %d\"\n";

    private String registryOfficeName;
    private Map<LocalDate, List<CivilActionRecord>> listCivilActionRecordsSortedByDate;

    public CivilRegistry(String registryOfficeName) {
        this.registryOfficeName = registryOfficeName;
        this.listCivilActionRecordsSortedByDate = new TreeMap<>();
    }

    // TODO: нужна ли нам тут варификация что это их ребенок?
    public void birthChild(@NonNull Citizen child,
                           @NonNull Citizen mather,
                           @NonNull Citizen father,
                           @NonNull LocalDate date) {
        if (!mather.getChildren().contains(child) || !father.getChildren().contains(child)) {
            throw new RuntimeException("Нельзя зарегистрировать чужого ребенка");
        }
        genderVerification(father, mather);
        makeCivilActionRecord(date, BIRTH_REGISTRATION, mather, father, child);
    }

    public void registrationWedding(@NonNull Citizen man,
                                    @NonNull Citizen woman,
                                    @NonNull LocalDate date) {
        if (man.getSpouse() != null || woman.getSpouse() != null) {
            throw new RuntimeException("Нельзя дважды зарегистрировать брак");
        }
        genderVerification(man, woman);
        manageMaritalRelations(man, woman, MARRIED);
        makeCivilActionRecord(date, WEDDING_REGISTRATION, man, woman);
    }

    public void registrationDivorce(@NonNull Citizen man,
                                    @NonNull Citizen woman,
                                    @NonNull LocalDate date) {
        if (!man.getSpouse().equals(woman)) {
            throw new RuntimeException("Нельзя развестись людям, которые не состоят в браке");
        }
        genderVerification(man, woman);
        manageMaritalRelations(man, woman, DIVORCED);
        makeCivilActionRecord(date, DIVORCE_REGISTRATION, man, woman);
    }

    public void getStatistic(@NonNull LocalDate findDate) {
        List<CivilActionRecord> recordsWithFindDate = listCivilActionRecordsSortedByDate.get(findDate);

        if (recordsWithFindDate == null) {
            printStatistic(findDate, 0, 0, 0);
            return;
        }

        long countWedding = countType(recordsWithFindDate, WEDDING_REGISTRATION);
        long countDivorce = countType(recordsWithFindDate, DIVORCE_REGISTRATION);
        long countBirth = countType(recordsWithFindDate, BIRTH_REGISTRATION);

        printStatistic(findDate, countWedding, countDivorce, countBirth);
    }

    private void printStatistic(LocalDate date, long countWedding, long countDivorce, long countBirth) {
        System.out.printf(statisticPattern,
                this.registryOfficeName,
                date,
                countWedding, countDivorce, countBirth);
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
        listCivilActionRecordsSortedByDate
                .computeIfAbsent(date, key -> new ArrayList<>())
                .add(civilActionRecord);
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
}
