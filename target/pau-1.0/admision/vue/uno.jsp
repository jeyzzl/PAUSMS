<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head>
	<meta charset="UTF-8">
    <title>CDN en Vue 3</title>
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
	<h3>Hola {{nombre}} estamos en {{meses[4]}} {{year}} Contador:{{contador}}</h3>
	<a href="#" class="btn btn-primary" v-on:click="stopInterval">{{btnNombre}}</a>
</div>
<script>	
	// data, methods, props, mounted, created, computed, watch, destroyed
    const vista = {
        data(){
            return{
            	nombre:'José',
            	meses:['Ene','Feb','Mar','Abr','May','Jun'],
            	year:2022,
            	contador:0,
            	interval:null,
            	isRunning:false,
            	btnNombre:'Detener'
            }
        },
        mounted(){
            this.interval = setInterval( () => {
                this.contador++;
            },1000);
            this.isRunning = true;
        },
        methods:{
            stopInterval(){
                if (this.isRunning){
                	clearInterval(this.interval);
                	this.isRunning = false;
                	this.btnNombre = 'Reiniciar';
                }else{
                	this.interval = setInterval( () => {
                        this.contador++;
                    },1000);
                    this.isRunning = true;
                    this.btnNombre = 'Detener';
                }	
            }
        }
    }   
    var mountedApp = Vue.createApp(vista).mount('#app')
</script>
</body>