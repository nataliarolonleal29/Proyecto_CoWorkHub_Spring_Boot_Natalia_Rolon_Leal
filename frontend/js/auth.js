
// Comprobar si el usuario ya inició sesión.
if (window.location.pathname.endsWith("/index.html") ||
    window.location.pathname.endsWith("/")) {

    if (getToken()) {
        window.location.href = "dashboard.html";
    }
}

// Iniciar sesión.
const loginForm = document.getElementById("loginForm");

if (loginForm) {
    loginForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        const username = document.getElementById("username").value.trim();
        const password = document.getElementById("password").value;
        const message = document.getElementById("loginMessage");
        const button = document.getElementById("loginButton");

        message.textContent = "";
        message.className = "message";
        button.disabled = true;
        button.textContent = "Ingresando...";

        try {
            const response = await fetch(`${API_URL}/auth/login`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    username,
                    password
                })
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(
                    data.message || "No fue posible iniciar sesión."
                );
            }

            // Guardar el token y el nombre de usuario.
            sessionStorage.setItem(
                "token",
                `${data.tokenType || "Bearer"} ${data.accessToken}`
            );

            sessionStorage.setItem("username", data.username);

            window.location.href = "dashboard.html";

        } catch (error) {
            message.textContent = error.message;
            message.classList.add("error");

        } finally {
            button.disabled = false;
            button.textContent = "Iniciar sesión";
        }
    });
}

// Cerrar sesión.
const logoutButton = document.getElementById("logoutButton");

if (logoutButton) {
    logoutButton.addEventListener("click", () => {
        sessionStorage.removeItem("token");
        sessionStorage.removeItem("username");

        window.location.href = "index.html";
    });
}

// Proteger las páginas internas.
const isLoginPage =
    window.location.pathname.endsWith("/index.html") ||
    window.location.pathname.endsWith("/");

if (!isLoginPage && !getToken()) {
    window.location.href = "index.html";
}

// Mostrar el nombre del usuario.
const welcomeUser = document.getElementById("welcomeUser");

if (welcomeUser) {
    welcomeUser.textContent =
        sessionStorage.getItem("username") || "Usuario";
}
