<template>
    <div class="full-container">
        <div class="procedure-container">
            <h1>
                {{ isDiscountView
                    ? 'Aplicar Descuento'
                    : isTopClientsView
                    ? 'Obtener usuarios con más querys':
                    isTopSpendersView
                    ? 'Top 5 Usuarios que más gastaron en Tecnología'
                    : isDeliveryZonesView
                    ? 'Listar Repartidores por Zona'
                    : isAreaCoverageView
                    ? 'Calcular Área Total Cubierta'
                    : '' }}
            </h1>
            <div class="switch-container">
                <button @click="toggleView('discount')" :class="{ active: isDiscountView }">Aplicar Descuento</button>
                <button @click="toggleView('topClients')" :class="{ active: isTopClientsView }">Obtener usuarios con más querys</button>
                <button @click="toggleView('topSpenders')" :class="{ active: isTopSpendersView }">Top 5 Usuarios en Tecnología</button>
                <button @click="toggleView('deliveryZones')" :class="{ active: isDeliveryZonesView }">Listar Repartidores por Zona</button>
                <button @click="toggleView('areaCoverage')" :class="{ active: isAreaCoverageView }">Calcular Área Cubierta</button>
            </div>
            <div class="action-container">
                <div v-if="isDiscountView" class="discount-container">
                    <form @submit.prevent="applyDiscount">
                        <select v-model="selectedCategory">
                            <option v-for="category in categories" :key="category.id" :value="category">
                                {{ category.nombre }}
                            </option>
                        </select>
                        <input type="number" step="0.001" min="0" max="1" v-model="discount" placeholder="Ingrese el descuento" class="styled-input" required>
                        <button type="submit">Aplicar Descuento</button>
                    </form>
                </div>
                <div v-else-if="isTopClientsView" class="top-clients-container">
                    <button @click="fetchTopClients">Obtener usuarios con más querys</button>
                </div>
                <div v-else-if="isTopSpendersView" class="top-spenders-container">
                    <button @click="fetchTopSpenders">Top 5 Usuarios en Tecnología</button>
                </div>
                <div v-else-if="isDeliveryZonesView" class="delivery-zones-container">
                    <form @submit.prevent="fetchRepartidores">
                        <select v-model="selectedZone">
                            <option v-for="zone in zones" :key="zone.id" :value="zone">
                                {{ zone.nombre }}
                            </option>
                        </select>
                        <button type="submit">Obtener Repartidores</button>
                    </form>
                    <div v-if="results && results.length > 0" class="results-list-container">
                        <h2>Repartidores en la zona seleccionada:</h2>
                        <ul class="results-list">
                            <li v-for="(repartidor, index) in results" :key="index">
                                <strong>Nombre:</strong> {{ repartidor.nombre }} <br>
                                <strong>Teléfono:</strong> {{ repartidor.telefono }}
                            </li>
                        </ul>
                    </div>
                    <p v-else-if="results && results.length === 0">No se encontraron repartidores en esta zona.</p>
                </div>
                <div v-else class="area-coverage-container">
                    <select v-model="selectedEmpresa">
                        <option v-for="empresa in empresas" :key="empresa.id" :value="empresa">
                            {{ empresa.nombre }}
                        </option>
                    </select>
                    <button @click="fetchAreaCoverage">Calcular Área Total Cubierta</button>
                </div>
            </div>
        </div>
        <div v-if="results && !isDeliveryZonesView" class="results-container">
            <h2>Resultados</h2>
            <pre class="results-text">{{ results }}</pre>
        </div>
    </div>
</template>


