import axios from 'axios';

import { IPassenger } from '@/shared/model/passenger.model';

export default class DispatcherService {
  private _routeId: number;

  get routeId(): number {
    return this._routeId;
  }

  set routeId(value: number) {
    this._routeId = value;
  }
}
