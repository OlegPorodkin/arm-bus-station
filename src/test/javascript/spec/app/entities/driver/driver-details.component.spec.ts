/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DriverDetailComponent from '@/entities/driver/driver-details.vue';
import DriverClass from '@/entities/driver/driver-details.component';
import DriverService from '@/entities/driver/driver.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Driver Management Detail Component', () => {
    let wrapper: Wrapper<DriverClass>;
    let comp: DriverClass;
    let driverServiceStub: SinonStubbedInstance<DriverService>;

    beforeEach(() => {
      driverServiceStub = sinon.createStubInstance<DriverService>(DriverService);

      wrapper = shallowMount<DriverClass>(DriverDetailComponent, {
        store,
        localVue,
        router,
        provide: { driverService: () => driverServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDriver = { id: 123 };
        driverServiceStub.find.resolves(foundDriver);

        // WHEN
        comp.retrieveDriver(123);
        await comp.$nextTick();

        // THEN
        expect(comp.driver).toBe(foundDriver);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDriver = { id: 123 };
        driverServiceStub.find.resolves(foundDriver);

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
