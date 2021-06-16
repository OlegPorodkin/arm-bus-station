import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import { IRoute, Route } from '@/shared/model/route.model';
import RouteService from './route.service';

const validations: any = {
  route: {
    uuid: {},
    plannedArrival: {},
    plannedDeparture: {},
    actualArrival: {},
    actualDeparture: {},
    timeRegistration: {},
    platform: {},
    routStatus: {},
    description: {},
  },
};

@Component({
  validations,
})
export default class RouteUpdate extends Vue {
  @Inject('routeService') private routeService: () => RouteService;
  public route: IRoute = new Route();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.routeId) {
        vm.retrieveRoute(to.params.routeId);
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

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.route[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.route[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.route[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.route[field] = null;
    }
  }

  public retrieveRoute(routeId): void {
    this.routeService()
      .find(routeId)
      .then(res => {
        res.plannedArrival = new Date(res.plannedArrival);
        res.plannedDeparture = new Date(res.plannedDeparture);
        res.actualArrival = new Date(res.actualArrival);
        res.actualDeparture = new Date(res.actualDeparture);
        res.timeRegistration = new Date(res.timeRegistration);
        this.route = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
