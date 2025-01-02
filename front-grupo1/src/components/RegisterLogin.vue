<script>
import clienteService from "../services/clientServices.js"; 
import { jwtDecode } from "jwt-decode";

export default {
    //Definir las propiedades del componente
    data() {
        return {
            register: false, 
            nombre: '',
            email: '',
            direccion: '',
            telefono: '',
            contrasena: ''
        };
    },
    methods: {
        async submitForm() {
            if (this.register) {
                //Si el usuario desea registrarse se validan los campos
                if (!this.nombre || !this.email || !this.direccion || !this.telefono || !this.contrasena) {
                    alert("Por favor, llene todos los campos.");
                    return;
                }
                //Crear un objeto con los datos del cliente
                const cliente = {
                    nombre: this.nombre, 
                    email: this.email,
                    direccion: this.direccion,  
                    telefono: this.telefono,  
                    contrasena: this.contrasena,  
                    rol: "cliente"
                };

                try {
                    //Registrar el cliente en la base de datos
                    const response = await clienteService.createCliente(cliente);
                    console.log("Registro exitoso:", response.data);
                    alert("Usuario registrado correctamente");
                    //Limpiar los campos del formulario y cambiar a la vista de inicio de sesión
                    this.resetForm();
                    this.register = false;
                } catch (error) {
                    //Mostrar un mensaje de error si no se puede registrar el usuario
                    console.error("Error al registrar el usuario:", error.response?.data || error.message);
                    alert("Hubo un error al registrar el usuario.");
                }
            } else {
                //Si el usuario desea iniciar sesión se validan los campos
                if (!this.email || !this.contrasena) {
                    alert("Por favor, llene todos los campos.");
                    return;
                }
                try {
                    //Iniciar sesión con el correo y la contraseña proporcionados
                    const response = await clienteService.loginCliente(this.email, this.contrasena); 
                    const token = response.data.token;
                    //Guardar el token en las cookies y el id del usuario en el local storage
                    this.$cookies.set("jwt", token, "1d");
                    const decoded = jwtDecode(token);
                    const id = decoded.id;
                    //Obtener el rol del usuario y guardarlo en el local storage
                    const responseRole = await clienteService.getRole(id, token);
                    const role = responseRole.data;
                    localStorage.setItem("idUser", id);
                    localStorage.setItem("userRole", role);
                    console.log("Inicio de sesión exitoso:", response.data);
                    alert("Inicio de sesión exitoso");
                    this.$router.push("/products");
                } catch (error) {
                    //Mostrar un mensaje de error si no se puede iniciar sesión
                    console.error("Error al iniciar sesión:", error.response?.data || error.message);
                    alert("Correo o contraseña incorrectos.");
                }
            }
        },
        resetForm() {
            //Limpiar los campos del formulario
            this.nombre = '';  
            this.email = '';
            this.direccion = '';  
            this.telefono = '';  
            this.contrasena = '';  
        }
    }
};
</script>

<template>
    <div class="container-register-login">
        <div class="content">
            <h1>Bienvenido al sistema de gestión</h1>
            <div class="form-container">
                <form v-if="!register">
                    <h2>Inicio de sesión</h2>
                    <div class="form-group">
                        <label class="form-group-label">Correo</label>
                        <input
                            class="form-group-elem"
                            type="email"
                            v-model="email"
                            placeholder="Ingrese su correo"
                            required
                        />
                    </div>
                    <div class="form-group">
                        <label class="form-group-label">Contraseña</label>
                        <input
                            class="form-group-elem"
                            type="password"
                            v-model="contrasena" 
                            placeholder="Ingrese su contraseña"
                            required
                        />
                    </div>
                    <button @click.prevent="submitForm" class="form-group-elem btn">Iniciar sesión</button>
                    <a @click="register = true" class="link">¿No tienes cuenta? Regístrate</a>
                </form>

                <form v-else>
                    <h2>Registro</h2>
                    <div class="form-group">
                        <label class="form-group-label">Nombre</label>
                        <input
                            class="form-group-elem"
                            type="text"
                            v-model="nombre"  
                            placeholder="Ingrese su nombre"
                            required
                        />
                    </div>
                    <div class="form-group">
                        <label class="form-group-label">Correo</label>
                        <input
                            class="form-group-elem"
                            type="email"
                            v-model="email"
                            placeholder="Ingrese su correo"
                            required
                        />
                    </div>
                    <div class="form-group">
                        <label class="form-group-label">Dirección</label>
                        <input
                            class="form-group-elem"
                            type="text"
                            v-model="direccion"  
                            placeholder="Ingrese su dirección"
                            required
                        />
                    </div>
                    <div class="form-group">
                        <label class="form-group-label">Teléfono</label>
                        <input
                            class="form-group-elem"
                            type="tel"
                            v-model="telefono"  
                            placeholder="Ingrese su teléfono"
                            required
                        />
                    </div>
                    <div class="form-group">
                        <label class="form-group-label">Contraseña</label>
                        <input
                            class="form-group-elem"
                            type="password"
                            v-model="contrasena"  
                            placeholder="Ingrese su contraseña"
                            required
                        />
                    </div>
                    <button @click.prevent="submitForm" class="form-group-elem btn">Registrar</button>
                    <a @click="register = false" class="link">¿Ya tienes cuenta? Inicia sesión</a>
                </form>
            </div>
        </div>
    </div>
</template>

<style>
/* El estilo sigue igual, sin cambios */
.container-register-login {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f0f0f0; 
    width: 100%;
    overflow: hidden;
}

.content {
    width: 100%;
    max-width: 400px;
    padding: 20px;
    background-color: #FFFFFF; 
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); 
}

h1 {
    text-align: center;
    color: #333;
}

h2 {
    color: #333;
    text-align: center;
}

.form-container {
    width: 100%;
    max-width: 400px;
    padding: 20px;
    background-color: #fff;
}

.form-group {
    margin-bottom: 15px;
}

.form-group-label {
    display: block;
    color: #333;
    font-weight: bold;
    margin-bottom: 5px;
    font-size: 14px;
}

.form-group-elem {
    width: 100%;
    padding: 12px;
    border: 1px solid #ddd;
    border-radius: 5px;
    background-color: #f9f9f9;
    color: #333;
    font-size: 14px;
    transition: border-color 0.3s;
}

.form-group-elem:focus {
    border-color: #3b82f6; 
}

button {
    width: 100%;
    padding: 12px;
    background-color: #3b82f6;
    color: #ffffff;
    border: none;
    border-radius: 5px;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s;
}

button:hover {
    background-color: #2563eb; 
}

a {
    text-align: center;
    display: block;
    margin-top: 15px;
    color: #3b82f6;
    text-decoration: none;
}

a:hover {
    text-decoration: underline;
}
</style>
