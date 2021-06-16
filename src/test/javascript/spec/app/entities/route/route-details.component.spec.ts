/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RouteDetailComponent from '@/entities/route/route-details.vue';
import RouteClass from '@/entities/route/route-details.component';
import RouteService from '@/entities/route/route.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Route Management Detail Component', () => {
    let wrapper: Wrapper<RouteClass>;
    let comp: RouteClass;
    let routeServiceStub: SinonStubbedInstance<RouteService>;

    beforeEach(() => {
      routeServiceStub = sinon.createStubInstance<RouteService>(RouteService);

      wrapper = shallowMount<RouteClass>(RouteDetailComponent, {
        store,
        localVue,
        router,
        provide: { routeService: () => routeServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRoute = { id: 123 };
        routeServiceStub.find.resolves(foundRoute);

        // WHEN
        comp.retrieveRoute(123);
        await comp.$nextTick();

        // THEN
        expect(comp.route).toBe(foundRoute);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRoute = { id: 123 };
        routeServiceStub.find.resolves(foundRoute);

        // WHEN
        comp.beforeRouteEnter({ params: { routeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.route).toBe(foundRoute);
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
