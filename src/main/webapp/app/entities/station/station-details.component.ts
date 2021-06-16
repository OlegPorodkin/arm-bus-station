import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStation } from '@/shared/model/station.model';
import StationService from './station.service';

@Component
export default class StationDetails extends Vue {
  @Inject('stationService') private stationService: () => StationService;
  public station: IStation = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stationId) {
        vm.retrieveStation(to.params.stationId);
      }
    });
  }

  public retrieveStation(stationId) {
    this.stationService()
      .find(stationId)
      .then(res => {
        this.station = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
