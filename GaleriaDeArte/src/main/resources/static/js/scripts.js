

/**
 * Función para 
 * @function
 */
function toggleMenu() {
    var menu = document.getElementById("listaIdiomas");
    menu.classList.toggle("mostrar");
}

// Cerrar menú si hacemos click fuera
window.onclick = function(event) {
    if (!event.target.matches('.boton-idioma')) {
        var dropdowns = document.getElementsByClassName("menu-desplegable");
        for (var i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('mostrar')) {
                openDropdown.classList.remove('mostrar');
            }
        }
    }
}



/**
 *  Función para la Confirmación de eliminar cuadro
 * @function
 */
function eliminarCuadro(url) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: "No podrás revertir esto",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            // Si confirma, redirige a la URL de eliminación
            window.location.href = url;
        }
    })
}

// Muestra las opciones según se filtra por época
// Muestra el input tipo text según se filtra por autor
function mostrarFiltro(tipo) {
    const divAutor = document.getElementById('divAutor');
    const divEpoca = document.getElementById('divEpoca');

    const claseOculto = 'buscador__grupo--oculto';

    if (tipo === 'autor') {
        divAutor.classList.remove(claseOculto);
        divEpoca.classList.add(claseOculto);
        
        // limpia el select select
        const selectEpoca = divEpoca.querySelector('select');
        if(selectEpoca) selectEpoca.selectedIndex = 0;

    } else {
        divAutor.classList.add(claseOculto);
        divEpoca.classList.remove(claseOculto);
        
        // limpia el input
        const inputAutor = divAutor.querySelector('input');
        if(inputAutor) inputAutor.value = '';
    }
}