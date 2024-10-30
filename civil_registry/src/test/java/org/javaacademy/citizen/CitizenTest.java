package org.javaacademy.citizen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CitizenTest {
    @Test
    @DisplayName("Успешное создание гражданина")
    public void createCitizenSuccess() {
        Citizen citizen = Assertions.assertDoesNotThrow(() -> new Citizen("Вася",
                "Вася",
                "Вася",
                true));
        Assertions.assertEquals(MaritalStatus.SINGLE, citizen.getMaritalStatus());
    }

    @Test
    @DisplayName("Неуспешное создание гражданина, null в имени")
    public void createCitizenWithNullName() {
        Assertions.assertThrows(NullPointerException.class, () -> new Citizen(null,
                "Вася",
                "Вася",
                true));
    }

    @Test
    @DisplayName("Неуспешное создание гражданина, null в фамилии")
    public void createCitizenWithNullFemale() {
        Assertions.assertThrows(NullPointerException.class, () -> new Citizen("Вася",
                null,
                "Вася",
                true));
    }

    @Test
    @DisplayName("Неуспешное создание гражданина, null в отчестве")
    public void createCitizenWithNullPatronymic() {
        Assertions.assertThrows(NullPointerException.class, () -> new Citizen("Вася",
                "Вася",
                null,
                true));
    }

    @Test
    @DisplayName("Граждане зачали ребенка. Успешное выполнение")
    public void makeCitizenChildSuccess() {
        Citizen citizen1 = new Citizen("1", "2", "3", true);
        Citizen citizen2 = new Citizen("1", "2", "3", false);

        Citizen child = citizen1.makeChild("1", "2", "3", true, citizen2);
        Assertions.assertNotNull(child);

        Assertions.assertEquals(citizen1, child.getFather());
        Assertions.assertEquals(citizen2, child.getMother());
        Assertions.assertEquals(child, citizen1.getChildren().get(0));
        Assertions.assertEquals(child, citizen2.getChildren().get(0));
    }

    @Test
    @DisplayName("Граждане одного пола - вызов метода зачать ребенка. Провал")
    public void makeCitizenChildFailure() {
        Citizen citizen1 = new Citizen("1", "2", "3", false);
        Citizen citizen2 = new Citizen("1", "2", "3", false);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> citizen1.makeChild("1",
                "2",
                "3",
                true,
                citizen2));

        Assertions.assertEquals("Отношения не могут быть между однополыми людьми.", exception.getMessage());
    }
}
