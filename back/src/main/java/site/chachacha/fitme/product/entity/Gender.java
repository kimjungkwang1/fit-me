package site.chachacha.fitme.product.entity;

import lombok.Getter;
import site.chachacha.fitme.product.exception.GenderBadRequestException;

@Getter
public enum Gender {
    MALE(0), FEMALE(1), UNISEX(2);

    private final int value;

    Gender(int value) {
        this.value = value;
    }

    public static Gender fromValue(int value) {
        for (Gender gender : Gender.values()) {
            if (gender.getValue() == value) {
                return gender;
            }
        }
        throw new GenderBadRequestException();
    }
}
