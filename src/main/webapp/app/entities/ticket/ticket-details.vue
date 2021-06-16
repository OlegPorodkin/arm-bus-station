<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="ticket">
        <h2 class="jh-entity-heading" data-cy="ticketDetailsHeading"><span>Ticket</span> {{ ticket.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Uuid</span>
          </dt>
          <dd>
            <span>{{ ticket.uuid }}</span>
          </dd>
          <dt>
            <span>Place</span>
          </dt>
          <dd>
            <span>{{ ticket.place }}</span>
          </dd>
          <dt>
            <span>Serial</span>
          </dt>
          <dd>
            <span>{{ ticket.serial }}</span>
          </dd>
          <dt>
            <span>Number</span>
          </dt>
          <dd>
            <span>{{ ticket.number }}</span>
          </dd>
          <dt>
            <span>Type</span>
          </dt>
          <dd>
            <span>{{ ticket.type }}</span>
          </dd>
          <dt>
            <span>Date Departure</span>
          </dt>
          <dd>
            <span>{{ ticket.dateDeparture }}</span>
          </dd>
          <dt>
            <span>Price</span>
          </dt>
          <dd>
            <span>{{ ticket.price }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="ticket.id" :to="{ name: 'TicketEdit', params: { ticketId: ticket.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./ticket-details.component.ts"></script>
