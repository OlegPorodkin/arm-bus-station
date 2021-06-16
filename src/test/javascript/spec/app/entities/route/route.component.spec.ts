/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RouteComponent from '@/entities/route/route.vue';
import RouteClass from '@/entities/route/route.component';
import RouteService from '@/entities/route/route.service';

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
  describe('Route Management Component', () => {
    let wrapper: Wrapper<RouteClass>;
    let comp: RouteClass;
    let routeServiceStub: SinonStubbedInstance<RouteService>;

    beforeEach(() => {
      routeServiceStub = sinon.createStubInstance<RouteService>(RouteService);
      routeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<RouteClass>(RouteComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          routeService: () => routeServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      routeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllRoutes();
      await comp.$nextTick();

      // THEN
      expect(routeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.routes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      routeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeRoute();
      await comp.$nextTick();

      // THEN
      expect(routeServiceStub.delete.called).toBeTruthy();
      expect(routeServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
