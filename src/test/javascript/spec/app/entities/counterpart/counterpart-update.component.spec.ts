/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import CounterpartUpdateComponent from '@/entities/counterpart/counterpart-update.vue';
import CounterpartClass from '@/entities/counterpart/counterpart-update.component';
import CounterpartService from '@/entities/counterpart/counterpart.service';

import BusService from '@/entities/bus/bus.service';

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
  describe('Counterpart Management Update Component', () => {
    let wrapper: Wrapper<CounterpartClass>;
    let comp: CounterpartClass;
    let counterpartServiceStub: SinonStubbedInstance<CounterpartService>;

    beforeEach(() => {
      counterpartServiceStub = sinon.createStubInstance<CounterpartService>(CounterpartService);

      wrapper = shallowMount<CounterpartClass>(CounterpartUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          counterpartService: () => counterpartServiceStub,

          busService: () => new BusService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.counterpart = entity;
        counterpartServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(counterpartServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.counterpart = entity;
        counterpartServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(counterpartServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCounterpart = { id: 123 };
        counterpartServiceStub.find.resolves(foundCounterpart);
        counterpartServiceStub.retrieve.resolves([foundCounterpart]);

        // WHEN
        comp.beforeRouteEnter({ params: { counterpartId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.counterpart).toBe(foundCounterpart);
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
