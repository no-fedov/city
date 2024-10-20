package org.javaacademy;

public class Main {
    public static void main(String[] args) {
        Human one = Human.builder()
                .firstName("АННа")
                .middleName("Ивановна")
                .lastName("Шпицкен")
                .isMale(false)
                .build();
        System.out.println(one);

        Human two = Human.builder()
                .firstName("АННа")
                .middleName("Ивановна")
                .lastName("Шпицкен")
                .isMale(false)
                .build();
        System.out.println(two);

        one.makeChild("Павел", "Андреевич", "Коневой", two);
    }
}