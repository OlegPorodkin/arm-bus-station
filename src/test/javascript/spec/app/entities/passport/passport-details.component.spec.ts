/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PassportDetailComponent from '@/entities/passport/passport-details.vue';
import PassportClass from '@/entities/passport/passport-details.component';
import PassportService from '@/entities/passport/passport.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Passport Management Detail Component', () => {
    let wrapper: Wrapper<PassportClass>;
    let comp: PassportClass;
    let passportServiceStub: SinonStubbedInstance<PassportService>;

    beforeEach(() => {
      passportServiceStub = sinon.createStubInstance<PassportService>(PassportService);

      wrapper = shallowMount<PassportClass>(PassportDetailComponent, {
        store,
        localVue,
        router,
        provide: { passportService: () => passportServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPassport = { id: 123 };
        passportServiceStub.find.resolves(foundPassport);

        // WHEN
        comp.retrievePassport(123);
        await comp.$nextTick();

        // THEN
        expect(comp.passport).toBe(foundPassport);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPassport = { id: 123 };
        passportServiceStub.find.resolves(foundPassport);

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
