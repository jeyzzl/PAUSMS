<%@ page import="java.util.List"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>	
<body>
<div id="app" class="container-fluid">
	<h1>Pruebas de Vue</h1>
	<div class="alert alert-info" :class="'d-flex align-items-center'">
		<a class="btn btn-primary" href="nivel">Regresar</a>&nbsp;
		<saludo></saludo>&nbsp;
		<input type="text" class="form-control" style="width:100px" v-model="nuevoNivel" @keyup.enter="agregarNivel"/>&nbsp;
		<button class="btn btn-primary" @click="agregarNivel">Agregar</button>
	</div>
	<ul>
		<li v-for="niv of niveles">
		<input type="number" style="width:100px" v-model.number="niv.cantidad"/>
		{{niv.nombre}}
		<span v-if="niv.cantidad===0"> - vacio</span>
		<button @click="niv.cantidad++" class="btn btn-primary" :class="'btn-sm'">+</button>
		</li>		
	</ul>
	<h5>Total:{{sumarNiveles}}</h5>
</div>
<!-- <script src="https://unpkg.com/vue@next"></script> -->
<script src="https://cdn.jsdelivr.net/npm/vue@2.6.14/dist/vue.js"></script>
<script src="saludo.js"></script>
<script src="nivel.js"></script>
<script>
	
</script>
</body>
</html>