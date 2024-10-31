package org.javaacademy.util;

import org.javaacademy.citizen.Citizen;
import org.javaacademy.registry_office.CivilActionRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.javaacademy.registry_office.TypeCivilAction.*;

public class CivilUtilTest {
    @Test
    @DisplayName("Правильное количество событий")
    public void countSuccess() {
        LocalDate date = LocalDate.now();
        Citizen citizen1 = new Citizen("Вениамин", "Иванов", "Иванович", true);
        Citizen citizen2 = new Citizen("Анна", "Иванова", "Ивановна", false);
        List<Citizen> citizens = new ArrayList<>(List.of(citizen1, citizen2));
        CivilActionRecord civilActionRecord1 = new CivilActionRecord(date, WEDDING_REGISTRATION, citizens);
        List<CivilActionRecord> actions = new ArrayList<>(List.of(civilActionRecord1));

        var result = CivilUtil.countType(actions, WEDDING_REGISTRATION);
        var expected = 1;
        Assertions.assertEquals(expected, result);
    }
}
