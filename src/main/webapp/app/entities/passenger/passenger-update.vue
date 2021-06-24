<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="busStationArmApp.passenger.home.createOrEditLabel" data-cy="PassengerCreateUpdateHeading">Create or edit a Passenger</h2>
        <div>
          <div class="form-group" v-if="passenger.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="passenger.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="passenger-uuid">Uuid</label>
            <input
              type="text"
              class="form-control"
              name="uuid"
              id="passenger-uuid"
              data-cy="uuid"
              :class="{ valid: !$v.passenger.uuid.$invalid, invalid: $v.passenger.uuid.$invalid }"
              v-model="$v.passenger.uuid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="passenger-firstName">First Name</label>
            <input
              type="text"
              class="form-control"
              name="firstName"
              id="passenger-firstName"
              data-cy="firstName"
              :class="{ valid: !$v.passenger.firstName.$invalid, invalid: $v.passenger.firstName.$invalid }"
              v-model="$v.passenger.firstName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="passenger-lastName">Last Name</label>
            <input
              type="text"
              class="form-control"
              name="lastName"
              id="passenger-lastName"
              data-cy="lastName"
              :class="{ valid: !$v.passenger.lastName.$invalid, invalid: $v.passenger.lastName.$invalid }"
              v-model="$v.passenger.lastName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="passenger-patronymic">Patronymic</label>
            <input
              type="text"
              class="form-control"
              name="patronymic"
              id="passenger-patronymic"
              data-cy="patronymic"
              :class="{ valid: !$v.passenger.patronymic.$invalid, invalid: $v.passenger.patronymic.$invalid }"
              v-model="$v.passenger.patronymic.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="passenger-birthday">Birthday</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="passenger-birthday"
                  v-model="$v.passenger.birthday.$model"
                  name="birthday"
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
                id="passenger-birthday"
                data-cy="birthday"
                type="text"
                class="form-control"
                name="birthday"
                :class="{ valid: !$v.passenger.birthday.$invalid, invalid: $v.passenger.birthday.$invalid }"
                v-model="$v.passenger.birthday.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="passenger-boardingStatus">Boarding Status</label>
            <input
              type="text"
              class="form-control"
              name="boardingStatus"
              id="passenger-boardingStatus"
              data-cy="boardingStatus"
              :class="{ valid: !$v.passenger.boardingStatus.$invalid, invalid: $v.passenger.boardingStatus.$invalid }"
              v-model="$v.passenger.boardingStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="passenger-sex">Sex</label>
            <input
              type="text"
              class="form-control"
              name="sex"
              id="passenger-sex"
              data-cy="sex"
              :class="{ valid: !$v.passenger.sex.$invalid, invalid: $v.passenger.sex.$invalid }"
              v-model="$v.passenger.sex.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="passenger-phone">Phone</label>
            <input
              type="text"
              class="form-control"
              name="phone"
              id="passenger-phone"
              data-cy="phone"
              :class="{ valid: !$v.passenger.phone.$invalid, invalid: $v.passenger.phone.$invalid }"
              v-model="$v.passenger.phone.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="passenger-citizenship">Citizenship</label>
            <input
              type="text"
              class="form-control"
              name="citizenship"
              id="passenger-citizenship"
              data-cy="citizenship"
              :class="{ valid: !$v.passenger.citizenship.$invalid, invalid: $v.passenger.citizenship.$invalid }"
              v-model="$v.passenger.citizenship.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="passenger-route">Route</label>
            <select class="form-control" id="passenger-route" data-cy="route" name="route" v-model="passenger.route">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="passenger.route && routeOption.id === passenger.route.id ? passenger.route : routeOption"
                v-for="routeOption in routes"
                :key="routeOption.id"
              >
                {{ routeOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="passenger-passport">Passport</label>
            <select class="form-control" id="passenger-passport" data-cy="passport" name="passport" v-model="passenger.passport">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="passenger.passport && passportOption.id === passenger.passport.id ? passenger.passport : passportOption"
                v-for="passportOption in passports"
                :key="passportOption.id"
              >
                {{ passportOption.id_passport }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="passenger-ticket">Ticket</label>
            <select class="form-control" id="passenger-ticket" data-cy="ticket" name="ticket" v-model="passenger.ticket">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="passenger.ticket && ticketOption.id === passenger.ticket.id ? passenger.ticket : ticketOption"
                v-for="ticketOption in tickets"
                :key="ticketOption.id"
              >
                {{ ticketOption.id_ticket }}
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
            :disabled="$v.passenger.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./passenger-update.component.ts"></script>
