package org.kie.meetingscheduler.solver;

import org.kie.meetingscheduler.domain.Meeting;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

import static org.optaplanner.core.api.score.stream.Joiners.equal;
import static org.optaplanner.core.api.score.stream.Joiners.overlapping;


public class MeetingScheduleConstraintProvider implements ConstraintProvider {


    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                roomConflict(constraintFactory),
        };
    }

    private Constraint roomConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEachUniquePair(Meeting.class,
                        equal(Meeting::getRoom),
                        overlapping(assignment -> assignment.getStart().getGrainIndex(),
                                assignment -> assignment.getStart().getGrainIndex() + assignment.getDuration())
                )
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Room conflict");
    }
}
