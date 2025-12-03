document.querySelectorAll(".form-post").forEach(form => {
    form.addEventListener("submit", async e => {
        e.preventDefault();
        const datos = Object.fromEntries(new FormData(form));

        try {
            const respuesta = await fetch(form.dataset.endpoint, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(datos),
                credentials: "include"
            });


            if(respuesta.redirected) {
                // Servidor no acepta los permisos de acceso y redirige
                window.location.href = respuesta.url;
            } else if(respuesta.ok) {
                console.log("Operacion exitosa");
                window.location.href = "/";
            } else if (respuesta.status === 401) {
                window.location.href = "/mibodega/login";
            } else if(respuesta.status === 403) {
                alert("No autorizado, por favor iniciar sesión");
                window.window.location.href="/mibodega/login";
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

