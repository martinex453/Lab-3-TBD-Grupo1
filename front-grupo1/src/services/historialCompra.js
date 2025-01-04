import httpClient from '../http-common';

const agregarCompra = (id, token) => {
    return httpClient.post(`/historialCompras/${id_usuario}`, companyService, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
};

export default {
    agregarCompra
};