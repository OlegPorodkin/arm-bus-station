import { Component, Vue, Inject } from 'vue-property-decorator';

import CountryService from '@/entities/country/country.service';
import { ICountry } from '@/shared/model/country.model';

import { IRegion, Region } from '@/shared/model/region.model';
import RegionService from './region.service';

const validations: any = {
  region: {
    uuid: {},
    name: {},
    description: {},
  },
};

@Component({
  validations,
})
export default class RegionUpdate extends Vue {
  @Inject('regionService') private regionService: () => RegionService;
  public region: IRegion = new Region();

  @Inject('countryService') private countryService: () => CountryService;

  public countries: ICountry[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.regionId) {
        vm.retrieveRegion(to.params.regionId);
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
    if (this.region.id) {
      this.regionService()
        .update(this.region)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Region is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.regionService()
        .create(this.region)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Region is created with identifier ' + param.id;
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

  public retrieveRegion(regionId): void {
    this.regionService()
      .find(regionId)
      .then(res => {
        this.region = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.countryService()
      .retrieve()
      .then(res => {
        this.countries = res.data;
      });
  }
}
