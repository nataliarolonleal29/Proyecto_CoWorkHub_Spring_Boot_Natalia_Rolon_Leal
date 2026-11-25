
const API_URL = "http://localhost:8080/api";

// Obtener el token guardado durante el inicio de sesión.
function getToken() {
    return sessionStorage.getItem("token");
}

// Realizar solicitudes al backend.
async function apiFetch(endpoint, options = {}) {
    const token = getToken();

    const headers = {
        ...options.headers
    };

    if (token) {
        headers.Authorization = token;
    }

    if (options.body) {
        headers["Content-Type"] = "application/json";
    }

    const response = await fetch(`${API_URL}${endpoint}`, {
        ...options,
        headers
    });

    // Leer la respuesta del servidor.
    const contentType = response.headers.get("content-type") || "";

    let data = null;

    if (contentType.includes("application/json")) {
        data = await response.json();
    } else {
        data = await response.text();
    }

    // Si el servidor devuelve un error, informarlo.
    if (!response.ok) {
        const message =
            data?.message ||
            data?.error ||
            `Error HTTP ${response.status}`;

        throw new Error(message);
    }

    return data;
}

// Mostrar datos de manera segura dentro de una tarjeta.
function createTextElement(tag, text, className = "") {
    const element = document.createElement(tag);
    element.textContent = text ?? "";

    if (className) {
        element.className = className;
    }

    return element;
}
