/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CountryComponent from '@/entities/country/country.vue';
import CountryClass from '@/entities/country/country.component';
import CountryService from '@/entities/country/country.service';

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
  describe('Country Management Component', () => {
    let wrapper: Wrapper<CountryClass>;
    let comp: CountryClass;
    let countryServiceStub: SinonStubbedInstance<CountryService>;

    beforeEach(() => {
      countryServiceStub = sinon.createStubInstance<CountryService>(CountryService);
      countryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CountryClass>(CountryComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          countryService: () => countryServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      countryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCountrys();
      await comp.$nextTick();

      // THEN
      expect(countryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.countries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      countryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeCountry();
      await comp.$nextTick();

      // THEN
      expect(countryServiceStub.delete.called).toBeTruthy();
      expect(countryServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
