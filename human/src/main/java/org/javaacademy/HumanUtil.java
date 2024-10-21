package org.javaacademy;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HumanUtil {
    @NonNull
    public void genderOppositeCheck(Human human1, Human human2) {
        if (human1.isMale() == human2.isMale()) {
            throw new RuntimeException("Родителями не могут быть однополые люди.");
        }
    }
}