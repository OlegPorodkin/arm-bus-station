<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="busStationArmApp.typeObject.home.createOrEditLabel" data-cy="TypeObjectCreateUpdateHeading">Create or edit a TypeObject</h2>
        <div>
          <div class="form-group" v-if="typeObject.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="typeObject.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="type-object-uuid">Uuid</label>
            <input
              type="text"
              class="form-control"
              name="uuid"
              id="type-object-uuid"
              data-cy="uuid"
              :class="{ valid: !$v.typeObject.uuid.$invalid, invalid: $v.typeObject.uuid.$invalid }"
              v-model="$v.typeObject.uuid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="type-object-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="type-object-name"
              data-cy="name"
              :class="{ valid: !$v.typeObject.name.$invalid, invalid: $v.typeObject.name.$invalid }"
              v-model="$v.typeObject.name.$model"
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
            :disabled="$v.typeObject.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./type-object-update.component.ts"></script>
