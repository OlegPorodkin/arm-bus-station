export interface ITypeObject {
  id?: number;
  uuid?: string | null;
  name?: string | null;
}

export class TypeObject implements ITypeObject {
  constructor(public id?: number, public uuid?: string | null, public name?: string | null) {}
}
