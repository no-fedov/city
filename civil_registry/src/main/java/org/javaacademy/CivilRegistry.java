package org.javaacademy;


import lombok.NonNull;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.javaacademy.MaritalStatus.DIVORCED;
import static org.javaacademy.MaritalStatus.MARRIED;
import static org.javaacademy.TypeCivilAction.DIVORCE_REGISTRATION;
import static org.javaacademy.TypeCivilAction.WEDDING_REGISTRATION;

public class CivilRegistry {
    private String registryOfficeName;
    private Set<CivilActionRecord> listCivilActionRecordsSortedByDate;

    public CivilRegistry(String registryOfficeName) {
        this.registryOfficeName = registryOfficeName;
        this.listCivilActionRecordsSortedByDate = new TreeSet<>(Comparator.comparing(CivilActionRecord::getActionDate)
                .thenComparing(CivilActionRecord::getActionType, Enum::compareTo)
                .thenComparing(CivilActionRecord::getInvolvedCitizens, Comparator.comparingInt(List::hashCode)));
    }

    // TODO: 3.4.1 Рождение ребенка - передается новорожденный, отец, мать + дата регистрации рождения.
    //  Создается запись гражданского действия за дату регистрации.
    public void birthChild(Citizen child, Citizen mather, Citizen father, Date date) {
        List<Citizen> citizens = new ArrayList<>();
        citizens.add(mather);
        citizens.add(father);
        CivilActionRecord civilActionRecord = new CivilActionRecord(LocalDate.now(),
                BIRTH_REGISTRATION,citizens );
        if (child != null && mather != null && father != null){
            listCivilActionRecordsSortedByDate.add(civilActionRecord);
        }
        // сделать проверки, создать запись, добавить в список
    }

    @NonNull
    public void registrationWedding(Citizen man, Citizen woman, LocalDate date) {
        if (man.getSpouse() != null && woman.getSpouse() != null) {
            throw new RuntimeException("Нельзя дважды зарегестрировать брак");
        }

        if (!man.isMale()) {
            throw new RuntimeException("Мужчина - не мужчина");
        }

        if (woman.isMale()) {
            throw new RuntimeException("Женщина - не женщина");
        }
        manageMaritalRelations(man, woman, MARRIED);
        makeCivilActionRecord(date, WEDDING_REGISTRATION, man, woman);
    }

    @NonNull
    public void registrationDivorce(Citizen man, Citizen woman, LocalDate date) {
        if (!man.getSpouse().equals(woman)) {
            throw new RuntimeException("Нельзя развеститсь людям которые не состоят в браке");
        }

        if (!man.isMale()) {
            throw new RuntimeException("Мужчина - не мужчина");
        }

        if (woman.isMale()) {
            throw new RuntimeException("Женщина - не женщина");
        }
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

    // TODO: 3.4.4 Создать метод получения статистики за дату, формат печати в консоль:
    //  "Статистика по ЗАГС: [имя загса]
    //  Дата 20/02/2023: количество свадеб - 1, количество разводов - 2, количество рождений - 5"
    public void getStatistic() {
        // тут работа со стримами и полем listCivilActionRecordsSortedByDate
    }
}
