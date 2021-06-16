<template>
  <div>
    <h2 id="page-heading" data-cy="TicketHeading">
      <span id="ticket-heading">Tickets</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'TicketCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-ticket"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Ticket </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && tickets && tickets.length === 0">
      <span>No tickets found</span>
    </div>
    <div class="table-responsive" v-if="tickets && tickets.length > 0">
      <table class="table table-striped" aria-describedby="tickets">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Uuid</span></th>
            <th scope="row"><span>Place</span></th>
            <th scope="row"><span>Serial</span></th>
            <th scope="row"><span>Number</span></th>
            <th scope="row"><span>Type</span></th>
            <th scope="row"><span>Date Departure</span></th>
            <th scope="row"><span>Price</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="ticket in tickets" :key="ticket.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TicketView', params: { ticketId: ticket.id } }">{{ ticket.id }}</router-link>
            </td>
            <td>{{ ticket.uuid }}</td>
            <td>{{ ticket.place }}</td>
            <td>{{ ticket.serial }}</td>
            <td>{{ ticket.number }}</td>
            <td>{{ ticket.type }}</td>
            <td>{{ ticket.dateDeparture }}</td>
            <td>{{ ticket.price }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TicketView', params: { ticketId: ticket.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TicketEdit', params: { ticketId: ticket.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(ticket)"
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
        ><span id="busStationArmApp.ticket.delete.question" data-cy="ticketDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-ticket-heading">Are you sure you want to delete this Ticket?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-ticket"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeTicket()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./ticket.component.ts"></script>
