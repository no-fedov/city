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

    @ToString.Exclude
    Citizen spouse;

    public Citizen(@NonNull String name,
                   @NonNull String surname,
                   @NonNull String patronymic,
                   boolean isMale) {
        super(name, surname, patronymic, isMale);
        this.maritalStatus = SINGLE;
    }

    private Citizen(@NonNull Human human) {
        super(human.getName(),
                human.getSurname(),
                human.getPatronymic(),
                human.isMale());
        this.setParents(human.getMother(), human.getFather());
    }

    @Override
    public Citizen makeChild(@NonNull String name,
                             @NonNull String surname,
                             @NonNull String patronymic,
                             boolean isMale,
                             @NonNull Human otherParent) {
        Human child = super.makeChild(name, surname, patronymic, isMale, otherParent);
        return new Citizen(child);
    }
}
