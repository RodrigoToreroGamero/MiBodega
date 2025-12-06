document.querySelectorAll(".form-post").forEach(form => {
    form.addEventListener("submit", async e => {
        e.preventDefault();
        const payload = Object.fromEntries(new FormData(form));

        try {
            const respuesta = await fetch(form.dataset.endpoint, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(payload)
            });

            if (respuesta.ok) {
                const datos = await respuesta.json();
                sessionStorage.setItem("jwt", datos.jwt);
                window.location.href = "/mibodega/registro";
            } else if (respuesta.status === 401) {
                alert("Credenciales inválidas");
            } else if (respuesta.status === 403) {
                alert("No autorizado, por favor iniciar sesión");
                window.location.href = "/mibodega/login";
            } else {
                const msg = await respuesta.text();
                alert(`Error: ${msg}`);
            } 
        } catch (error) {
            console.error(error);
            alert("No se pudo conectar con el servidor");
        }

    });
});
//formulario_jwt

