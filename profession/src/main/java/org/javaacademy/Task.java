package org.javaacademy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Duration;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Task {
    String specification;
    boolean isSolved;
    Duration hours;

    @Override
    public String toString() {
        return "Задача: " + specification;
    }
}