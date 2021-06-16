/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import BusDetailComponent from '@/entities/bus/bus-details.vue';
import BusClass from '@/entities/bus/bus-details.component';
import BusService from '@/entities/bus/bus.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Bus Management Detail Component', () => {
    let wrapper: Wrapper<BusClass>;
    let comp: BusClass;
    let busServiceStub: SinonStubbedInstance<BusService>;

    beforeEach(() => {
      busServiceStub = sinon.createStubInstance<BusService>(BusService);

      wrapper = shallowMount<BusClass>(BusDetailComponent, { store, localVue, router, provide: { busService: () => busServiceStub } });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundBus = { id: 123 };
        busServiceStub.find.resolves(foundBus);

        // WHEN
        comp.retrieveBus(123);
        await comp.$nextTick();

        // THEN
        expect(comp.bus).toBe(foundBus);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBus = { id: 123 };
        busServiceStub.find.resolves(foundBus);

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
