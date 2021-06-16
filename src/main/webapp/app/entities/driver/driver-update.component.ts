import { Component, Vue, Inject } from 'vue-property-decorator';

import BusService from '@/entities/bus/bus.service';
import { IBus } from '@/shared/model/bus.model';

import { IDriver, Driver } from '@/shared/model/driver.model';
import DriverService from './driver.service';

const validations: any = {
  driver: {
    uuid: {},
    firstName: {},
    lastName: {},
    driverLicense: {},
  },
};

@Component({
  validations,
})
export default class DriverUpdate extends Vue {
  @Inject('driverService') private driverService: () => DriverService;
  public driver: IDriver = new Driver();

  @Inject('busService') private busService: () => BusService;

  public buses: IBus[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.driverId) {
        vm.retrieveDriver(to.params.driverId);
      }
      vm.initRelationships();
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
    if (this.driver.id) {
      this.driverService()
        .update(this.driver)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Driver is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.driverService()
        .create(this.driver)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Driver is created with identifier ' + param.id;
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

  public retrieveDriver(driverId): void {
    this.driverService()
      .find(driverId)
      .then(res => {
        this.driver = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.busService()
      .retrieve()
      .then(res => {
        this.buses = res.data;
      });
  }
}
