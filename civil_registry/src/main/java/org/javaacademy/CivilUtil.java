package org.javaacademy;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class CivilUtil {
    public Long countType(@NonNull List<CivilActionRecord> list,
                          @NonNull TypeCivilAction type) {
        return list.stream()
                .filter(e -> e.getActionType().equals(type))
                .count();
    }
}
