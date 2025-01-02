<template>
  <nav v-if="!isRegisterLogin" class="navbar">
    <div class="navbar-container">
      <div class="navbar-links">
        <router-link to="/" class="navbar-link">Salir</router-link>
        <router-link to="/products" class="navbar-link">Productos</router-link>
        <router-link to="/myOrders" class="navbar-link">Mis Ordenes</router-link>
        <router-link to="/order" class="navbar-link">Carrito</router-link>
        <router-link v-if="isAdmin" to="/procedures" class="navbar-link">Procedimientos</router-link>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  computed: {
    isRegisterLogin() {
      //Redirigir a la página de inicio si el usuario no está registrado
      return this.$route.path === '/';
    },
    isAdmin() {
      //Verificar si el usuario es administrador
      const userRole = localStorage.getItem("userRole");
      return userRole === 'admin';
    }
  }
};
</script>
  
  
<style scoped>
.navbar {
  background-color: #ffffff;
  color: #333;
  display: flex;
  justify-content: center;
  height: 40px;
}
  
.navbar-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}
  
.navbar-links {
  display: flex;
  gap: 20px;
}
  
.navbar-link {
  color: #333;
  text-decoration: none;
  font-size: 1rem;
}
</style>
  