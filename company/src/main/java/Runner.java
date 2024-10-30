import org.javaacademy.Company;
import org.javaacademy.Manager;
import org.javaacademy.Programmer;
import org.javaacademy.Task;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Runner {
    private static final int TIME_FOR_TASK = 2;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Аргументы не переданы.");
            return;
        }

        String companyName = args[0];
        BigDecimal programmerRate = new BigDecimal(args[1]);

        Programmer prog1 = new Programmer("Александр", "Нож", "Иванович", true);
        Programmer prog2 = new Programmer("Анна", "Кот", "Олеговна", false);

        Manager manager = new Manager("Василий", "Алексеевич", "Баранов", true);

        Set<Programmer> programmers = new HashSet<>(List.of(prog1, prog2));
        Company oracle = new Company(companyName, manager, programmers, programmerRate);

        Task task1 = new Task("Создать модули", TIME_FOR_TASK);
        Task task2 = new Task("Написать код", TIME_FOR_TASK);
        Task task3 = new Task("Собрать программу", TIME_FOR_TASK);
        List<Task> tasks = List.of(task1, task2, task3);

        oracle.weekWork(tasks);
        oracle.payForWeekWork();
        oracle.info();
    }
}
