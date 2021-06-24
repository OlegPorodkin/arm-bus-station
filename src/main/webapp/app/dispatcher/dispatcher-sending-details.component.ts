import { Component, Vue, Inject } from 'vue-property-decorator';
import { IPassenger } from '@/shared/model/passenger.model';

import PassengerService from '@/entities/passenger/passenger.service';
import { IRoute, Route } from '@/shared/model/route.model';
// import RouteService from "@/entities/route/route.service";
import DispatcherService from './dispatcher.service';
import DispatcherDetails from './dispatcher-details.component';
import DispatcherSendingVue from '@/dispatcher/dispatcher-sending-details.vue';

@Component
export default class DispatcherSendingDetails extends Vue {
  props: ['routeId'];

  @Inject('passengerService') private passengerService: () => PassengerService;
  // @Inject('routeService') private routeService: () => RouteService;
  // @Inject('dispatcherService') private dispatcherService: () => DispatcherService;
  // @Inject('dispatcherDetails') private dispatcherDetails: () => DispatcherDetails;
  private removeId: number = null;

  public passengers: IPassenger[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPassengers();
  }

  public clear(): void {
    this.retrieveAllPassengers();
  }

  public retrieveAllPassengers(): void {
    this.isFetching = true;

    this.passengerService()
      .retrieveByRoute(Number(this.$router.currentRoute.params['routeId']))
      .then(
        res => {
          this.passengers = res.data;
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

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }

  public previousState() {
    this.$router.go(-1);
  }

  public appearance(id: number) {
    this.passengers[id].boardingStatus = 'прибыл';
    console.log(this.passengers[id].boardingStatus);
    this.passengerService().update(this.passengers[id]);
  }

  public notAppearance(id: number) {
    this.passengers[id].boardingStatus = 'не прибыл';
    console.log(this.passengers[id].boardingStatus);
    this.passengerService().update(this.passengers[id]);
  }
}
