package ru.javawebinar.topjava.web;

import java.time.LocalDate;
import java.time.LocalTime;

public class FilterDateAndTimeValuesStorage {
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private LocalTime timeStart;
    private LocalTime timeEnd;

    public void saveValues(LocalDate dStart, LocalDate dEnd, LocalTime tStart, LocalTime tEnd) {
        dateStart = dStart;
        dateEnd = dEnd;
        timeStart = tStart;
        timeEnd = tEnd;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void clear(){
        dateStart = null;
        dateEnd = null;
        timeStart = null;
        timeEnd = null;
    }
}