<script>
import categoryService from '../services/categoryService';
import clienteService from "../services/clientServices.js"; 
import productService from "../services/productService.js";
import repartidorService from "../services/repartidorService.js";
import companyService from "../services/companyService.js";
import zonaService from "../services/zonaService";
export default {
    //Definir las propiedades del componente
    data() {
        return {
            isDiscountView: true,
            isTopClientsView: false,
            isTopSpendersView: false,
            isAreaCoverageView: false,
            isDeliveryZonesView: false,
            selectedCategory: null,
            selectedZone: "",
            selectedEmpresa: null,
            selectedZone: null,
            categories: [],
            empresas: [],
            results: '',
            zones: [],
            discount: 0
        };
    },
    methods: {
        toggleView(view) {
            //Cambiar la vista del procedimiento almacenado
            this.isDiscountView = view === 'discount';
            this.isTopClientsView = view === 'topClients';
            this.isTopSpendersView = view === 'topSpenders';
            this.isDeliveryZonesView = view === 'deliveryZones';
            this.isAreaCoverageView = view === 'areaCoverage';
            //Limpiar los resultados al cambiar de vista
            this.results = '';
        },
        async applyDiscount() {
            //Aplicar un descuento a una categoría de productos
            await productService.applyDiscount(this.$cookies.get("jwt"), this.selectedCategory.id_categoria, this.discount);
            alert("Descuento aplicado correctamente");
        },
        async fetchTopClients() {
            //Obtener los usuarios con más querys
            const response = await clienteService.getTopUsers(this.$cookies.get("jwt"));
            //Asignar el string directamente
            this.results = response.data; 
        },
        async fetchTopSpenders() {
            //Obtener los usuarios que más gastaron en tecnología
            const response = await clienteService.getTopSpenders(this.$cookies.get("jwt"));
            //Formatear los resultados
            this.results = response.data.map((user, index) => `${index + 1}. ${user.cliente}, total gastado:${user.totalgastado}`).join('\n');
        },
        async loadCategories() {
            //Obtener las categorías de productos y asignarlas a la variable
            const response = await categoryService.getAll(this.$cookies.get("jwt"));
            this.categories = response.data;
        },
        async fetchAreaCoverage() {
            console.log(this.selectedEmpresa);
            //obtener el área total cubierta por una empresa
            const response = await companyService.AreaCompany(this.selectedEmpresa.id_empresa, this.$cookies.get("jwt"));
            this.results = (response.data / 1000000).toFixed(4) + " km²";
        },
        async fetchEmpresas() {
            // Obtener todas las empresas para listar
            const response = await companyService.getAll(this.$cookies.get("jwt"));
            this.empresas = response.data;
        },  
        async fetchZonas() {
            // Obtener todas las zonas para listar
            const response = await zonaService.getZonas("reparto",this.$cookies.get("jwt"));
            this.zones= response.data;
        }, 
        async fetchRepartidores() {
            // Obtener los repartidores de la zona selecionada
            const response = await repartidorService.getRepartidorByZone(this.selectedZone.id_zona, this.$cookies.get("jwt"));
            this.results = [...new Set(response.data.map(item => ({nombre: item.nombre, telefono: item.telefono})))];
            console.log("Repartidores obtenidos:", this.results);
        }

    },
    mounted() {
        //Cargar las categorías al cargar el componente
        this.fetchZonas();
        this.fetchEmpresas();
        this.loadCategories();
    }
}
</script>

<style scoped>
.full-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 100vh;
    width: 100%;
    gap: 20px;
    margin-top: 20px;
    background-color: #f0f0f0;
}

.results-list-container {
    margin-top: 20px;
    background-color: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.results-list {
    list-style-type: none;
    padding: 0;
    margin: 0;
}

.procedure-container {
    display: flex;
    flex-direction: column;
    gap: 20px;
    padding: 20px;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
    width: 800px;
}

.switch-container {
    display: flex;
    justify-content: space-between;
}

.switch-container button {
    flex: 1;
    padding: 10px;
    margin: 0 5px;
    background-color: #3b82f6;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.switch-container button.active {
    background-color: #2563eb;
}

.action-container {
    display: flex;
    flex-direction: column;
}

.discount-container,
.top-clients-container,
.top-spenders-container {
    flex: 1;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
    padding: 20px;
    background-color: #fff;
    border-radius: 8px;
}

.results-container {
    width: 80%;
    background-color: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    max-height: 300px;
    overflow-y: auto;
    margin-top: 20px;
}


.results-text {
    color: #000;
    font-size: 16px; 
    line-height: 1.5; 
    white-space: pre-wrap; 
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
    margin-top: 10px;
}

button:hover {
    background-color: #2563eb;
}

select {
    width: 100%;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #d1d5db;
    margin-bottom: 10px;
}

.styled-input {
    width: 100%;
    padding: 12px;
    margin-bottom: 10px;
    border: 1px solid #d1d5db;
    border-radius: 5px;
    font-size: 16px;
    transition: border-color 0.3s, box-shadow 0.3s;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.styled-input:focus {
    border-color: #3b82f6;
    box-shadow: 0 0 8px rgba(59, 130, 246, 0.5);
    outline: none;
}
</style>