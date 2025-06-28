package dev.application.common.mapping;

import java.util.function.Function;

import dev.application.entity.ThresHoldEntity;

public class FieldMapping {
    private final int index;
    private final Function<ThresHoldEntity, String> getter;

    public FieldMapping(int index, Function<ThresHoldEntity, String> getter) {
        this.index = index;
        this.getter = getter;
    }

    public int getIndex() {
        return index;
    }

    public Function<ThresHoldEntity, String> getGetter() {
        return getter;
    }
}
