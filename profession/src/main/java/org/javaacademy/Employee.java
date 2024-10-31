package org.javaacademy;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Employee extends Human {
    protected BigDecimal rate;

    protected BigDecimal earnedMoney = BigDecimal.ZERO;

    public Employee(@NonNull String name,
                    @NonNull String surname,
                    @NonNull String patronymic,
                    boolean isMale) {
        super(name, surname, patronymic, isMale);
    }

    public void receiveMoney(BigDecimal money) {
        earnedMoney = earnedMoney.add(money);
    }
}
