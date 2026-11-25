
// Mostrar una colección de elementos.
function renderCatalog(containerId, items, titleField) {
    const container = document.getElementById(containerId);

    container.replaceChildren();

    if (!Array.isArray(items) || items.length === 0) {
        container.appendChild(
            createTextElement("p", "No hay registros disponibles.")
        );
        return;
    }

    items.forEach((item) => {
        const card = document.createElement("article");
        card.className = "catalog-card";

        const title =
            item[titleField] ??
            item.name ??
            item.fullName ??
            item.description ??
            "Elemento del catálogo";

        card.appendChild(createTextElement("h3", title));

        // Mostrar los demás datos sin insertar HTML externo.
        Object.entries(item).forEach(([key, value]) => {
            if (key === titleField || value === null || typeof value === "object") {
                return;
            }

            card.appendChild(
                createTextElement("p", `${key}: ${value}`)
            );
        });

        container.appendChild(card);
    });
}

// Consultar los catálogos.
async function loadCatalogs() {
    const catalogs = [
        {
            endpoint: "/locations",
            container: "locationsList",
            title: "name"
        },
        {
            endpoint: "/rooms",
            container: "roomsList",
            title: "name"
        },
        {
            endpoint: "/equipment",
            container: "equipmentList",
            title: "name"
        },
        {
            endpoint: "/membership-plans",
            container: "plansList",
            title: "name"
        },
        {
            endpoint: "/additional-services",
            container: "servicesList",
            title: "name"
        }
    ];

    await Promise.all(
        catalogs.map(async (catalog) => {
            const container = document.getElementById(catalog.container);

            try {
                const data = await apiFetch(catalog.endpoint);

                renderCatalog(
                    catalog.container,
                    data,
                    catalog.title
                );

            } catch (error) {
                container.replaceChildren(
                    createTextElement(
                        "p",
                        `No fue posible cargar este catálogo: ${error.message}`
                    )
                );
            }
        })
    );
}

loadCatalogs();
