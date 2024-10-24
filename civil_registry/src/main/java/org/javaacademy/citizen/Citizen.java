package org.javaacademy.citizen;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.javaacademy.Human;

import static org.javaacademy.HumanUtil.*;
import static org.javaacademy.citizen.MaritalStatus.SINGLE;

@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Citizen extends Human {
    @NonNull
    MaritalStatus maritalStatus;

    @ToString.Exclude
    Citizen spouse;

    public Citizen(@NonNull String name,
                   @NonNull String surname,
                   @NonNull String patronymic,
                   boolean isMale) {
        super(name, surname, patronymic, isMale);
        this.maritalStatus = SINGLE;
    }

    @Override
    public Citizen makeChild(@NonNull String name,
                             @NonNull String surname,
                             @NonNull String patronymic,
                             boolean isMale,
                             @NonNull Human otherParent) {
        genderOppositeCheck(this, otherParent);
        Citizen childCitizen = new Citizen(name, surname, patronymic, isMale);
        childCitizen.setParents(this, otherParent);
        return childCitizen;
    }
}
