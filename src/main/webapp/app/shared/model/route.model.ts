import { IBus } from '@/shared/model/bus.model';

export interface IRoute {
  id?: number;
  uuid?: string | null;
  plannedArrival?: Date | null;
  plannedDeparture?: Date | null;
  actualArrival?: Date | null;
  actualDeparture?: Date | null;
  timeRegistration?: Date | null;
  platform?: string | null;
  routStatus?: string | null;
  description?: string | null;
  bus?: IBus | null;
}

export class Route implements IRoute {
  constructor(
    public id?: number,
    public uuid?: string | null,
    public plannedArrival?: Date | null,
    public plannedDeparture?: Date | null,
    public actualArrival?: Date | null,
    public actualDeparture?: Date | null,
    public timeRegistration?: Date | null,
    public platform?: string | null,
    public routStatus?: string | null,
    public description?: string | null,
    public bus?: IBus | null
  ) {}
}
