<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="driver">
        <h2 class="jh-entity-heading" data-cy="driverDetailsHeading"><span>Driver</span> {{ driver.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Uuid</span>
          </dt>
          <dd>
            <span>{{ driver.uuid }}</span>
          </dd>
          <dt>
            <span>First Name</span>
          </dt>
          <dd>
            <span>{{ driver.firstName }}</span>
          </dd>
          <dt>
            <span>Last Name</span>
          </dt>
          <dd>
            <span>{{ driver.lastName }}</span>
          </dd>
          <dt>
            <span>Driver License</span>
          </dt>
          <dd>
            <span>{{ driver.driverLicense }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="driver.id" :to="{ name: 'DriverEdit', params: { driverId: driver.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./driver-details.component.ts"></script>
