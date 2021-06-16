import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICounterpart } from '@/shared/model/counterpart.model';

import CounterpartService from './counterpart.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Counterpart extends Vue {
  @Inject('counterpartService') private counterpartService: () => CounterpartService;
  private removeId: number = null;

  public counterparts: ICounterpart[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCounterparts();
  }

  public clear(): void {
    this.retrieveAllCounterparts();
  }

  public retrieveAllCounterparts(): void {
    this.isFetching = true;

    this.counterpartService()
      .retrieve()
      .then(
        res => {
          this.counterparts = res.data;
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

  public prepareRemove(instance: ICounterpart): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCounterpart(): void {
    this.counterpartService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Counterpart is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCounterparts();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
