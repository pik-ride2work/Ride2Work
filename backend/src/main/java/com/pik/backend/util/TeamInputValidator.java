package com.pik.backend.util;

import com.pik.ride2work.tables.pojos.Team;

public class TeamInputValidator implements RestInputValidator<Team> {

  private static final StringValidator stringValidator = StringValidator.getInstance();
  private static final String NAME_FORMAT_TEMPLATE = "Invalid team name format (Should be %s-%s characters long, no special chars)";
  private static final int NAME_MIN_LEN = 6;
  private static final int NAME_MAX_LEN = 32;

  @Override
  public Validated validateRegistrationInput(Team input) {
    if (input.getId() != null) {
      return Validated.invalid("Created team should not have ID!");
    }
    if (!stringValidator.lettersAndDigits(NAME_MIN_LEN, NAME_MAX_LEN, input.getName())) {
      return Validated.invalid(String.format(NAME_FORMAT_TEMPLATE, NAME_MIN_LEN, NAME_MAX_LEN));
    }
    if (input.getMemberCount() != null) {
      return Validated.invalid("Member count can't be set manually.");
    }
    return Validated.valid();
  }

  @Override
  public Validated validateUpdateInput(Team input) {
    if (input.getId() == null) {
      return Validated.invalid("Updated team must have ID!");
    }
    if(input.getName() != null && !stringValidator.lettersAndDigits(NAME_MIN_LEN, NAME_MAX_LEN, input.getName())){
      return Validated.invalid(String.format(NAME_FORMAT_TEMPLATE, NAME_MIN_LEN, NAME_MAX_LEN));
    }
    if(input.getMemberCount() != null){
      return Validated.invalid("Member count can't be set manually.");
    }
    return Validated.valid();
  }
}
