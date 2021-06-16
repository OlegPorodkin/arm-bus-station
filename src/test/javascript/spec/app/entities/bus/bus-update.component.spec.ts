/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import BusUpdateComponent from '@/entities/bus/bus-update.vue';
import BusClass from '@/entities/bus/bus-update.component';
import BusService from '@/entities/bus/bus.service';

import DriverService from '@/entities/driver/driver.service';

import CounterpartService from '@/entities/counterpart/counterpart.service';

import RouteService from '@/entities/route/route.service';

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
  describe('Bus Management Update Component', () => {
    let wrapper: Wrapper<BusClass>;
    let comp: BusClass;
    let busServiceStub: SinonStubbedInstance<BusService>;

    beforeEach(() => {
      busServiceStub = sinon.createStubInstance<BusService>(BusService);

      wrapper = shallowMount<BusClass>(BusUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          busService: () => busServiceStub,

          driverService: () => new DriverService(),

          counterpartService: () => new CounterpartService(),

          routeService: () => new RouteService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.bus = entity;
        busServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(busServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.bus = entity;
        busServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(busServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBus = { id: 123 };
        busServiceStub.find.resolves(foundBus);
        busServiceStub.retrieve.resolves([foundBus]);

        // WHEN
        comp.beforeRouteEnter({ params: { busId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.bus).toBe(foundBus);
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
