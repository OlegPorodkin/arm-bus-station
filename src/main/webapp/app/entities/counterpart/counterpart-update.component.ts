import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICounterpart, Counterpart } from '@/shared/model/counterpart.model';
import CounterpartService from './counterpart.service';

const validations: any = {
  counterpart: {
    uuid: {},
    name: {},
    shortName: {},
    tin: {},
    ogrn: {},
    egis_otb_rf: {},
    taxSystem: {},
    address: {},
    description: {},
    country: {},
  },
};

@Component({
  validations,
})
export default class CounterpartUpdate extends Vue {
  @Inject('counterpartService') private counterpartService: () => CounterpartService;
  public counterpart: ICounterpart = new Counterpart();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.counterpartId) {
        vm.retrieveCounterpart(to.params.counterpartId);
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
    if (this.counterpart.id) {
      this.counterpartService()
        .update(this.counterpart)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Counterpart is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.counterpartService()
        .create(this.counterpart)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Counterpart is created with identifier ' + param.id;
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

  public retrieveCounterpart(counterpartId): void {
    this.counterpartService()
      .find(counterpartId)
      .then(res => {
        this.counterpart = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
