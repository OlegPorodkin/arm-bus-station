import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStation, Station } from '@/shared/model/station.model';
import StationService from './station.service';

const validations: any = {
  station: {
    uuid: {},
    name: {},
    description: {},
    okato: {},
    street: {},
    longitude: {},
    latitude: {},
    distance: {},
  },
};

@Component({
  validations,
})
export default class StationUpdate extends Vue {
  @Inject('stationService') private stationService: () => StationService;
  public station: IStation = new Station();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stationId) {
        vm.retrieveStation(to.params.stationId);
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
    if (this.station.id) {
      this.stationService()
        .update(this.station)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Station is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.stationService()
        .create(this.station)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Station is created with identifier ' + param.id;
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

  public retrieveStation(stationId): void {
    this.stationService()
      .find(stationId)
      .then(res => {
        this.station = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
