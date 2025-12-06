 if (!sessionStorage.getItem("jwt")) { 
     window.location.replace("/mibodega/login"); 
 }

/*
 Opcional: validar token con un ping al backend
 else {
 fetch("/api/usuario/validar", {
 headers: { "Authorization": `Bearer ${token}` }
 }).then(resp => {
 if (!resp.ok) {
 // Token inválido o expirado → redirigir al login
 window.location.href = "/mibodega/login";
 }
 }).catch(() => {
 window.location.href = "/mibodega/login";
 });     
 }
 */


