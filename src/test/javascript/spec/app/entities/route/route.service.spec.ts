/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import RouteService from '@/entities/route/route.service';
import { Route } from '@/shared/model/route.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Route Service', () => {
    let service: RouteService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new RouteService();
      currentDate = new Date();
      elemDefault = new Route(
        0,
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            plannedArrival: dayjs(currentDate).format(DATE_TIME_FORMAT),
            plannedDeparture: dayjs(currentDate).format(DATE_TIME_FORMAT),
            actualArrival: dayjs(currentDate).format(DATE_TIME_FORMAT),
            actualDeparture: dayjs(currentDate).format(DATE_TIME_FORMAT),
            timeRegistration: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Route', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            plannedArrival: dayjs(currentDate).format(DATE_TIME_FORMAT),
            plannedDeparture: dayjs(currentDate).format(DATE_TIME_FORMAT),
            actualArrival: dayjs(currentDate).format(DATE_TIME_FORMAT),
            actualDeparture: dayjs(currentDate).format(DATE_TIME_FORMAT),
            timeRegistration: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            plannedArrival: currentDate,
            plannedDeparture: currentDate,
            actualArrival: currentDate,
            actualDeparture: currentDate,
            timeRegistration: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Route', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Route', async () => {
        const returnedFromService = Object.assign(
          {
            uuid: 'BBBBBB',
            plannedArrival: dayjs(currentDate).format(DATE_TIME_FORMAT),
            plannedDeparture: dayjs(currentDate).format(DATE_TIME_FORMAT),
            actualArrival: dayjs(currentDate).format(DATE_TIME_FORMAT),
            actualDeparture: dayjs(currentDate).format(DATE_TIME_FORMAT),
            timeRegistration: dayjs(currentDate).format(DATE_TIME_FORMAT),
            platform: 'BBBBBB',
            routStatus: 'BBBBBB',
            description: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            plannedArrival: currentDate,
            plannedDeparture: currentDate,
            actualArrival: currentDate,
            actualDeparture: currentDate,
            timeRegistration: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Route', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Route', async () => {
        const patchObject = Object.assign(
          {
            actualDeparture: dayjs(currentDate).format(DATE_TIME_FORMAT),
            timeRegistration: dayjs(currentDate).format(DATE_TIME_FORMAT),
            platform: 'BBBBBB',
            routStatus: 'BBBBBB',
          },
          new Route()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            plannedArrival: currentDate,
            plannedDeparture: currentDate,
            actualArrival: currentDate,
            actualDeparture: currentDate,
            timeRegistration: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Route', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Route', async () => {
        const returnedFromService = Object.assign(
          {
            uuid: 'BBBBBB',
            plannedArrival: dayjs(currentDate).format(DATE_TIME_FORMAT),
            plannedDeparture: dayjs(currentDate).format(DATE_TIME_FORMAT),
            actualArrival: dayjs(currentDate).format(DATE_TIME_FORMAT),
            actualDeparture: dayjs(currentDate).format(DATE_TIME_FORMAT),
            timeRegistration: dayjs(currentDate).format(DATE_TIME_FORMAT),
            platform: 'BBBBBB',
            routStatus: 'BBBBBB',
            description: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            plannedArrival: currentDate,
            plannedDeparture: currentDate,
            actualArrival: currentDate,
            actualDeparture: currentDate,
            timeRegistration: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Route', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Route', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Route', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
