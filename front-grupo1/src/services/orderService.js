import httpClient from '../http-common';

const makeOrder = (order, clientId, token) => {
    //Enviar solicitud de creacion de una orden
    console.log("Enviando orden");
    return httpClient.post(`/orden/crear?id=${clientId}`, order, {
        headers: {
            Authorization: `Bearer ${token}`, 
        },
    });
};

const getOrderByUserId = (id, nroPag, tamanioPag, token) => { 
    //Obtener ordenes de un usuario
    return httpClient.get(`/orden/pagina/user/${id}/${nroPag}/${tamanioPag}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
};

const getOrderById = (id, token) => {
    //Obtener orden por su id
    return httpClient.get(`/orden/getOrderid/${id}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
}

const updateOrder = (id, order, clientId, token) => {
    //Actualizar orden
    return httpClient.post(`/orden/update/${id}?id_cliente=${clientId}`, order, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
}

const orderByTimestamp = (token) => {
    //Buscar ordenes por su fecha de creacion
    return httpClient.get("/orden/timestamp", {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
}

const submitOrder = (carrito, idUser, token, latitud, longitud) => {
    //Crear orden de compra
    return httpClient.post(`/crearOrdenCompra/${idUser}?latitud=${latitud}&longitud=${longitud}`, carrito, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
}

const getOrders = (nroPag, tamanioPag, token) => {
    //Obtener ordenes paginadas
    return httpClient.get(`/orden/pagina/${nroPag}/${tamanioPag}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
};

const verificarZona = (x, y, token) => {
    // Verificar si el punto está en una zona restringida
    return httpClient.get(`/orden/point_restricted?x=${x}&y=${y}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
};

const getNumberOfPages = (user, pageSize, token) => {
    //Obtener numero de paginas
    return httpClient.get(`/orden/TotalPages/user/${user}/${pageSize}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
}

const getNumberOfPagesAdmin = (pageSize, token) => {
    //Obtener numero de paginas
    return httpClient.get(`/orden/TotalPages/${pageSize}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
}

export default {
    makeOrder,
    getOrderByUserId,
    getOrderById, 
    updateOrder,
    orderByTimestamp,
    submitOrder,
    getOrders,
    verificarZona,
    getNumberOfPages,
    getNumberOfPagesAdmin
};