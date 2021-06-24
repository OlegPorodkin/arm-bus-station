<template>
  <b-jumbotron v-if="hasAnyAuthority('ROLE_DISPATCHER') && authenticated">
    <h1>Управление рейсами</h1>

    <div class="alert alert-warning" v-if="!isFetching && routes && routes.length === 0">
      <span>No routes found</span>
    </div>

    <div class="table-responsive" v-if="routes && routes.length > 0">
      <table class="table table-striped" aria-describedby="routes">
        <thead>
          <tr>
            <!--          <th scope="row"><span>ID</span></th>-->
            <th scope="row"><span>Рейс</span></th>
            <th scope="row"><span>Плановое прибытие</span></th>
            <th scope="row"><span>Плановое отправление</span></th>
            <th scope="row"><span>Фактичиское прибытие</span></th>
            <th scope="row"><span>Фактичиское отправление</span></th>
            <th scope="row"><span>Регистрация</span></th>
            <th scope="row"><span>Платформа</span></th>
            <th scope="row"><span>Статус рейса</span></th>
            <th scope="row"><span>Описание</span></th>
            <th scope="row"><span>Bus</span></th>
            <th scope="row"><span>Station</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="route in routes" :key="route.id" data-cy="entityTable">
            <!--          <td>-->
            <!--            <router-link :to="{ name: 'RouteView', params: { routeId: route.id } }">{{ route.id }}</router-link>-->
            <!--          </td>-->
            <td>{{ route.uuid }}</td>
            <td>{{ route.plannedArrival | formatDate }}</td>
            <td>{{ route.plannedDeparture | formatDate }}</td>
            <td>{{ route.actualArrival | formatDate }}</td>
            <td>{{ route.actualDeparture | formatDate }}</td>
            <td>{{ route.timeRegistration | formatDate }}</td>
            <td>{{ route.platform }}</td>
            <td>{{ route.routStatus }}</td>
            <td>{{ route.description }}</td>
            <td>
              <div v-if="route.bus">
                <router-link :to="{ name: 'BusView', params: { busId: route.bus.id } }">{{ route.bus.model }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="route.station">
                <router-link :to="{ name: 'StationView', params: { stationId: route.station.id } }">{{ route.station.name }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DispatcherDetails', params: { routeId: route.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <!--              <router-link :to="{ name: 'RouteEdit', params: { routeId: route.id } }" custom v-slot="{ navigate }">-->
                <!--                <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">-->
                <!--                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>-->
                <!--                  <span class="d-none d-md-inline">Edit</span>-->
                <!--                </button>-->
                <!--              </router-link>-->
                <!--              <b-button-->
                <!--                v-on:click="prepareRemove(route)"-->
                <!--                variant="danger"-->
                <!--                class="btn btn-sm"-->
                <!--                data-cy="entityDeleteButton"-->
                <!--                v-b-modal.removeEntity-->
                <!--              >-->
                <!--                <font-awesome-icon icon="times"></font-awesome-icon>-->
                <!--                <span class="d-none d-md-inline">Delete</span>-->
                <!--              </b-button>-->
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </b-jumbotron>

  <b-jumbotron v-else-if="!hasAnyAuthority('ROLE_DISPATCHER')">
    <p>Для работы под модулем ДИСПЕТЧЕР необходимо авторизоваться с правами диспетчера</p>
    <b-button variant="primary" href="#">More Info</b-button>
  </b-jumbotron>
</template>

<script lang="ts" src="./dispatcher.component.ts"></script>
