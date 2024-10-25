package org.javaacademy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company {
    @NonNull
    final String companyName;

    @NonNull
    Manager manager;

    @NonNull
    final Set<Programmer> programmers;

    final MultiValuedMap<Employee, Task> doneTasks = new HashSetValuedHashMap<>();

    final Map<Employee, Duration> timeSheet = new HashMap<>();

    BigDecimal expenses = BigDecimal.ZERO;

    public Company(@NonNull String companyName,
                   @NonNull Manager manager,
                   @NonNull Set<Programmer> programmers,
                   @NonNull BigDecimal programmerRate) {
        this.companyName = companyName;
        this.manager = manager;
        this.programmers = programmers;
        setRateForProgrammers(programmers, programmerRate);
    }

    private void setRateForProgrammers(Set<Programmer> programmers, BigDecimal rate) {
        for (Programmer programmer : programmers) {
            programmer.setRate(rate);
        }
    }
}