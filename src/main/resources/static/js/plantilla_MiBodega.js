  function actualizarFechaHora() {
    const ahora = new Date();

    // Opciones para la fecha
    const opcionesFecha = { day: '2-digit', month: '2-digit', year: 'numeric' };
    const fechaFormateada = ahora.toLocaleDateString('es-PE', opcionesFecha);

    // Opciones para la hora
    const opcionesHora = { hour: '2-digit', minute: '2-digit', second: '2-digit' };
    const horaFormateada = ahora.toLocaleTimeString('es-PE', opcionesHora);

    document.getElementById("fecha").textContent = "FECHA: " + fechaFormateada;
    document.getElementById("hora").textContent = "HORA: " + horaFormateada;
  }

  // Llamar la función cada 1 segundo
  setInterval(actualizarFechaHora, 1000);

  // Llamar una vez al cargar la página
  actualizarFechaHora();