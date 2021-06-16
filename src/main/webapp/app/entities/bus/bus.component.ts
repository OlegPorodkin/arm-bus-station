import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IBus } from '@/shared/model/bus.model';

import BusService from './bus.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Bus extends Vue {
  @Inject('busService') private busService: () => BusService;
  private removeId: number = null;

  public buses: IBus[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllBuss();
  }

  public clear(): void {
    this.retrieveAllBuss();
  }

  public retrieveAllBuss(): void {
    this.isFetching = true;

    this.busService()
      .retrieve()
      .then(
        res => {
          this.buses = res.data;
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

  public prepareRemove(instance: IBus): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeBus(): void {
    this.busService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Bus is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllBuss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
