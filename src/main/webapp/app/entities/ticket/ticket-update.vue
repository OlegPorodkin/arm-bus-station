<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="busStationArmApp.ticket.home.createOrEditLabel" data-cy="TicketCreateUpdateHeading">Create or edit a Ticket</h2>
        <div>
          <div class="form-group" v-if="ticket.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="ticket.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ticket-uuid">Uuid</label>
            <input
              type="text"
              class="form-control"
              name="uuid"
              id="ticket-uuid"
              data-cy="uuid"
              :class="{ valid: !$v.ticket.uuid.$invalid, invalid: $v.ticket.uuid.$invalid }"
              v-model="$v.ticket.uuid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ticket-place">Place</label>
            <input
              type="number"
              class="form-control"
              name="place"
              id="ticket-place"
              data-cy="place"
              :class="{ valid: !$v.ticket.place.$invalid, invalid: $v.ticket.place.$invalid }"
              v-model.number="$v.ticket.place.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ticket-serial">Serial</label>
            <input
              type="number"
              class="form-control"
              name="serial"
              id="ticket-serial"
              data-cy="serial"
              :class="{ valid: !$v.ticket.serial.$invalid, invalid: $v.ticket.serial.$invalid }"
              v-model.number="$v.ticket.serial.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ticket-number">Number</label>
            <input
              type="number"
              class="form-control"
              name="number"
              id="ticket-number"
              data-cy="number"
              :class="{ valid: !$v.ticket.number.$invalid, invalid: $v.ticket.number.$invalid }"
              v-model.number="$v.ticket.number.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ticket-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="ticket-type"
              data-cy="type"
              :class="{ valid: !$v.ticket.type.$invalid, invalid: $v.ticket.type.$invalid }"
              v-model="$v.ticket.type.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ticket-dateDeparture">Date Departure</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="ticket-dateDeparture"
                  v-model="$v.ticket.dateDeparture.$model"
                  name="dateDeparture"
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
                id="ticket-dateDeparture"
                data-cy="dateDeparture"
                type="text"
                class="form-control"
                name="dateDeparture"
                :class="{ valid: !$v.ticket.dateDeparture.$invalid, invalid: $v.ticket.dateDeparture.$invalid }"
                v-model="$v.ticket.dateDeparture.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ticket-price">Price</label>
            <input
              type="number"
              class="form-control"
              name="price"
              id="ticket-price"
              data-cy="price"
              :class="{ valid: !$v.ticket.price.$invalid, invalid: $v.ticket.price.$invalid }"
              v-model.number="$v.ticket.price.$model"
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
            :disabled="$v.ticket.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./ticket-update.component.ts"></script>
