package org.javaacademy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.function.Predicate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class Programmer extends Employee {
    Task task;
    static final BigDecimal MIN_RATE = BigDecimal.valueOf(1500);
    static final BigDecimal MAX_RATE = BigDecimal.valueOf(2000);

    public Programmer(@NonNull String name,
                      @NonNull String surname,
                      @NonNull String patronymic,
                      boolean isMale) {
        super(name, surname, patronymic, isMale);
    }

    public void setProgrammerRate(@NonNull BigDecimal rate) {
        checkValidateRate(expectValidate -> expectValidate.compareTo(MIN_RATE) >= 0, rate);
        checkValidateRate(expectValidate -> expectValidate.compareTo(MAX_RATE) < 0, rate);
        this.setRate(rate);
    }

    private void checkValidateRate(Predicate<BigDecimal> condition, BigDecimal rate) {
        if (condition.test(rate)) {
            return;
        }
        throw new RuntimeException(String.format("Неподходящая ставка, ставка должна быть в диапазоне %s - %s.",
                MIN_RATE, MAX_RATE));
    }

    public void takeTask(@NonNull Task receivedtask) {
        if (receivedtask.isSolved()) {
            throw new RuntimeException("Задача уже решена");
        }
        this.task = receivedtask;
        task.setSolved(true);
    }
}
