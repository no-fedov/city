package org.javaacademy;

import lombok.Getter;
import lombok.NonNull;

import java.util.*;

import static org.javaacademy.CivilUtil.countType;
import static org.javaacademy.TypeCivilAction.*;

@Getter
public class CivilRegistry {
    private static final String statisticPattern = "\"Статистика по ЗАГС: %s\n" +
            "\"Дата %s: количество свадеб - %d, количество разводов - %d, количество рождений - %d\"\n";

    private final String registryOfficeName;
    private final Set<CivilActionRecord> listCivilActionRecordsSortedByDate;

    public CivilRegistry(String registryOfficeName) {
        this.registryOfficeName = registryOfficeName;
        this.listCivilActionRecordsSortedByDate = new TreeSet<>(Comparator.comparing(CivilActionRecord::getActionDate));
    }

    // TODO: 3.4.1 Рождение ребенка - передается новорожденный, отец, мать + дата регистрации рождения.
    //  Создается запись гражданского действия за дату регистрации.
    public void birthChild(Citizen child, Citizen mather, Citizen father, Date date) {
        // сделать проверки, создать запись, добавить в список
    }

    // TODO: 3.4.2 Регистрация свадьбы - передаются мужчина и женщина + дата регистрации свадьбы.
    //  Меняется семейный статус у мужчины и женщины. Создается запись гражданского действия за дату регистрации.
    public void registrationWedding(Citizen man, Citizen woman, Date date) {
    }

    // TODO: 3.4.3 Регистрация развода - передаются мужчина и женщина + дата регистрации развода.
    //  Меняется семейный статус у мужчины и женщины. Создается запись гражданского действия за дату регистрации.
    public void registrationDivorce(Citizen man, Citizen woman, Date date) {
        // сделать проверку, что они были женаты, потом логику выполнять
    }

    public void getStatistic(@NonNull Date findDate) {
        List<CivilActionRecord> recordsWithFindDate = listCivilActionRecordsSortedByDate.stream()
                .filter(e -> e.getActionDate().equals(findDate))
                .toList();

        long countWedding = countType(recordsWithFindDate, WEDDING_REGISTRATION);
        long countDivorce = countType(recordsWithFindDate, DIVORCE_REGISTRATION);
        long countBirth = countType(recordsWithFindDate, BIRTH_REGISTRATION);

        System.out.printf(statisticPattern,
                this.getRegistryOfficeName(),
                findDate,
                countWedding, countDivorce, countBirth);
    }
}
