export interface ICountry {
  id?: number;
  uuid?: string | null;
  name?: string | null;
}

export class Country implements ICountry {
  constructor(public id?: number, public uuid?: string | null, public name?: string | null) {}
}
