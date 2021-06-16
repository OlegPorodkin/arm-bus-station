<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="busStationArmApp.driver.home.createOrEditLabel" data-cy="DriverCreateUpdateHeading">Create or edit a Driver</h2>
        <div>
          <div class="form-group" v-if="driver.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="driver.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="driver-uuid">Uuid</label>
            <input
              type="text"
              class="form-control"
              name="uuid"
              id="driver-uuid"
              data-cy="uuid"
              :class="{ valid: !$v.driver.uuid.$invalid, invalid: $v.driver.uuid.$invalid }"
              v-model="$v.driver.uuid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="driver-firstName">First Name</label>
            <input
              type="text"
              class="form-control"
              name="firstName"
              id="driver-firstName"
              data-cy="firstName"
              :class="{ valid: !$v.driver.firstName.$invalid, invalid: $v.driver.firstName.$invalid }"
              v-model="$v.driver.firstName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="driver-lastName">Last Name</label>
            <input
              type="text"
              class="form-control"
              name="lastName"
              id="driver-lastName"
              data-cy="lastName"
              :class="{ valid: !$v.driver.lastName.$invalid, invalid: $v.driver.lastName.$invalid }"
              v-model="$v.driver.lastName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="driver-driverLicense">Driver License</label>
            <input
              type="text"
              class="form-control"
              name="driverLicense"
              id="driver-driverLicense"
              data-cy="driverLicense"
              :class="{ valid: !$v.driver.driverLicense.$invalid, invalid: $v.driver.driverLicense.$invalid }"
              v-model="$v.driver.driverLicense.$model"
            />
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
            :disabled="$v.driver.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./driver-update.component.ts"></script>
