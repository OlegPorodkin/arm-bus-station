import { Component, Inject, Vue } from 'vue-property-decorator';
import AccountService from '@/account/account.service';
import RouteService from '@/entities/route/route.service';
import { IRoute } from '@/shared/model/route.model';

@Component
export default class DispatcherComponent extends Vue {
  private hasAnyAuthorityValue = false;

  @Inject('accountService')
  private accountService: () => AccountService;

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public hasAnyAuthority(authorities: any): boolean {
    this.accountService()
      .hasAnyAuthorityAndCheckAuth(authorities)
      .then(value => {
        this.hasAnyAuthorityValue = value;
      });
    return this.hasAnyAuthorityValue;
  }

  @Inject('routeService') private routeService: () => RouteService;
  private removeId: number = null;
  public routes: IRoute[] = [];
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
}
