import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPassenger } from '@/shared/model/passenger.model';
import PassengerService from './passenger.service';

@Component
export default class PassengerDetails extends Vue {
  @Inject('passengerService') private passengerService: () => PassengerService;
  public passenger: IPassenger = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.passengerId) {
        vm.retrievePassenger(to.params.passengerId);
      }
    });
  }

  public retrievePassenger(passengerId) {
    this.passengerService()
      .find(passengerId)
      .then(res => {
        this.passenger = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
