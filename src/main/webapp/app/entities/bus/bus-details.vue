<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="bus">
        <h2 class="jh-entity-heading" data-cy="busDetailsHeading"><span>Bus</span> {{ bus.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Model</span>
          </dt>
          <dd>
            <span>{{ bus.model }}</span>
          </dd>
          <dt>
            <span>Number</span>
          </dt>
          <dd>
            <span>{{ bus.number }}</span>
          </dd>
          <dt>
            <span>Description</span>
          </dt>
          <dd>
            <span>{{ bus.description }}</span>
          </dd>
          <dt>
            <span>Passenger Places</span>
          </dt>
          <dd>
            <span>{{ bus.passengerPlaces }}</span>
          </dd>
          <dt>
            <span>Driver</span>
          </dt>
          <dd>
            <div v-if="bus.driver">
              <router-link :to="{ name: 'DriverView', params: { driverId: bus.driver.id } }">{{ bus.driver.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Counterpart</span>
          </dt>
          <dd>
            <div v-if="bus.counterpart">
              <router-link :to="{ name: 'CounterpartView', params: { counterpartId: bus.counterpart.id } }">{{
                bus.counterpart.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="bus.id" :to="{ name: 'BusEdit', params: { busId: bus.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./bus-details.component.ts"></script>
