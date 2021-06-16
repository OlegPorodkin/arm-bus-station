<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="counterpart">
        <h2 class="jh-entity-heading" data-cy="counterpartDetailsHeading"><span>Counterpart</span> {{ counterpart.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Uuid</span>
          </dt>
          <dd>
            <span>{{ counterpart.uuid }}</span>
          </dd>
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ counterpart.name }}</span>
          </dd>
          <dt>
            <span>Short Name</span>
          </dt>
          <dd>
            <span>{{ counterpart.shortName }}</span>
          </dd>
          <dt>
            <span>Tin</span>
          </dt>
          <dd>
            <span>{{ counterpart.tin }}</span>
          </dd>
          <dt>
            <span>Ogrn</span>
          </dt>
          <dd>
            <span>{{ counterpart.ogrn }}</span>
          </dd>
          <dt>
            <span>Egis Otb Rf</span>
          </dt>
          <dd>
            <span>{{ counterpart.egis_otb_rf }}</span>
          </dd>
          <dt>
            <span>Tax System</span>
          </dt>
          <dd>
            <span>{{ counterpart.taxSystem }}</span>
          </dd>
          <dt>
            <span>Address</span>
          </dt>
          <dd>
            <span>{{ counterpart.address }}</span>
          </dd>
          <dt>
            <span>Description</span>
          </dt>
          <dd>
            <span>{{ counterpart.description }}</span>
          </dd>
          <dt>
            <span>Country</span>
          </dt>
          <dd>
            <span>{{ counterpart.country }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="counterpart.id"
          :to="{ name: 'CounterpartEdit', params: { counterpartId: counterpart.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./counterpart-details.component.ts"></script>
