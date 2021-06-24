import { IPassenger } from '@/shared/model/passenger.model';
import { IStation } from '@/shared/model/station.model';

export interface ITicket {
  id?: number;
  uuid?: string | null;
  place?: number | null;
  serial?: number | null;
  number?: number | null;
  type?: string | null;
  dateDeparture?: Date | null;
  price?: number | null;
  passenger?: IPassenger | null;
  departure?: IStation | null;
  destination?: IStation | null;
}

export class Ticket implements ITicket {
  constructor(
    public id?: number,
    public uuid?: string | null,
    public place?: number | null,
    public serial?: number | null,
    public number?: number | null,
    public type?: string | null,
    public dateDeparture?: Date | null,
    public price?: number | null,
    public passenger?: IPassenger | null,
    public departure?: IStation | null,
    public destination?: IStation | null
  ) {}
}
