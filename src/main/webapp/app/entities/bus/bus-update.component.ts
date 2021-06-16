import { Component, Vue, Inject } from 'vue-property-decorator';

import { IBus, Bus } from '@/shared/model/bus.model';
import BusService from './bus.service';

const validations: any = {
  bus: {
    model: {},
    number: {},
    description: {},
    passengerPlaces: {},
  },
};

@Component({
  validations,
})
export default class BusUpdate extends Vue {
  @Inject('busService') private busService: () => BusService;
  public bus: IBus = new Bus();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.busId) {
        vm.retrieveBus(to.params.busId);
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
    if (this.bus.id) {
      this.busService()
        .update(this.bus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Bus is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.busService()
        .create(this.bus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Bus is created with identifier ' + param.id;
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

  public retrieveBus(busId): void {
    this.busService()
      .find(busId)
      .then(res => {
        this.bus = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
