import { IRegion } from '@/shared/model/region.model';

export interface ICountry {
  id?: number;
  uuid?: string | null;
  name?: string | null;
  regions?: IRegion[] | null;
  countryOfLocations?: IRegion[] | null;
}

export class Country implements ICountry {
  constructor(
    public id?: number,
    public uuid?: string | null,
    public name?: string | null,
    public regions?: IRegion[] | null,
    public countryOfLocations?: IRegion[] | null
  ) {}
}
