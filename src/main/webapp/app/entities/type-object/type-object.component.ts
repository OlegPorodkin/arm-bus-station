import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITypeObject } from '@/shared/model/type-object.model';

import TypeObjectService from './type-object.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class TypeObject extends Vue {
  @Inject('typeObjectService') private typeObjectService: () => TypeObjectService;
  private removeId: number = null;

  public typeObjects: ITypeObject[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTypeObjects();
  }

  public clear(): void {
    this.retrieveAllTypeObjects();
  }

  public retrieveAllTypeObjects(): void {
    this.isFetching = true;

    this.typeObjectService()
      .retrieve()
      .then(
        res => {
          this.typeObjects = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: ITypeObject): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTypeObject(): void {
    this.typeObjectService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A TypeObject is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTypeObjects();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
