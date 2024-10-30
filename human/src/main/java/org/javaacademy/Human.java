package org.javaacademy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
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

    final List<Human> children = new ArrayList<>();

    public Human(@NonNull String name,
                 @NonNull String surname,
                 @NonNull String patronymic,
                 boolean isMale) {
        this.name = StringUtils.capitalize(name.toLowerCase());
        this.surname = StringUtils.capitalize(surname.toLowerCase());
        this.patronymic = StringUtils.capitalize(patronymic.toLowerCase());
        this.isMale = isMale;
    }

    public void setParents(@NonNull Human parent1,
                           @NonNull Human parent2) {
        HumanUtil.genderOppositeCheck(parent1, parent2);
        if (parent1.isMale) {
            this.father = parent1;
            this.mother = parent2;
        } else {
            this.father = parent2;
            this.mother = parent1;
        }
        addChildToParents(father, mother, this);
    }

    public Human makeChild(@NonNull String name,
                           @NonNull String surname,
                           @NonNull String patronymic,
                           boolean isMale,
                           @NonNull Human otherParent) {
        HumanUtil.genderOppositeCheck(this, otherParent);
        Human newHuman = new Human(name, surname, patronymic, isMale);
        newHuman.setParents(this, otherParent);
        return newHuman;
    }

    public String getFullName() {
        return String.format("ФИО: %s %s %s", surname, name, patronymic);
    }

    public List<Human> getChildren() {
        return new ArrayList<>(children);
    }

    private void addChildToParents(Human parent1,
                                   Human parent2,
                                   Human child) {
        parent1.children.add(child);
        parent2.children.add(child);
    }
}
