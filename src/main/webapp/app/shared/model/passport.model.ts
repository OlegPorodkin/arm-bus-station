import { IPassenger } from '@/shared/model/passenger.model';

export interface IPassport {
  id?: number;
  uuid?: string | null;
  serial?: number | null;
  number?: number | null;
  dateOfIssue?: Date | null;
  whoIssued?: string | null;
  passenger?: IPassenger | null;
}

export class Passport implements IPassport {
  constructor(
    public id?: number,
    public uuid?: string | null,
    public serial?: number | null,
    public number?: number | null,
    public dateOfIssue?: Date | null,
    public whoIssued?: string | null,
    public passenger?: IPassenger | null
  ) {}
}
