<template>
    <div class="container">
        <div class="order-review">
            <h1>Reseña del producto:</h1>
            <h2>{{this.name}}</h2>
            <div class="image">
                <img class="product-img" src="./images/caja.png" alt="Producto" />
            </div>
            <div class="star-rating">
                <span v-for="star in 5" :key="star" @click="setRating(star)" :class="{'active': star <= rating}">
                    ★
                </span>
            </div>
            <textarea v-model="reviewComment" placeholder="Escribe tu comentario aquí..."></textarea>
            <button @click="submitReview">Enviar Reseña</button>
        </div>
    </div>
</template>

<script>
import productService from '@/services/productService';
    export default {    
        data() {
            return {
                productId: this.$route.params.id,
                token: this.$cookies.get("jwt"),
                idUser: localStorage.getItem("idUser"),
                rating: 0,
                reviewComment: '',
                name: '',
            };
        },
        methods: {
            setRating(star) {
                this.rating = star;
                console.log('Calificación:', this.rating);
            },
            async submitReview() {
                if (this.rating === 0 || this.reviewComment.trim() === '') {
                    alert('Por favor, completa la calificación y el comentario.');
                    return;
                }
                /*{
                    "idUsuario": 12,
                    "idProducto": 45,
                    "puntuacion": 5,
                    "comentario": "Excelente producto, lo recomiendo!"
                } */
                const resena = {
                    idUsuario: Number(this.idUser),
                    idProducto: Number(this.productId),
                    puntuacion: this.rating,
                    comentario: this.reviewComment
                };
                console.log(resena);
                console.log(this.token);
                const respuesta = await productService.submitReview(resena, this.token);
                console.log(respuesta.data);
                if(respuesta.data == ""){
                    alert('Ya has enviado una reseña para este producto.');
                    this.$router.push('/myOrders');
                }
                else{
                    alert('Reseña enviada con éxito.');
                    this.$router.push('/myOrders');
                }
                
            }
        },
        async mounted() {
                console.log(this.productId)
                try {
                    const prod = await productService.getProductById(this.productId, this.token);
                    this.name = prod.data.nombre;
                } catch (error) {
                    console.error('Error al cargar el producto', error);
                }
            }
    };
</script>

<style>
.container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    margin: auto;
}

.order-review {
    background-color: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    margin-top: 20px;
}

.star-rating {
    display: flex;
    justify-content: center;
    margin-bottom: 10px;
}

.star-rating span {
    font-size: 30px;
    cursor: pointer;
    color: #ddd;
    transition: color 0.3s;
}

.star-rating span.active {
    color: #f39c12;
}

textarea {
    width: 100%;
    height: 100px;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ddd;
    margin-bottom: 10px;
    resize: none;
}
</style>