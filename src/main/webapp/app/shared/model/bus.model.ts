export interface IBus {
  id?: number;
  model?: string | null;
  number?: string | null;
  description?: string | null;
  passengerPlaces?: number | null;
}

export class Bus implements IBus {
  constructor(
    public id?: number,
    public model?: string | null,
    public number?: string | null,
    public description?: string | null,
    public passengerPlaces?: number | null
  ) {}
}
