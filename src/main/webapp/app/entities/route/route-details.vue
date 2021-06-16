<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="route">
        <h2 class="jh-entity-heading" data-cy="routeDetailsHeading"><span>Route</span> {{ route.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Uuid</span>
          </dt>
          <dd>
            <span>{{ route.uuid }}</span>
          </dd>
          <dt>
            <span>Planned Arrival</span>
          </dt>
          <dd>
            <span>{{ route.plannedArrival | formatDate }}</span>
          </dd>
          <dt>
            <span>Planned Departure</span>
          </dt>
          <dd>
            <span>{{ route.plannedDeparture | formatDate }}</span>
          </dd>
          <dt>
            <span>Actual Arrival</span>
          </dt>
          <dd>
            <span>{{ route.actualArrival | formatDate }}</span>
          </dd>
          <dt>
            <span>Actual Departure</span>
          </dt>
          <dd>
            <span>{{ route.actualDeparture | formatDate }}</span>
          </dd>
          <dt>
            <span>Time Registration</span>
          </dt>
          <dd>
            <span>{{ route.timeRegistration | formatDate }}</span>
          </dd>
          <dt>
            <span>Platform</span>
          </dt>
          <dd>
            <span>{{ route.platform }}</span>
          </dd>
          <dt>
            <span>Rout Status</span>
          </dt>
          <dd>
            <span>{{ route.routStatus }}</span>
          </dd>
          <dt>
            <span>Description</span>
          </dt>
          <dd>
            <span>{{ route.description }}</span>
          </dd>
          <dt>
            <span>Bus</span>
          </dt>
          <dd>
            <div v-if="route.bus">
              <router-link :to="{ name: 'BusView', params: { busId: route.bus.id } }">{{ route.bus.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="route.id" :to="{ name: 'RouteEdit', params: { routeId: route.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./route-details.component.ts"></script>
