<template>
  <div>
    <h2 id="page-heading" data-cy="StationHeading">
      <span id="station-heading">Stations</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'StationCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-station"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Station </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && stations && stations.length === 0">
      <span>No stations found</span>
    </div>
    <div class="table-responsive" v-if="stations && stations.length > 0">
      <table class="table table-striped" aria-describedby="stations">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Uuid</span></th>
            <th scope="row"><span>Name</span></th>
            <th scope="row"><span>Description</span></th>
            <th scope="row"><span>Okato</span></th>
            <th scope="row"><span>Street</span></th>
            <th scope="row"><span>Longitude</span></th>
            <th scope="row"><span>Latitude</span></th>
            <th scope="row"><span>Distance</span></th>
            <th scope="row"><span>Next Station</span></th>
            <th scope="row"><span>Type Object</span></th>
            <th scope="row"><span>Region</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="station in stations" :key="station.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'StationView', params: { stationId: station.id } }">{{ station.id }}</router-link>
            </td>
            <td>{{ station.uuid }}</td>
            <td>{{ station.name }}</td>
            <td>{{ station.description }}</td>
            <td>{{ station.okato }}</td>
            <td>{{ station.street }}</td>
            <td>{{ station.longitude }}</td>
            <td>{{ station.latitude }}</td>
            <td>{{ station.distance }}</td>
            <td>
              <div v-if="station.nextStation">
                <router-link :to="{ name: 'StationView', params: { stationId: station.nextStation.id } }">{{
                  station.nextStation.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="station.typeObject">
                <router-link :to="{ name: 'TypeObjectView', params: { typeObjectId: station.typeObject.id } }">{{
                  station.typeObject.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="station.region">
                <router-link :to="{ name: 'RegionView', params: { regionId: station.region.id } }">{{ station.region.name }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'StationView', params: { stationId: station.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'StationEdit', params: { stationId: station.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(station)"
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
        ><span id="busStationArmApp.station.delete.question" data-cy="stationDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-station-heading">Are you sure you want to delete this Station?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-station"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeStation()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./station.component.ts"></script>
