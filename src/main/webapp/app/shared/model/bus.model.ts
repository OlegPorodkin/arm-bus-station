import { IDriver } from '@/shared/model/driver.model';
import { ICounterpart } from '@/shared/model/counterpart.model';
import { IRoute } from '@/shared/model/route.model';

export interface IBus {
  id?: number;
  model?: string | null;
  number?: string | null;
  description?: string | null;
  passengerPlaces?: number | null;
  driver?: IDriver | null;
  counterpart?: ICounterpart | null;
  route?: IRoute | null;
}

export class Bus implements IBus {
  constructor(
    public id?: number,
    public model?: string | null,
    public number?: string | null,
    public description?: string | null,
    public passengerPlaces?: number | null,
    public driver?: IDriver | null,
    public counterpart?: ICounterpart | null,
    public route?: IRoute | null
  ) {}
}
