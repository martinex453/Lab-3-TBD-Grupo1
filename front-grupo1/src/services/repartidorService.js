import httpClient from '../http-common';

const getRepartidorByZone = (id, token) => {
    return httpClient.get(`/repartidor/getByZona/${id}`,
        {
            headers: {
                Authorization: `Bearer ${token}`,
            },
    
        });
}

const getRepartidorRandom = token => {
    return httpClient.get('/repartidor/random',
    {
        headers: {
            Authorization: `Bearer ${token}`,
        },

    });
}

const createOrderRepartidor = (tupla, id_cliente, token) => {
    return httpClient.post(`/OrdenZonaRepartidor/crear?id_cliente=${id_cliente}`, tupla,
    {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
}

export default {
    getRepartidorByZone,
    getRepartidorRandom,
    createOrderRepartidor
};