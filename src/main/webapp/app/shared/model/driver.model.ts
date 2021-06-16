import { IBus } from '@/shared/model/bus.model';

export interface IDriver {
  id?: number;
  uuid?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  driverLicense?: string | null;
  buses?: IBus[] | null;
}

export class Driver implements IDriver {
  constructor(
    public id?: number,
    public uuid?: string | null,
    public firstName?: string | null,
    public lastName?: string | null,
    public driverLicense?: string | null,
    public buses?: IBus[] | null
  ) {}
}
