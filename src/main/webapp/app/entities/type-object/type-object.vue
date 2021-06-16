<template>
  <div>
    <h2 id="page-heading" data-cy="TypeObjectHeading">
      <span id="type-object-heading">Type Objects</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'TypeObjectCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-type-object"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Type Object </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && typeObjects && typeObjects.length === 0">
      <span>No typeObjects found</span>
    </div>
    <div class="table-responsive" v-if="typeObjects && typeObjects.length > 0">
      <table class="table table-striped" aria-describedby="typeObjects">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Uuid</span></th>
            <th scope="row"><span>Name</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="typeObject in typeObjects" :key="typeObject.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TypeObjectView', params: { typeObjectId: typeObject.id } }">{{ typeObject.id }}</router-link>
            </td>
            <td>{{ typeObject.uuid }}</td>
            <td>{{ typeObject.name }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TypeObjectView', params: { typeObjectId: typeObject.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TypeObjectEdit', params: { typeObjectId: typeObject.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(typeObject)"
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
        ><span id="busStationArmApp.typeObject.delete.question" data-cy="typeObjectDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-typeObject-heading">Are you sure you want to delete this Type Object?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-typeObject"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeTypeObject()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./type-object.component.ts"></script>
