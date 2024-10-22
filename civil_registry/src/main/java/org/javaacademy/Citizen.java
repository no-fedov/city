package org.javaacademy;

import lombok.*;
import lombok.experimental.FieldDefaults;

import static org.javaacademy.MaritalStatus.SINGLE;

@ToString
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Citizen extends Human {
    MaritalStatus maritalStatus;
    Citizen spouse;

    public Citizen(@NonNull String name,
                   @NonNull String surname,
                   @NonNull String patronymic,
                   boolean isMale,
                   Citizen spouse) {
        super(name, surname, patronymic, isMale);
        this.maritalStatus = SINGLE;
    }
}
