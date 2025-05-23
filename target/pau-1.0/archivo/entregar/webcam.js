'use strict'; // Para hacer que el código sea mas seguro.

// Definimos las constantes que vamos a utilizar
const videoFrame = document.getElementById('video_frame');
const canvasFrame = document.getElementById('canvas_frame');
const snapFrame = document.getElementById("snap_frame");
const errorMsgElement = document.querySelector('span#errorMsg');

// Definimos tamaño del video y si queremos audio o no
const constraints = {
  audio: true,
  video: {
	width: 720, height: 405
  }
};

// Comprobamos acceso a la Webcam
async function init() {
  try {
    const stream = await navigator.mediaDevices.getUserMedia(constraints);
    handleSuccess(stream);
  } catch (e) {
    errorMsgElement.innerHTML = `navigator.getUserMedia error:${e.toString()}`;
  }
}

// En caso de que el acceso sea correcto, cargamos la webcam
async function handleSuccess(stream) {
  window.stream = stream;
  videoFrame.srcObject = stream;
}

// Iniciamos JS
init();

// Hacemos captura de pantalla al hacer click
var context = canvasFrame.getContext('2d');
snapFrame.addEventListener("click", function() {
        context.drawImage(videoFrame, 0, 0, 320, 140);
});