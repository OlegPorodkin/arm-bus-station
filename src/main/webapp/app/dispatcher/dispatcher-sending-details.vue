<template>
  <div class="row justify-content-center">
    <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
      <font-awesome-icon icon="arrow-left"></font-awesome-icon>
      <span> Back</span>
    </button>

    <div class="table-responsive" v-if="passengers && passengers.length > 0">
      <table class="table table-striped" aria-describedby="passengers">
        <thead>
          <tr>
            <th scope="row"><span>Статус</span></th>
            <th scope="row"><span>Имя</span></th>
            <th scope="row"><span>Фамилия</span></th>
            <th scope="row"><span>Фамииля</span></th>
            <th scope="row"><span>Phone</span></th>
            <th scope="row"><span>Ticket</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(passenger, index) in passengers" :key="passenger.id" data-cy="entityTable">
            <td>{{ passenger.boardingStatus }}</td>
            <td>{{ passenger.firstName }}</td>
            <td>{{ passenger.lastName }}</td>
            <td>{{ passenger.patronymic }}</td>
            <td>{{ passenger.phone }}</td>
            <td>
              <div v-if="passenger.ticket">
                <router-link :to="{ name: 'TicketView', params: { ticketId: passenger.ticket.id } }">{{
                  passenger.ticket.id_ticket
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <button class="btn btn-info btn-sm" data-cy="entityDetailsButton" v-on:click.prevent="appearance(index)">
                  <span class="d-none d-md-inline">Явка</span>
                </button>
                <button class="btn btn-danger btn-sm" data-cy="entityEditButton" v-on:click.prevent="notAppearance(index)">
                  <span class="d-none d-md-inline">Не явка</span>
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script lang="ts" src="./dispatcher-sending-details.component.ts"></script>

<style scoped></style>
