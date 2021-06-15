import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Bus = () => import('@/entities/bus/bus.vue');
// prettier-ignore
const BusUpdate = () => import('@/entities/bus/bus-update.vue');
// prettier-ignore
const BusDetails = () => import('@/entities/bus/bus-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/bus',
    name: 'Bus',
    component: Bus,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/bus/new',
    name: 'BusCreate',
    component: BusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/bus/:busId/edit',
    name: 'BusEdit',
    component: BusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/bus/:busId/view',
    name: 'BusView',
    component: BusDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
