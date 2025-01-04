import httpClient from '../http-common';

const getProductById = (id, token) => {
    //Obtener producto por su id
    return httpClient.get(`/producto/get/${id}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
};

const updateProduct = (id, product, clientId, token) => {
    //Actualizar producto
    return httpClient.post(`/producto/update/${id}?id_cliente=${clientId}`, product, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
};

const getAllproducts = (token) => {
    //Obtener todos los productos
    console.log(token);
    return httpClient.get(`/producto/All`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
}

const updateStock = (id, stock, token, clientId) => {
    //Actualizar stock de un producto
    return httpClient.put(`/producto/updateStock/${id}/${stock}?id_cliente=${clientId}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
}

const getPoductsForPages = (page, pageSize, token) => {
    //Obtener productos paginados
    return httpClient.get(`/producto/pagina/${page}/${pageSize}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
}

const applyDiscount = (token, idCategory, discount) =>{
    //Aplicar descuento a una categoria
    return httpClient.get(`/aplicar_descuento?idCategoria=${idCategory}&descuento=${discount}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
}

const getNumberOfPages = (pageSize, token) => {
    //Obtener numero de paginas
    return httpClient.get(`/producto/getTotalPages/${pageSize}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
}

const submitReview = (resena, token) => {
    //Agregar review al producto
    return httpClient.post(`/resenas/crear`, resena, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
}

const getRecomendaciones = (idUsuario, token) => {
    //Trae productos recomendados para el user
    return httpClient.get(`/historialCompras/historial/recomendaciones/${idUsuario}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
}

const getCategoriaId = (id_categoria, token) => {
    //Obtener producto por su id
    return httpClient.get(`/producto/getCategoriaId/${id_categoria}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
}

export default {
    getProductById,
    updateProduct,
    getAllproducts,
    updateStock,
    getPoductsForPages,
    applyDiscount,
    getNumberOfPages,
    submitReview,
    getRecomendaciones,
    getCategoriaId
};
