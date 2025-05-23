<%@ page import= "java.util.List"%>
<%@ page import= "aca.vista.spring.Maestros"%>
<%@ page import= "aca.salida.spring.SalGrupo"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<script type="text/javascript">

	function Nuevo()	{		
		document.frmgrupos.GrupoId.value 		    = "0";
		document.frmgrupos.NombreGrupos.value 	    = " ";
		document.frmgrupos.Responsable.value 	    = " ";		
		document.frmgrupos.Correo.value 	   		= " ";
		document.frmgrupos.Usuariose.value 	    	= " ";		
		document.frmgrupos.Accion.value="1";
		document.frmgrupos.submit();
	}
			
	function Guardar(){
		
		var selectedValues = "";    
	    jQuery("#Usuario :selected").each(function(){
	    	selectedValues+= "-"+jQuery(this).val(); 
	    });
	    selectedValues+="-";
	    document.frmgrupos.Usuarios.value=selectedValues;
	    
		if(document.frmgrupos.GrupoId.value!="" && document.frmgrupos.NombreGrupos.value!="" && document.frmgrupos.Responsable.value!="" && document.frmgrupos.Correo.value!=""){			
			document.frmgrupos.submit();
		}else{
			alert("Complete el formulario ..! ");
		}
	}		
</script>
</head>
<%		
	String mensaje 				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	SalGrupo salGrupo 			= (SalGrupo) request.getAttribute("salGrupo");	
	List<Maestros> lisMaestros 	= (List<Maestros>)request.getAttribute("lisMaestros");
%>
<body>
<div class="container-fluid">
	<h2>Catálogo de grupos</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="grupos"><spring:message code="aca.Regresar"/></a>
	</div>
<%	if(!mensaje.equals("-")){%>
	<div class="alert alert-secondary"><%=mensaje%></div>
<%	} %>	 	
	<form action="grabar" method="post" name="frmgrupos" target="_self">	
	<input id="GrupoId" name="GrupoId" type="hidden" value="<%=salGrupo.getGrupoId()%>">
	<div class="row">
		<div class="col-5">			
			<label for="NombreGrupos"><spring:message code="aca.Nombre"/> Grupo:</label>			
			<input name="NombreGrupos" type="text" id="NombreGrupos" class="form-control" size="40" maxlength="40" value="<%=salGrupo.getGrupoNombre()%>">
			<br>
			<label for="Responsable">Responsable:</label>			
			<input name="Responsable" type="text" id="Responsable" class ="form-control" maxlength="40" value="<%=salGrupo.getResponsable()%>">
			<br>
			<label for="Correo"><spring:message code="aca.Correo"/>:</label>			
			<textarea name="Correo" id="Correo" class="form-control" cols="40" rows="3"><%=salGrupo.getCorreo()%></textarea>
			<br>
			<label for="CategoriaId">Usuarios:</label>
			<br>	
			<select name="Usuario" id="Usuario" class="chzn-select" style="width:300px;" multiple>
<%
			 	for(Maestros maestro: lisMaestros){
%>
				<option value="<%=maestro.getCodigoPersonal()%>" <%if(salGrupo.getUsuarios().contains("-"+maestro.getCodigoPersonal()+"-"))out.print("selected"); %>><%= maestro.getNombre()+" "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno() %></option>
<%
			 	}%>
			</select>
			<input type="hidden" name="Usuarios"/>
		</div>			
	</div>			
	<br>
	<div class="alert alert-info">
		<a class="btn btn-success" href="javascript:Nuevo()"><spring:message code='aca.Nuevo'/></a>&nbsp;
		<a class="btn btn-primary" href="javascript:Guardar()"><spring:message code="aca.Grabar"/></a> &nbsp;
	</div>		
</div>	
</body>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
	jQuery(".chzn-select").chosen(); 
</script>	
</html>