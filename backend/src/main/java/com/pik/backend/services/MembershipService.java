package com.pik.backend.services;

import com.pik.ride2work.tables.pojos.Membership;
import java.util.List;
import java.util.concurrent.Future;

public interface MembershipService {

    Future<Void> changeOwner(Integer userId, Integer teamId);

    Future<List<Membership>> getMembers(Integer teamId);

    Future<Membership> joinTeam(Integer userId, Integer teamId);

    Future<Void> leaveTeam(Integer userId);

    Future<Membership> getCurrentMembershipByUserId(Integer userId);
}
