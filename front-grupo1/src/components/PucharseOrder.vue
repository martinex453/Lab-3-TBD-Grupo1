<script>
import productService from '@/services/productService';
import orderService from '@/services/orderService';
import orderDetailService from '@/services/orderDetailService';
export default {
    //Definir las propiedades del componente
    data() {
        return {
            product: null,
            amount: 1,
            token: this.$cookies.get("jwt"),
            idUser: localStorage.getItem("idUser")
        }
    },
    computed: {
        totalPrice() {
            //Calcular el precio total del producto
            return this.product?.precio * this.amount;
        },
        productStock() {
            //Calcular el stock del producto
            return this.product?.stock - 1;
        }
    },
    methods: {
        async getProduct(){
            try {
                //Obtener el id del producto y buscarlo
                const id = this.$route.params.id;
                const response = await productService.getProductById(id, this.token);
                this.product = response.data;
            } catch (error) {
                //Mostrar un mensaje de error si no se encuentra el producto
                alert('Error al obtener producto');
            }
        },
        async makeOrder(){
            try {
                if(this.amount > this.product.stock){
                    //Mostrar un mensaje si no hay suficiente stock
                    alert('No hay suficiente stock');
                    return;
                }
                //Buscar si el producto ya está en el carrito y actualizar la cantidad
                for(let i = 0; i < this.$carrito.length; i++){
                    if(this.$carrito[i][0] == this.$route.params.id){
                        this.$carrito[i][1] += this.amount;
                        this.updateCarrito();
                        return;
                    }
                }
                //Agregar el producto al carrito y actualizarlo
                const product_list = [this.$route.params.id, this.amount, this.product.precio];
                this.$carrito.push(product_list);
                this.updateCarrito();
                alert('Producto agregado al carrito');
                console.log(this.$carrito);
            } catch (error) {
                //Mostrar un mensaje de error si no se puede realizar la orden
                alert('Error al realizar orden');
            }
        },
        validateAmount() {
            //Validar la cantidad del producto
            if (this.amount < 1) {
                this.amount = 0;
            }
        }

    },
    mounted() {
        //Obtener el producto al cargar el componente
        this.getProduct();
    }
}
</script>

<template>
    <div class="pucharce-order-container">
        <div class="image">
            <img class="product-img" src="./images/caja.png" alt="Producto" />
        </div>
        <div class="product-information">
            <h1>{{ this.product?.nombre }}</h1>
            <h2>Descripcion del producto:</h2>
            <br>
            <p>{{ this.product?.descripcion }}</p>

        </div>
        <div class="order-form-container">
            <h1 class="order-title">Detalles de la orden</h1>
            <div class="pucharse-information">
                <h1>Precio: ${{this.product?.precio}} clp</h1>
                <h1>Cantidad</h1>
                <input type="number" min="1" step="1" v-model="amount" @input="validateAmount" class="amount-selection">
                <h1>Stock: {{this.product?.stock}}</h1>
                <h1>Estado: {{this.product?.estado}}</h1>
                <h1>Precio total: </h1>
                <h1>${{totalPrice}} clp</h1>
                <br>
            </div>
            <button v-if="this.product?.stock>=1" class="pucharse-button" @click="makeOrder">Agregar a la orden</button>
            <h1 v-else class="no-stock-text">No hay stock del producto</h1>
        </div>
    </div>
</template>

<style>
.pucharce-order-container {
    display: grid;
    grid-template-columns: 60% 1fr;
    grid-template-rows: auto 1fr;
    gap: 20px;
    padding: 20px;
    width: 100%;
    height: 100vh;
    box-sizing: border-box;
    background-color: #F3F4F6;
    color: #1F2937;
}

.image {
    grid-column: 1 / 2;
    grid-row: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    max-height: 300px;
    border-radius: 15px;
    background-color: #E5E7EB;
    overflow: hidden;
    padding: 10px;
}

.product-img {
    width: 100%;
    max-width: 400px;
    height: auto;
    max-height: 300px;
    object-fit: contain;
    border-radius: 10px;
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
}

.product-information {
    grid-column: 1 / 2;
    grid-row: 2;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    padding: 20px;
    border-radius: 15px;
    background-color: #E5E7EB;
    color: #374151;
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
}

.order-form-container {
    grid-column: 2;
    grid-row: 1 / 3;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 20px;
    border-radius: 15px;
    background-color: #E5E7EB;
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
}

.pucharse-button, .no-stock-text {
    font-size: 1.5rem;
    width: 100%;
    max-width: 20rem;
    height: 3rem;
    padding: 10px;
    border-radius: 5px;
    text-align: center;
    color: white;
    margin-top: 20px;
}

.pucharse-button {
    background-color: #60A5FA;
}

.no-stock-text {
    background-color: #EF4444;
}

.amount-selection {
    width: 100%;
    max-width: 15rem;
    height: 2.5rem;
    padding: 10px;
    border-radius: 5px;
    text-align: center;
    font-size: 1rem;
    margin-bottom: 20px;
    background-color: white;
    color: #374151;
    border: 1px solid #D1D5DB;
}

.pucharse-information {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    gap: 10px;
    padding: 20px;
    border-radius: 15px;
    background-color: #E5E7EB;
}

.order-title {
    text-align: center;
    font-size: 1.8rem;
    margin-bottom: 20px;
    color: #1F2937;
    background-color: #F9FAFB;
    padding: 10px;
    border-radius: 10px;
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
}
</style>