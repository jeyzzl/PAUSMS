<%@ page import="java.util.List"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/vue@2.6.14/dist/vue.js"></script>
<script src="https://unpkg.com/vuex@3.6.2/dist/vuex.js"></script>	
<body>

<div id="app" class="container-fluid">
	<h1>Pruebas de Vue 2</h1>
	<div class="alert alert-info" :class="'d-flex align-items-center'">
		<a class="btn btn-primary" href="nivel">Regresar</a>&nbsp;		
	</div>
	<padre></padre>
</div>
<script>
	Vue.component('padre',{
		template:
		`
		<div>
			<h1>numero {{numero}}</h1>
			<hijo></hijo>
		</div>
		`,
		computed:{
			...Vuex.mapState(['numero'])
		}
	});
	
	Vue.component('hijo',{
		template:
		`
		<div>
			<button @click="aumentar">+</button>
			<button @click="disminuir(2)">-</button>
			<button @click="obtenerCursos">Obtener Cursos</button>
			<h1>numero {{numero}}</h1>
			<ul v-for="item of cursos">
				<li>{{item.nombre}}</li>
			<ul>	
		</div>
		`,
		computed:{
			...Vuex.mapState(['numero','cursos'])
		},
		methods:{
			...Vuex.mapMutations(['aumentar','disminuir']),
			...Vuex.mapActions(['obtenerCursos'])
		}
	});
	
	const store = new Vuex.Store({
		state:{
			numero:10,
			cursos:[]
		},
		mutations:{
			aumentar(state){
				state.numero++
			},
			disminuir(state,n){
				state.numero-=n
			},
			llenarCursos(state, cursosAccion){
				state.cursos = cursosAccion
			}
		},
		actions:{
			obtenerCursos: async function({commit}){
				const data = await fetch('cursos.json');
				const cursos = await data.json();
				commit('llenarCursos',cursos);
			}
		}
	});
	new Vue({
		el:'#app',
		store:store
	});	
</script>
</body>
</html>