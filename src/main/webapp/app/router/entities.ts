import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Bus = () => import('@/entities/bus/bus.vue');
// prettier-ignore
const BusUpdate = () => import('@/entities/bus/bus-update.vue');
// prettier-ignore
const BusDetails = () => import('@/entities/bus/bus-details.vue');
// prettier-ignore
const Counterpart = () => import('@/entities/counterpart/counterpart.vue');
// prettier-ignore
const CounterpartUpdate = () => import('@/entities/counterpart/counterpart-update.vue');
// prettier-ignore
const CounterpartDetails = () => import('@/entities/counterpart/counterpart-details.vue');
// prettier-ignore
const Country = () => import('@/entities/country/country.vue');
// prettier-ignore
const CountryUpdate = () => import('@/entities/country/country-update.vue');
// prettier-ignore
const CountryDetails = () => import('@/entities/country/country-details.vue');
// prettier-ignore
const Driver = () => import('@/entities/driver/driver.vue');
// prettier-ignore
const DriverUpdate = () => import('@/entities/driver/driver-update.vue');
// prettier-ignore
const DriverDetails = () => import('@/entities/driver/driver-details.vue');
// prettier-ignore
const Passenger = () => import('@/entities/passenger/passenger.vue');
// prettier-ignore
const PassengerUpdate = () => import('@/entities/passenger/passenger-update.vue');
// prettier-ignore
const PassengerDetails = () => import('@/entities/passenger/passenger-details.vue');
// prettier-ignore
const Passport = () => import('@/entities/passport/passport.vue');
// prettier-ignore
const PassportUpdate = () => import('@/entities/passport/passport-update.vue');
// prettier-ignore
const PassportDetails = () => import('@/entities/passport/passport-details.vue');
// prettier-ignore
const Region = () => import('@/entities/region/region.vue');
// prettier-ignore
const RegionUpdate = () => import('@/entities/region/region-update.vue');
// prettier-ignore
const RegionDetails = () => import('@/entities/region/region-details.vue');
// prettier-ignore
const Route = () => import('@/entities/route/route.vue');
// prettier-ignore
const RouteUpdate = () => import('@/entities/route/route-update.vue');
// prettier-ignore
const RouteDetails = () => import('@/entities/route/route-details.vue');
// prettier-ignore
const Station = () => import('@/entities/station/station.vue');
// prettier-ignore
const StationUpdate = () => import('@/entities/station/station-update.vue');
// prettier-ignore
const StationDetails = () => import('@/entities/station/station-details.vue');
// prettier-ignore
const Ticket = () => import('@/entities/ticket/ticket.vue');
// prettier-ignore
const TicketUpdate = () => import('@/entities/ticket/ticket-update.vue');
// prettier-ignore
const TicketDetails = () => import('@/entities/ticket/ticket-details.vue');
// prettier-ignore
const TypeObject = () => import('@/entities/type-object/type-object.vue');
// prettier-ignore
const TypeObjectUpdate = () => import('@/entities/type-object/type-object-update.vue');
// prettier-ignore
const TypeObjectDetails = () => import('@/entities/type-object/type-object-details.vue');
// prettier-ignore
const DispatcherDetails = () => import('@/dispatcher/dispatcher-details.vue');
// prettier-ignore
const DispatcherSendingDetails = () => import('@/dispatcher/dispatcher-sending-details.vue');

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
  {
    path: '/counterpart',
    name: 'Counterpart',
    component: Counterpart,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/counterpart/new',
    name: 'CounterpartCreate',
    component: CounterpartUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/counterpart/:counterpartId/edit',
    name: 'CounterpartEdit',
    component: CounterpartUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/counterpart/:counterpartId/view',
    name: 'CounterpartView',
    component: CounterpartDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/country',
    name: 'Country',
    component: Country,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/country/new',
    name: 'CountryCreate',
    component: CountryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/country/:countryId/edit',
    name: 'CountryEdit',
    component: CountryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/country/:countryId/view',
    name: 'CountryView',
    component: CountryDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/driver',
    name: 'Driver',
    component: Driver,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/driver/new',
    name: 'DriverCreate',
    component: DriverUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/driver/:driverId/edit',
    name: 'DriverEdit',
    component: DriverUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/driver/:driverId/view',
    name: 'DriverView',
    component: DriverDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/passenger',
    name: 'Passenger',
    component: Passenger,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/passenger/new',
    name: 'PassengerCreate',
    component: PassengerUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/passenger/:passengerId/edit',
    name: 'PassengerEdit',
    component: PassengerUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/passenger/:passengerId/view',
    name: 'PassengerView',
    component: PassengerDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/passport',
    name: 'Passport',
    component: Passport,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/passport/new',
    name: 'PassportCreate',
    component: PassportUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/passport/:passportId/edit',
    name: 'PassportEdit',
    component: PassportUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/passport/:passportId/view',
    name: 'PassportView',
    component: PassportDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/region',
    name: 'Region',
    component: Region,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/region/new',
    name: 'RegionCreate',
    component: RegionUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/region/:regionId/edit',
    name: 'RegionEdit',
    component: RegionUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/region/:regionId/view',
    name: 'RegionView',
    component: RegionDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/route',
    name: 'Route',
    component: Route,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/route/new',
    name: 'RouteCreate',
    component: RouteUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/route/:routeId/edit',
    name: 'RouteEdit',
    component: RouteUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/route/:routeId/view',
    name: 'RouteView',
    component: RouteDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/station',
    name: 'Station',
    component: Station,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/station/new',
    name: 'StationCreate',
    component: StationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/station/:stationId/edit',
    name: 'StationEdit',
    component: StationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/station/:stationId/view',
    name: 'StationView',
    component: StationDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ticket',
    name: 'Ticket',
    component: Ticket,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ticket/new',
    name: 'TicketCreate',
    component: TicketUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ticket/:ticketId/edit',
    name: 'TicketEdit',
    component: TicketUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ticket/:ticketId/view',
    name: 'TicketView',
    component: TicketDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/type-object',
    name: 'TypeObject',
    component: TypeObject,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/type-object/new',
    name: 'TypeObjectCreate',
    component: TypeObjectUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/type-object/:typeObjectId/edit',
    name: 'TypeObjectEdit',
    component: TypeObjectUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/type-object/:typeObjectId/view',
    name: 'TypeObjectView',
    component: TypeObjectDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dispatcher-details/:routeId/view',
    name: 'DispatcherDetails',
    component: DispatcherDetails,
    meta: { authorities: [Authority.USER] },
    props: true,
  },
  {
    path: '/sending-details/:routeId/view',
    name: 'DispatcherSendingDetails',
    component: DispatcherSendingDetails,
    meta: { authorities: [Authority.USER] },
    props: true,
  },

  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
