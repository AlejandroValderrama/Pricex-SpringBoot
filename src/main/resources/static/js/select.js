var ordenar = document.getElementById('ordenar');
var juegos = document.querySelector('#lista');
var consola = document.querySelector('#consola').value;

ordenar.addEventListener('change', getLista, false);


function getLista() {
   var selectedOption = this.options[ordenar.selectedIndex].value;
   var path = window.location.pathname + "/";

   if(path.search('O') != -1){
      var index = path.search('O');
      path = path.substring(0, index);
   }

   var peticion = "";

   if(selectedOption == "1") {
      peticion = "OrderBy=Name_Desc";
   }else if(selectedOption == "2") {
      peticion = "OrderBy=Name_Asc";
   }else if(selectedOption == "3"){
      peticion = "OrderBy=Price_Desc";
   }else if(selectedOption == "4") {
      peticion = "OrderBy=Price_Asc";
   }

   window.location = path + peticion;
   
};