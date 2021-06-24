/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import RouteUpdateComponent from '@/entities/route/route-update.vue';
import RouteClass from '@/entities/route/route-update.component';
import RouteService from '@/entities/route/route.service';

import BusService from '@/entities/bus/bus.service';

import StationService from '@/entities/station/station.service';

import PassengerService from '@/entities/passenger/passenger.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Route Management Update Component', () => {
    let wrapper: Wrapper<RouteClass>;
    let comp: RouteClass;
    let routeServiceStub: SinonStubbedInstance<RouteService>;

    beforeEach(() => {
      routeServiceStub = sinon.createStubInstance<RouteService>(RouteService);

      wrapper = shallowMount<RouteClass>(RouteUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          routeService: () => routeServiceStub,

          busService: () => new BusService(),

          stationService: () => new StationService(),

          passengerService: () => new PassengerService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.route = entity;
        routeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(routeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.route = entity;
        routeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(routeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRoute = { id: 123 };
        routeServiceStub.find.resolves(foundRoute);
        routeServiceStub.retrieve.resolves([foundRoute]);

        // WHEN
        comp.beforeRouteEnter({ params: { routeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.route).toBe(foundRoute);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
