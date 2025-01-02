import httpClient from '../http-common';

const AreaCompany = (id, token) => {
    return httpClient.get(`/empresa/area_zonas/${id}`,{
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
};

const getAll = (token) => {
    //Obtener todas las empresas
    return httpClient.get("/empresa/All", {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
};

export default {
    AreaCompany,
    getAll
}