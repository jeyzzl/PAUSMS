<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<head>
	<meta charset="iso-8859-1">	
	<script src="https://unpkg.com/vue@next"></script>		
</head>
<body>
<div class="container-fluid" id="app">
	<h1><spring:message code="catalogos.paises.Titulo"/></h1>	
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="editarPais"><spring:message code="aca.Anadir"/></a>&nbsp;&nbsp;
		<input type="text" class="form-control search-query" placeholder="Buscar..." id="buscar" style="width:170px;">
	</div>	
	<table id="table" class="table table-sm table-bordered">
	<thead>  
		<tr>
			<th width="17%"><spring:message code="aca.Operacion"/></th>
			<th width="9%"><spring:message code="aca.Numero"/></th>
			<th width="37%"><spring:message code="catalogos.paises.Paises"/></th>
			<th width="37%"><spring:message code="aca.Nacionalidad"/></th>
			<th width="37%"><spring:message code="catalogos.paises.Interamericana"/></th>			
			<th width="5%"><spring:message code="catalogos.paises.Estados"/></th>
		</tr>
	</thead>	
	<tbody>		
		<tr v-for="pais in arrPaises" v-bind:key="pais">
			<td style="text-align: left">				
				<a v-bind:href="'editarPais?PaisId='+pais.paisId"><i class="fas fa-edit"></i></a>				
				&nbsp;
				<i v-if="pais.totEstados==0" class="fas fa-trash-alt" v-on:click="borrarPais(pais.paisId)" title="<spring:message code="aca.Eliminar"/>" style="color:red"></i>
			</td>
			<td align="left">{{pais.paisId}}</td>
			<td><a v-bind:href="'estados?PaisId='+pais.paisId">{{pais.nombrePais}}</a></td>
			<td>{{pais.nacionalidad}}</td>
			<td>{{pais.interamerica}}</td>			
			<td class="text-center">
				<span v-if="pais.totEstados==0" class="badge bg-warning rounded-pill">{{pais.totEstados}}</span>
				<span v-else class="badge bg-dark rounded-pill">{{pais.totEstados}}</span>
			</td>
		</tr>		
	</tbody>	
	</table>	
	<ul>
		<li v-for="edo in arrEstados">{{edo.llave}}-{{edo.valor}}</li>
	</ul>	
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script type="text/javascript">
	/*jQuery('#buscar').focus().search({table:jQuery("#table")});*/
	$('#buscar').search();
	
    const vista = {    
        data(){
            return{
            	mensaje:'',                
                arrPaises:[],
            	num:0
            }
        },
        methods:{            
            getPaises(){
                fetch('getPaises')
                .then(response => response.json())
                .then(data => {
                    this.arrPaises = data;
                 })
            },            
            borrarPais(paisId){
            	if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
                	fetch('deletePais?PaisId='+paisId)                
                	.then(data => {
                    	mensaje = data;
                    	this.getPaises();
                 	})
            	}	                 
            }
        },
        mounted(){        	
        	this.getPaises();        	     	
        }        
    }       
    var mountedApp = Vue.createApp(vista).mount('#app')
</script>
</body>