package com.pik.backend.services;

import com.pik.ride2work.tables.pojos.Membership;
import java.util.concurrent.Future;

public interface MembershipService {

  Future<Membership> joinTeam(Integer userId, Integer teamId);

  Future<Void> leaveTeam(Integer userId);

}
