<template>
  <div>
    <h2 id="page-heading" data-cy="CounterpartHeading">
      <span id="counterpart-heading">Counterparts</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'CounterpartCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-counterpart"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Counterpart </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && counterparts && counterparts.length === 0">
      <span>No counterparts found</span>
    </div>
    <div class="table-responsive" v-if="counterparts && counterparts.length > 0">
      <table class="table table-striped" aria-describedby="counterparts">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Uuid</span></th>
            <th scope="row"><span>Name</span></th>
            <th scope="row"><span>Short Name</span></th>
            <th scope="row"><span>Tin</span></th>
            <th scope="row"><span>Ogrn</span></th>
            <th scope="row"><span>Egis Otb Rf</span></th>
            <th scope="row"><span>Tax System</span></th>
            <th scope="row"><span>Address</span></th>
            <th scope="row"><span>Description</span></th>
            <th scope="row"><span>Country</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="counterpart in counterparts" :key="counterpart.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CounterpartView', params: { counterpartId: counterpart.id } }">{{ counterpart.id }}</router-link>
            </td>
            <td>{{ counterpart.uuid }}</td>
            <td>{{ counterpart.name }}</td>
            <td>{{ counterpart.shortName }}</td>
            <td>{{ counterpart.tin }}</td>
            <td>{{ counterpart.ogrn }}</td>
            <td>{{ counterpart.egis_otb_rf }}</td>
            <td>{{ counterpart.taxSystem }}</td>
            <td>{{ counterpart.address }}</td>
            <td>{{ counterpart.description }}</td>
            <td>{{ counterpart.country }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CounterpartView', params: { counterpartId: counterpart.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CounterpartEdit', params: { counterpartId: counterpart.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(counterpart)"
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
        ><span id="busStationArmApp.counterpart.delete.question" data-cy="counterpartDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-counterpart-heading">Are you sure you want to delete this Counterpart?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-counterpart"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeCounterpart()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./counterpart.component.ts"></script>
