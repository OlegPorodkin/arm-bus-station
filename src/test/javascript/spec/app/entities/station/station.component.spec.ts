/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import StationComponent from '@/entities/station/station.vue';
import StationClass from '@/entities/station/station.component';
import StationService from '@/entities/station/station.service';

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
  describe('Station Management Component', () => {
    let wrapper: Wrapper<StationClass>;
    let comp: StationClass;
    let stationServiceStub: SinonStubbedInstance<StationService>;

    beforeEach(() => {
      stationServiceStub = sinon.createStubInstance<StationService>(StationService);
      stationServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<StationClass>(StationComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          stationService: () => stationServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      stationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllStations();
      await comp.$nextTick();

      // THEN
      expect(stationServiceStub.retrieve.called).toBeTruthy();
      expect(comp.stations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      stationServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeStation();
      await comp.$nextTick();

      // THEN
      expect(stationServiceStub.delete.called).toBeTruthy();
      expect(stationServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
