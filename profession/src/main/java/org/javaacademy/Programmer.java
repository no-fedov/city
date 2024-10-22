package org.javaacademy;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Programmer extends Employee {
    private Task task;
    public Programmer(@NonNull String name,
                      @NonNull String surname,
                      @NonNull String patronymic,
                      boolean isMale) {
        super(name, surname, patronymic, isMale);
    }

    public void setRate(BigDecimal rateToCheck) {
        int flag = 0;
        flag = rateToCheck.min(BigDecimal.valueOf(1500)).equals(rateToCheck) ? flag += 1 : flag;
        flag = rateToCheck.max(BigDecimal.valueOf(2000)).equals(rateToCheck) ? flag += 1 : flag;
        if (flag > 0){
            throw new RuntimeException();
        }
    }

    private void takeTask (Task receivedtask) {
        this.task = receivedtask;
        task.setStatus(true);
    }

}
