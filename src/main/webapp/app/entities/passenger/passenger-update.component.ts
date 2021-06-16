import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPassenger, Passenger } from '@/shared/model/passenger.model';
import PassengerService from './passenger.service';

const validations: any = {
  passenger: {
    uuid: {},
    firstName: {},
    lastName: {},
    patronymic: {},
    birthday: {},
    boardingStatus: {},
    sex: {},
    phone: {},
    citizenship: {},
  },
};

@Component({
  validations,
})
export default class PassengerUpdate extends Vue {
  @Inject('passengerService') private passengerService: () => PassengerService;
  public passenger: IPassenger = new Passenger();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.passengerId) {
        vm.retrievePassenger(to.params.passengerId);
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
    if (this.passenger.id) {
      this.passengerService()
        .update(this.passenger)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Passenger is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.passengerService()
        .create(this.passenger)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Passenger is created with identifier ' + param.id;
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

  public retrievePassenger(passengerId): void {
    this.passengerService()
      .find(passengerId)
      .then(res => {
        this.passenger = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
