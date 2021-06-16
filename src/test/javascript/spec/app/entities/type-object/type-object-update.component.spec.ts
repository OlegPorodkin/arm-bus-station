/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import TypeObjectUpdateComponent from '@/entities/type-object/type-object-update.vue';
import TypeObjectClass from '@/entities/type-object/type-object-update.component';
import TypeObjectService from '@/entities/type-object/type-object.service';

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
  describe('TypeObject Management Update Component', () => {
    let wrapper: Wrapper<TypeObjectClass>;
    let comp: TypeObjectClass;
    let typeObjectServiceStub: SinonStubbedInstance<TypeObjectService>;

    beforeEach(() => {
      typeObjectServiceStub = sinon.createStubInstance<TypeObjectService>(TypeObjectService);

      wrapper = shallowMount<TypeObjectClass>(TypeObjectUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          typeObjectService: () => typeObjectServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.typeObject = entity;
        typeObjectServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeObjectServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.typeObject = entity;
        typeObjectServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeObjectServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTypeObject = { id: 123 };
        typeObjectServiceStub.find.resolves(foundTypeObject);
        typeObjectServiceStub.retrieve.resolves([foundTypeObject]);

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
