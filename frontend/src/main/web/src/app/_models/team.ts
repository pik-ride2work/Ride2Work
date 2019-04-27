export class Team {
  id: number;
  name: string;
  memberCount: number;

  constructor(name, memberCount) {
    this.name = name;
    this.memberCount = memberCount;
  }
}
