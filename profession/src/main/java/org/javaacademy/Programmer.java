package org.javaacademy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.function.Predicate;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Programmer extends Employee {
    static final BigDecimal MIN_RATE = BigDecimal.valueOf(1500);
    static final BigDecimal MAX_RATE = BigDecimal.valueOf(2000);

    public Programmer(@NonNull String name,
                      @NonNull String surname,
                      @NonNull String patronymic,
                      boolean isMale) {
        super(name, surname, patronymic, isMale);
    }

    @Override
    public void setRate(@NonNull BigDecimal rate) {
        checkValidateRate(expectValidate -> expectValidate.compareTo(MIN_RATE) >= 0, rate);
        checkValidateRate(expectValidate -> expectValidate.compareTo(MAX_RATE) < 0, rate);
        this.rate = rate;
    }

    public void takeTask(@NonNull Task receivedtask) {
        if (receivedtask.isSolved()) {
            throw new RuntimeException("Задача уже решена");
        }
        receivedtask.setSolved(true);
    }

    private void checkValidateRate(Predicate<BigDecimal> condition, BigDecimal rate) {
        if (condition.test(rate)) {
            return;
        }
        throw new RuntimeException(String.format("Неподходящая ставка, ставка должна быть в диапазоне %s - %s.",
                MIN_RATE, MAX_RATE));
    }
}
