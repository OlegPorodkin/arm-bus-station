import { Component, Vue, Inject } from 'vue-property-decorator';

import { IBus } from '@/shared/model/bus.model';
import BusService from './bus.service';

@Component
export default class BusDetails extends Vue {
  @Inject('busService') private busService: () => BusService;
  public bus: IBus = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.busId) {
        vm.retrieveBus(to.params.busId);
      }
    });
  }

  public retrieveBus(busId) {
    this.busService()
      .find(busId)
      .then(res => {
        this.bus = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
