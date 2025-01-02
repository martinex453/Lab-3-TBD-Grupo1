import httpClient from "../http-common";

const getAll = (token) => {
    //Obtener todas las categorias
    return httpClient.get("/categoria/All", {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
};

export default {
    getAll,
};