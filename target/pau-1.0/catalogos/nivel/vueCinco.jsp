<%@ page import="java.util.List"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<script src="https://unpkg.com/vue@next"></script>
<body>
<div id="app" class="container-fluid">
	<h1>Vue 3 c)</h1>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="nivel">Regresar</a>&nbsp;		
	</div>
	<h6 v-if="(numeroUno>numeroDos)">{{numeroUno}} es mayor que {{numeroDos}}</h6>
	<h6 v-else-if="(numeroUno==numeroDos)">Son iguales</h6>
	<h6 v-else>{{numeroUno}} es menor que {{numeroDos}}</h6>
	<span class="btn btn-primary" @click="(contador++)">+</span> {{contador}}
	<span class="btn btn-success" @mouseover="estado=!estado">Show</span>
	<span v-if="estado">Mostrar</span>
	<span v-if="!estado">Ocultar</span>
	<select name="evento" @change="cambiarImagen($event)">
		<option value="../../imagenes/alta.png">Alta</option>
		<option value="../../imagenes/baja.png">Baja</option>
	</select>
	<img v-bind:src="imagen">&nbsp;
	<input type="text" v-bind:disabled="estado"/>&nbsp;
	<input type="checkbox" v-bind:checked="estado"/>&nbsp;
	<select v-model="idFruta">
		<option v-for="fruta in frutas" v-bind:value="fruta.id">{{fruta.name}}</option>
	</select>
	<div>
	<a href="" class="btn btn-info ml-3" v-bind:class="['btn-sm','text-muted']">Clases</a>
	<span v-bind:style="{'font-size':'18px', color:'blue', }">Hola Mundo</span>&nbsp;
	<input name="numUno" type="text" style="width:50px" v-model="numUno"> + 
	<input name="numDos" type="text" style="width:50px" v-model="numDos">&nbsp;
	{{resultado}}
	</div>
	<table class="table table-bordered">
	<thead>
	<tr>
		<th>Id</th>
		<th>Usuario</th>
		<th>Nombre</th>
		<th>Correo</th>
	</tr>
	</thead>
	<tbody>
	<tr v-for="usuario in usuarios_pares">
		<td>{{usuario.id}}</td>
		<td>{{usuario.name}}</td>
		<td>{{usuario.username}}</td>
		<td>{{usuario.email}}</td>
	</tr>
	</tbody>
	</table>
</div>
<script>
	const holaMundo = {
		data(){
			return{
				usuarios:[],
				usuarios_pares:[],
				posts:[],
				numeroUno:4,
				numeroDos:4,
				contador:0,
				estado:true,
				imagen:'',
				frutas:[
					{id:1,name:'Manzana'},{id:2,name:'Pera'}
				],
				idFruta:2,
				numUno:0,
				numDos:0
			}
		},
		computed:{
			resultado:function(){
				return parseFloat(this.numUno)+parseFloat(this.numDos);
			}
		},
		methods:{
			getUsuarios(){
				fetch('https://jsonplaceholder.typicode.com/users').
				then(response => response.json()).
				then(data => {
					this.usuarios = data;
					this.usuarios_pares = data.filter(x=> (x.id%2==0));
				})
			},
			getPosts(){
				fetch('https://jsonplaceholder.typicode.com/posts').
				then(response => response.json()).
				then(data => {
					this.posts = data;
				})
			},
			cambiarImagen(event){
				this.imagen = event.target.value;@RequestMapping("/catalogos/nivel/vueCinco")
				public String catalogoNivelVueCinco(HttpServletRequest request, Model model){
					return "catalogos/nivel/vueCinco";
				}
			}
		},
		mounted(){
			this.getUsuarios();
			this.getPosts();
		}
	}
	var mountedApp = Vue.createApp(holaMundo).mount('#app')
</script>
</body>
</html>