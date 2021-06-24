import Vue from 'vue';
import Component from 'vue-class-component';
import Router from 'vue-router';
import account from '@/router/account';
import admin from '@/router/admin';
import entities from '@/router/entities';
import pages from '@/router/pages';
import { Authority } from '@/shared/security/authority';

Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate', // for vue-router 2.2+
]);

const Home = () => import('@/core/home/home.vue');
const Dispatcher = () => import('@/dispatcher/dispatcher.vue');
const Administrator = () => import('@/administrator/administrator.vue');
const Error = () => import('@/core/error/error.vue');

Vue.use(Router);

// prettier-ignore
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/dispatcher',
      name: '',
      component: Dispatcher,
      meta: {authorities: [Authority.DISPATCHER]},
    },
    {
      path: '/administrator',
      name: '',
      component: Administrator,
      meta: {authorities: [Authority.ADMINISTRATOR]},
    },
    {
      path: '/forbidden',
      name: 'Forbidden',
      component: Error,
      meta: {error403: true}
    },
    {
      path: '/not-found',
      name: 'NotFound',
      component: Error,
      meta: {error404: true}
    },
    ...account,
    ...admin,
    ...entities,
    ...pages
  ]
});
