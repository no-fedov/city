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
    final String companyName;
    Manager manager;
    final Set<Programmer> programmers;
    MultiValuedMap<Employee, Task> doneTasks = new HashSetValuedHashMap<>();
    Map<Employee, Duration> timeSheet = new HashMap<>();
    BigDecimal expenses;

    public Company(String companyName,
                   Manager manager,
                   Set<Programmer> programmers,
                   BigDecimal expenses,
                   BigDecimal programmerRate) {
        this.companyName = companyName;
        this.manager = manager;
        this.programmers = programmers;
        this.expenses = expenses;

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
            timeSheet.put(manager, (weekTasks.get(i).getHours().plus(timeSheet.get(key))));
//            у менеджера требуется умножение на коэффициент 0.1. Не знаю как сделать.
//            Возможно стоит изменить duration на int и не париться. На работе никто минуты не считает,
//            только часы
        }
    }

    private static void tasksDescription(Task doneTask) {
        System.out.printf("[%s] - сделана.", doneTask.getSpecification());
    }
}