import httpClient from "../http-common";

const createCliente = (cliente) => {
    //Enviar solicitud para crear un cliente
    return httpClient.post("/cliente/crear_cuenta", cliente);
};

const loginCliente = (email, contrasena) => {
    const data = {
        email: email,
        contrasena: contrasena
    }
    //Enviar solicitud de inicio de sesion
    return httpClient.post(`/authenticate/login`,data, {
        headers: "application/json"
    });
};

const getRole = (id, token) => {
    //Obtener el rol de un cliente
    return httpClient.get(`/cliente/getRol?id=${id}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
};

const getTopUsers = (token) => {
    //Obtener el top de usuarios que mas querys han hecho
    return httpClient.get(`/top_usuarios`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
};

const getTopSpenders = (token) => {
    //Obtener el top 5 de usuarios que mas han gastado en la categoria tecnologia
    return httpClient.get("/cliente/getTop5", {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
};


export default {
    createCliente,
    loginCliente,
    getRole,
    getTopUsers,
    getTopSpenders
};

