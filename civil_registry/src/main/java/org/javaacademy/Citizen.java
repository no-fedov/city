package org.javaacademy;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
public class Citizen extends Human {
    @Setter
    private MaritalStatus maritalStatus;
    private String spouseName;

    public Citizen(@NonNull String firstName, @NonNull String middleName, @NonNull String lastName,
                   boolean isMale, MaritalStatus maritalStatus) {
        super(firstName, middleName, lastName, isMale);
        this.maritalStatus = maritalStatus;
        this.spouseName = null;
    }

    public Citizen(@NonNull String firstName, @NonNull String middleName, @NonNull String lastName,
                   boolean isMale, MaritalStatus maritalStatus, String spouseName) {
        super(firstName, middleName, lastName, isMale);
        this.maritalStatus = maritalStatus;
        this.spouseName = spouseName;
    }

    public void setSpouseName(String spouseName) {
        if (maritalStatus == MaritalStatus.MARRIED) {
            this.spouseName = spouseName;
        } else {
            throw new IllegalArgumentException("Невозможно установить имя супруга(и).");
        }
    }

    @Override
    public String toString() {
        return "Citizen{" +
                ", maritalStatus=" + maritalStatus +
                (maritalStatus == MaritalStatus.MARRIED ?
                        ", spouseName='" + spouseName + '\'' : "") + '}';

    }
}
