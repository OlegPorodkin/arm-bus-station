/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TypeObjectDetailComponent from '@/entities/type-object/type-object-details.vue';
import TypeObjectClass from '@/entities/type-object/type-object-details.component';
import TypeObjectService from '@/entities/type-object/type-object.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TypeObject Management Detail Component', () => {
    let wrapper: Wrapper<TypeObjectClass>;
    let comp: TypeObjectClass;
    let typeObjectServiceStub: SinonStubbedInstance<TypeObjectService>;

    beforeEach(() => {
      typeObjectServiceStub = sinon.createStubInstance<TypeObjectService>(TypeObjectService);

      wrapper = shallowMount<TypeObjectClass>(TypeObjectDetailComponent, {
        store,
        localVue,
        router,
        provide: { typeObjectService: () => typeObjectServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTypeObject = { id: 123 };
        typeObjectServiceStub.find.resolves(foundTypeObject);

        // WHEN
        comp.retrieveTypeObject(123);
        await comp.$nextTick();

        // THEN
        expect(comp.typeObject).toBe(foundTypeObject);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTypeObject = { id: 123 };
        typeObjectServiceStub.find.resolves(foundTypeObject);

        // WHEN
        comp.beforeRouteEnter({ params: { typeObjectId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.typeObject).toBe(foundTypeObject);
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
