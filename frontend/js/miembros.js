
// Obtener las referencias de los elementos HTML.
const membersTableBody = document.getElementById("membersTableBody");
const membersMessage = document.getElementById("membersMessage");

const memberModal = document.getElementById("memberModal");
const memberForm = document.getElementById("memberForm");

const membershipPlanSelect = document.getElementById("membershipPlan");
const formMessage = document.getElementById("formMessage");

const saveMemberButton = document.getElementById("saveMemberButton");


// Mostrar mensajes en pantalla.
function showMembersMessage(message, type = "info") {
    membersMessage.replaceChildren();

    const paragraph = document.createElement("p");
    paragraph.textContent = message;
    paragraph.className = `message-${type}`;

    membersMessage.appendChild(paragraph);
}

// Mostrar los miembros en la tabla.
function renderMembers(members) {

    membersTableBody.replaceChildren();

    if (!Array.isArray(members) || members.length === 0) {
        const row = document.createElement("tr");
        const cell = document.createElement("td");

        cell.colSpan = 7;
        cell.textContent = "No hay miembros registrados.";

        row.appendChild(cell);
        membersTableBody.appendChild(row);
        return;
    }

    members.forEach((member) => {

        const row = document.createElement("tr");

        const idCell = createTextElement("td", member.id);
        const documentCell = createTextElement("td", member.documentNumber);
        const nameCell = createTextElement("td", member.fullName);
        const emailCell = createTextElement("td", member.email);

        const statusCell = document.createElement("td");
        const statusBadge = document.createElement("span");

        statusBadge.className = `member-status ${
            member.status === "ACTIVE" ? "active" : "suspended"
        }`;

        statusBadge.textContent =
            member.status === "ACTIVE" ? "Activo" : "Suspendido";

        statusCell.appendChild(statusBadge);

        const planCell = createTextElement(
            "td",
            member.membershipPlan?.name ?? "Sin plan"
        );

        const actionsCell = createTextElement("td", "Próximamente");

        row.append(
            idCell,
            documentCell,
            nameCell,
            emailCell,
            statusCell,
            planCell,
            actionsCell
        );

        membersTableBody.appendChild(row);
    });
}

// Consultar los miembros en el backend.
async function loadMembers() {

    try {
        const members = await apiFetch("/members");

        renderMembers(members);
        membersMessage.replaceChildren();

    } catch (error) {
        console.error(error);

        showMembersMessage(
            `No fue posible cargar los miembros: ${error.message}`,
            "error"
        );
    }
}

// Cargar planes de membresía.
async function loadMembershipPlans() {

    try {
        const plans = await apiFetch("/membership-plans");

        membershipPlanSelect.replaceChildren();

        const defaultOption = document.createElement("option");
        defaultOption.value = "";
        defaultOption.textContent = "Selecciona un plan";
        defaultOption.disabled = true;
        defaultOption.selected = true;

        membershipPlanSelect.appendChild(defaultOption);

        plans.forEach((plan) => {

            const option = document.createElement("option");

            option.value = plan.id;
            option.textContent = plan.name;

            membershipPlanSelect.appendChild(option);
        });

    } catch (error) {
        console.error(error);

        membershipPlanSelect.replaceChildren();

        const option = document.createElement("option");
        option.value = "";
        option.textContent = "No fue posible cargar los planes";

        membershipPlanSelect.appendChild(option);
    }
}

// Abrir formulario.
function openMemberModal() {
    formMessage.replaceChildren();
    memberModal.hidden = false;
}

// Cerrar formulario.
function closeMemberModal() {
    memberModal.hidden = true;
    memberForm.reset();
    formMessage.replaceChildren();
}

// Registrar miembro.
async function createMember(event) {

    event.preventDefault();

    const member = {
        documentNumber: document.getElementById("documentNumber").value.trim(),
        fullName: document.getElementById("fullName").value.trim(),
        email: document.getElementById("email").value.trim(),
        status: document.getElementById("memberStatus").value,
        membershipPlan: {
            id: Number(membershipPlanSelect.value)
        }
    };

    if (!member.membershipPlan.id) {
        formMessage.textContent = "Selecciona un plan de membresía.";
        return;
    }

    try {

        saveMemberButton.disabled = true;
        saveMemberButton.textContent = "Guardando...";

        await apiFetch("/members", {
            method: "POST",
            body: JSON.stringify(member)
        });

        closeMemberModal();

        await loadMembers();

        showMembersMessage(
            "¡Miembro registrado correctamente!",
            "success"
        );

    } catch (error) {

        console.error("Error al registrar miembro:", error);

        formMessage.textContent = error.message;
        formMessage.className = "message-error";

    } finally {

        saveMemberButton.disabled = false;
        saveMemberButton.textContent = "Guardar miembro";
    }
}

// Eventos.
document.getElementById("addMemberButton")
    .addEventListener("click", openMemberModal);

document.getElementById("closeMemberModal")
    .addEventListener("click", closeMemberModal);

document.getElementById("cancelMemberButton")
    .addEventListener("click", closeMemberModal);

memberForm.addEventListener("submit", createMember);

// Inicializar página.
loadMembershipPlans();
loadMembers();