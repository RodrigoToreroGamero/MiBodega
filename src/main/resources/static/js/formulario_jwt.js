document.querySelectorAll(".form-post").forEach(form => {
    form.addEventListener("submit", async e => {
        e.preventDefault();
        const datos = Object.fromEntries(new FormData(form));
        const respuesta = await fetch(form.dataset.endpoint, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(datos)
        });
        if (respuesta.ok) {
            const resultado = await respuesta.json();
            console.log("Respuesta:", resultado);
        } else {
            alert("Error al enviar los datos");
        }
    });
});

