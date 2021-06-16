<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="busStationArmApp.country.home.createOrEditLabel" data-cy="CountryCreateUpdateHeading">Create or edit a Country</h2>
        <div>
          <div class="form-group" v-if="country.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="country.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="country-uuid">Uuid</label>
            <input
              type="text"
              class="form-control"
              name="uuid"
              id="country-uuid"
              data-cy="uuid"
              :class="{ valid: !$v.country.uuid.$invalid, invalid: $v.country.uuid.$invalid }"
              v-model="$v.country.uuid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="country-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="country-name"
              data-cy="name"
              :class="{ valid: !$v.country.name.$invalid, invalid: $v.country.name.$invalid }"
              v-model="$v.country.name.$model"
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
            :disabled="$v.country.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./country-update.component.ts"></script>
