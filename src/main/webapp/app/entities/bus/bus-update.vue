<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="busStationArmApp.bus.home.createOrEditLabel" data-cy="BusCreateUpdateHeading">Create or edit a Bus</h2>
        <div>
          <div class="form-group" v-if="bus.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="bus.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="bus-model">Model</label>
            <input
              type="text"
              class="form-control"
              name="model"
              id="bus-model"
              data-cy="model"
              :class="{ valid: !$v.bus.model.$invalid, invalid: $v.bus.model.$invalid }"
              v-model="$v.bus.model.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="bus-number">Number</label>
            <input
              type="text"
              class="form-control"
              name="number"
              id="bus-number"
              data-cy="number"
              :class="{ valid: !$v.bus.number.$invalid, invalid: $v.bus.number.$invalid }"
              v-model="$v.bus.number.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="bus-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="bus-description"
              data-cy="description"
              :class="{ valid: !$v.bus.description.$invalid, invalid: $v.bus.description.$invalid }"
              v-model="$v.bus.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="bus-passengerPlaces">Passenger Places</label>
            <input
              type="number"
              class="form-control"
              name="passengerPlaces"
              id="bus-passengerPlaces"
              data-cy="passengerPlaces"
              :class="{ valid: !$v.bus.passengerPlaces.$invalid, invalid: $v.bus.passengerPlaces.$invalid }"
              v-model.number="$v.bus.passengerPlaces.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="bus-driver">Driver</label>
            <select class="form-control" id="bus-driver" data-cy="driver" name="driver" v-model="bus.driver">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="bus.driver && driverOption.id === bus.driver.id ? bus.driver : driverOption"
                v-for="driverOption in drivers"
                :key="driverOption.id"
              >
                {{ driverOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="bus-counterpart">Counterpart</label>
            <select class="form-control" id="bus-counterpart" data-cy="counterpart" name="counterpart" v-model="bus.counterpart">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="bus.counterpart && counterpartOption.id === bus.counterpart.id ? bus.counterpart : counterpartOption"
                v-for="counterpartOption in counterparts"
                :key="counterpartOption.id"
              >
                {{ counterpartOption.id }}
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
            :disabled="$v.bus.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./bus-update.component.ts"></script>
