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
	<ul>
		<li v-for="fruta in frutas" v-bind:key="fruta" style="color:black">{{fruta}}</li>
	</ul>
	<hr>
	<ul>
		<li v-for="(fruta,index) in frutasConId" v-bind:key="fruta" style="color:black">
			{{index}} - {{fruta.id}} - {{fruta.nombre}}
			<ul>
				<li v-for="pais in fruta.origen">{{pais.paisId}}-{{pais.paisNombre}}</li>
			</ul>
		</li>
	</ul>
	<hr>
	<select v-model="frutaId">
		<option v-for="fruta in frutasConId" v-bind:value="fruta.id">{{fruta.id}} - {{fruta.nombre}}</option>
	</ul>
</div>
<script>	
	// data, methods, props, mounted, created, computed, watch, destroyed
    const vista = {
        data(){
            return{
                frutas:['Manzana','Pera','Una'],
                frutasConId:[
                    {id:1,nombre:'Manzana',origen:[{paisId:1,paisNombre:'Mexico'},{paisId:2,paisNombre:'USA'}]},
                    {id:2,nombre:'Pera',origen:[{paisId:3,paisNombre:'Panama'},{paisId:4,paisNombre:'Guatemala'}]},
                    {id:3,nombre:'Uva',origen:null},
                ],
            	frutaId:2
            	// frutasConId.push({id:4,nombre:'Platano'}), frutasConId.splice(0,1)
            }
        }        
    }   
    var mountedApp = Vue.createApp(vista).mount('#app')
</script>
</body>