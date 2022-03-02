package com.akebabi.backend.security.enums;

import java.util.stream.Stream;

public enum DAY_OF_WEEK {
    MONDAY(1),TUESDAY(2),WEDNESDAY(3),THURSDAY(4),FRIDAY(5),SATURDAY(6),SUNDAY(7);
    private int intDayOfWeek;

    private DAY_OF_WEEK(int intDayOfWeek) {
        this.intDayOfWeek = intDayOfWeek;
    }

    public int getIntDayOfWeek() {
        return this.intDayOfWeek;
    }

    public static DAY_OF_WEEK of(int intDayOfWeek) {
        return Stream.of(DAY_OF_WEEK.values())
                .filter(p -> p.getIntDayOfWeek() == intDayOfWeek)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
