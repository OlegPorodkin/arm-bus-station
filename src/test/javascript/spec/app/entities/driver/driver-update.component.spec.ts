/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import DriverUpdateComponent from '@/entities/driver/driver-update.vue';
import DriverClass from '@/entities/driver/driver-update.component';
import DriverService from '@/entities/driver/driver.service';

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
  describe('Driver Management Update Component', () => {
    let wrapper: Wrapper<DriverClass>;
    let comp: DriverClass;
    let driverServiceStub: SinonStubbedInstance<DriverService>;

    beforeEach(() => {
      driverServiceStub = sinon.createStubInstance<DriverService>(DriverService);

      wrapper = shallowMount<DriverClass>(DriverUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          driverService: () => driverServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.driver = entity;
        driverServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(driverServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.driver = entity;
        driverServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(driverServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDriver = { id: 123 };
        driverServiceStub.find.resolves(foundDriver);
        driverServiceStub.retrieve.resolves([foundDriver]);

        // WHEN
        comp.beforeRouteEnter({ params: { driverId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.driver).toBe(foundDriver);
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
