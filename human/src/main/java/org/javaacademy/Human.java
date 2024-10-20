package org.javaacademy;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
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
    List<Human> children;

    @Builder
    public Human(@NonNull String firstName, @NonNull String middleName, @NonNull String lastName, boolean isMale) {
        this.firstName = StringUtils.capitalize(firstName.toLowerCase());
        this.middleName = StringUtils.capitalize(middleName.toLowerCase());
        this.lastName = StringUtils.capitalize(lastName.toLowerCase());
        this.isMale = isMale;
    }

    public void setParents(Human parent1, Human parent2) {
        genderCheck(parent1, parent2);
        if (parent1.isMale) {
            this.father = parent1;
            this.mother = parent2;
            father.children.add(this);
            mother.children.add(this);
        }
    }

    public Human makeChild(String firstName, String middleName, String lastName, Human parent2) {
        genderCheck(this, parent2);
        Human newHuman = Human.builder()
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .build();
        newHuman.setParents(this, parent2);
        return newHuman;
    }

    public static void genderCheck(Human human1, Human human2) {
        if (human1.isMale == human2.isMale) {
            throw new RuntimeException("Родителями не могут быть однополые люди.");
        }
    }

    public String getFullName() {
        return String.format("ФИО: %s %s %s", firstName, middleName, lastName);
    }
}
