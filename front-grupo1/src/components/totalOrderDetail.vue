<template>
    <div class="container-order-summary">
        <div class="order-title">
            <h1>Resumen orden de compra</h1>
        </div>
        <div class="order-details">
            <table>
                <thead>
                    <tr>
                        <th>Producto</th>
                        <th>Cantidad</th>
                        <th>Precio Unitario</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(item, index) in totalOrderDetail" :key="index">
                        <td>{{ item.name }}</td>
                        <td>{{ item.cantidad }}</td>
                        <td>${{ item.precio_unitario }} CLP</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="order-summary">
            <h1>Total a pagar:</h1>
            <h2>${{ this.totalPrice }} CLP</h2>
            <div class="map">
                <div>
                    <h1>Ingrese dirección:</h1>
                    <input type="text" v-model="direccion" id="direccion" @input="onInputChange"
                        placeholder="Escriba una dirección" />
                    <ul v-if="sugerencias.length">
                        <li v-for="(sugerencia, index) in sugerencias" :key="index"
                            @click="seleccionarDireccion(sugerencia)"
                            style="cursor: pointer; list-style: none; margin: 5px 0;">
                            {{ sugerencia.display_name }}
                        </li>
                    </ul>
                    <button @click="buscarCoordenadas" class="coord-button">Buscar ubicacion</button>
                </div>
                <div id="map"></div>
            </div>
            <button class="order-button" @click="submitOrder">Ordenar</button>
        </div>
    </div>
</template>

<script>
import productService from '@/services/productService';
import orderService from '@/services/orderService';
import orderDetailService from '@/services/orderDetailService';

export default {
    //Definir las propiedades del componente
    data() {
        return {
            direccion: "",
            sugerencias: [],
            coordenadas: null,
            error: null,
            token: this.$cookies.get("jwt"),
            totalOrderDetail: [],
            totalPrice: 0,
            idUser: localStorage.getItem("idUser"),
            latitud: -33.4497846,
            longitud: -70.6898598,
            coord_act: 0,
            debounceTimer: null
        };
    },
    methods: {
        async submitOrder() {
            if (this.coord_act == 0) {
                alert("Por favor ingrese una ubicacion.");
                return;
            }
            if (this.$carrito.length == 0) {
                alert("Por favor agregue productos al carrito.");
                return;
            }
            // Verificar si la dirección ingresada está dentro de la zona de cobertura
            const zona = await orderService.verificarZona(this.latitud, this.longitud, this.token);
            console.log(zona);
            if (zona.data == false) {
                alert("La dirección ingresada no está dentro de la zona de cobertura");
                return;
            }
            alert("Zona verificada con éxito, puede continuar con la orden de compra");

            //Convertir el carrito de compras a un arreglo JSON
            const carritoJson = [];
            for (let i = 0; i < this.$carrito.length; i++) {
                const productoCarrito = {
                    id_producto: parseInt(this.$carrito[i][0]),
                    id_producto: Number(this.$carrito[i][0]),
                    cantidad: this.$carrito[i][1],
                    precio_unitario: this.$carrito[i][2],

                }
                carritoJson.push(productoCarrito);
            }
            /*const ubicacion = {
                latitud: this.latitud,
                longitud: this.longitud,
            }
            const body = {
                carrito: carritoJson,
                ubicacion: ubicacion,
            }*/
            //Enviar la orden de compra al servidor
            await orderService.submitOrder(carritoJson, this.idUser, this.token, this.longitud, this.latitud);
            alert("Orden realizada con éxito");
            //Limpiar el carrito de compras
            this.$carrito.splice(0, this.$carrito.length);
            this.updateCarrito();
            this.loadCart();
            //Obtener los nombres de los productos y calcular el precio total
            await this.fetchProductNames();
            this.calculateTotalPrice();
        },
        loadCart() {
            //Obtener el carrito de compras del almacenamiento local
            const carrito = JSON.parse(localStorage.getItem("carrito")) || [];
            //Convertir el carrito de compras a un arreglo de productos
            this.totalOrderDetail = carrito.map(item => {
                return {
                    id: item[0],
                    cantidad: item[1],
                    precio_unitario: item[2],
                    name: ''
                };
            });
        },
        async fetchProductNames() {
            //Obtener los nombres de los productos en el carrito
            for (const detail of this.totalOrderDetail) {
                const productResponse = await productService.getProductById(detail.id, this.token);
                console.log(productResponse.data.nombre);
                detail.name = productResponse.data.nombre;
            }
        },
        calculateTotalPrice() {
            //Calcular el precio total de la orden de compra
            this.totalPrice = this.totalOrderDetail.reduce((total, item) => {
                return total + (item.precio_unitario * item.cantidad);
            }, 0);
        },
        loadGoogleMaps() {
            if (document.getElementById('google-maps-script')) return Promise.resolve();

            return new Promise((resolve, reject) => {
                const script = document.createElement('script');
                script.id = 'google-maps-script';
                script.src = `https://maps.googleapis.com/maps/api/js?key=AIzaSyCYAYiPijDUHxTE5FaY9rJvOpexDyccxqg`;
                script.async = true;
                script.defer = true;
                script.onload = resolve;
                script.onerror = reject;
                document.head.appendChild(script);
            });
        },
        // Funcion iniciar el mapa
        iniciarMap() {
            var coord = { lat: this.latitud, lng: this.longitud };
            var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 10,
                center: coord
            });
            var marker = new google.maps.Marker({
                position: coord,
                map: map
            });
        },
        async obtenerSugerencias() {
            if (this.direccion.length < 3) {
                this.sugerencias = [];
                return;
            }

            try {
                const encodedAddress = encodeURIComponent(this.direccion);
                const response = await fetch(
                    `https://nominatim.openstreetmap.org/search?q=${encodedAddress}&format=json&addressdetails=1&countrycodes=cl&limit=5`,
                    {
                        headers: {
                            "User-Agent": "UniversityProject/1.0 (alvaro.zamorano@usach.cl)"
                        }
                    }
                );
                const data = await response.json();
                this.sugerencias = data;
                this.error = null;
            } catch (err) {
                console.error("Error obteniendo sugerencias de direcciones:", err);
                this.error = "Error obteniendo sugerencias de direcciones.";
            }
        },

        onInputChange() {
            // Cancelamos cualquier solicitud pendiente
            clearTimeout(this.debounceTimer);

            // Establecemos un nuevo temporizador
            this.debounceTimer = setTimeout(() => {
                this.obtenerSugerencias(); // Llamamos a la API después de un retraso
            }, 1500); // 500ms de espera después de que el usuario deja de escribir
        },
        seleccionarDireccion(sugerencia) {
            this.direccion = sugerencia.display_name;
            this.coordenadas = { lat: sugerencia.lat, lon: sugerencia.lon };
            this.sugerencias = [];
            this.error = null;
        },
        async buscarCoordenadas() {
            if (!this.direccion) {
                this.error = "Debe ingresar una dirección.";
                return;
            }

            try {
                const encodedAddress = encodeURIComponent(this.direccion);
                const response = await fetch(
                    `https://nominatim.openstreetmap.org/search?q=${encodedAddress}&format=json&addressdetails=1&countrycodes=cl`,
                    {
                        headers: {
                            "User-Agent": "UniversityProject/1.0 (alvaro.zamorano@usach.cl)"
                        }
                    }
                );

                const data = await response.json();

                if (data.length === 0) {
                    this.error = "No se encontraron resultados para la dirección ingresada.";
                    this.coordenadas = null;
                } else {
                    this.coordenadas = { lat: data[0].lat, lon: data[0].lon };
                    this.error = null;
                }

                this.latitud = Number(this.coordenadas.lat);
                this.longitud = Number(this.coordenadas.lon);

                // Cargar Google Maps y configurar mapa
                await this.loadGoogleMaps();
                this.iniciarMap();

                this.coord_act = 1;
                this.direccion = "";
                console.log(this.coord_act);
            } catch (err) {
                console.error("Error buscando las coordenadas:", err);
                this.error = "Error buscando las coordenadas de la dirección.";
            }
        }

    },
    async created() {
        //Cargar el carrito de compras, obtener los nombres de los productos y calcular el precio total al cargar el componente
        this.loadCart();
        await this.fetchProductNames();
        this.calculateTotalPrice();
    },
    async mounted() {
        try {
            this.coord_act = 0;
            await this.loadGoogleMaps();
            this.iniciarMap();
        } catch (error) {
            console.error('Error al cargar Google Maps:', error);
        }
    },
};
</script>

