<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp" %>
<%@ include file="../../headPortal.jsp"%>
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
	{{usuarios}}	
	<br>
	<a href="alumnos">Prueba</a>
	<br>
	<table class="table">
	<thead>
	<tr>
		<th>Clave</th>
		<th>Nombre</th>
	</tr>	
	</thead>
	<tbody>
	<tr v-for="pais in arrPaises" v-bind:key="pais">
		<td>{{pais.paisId}}</td>
		<td>{{pais.nombrePais}}</td>
	</tr>	
	</tbody>	
</div>
<script>	
	// data, methods, props, mounted, created, computed, watch, destroyed
    const vista = {
        data(){
            return{
                usuarios:[],
                posts:[],
                arrPaises:[]
            }
        },
        methods:{
            getUsuarios(){
                fetch('https://jsonplaceholder.typicode.com/users')
                .then(response => response.json())
                .then(data => {
                    this.usuarios = data;
                 })
            },
            getPosts(){
                fetch('https://jsonplaceholder.typicode.com/posts')
                .then(response => response.json())
                .then(data => {
                    this.posts = data;
                 })
            },
            getPaises(){
                fetch('alumnos')
                .then(response => response.json())
                .then(data => {
                    this.arrPaises = data;
                 })
            }
        },
        mounted(){
        	this.getUsuarios();   
        	this.getPosts();
        	this.getPaises();
        }        
    }   
    var mountedApp = Vue.createApp(vista).mount('#app')
</script>
</body>