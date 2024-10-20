package org.javaacademy;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HumanUtil {
    public static void genderOppositeCheck(Human human1, Human human2) {
        if (human1.isMale() == human2.isMale()) {
            throw new RuntimeException("Родителями не могут быть однополые люди.");
        }
    }
}