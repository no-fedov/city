package org.javaacademy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company {
    private static final double MANAGERS_FACTOR = 0.1;
    private static final int MINUTES_IN_HOUR = 60;

    @NonNull
    final String companyName;

    @NonNull
    Manager manager;

    @NonNull
    final Set<Programmer> programmers;

    final MultiValuedMap<Employee, Task> doneTasks = new HashSetValuedHashMap<>();

    final Map<Employee, Double> timeSheet = new HashMap<>();

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

    private BigDecimal calculateSalary(Double hours, BigDecimal rate) {
        return BigDecimal.valueOf(hours).multiply(rate);
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
            tasksDescription(weekTasks.get(i));

            double allKeyTime;
            if (timeSheet.get(key) == null) {
                allKeyTime = 0;
            } else {
                allKeyTime = timeSheet.get(key);
            }

            timeSheet.put(key, (weekTasks.get(i).getHours() + allKeyTime));
        }
        calculateWorkingTimeForManager(weekTasks);
    }

    private void calculateWorkingTimeForManager(List<Task> tasks) {
        Optional<Integer> hours = tasks.stream().map(Task::getHours).reduce(Integer::sum);
        if (hours.isPresent()) {
            timeSheet.put(manager, timeSheet.getOrDefault(manager, 0d)
                    + hours.get() * MANAGERS_FACTOR);
        }
    }

    private void tasksDescription(Task doneTask) {
        System.out.printf("[%s] - сделана.\n", doneTask.getSpecification());
    }

    public void info() {
        System.out.printf("\n[%s]\nЗатраты: [%s]\n"
                        + "Список выполненных задач у компании:\n",
                this.companyName, expenses.setScale(2, RoundingMode.HALF_UP));

        for (Programmer worker : programmers) {
            System.out.printf("%s - %s\n", worker.getFullName(), doneTasks.get(worker).toString());
        }
    }
}
