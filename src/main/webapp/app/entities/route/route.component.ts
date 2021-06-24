import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRoute } from '@/shared/model/route.model';

import RouteService from './route.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Route extends Vue {
  @Inject('routeService') private routeService: () => RouteService;
  private removeId: number = null;

  public routes: IRoute[] = [];
  private selected: [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRoutes();
  }

  public clear(): void {
    this.retrieveAllRoutes();
  }

  public retrieveAllRoutes(): void {
    this.isFetching = true;

    this.routeService()
      .retrieve()
      .then(
        res => {
          this.routes = res.data;
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

  public prepareRemove(instance: IRoute): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRoute(): void {
    this.routeService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Route is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllRoutes();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }

  public rowSelected(items) {
    console.log(items);
  }
}
