<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.alumno.spring.AlumPlan"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>
<%@ page import= "aca.financiero.spring.ContCcosto"%>
<%@ page import= "aca.bec.spring.BecPuestoAlumno"%>
<%@ page import= "aca.bec.spring.BecPlazas"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	// Codigo del usuario en sesión 
	String codigo			= (String)request.getAttribute("codigoPersonal");	
	String idEjercicio		= request.getParameter("idEjercicio");
	String periodoId 		= (String) session.getAttribute("periodoBecas");
	String idCcosto   		= request.getParameter("idCcosto");
	
	String categoriaId		= request.getParameter("categoriaId");
	String codigoAlumno		= request.getParameter("codigoAlumno")==null?"0":request.getParameter("codigoAlumno");
	String puestoId 		= request.getParameter("puestoId")==null?"0":request.getParameter("puestoId");
	String descripcion 		= request.getParameter("descripcion")==null?"0":request.getParameter("descripcion");	
	String becas 			= request.getParameter("becas")==null?"":request.getParameter("becas");
	String msj 				= request.getParameter("mensaje")==null?"-":request.getParameter("mensaje");
	boolean admin       	= (boolean)request.getAttribute("admin");	
	boolean existePuesto  	= (boolean)request.getAttribute("existePuesto");
		
	if( idEjercicio == null || idCcosto == null || categoriaId == null ){
		response.sendRedirect("puesto");
	}
	
	int cantidadT 			= (int)request.getAttribute("cantidadT");
	int cantidadI 			= (int)request.getAttribute("cantidadI");
	int cantidadB 			= (int)request.getAttribute("cantidadB");
	int cantidadP 			= (int)request.getAttribute("cantidadP");
	int cantidadM 			= (int)request.getAttribute("cantidadM");
	
	String tipoAcuerdo 		= (String)request.getAttribute("tipoAcuerdo");	
	String periodoNombre	= (String)request.getAttribute("periodoNombre");
	
	BecPuestoAlumno becPuestoAlumno 		= (BecPuestoAlumno)request.getAttribute("becPuestoAlumno");	
	BecPlazas becPlazas 					= (BecPlazas)request.getAttribute("becPlazas");	
	
	List<AlumPlan> lisPlanes 				= (List<AlumPlan>)request.getAttribute("lisPlanes");			
	HashMap<String,ContCcosto> mapaCostos 	= (HashMap<String,ContCcosto>)request.getAttribute("mapaCostos");
	HashMap<String,MapaPlan> mapaPlanes 	= (HashMap<String,MapaPlan>)request.getAttribute("mapaPlanes");	
%>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<style>
	body{
		background:white;
	}
</style>

<div class="container-fluid">	
	<h2>Añadir Puesto<small class="text-muted fs-5">( Ejercicio:<%=idEjercicio %>&nbsp; Periodo:<%=periodoNombre%> )</small></h2>	
	<div class="alert alert-info">
		<a href="puestoAlumno?idEjercicio=<%=idEjercicio%>&idCcosto=<%=idCcosto%>&categoriaId=<%=categoriaId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>		
	</div>
<%	if (!msj.equals("-")){%>		
	<%=msj%>		
<%	}%>	
	<form  method="post" action="grabarPuestoAlumno" name="forma" id="forma">	
		<input type="hidden" name="codigoPersonalOld" class="form-control" style="width:880px;" value="<%=becPuestoAlumno.getCodigoPersonal()%>" />	
		<input type="hidden" name="becas" class="form-control" style="width:880px;" value="<%=becas%>" />		
		<input type="hidden" name="puestoId" class="form-control" style="width:880px;" value="<%=puestoId%>" />	
	    <input type="hidden" name="idEjercicio" class="form-control" style="width:880px;" value="<%=idEjercicio%>" />
	    <input type="hidden" name="idCcosto" class="form-control" style="width:880px;" value="<%=idCcosto%>" />
	    <input type="hidden" name="categoriaId" class="form-control" style="width:880px;" value="<%=categoriaId%>" />
	
	    <fieldset>	
        	<div class="control-group ">
                <label for="nombre"><b>Matrícula</b></label>
                <input autocomplete="off" id="alumno" name="alumno" type="text" class="form-control" style="width:880px;" value="<%=becPuestoAlumno.getCodigoPersonal()==null?"":becPuestoAlumno.getCodigoPersonal() %>" maxlength="7" <%if(becas.equals("S")){out.print("readonly");} %>/>
                &nbsp;
                <img src="../../imagenes/loading.gif" alt="" style="display:none;" class="loading-bar" />
                <span class="result"></span>
            </div>            
        	<div class="control-group">
                <label for="Plan"><b>Plan</b></label>
		        <select style="width:880px;" class="selectPlan form-select" id="PlanId" name="PlanId" onchange="javascript:buscarFechas()">
		     <%	     
		     	for(AlumPlan plan : lisPlanes){
		    	 	String principal = plan.getPrincipal().equals("1")?"P":"S";
		    	 	String planNombre = "-";
		    	 	if (mapaPlanes.containsKey(plan.getPlanId())){
		    	 		planNombre = mapaPlanes.get(plan.getPlanId()).getNombrePlan();
		    	 	}
		     %>
		        	<option value="<%=plan.getPlanId()%>" <%if(plan.getPlanId().equals(becPuestoAlumno.getPlanId()))out.print("selected"); %>>[<%=principal%>-<%=plan.getFInicio()%>] <%=plan.getPlanId()%>-<%=planNombre%></option>
		     <%	} %>
		        </select>
	        </div>	            
	        <div class="control-group ">
	        	<label for="username"><b>Fecha Inicio Convenio</b></label>	                
	            <input id="fechaIni" data-date-format="dd/mm/yyyy" name="fechaIni" class="form-control" style="width:880px;" type="text" value="<%=becPuestoAlumno.getFechaIni()==null?"":becPuestoAlumno.getFechaIni() %>" maxlength="100" <%=admin!=true?"readonly":""%>/>               
	        </div>	        
	        <div class="control-group ">
	        	<label for="apPaterno"><b>Fecha Final Convenio</b></label>
	            <input id="fechaFin" data-date-format="dd/mm/yyyy" name="fechaFin" class="form-control" style="width:880px;" type="text" value="<%=becPuestoAlumno.getFechaFin()==null?"":becPuestoAlumno.getFechaFin() %>" maxlength="30"/>
			</div>	        
