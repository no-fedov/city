package org.javaacademy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

import static org.javaacademy.HumanUtil.genderOppositeCheck;

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

    public Human(String firstName, String middleName, String lastName, boolean isMale) {
        this.firstName = StringUtils.capitalize(firstName.toLowerCase());
        this.middleName = StringUtils.capitalize(middleName.toLowerCase());
        this.lastName = StringUtils.capitalize(lastName.toLowerCase());
        this.isMale = isMale;
    }

    @NonNull
    public Human makeChild(String firstName, String middleName, String lastName, boolean isMale,
                           Human parent2) {
        genderOppositeCheck(this, parent2);
        Human newHuman = new Human(firstName, middleName, lastName, isMale);
        newHuman.setParents(this, parent2);
        return newHuman;
    }

    private void setParents(Human parent1, Human parent2) {
        genderOppositeCheck(this, parent2);
        if (parent1.isMale) {
            this.father = parent1;
            this.mother = parent2;
        } else {
            this.father = parent2;
            this.mother = parent1;
        }
        addChildToParents(father, mother, this);
    }

    private static void addChildToParents(Human parent1, Human parent2, Human child) {
        parent1.children.add(child);
        parent2.children.add(child);
    }

    public String getFullName() {
        return String.format("ФИО: %s %s %s", lastName, firstName, middleName);
    }
}
