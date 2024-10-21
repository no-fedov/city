package org.javaacademy;

public class Main {
    public static void main(String[] args) {
        Human one = Human.builder()
                .firstName("АННа")
                .middleName("ИваНовна")
                .lastName("шпицкен")
                .isMale(false)
                .build();

        Human two = Human.builder()
                .firstName("Андрей")
                .middleName("Федорович")
                .lastName("Коневой")
                .isMale(true)
                .build();

        Human pavel = one.makeChild("Павел", "Андреевич", "Коневой", false,
                null);
        System.out.println("Создали людей:");
        System.out.println(one);
        System.out.println(two);
        System.out.println();
        System.out.println("Создали ребенка");
        System.out.println(pavel);
        System.out.println();
        System.out.println("Дети Анны: " + one.getChildren().toString());
        System.out.println("Дети Андрея: " + one.getChildren().toString());
        System.out.printf("Отец Павла - %s,\nмать -  %s\n", pavel.getFather(), pavel.getMother());

        System.out.println(pavel.getFullName());

    }
}