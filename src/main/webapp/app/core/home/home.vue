<template>
  <div class="center">
    <div>
      <h1 class="display-4 main-button-menu">Добро пожаловать в АРМ Автовокзала</h1>

      <div>
        <div class="alert alert-warning" v-if="!authenticated">
          <span>You don't have an account yet?</span>&nbsp;
          <router-link class="alert-link" to="/register">Register a new account</router-link>
        </div>

        <div class="main-button-menu">
          <b-row>
            <b-col lg="4" class="pb-2"><b-button to="/dispatcher" size="lg">Диспетчер</b-button></b-col>
            <b-col lg="4" class="pb-2"><b-button to="/administrator" size="lg">Администратор</b-button></b-col>
          </b-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts"></script>
