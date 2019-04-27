import {Time} from "@angular/common";

export class Membership {
  id: number;
  start: Time;
  end: Time;
  ispresent: boolean;
  idUser: number;
  idTeam: number;

  constructor(start, end, ispresent, idUser, idTeam) {
    this.start = start;
    this.end = end;
    this.ispresent = ispresent;
    this.idUser = idUser;
    this.idTeam = idTeam;
  }
}
