
// Elementos de la página.
const bookingForm = document.getElementById("bookingForm");
const bookingsList = document.getElementById("bookingsList");
const bookingMessage = document.getElementById("bookingMessage");
const loadBookingsButton = document.getElementById("loadBookingsButton");

// Consultar reservas.
async function loadBookings() {
    bookingsList.replaceChildren(
        createTextElement("p", "Cargando reservas...")
    );

    try {
        const bookings = await apiFetch("/bookings");

        bookingsList.replaceChildren();

        if (!Array.isArray(bookings) || bookings.length === 0) {
            bookingsList.appendChild(
                createTextElement("p", "No hay reservas para mostrar.")
            );
            return;
        }

        bookings.forEach((booking) => {
            const card = document.createElement("article");
            card.className = "booking-card";

            card.appendChild(
                createTextElement(
                    "h3",
                    `Reserva #${booking.id ?? "Sin identificador"}`
                )
            );

            card.appendChild(
                createTextElement(
                    "p",
                    `Estado: ${booking.status ?? "No disponible"}`
                )
            );

            card.appendChild(
                createTextElement(
                    "p",
                    `Inicio: ${booking.startTime ?? "No disponible"}`
                )
            );

            card.appendChild(
                createTextElement(
                    "p",
                    `Finalización: ${booking.endTime ?? "No disponible"}`
                )
            );

            card.appendChild(
                createTextElement(
                    "p",
                    `Asistentes: ${booking.attendees ?? "No disponible"}`
                )
            );

            bookingsList.appendChild(card);
        });

    } catch (error) {
        bookingsList.replaceChildren(
            createTextElement(
                "p",
                `No fue posible consultar las reservas: ${error.message}`
            )
        );
    }
}

// Crear una reserva.
if (bookingForm) {
    bookingForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        bookingMessage.textContent = "";
        bookingMessage.className = "message";

        const roomId = Number(document.getElementById("roomId").value);
        const attendees = Number(document.getElementById("attendees").value);
        const startTime = document.getElementById("startTime").value;
        const endTime = document.getElementById("endTime").value;
        const memberIdValue = document.getElementById("memberId").value;

        if (new Date(endTime) <= new Date(startTime)) {
            bookingMessage.textContent =
                "La hora de finalización debe ser posterior al inicio.";
            bookingMessage.classList.add("error");
            return;
        }

        // Construir el objeto que espera BookingRequestDTO.
        const bookingData = {
            roomId,
            attendees,
            startTime,
            endTime
        };

        // Solo el personal debe indicar el miembro.
        if (memberIdValue.trim() !== "") {
            bookingData.memberId = Number(memberIdValue);
        }

        try {
            await apiFetch("/bookings", {
                method: "POST",
                body: JSON.stringify(bookingData)
            });

            bookingMessage.textContent = "¡Reserva creada correctamente!";
            bookingMessage.classList.add("success");

            bookingForm.reset();
            loadBookings();

        } catch (error) {
            bookingMessage.textContent = error.message;
            bookingMessage.classList.add("error");
        }
    });
}

// Actualizar listado.
if (loadBookingsButton) {
    loadBookingsButton.addEventListener("click", loadBookings);
}

// Cargar reservas al abrir la página.
loadBookings();
