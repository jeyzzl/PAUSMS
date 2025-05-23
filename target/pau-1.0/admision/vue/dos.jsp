<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head>
	<meta charset="UTF-8">
    <title>CDN en Vue 3 (2)</title>
    <script src="https://unpkg.com/vue@next"></script>
</head>
<body>
<div class="container-fluid" id="app">
	<a href="uno" class="btn btn-outline-primary">1</a>	
	<a href="dos" class="btn btn-outline-primary">2</a>
	<a href="tres" class="btn btn-outline-primary">3</a>
	<a href="cuatro" class="btn btn-outline-primary">4</a>
	<a href="cinco" class="btn btn-outline-primary">5</a>
	<a href="seis" class="btn btn-outline-primary">6</a>
	<a href="siete" class="btn btn-outline-primary">7</a>
	<a href="ocho" class="btn btn-outline-primary">8</a>
	<a href="nueve" class="btn btn-outline-primary">9</a>
	<a href="diez" class="btn btn-outline-primary">10</a>	
	<br>
	<label v-bind:title="titulo">Hola mundo</label>	
	<hr>
	<button class="btn btn-primary" v-on:click="mostrarImagen('carro1.jpg')">Carro 1</button>&nbsp;
	<button class="btn btn-primary" v-on:click="mostrarImagen('carro2.jpg')">Carro 2</button>
	<hr>
	<img v-bind:src="imagen">
</div>
<script>	
	// data, methods, props, mounted, created, computed, watch, destroyed
    const vista = {
        data(){
            return{
                titulo:'Este es un titulo dinámico',
                imagen:'carro1.jpg'
            }
        },
        methods:{
            mostrarImagen(imagen){
                this.imagen = imagen;
            }
        }    
    }   
    var mountedApp = Vue.createApp(vista).mount('#app')
</script>
</body>