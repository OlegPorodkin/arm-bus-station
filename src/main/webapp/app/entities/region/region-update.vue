<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="busStationArmApp.region.home.createOrEditLabel" data-cy="RegionCreateUpdateHeading">Create or edit a Region</h2>
        <div>
          <div class="form-group" v-if="region.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="region.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="region-uuid">Uuid</label>
            <input
              type="text"
              class="form-control"
              name="uuid"
              id="region-uuid"
              data-cy="uuid"
              :class="{ valid: !$v.region.uuid.$invalid, invalid: $v.region.uuid.$invalid }"
              v-model="$v.region.uuid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="region-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="region-name"
              data-cy="name"
              :class="{ valid: !$v.region.name.$invalid, invalid: $v.region.name.$invalid }"
              v-model="$v.region.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="region-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="region-description"
              data-cy="description"
              :class="{ valid: !$v.region.description.$invalid, invalid: $v.region.description.$invalid }"
              v-model="$v.region.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="region-country">Country</label>
            <select class="form-control" id="region-country" data-cy="country" name="country" v-model="region.country">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="region.country && countryOption.id === region.country.id ? region.country : countryOption"
                v-for="countryOption in countries"
                :key="countryOption.id"
              >
                {{ countryOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="region-countryOfLocation">Country Of Location</label>
            <select
              class="form-control"
              id="region-countryOfLocation"
              data-cy="countryOfLocation"
              name="countryOfLocation"
              v-model="region.countryOfLocation"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  region.countryOfLocation && countryOption.id === region.countryOfLocation.id ? region.countryOfLocation : countryOption
                "
                v-for="countryOption in countries"
                :key="countryOption.id"
              >
                {{ countryOption.id }}
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
            :disabled="$v.region.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./region-update.component.ts"></script>
