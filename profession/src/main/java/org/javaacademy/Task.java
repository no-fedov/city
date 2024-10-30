package org.javaacademy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Task {
    String specification;
    boolean isSolved;
    int hours;

    public Task(String specification, int hours) {
        this.specification = specification;
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "Задача: " + specification;
    }
}
