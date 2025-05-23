<%@ page import="java.util.List"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<script src="https://unpkg.com/vue@next"></script>
<body>
<div id="app" class="container-fluid">
	<h1>Vue 3 a)</h1>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="nivel">Regresar</a>&nbsp;		
	</div>
	Hola {{nombre}} estamos en el año {{year}}
	<hr>
	Contador {{contador}}
	<button type="button" v-on:click="stopInterval">{{btnMessage}}</button>
	<hr>
	<label v-bind:title="titulo">Hola Mundo</label>
	<hr>
	<button v-on:click="mostrarImagen('../../imagenes/abajo.png')">Abajo</button>
	<button v-on:click="mostrarImagen('../../imagenes/arriba.png')">Arriba</button>
	<hr>
	<img v-bind:src="imagen">
</div>
<script>
	const holaMundo = {
		data(){
			return{
				nombre:'Jairo',
				year:2021,
				contador:0,
				interval:null,
				isRunning:false,
				btnMessage:'Detener',
				titulo:'Este es un titulo dinámico',
				imagen:''
			}
		},
		mounted(){
			this.interval = setInterval (() =>{
				this.contador++;
			},1000);
			this.isRunning = true;
		},
		methods:{
			stopInterval(){
				if (this.isRunning){
					clearInterval(this.interval);
					this.isRunning = false;
					this.btnMessage = 'Continuar';
				}else{
					this.interval = setInterval (() =>{
						this.contador++;
					},1000);
					this.isRunning = true;
					this.btnMessage = 'Detener';
				}	
			},
			mostrarImagen(img){
				this.imagen = img;
			}
		}
	}
	var mountedApp = Vue.createApp(holaMundo).mount('#app')
</script>
</body>
</html>