package org.javaacademy.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.javaacademy.registry_office.CivilActionRecord;
import org.javaacademy.registry_office.TypeCivilAction;

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