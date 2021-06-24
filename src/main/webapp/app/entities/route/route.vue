<template>
  <div>
    <h2 id="page-heading" data-cy="RouteHeading">
      <span id="route-heading">Routes</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'RouteCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-route"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Route </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && routes && routes.length === 0">
      <span>No routes found</span>
    </div>
    <div class="table-responsive" v-if="routes && routes.length > 0">
      <table class="table table-striped" aria-describedby="routes">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Uuid</span></th>
            <th scope="row"><span>Planned Arrival</span></th>
            <th scope="row"><span>Planned Departure</span></th>
            <th scope="row"><span>Actual Arrival</span></th>
            <th scope="row"><span>Actual Departure</span></th>
            <th scope="row"><span>Time Registration</span></th>
            <th scope="row"><span>Platform</span></th>
            <th scope="row"><span>Rout Status</span></th>
            <th scope="row"><span>Description</span></th>
            <th scope="row"><span>Bus</span></th>
            <th scope="row"><span>Station</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="route in routes" :key="route.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RouteView', params: { routeId: route.id } }">{{ route.id }}</router-link>
            </td>
            <td>{{ route.uuid }}</td>
            <td>{{ route.plannedArrival | formatDate }}</td>
            <td>{{ route.plannedDeparture | formatDate }}</td>
            <td>{{ route.actualArrival | formatDate }}</td>
            <td>{{ route.actualDeparture | formatDate }}</td>
            <td>{{ route.timeRegistration | formatDate }}</td>
            <td>{{ route.platform }}</td>
            <td>{{ route.routStatus }}</td>
            <td>{{ route.description }}</td>
            <td>
              <div v-if="route.bus">
                <router-link :to="{ name: 'BusView', params: { busId: route.bus.id } }">{{ route.bus.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="route.station">
                <router-link :to="{ name: 'StationView', params: { stationId: route.station.id } }">{{ route.station.name }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'RouteView', params: { routeId: route.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'RouteEdit', params: { routeId: route.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(route)"
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
        ><span id="busStationArmApp.route.delete.question" data-cy="routeDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-route-heading">Are you sure you want to delete this Route?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-route"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeRoute()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./route.component.ts"></script>
