const token = sessionStorage.getItem("jwt");
let productos = [];
let carrito = [];

// Buscar producto
document.getElementById("formBuscarProducto").addEventListener("submit", async (e) => {
    e.preventDefault();
    const nombre = document.getElementById("busqueda-producto").value;
    buscarProductos(nombre);
});

async function buscarProductos(nombre) {
    try {
        const response = await fetch("/api/registro/buscar?nombre=" + encodeURIComponent(nombre), {
            headers: {"Authorization": "Bearer " + token}
        });
        if (!response.ok)
            throw new Error("Error en búsqueda");
        productos = await response.json();
        renderResultados(productos);
    } catch (error) {
        alert(error.message);
    }
}

async function mostrarTodos() {
    try {
        const response = await fetch("/api/registro/mostrar-todos", {
            headers: {"Authorization": "Bearer " + token}
        });
        if (!response.ok) throw new Error("Error al mostrar todos");
        const productos = await response.json();
        renderResultados(productos);
    } catch (error) {
        alert(error.message);
    }
}
document.getElementById("btnMostrarTodos").addEventListener("click", mostrarTodos);


function renderResultados(productos) {
    const tbody = document.getElementById("tbody-resultados");
    tbody.innerHTML = "";
    
    if (productos.length === 0) {
        return;
    }
    
    productos.forEach(p => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td>${p.nombre}</td>
            <td>${p.stock}</td>
            <td>${p.precioUnitario}</td>
            <td>
                <button class="btn btn-success btnSeleccionar" data-id="${p.id}">
                    <i class="bi bi-plus-circle"></i> Seleccionar
                </button>
            </td>
        `;
        tbody.appendChild(fila);
    });
    
    tbody.querySelectorAll(".btnSeleccionar").forEach(btn => {
        btn.addEventListener("click", () => seleccionarProducto(btn.dataset.id));
    });
}

function seleccionarProducto(idProducto) {
    const producto = productos.find(p => p.id == idProducto);
    if (!producto) {
        return;
    }

    const cantidad = parseInt(prompt(`¿Cuántas unidades de ${producto.nombre}?`), 10);
    if (isNaN(cantidad) || cantidad <= 0) {
        return;
    }

    const existente = carrito.find(d => d.producto.id == idProducto);
    if (existente) {
        existente.cantidad += cantidad;
        existente.precioTotal = existente.precioUnitario * existente.cantidad;
    } else {
        carrito.push({
            producto: producto,
            cantidad: cantidad,
            precioUnitario: producto.precioUnitario,
            precioTotal: producto.precioUnitario * cantidad
        });
    }

    actualizarCarrito(carrito);
    document.getElementById("tbody-resultados").innerHTML = "";
}



function actualizarCarrito(detalles) {
    const tbody = document.getElementById("tbody-carrito");
    tbody.innerHTML = "";
    detalles.forEach(d => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td>${d.producto.nombre}</td>
            <td>${d.cantidad}</td>
            <td>${d.precioUnitario}</td>
            <td>${d.precioTotal}</td>
            <td>
                <button class="btn btn-sm btn-danger btnEliminar" data-id="${d.producto.id}">
                    <i class="bi bi-trash"></i> Eliminar
                </button>
            </td>
        `;
        tbody.appendChild(fila);
    });
    
    tbody.querySelectorAll(".btnEliminar").forEach(btn => {
        btn.addEventListener("click", () => eliminarProducto(btn.dataset.id));
    });
}

function eliminarProducto(idProducto) {
    carrito = carrito.filter(d => d.producto.id != idProducto);
    actualizarCarrito(carrito);
}


document.getElementById("formRegistroSimple").addEventListener("submit", async (e) => {
    e.preventDefault();
    try {
        const response = await fetch("/api/registro/simple", {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                estado: "pendiente",
                precioTotal: carrito.reduce((sum, d) => sum + d.precioTotal, 0),
                detalles: carrito.map(d => ({
                    cantidad: d.cantidad,
                    precioUnitario: d.precioUnitario,
                    precioTotal: d.precioTotal,
                    producto: { id: d.producto.id }
                }))
            })
        });
        if (!response.ok) throw new Error("Error al registrar venta");
        alert("Venta registrada con éxito");
        carrito = [];
        actualizarCarrito(carrito);
    } catch (error) {
        alert(error.message);
    }
});






/*
 const token = sessionStorage.getItem("jwt");
 
 // formulario de búsqueda
 document.getElementById("formBuscarProducto").addEventListener("submit", async (e) => {
 e.preventDefault();    
 const nombre = document.getElementById("busqueda-producto").value;
 
 try {
 const response = await fetch("/api/productos/buscar?nombre=" + encodeURIComponent(nombre), {
 method: "GET",
 headers: {
 "Authorization": "Bearer " + token
 }
 });
 
 if (!response.ok) {
 throw new Error("Error en la búsqueda: " + response.status);
 }
 
 const productos = await response.json();
 renderResultados(productos); // función que actualiza la tabla con JS
 
 } catch (error) {
 console.error(error);
 mostrarError("No se pudo realizar la búsqueda de productos");
 }
 });
 */
