<script></script>

<template>
    <div class="container-order-summary">
        <div class="order-title">
            <h1>Resumen orden de compra</h1>
            <h2>Estado de la orden: {{ orderStatus }}</h2>
        </div>
        <div class="order-details">
            <table>
                <thead>
                    <tr>
                        <th>Producto</th>
                        <th>Cantidad</th>
                        <th>Precio Unitario</th>
                        <th v-if="userRol != 'admin'"> Valorar</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="detail in totalOrderDetail" :key="detail.id_detalle">
                        <td>{{ detail.name }}</td>
                        <td>{{ detail.cantidad }}</td>
                        <td>{{ detail.precio_unitario }}</td>
                        <td v-if="userRol != 'admin'">
                            <button @click="goToComment(detail.id_producto)">Valorar</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="order-summary">
            <h1>Precio total:</h1>
            <h2>${{ this.totalPrice }} CLP</h2>
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
            totalOrderDetail: [],
            totalPrice: 0,
            token: this.$cookies.get("jwt"),
            orderStatus: '',
            rating: 0,
            reviewComment: '',
            userRol: localStorage.getItem("userRole"),
        };
    },
    methods: {
        async getTotalOrderDetail() {
            //Obtener el id de la orden de compra
            const orderId = this.$route.params.id;
            
            if (!orderId) {
                //Mostrar un mensaje de error si no se encuentra la orden de compra
                alert("No se ha encontrado una orden de compra.");
                return;
            }
            try {
                console.log(orderId);
                //Obtener los detalle de la orden de compra
                const response = await orderDetailService.getOrderDetailByOrderId(orderId, this.token);
                this.totalOrderDetail = response.data;
                console.log(response);
                console.log(this.totalOrderDetail);
                //Obtener los nombres de los productos
                for (const detail of this.totalOrderDetail) {
                    const productResponse = await productService.getProductById(detail.id_producto, this.token);
                    console.log(productResponse.data.nombre);
                    detail.name = productResponse.data.nombre;
                    console.log(detail);
                }
                //Obtener el precio total de la orden de compra y su estado
                const total = await orderService.getOrderById(orderId, this.token);
                this.totalPrice = total.data.total;
                this.orderStatus = total.data.estado;
            } catch (error) {
                //Mostrar un mensaje de error si no se pueden obtener los detalles de la orden de compra
                alert('Error al obtener detalle de orden');
            }
        },
        goToComment(id){
            this.$router.push(`/comment/${id}`)
        },
    },
    
    created() {
        //Obtener los detalles de la orden de compra al cargar el componente
        this.getTotalOrderDetail();
    }
};
</script>

<style>
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

th, td {
    text-align: center;
    padding: 10px;
    border-bottom: 1px solid #ddd;
}

tbody tr:hover {
    background-color: #f9f9f9;
}

h1, h2 {
    text-align: center;
    color: #333;
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