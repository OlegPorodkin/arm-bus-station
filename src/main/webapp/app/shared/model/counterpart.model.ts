export interface ICounterpart {
  id?: number;
  uuid?: string | null;
  name?: string | null;
  shortName?: string | null;
  tin?: string | null;
  ogrn?: string | null;
  egis_otb_rf?: string | null;
  taxSystem?: string | null;
  address?: string | null;
  description?: string | null;
  country?: string | null;
}

export class Counterpart implements ICounterpart {
  constructor(
    public id?: number,
    public uuid?: string | null,
    public name?: string | null,
    public shortName?: string | null,
    public tin?: string | null,
    public ogrn?: string | null,
    public egis_otb_rf?: string | null,
    public taxSystem?: string | null,
    public address?: string | null,
    public description?: string | null,
    public country?: string | null
  ) {}
}
