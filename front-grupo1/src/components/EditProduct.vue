<template>
    <div class="pucharce-order-container">
        <div class="image">
            <img class="product-img" src="./images/caja.png" alt="Producto" />
        </div>
        <div class="product-information">
            <h1>Nombre</h1>
            <input type="text" :placeholder="name" class="input-field" v-model="name">
            <h2>Descripcion del producto:</h2>
            <textarea name="description" id="description" cols="30" rows="10" :placeholder="description" class="textarea-field" v-model="description"></textarea>
            <br>
        </div>
        <div class="order-form-container">
            <h1 class="order-title">Detalles del producto</h1>
            <div class="pucharse-information">
                <h1>Precio:</h1>
                <input type="number" :placeholder="price" class="input-field" v-model="price">
                <h1>Stock:</h1>
                <input type="number" :placeholder="stock" class="input-field" v-model="stock">
                <h1>Estado</h1>
                <select id="status" v-model="state" class="select-state">
                    <option value="disponible">Disponible</option>
                    <option value="agotado">Agotado</option>
                </select>
                <br>
            </div>
            <button class="act-button" @click="updateProduct">Actualizar</button>
        </div>
    </div>
</template>

<script>
import productService from '@/services/productService';
export default {
    //Definir las propiedades del componente
    data() {
        return {
            product: null,
            amount: 1,
            token: this.$cookies.get("jwt"),
            idUser: localStorage.getItem("idUser"),
            name: "",
            description: "",
            stock: "",
            price: "",
            state: ""
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
                //Asignar los valores del producto a las variables
                this.product = response.data;
                this.name = this.product.nombre;
                this.description = this.product.descripcion;
                this.stock = String(this.product.stock);
                this.price = String(this.product.precio);
            } catch (error) {
                //Mostrar un mensaje de error si no se encuentra el producto
                alert('Error al buscar producto');
            }
        },
        validateAmount() {
            //Validar la cantidad del producto
            if (this.amount < 1) {
                this.amount = 0;
            }
        },
        async updateProduct(){
            try{
                //Verificar si el campo de estado esta vacio
                if(this.state === ""){
                    this.state = this.product.estado;
                }
                //Construir el objeto con los nuevos valores del producto
                const newProduct = {
                    id_producto: this.product.id_producto,
                    nombre: this.name,
                    descripcion: this.description,
                    stock: Number(this.stock),
                    precio: Number(this.price),
                    estado: this.state,
                    id_categoria: this.product.id_categoria
                }
                console.log(newProduct);
                console.log(this.product.id_producto);
                //Actualizar el producto
                const userId = localStorage.getItem("idUser");
                await productService.updateProduct(this.product.id_producto, newProduct, userId, this.token);
                //Mostrar un mensaje de exito y redirigir a la pagina de productos
                alert('Producto actualizado');
                this.$router.push("/products");
            }
            catch(error){
                //Mostrar un mensaje de error si no se puede actualizar el producto
                alert('Error al actualizar producto');
            }
        }

    },
    mounted() {
        //Llamar al metodo para obtener el producto
        this.getProduct();
    }
}
</script>

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

.input-field, .select-state, .textarea-field {
    width: 100%;
    max-width: 30rem;  /* Aumenta el ancho máximo */
    padding: 15px;
    font-size: 1.2rem;
    margin-bottom: 15px;
    border-radius: 8px;
    border: 1px solid #D1D5DB;
    box-sizing: border-box;
    background-color: #fff;
    color: #374151;
    transition: border-color 0.2s ease;
}

.textarea-field {
    resize: vertical;
    height: 150px;
    font-size: 1.2rem;
}

.input-field:focus, .select-state:focus, .textarea-field:focus {
    border-color: #60A5FA;
    outline: none;
}


.act-button, .no-stock-text {
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

.act-button {
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
