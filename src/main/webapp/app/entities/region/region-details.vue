<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="region">
        <h2 class="jh-entity-heading" data-cy="regionDetailsHeading"><span>Region</span> {{ region.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Uuid</span>
          </dt>
          <dd>
            <span>{{ region.uuid }}</span>
          </dd>
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ region.name }}</span>
          </dd>
          <dt>
            <span>Description</span>
          </dt>
          <dd>
            <span>{{ region.description }}</span>
          </dd>
          <dt>
            <span>Country</span>
          </dt>
          <dd>
            <div v-if="region.country">
              <router-link :to="{ name: 'CountryView', params: { countryId: region.country.id } }">{{ region.country.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Country Of Location</span>
          </dt>
          <dd>
            <div v-if="region.countryOfLocation">
              <router-link :to="{ name: 'CountryView', params: { countryId: region.countryOfLocation.id } }">{{
                region.countryOfLocation.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="region.id" :to="{ name: 'RegionEdit', params: { regionId: region.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./region-details.component.ts"></script>
