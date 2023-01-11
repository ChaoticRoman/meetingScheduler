package org.kie.meetingscheduler.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Meeting {
    @PlanningId
    private Long id;
    private String topic;
    private String speaker;
    private String required;
    private String preffered;
    private Long duration;
    @PlanningVariable(valueRangeProviderRefs = "roomRange")
    private Room room;
    @PlanningVariable(valueRangeProviderRefs = "timeGainRange")
    private TimeGrain start;

    public Meeting() {
    }

    public Meeting(Long id, String topic, String speaker, String required, String preffered, Long duration, Room room, TimeGrain start) {
        this.id = id;
        this.topic = topic;
        this.speaker = speaker;
        this.required = required;
        this.preffered = preffered;
        this.duration = duration;
        this.room = room;
        this.start = start;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getPreffered() {
        return preffered;
    }

    public void setPreffered(String preffered) {
        this.preffered = preffered;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public TimeGrain getStart() {
        return start;
    }

    public void setStart(TimeGrain start) {
        this.start = start;
    }
}
