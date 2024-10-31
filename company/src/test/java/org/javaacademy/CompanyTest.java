package org.javaacademy;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompanyTest {
    private ByteArrayOutputStream outputStream;
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setup() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    @SneakyThrows
    void tearDown() {
        System.setOut(originalOut);
        outputStream.close();
    }

    @Test
    @DisplayName("Успешное выполнение задач")
    public void doneTaskSuccess() {
        String companyName = "Компания";
        BigDecimal programmerRate = BigDecimal.valueOf(1_700);
        Programmer prog1 = new Programmer("Александр", "Нож", "Иванович", true);

        Manager manager = new Manager("Василий", "Алексеевич", "Баранов", true);

        Set<Programmer> programmers = new HashSet<>(List.of(prog1));
        Company company = new Company(companyName, manager, programmers, programmerRate);

        Task task1 = new Task("Создать модули", 2);
        Task task2 = new Task("Написать код", 6);
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

        Task task1 = new Task("Создать модули", 2);
        Task task2 = new Task("Написать код", 6);
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

        Task task1 = new Task("Создать модули", 1);
        List<Task> tasks = new ArrayList<>(List.of(task1));

        company.weekWork(tasks);
        company.payForWeekWork();

        BigDecimal expected = BigDecimal.valueOf(2_700).setScale(2);
        BigDecimal result = company.getExpenses().setScale(2);

        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Получение статистики компании")
    public void getStatistic() {
        Company company = new Company("Oracle",
                new Manager("1", "2", "3", false),
                Set.of(),
                BigDecimal.valueOf(2000));
        company.info();
        Assertions.assertEquals("[Oracle]\nЗатраты: [0.00]\nСписок выполненных задач у компании:",
                outputStream.toString().trim());
    }
}
