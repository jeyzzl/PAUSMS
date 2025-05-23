<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.archivo.spring.ArchEntrega"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head>

</head>
<style>
        canvas {
        width: 500px;
        height: 500px;
        background-color: #fff;
        border: 2px solid black;

        }

    </style>
<body>
	
	
	 <form id="miForm">
            <canvas id="pizarra"></canvas>
            <br>
            <button id="boton" class="btn btn-primary">Limpiar</button>
            <a id="download">
     		<input type="button" class="btn btn-success" value="Descargar" onClick="descargar()" />
   	 		</a>
    
            </form>
  	
</body>
<script>
  //======================================================================
  // VARIABLES
  //======================================================================
  let miCanvas = document.querySelector("#pizarra");
  let lineas = [];
  let correccionX = 0;
  let correccionY = 0;
  let pintarLinea = false;
  // Marca el nuevo punto
  let nuevaPosicionX = 0;
  let nuevaPosicionY = 0;

  let posicion = miCanvas.getBoundingClientRect();
  correccionX = posicion.x;
  correccionY = posicion.y;

  miCanvas.width = 500;
  miCanvas.height =500;

  //======================================================================
  // FUNCIONES
  //======================================================================

  /**
   * Funcion que empieza a dibujar la linea
   */
  function empezarDibujo() {
    pintarLinea = true;
    lineas.push([]);
  }

  /**
   * Funcion que guarda la posicion de la nueva línea
   */
  function guardarLinea() {
    lineas[lineas.length - 1].push({
      x: nuevaPosicionX,
      y: nuevaPosicionY
    });
  }

  /**
   * Funcion dibuja la linea
   */
  function dibujarLinea(event) {
    event.preventDefault();
    if (pintarLinea) {
      let ctx = miCanvas.getContext("2d");
      // Estilos de linea
      ctx.lineJoin = ctx.lineCap = "round";
      ctx.lineWidth = 8;
      // Color de la linea
      ctx.strokeStyle = "black";
      // Marca el nuevo punto
      if (event.changedTouches == undefined) {
        // Versión ratón
        nuevaPosicionX = event.layerX;
        nuevaPosicionY = event.layerY;
      } else {
        // Versión touch, pantalla tactil
        nuevaPosicionX = event.changedTouches[0].pageX - correccionX;
        nuevaPosicionY = event.changedTouches[0].pageY - correccionY;
      }
      // Guarda la linea
      guardarLinea();
      // Redibuja todas las lineas guardadas
      ctx.beginPath();
      lineas.forEach(function (segmento) {
        ctx.moveTo(segmento[0].x, segmento[0].y);
        segmento.forEach(function (punto, index) {
          ctx.lineTo(punto.x, punto.y);
        });
      });
      ctx.stroke();
    }
  }

  /**
   * Funcion que deja de dibujar la linea
   */
  function pararDibujar() {
    pintarLinea = false;
    guardarLinea();
  }

  //======================================================================
  // EVENTOS
  //======================================================================

  // Eventos raton
  miCanvas.addEventListener("mousedown", empezarDibujo, false);
  miCanvas.addEventListener("mousemove", dibujarLinea, false);
  miCanvas.addEventListener("mouseup", pararDibujar, false);

  // Eventos pantallas táctiles
  miCanvas.addEventListener("touchstart", empezarDibujo, false);
  miCanvas.addEventListener("touchmove", dibujarLinea, false);

  document.getElementById("miForm").reset();

  function descargar() {
    var filename = prompt("Guardar como...", "Nombre del archivo");
    if (miCanvas.msToBlob) {
      //para internet explorer
      var blob = miCanvas.msToBlob();
      window.navigator.msSaveBlob(blob, filename + ".png"); // la extensión de preferencia pon jpg o png
    } else {
      link = document.getElementById("download");
      //Otros navegadores: Google chrome, Firefox etc...
      link.href = miCanvas.toDataURL("image/png"); // Extensión .png ("image/png") --- Extension .jpg ("image/jpeg")
      link.download = filename;
    }
  }
</script>
