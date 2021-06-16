import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPassenger } from '@/shared/model/passenger.model';

import PassengerService from './passenger.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Passenger extends Vue {
  @Inject('passengerService') private passengerService: () => PassengerService;
  private removeId: number = null;

  public passengers: IPassenger[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPassengers();
  }

  public clear(): void {
    this.retrieveAllPassengers();
  }

  public retrieveAllPassengers(): void {
    this.isFetching = true;

    this.passengerService()
      .retrieve()
      .then(
        res => {
          this.passengers = res.data;
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

  public prepareRemove(instance: IPassenger): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePassenger(): void {
    this.passengerService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Passenger is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPassengers();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
