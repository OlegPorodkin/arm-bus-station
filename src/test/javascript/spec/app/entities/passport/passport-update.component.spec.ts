/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import PassportUpdateComponent from '@/entities/passport/passport-update.vue';
import PassportClass from '@/entities/passport/passport-update.component';
import PassportService from '@/entities/passport/passport.service';

import PassengerService from '@/entities/passenger/passenger.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Passport Management Update Component', () => {
    let wrapper: Wrapper<PassportClass>;
    let comp: PassportClass;
    let passportServiceStub: SinonStubbedInstance<PassportService>;

    beforeEach(() => {
      passportServiceStub = sinon.createStubInstance<PassportService>(PassportService);

      wrapper = shallowMount<PassportClass>(PassportUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          passportService: () => passportServiceStub,

          passengerService: () => new PassengerService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.passport = entity;
        passportServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(passportServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.passport = entity;
        passportServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(passportServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPassport = { id: 123 };
        passportServiceStub.find.resolves(foundPassport);
        passportServiceStub.retrieve.resolves([foundPassport]);

        // WHEN
        comp.beforeRouteEnter({ params: { passportId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.passport).toBe(foundPassport);
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
