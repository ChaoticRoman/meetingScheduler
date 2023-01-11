package org.kie.meetingscheduler.domain;

import java.time.LocalDateTime;
import java.util.Comparator;

public class TimeGrain implements Comparable<TimeGrain>{

    private static final Comparator<TimeGrain> COMPARATOR = Comparator.comparing(TimeGrain::getDay)
            .thenComparingLong(TimeGrain::getStartingMinuteOfDay);
    public static final int GRAIN_LENGTH_IN_MINUTES = 15;
    Day day;
    private Long grainIndex;
    private Long startingMinuteOfDay;

    public TimeGrain(Long grainIndex, Day day, Long startingMinuteOfDay) {
        this.grainIndex = grainIndex;
        this.startingMinuteOfDay = startingMinuteOfDay;
        this.day=day;
    }

    public Long getGrainIndex() {
        return grainIndex;
    }

    public void setGrainIndex(Long grainIndex) {
        this.grainIndex = grainIndex;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Long getStartingMinuteOfDay() {
        return startingMinuteOfDay;
    }

    public void setStartingMinuteOfDay(Long startingMinuteOfDay) {
        this.startingMinuteOfDay = startingMinuteOfDay;
    }

    @Override
    public int compareTo(TimeGrain other) {
        return COMPARATOR.compare(this, other);
    }

    @Override
    public String toString() {
        return grainIndex + "(" + getDateTimeString() + ")";
    }

    public String getDateTimeString() {
        return day.getDateString() + " " + getTimeString();
    }

    public String getTimeString() {
        Long hourOfDay = startingMinuteOfDay / 60;
        Long minuteOfHour = startingMinuteOfDay % 60;
        return (hourOfDay < 10 ? "0" : "") + hourOfDay
                + ":" + (minuteOfHour < 10 ? "0" : "") + minuteOfHour;
    }

}
