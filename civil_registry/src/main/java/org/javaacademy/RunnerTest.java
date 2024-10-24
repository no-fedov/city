package org.javaacademy;

import org.javaacademy.citizen.Citizen;
import org.javaacademy.registry_office.CivilRegistry;

import java.time.LocalDate;

public class RunnerTest {
    public static void main(String[] args) {
        String nameCivilRegistry = "TEST_ZAGS";
        CivilRegistry civilRegistry = new CivilRegistry(nameCivilRegistry);
        LocalDate date = LocalDate.of(2000, 1, 10);

        Citizen citizen1 = new Citizen("Вениамин", "Иванов", "Иванович", true);
        Citizen citizen2 = new Citizen("Анна", "Иванова", "Ивановна", false);
        civilRegistry.registrationWedding(citizen1, citizen2, date);

        Citizen citizen3 = new Citizen("Антон", "Лебедев", "Артемович", true);
        Citizen citizen4 = new Citizen("Елена", "Лебедева", "Степановна", false);
        civilRegistry.registrationWedding(citizen3, citizen4, date);

        civilRegistry.registrationDivorce(citizen3, citizen4, date);

        Citizen child1 = citizen1.makeChild(
                "Ребенок1",
                "Ребенок1",
                "Ребенок1",
                false,
                citizen2
        );

        Citizen child2 = citizen1.makeChild(
                "Ребенок2",
                "Ребенок2",
                "Ребенок2",
                false,
                citizen2
        );

        Citizen child3 = citizen1.makeChild(
                "Ребенок3",
                "Ребенок3",
                "Ребенок3",
                false,
                citizen2
        );

        civilRegistry.birthChild(child1, citizen2, citizen1, date);
        civilRegistry.birthChild(child2, citizen2, citizen1, date);
        civilRegistry.birthChild(child3, citizen2, citizen1, date);

        civilRegistry.getStatistic(date);
    }
}
