import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICounterpart } from '@/shared/model/counterpart.model';
import CounterpartService from './counterpart.service';

@Component
export default class CounterpartDetails extends Vue {
  @Inject('counterpartService') private counterpartService: () => CounterpartService;
  public counterpart: ICounterpart = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.counterpartId) {
        vm.retrieveCounterpart(to.params.counterpartId);
      }
    });
  }

  public retrieveCounterpart(counterpartId) {
    this.counterpartService()
      .find(counterpartId)
      .then(res => {
        this.counterpart = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
