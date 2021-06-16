/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PassengerDetailComponent from '@/entities/passenger/passenger-details.vue';
import PassengerClass from '@/entities/passenger/passenger-details.component';
import PassengerService from '@/entities/passenger/passenger.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Passenger Management Detail Component', () => {
    let wrapper: Wrapper<PassengerClass>;
    let comp: PassengerClass;
    let passengerServiceStub: SinonStubbedInstance<PassengerService>;

    beforeEach(() => {
      passengerServiceStub = sinon.createStubInstance<PassengerService>(PassengerService);

      wrapper = shallowMount<PassengerClass>(PassengerDetailComponent, {
        store,
        localVue,
        router,
        provide: { passengerService: () => passengerServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPassenger = { id: 123 };
        passengerServiceStub.find.resolves(foundPassenger);

        // WHEN
        comp.retrievePassenger(123);
        await comp.$nextTick();

        // THEN
        expect(comp.passenger).toBe(foundPassenger);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPassenger = { id: 123 };
        passengerServiceStub.find.resolves(foundPassenger);

        // WHEN
        comp.beforeRouteEnter({ params: { passengerId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.passenger).toBe(foundPassenger);
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
