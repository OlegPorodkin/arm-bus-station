import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRoute } from '@/shared/model/route.model';
import RouteService from './route.service';

@Component
export default class RouteDetails extends Vue {
  @Inject('routeService') private routeService: () => RouteService;
  public route: IRoute = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.routeId) {
        vm.retrieveRoute(to.params.routeId);
      }
    });
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
}
