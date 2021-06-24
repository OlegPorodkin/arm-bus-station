<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="route">
        <h2 class="jh-entity-heading" data-cy="routeDetailsHeading"><span>Маршрут</span> {{ route.uuid }}</h2>

        <div>
          <b-button v-on:click="arrived()">
            <span>Отметить прибытие</span>
          </b-button>
          <b-button v-on:click="departure()">
            <span>Отправить рейс</span>
          </b-button>
          <b-button v-on:click="cancelRoute()">
            <span>Отменить рейс</span>
          </b-button>
          <router-link
            v-if="route.id"
            :to="{ name: 'DispatcherSendingDetails', params: { routeId: route.id } }"
            custom
            v-slot="{ navigate }"
          >
            <button @click="navigate" v-on:click="boarding()" class="btn btn-primary">
              <span>Начать/Закончить посатку</span>
            </button>
          </router-link>
        </div>

        <dl class="row jh-entity-details">
          <dt>
            <span>Маршрут</span>
          </dt>
          <dd>
            <span>{{ route.uuid }}</span>
          </dd>
          <dt>
            <span>Плановое прибытие</span>
          </dt>
          <dd>
            <span>{{ route.plannedArrival | formatDate }}</span>
          </dd>
          <dt>
            <span>Плановое отправление</span>
          </dt>
          <dd>
            <span>{{ route.plannedDeparture | formatDate }}</span>
          </dd>
          <dt>
            <span>Фактичиское прибытие</span>
          </dt>
          <dd>
            <span>{{ route.actualArrival | formatDate }}</span>
          </dd>
          <dt>
            <span>Фактичиское отправление</span>
          </dt>
          <dd>
            <span>{{ route.actualDeparture | formatDate }}</span>
          </dd>
          <dt>
            <span>Регистрация</span>
          </dt>
          <dd>
            <span>{{ route.timeRegistration | formatDate }}</span>
          </dd>
          <dt>
            <span>Платформа</span>
          </dt>
          <dd>
            <span>{{ route.platform }}</span>
          </dd>
          <dt>
            <span>Статус рейса</span>
          </dt>
          <dd>
            <span>{{ route.routStatus }}</span>
          </dd>
          <dt>
            <span>Описание</span>
          </dt>
          <dd>
            <span>{{ route.description }}</span>
          </dd>
          <dt>
            <span>Автобус</span>
          </dt>
          <dd>
            <span>{{ route.bus.model }}</span>
          </dd>
          <dt>
            <span>Гос. номер</span>
          </dt>
          <dd>
            <span>{{ route.bus.number }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>
          <span>Back</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./dispatcher-details.component.ts"></script>

<style scoped></style>
