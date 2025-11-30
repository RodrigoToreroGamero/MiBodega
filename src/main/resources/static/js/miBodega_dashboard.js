/*
async function cargarNotificaciones() {
    try {
        const respuesta = await fetch('/api/notificaciones');
        const notificaciones = await respuesta.json();

        const lista = document.querySelector('.cuadro ul');
        lista.innerHTML = ''; // limpiar lista

        notificaciones.forEach(n => {
            let colorClass = 'blue-notification';
            if (n.tipo === 'error') colorClass = 'red-notification';
            else if (n.tipo === 'advertencia') colorClass = 'yellow-notification';
            else if (n.tipo === 'info') colorClass = 'green-notification';

            lista.innerHTML += `
                <li>
                    <div class="notification-item ${colorClass}">
                        <span><i class="fa-regular fa-bell"></i></span>
                        <div>
                            <div class="notification-text">${n.mensaje}</div>
                            <div class="notification-time">${n.fechaCreacion.replace('T', ' ')}</div>
                        </div>
                    </div>
                </li>`;
        });
    } catch (error) {
        console.error('Error al cargar notificaciones:', error);
    }
}

// Cargar al iniciar y actualizar cada 10 segundos
cargarNotificaciones();
setInterval(cargarNotificaciones, 10000);
*/

async function cargarNotificaciones() {
    try {
        const respuesta = await fetch('/api/notificaciones');
        const notificaciones = await respuesta.json();

        const lista = document.querySelector('.cuadro ul');
        lista.innerHTML = ''; // limpiar lista

        notificaciones.forEach(n => {
            let colorClass = 'blue-notification';
            let iconClass = 'fa-solid fa-circle-info'; // valor por defecto

            if (n.tipo === 'error') {
                colorClass = 'red-notification';
                iconClass = 'fa-solid fa-triangle-exclamation';
            } else if (n.tipo === 'advertencia') {
                colorClass = 'yellow-notification';
                iconClass = 'fa-regular fa-bell';
            } else if (n.tipo === 'info') {
                colorClass = 'green-notification';
                iconClass = 'fa-regular fa-circle-check';
            }

            lista.innerHTML += `
                <li>
                    <div class="notification-item ${colorClass}">
                        <span><i class="${iconClass}"></i></span>
                        <div>
                            <div class="notification-text">${n.mensaje}</div>
                            <div class="notification-time">${n.fechaCreacion.replace('T', ' ')}</div>
                        </div>
                    </div>
                </li>`;
        });
    } catch (error) {
        console.error('Error al cargar notificaciones:', error);
    }
}

// Cargar al iniciar y actualizar cada 10 segundos
cargarNotificaciones();
setInterval(cargarNotificaciones, 10000);
