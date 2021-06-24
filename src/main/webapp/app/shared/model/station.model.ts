import { ITypeObject } from '@/shared/model/type-object.model';
import { IRegion } from '@/shared/model/region.model';

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
  nextStation?: IStation | null;
  typeObject?: ITypeObject | null;
  region?: IRegion | null;
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
    public distance?: number | null,
    public nextStation?: IStation | null,
    public typeObject?: ITypeObject | null,
    public region?: IRegion | null
  ) {}
}
