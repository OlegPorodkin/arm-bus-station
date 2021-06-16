import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITypeObject, TypeObject } from '@/shared/model/type-object.model';
import TypeObjectService from './type-object.service';

const validations: any = {
  typeObject: {
    uuid: {},
    name: {},
  },
};

@Component({
  validations,
})
export default class TypeObjectUpdate extends Vue {
  @Inject('typeObjectService') private typeObjectService: () => TypeObjectService;
  public typeObject: ITypeObject = new TypeObject();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.typeObjectId) {
        vm.retrieveTypeObject(to.params.typeObjectId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.typeObject.id) {
      this.typeObjectService()
        .update(this.typeObject)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A TypeObject is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.typeObjectService()
        .create(this.typeObject)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A TypeObject is created with identifier ' + param.id;
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveTypeObject(typeObjectId): void {
    this.typeObjectService()
      .find(typeObjectId)
      .then(res => {
        this.typeObject = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
