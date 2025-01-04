<template>
    <div class="products-container">
        <h1 class="title">Lista de Productos</h1>
        <div class="products-list">
            <div class="product-item" v-for="product in products" :key="product.id">
                <img class="product-img" src="./images/caja.png" alt="Producto" />
                <h2>{{ product.nombre }}</h2>
                <p>{{ product.descripcion }}</p>
                <h3>Precio: ${{ product.precio }} CLP</h3>
                <h4>Stock: {{ product.stock === 0 ? 'Producto no disponible' : product.stock }}</h4>
                <button class="purchase-button" v-if="product.stock > 0" @click="productDetails(product)">Comprar</button>
                <button v-if="this.isAdmin" class="edit-button" @click="editProduct(product.id_producto)">Editar</button>
            </div>
        </div>
        <div class="pagination-container">
            <button v-if="showPrevButton" @click="changePage(page - 1)" class="pageButton">Ant</button>
            <button v-for="p in visiblePages" :key="p" @click="goToPage(p)" class="pageButton">{{ p }}</button>
            <button v-if="showNextButton" @click="changePage(page + 1)" class="pageButton">Sig</button>
        </div>
        <h1 class="title1" v-if="!isEmptyRecomendations">Productos Recomendados</h1>
        <div class="selected-products-list">
            <div class="product-item" v-for="product in selectedProducts" :key="product.id">
                <img class="product-img" src="./images/caja.png" alt="Producto" />
                <h2>{{ product.nombre }}</h2>
                <p>{{ product.descripcion }}</p>
                <h3>Precio: ${{ product.precio }} CLP</h3>
                <h4>Stock: {{ product.stock === 0 ? 'Producto no disponible' : product.stock }}</h4>
                <button class="purchase-button" v-if="product.stock > 0" @click="productDetails(product)">Comprar</button>
                <button v-if="this.isAdmin" class="edit-button" @click="editProduct(product.id_producto)">Editar</button>
            </div>
        </div>
    </div>
</template>

<script>
import { isEmpty } from 'lodash';
import productService from '../services/productService.js';

export default {
    //Definir las propiedades del componente
    data() {
        return {
            selectedProducts: [],
            products: [],
            page: 1,
            pageSize: 12,
            isAdmin: localStorage.getItem("userRole") === "admin",
            numberOfPages: 0,
            visiblePages: []
        };
    },
    computed: {
        showPrevButton() {
            //Verificar si se puede mostrar el botón de página anterior
            return this.page > 1;
        },
        showNextButton() {
            //Verificar si se puede mostrar el botón de página siguiente
            return this.page <= this.numberOfPages - 1;
        },
        isEmptyRecomendations() {
            return isEmpty(this.selectedProducts);
        }
    },
    methods: {
        async getProducts() {
            try {
                const token = this.$cookies.get("jwt");
                const response = await productService.getPoductsForPages(this.page, this.pageSize, token);
                this.products = response.data;
            } catch (error) {
                console.error(error);
            }
        },
        async numberPages() {
            const response = await productService.getNumberOfPages(this.pageSize, this.$cookies.get("jwt"));
            this.numberOfPages = response.data;
            this.updateVisiblePages();
        },
        updateVisiblePages() {
            const range = 2; // Número de páginas a mostrar antes y después de la página actual
            const start = Math.max(1, this.page - range);
            const end = Math.min(this.numberOfPages, this.page + range);

            this.visiblePages = [];
            for (let i = start; i <= end; i++) {
                this.visiblePages.push(i);
            }
        },
        async changePage(newPage) {
            if (newPage < 1 || newPage > this.numberOfPages) return;
            this.page = newPage;
            this.getProducts();
            this.updateVisiblePages();  // Actualiza las páginas visibles
        },
        productDetails(product) {
            //Redirigir al usuario a la página de compra del producto
            this.$router.push(`/purchase/${product.id_producto}`);
        },
        goToPage(page) {
            this.page = page;
            this.getProducts();
            this.updateVisiblePages();  // Actualiza las páginas visibles
        },
        editProduct(productId) {
            //Redirigir al usuario a la página de edición del producto
            this.$router.push(`/edit-product/${productId}`);
        },
        async getSelectedProducts(){
            try {
                const token = this.$cookies.get("jwt");
                const userId = localStorage.getItem("idUser");
                const response = await productService.getRecomendaciones(userId, token);
                this.selectedProducts = response.data;
            } catch (error) {
                console.error(error);
            }
        }
    },
    mounted() {
        //Obtener los productos al cargar el componente
        this.getProducts();
        this.numberPages();
        this.getSelectedProducts();
    },
};
</script>

<style>
.products-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px;
    background-color: #f0f0f0;
    min-height: 100vh;
    width: 100%;
}

.title, .title1 {
    font-size: 24px;
    color: #333;
    margin-bottom: 20px;
    text-align: center;
}

.title1 {
    margin-top: 20px;
}

.products-list, .selected-products-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 20px;
    width: 100%;
    max-width: 1200px;
    margin: 0 auto;
    justify-content: center;
}

.product-item {
    background-color: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    transition: transform 0.3s;
    height: 100%;
    min-height: 300px;
}

.product-item:hover {
    transform: translateY(-5px);
}

.product-img {
    width: 100%;
    max-height: 150px;
    object-fit: cover;
    margin-bottom: 15px;
}

.purchase-button,
.edit-button {
    padding: 12px;
    background-color: #3b82f6;
    color: white;
    border: none;
    border-radius: 5px;
    font-size: 14px;
    cursor: pointer;
    transition: background-color 0.3s;
    margin-top: 10px;
}

.purchase-button:hover,
.edit-button:hover {
    background-color: #2563eb;
}

.product-img {
    width: 100%;
    max-height: 150px;
    object-fit: cover;
    margin-bottom: 15px;
}

.product-item h2,
.product-item p,
.product-item h3,
.product-item h4 {
    margin: 0;
    text-align: center;
}

.product-item h2 {
    font-size: 18px;
    color: #333;
    margin-bottom: 10px;
}

.product-item p {
    font-size: 14px;
    color: #666;
    margin-bottom: 10px;
    line-height: 1.4;
}

.product-item h3,
.product-item h4 {
    font-size: 16px;
    color: #333;
    margin-bottom: 10px;
}

.pagination-container {
    display: flex;
    justify-content: center;
    gap: 10px;
    margin-top: 20px;
}

.pageButton {
    padding: 12px;
    background-color: #3b82f6;
    color: white;
    border: none;
    border-radius: 5px;
    width: 70px;
    font-size: 14px;
    transition: background-color 0.3s;
}
</style>
