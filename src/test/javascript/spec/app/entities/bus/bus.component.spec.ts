/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import BusComponent from '@/entities/bus/bus.vue';
import BusClass from '@/entities/bus/bus.component';
import BusService from '@/entities/bus/bus.service';

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
  describe('Bus Management Component', () => {
    let wrapper: Wrapper<BusClass>;
    let comp: BusClass;
    let busServiceStub: SinonStubbedInstance<BusService>;

    beforeEach(() => {
      busServiceStub = sinon.createStubInstance<BusService>(BusService);
      busServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<BusClass>(BusComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          busService: () => busServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      busServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllBuss();
      await comp.$nextTick();

      // THEN
      expect(busServiceStub.retrieve.called).toBeTruthy();
      expect(comp.buses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      busServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeBus();
      await comp.$nextTick();

      // THEN
      expect(busServiceStub.delete.called).toBeTruthy();
      expect(busServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
