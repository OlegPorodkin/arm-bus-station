/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PassportComponent from '@/entities/passport/passport.vue';
import PassportClass from '@/entities/passport/passport.component';
import PassportService from '@/entities/passport/passport.service';

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
  describe('Passport Management Component', () => {
    let wrapper: Wrapper<PassportClass>;
    let comp: PassportClass;
    let passportServiceStub: SinonStubbedInstance<PassportService>;

    beforeEach(() => {
      passportServiceStub = sinon.createStubInstance<PassportService>(PassportService);
      passportServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PassportClass>(PassportComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          passportService: () => passportServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      passportServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPassports();
      await comp.$nextTick();

      // THEN
      expect(passportServiceStub.retrieve.called).toBeTruthy();
      expect(comp.passports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      passportServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePassport();
      await comp.$nextTick();

      // THEN
      expect(passportServiceStub.delete.called).toBeTruthy();
      expect(passportServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
