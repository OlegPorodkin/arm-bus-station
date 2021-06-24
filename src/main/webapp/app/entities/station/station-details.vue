<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="station">
        <h2 class="jh-entity-heading" data-cy="stationDetailsHeading"><span>Station</span> {{ station.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Uuid</span>
          </dt>
          <dd>
            <span>{{ station.uuid }}</span>
          </dd>
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ station.name }}</span>
          </dd>
          <dt>
            <span>Description</span>
          </dt>
          <dd>
            <span>{{ station.description }}</span>
          </dd>
          <dt>
            <span>Okato</span>
          </dt>
          <dd>
            <span>{{ station.okato }}</span>
          </dd>
          <dt>
            <span>Street</span>
          </dt>
          <dd>
            <span>{{ station.street }}</span>
          </dd>
          <dt>
            <span>Longitude</span>
          </dt>
          <dd>
            <span>{{ station.longitude }}</span>
          </dd>
          <dt>
            <span>Latitude</span>
          </dt>
          <dd>
            <span>{{ station.latitude }}</span>
          </dd>
          <dt>
            <span>Distance</span>
          </dt>
          <dd>
            <span>{{ station.distance }}</span>
          </dd>
          <dt>
            <span>Next Station</span>
          </dt>
          <dd>
            <div v-if="station.nextStation">
              <router-link :to="{ name: 'StationView', params: { stationId: station.nextStation.id } }">{{
                station.nextStation.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Type Object</span>
          </dt>
          <dd>
            <div v-if="station.typeObject">
              <router-link :to="{ name: 'TypeObjectView', params: { typeObjectId: station.typeObject.id } }">{{
                station.typeObject.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Region</span>
          </dt>
          <dd>
            <div v-if="station.region">
              <router-link :to="{ name: 'RegionView', params: { regionId: station.region.id } }">{{ station.region.name }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="station.id" :to="{ name: 'StationEdit', params: { stationId: station.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./station-details.component.ts"></script>
