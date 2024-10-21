package org.javaacademy;

import lombok.Getter;
import lombok.NonNull;

@Getter
// TODO: установить доступ к полям можно через аннотации ломбок, модуль human написан в таком стиле
public class Citizen extends Human {
    private MaritalStatus maritalStatus;
    private Citizen spouse;

    public Citizen(@NonNull String name,
                   @NonNull String surname,
                   @NonNull String patronymic,
                   boolean isMale,
                   @NonNull MaritalStatus maritalStatus,
                   Citizen spouse) {
        super(name, surname, patronymic, isMale);
        this.maritalStatus = maritalStatus;
        // TODO: сделать проверку если maritalStatus= в браке, то spouse != null
        // TODO: так же проверить что люди разных полов, если передается соответствующий статус
        this.spouse = spouse;
    }

    // TODO: продумать этот сеттор
    public void setSpouse(Citizen spouse) {

    }

    // TODO: подумать нужны ли тут дургие setters так как в загсе нужно будет людей разводить и женить

    // TODO: подумать как реализовать toString через аннотации ломбок
}
