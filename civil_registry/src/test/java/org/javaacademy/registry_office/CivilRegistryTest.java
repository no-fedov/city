package org.javaacademy.registry_office;

import lombok.SneakyThrows;
import org.javaacademy.citizen.Citizen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CivilRegistryTest {
    private CivilRegistry civilRegistry;

    @BeforeEach
    public void init() {
        civilRegistry = new CivilRegistry("Тестовый ЗАГС");
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

        List<Citizen> involvedCitizen = records.get(date).get(0).getInvolvedCitizens();
        Assertions.assertIterableEquals(involvedCitizen, List.of(citizen1, citizen2, child));
    }

    @Test
    @DisplayName("Регистрация рождения ребенка, однополые люди. Неуспешное выполнение")
    @SneakyThrows
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
    @SneakyThrows
    public void registerAlienChildInCivilRegistryFailure() {
        Citizen citizen1 = new Citizen("1", "2", "3", true);
        Citizen citizen2 = new Citizen("1", "2", "3", false);
        Citizen child = new Citizen("ребенок", "ребенок", "ребенок", false);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> civilRegistry.birthChild(child, citizen1, citizen2, LocalDate.now()));

        Assertions.assertEquals("Нельзя зарегистрировать чужого ребенка", exception.getMessage());
    }
}
