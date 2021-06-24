import { Component, Vue, Inject } from 'vue-property-decorator';

import PassengerService from '@/entities/passenger/passenger.service';
import { IPassenger } from '@/shared/model/passenger.model';

import { IPassport, Passport } from '@/shared/model/passport.model';
import PassportService from './passport.service';

const validations: any = {
  passport: {
    uuid: {},
    serial: {},
    number: {},
    dateOfIssue: {},
    whoIssued: {},
  },
};

@Component({
  validations,
})
export default class PassportUpdate extends Vue {
  @Inject('passportService') private passportService: () => PassportService;
  public passport: IPassport = new Passport();

  @Inject('passengerService') private passengerService: () => PassengerService;

  public passengers: IPassenger[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.passportId) {
        vm.retrievePassport(to.params.passportId);
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
    if (this.passport.id) {
      this.passportService()
        .update(this.passport)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Passport is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.passportService()
        .create(this.passport)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Passport is created with identifier ' + param.id;
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

  public retrievePassport(passportId): void {
    this.passportService()
      .find(passportId)
      .then(res => {
        this.passport = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.passengerService()
      .retrieve()
      .then(res => {
        this.passengers = res.data;
      });
  }
}