/*
 async function seleccionarProducto(idProducto) {      
 try {
 const response = await fetch("/api/ventas/seleccionar", {
 method: "POST",
 headers: {
 "Authorization": "Bearer " + token,
 "Content-Type": "application/json"
 },
 body: JSON.stringify({ idProducto })
 });
 
 if (!response.ok) {
 throw new Error("Error al seleccionar producto: " + response.status);
 }
 
 const resultado = await response.json();
 actualizarCarrito(resultado);
 
 } catch (error) {
 console.error(error);
 mostrarError("No se pudo seleccionar el producto");
 }
 }
 
 
 document.getElementById("formSeleccionarProducto").addEventListener("submit", (e) => {
 e.preventDefault();
 const idProducto = e.target.querySelector("input[name='idProducto']").value;
 seleccionarProducto(idProducto); // aquí usas la función
 });
 */
/* Seleccionar producto
 document.querySelectorAll(".btnSeleccionar").forEach(btn => {
 btn.addEventListener("click", async () => {
 const idProducto = btn.dataset.id;
 try {
 const response = await fetch("/api/ventas/seleccionar", {
 method: "POST",
 headers: {
 "Authorization": "Bearer " + token,
 "Content-Type": "application/json"
 },
 body: JSON.stringify({ idProducto })
 });
 
 if (!response.ok) throw new Error("Error al seleccionar producto");
 
 const resultado = await response.json();
 actualizarCarrito(resultado);
 
 } catch (error) {
 console.error(error);
 mostrarError("No se pudo seleccionar el producto: " + error.message);
 }
 });
 });
 */

/*
 
 // Ejemplo de función para refrescar la tabla de seleccionados
 function actualizarCarrito(detalles) {
 const tabla = document.getElementById("tabla-resultados");
 tabla.innerHTML = ""; // Limpias la tabla
 detalles.forEach(detalle => {
 const fila = document.createElement("tr");
 fila.innerHTML = `<!--
 <td>${detalle.producto.nombre}</td>
 <td>${detalle.cantidad}</td>
 <td>${detalle.precioUnitario}</td>
 <td>${detalle.precioTotal}</td>
 <td>
 <button class="btn btn-sm btn-danger" onclick="eliminarProducto(${detalle.producto.id})">
 <i class="bi bi-trash"></i> Eliminar
 </button>
 </td>-->
 `;
 tabla.appendChild(fila);
 });
 }
 
 */
/*
 function actualizarCarrito(detalles) {
 const tabla = document.getElementById("tabla-resultados");
 tabla.innerHTML = ""; // Limpias la tabla
 detalles.forEach(detalle => {
 const fila = document.createElement("tr");
 fila.innerHTML = `
 <td>${detalle.producto.nombre}</td>
 <td>${detalle.cantidad}</td>
 <td>${detalle.precioUnitario}</td>
 <td>${detalle.precioTotal}</td>
 <td>
 <button class="btn btn-sm btn-danger btnEliminar" data-id="${detalle.producto.id}">
 <i class="bi bi-trash"></i> Eliminar
 </button>
 </td>
 `;
 tabla.appendChild(fila);
 });
 
 // Reasignar listeners a los botones recién renderizados
 tabla.querySelectorAll(".btnEliminar").forEach(btn => {
 btn.addEventListener("click", async () => {
 const idProducto = btn.dataset.id;
 try {
 const response = await fetch("/api/ventas/eliminar-seleccion", {
 method: "DELETE",
 headers: {
 "Authorization": "Bearer " + token,
 "Content-Type": "application/json"
 },
 body: JSON.stringify({ idProducto })
 });
 
 if (!response.ok) throw new Error("Error al eliminar producto");
 
 const resultado = await response.json();
 actualizarCarrito(resultado);
 
 } catch (error) {
 console.error(error);
 mostrarError("No se pudo eliminar el producto: " + error.message);
 }
 });
 });
 }
 */

/*
 // Función reutilizable para eliminar producto
 async function eliminarProducto(idProducto) {
 
 try {
 const response = await fetch("/api/ventas/eliminar-seleccion", {
 method: "DELETE",
 headers: {
 "Authorization": "Bearer " + token,
 "Content-Type": "application/json"
 },
 body: JSON.stringify({ idProducto })
 });
 
 if (!response.ok) {
 throw new Error("Error al eliminar producto: " + response.status);
 }
 
 const resultado = await response.json();
 actualizarCarrito(resultado); // refresca la tabla con el estado actualizado
 
 } catch (error) {
 console.error(error);
 mostrarError("No se pudo eliminar el producto: " + error.message);
 }
 }
 
 
 // Listener del formulario
 document.getElementById("formEliminarSeleccion").addEventListener("submit", (e) => {
 e.preventDefault();
 const idProducto = e.target.querySelector("input[name='idProducto']").value;
 eliminarProducto(idProducto);
 });
 */
/* Eliminar producto
 document.querySelectorAll(".btnEliminar").forEach(btn => {
 btn.addEventListener("click", async () => {
 const idProducto = btn.dataset.id;
 try {
 const response = await fetch("/api/ventas/eliminar-seleccion", {
 method: "DELETE",
 headers: {
 "Authorization": "Bearer " + token,
 "Content-Type": "application/json"
 },
 body: JSON.stringify({ idProducto })
 });
 
 if (!response.ok) throw new Error("Error al eliminar producto");
 
 const resultado = await response.json();
 actualizarCarrito(resultado);
 
 } catch (error) {
 console.error(error);
 mostrarError("No se pudo eliminar el producto: " + error.message);
 }
 });
 });
 */

