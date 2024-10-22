package org.javaacademy.citizen;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.javaacademy.Human;

import static org.javaacademy.citizen.MaritalStatus.SINGLE;

@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Citizen extends Human {
    MaritalStatus maritalStatus;

    Citizen spouse;

    public Citizen(@NonNull String name,
                   @NonNull String surname,
                   @NonNull String patronymic,
                   boolean isMale) {
        super(name, surname, patronymic, isMale);
        this.maritalStatus = SINGLE;
    }
}
