<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="busStationArmApp.route.home.createOrEditLabel" data-cy="RouteCreateUpdateHeading">Create or edit a Route</h2>
        <div>
          <div class="form-group" v-if="route.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="route.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="route-uuid">Uuid</label>
            <input
              type="text"
              class="form-control"
              name="uuid"
              id="route-uuid"
              data-cy="uuid"
              :class="{ valid: !$v.route.uuid.$invalid, invalid: $v.route.uuid.$invalid }"
              v-model="$v.route.uuid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="route-plannedArrival">Planned Arrival</label>
            <div class="d-flex">
              <input
                id="route-plannedArrival"
                data-cy="plannedArrival"
                type="datetime-local"
                class="form-control"
                name="plannedArrival"
                :class="{ valid: !$v.route.plannedArrival.$invalid, invalid: $v.route.plannedArrival.$invalid }"
                :value="convertDateTimeFromServer($v.route.plannedArrival.$model)"
                @change="updateZonedDateTimeField('plannedArrival', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="route-plannedDeparture">Planned Departure</label>
            <div class="d-flex">
              <input
                id="route-plannedDeparture"
                data-cy="plannedDeparture"
                type="datetime-local"
                class="form-control"
                name="plannedDeparture"
                :class="{ valid: !$v.route.plannedDeparture.$invalid, invalid: $v.route.plannedDeparture.$invalid }"
                :value="convertDateTimeFromServer($v.route.plannedDeparture.$model)"
                @change="updateZonedDateTimeField('plannedDeparture', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="route-actualArrival">Actual Arrival</label>
            <div class="d-flex">
              <input
                id="route-actualArrival"
                data-cy="actualArrival"
                type="datetime-local"
                class="form-control"
                name="actualArrival"
                :class="{ valid: !$v.route.actualArrival.$invalid, invalid: $v.route.actualArrival.$invalid }"
                :value="convertDateTimeFromServer($v.route.actualArrival.$model)"
                @change="updateZonedDateTimeField('actualArrival', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="route-actualDeparture">Actual Departure</label>
            <div class="d-flex">
              <input
                id="route-actualDeparture"
                data-cy="actualDeparture"
                type="datetime-local"
                class="form-control"
                name="actualDeparture"
                :class="{ valid: !$v.route.actualDeparture.$invalid, invalid: $v.route.actualDeparture.$invalid }"
                :value="convertDateTimeFromServer($v.route.actualDeparture.$model)"
                @change="updateZonedDateTimeField('actualDeparture', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="route-timeRegistration">Time Registration</label>
            <div class="d-flex">
              <input
                id="route-timeRegistration"
                data-cy="timeRegistration"
                type="datetime-local"
                class="form-control"
                name="timeRegistration"
                :class="{ valid: !$v.route.timeRegistration.$invalid, invalid: $v.route.timeRegistration.$invalid }"
                :value="convertDateTimeFromServer($v.route.timeRegistration.$model)"
                @change="updateInstantField('timeRegistration', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="route-platform">Platform</label>
            <input
              type="text"
              class="form-control"
              name="platform"
              id="route-platform"
              data-cy="platform"
              :class="{ valid: !$v.route.platform.$invalid, invalid: $v.route.platform.$invalid }"
              v-model="$v.route.platform.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="route-routStatus">Rout Status</label>
            <input
              type="text"
              class="form-control"
              name="routStatus"
              id="route-routStatus"
              data-cy="routStatus"
              :class="{ valid: !$v.route.routStatus.$invalid, invalid: $v.route.routStatus.$invalid }"
              v-model="$v.route.routStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="route-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="route-description"
              data-cy="description"
              :class="{ valid: !$v.route.description.$invalid, invalid: $v.route.description.$invalid }"
              v-model="$v.route.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="route-bus">Bus</label>
            <select class="form-control" id="route-bus" data-cy="bus" name="bus" v-model="route.bus">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="route.bus && busOption.id === route.bus.id ? route.bus : busOption"
                v-for="busOption in buses"
                :key="busOption.id"
              >
                {{ busOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="route-station">Station</label>
            <select class="form-control" id="route-station" data-cy="station" name="station" v-model="route.station">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="route.station && stationOption.id === route.station.id ? route.station : stationOption"
                v-for="stationOption in stations"
                :key="stationOption.id"
              >
                {{ stationOption.name }}
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
            :disabled="$v.route.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./route-update.component.ts"></script>
