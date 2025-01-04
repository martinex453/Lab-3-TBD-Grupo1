import { createRouter, createWebHistory } from 'vue-router';
import RegisterLogin from '../components/RegisterLogin.vue';
import Products from '../components/Products.vue';
import PurchaseOrder from '../components/PucharseOrder.vue';
import totalOrderDetail from '../components/totalOrderDetail.vue';
import OrderDetail from '../components/OrderDetail.vue';
import MyOrders from '../components/MyOrders.vue';
import EditProduct from '../components/EditProduct.vue';
import ProcedimientosAlmacenados from '../components/ProcedimientosAlmacenados.vue';
import Comment from '../components/Comment.vue';

//Definir las rutas de la aplicacion
const routes = [
    {
        path: '/',
        name: 'register-login',
        component: RegisterLogin,
        meta: { requiresAuth: false}
    },
    {
        path: '/products',
        name: 'products',
        component: Products,
        meta: { requiresAuth: true}
    },
    {
        path: '/purchase/:id',
        name: 'pucharse-order',
        component: PurchaseOrder,
        meta: { requiresAuth: true}
    },
    {
        path: '/order',
        name: 'totalOrderDetail',
        component: totalOrderDetail,
        meta: { requiresAuth: true}
    },
    {
        path: '/order/:id',
        name: 'orderDetail',
        component: OrderDetail,
        meta: { requiresAuth: true}
    },
    {
        path: '/edit-product/:id',
        name: 'editProduct',
        component: EditProduct,
        meta: { requiresAuth: true}
    },
    {
      path: '/comment/:id',
      name: 'Comment',
      component: Comment,
      meta: { requiresAuth: true}
    },
    {
        path: '/myOrders',
        name: 'MyOrders',
        component: MyOrders,
        meta: { requiresAuth: true}
    },
    {
        path: '/procedures',
        name: 'procedure',
        component: ProcedimientosAlmacenados,
        meta: { requiresAuth: true, requiresAdmin: true}
    }
];

//Crear el router
const router = createRouter({
    history: createWebHistory(),
    routes,
  });
  
//Proteger las rutas exclusivas para usuarios admin
router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAdmin)) {
    const userRole = localStorage.getItem("userRole");
    if (userRole === 'admin') {
      next();
    } else {
      next({ name: 'products' });
    }
  } else {
    next();
  }
});
  
export default router;