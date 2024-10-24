package org.javaacademy;

import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;

@Getter
public class Manager extends Employee {
    private final BigDecimal rate = BigDecimal.valueOf(10_000);

    public Manager(@NonNull String name, @NonNull String surname, @NonNull String patronymic, boolean isMale) {
        super(name, surname, patronymic, isMale);
    }
}
