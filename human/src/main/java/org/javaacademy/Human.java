package org.javaacademy;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(onlyExplicitlyIncluded = true)
@Getter
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
    Set<Human> children;

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
            addChildToParents(father, mother,this);
        } else {
            this.father = parent2;
            this.mother = parent1;
            addChildToParents(this.father, this.mother,this);
        }
    }

    public Set<Human> getChildren() {
        if (children == null) {
            throw new RuntimeException("У человека нет детей.");
        }
        return children;
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

    public static void genderCheck(Human human1, Human human2) {
        if (human1.isMale == human2.isMale) {
            throw new RuntimeException("Родителями не могут быть однополые люди.");
        }
    }

    public static void addChildToParents(Human parent1, Human parent2, Human child) {
        if (parent1.children != null) {
            parent1.children.add(child);
        } else {
            parent1.children = new HashSet<>(List.of(child));
        }

        if (parent2.children != null) {
            parent2.children.add(child);
        } else {
            parent2.children = new HashSet<>(List.of(child));
        }

    }

    public String getFullName() {
        return String.format("Имя Отчество Фамилия: %s %s %s", firstName, middleName, lastName);
    }
}
