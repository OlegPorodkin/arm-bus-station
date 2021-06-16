<template>
  <div>
    <h2 id="page-heading" data-cy="PassportHeading">
      <span id="passport-heading">Passports</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'PassportCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-passport"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Passport </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && passports && passports.length === 0">
      <span>No passports found</span>
    </div>
    <div class="table-responsive" v-if="passports && passports.length > 0">
      <table class="table table-striped" aria-describedby="passports">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Uuid</span></th>
            <th scope="row"><span>Serial</span></th>
            <th scope="row"><span>Number</span></th>
            <th scope="row"><span>Date Of Issue</span></th>
            <th scope="row"><span>Who Issued</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="passport in passports" :key="passport.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PassportView', params: { passportId: passport.id } }">{{ passport.id }}</router-link>
            </td>
            <td>{{ passport.uuid }}</td>
            <td>{{ passport.serial }}</td>
            <td>{{ passport.number }}</td>
            <td>{{ passport.dateOfIssue }}</td>
            <td>{{ passport.whoIssued }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PassportView', params: { passportId: passport.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PassportEdit', params: { passportId: passport.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(passport)"
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
        ><span id="busStationArmApp.passport.delete.question" data-cy="passportDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-passport-heading">Are you sure you want to delete this Passport?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-passport"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removePassport()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./passport.component.ts"></script>
