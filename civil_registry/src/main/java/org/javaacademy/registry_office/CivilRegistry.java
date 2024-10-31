package org.javaacademy.registry_office;

import lombok.NonNull;
import org.javaacademy.HumanUtil;
import org.javaacademy.citizen.Citizen;
import org.javaacademy.citizen.MaritalStatus;
import org.javaacademy.util.CivilUtil;
import org.javaacademy.util.DateFormatter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.javaacademy.citizen.MaritalStatus.DIVORCED;
import static org.javaacademy.citizen.MaritalStatus.MARRIED;
import static org.javaacademy.registry_office.TypeCivilAction.BIRTH_REGISTRATION;
import static org.javaacademy.registry_office.TypeCivilAction.DIVORCE_REGISTRATION;
import static org.javaacademy.registry_office.TypeCivilAction.WEDDING_REGISTRATION;

public class CivilRegistry {
    private static final String STATISTIC_TEMPLATE = "\"Статистика по ЗАГС: %s\n"
            + "Дата %s: количество свадеб - %d, количество разводов - %d, количество рождений - %d\"\n";

    private String registryOfficeName;
    private Map<LocalDate, List<CivilActionRecord>> sortedRecordsByDate;

    public CivilRegistry(String registryOfficeName) {
        this.registryOfficeName = registryOfficeName;
        this.sortedRecordsByDate = new TreeMap<>();
    }

    public void birthChild(@NonNull Citizen child,
                           @NonNull Citizen firstCitizen,
                           @NonNull Citizen secondCitizen,
                           @NonNull LocalDate date) {
        HumanUtil.genderOppositeCheck(firstCitizen, secondCitizen);
        if (!firstCitizen.getChildren().contains(child)
                || !secondCitizen.getChildren().contains(child)) {
            throw new RuntimeException("Нельзя зарегистрировать чужого ребенка");
        }
        makeCivilActionRecord(date, BIRTH_REGISTRATION, firstCitizen, secondCitizen, child);
    }

    public void registrationWedding(@NonNull Citizen firstCitizen,
                                    @NonNull Citizen secondCitizen,
                                    @NonNull LocalDate date) {
        HumanUtil.genderOppositeCheck(firstCitizen, secondCitizen);
        if (firstCitizen.getSpouse() != null || secondCitizen.getSpouse() != null) {
            throw new RuntimeException("Нельзя дважды зарегистрировать брак");
        }
        manageMaritalRelations(firstCitizen, secondCitizen, MARRIED);
        makeCivilActionRecord(date, WEDDING_REGISTRATION, firstCitizen, secondCitizen);
    }

    public void registrationDivorce(@NonNull Citizen firstCitizen,
                                    @NonNull Citizen secondCitizen,
                                    @NonNull LocalDate date) {
        HumanUtil.genderOppositeCheck(firstCitizen, secondCitizen);
        if (firstCitizen.getMaritalStatus() != MARRIED
                || secondCitizen.getMaritalStatus() != MARRIED
                || firstCitizen.getSpouse() == null
                || secondCitizen.getSpouse() == null
                || !firstCitizen.getSpouse().equals(secondCitizen)) {
            throw new RuntimeException("Нельзя развестись людям, которые не состоят в браке");
        }
        manageMaritalRelations(firstCitizen, secondCitizen, DIVORCED);
        makeCivilActionRecord(date, DIVORCE_REGISTRATION, firstCitizen, secondCitizen);
    }

    public void getStatistic(@NonNull LocalDate findDate) {
        List<CivilActionRecord> recordsWithFindDate = sortedRecordsByDate.get(findDate);

        if (recordsWithFindDate == null) {
            printStatistic(findDate, 0, 0, 0);
            return;
        }

        long countWedding = CivilUtil.countType(recordsWithFindDate, WEDDING_REGISTRATION);
        long countDivorce = CivilUtil.countType(recordsWithFindDate, DIVORCE_REGISTRATION);
        long countBirth = CivilUtil.countType(recordsWithFindDate, BIRTH_REGISTRATION);

        printStatistic(findDate, countWedding, countDivorce, countBirth);
    }

    private void printStatistic(LocalDate date, long countWedding, long countDivorce, long countBirth) {
        System.out.printf(STATISTIC_TEMPLATE,
                this.registryOfficeName,
                DateFormatter.formatDate(date),
                countWedding, countDivorce, countBirth);
    }

    private void manageMaritalRelations(Citizen firstCitizen,
                                        Citizen secondCitizen,
                                        MaritalStatus maritalStatus) {
        firstCitizen.setMaritalStatus(maritalStatus);
        secondCitizen.setMaritalStatus(maritalStatus);
        if (maritalStatus == DIVORCED) {
            firstCitizen.setSpouse(null);
            secondCitizen.setSpouse(null);
            return;
        }
        firstCitizen.setSpouse(secondCitizen);
        secondCitizen.setSpouse(firstCitizen);
    }

    private void makeCivilActionRecord(LocalDate date,
                                       TypeCivilAction civilAction,
                                       Citizen... citizens) {
        CivilActionRecord civilActionRecord = new CivilActionRecord(date, civilAction, List.of(citizens));
        sortedRecordsByDate.computeIfAbsent(date, key -> new ArrayList<>())
                .add(civilActionRecord);
    }
}
