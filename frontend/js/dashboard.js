
// Consultar las sedes y salas registradas.
async function loadDashboard() {
    const locationsElement = document.getElementById("locationsCount");
    const roomsElement = document.getElementById("roomsCount");
    const bookingsElement = document.getElementById("bookingsCount");

    try {
        const [locationsResult, roomsResult, bookingsResult] =
            await Promise.allSettled([
                apiFetch("/locations"),
                apiFetch("/rooms"),
                apiFetch("/bookings")
            ]);

        if (locationsResult.status === "fulfilled") {
            locationsElement.textContent =
                Array.isArray(locationsResult.value)
                    ? locationsResult.value.length
                    : "No disponible";
        } else {
            locationsElement.textContent = "No disponible";
        }

        if (roomsResult.status === "fulfilled") {
            roomsElement.textContent =
                Array.isArray(roomsResult.value)
                    ? roomsResult.value.length
                    : "No disponible";
        } else {
            roomsElement.textContent = "No disponible";
        }

        if (bookingsResult.status === "fulfilled") {
            bookingsElement.textContent =
                Array.isArray(bookingsResult.value)
                    ? bookingsResult.value.length
                    : "No disponible";
        } else {
            bookingsElement.textContent = "Restringido";
        }

    } catch (error) {
        console.error("Error al cargar el panel:", error);
    }
}

loadDashboard();
