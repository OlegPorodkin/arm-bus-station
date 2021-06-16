export interface IRegion {
  id?: number;
  uuid?: string | null;
  name?: string | null;
  description?: string | null;
}

export class Region implements IRegion {
  constructor(public id?: number, public uuid?: string | null, public name?: string | null, public description?: string | null) {}
}
