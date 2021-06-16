<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="busStationArmApp.counterpart.home.createOrEditLabel" data-cy="CounterpartCreateUpdateHeading">
          Create or edit a Counterpart
        </h2>
        <div>
          <div class="form-group" v-if="counterpart.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="counterpart.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="counterpart-uuid">Uuid</label>
            <input
              type="text"
              class="form-control"
              name="uuid"
              id="counterpart-uuid"
              data-cy="uuid"
              :class="{ valid: !$v.counterpart.uuid.$invalid, invalid: $v.counterpart.uuid.$invalid }"
              v-model="$v.counterpart.uuid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="counterpart-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="counterpart-name"
              data-cy="name"
              :class="{ valid: !$v.counterpart.name.$invalid, invalid: $v.counterpart.name.$invalid }"
              v-model="$v.counterpart.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="counterpart-shortName">Short Name</label>
            <input
              type="text"
              class="form-control"
              name="shortName"
              id="counterpart-shortName"
              data-cy="shortName"
              :class="{ valid: !$v.counterpart.shortName.$invalid, invalid: $v.counterpart.shortName.$invalid }"
              v-model="$v.counterpart.shortName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="counterpart-tin">Tin</label>
            <input
              type="text"
              class="form-control"
              name="tin"
              id="counterpart-tin"
              data-cy="tin"
              :class="{ valid: !$v.counterpart.tin.$invalid, invalid: $v.counterpart.tin.$invalid }"
              v-model="$v.counterpart.tin.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="counterpart-ogrn">Ogrn</label>
            <input
              type="text"
              class="form-control"
              name="ogrn"
              id="counterpart-ogrn"
              data-cy="ogrn"
              :class="{ valid: !$v.counterpart.ogrn.$invalid, invalid: $v.counterpart.ogrn.$invalid }"
              v-model="$v.counterpart.ogrn.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="counterpart-egis_otb_rf">Egis Otb Rf</label>
            <input
              type="text"
              class="form-control"
              name="egis_otb_rf"
              id="counterpart-egis_otb_rf"
              data-cy="egis_otb_rf"
              :class="{ valid: !$v.counterpart.egis_otb_rf.$invalid, invalid: $v.counterpart.egis_otb_rf.$invalid }"
              v-model="$v.counterpart.egis_otb_rf.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="counterpart-taxSystem">Tax System</label>
            <input
              type="text"
              class="form-control"
              name="taxSystem"
              id="counterpart-taxSystem"
              data-cy="taxSystem"
              :class="{ valid: !$v.counterpart.taxSystem.$invalid, invalid: $v.counterpart.taxSystem.$invalid }"
              v-model="$v.counterpart.taxSystem.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="counterpart-address">Address</label>
            <input
              type="text"
              class="form-control"
              name="address"
              id="counterpart-address"
              data-cy="address"
              :class="{ valid: !$v.counterpart.address.$invalid, invalid: $v.counterpart.address.$invalid }"
              v-model="$v.counterpart.address.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="counterpart-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="counterpart-description"
              data-cy="description"
              :class="{ valid: !$v.counterpart.description.$invalid, invalid: $v.counterpart.description.$invalid }"
              v-model="$v.counterpart.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="counterpart-country">Country</label>
            <input
              type="text"
              class="form-control"
              name="country"
              id="counterpart-country"
              data-cy="country"
              :class="{ valid: !$v.counterpart.country.$invalid, invalid: $v.counterpart.country.$invalid }"
              v-model="$v.counterpart.country.$model"
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
            :disabled="$v.counterpart.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./counterpart-update.component.ts"></script>
