package com.pik.backend.util;

import com.pik.ride2work.tables.pojos.Team;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

@Component
public class TeamInputValidator implements RestInputValidator<Team> {

    private static StringValidator stringValidator = StringValidator.getInstance();
    private static int TEAM_NAME_MIN_LEN = 3;
    private static int TEAM_NAME_MAX_LEN = 32;

    @Override
    public Validated validCreateInput(Team input) {
        if (input.getId() != null) {
            return Validated.invalid("Created team should not have an ID!");
        }
        if (!stringValidator.lettersOnly(TEAM_NAME_MIN_LEN, TEAM_NAME_MAX_LEN, input.getName())) {
            return Validated.invalid(String.format("Invalid team name format (Should be %s-%s characters long, English letters only.)", TEAM_NAME_MIN_LEN, TEAM_NAME_MAX_LEN));
        }
        Integer memberCount = input.getMemberCount();
        if (memberCount != null && memberCount != 1) {
            return Validated.invalid("Member count should not be set or should be equal to 1.");
        }
        return Validated.valid();
    }

    @Override
    public Validated validUpdateInput(Team input) {
        if (input.getId() == null) {
            return Validated.invalid("Updated team must have an ID!");
        }
        if (input.getName() != null && !stringValidator.lettersOnly(TEAM_NAME_MIN_LEN, TEAM_NAME_MAX_LEN, input.getName())) {
            return Validated.invalid(String.format("Invalid team name format (Should be %s-%s characters long, English letters only.)", TEAM_NAME_MIN_LEN, TEAM_NAME_MAX_LEN));
        }
        if (input.getMemberCount() != null) {
            return Validated.invalid("You can't change the number of the team members.");
        }
        return Validated.valid();
    }
}
