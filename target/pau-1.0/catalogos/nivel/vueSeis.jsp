<%@ page import="java.util.List"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<script src="https://unpkg.com/vue@next"></script>
<body>
<div id="app" class="container-fluid">
	<h1>Vue 3 d)</h1>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="nivel">Regresar</a>&nbsp;		
	</div>
	<Hola></Hola>
</div>
<script>
	var mountedApp = Vue.createApp(holaMundo).mount('#app');
	
	const holaMundo = {
		data(){
			return{
				
			}
		},
		methods:{
			
		}
	},
	
	mountedApp.component('Hola',{
		template:
		`
		<div>
			<h2>Hola {{nombre}}</h2>
		</div>
		`,
		data(){
			return{
				nombre:'Jose'
			}
		}
	});
	
	
</script>
</body>
</html>