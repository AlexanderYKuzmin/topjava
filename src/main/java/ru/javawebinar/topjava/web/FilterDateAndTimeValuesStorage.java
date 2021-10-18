package ru.javawebinar.topjava.web;

import java.time.LocalDate;
import java.time.LocalTime;

public class FilterDateAndTimeValuesStorage {
    private static LocalDate dateStart;
    private static LocalDate dateEnd;
    private static LocalTime timeStart;
    private static LocalTime timeEnd;

    public static void saveValues(LocalDate dStart, LocalDate dEnd, LocalTime tStart, LocalTime tEnd) {
        dateStart = dStart;
        dateEnd = dEnd;
        timeStart = tStart;
        timeEnd = tEnd;
    }

    public static LocalDate getDateStart() {
        return dateStart;
    }

    public static LocalDate getDateEnd() {
        return dateEnd;
    }

    public static LocalTime getTimeStart() {
        return timeStart;
    }

    public static LocalTime getTimeEnd() {
        return timeEnd;
    }

    public static void clear(){
        dateStart = null;
        dateEnd = null;
        timeStart = null;
        timeEnd = null;
    }
}