<%
	        String tipo = becPuestoAlumno.getTipo()==null?"":becPuestoAlumno.getTipo();
%>	        
            <div class="control-group ">
                <label for="Tipo"><b>Tipo</b></label>
               <select <%if(existePuesto){out.print("disabled");}else{ %> name="tipo" class="form-select" style="width:880px;" id="tipo" <%}%>>
	               	<option value="B" <%if(tipo.equals("B"))out.print("selected"); %>>Basica</option>
	               	<option value="I" <%if(tipo.equals("I"))out.print("selected"); %>>Institucional</option>
	               	<option value="P" <%if(tipo.equals("P"))out.print("selected"); %>>Preinstitucional</option>
	               	<option value="T" <%if(tipo.equals("T"))out.print("selected"); %>>Temporal</option>
	               	<option value="M" <%if(tipo.equals("M"))out.print("selected"); %>>Postgrado</option>
               </select>
               <%if(!tipo.equals("")){%>
               <input type="hidden" name="tipo" id="tipo" class="form-control" style="width:880px;" value="<%=tipo%>" />
               <%} %>
            </div>
                      
            <div class="control-group">	     
            	<label for="descripcion"><b>Descripción</b></label>
                <input type="text" name="descripcion" id="descripcion" class="form-control" style="width:880px;" value="<%=becPuestoAlumno.getDescripcion()==null?"":becPuestoAlumno.getDescripcion()%>" maxlength="100" size="100">
            </div>	        
	    </fieldset>	
	    <p class="alert alert-info" style="margin-top: 10px;">
	    <%
	    	String txt = "Crear";	    	
	    	if( existePuesto ){
	    		txt = "Actualizar";
	    	}
	    %>	    
	    <a onclick="grabar();" class="btn btn-primary btn-large" ><i class="fas fa-check"></i>  <%=txt %></a>	        
	    <a class="btn btn-light" href="puestoAlumno?idEjercicio=<%=idEjercicio%>&idCcosto=<%=idCcosto%>&categoriaId=<%=categoriaId%>"><i class="fas fa-trash-alt"></i> Cancelar</a>
	    </p>
	</form>

	<script>
	
	var fIni = jQuery('#fechaIni');
	var fFin = jQuery('#fechaFin');	
	
	function buscarFechas(){
		var codigoAlumno = jQuery('#alumno').val();
		var planId = jQuery('#PlanId').val();
		//alert("Plan:"+planId+":"+codigoAlumno);
		jQuery.get("getFechasConvenio?Matricula="+codigoAlumno+"&PlanId="+planId, function(r){
			var fechas = jQuery.trim(r);
			fIni.val(fechas.split('&')[0]);
			fFin.val(fechas.split('&')[1]);
		});
	}
	
	var loadingBar 	= jQuery('.loading-bar');
	var result 		= jQuery('.result');
	var planes 		= jQuery('.selectPlan');
	jQuery("#alumno").keyup(function(){
		$this = jQuery(this);
		var value 	= $this.val();
		var length 	= value.length;
		if(length == 7){
			loadingBar.show();
			jQuery.get("getNombreAlumno?matricula="+value, function(r){
				loadingBar.hide();
				var datos = jQuery.trim(r);
				var nombre = datos.split('&')[0];
				var select = datos.split('&')[1];
				
				planes.html(select);
				result.html(nombre);
				if(nombre.indexOf("Numero de Matrícula No Válido") != -1){
					//matricula no valida
				}else{
					buscarFechas();
				}
			});
		}else{
			loadingBar.hide();
			result.html("");
		}
	});
	
	function grabar(){
		if(document.forma.fechaIni.value!="" && document.forma.fechaFin.value!="" && document.forma.alumno.value!=""){
			var fIni = document.forma.fechaIni.value;
			var fFin = document.forma.fechaFin.value;
			
			if(fIni != "" && fFin != ""){
				var numfIni = parseInt(fIni.split("/").reverse().join(''));
				var numfFin = parseInt(fFin.split("/").reverse().join(''));
				
				if(numfFin<numfIni){
					alert("La Fecha Fin no puede ser menor que la fecha Inicio");
				}else{
					document.forma.submit();
				}
				
			}else{
				document.forma.submit();	
			}	
		}else{
			alert("Todos los campos son obligatorios");
		}
	}
	
	jQuery('#fechaFin').datepicker();
	
	</script>	

</div>