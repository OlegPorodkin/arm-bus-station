/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PassengerComponent from '@/entities/passenger/passenger.vue';
import PassengerClass from '@/entities/passenger/passenger.component';
import PassengerService from '@/entities/passenger/passenger.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Passenger Management Component', () => {
    let wrapper: Wrapper<PassengerClass>;
    let comp: PassengerClass;
    let passengerServiceStub: SinonStubbedInstance<PassengerService>;

    beforeEach(() => {
      passengerServiceStub = sinon.createStubInstance<PassengerService>(PassengerService);
      passengerServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PassengerClass>(PassengerComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          passengerService: () => passengerServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      passengerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPassengers();
      await comp.$nextTick();

      // THEN
      expect(passengerServiceStub.retrieve.called).toBeTruthy();
      expect(comp.passengers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      passengerServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePassenger();
      await comp.$nextTick();

      // THEN
      expect(passengerServiceStub.delete.called).toBeTruthy();
      expect(passengerServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
