import { Component, Inject, Vue } from 'vue-property-decorator';

import { IRoute } from '@/shared/model/route.model';
import RouteService from '@/entities/route/route.service';
import BusService from '@/entities/bus/bus.service';
import StationService from '@/entities/station/station.service';
import { IBus, Bus } from '@/shared/model/bus.model';
import { IStation, Station } from '@/shared/model/station.model';
import DispatcherSendingDetails from '@/dispatcher/dispatcher-sending-details.component';
import DispatcherService from './dispatcher.service';

@Component
export default class DispatcherDetails extends Vue {
  @Inject('routeService') private routeService: () => RouteService;
  @Inject('busService') private busService: () => BusService;
  @Inject('stationService') private stationService: () => StationService;
  @Inject('dispatcherService') private dispatcherService: () => DispatcherService;

  public routeID: number;
  public route: IRoute = {};
  public bus: IBus = {};
  public station: IStation = {};

  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.routeId) {
        vm.retrieveRoute(to.params.routeId);
      }
    });
    console.log(this.route);
  }

  public retrieveRoute(routeId) {
    this.routeService()
      .find(routeId)
      .then(res => {
        this.route = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public save(): void {
    this.isSaving = true;
    if (this.route.id) {
      this.routeService()
        .update(this.route)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Route is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.routeService()
        .create(this.route)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Route is created with identifier ' + param.id;
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

  public departure(): void {
    console.log('Отправка маршрута');
    this.route.actualDeparture = new Date();
    this.route.routStatus = 'Отправлен';
    this.routeService()
      .update(this.route)
      .then(param => {
        this.isSaving = false;
        this.$router.go(-1);
        const message = 'A Route is updated with identifier ' + param.id;
        return this.$root.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'info',
          solid: true,
          autoHideDelay: 5000,
        });
      });
  }

  public arrived(): void {
    console.log('Прибытие маршрута');
    this.route.actualArrival = new Date();
    this.route.routStatus = 'Прибыл';
    this.routeService()
      .update(this.route)
      .then(param => {
        this.isSaving = false;
        this.$router.go(-1);
        const message = 'A Route is updated with identifier ' + param.id;
        return this.$root.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'info',
          solid: true,
          autoHideDelay: 5000,
        });
      });
  }

  public cancelRoute(): void {
    console.log('Отмена маршрута');
    this.route.routStatus = 'Отменен';
    this.routeService()
      .update(this.route)
      .then(param => {
        this.isSaving = false;
        this.$router.go(-1);
        const message = 'A Route is updated with identifier ' + param.id;
        return this.$root.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'info',
          solid: true,
          autoHideDelay: 5000,
        });
      });
  }

  public boarding(): void {
    console.log('Посадка');
    this.route.routStatus = 'Посадка';
    super['routesID'] = this.route.id;
    this.routeService().update(this.route);
  }
}
