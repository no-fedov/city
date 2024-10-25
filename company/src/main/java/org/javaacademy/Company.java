package org.javaacademy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.MultiValuedMap;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company {
    final String companyName;
    Manager manager;
    final Set<Programmer> programmers;
    final MultiValuedMap<Employee, Task> doneTasks;
    final Map<Employee, Duration> timeSheet;
    BigDecimal expenses;

    public Company(String companyName,
                   Manager manager,
                   Set<Programmer> programmers,
                   MultiValuedMap<Employee, Task> doneTasks,
                   Map<Employee, Duration> timeSheet,
                   BigDecimal expenses,
                   BigDecimal programmerRate) {
        this.companyName = companyName;
        this.manager = manager;
        this.programmers = programmers;
        this.doneTasks = doneTasks;
        this.timeSheet = timeSheet;
        this.expenses = expenses;

        for (Programmer programmer : programmers) {
            programmer.setRate(programmerRate);
        }
    }
}