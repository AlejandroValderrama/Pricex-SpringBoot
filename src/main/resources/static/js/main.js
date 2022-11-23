/*--------------------------------------------------------------
# Back to top button
--------------------------------------------------------------*/
const select = (el, all = false) => {
    el = el.trim()
    if (all) {
        return [...document.querySelectorAll(el)]
    } else {
        return document.querySelector(el)
    }
}


/**
 * Easy on scroll event listener 
 */
const onscroll = (el, listener) => {
    el.addEventListener('scroll', listener)
}



let backtotop = select('.back-to-top')
if (backtotop) {
    const toggleBacktotop = () => {
        if (window.scrollY > 100) {
            backtotop.classList.add('active')
        } else {
            backtotop.classList.remove('active')
        }
    }
    window.addEventListener('load', toggleBacktotop)
    onscroll(document, toggleBacktotop)
}




/*--------------------------------------------------------------
# Search
--------------------------------------------------------------*/
const tag = document.querySelector('#tag');
const list = document.querySelector('#search');

tag.addEventListener('keypress', getSearch, false);
tag.addEventListener('blur', cleanSearch, false);


function cleanSearch(event) {
    var lista = list.getElementsByTagName('a');
    var click = false;

    for(var i=0; i<lista.length; i++){
        if(event.relatedTarget === lista[i])
            click = true;
    }

    if(!click)
        list.innerHTML = "";
}


function getSearch(event) {
    var path = window.location.pathname;
    var data = [];

    list.innerHTML = "";

    if (event.key === "Enter") {
        $.ajax({
            url: "/getSearch/" + tag.value.toLowerCase(),
            dataType: 'json',
            success: function(result) {
                data.push(result.nombre);
                data.push(result.consola);

                console.log(data);
                if(data[0].length == 0){
                    list.innerHTML += '<li class="list-group-item text-center"> Sin resultados disponibles </li>';
                } else {
                    for(var i = 0; i<data[0].length; i++) 
                        list.innerHTML += '<a href="/ficha/'+data[1][i]+'/'+data[0][i]+'" class="enlace"><li class="list-group-item">'+data[0][i] +' ('+ data[1][i]+')</li></a>';
                }
            }
        });
    }
}


























const filtrar = () => {
    //console.log(formulario.value);
    const texto = formulario.ariaValueMax.toLowerCase();

    for (let producto of productos) {
        let nombre = producto.nombre.toLowerCase();
        if (nombre.indexOf(texto) !== -1) {

        }
    }
}

//boton.addEventListener('click', filtrar);