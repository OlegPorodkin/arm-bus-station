import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPassport } from '@/shared/model/passport.model';
import PassportService from './passport.service';

@Component
export default class PassportDetails extends Vue {
  @Inject('passportService') private passportService: () => PassportService;
  public passport: IPassport = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.passportId) {
        vm.retrievePassport(to.params.passportId);
      }
    });
  }

  public retrievePassport(passportId) {
    this.passportService()
      .find(passportId)
      .then(res => {
        this.passport = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
