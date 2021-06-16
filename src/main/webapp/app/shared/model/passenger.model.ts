export interface IPassenger {
  id?: number;
  uuid?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  patronymic?: string | null;
  birthday?: Date | null;
  boardingStatus?: string | null;
  sex?: string | null;
  phone?: string | null;
  citizenship?: string | null;
}

export class Passenger implements IPassenger {
  constructor(
    public id?: number,
    public uuid?: string | null,
    public firstName?: string | null,
    public lastName?: string | null,
    public patronymic?: string | null,
    public birthday?: Date | null,
    public boardingStatus?: string | null,
    public sex?: string | null,
    public phone?: string | null,
    public citizenship?: string | null
  ) {}
}
