import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPassport } from '@/shared/model/passport.model';

import PassportService from './passport.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Passport extends Vue {
  @Inject('passportService') private passportService: () => PassportService;
  private removeId: number = null;

  public passports: IPassport[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPassports();
  }

  public clear(): void {
    this.retrieveAllPassports();
  }

  public retrieveAllPassports(): void {
    this.isFetching = true;

    this.passportService()
      .retrieve()
      .then(
        res => {
          this.passports = res.data;
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

  public prepareRemove(instance: IPassport): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePassport(): void {
    this.passportService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Passport is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPassports();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
