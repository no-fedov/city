import org.javaacademy.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class CompanyTest {

    @Test
    @DisplayName("Успешное выполнение задач")
    public void doneTaskSuccess() {
        String companyName = "Компания";
        BigDecimal programmerRate = BigDecimal.valueOf(1_700);
        Programmer prog1 = new Programmer("Александр", "Нож", "Иванович", true);

        Manager manager = new Manager("Василий", "Алексеевич", "Баранов", true);

        Set<Programmer> programmers = new HashSet<>(List.of(prog1));
        Company company = new Company(companyName, manager, programmers, programmerRate);

        Task task1 = new Task("Создать модули", Duration.ofHours(2));
        Task task2 = new Task("Написать код", Duration.ofHours(6));
        List<Task> tasks = new ArrayList<>(List.of(task1, task2));

        company.weekWork(tasks);
        int expected = 2;
        int result = company.getDoneTasks().size();
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Успешное выполнение рабочей недели")
    public void weekWorkSuccess() {
        String companyName = "Компания";
        BigDecimal programmerRate = BigDecimal.valueOf(1_700);
        Programmer prog1 = new Programmer("Александр", "Нож", "Иванович", true);

        Manager manager = new Manager("Василий", "Алексеевич", "Баранов", true);

        Set<Programmer> programmers = new HashSet<>(List.of(prog1));
        Company company = new Company(companyName, manager, programmers, programmerRate);

        Task task1 = new Task("Создать модули", Duration.ofHours(2));
        Task task2 = new Task("Написать код", Duration.ofHours(6));
        List<Task> tasks = new ArrayList<>(List.of(task1, task2));

        company.weekWork(tasks);
        int expected = 2;
        int result = company.getTimeSheet().size();
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Удачная оплата работы")
    public void paySuccess() {
        String companyName = "Компания";
        BigDecimal programmerRate = BigDecimal.valueOf(1_700);
        Programmer prog1 = new Programmer("Александр", "Нож", "Иванович", true);

        Manager manager = new Manager("Василий", "Алексеевич", "Баранов", true);

        Set<Programmer> programmers = new HashSet<>(List.of(prog1));
        Company company = new Company(companyName, manager, programmers, programmerRate);

        Task task1 = new Task("Создать модули", Duration.ofHours(1));
        List<Task> tasks = new ArrayList<>(List.of(task1));

        company.weekWork(tasks);
        company.payForWeekWork();

        BigDecimal expected = BigDecimal.valueOf(2_700);
        BigDecimal result = company.getExpenses();

        Assertions.assertEquals(expected, result);
    }
}
