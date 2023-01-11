package org.kie.meetingscheduler.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Day implements Comparable<Day>{
    private int dayOfYear;

    public Day() {
    }

    public Day(long id, int dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    @Override
    public int compareTo(Day o) {
        return Integer.compare(this.dayOfYear, o.dayOfYear);
    }

    public int getDayOfYear() {
        return dayOfYear;
    }
    public LocalDate toDate() {
        return LocalDate.ofYearDay(LocalDate.now().getYear(), dayOfYear);
    }

    private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormatter.ofPattern("E", Locale.ENGLISH);


    public String getDateString() {
        return DAY_FORMATTER.format(toDate());
    }

}
