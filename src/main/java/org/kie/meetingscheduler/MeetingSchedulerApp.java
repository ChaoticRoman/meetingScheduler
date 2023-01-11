package org.kie.meetingscheduler;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.kie.meetingscheduler.domain.*;
import org.kie.meetingscheduler.solver.MeetingScheduleConstraintProvider;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@QuarkusMain
public class MeetingSchedulerApp implements QuarkusApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeetingSchedulerApp.class);

    private final static String format = "%-15s";
    @Override
    public int run(String... args) throws Exception {

        List<TimeGrain> avalibleTimeGrain =
                getTimeGrain(LocalTime.of(0,0,0), LocalTime.of(4,59,59), new Day(0, LocalDate.now().getDayOfYear()));

        Room sameRoom = new Room(0L, "Room A", 10L);
        Room otherRoom = new Room(1L, "Room B", 10L);
        Meeting first = new Meeting(0L, "Java", "Speaker", "Required",
                "Preferred", 3L, sameRoom, avalibleTimeGrain.get(0));
        Meeting second = new Meeting(1L, "C#", "Speaker", "Required",
                "Preferred", 5L, sameRoom, avalibleTimeGrain.get(0));

        List<Meeting> meetingList = new ArrayList<>();
        meetingList.add(first);
        meetingList.add(second);

        MeetingSchedule problem = new MeetingSchedule();
        problem.setMeetingList(meetingList);
        problem.setRoomList(List.of(sameRoom, otherRoom));
        problem.setTimeGainList(avalibleTimeGrain);

        //for a while I want to use java api to manage solver
        SolverFactory<MeetingSchedule> solverFactory = SolverFactory.create(new SolverConfig()
                .withSolutionClass(MeetingSchedule.class)
                .withEntityClasses(Meeting.class)
                .withConstraintProviderClass(MeetingScheduleConstraintProvider.class)
                .withTerminationSpentLimit(Duration.ofSeconds(5)));

//        Solver<MeetingSchedule> solver = solverFactory.buildSolver();
//        MeetingSchedule solution = solver.solve(problem);

        printTimetable(problem);

        return 0;
    }

    private static List<TimeGrain> getTimeGrain(LocalTime startTime, LocalTime endTime, Day day) {
        int startMinuteOfDay = startTime.getHour() * 60 + startTime.getMinute();
        int endMinuteOfDay = endTime.getHour() * 60 + endTime.getMinute();
        List<TimeGrain> timeGrainList = new ArrayList<>();
        for (Long i = 0l; (endMinuteOfDay - startMinuteOfDay) > i * TimeGrain.GRAIN_LENGTH_IN_MINUTES; i++) {
            Long timeGrainStartingMinuteOfDay = i * TimeGrain.GRAIN_LENGTH_IN_MINUTES + startMinuteOfDay;
            TimeGrain timeGrain = new TimeGrain(i, day, timeGrainStartingMinuteOfDay);
            timeGrainList.add(timeGrain);
        }
        return timeGrainList;
    }

    private static void printTimetable(MeetingSchedule meetingSchedule) {
        LOGGER.info("");
        List<Room> roomList = meetingSchedule.getRoomList();
        List<TimeGrain> timeGrains = meetingSchedule.getTimeGainList();
        List<Meeting> initializedMeetings = meetingSchedule.getMeetingList().stream().filter(meeting -> meeting.getStart() != null
                && meeting.getRoom() != null).collect(Collectors.toList());
        LOGGER.info("|               |" + roomList.stream()
                .map(room -> String.format(format, room.getName())).collect(Collectors.joining("|")));

        for (TimeGrain tg : timeGrains) {
            StringBuilder row = new StringBuilder("|" + String.format(format, tg));

            List<Meeting> sheculed = initializedMeetings.stream().filter(m -> tg.getGrainIndex() < m.getStart().getGrainIndex() + m.getDuration()
                    && tg.getGrainIndex() >= m.getStart().getGrainIndex()).collect(Collectors.toList());

                for (Room room : roomList) {
                    List<Meeting> roomedMeeting = sheculed.stream().filter(s -> s.getRoom() == room).collect(Collectors.toList());
                    if (roomedMeeting.size()!=0) {
                        row.append("|").append(String.format(format, roomedMeeting.stream().map(Meeting::getTopic).collect(Collectors.joining())));
                    } else {
                        row.append("|").append(String.format(format, "#######"));
                    }
                }
            LOGGER.info(row.toString());
        }


            List<Meeting> unassignedMeetings = meetingSchedule.getMeetingList().stream()
                    .filter(lesson -> lesson.getStart() == null || lesson.getRoom() == null)
                    .collect(Collectors.toList());
            if (!unassignedMeetings.isEmpty()) {
                LOGGER.info("");
                LOGGER.info("Unassigned lessons");
                for (Meeting lesson : unassignedMeetings) {
                    LOGGER.info("  " + lesson.getTopic() + " - " + lesson.getSpeaker() + " - " + lesson.getPreffered());
                }
            }
        }
    }
