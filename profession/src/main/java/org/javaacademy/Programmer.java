package org.javaacademy;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.function.Predicate;

@Getter
public class Programmer extends Employee {
    private Task task;
    private static final BigDecimal MIN_RATE = BigDecimal.valueOf(1500);
    private static final BigDecimal MAX_RATE = BigDecimal.valueOf(2000);

    public Programmer(@NonNull String name,
                      @NonNull String surname,
                      @NonNull String patronymic,
                      boolean isMale) {
        super(name, surname, patronymic, isMale);
    }

//    public void setRate(BigDecimal rate) {
//        if (checkRate(rate)) {
//            throw new RuntimeException();
//        } else {
//            this.rate = rate;
//        }
//    }
//
//    private boolean checkRate(BigDecimal rate) {
//        int flag = 0;
//        flag = rate.min(MIN_RATE).equals(rate) ? flag += 1 : flag;
//        flag = rate.max(MAX_RATE).equals(rate) ? flag += 1 : flag;
//        if (flag > 0) {
//            return false;
//        }
//        return true;
//    }
    public void setRate(BigDecimal rate) {
        Predicate<BigDecimal> check = (BigDecimal chRate) -> chRate.min(MIN_RATE).equals(chRate) || chRate.max(MAX_RATE).equals(chRate);

        if(checkRate(check, rate)) {
            this.rate = rate;
        }
        else {
            throw new RuntimeException();
        }
    }
    private boolean checkRate(Predicate<BigDecimal> checking, BigDecimal rate) {
        if(!checking.test(rate)) {
            return false;
        }
        else {
            return true;
        }
    }


    private void takeTask(Task receivedtask) {
        if (receivedtask.isSolved() == false) {
            this.task = receivedtask;
            task.setSolved(true);
        }
    }

}
