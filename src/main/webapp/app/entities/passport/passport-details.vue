<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="passport">
        <h2 class="jh-entity-heading" data-cy="passportDetailsHeading"><span>Passport</span> {{ passport.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Uuid</span>
          </dt>
          <dd>
            <span>{{ passport.uuid }}</span>
          </dd>
          <dt>
            <span>Serial</span>
          </dt>
          <dd>
            <span>{{ passport.serial }}</span>
          </dd>
          <dt>
            <span>Number</span>
          </dt>
          <dd>
            <span>{{ passport.number }}</span>
          </dd>
          <dt>
            <span>Date Of Issue</span>
          </dt>
          <dd>
            <span>{{ passport.dateOfIssue }}</span>
          </dd>
          <dt>
            <span>Who Issued</span>
          </dt>
          <dd>
            <span>{{ passport.whoIssued }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="passport.id" :to="{ name: 'PassportEdit', params: { passportId: passport.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./passport-details.component.ts"></script>
