<template>
  <div>
    <h2 id="page-heading" data-cy="BusHeading">
      <span id="bus-heading">Buses</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'BusCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-bus">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Bus </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && buses && buses.length === 0">
      <span>No buses found</span>
    </div>
    <div class="table-responsive" v-if="buses && buses.length > 0">
      <table class="table table-striped" aria-describedby="buses">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Model</span></th>
            <th scope="row"><span>Number</span></th>
            <th scope="row"><span>Description</span></th>
            <th scope="row"><span>Passenger Places</span></th>
            <th scope="row"><span>Driver</span></th>
            <th scope="row"><span>Counterpart</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="bus in buses" :key="bus.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'BusView', params: { busId: bus.id } }">{{ bus.id }}</router-link>
            </td>
            <td>{{ bus.model }}</td>
            <td>{{ bus.number }}</td>
            <td>{{ bus.description }}</td>
            <td>{{ bus.passengerPlaces }}</td>
            <td>
              <div v-if="bus.driver">
                <router-link :to="{ name: 'DriverView', params: { driverId: bus.driver.id } }">{{ bus.driver.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="bus.counterpart">
                <router-link :to="{ name: 'CounterpartView', params: { counterpartId: bus.counterpart.id } }">{{
                  bus.counterpart.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'BusView', params: { busId: bus.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'BusEdit', params: { busId: bus.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(bus)"
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
        ><span id="busStationArmApp.bus.delete.question" data-cy="busDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-bus-heading">Are you sure you want to delete this Bus?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-bus"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeBus()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./bus.component.ts"></script>
