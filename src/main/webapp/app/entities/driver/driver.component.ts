import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDriver } from '@/shared/model/driver.model';

import DriverService from './driver.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Driver extends Vue {
  @Inject('driverService') private driverService: () => DriverService;
  private removeId: number = null;

  public drivers: IDriver[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDrivers();
  }

  public clear(): void {
    this.retrieveAllDrivers();
  }

  public retrieveAllDrivers(): void {
    this.isFetching = true;

    this.driverService()
      .retrieve()
      .then(
        res => {
          this.drivers = res.data;
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

  public prepareRemove(instance: IDriver): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDriver(): void {
    this.driverService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Driver is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllDrivers();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
