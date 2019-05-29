import {Time} from "@angular/common";

export class Membership {
  id: number;
  start: Time;
  end: Time;
  isPresent: boolean;
  idUser: number;
  idTeam: number;

  constructor(start, end, isPresent, idUser, idTeam) {
    this.start = start;
    this.end = end;
    this.isPresent = isPresent;
    this.idUser = idUser;
    this.idTeam = idTeam;
  }
}
