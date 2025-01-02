import './assets/main.css'
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import VueCookies from 'vue-cookies';

const app = createApp(App);

//Cargar el carrito desde localStorage si existe, sino crear un carrito vacío
const storedCarrito = localStorage.getItem('carrito');
app.config.globalProperties.$carrito = storedCarrito ? JSON.parse(storedCarrito) : [];

//Guardar el carrito en localStorage cada vez que se modifique
app.config.globalProperties.updateCarrito = function() {
    localStorage.setItem('carrito', JSON.stringify(this.$carrito));
};

app.use(router);
app.use(VueCookies);
app.mount('#app');