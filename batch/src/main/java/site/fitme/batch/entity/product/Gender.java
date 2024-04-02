package site.fitme.batch.entity.product;

import lombok.Getter;

@Getter
public enum Gender {
    MALE(0), FEMALE(1), UNISEX(2);

    private final int value;

    Gender(int value) {
        this.value = value;
    }
}
