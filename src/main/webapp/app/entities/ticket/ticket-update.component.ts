import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITicket, Ticket } from '@/shared/model/ticket.model';
import TicketService from './ticket.service';

const validations: any = {
  ticket: {
    uuid: {},
    place: {},
    serial: {},
    number: {},
    type: {},
    dateDeparture: {},
    price: {},
  },
};

@Component({
  validations,
})
export default class TicketUpdate extends Vue {
  @Inject('ticketService') private ticketService: () => TicketService;
  public ticket: ITicket = new Ticket();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ticketId) {
        vm.retrieveTicket(to.params.ticketId);
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
    if (this.ticket.id) {
      this.ticketService()
        .update(this.ticket)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Ticket is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.ticketService()
        .create(this.ticket)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Ticket is created with identifier ' + param.id;
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

  public retrieveTicket(ticketId): void {
    this.ticketService()
      .find(ticketId)
      .then(res => {
        this.ticket = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
