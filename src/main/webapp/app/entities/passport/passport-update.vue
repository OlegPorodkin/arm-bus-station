<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="busStationArmApp.passport.home.createOrEditLabel" data-cy="PassportCreateUpdateHeading">Create or edit a Passport</h2>
        <div>
          <div class="form-group" v-if="passport.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="passport.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="passport-uuid">Uuid</label>
            <input
              type="text"
              class="form-control"
              name="uuid"
              id="passport-uuid"
              data-cy="uuid"
              :class="{ valid: !$v.passport.uuid.$invalid, invalid: $v.passport.uuid.$invalid }"
              v-model="$v.passport.uuid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="passport-serial">Serial</label>
            <input
              type="number"
              class="form-control"
              name="serial"
              id="passport-serial"
              data-cy="serial"
              :class="{ valid: !$v.passport.serial.$invalid, invalid: $v.passport.serial.$invalid }"
              v-model.number="$v.passport.serial.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="passport-number">Number</label>
            <input
              type="number"
              class="form-control"
              name="number"
              id="passport-number"
              data-cy="number"
              :class="{ valid: !$v.passport.number.$invalid, invalid: $v.passport.number.$invalid }"
              v-model.number="$v.passport.number.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="passport-dateOfIssue">Date Of Issue</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="passport-dateOfIssue"
                  v-model="$v.passport.dateOfIssue.$model"
                  name="dateOfIssue"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="passport-dateOfIssue"
                data-cy="dateOfIssue"
                type="text"
                class="form-control"
                name="dateOfIssue"
                :class="{ valid: !$v.passport.dateOfIssue.$invalid, invalid: $v.passport.dateOfIssue.$invalid }"
                v-model="$v.passport.dateOfIssue.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="passport-whoIssued">Who Issued</label>
            <input
              type="text"
              class="form-control"
              name="whoIssued"
              id="passport-whoIssued"
              data-cy="whoIssued"
              :class="{ valid: !$v.passport.whoIssued.$invalid, invalid: $v.passport.whoIssued.$invalid }"
              v-model="$v.passport.whoIssued.$model"
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
            :disabled="$v.passport.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./passport-update.component.ts"></script>
