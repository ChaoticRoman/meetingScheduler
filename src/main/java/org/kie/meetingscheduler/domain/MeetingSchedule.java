package org.kie.meetingscheduler.domain;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

@PlanningSolution
public class MeetingSchedule {

    @PlanningEntityCollectionProperty
    private List<Meeting> meetingList;
    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "roomRange")
    private List<Room> roomList;
    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "timeGainRange")
    private List<TimeGrain> timeGrainList;
    @PlanningScore
    private HardSoftScore score;

    public MeetingSchedule() {
    }

    public MeetingSchedule(List<Meeting> meetingList, List<Room> roomList, List<TimeGrain> timeGrainList) {
        this.meetingList = meetingList;
        this.roomList = roomList;
        this.timeGrainList = timeGrainList;
    }

    public List<Meeting> getMeetingList() {
        return meetingList;
    }

    public void setMeetingList(List<Meeting> meetingList) {
        this.meetingList = meetingList;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public List<TimeGrain> getTimeGainList() {
        return timeGrainList;
    }

    public void setTimeGainList(List<TimeGrain> timeGrainList) {
        this.timeGrainList = timeGrainList;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }
}
