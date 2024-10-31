package org.javaacademy.registry_office;

import lombok.SneakyThrows;
import org.javaacademy.citizen.Citizen;
import org.javaacademy.citizen.MaritalStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CivilRegistryTest {
    private CivilRegistry civilRegistry;
    private ByteArrayOutputStream outputStream;
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void init() {
        civilRegistry = new CivilRegistry("Тестовый ЗАГС");
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    @SneakyThrows
    void tearDown() {
        System.setOut(originalOut);
        outputStream.close();
    }

    @Test
    @DisplayName("Регистрация рождения ребенка. Успешное выполнение")
    @SneakyThrows
    public void registerChildInCivilRegistrySuccess() {
        Citizen citizen1 = new Citizen("1", "2", "3", true);
        Citizen citizen2 = new Citizen("1", "2", "3", false);
        Citizen child = citizen1.makeChild("1", "2", "3", true, citizen2);
        LocalDate date = LocalDate.now();
        civilRegistry.birthChild(child, citizen1, citizen2, date);

        Field recordsField = CivilRegistry.class.getDeclaredField("sortedRecordsByDate");
        recordsField.setAccessible(true);
        var records = (Map<LocalDate, List<CivilActionRecord>>) recordsField.get(civilRegistry);

        CivilActionRecord record = records.get(date).get(0);
        Assertions.assertEquals(TypeCivilAction.BIRTH_REGISTRATION, record.getActionType());

        List<Citizen> involvedCitizen = record.getInvolvedCitizens();
        Assertions.assertIterableEquals(involvedCitizen, List.of(citizen1, citizen2, child));
    }

    @Test
    @DisplayName("Регистрация рождения ребенка, однополые люди. Неуспешное выполнение")
    public void registerChildInCivilRegistryWithTwoSameSexFailure() {
        Citizen citizen1 = new Citizen("1", "2", "3", false);
        Citizen citizen2 = new Citizen("1", "2", "3", false);
        Citizen child = new Citizen("ребенок", "ребенок", "ребенок", false);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> civilRegistry.birthChild(child, citizen1, citizen2, LocalDate.now()));

        Assertions.assertEquals("Отношения не могут быть между однополыми людьми.", exception.getMessage());
    }

    @Test
    @DisplayName("Регистрация чужого ребенка. Неуспешное выполнение")
    public void registerAlienChildInCivilRegistryFailure() {
        Citizen citizen1 = new Citizen("1", "2", "3", true);
        Citizen citizen2 = new Citizen("1", "2", "3", false);
        Citizen child = new Citizen("ребенок", "ребенок", "ребенок", false);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> civilRegistry.birthChild(child, citizen1, citizen2, LocalDate.now()));

        Assertions.assertEquals("Нельзя зарегистрировать чужого ребенка", exception.getMessage());
    }

    @Test
    @DisplayName("Регистрация свадьбы. Успешное выполнение")
    @SneakyThrows
    public void registerWeddingSuccess() {
        Citizen citizen1 = new Citizen("1", "2", "3", true);
        Citizen citizen2 = new Citizen("1", "2", "3", false);

        LocalDate date = LocalDate.now();
        civilRegistry.registrationWedding(citizen1, citizen2, date);

        Assertions.assertEquals(citizen1, citizen2.getSpouse());
        Assertions.assertEquals(citizen2, citizen1.getSpouse());
        Assertions.assertEquals(MaritalStatus.MARRIED, citizen1.getMaritalStatus());
        Assertions.assertEquals(MaritalStatus.MARRIED, citizen2.getMaritalStatus());

        Field recordsField = CivilRegistry.class.getDeclaredField("sortedRecordsByDate");
        recordsField.setAccessible(true);
        var records = (Map<LocalDate, List<CivilActionRecord>>) recordsField.get(civilRegistry);

        CivilActionRecord record = records.get(date).get(0);
        Assertions.assertEquals(TypeCivilAction.WEDDING_REGISTRATION, record.getActionType());

        List<Citizen> involvedCitizen = record.getInvolvedCitizens();
        Assertions.assertIterableEquals(involvedCitizen, List.of(citizen1, citizen2));
    }

    @Test
    @DisplayName("Регистрация свадьбы между однополыми людьми. Неуспешное выполнение")
    public void registerWeddingWithTwoSameSexFailure() {
        Citizen citizen1 = new Citizen("1", "2", "3", true);
        Citizen citizen2 = new Citizen("1", "2", "3", true);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> civilRegistry.registrationWedding(citizen1, citizen2, LocalDate.now()));

        Assertions.assertEquals("Отношения не могут быть между однополыми людьми.", exception.getMessage());
    }

    @Test
    @DisplayName("Регистрация свадьбы, когда человек находится в браке. Неуспешное выполнение")
    public void registerWeddingWithMarriedCitizen() {
        Citizen citizen1 = new Citizen("1", "2", "3", true);
        Citizen citizen2 = new Citizen("1", "2", "3", false);
        Citizen citizen3 = new Citizen("3", "3", "3", true);
        citizen2.setSpouse(citizen3);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> civilRegistry.registrationWedding(citizen1, citizen2, LocalDate.now()));

        Assertions.assertEquals("Нельзя дважды зарегистрировать брак", exception.getMessage());
    }

    @Test
    @DisplayName("Регистрация развода. Успешное выполнение")
    @SneakyThrows
    public void registerDivorceSuccess() {
        Citizen citizen1 = new Citizen("1", "2", "3", true);
        Citizen citizen2 = new Citizen("1", "2", "3", false);

        LocalDate dateWedding = LocalDate.now();
        civilRegistry.registrationWedding(citizen1, citizen2, dateWedding);

        LocalDate dateDivorce = dateWedding.plusDays(1);
        civilRegistry.registrationDivorce(citizen1, citizen2, dateDivorce);

        Assertions.assertNull(citizen1.getSpouse());
        Assertions.assertNull(citizen2.getSpouse());

        Field recordsField = CivilRegistry.class.getDeclaredField("sortedRecordsByDate");
        recordsField.setAccessible(true);
        var records = (Map<LocalDate, List<CivilActionRecord>>) recordsField.get(civilRegistry);

        CivilActionRecord record = records.get(dateDivorce).get(0);
        Assertions.assertEquals(TypeCivilAction.DIVORCE_REGISTRATION, record.getActionType());

        List<Citizen> involvedCitizen = record.getInvolvedCitizens();
        Assertions.assertIterableEquals(involvedCitizen, List.of(citizen1, citizen2));
    }

    @Test
    @DisplayName("Регистрация развода между однополыми людьми. Неуспешное выполнение")
    public void registerDivorceBetweenSameSexCitizenFailure() {
        Citizen citizen1 = new Citizen("1", "2", "3", true);
        Citizen citizen2 = new Citizen("1", "2", "3", true);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> civilRegistry.registrationDivorce(citizen1, citizen2, LocalDate.now()));

        Assertions.assertEquals("Отношения не могут быть между однополыми людьми.", exception.getMessage());
    }

    @Test
    @DisplayName("Регистрация развода между неженатыми людьми. Неуспешное выполнение")
    public void registerDivorceBetweenUnmarriedCitizenFailure() {
        Citizen citizen1 = new Citizen("1", "2", "3", true);
        Citizen citizen2 = new Citizen("1", "2", "3", false);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> civilRegistry.registrationDivorce(citizen1, citizen2, LocalDate.now()));

        Assertions.assertEquals("Нельзя развестись людям, которые не состоят в браке", exception.getMessage());
    }

    @Test
    @DisplayName("Получение статистики за дату, когда не было регистраций событий")
    public void getNothingStatisticSuccess() {
        LocalDate date = LocalDate.of(2000, 1, 1);
        civilRegistry.getStatistic(date);
        Assertions.assertEquals("\"Статистика по ЗАГС: Тестовый ЗАГС\n"
               + "Дата 2000/01/01: количество свадеб - 0, количество разводов - 0, количество рождений - 0\"",
                outputStream.toString().trim());
    }

    @Test
    @DisplayName("Получение статистики за дату, когда было событие")
    public void getStatisticSuccess() {
        LocalDate date = LocalDate.of(2000, 1, 1);
        Citizen citizen1 = new Citizen("Вениамин", "Иванов", "Иванович", true);
        Citizen citizen2 = new Citizen("Анна", "Иванова", "Ивановна", false);
        civilRegistry.registrationWedding(citizen1, citizen2, date);

        civilRegistry.getStatistic(date);
        Assertions.assertEquals("\"Статистика по ЗАГС: Тестовый ЗАГС\n"
                        + "Дата 2000/01/01: количество свадеб - 1, количество разводов - 0, количество рождений - 0\"",
                outputStream.toString().trim());
    }
}
