package org.javaacademy;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@ToString(onlyExplicitlyIncluded = true)
public class Human {
    @NonNull
    @ToString.Include
    final String firstName;
    @NonNull
    @ToString.Include
    final String middleName;
    @NonNull
    @ToString.Include
    final String lastName;
    final boolean isMale;
    Human father;
    Human mother;
    final Set<Human> children = new HashSet<>(){};

    @Builder
    public Human(@NonNull String firstName, @NonNull String middleName, @NonNull String lastName, boolean isMale) {
        this.firstName = StringUtils.capitalize(firstName.toLowerCase());
        this.middleName = StringUtils.capitalize(middleName.toLowerCase());
        this.lastName = StringUtils.capitalize(lastName.toLowerCase());
        this.isMale = isMale;
    }

    public Human makeChild(String firstName, String middleName, String lastName, boolean isMale, Human parent2) {
        genderCheck(this, parent2);
        Human newHuman = Human.builder()
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .isMale(isMale)
                .build();
        newHuman.setParents(this, parent2);
        return newHuman;
    }

    private void setParents(Human parent1, Human parent2) {
        genderCheck(parent1, parent2);
        if (parent1.isMale) {
            this.father = parent1;
            this.mother = parent2;
        } else {
            this.father = parent2;
            this.mother = parent1;
        }
        addChildToParents(father, mother,this);
    }

    private static void addChildToParents(Human parent1, Human parent2, Human child) {
        parent1.children.add(child);
        parent2.children.add(child);
    }

    public static void genderCheck(Human human1, Human human2) {
        if (human1.isMale == human2.isMale) {
            throw new RuntimeException("Родителями не могут быть однополые люди.");
        }
    }

    public String getFullName() {
        return String.format("Имя Отчество Фамилия: %s %s %s", firstName, middleName, lastName);
    }
}
