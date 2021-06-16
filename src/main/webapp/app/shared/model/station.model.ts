export interface IStation {
  id?: number;
  uuid?: string | null;
  name?: string | null;
  description?: string | null;
  okato?: number | null;
  street?: string | null;
  longitude?: number | null;
  latitude?: number | null;
  distance?: number | null;
}

export class Station implements IStation {
  constructor(
    public id?: number,
    public uuid?: string | null,
    public name?: string | null,
    public description?: string | null,
    public okato?: number | null,
    public street?: string | null,
    public longitude?: number | null,
    public latitude?: number | null,
    public distance?: number | null
  ) {}
}
