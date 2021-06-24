<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="passenger">
        <h2 class="jh-entity-heading" data-cy="passengerDetailsHeading"><span>Passenger</span> {{ passenger.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Uuid</span>
          </dt>
          <dd>
            <span>{{ passenger.uuid }}</span>
          </dd>
          <dt>
            <span>First Name</span>
          </dt>
          <dd>
            <span>{{ passenger.firstName }}</span>
          </dd>
          <dt>
            <span>Last Name</span>
          </dt>
          <dd>
            <span>{{ passenger.lastName }}</span>
          </dd>
          <dt>
            <span>Patronymic</span>
          </dt>
          <dd>
            <span>{{ passenger.patronymic }}</span>
          </dd>
          <dt>
            <span>Birthday</span>
          </dt>
          <dd>
            <span>{{ passenger.birthday }}</span>
          </dd>
          <dt>
            <span>Boarding Status</span>
          </dt>
          <dd>
            <span>{{ passenger.boardingStatus }}</span>
          </dd>
          <dt>
            <span>Sex</span>
          </dt>
          <dd>
            <span>{{ passenger.sex }}</span>
          </dd>
          <dt>
            <span>Phone</span>
          </dt>
          <dd>
            <span>{{ passenger.phone }}</span>
          </dd>
          <dt>
            <span>Citizenship</span>
          </dt>
          <dd>
            <span>{{ passenger.citizenship }}</span>
          </dd>
          <dt>
            <span>Route</span>
          </dt>
          <dd>
            <div v-if="passenger.route">
              <router-link :to="{ name: 'RouteView', params: { routeId: passenger.route.id } }">{{ passenger.route.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Passport</span>
          </dt>
          <dd>
            <div v-if="passenger.passport">
              <router-link :to="{ name: 'PassportView', params: { passportId: passenger.passport.id } }">{{
                passenger.passport.id_passport
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Ticket</span>
          </dt>
          <dd>
            <div v-if="passenger.ticket">
              <router-link :to="{ name: 'TicketView', params: { ticketId: passenger.ticket.id } }">{{
                passenger.ticket.id_ticket
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="passenger.id"
          :to="{ name: 'PassengerEdit', params: { passengerId: passenger.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./passenger-details.component.ts"></script>
