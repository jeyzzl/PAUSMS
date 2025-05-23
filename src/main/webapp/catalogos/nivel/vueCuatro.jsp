<%@ page import="java.util.List"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<script src="https://unpkg.com/vue@next"></script>
<body>
<div id="app" class="container-fluid">
	<h1>Vue 3 b)</h1>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="nivel">Regresar</a>&nbsp;		
	</div>
	<label>Escribe tu nombre:</label>
	<input type="text" v-model="nombre">
	<hr>
	Hola {{nombre}}
	<hr>
	<select v-model="fruta">
		<option value="1">Manzana</option>
		<option value="2">Uva</option>
		<option value="3">Pera</option>
	</select>
	Fruta:{{fruta}}
	<hr>
	<input type="checkbox" v-model="activo"> {{activo}}
	<hr>
	<ul>
		<li v-for="fru in frutas" v-bind:key="fru">{{fru}}</li>
	</ul>
	<hr>
	<ul>
		<li v-for="(fruta, row) in frutasKey" v-bind:key="fruta">{{row}} - {{fruta.id}} - {{fruta.nombre}}
			<ul>
				<li v-for="pais in fruta.origen">{{pais.paisId}} - {{pais.paisNombre}}</li>
			</ul>
		</li>
	</ul>
	<hr>
	<select v-model="frutaId">
		<option v-for="(f,index) in frutasKey" v-bind:value="f.id">{{index}} - {{f.nombre}}</option>
	</select>
	<hr>
	<ul>
		<li v-for="numero in 100" v-bind:key="numero">{{numero / 2}}</li>
	</ul>
</div>
<script>
	const holaMundo = {
		data(){
			return{
				frutaId:2,
				nombre:'Jairo',
				fruta:'Uva',
				activo:false,
				frutas:['Manzana','Pera','Uva'],
				frutasKey:[
					{id:1,nombre:'Manzana',origen:[{paisId:1,paisNombre:'México'},{paisId:2,paisNombre:'USA'}]},
					{id:2,nombre:'Pera',origen:[{paisId:3,paisNombre:'Guatemala'},{paisId:4,paisNombre:'Honduras'}]},
					{id:3,nombre:'Uva',origen:[{paisId:5,paisNombre:'El Salvador'},{paisId:6,paisNombre:'Nicaragua'}]}
				]
			}
		},
		methods:{
			
		}
	}
	var mountedApp = Vue.createApp(holaMundo).mount('#app')
</script>
</body>
</html>