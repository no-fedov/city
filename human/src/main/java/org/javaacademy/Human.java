package org.javaacademy;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.javaacademy.HumanUtil.genderOppositeCheck;

@Getter
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(onlyExplicitlyIncluded = true)
public class Human {
    @NonNull
    @ToString.Include
    final String name;

    @NonNull
    @ToString.Include
    final String surname;

    @NonNull
    @ToString.Include
    final String patronymic;

    final boolean isMale;

    Human father;

    Human mother;

    @Builder.Default
    List<Human> children = new ArrayList<>();

    public Human(@NonNull String name,
                 @NonNull String surname,
                 @NonNull String patronymic,
                 boolean isMale) {
        this.name = StringUtils.capitalize(name.toLowerCase());
        this.surname = StringUtils.capitalize(surname.toLowerCase());
        this.patronymic = StringUtils.capitalize(patronymic.toLowerCase());
        this.isMale = isMale;
    }

    private Human(String name,
                  String surname,
                  String patronymic,
                  boolean isMale,
                  Human father,
                  Human mother,
                  List<Human> children) {
        this(name, surname, patronymic, isMale);
        this.father = father;
        this.mother = mother;
        this.children = children;
    }

    @NonNull
    public void setParents(Human parent1, Human parent2) {
        genderOppositeCheck(parent1, parent2);
        if (parent1.isMale) {
            this.father = parent1;
            this.mother = parent2;
        } else {
            this.father = parent2;
            this.mother = parent1;
        }
        addChildToParents(father, mother, this);
    }

    @NonNull
    public Human makeChild(String name,
                           String surname,
                           String patronymic,
                           boolean isMale,
                           Human otherParent) {
        genderOppositeCheck(this, otherParent);
        Human newHuman = Human.builder()
                .name(name)
                .surname(surname)
                .patronymic(patronymic)
                .isMale(isMale)
                .build();
        newHuman.setParents(this, otherParent);
        return newHuman;
    }

    public String getFullName() {
        return String.format("ФИО: %s %s %s", surname, name, patronymic);
    }

    private void addChildToParents(Human parent1,
                                   Human parent2,
                                   Human child) {
        parent1.children.add(child);
        parent2.children.add(child);
    }
}