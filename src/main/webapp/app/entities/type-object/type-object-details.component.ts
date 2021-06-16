import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITypeObject } from '@/shared/model/type-object.model';
import TypeObjectService from './type-object.service';

@Component
export default class TypeObjectDetails extends Vue {
  @Inject('typeObjectService') private typeObjectService: () => TypeObjectService;
  public typeObject: ITypeObject = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.typeObjectId) {
        vm.retrieveTypeObject(to.params.typeObjectId);
      }
    });
  }

  public retrieveTypeObject(typeObjectId) {
    this.typeObjectService()
      .find(typeObjectId)
      .then(res => {
        this.typeObject = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
