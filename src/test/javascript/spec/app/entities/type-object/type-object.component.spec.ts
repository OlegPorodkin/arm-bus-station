/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TypeObjectComponent from '@/entities/type-object/type-object.vue';
import TypeObjectClass from '@/entities/type-object/type-object.component';
import TypeObjectService from '@/entities/type-object/type-object.service';

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
  describe('TypeObject Management Component', () => {
    let wrapper: Wrapper<TypeObjectClass>;
    let comp: TypeObjectClass;
    let typeObjectServiceStub: SinonStubbedInstance<TypeObjectService>;

    beforeEach(() => {
      typeObjectServiceStub = sinon.createStubInstance<TypeObjectService>(TypeObjectService);
      typeObjectServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TypeObjectClass>(TypeObjectComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          typeObjectService: () => typeObjectServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      typeObjectServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTypeObjects();
      await comp.$nextTick();

      // THEN
      expect(typeObjectServiceStub.retrieve.called).toBeTruthy();
      expect(comp.typeObjects[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      typeObjectServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeTypeObject();
      await comp.$nextTick();

      // THEN
      expect(typeObjectServiceStub.delete.called).toBeTruthy();
      expect(typeObjectServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