<style>
/* Estilo del campo de entrada */
input {
    width: 100%;
    padding: 12px;
    /* Aumentado para mayor consistencia */
    font-size: 16px;
    margin-bottom: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* Contenedor de la lista de sugerencias */
ul {
    background-color: #fff;
    border: 1px solid #ddd;
    border-radius: 5px;
    margin-top: 0;
    padding: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    font-size: 15px;
    /* Más consistente con otros textos */
    max-height: 200px;
    /* Limitar la altura para que no ocupe demasiado espacio */
    overflow-y: auto;
    /* Scroll si hay muchas sugerencias */
    color: #333;
    /* Texto más oscuro para mayor legibilidad */
}

/* Estilo de los elementos de la lista */
li {
    list-style: none;
    padding: 8px 12px;
    border-radius: 3px;
    transition: background-color 0.2s;
    cursor: pointer;
}

/* Hover de los elementos de la lista */
li:hover {
    background-color: #f9f9f9;
    /* Color más claro */
    color: #000;
    /* Aumentar contraste */
}

.container-order-summary {
    display: grid;
    grid-template-columns: 2fr 1fr;
    grid-template-rows: auto 1fr;
    gap: 20px;
    padding: 20px;
    height: 100vh;
    width: 100%;
    background-color: #f0f0f0;
    overflow: hidden;
}

.order-title {
    grid-column: span 2;
    text-align: center;
    background-color: #fff;
    padding: 15px;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    font-size: 15px;
}

.order-details {
    background-color: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    color: #000000;
}

.map #map {
    height: 500px;
    width: 100%;
    margin-bottom: 10px;
}

.input-map {
    width: 100%;
    padding: 12px;
    margin-top: 10px;
    margin-bottom: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
}

.coord-button {
    margin-bottom: 10px;
}

.order-summary {
    background-color: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    grid-row: span 2;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px;
}

thead {
    background-color: #3b82f6;
    color: white;
}

th,
td {
    text-align: center;
    padding: 10px;
    border-bottom: 1px solid #ddd;
}

tbody tr:hover {
    background-color: #f9f9f9;
}

h1,
h2 {
    text-align: center;
    color: #333;
}

h1 {
    font-size: 25px;
}

button {
    width: 100%;
    padding: 12px;
    background-color: #3b82f6;
    color: white;
    border: none;
    border-radius: 5px;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s;
}

button:hover {
    background-color: #2563eb;
}
</style>
