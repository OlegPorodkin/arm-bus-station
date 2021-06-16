/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DriverComponent from '@/entities/driver/driver.vue';
import DriverClass from '@/entities/driver/driver.component';
import DriverService from '@/entities/driver/driver.service';

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
  describe('Driver Management Component', () => {
    let wrapper: Wrapper<DriverClass>;
    let comp: DriverClass;
    let driverServiceStub: SinonStubbedInstance<DriverService>;

    beforeEach(() => {
      driverServiceStub = sinon.createStubInstance<DriverService>(DriverService);
      driverServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DriverClass>(DriverComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          driverService: () => driverServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      driverServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDrivers();
      await comp.$nextTick();

      // THEN
      expect(driverServiceStub.retrieve.called).toBeTruthy();
      expect(comp.drivers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      driverServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDriver();
      await comp.$nextTick();

      // THEN
      expect(driverServiceStub.delete.called).toBeTruthy();
      expect(driverServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
