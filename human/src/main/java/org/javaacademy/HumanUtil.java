package org.javaacademy;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HumanUtil {
    public static void genderOppositeCheck(Human human1, Human human2) {
        if (human1.isMale() == human2.isMale()) {
            throw new RuntimeException("Отношения не могут быть между однополыми людьми.");
        }
    }
}