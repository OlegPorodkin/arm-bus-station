<template>
  <div>
    <h2 id="page-heading" data-cy="PassengerHeading">
      <span id="passenger-heading">Passengers</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'PassengerCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-passenger"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Passenger </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && passengers && passengers.length === 0">
      <span>No passengers found</span>
    </div>
    <div class="table-responsive" v-if="passengers && passengers.length > 0">
      <table class="table table-striped" aria-describedby="passengers">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Uuid</span></th>
            <th scope="row"><span>First Name</span></th>
            <th scope="row"><span>Last Name</span></th>
            <th scope="row"><span>Patronymic</span></th>
            <th scope="row"><span>Birthday</span></th>
            <th scope="row"><span>Boarding Status</span></th>
            <th scope="row"><span>Sex</span></th>
            <th scope="row"><span>Phone</span></th>
            <th scope="row"><span>Citizenship</span></th>
            <th scope="row"><span>Route</span></th>
            <th scope="row"><span>Passport</span></th>
            <th scope="row"><span>Ticket</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="passenger in passengers" :key="passenger.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PassengerView', params: { passengerId: passenger.id } }">{{ passenger.id }}</router-link>
            </td>
            <td>{{ passenger.uuid }}</td>
            <td>{{ passenger.firstName }}</td>
            <td>{{ passenger.lastName }}</td>
            <td>{{ passenger.patronymic }}</td>
            <td>{{ passenger.birthday }}</td>
            <td>{{ passenger.boardingStatus }}</td>
            <td>{{ passenger.sex }}</td>
            <td>{{ passenger.phone }}</td>
            <td>{{ passenger.citizenship }}</td>
            <td>
              <div v-if="passenger.route">
                <router-link :to="{ name: 'RouteView', params: { routeId: passenger.route.id } }">{{ passenger.route.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="passenger.passport">
                <router-link :to="{ name: 'PassportView', params: { passportId: passenger.passport.id } }">{{
                  passenger.passport.id_passport
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="passenger.ticket">
                <router-link :to="{ name: 'TicketView', params: { ticketId: passenger.ticket.id } }">{{
                  passenger.ticket.id_ticket
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PassengerView', params: { passengerId: passenger.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PassengerEdit', params: { passengerId: passenger.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(passenger)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="busStationArmApp.passenger.delete.question" data-cy="passengerDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-passenger-heading">Are you sure you want to delete this Passenger?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-passenger"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removePassenger()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./passenger.component.ts"></script>
