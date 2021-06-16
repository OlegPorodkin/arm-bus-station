/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CounterpartDetailComponent from '@/entities/counterpart/counterpart-details.vue';
import CounterpartClass from '@/entities/counterpart/counterpart-details.component';
import CounterpartService from '@/entities/counterpart/counterpart.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Counterpart Management Detail Component', () => {
    let wrapper: Wrapper<CounterpartClass>;
    let comp: CounterpartClass;
    let counterpartServiceStub: SinonStubbedInstance<CounterpartService>;

    beforeEach(() => {
      counterpartServiceStub = sinon.createStubInstance<CounterpartService>(CounterpartService);

      wrapper = shallowMount<CounterpartClass>(CounterpartDetailComponent, {
        store,
        localVue,
        router,
        provide: { counterpartService: () => counterpartServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCounterpart = { id: 123 };
        counterpartServiceStub.find.resolves(foundCounterpart);

        // WHEN
        comp.retrieveCounterpart(123);
        await comp.$nextTick();

        // THEN
        expect(comp.counterpart).toBe(foundCounterpart);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCounterpart = { id: 123 };
        counterpartServiceStub.find.resolves(foundCounterpart);

        // WHEN
        comp.beforeRouteEnter({ params: { counterpartId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.counterpart).toBe(foundCounterpart);
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
