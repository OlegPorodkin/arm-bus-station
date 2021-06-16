import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDriver } from '@/shared/model/driver.model';
import DriverService from './driver.service';

@Component
export default class DriverDetails extends Vue {
  @Inject('driverService') private driverService: () => DriverService;
  public driver: IDriver = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.driverId) {
        vm.retrieveDriver(to.params.driverId);
      }
    });
  }

  public retrieveDriver(driverId) {
    this.driverService()
      .find(driverId)
      .then(res => {
        this.driver = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
