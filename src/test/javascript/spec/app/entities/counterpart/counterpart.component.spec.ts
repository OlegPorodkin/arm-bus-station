/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CounterpartComponent from '@/entities/counterpart/counterpart.vue';
import CounterpartClass from '@/entities/counterpart/counterpart.component';
import CounterpartService from '@/entities/counterpart/counterpart.service';

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
  describe('Counterpart Management Component', () => {
    let wrapper: Wrapper<CounterpartClass>;
    let comp: CounterpartClass;
    let counterpartServiceStub: SinonStubbedInstance<CounterpartService>;

    beforeEach(() => {
      counterpartServiceStub = sinon.createStubInstance<CounterpartService>(CounterpartService);
      counterpartServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CounterpartClass>(CounterpartComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          counterpartService: () => counterpartServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      counterpartServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCounterparts();
      await comp.$nextTick();

      // THEN
      expect(counterpartServiceStub.retrieve.called).toBeTruthy();
      expect(comp.counterparts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      counterpartServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeCounterpart();
      await comp.$nextTick();

      // THEN
      expect(counterpartServiceStub.delete.called).toBeTruthy();
      expect(counterpartServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
