import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IStation } from '@/shared/model/station.model';

import StationService from './station.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Station extends Vue {
  @Inject('stationService') private stationService: () => StationService;
  private removeId: number = null;

  public stations: IStation[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllStations();
  }

  public clear(): void {
    this.retrieveAllStations();
  }

  public retrieveAllStations(): void {
    this.isFetching = true;

    this.stationService()
      .retrieve()
      .then(
        res => {
          this.stations = res.data;
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

  public prepareRemove(instance: IStation): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeStation(): void {
    this.stationService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Station is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllStations();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
