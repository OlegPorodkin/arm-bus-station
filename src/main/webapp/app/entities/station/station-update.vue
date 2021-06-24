<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="busStationArmApp.station.home.createOrEditLabel" data-cy="StationCreateUpdateHeading">Create or edit a Station</h2>
        <div>
          <div class="form-group" v-if="station.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="station.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="station-uuid">Uuid</label>
            <input
              type="text"
              class="form-control"
              name="uuid"
              id="station-uuid"
              data-cy="uuid"
              :class="{ valid: !$v.station.uuid.$invalid, invalid: $v.station.uuid.$invalid }"
              v-model="$v.station.uuid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="station-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="station-name"
              data-cy="name"
              :class="{ valid: !$v.station.name.$invalid, invalid: $v.station.name.$invalid }"
              v-model="$v.station.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="station-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="station-description"
              data-cy="description"
              :class="{ valid: !$v.station.description.$invalid, invalid: $v.station.description.$invalid }"
              v-model="$v.station.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="station-okato">Okato</label>
            <input
              type="number"
              class="form-control"
              name="okato"
              id="station-okato"
              data-cy="okato"
              :class="{ valid: !$v.station.okato.$invalid, invalid: $v.station.okato.$invalid }"
              v-model.number="$v.station.okato.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="station-street">Street</label>
            <input
              type="text"
              class="form-control"
              name="street"
              id="station-street"
              data-cy="street"
              :class="{ valid: !$v.station.street.$invalid, invalid: $v.station.street.$invalid }"
              v-model="$v.station.street.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="station-longitude">Longitude</label>
            <input
              type="number"
              class="form-control"
              name="longitude"
              id="station-longitude"
              data-cy="longitude"
              :class="{ valid: !$v.station.longitude.$invalid, invalid: $v.station.longitude.$invalid }"
              v-model.number="$v.station.longitude.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="station-latitude">Latitude</label>
            <input
              type="number"
              class="form-control"
              name="latitude"
              id="station-latitude"
              data-cy="latitude"
              :class="{ valid: !$v.station.latitude.$invalid, invalid: $v.station.latitude.$invalid }"
              v-model.number="$v.station.latitude.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="station-distance">Distance</label>
            <input
              type="number"
              class="form-control"
              name="distance"
              id="station-distance"
              data-cy="distance"
              :class="{ valid: !$v.station.distance.$invalid, invalid: $v.station.distance.$invalid }"
              v-model.number="$v.station.distance.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="station-nextStation">Next Station</label>
            <select class="form-control" id="station-nextStation" data-cy="nextStation" name="nextStation" v-model="station.nextStation">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="station.nextStation && stationOption.id === station.nextStation.id ? station.nextStation : stationOption"
                v-for="stationOption in nextStations"
                :key="stationOption.id"
              >
                {{ stationOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="station-typeObject">Type Object</label>
            <select class="form-control" id="station-typeObject" data-cy="typeObject" name="typeObject" v-model="station.typeObject">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="station.typeObject && typeObjectOption.id === station.typeObject.id ? station.typeObject : typeObjectOption"
                v-for="typeObjectOption in typeObjects"
                :key="typeObjectOption.id"
              >
                {{ typeObjectOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="station-region">Region</label>
            <select class="form-control" id="station-region" data-cy="region" name="region" v-model="station.region">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="station.region && regionOption.id === station.region.id ? station.region : regionOption"
                v-for="regionOption in regions"
                :key="regionOption.id"
              >
                {{ regionOption.name }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.station.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./station-update.component.ts"></script>
