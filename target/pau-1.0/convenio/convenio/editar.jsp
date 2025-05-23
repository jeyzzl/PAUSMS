<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.convenio.spring.ConConvenio"%>
<%@page import="aca.convenio.spring.ConTipo"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>		
</head>	
<% 	
	ConConvenio convenio 		= (ConConvenio)request.getAttribute("convenio");
	String mensaje				=  request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");		 

	List<ConTipo> listaTipos 	= (List<ConTipo>)request.getAttribute("listaTipos");
%>
<body>
<div class="container-fluid">
	<h2>Nuevo / Editar convenio<small class="text-muted fs-4">( <%=convenio.getId()%> )</small></h2>
	<div class="alert alert-info">
		<a href="listado" class="btn btn-primary">Regresar</a>	
	</div>	
<%	if (!mensaje.equals("-")){ %>
	<div class="alert alert-info"><%=mensaje%></div>
<% 	}%>	
	<form name="frmConvenio" action="grabar" method="post">
	<input name="Id" type="hidden" value="<%=convenio.getId()%>">
	<div class="row container-fluid">
	<div class="col">		
			<fieldset>
				<label ><b>Nombre convenio</b></label>
				<input type="text" name="Nombre" class="form-control" value="<%=convenio.getNombre()%>" size="45" required/>
			</fieldset>			
			<br/>			
			<fieldset>
				<label ><b>Fecha firma</b></label>
				<input type="text" name="FechaFirma" class="form-control" data-date-format="yyyy/mm/dd" id="FechaFirma" value="<%=convenio.getFechaFirma()%>" maxlength="10" size="12" >	
			</fieldset>
			<br/>
			<fieldset>
				<label ><b>Fecha vigencia</b></label>
				<input type="text" name="FechaVigencia" class="form-control" data-date-format="yyyy/mm/dd" id="FechaVigencia" value="<%=convenio.getFechaVigencia()%>" maxlength="10" size="12" >
			</fieldset>
			<br/>
			<fieldset>
				<label ><b>Estado</b></label>
				<select name="Estado" class="form-select">
					<option value="1" <%=convenio.getEstado().equals("1") ? "selected": ""%>>Vigente</option>
					<option value="2" <%=convenio.getEstado().equals("2") ? "selected": ""%>>Indefinido</option>
					<option value="3" <%=convenio.getEstado().equals("3") ? "selected": ""%>>Vencido</option>					
				</select>			
			</fieldset>
			<br/>
	</div>	
			
	<div class="col">
		<fieldset>
			<label ><b>Tipo</b></label>
			<select name="Tipo" class="form-select">
<% 			for(ConTipo  tipo : listaTipos){%>
				<option value="<%=tipo.getTipoId()%>" <%=convenio.getTipoId().equals(tipo.getTipoId()) ? "selected": ""%>><%=tipo.getTipoNombre()%></option>
<% 			}%>
			</select>			
		</fieldset>
		<br>	
		<fieldset>
			<label ><b>Programa</b></label>
			<input type="text" name="Programa" class="form-control" value="<%=convenio.getPrograma()%>" size="45" required/>
		</fieldset>
		<br/>			
		<fieldset>
			<label ><b>Objetivo</b> <span class="badge bg-dark" id="ObjetivoSize">1500</span> </label>
			<textarea name="Objetivo" id="Objetivo" class="form-control" maxsize="1500" cols="50" rows="4" onKeyUp="refrescarObjetivo();" onkeyDown="return revisarObjetivo();" required><%=convenio.getObjetivo()%></textarea>
		</fieldset>		
	</div>				
	</div>	
	<div class="alert alert-info">
		<a href="javascript:Grabar()" class="btn btn-primary">Grabar</a>
	</div>
	</form>
</div>		
</body>
<script>
	function Grabar(){
		document.frmConvenio.submit();
	}

	function revisarObjetivo(){
		var com = document.getElementById('Objetivo');
		if(event.keyCode==8) return true;
		if(com.value.length==1500) return false; else return true;
	}

	function refrescarObjetivo(){
		var com = document.getElementById('Objetivo');
		var cant = document.getElementById('ObjetivoSize');
		cant.innerHTML = 1500-com.value.length;
		if(com.value.length>1500){
			com.value = com.value.substr(0, 1500);
		}
	}

	jQuery('#FechaFirma').datepicker();
	jQuery('#FechaVigencia').datepicker();
</script>
</html>