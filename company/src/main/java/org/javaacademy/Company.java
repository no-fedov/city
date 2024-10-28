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
import java.util.*;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company {
    static final double MANAGERS_FACTOR = 0.1;

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

    public void payForWeekWork() {
        timeSheet.forEach((employee, hours) -> {
            BigDecimal salary = calculateSalary(hours, employee.getRate());
            paySalaryForEmployee(employee, salary);
            this.expenses = expenses.add(salary);
        });
        timeSheet.clear();
    }

    private void paySalaryForEmployee(Employee employee, BigDecimal salary) {
        employee.receiveMoney(salary);
    }

    private BigDecimal calculateSalary(Duration hours, BigDecimal rate) {
        return BigDecimal.valueOf(hours.toHours()).multiply(rate);
    }

    private void setRateForProgrammers(Set<Programmer> programmers, BigDecimal programmerRate) {
        for (Programmer programmer : programmers) {
            programmer.setRate(programmerRate);
        }
    }

    public void weekWork(@NonNull List<Task> weekTasks) {
        ArrayList<Programmer> programmersToList = new ArrayList<>(programmers);
        int numKeys = programmers.size();
        int numValues = weekTasks.size();

        for (int i = 0; i < numValues; i++) {
            Programmer key = programmersToList.get(i % numKeys);
            doneTasks.put(key, weekTasks.get(i));
            System.out.printf("[%s] - сделана.", weekTasks.get(i).getSpecification());

            timeSheet.put(key, (weekTasks.get(i).getHours().plus(timeSheet.get(key))));

            long nanosTask = (weekTasks.get(i).getHours().toNanos());
            long resultNanos = (long) (nanosTask * MANAGERS_FACTOR);
            Duration resultDuration = Duration.ofNanos(resultNanos);
            timeSheet.put(manager, (resultDuration.plus(timeSheet.get(key))));
        }
    }

    private static void tasksDescription(Task doneTask) {
        System.out.printf("[%s] - сделана.", doneTask.getSpecification());
    }

    public void info() {
        System.out.printf("""
                [%s]
                Затраты: [%s]
                Список выполненных задач у компании:
                [ФИО программиста] - [список задач]
                [ФИО программиста] - [список задач]
                """,
                this.companyName, expenses.setScale(2));
        for (Programmer e : programmers) {
            System.out.printf("%s - [%s]\n", e.getFullName(), doneTasks.get(e).toString());
        }
    }
}