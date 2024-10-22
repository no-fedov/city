package org.javaacademy;

import java.time.LocalDate;
import java.util.*;

import static org.javaacademy.TypeCivilAction.*;

public class CivilRegistry {
    private String registryOfficeName;
    private Set<CivilActionRecord> listCivilActionRecordsSortedByDate;

    public CivilRegistry(String registryOfficeName) {
        this.registryOfficeName = registryOfficeName;
        this.listCivilActionRecordsSortedByDate = new TreeSet<>(Comparator.comparing(CivilActionRecord::getActionDate));
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

    // TODO: 3.4.2 Регистрация свадьбы - передаются мужчина и женщина + дата регистрации свадьбы.
    //  Меняется семейный статус у мужчины и женщины. Создается запись гражданского действия за дату регистрации.
    public void registrationWedding(Citizen man, Citizen woman, Date date) {
    }

    // TODO: 3.4.3 Регистрация развода - передаются мужчина и женщина + дата регистрации развода.
    //  Меняется семейный статус у мужчины и женщины. Создается запись гражданского действия за дату регистрации.
    public void registrationDivorce(Citizen man, Citizen woman, Date date) {
        // сдеалть проверку что они были женаты, потом логику выполнять
    }

    // TODO: 3.4.4 Создать метод получения статистики за дату, формат печати в консоль:
    //  "Статистика по ЗАГС: [имя загса]
    //  Дата 20/02/2023: количество свадеб - 1, количество разводов - 2, количество рождений - 5"
    public void getStatistic() {
        // тут работа со стримами и полем listCivilActionRecordsSortedByDate
    }
}
