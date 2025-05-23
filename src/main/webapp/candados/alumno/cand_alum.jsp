<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.candado.spring.Candado"%>
<%@page import="aca.candado.spring.CandAlumno"%>
<%@page import="aca.candado.spring.CandTipo"%>

<script type="text/javascript">
	function Grabar() {
		if (document.frmcandado.candadoId.value != "") {
			document.frmcandado.submit();
		} else {
			alert("Fill out the entire form!");
		}
	}

	function Desactivar(tipo, folio) {
		document.location.href = "desactivar?tipoId="+tipo+"&folio="+folio;
	}	

	function Consultar(tipo, folio) {	
		document.location.href = "cand_alum?tipoId="+tipo+"&folio="+folio;
	}
</script>
<%	
	String codigoPersonal 	= (String) request.getAttribute("codigoPersonal");
	String tipoId 			= (String) request.getAttribute("tipoId");
	String accion 			= (String) request.getAttribute("accion");
	boolean existe 			= (boolean) request.getAttribute("existe");
	int i = 0;
	
	String fechaHoy 		= aca.util.Fecha.getHoy();
	String usuario 			= (String) request.getAttribute("usuario");	
	String nombreAlumno	 	= (String) request.getAttribute("nombreAlumno");	
	CandAlumno candAlumno 	= (CandAlumno) request.getAttribute("candAlumno");
	
	List<CandTipo> lisTipo = (List<CandTipo>) request.getAttribute("lisTipo");
	
	List<Candado> lisCandados 		= (List<Candado>) request.getAttribute("lisCandados");
	List<CandAlumno> lisDelAlumno 	= (List<CandAlumno>) request.getAttribute("lisDelAlumno");
	List<CandAlumno> lisTodos 		= (List<CandAlumno>) request.getAttribute("lisTodos");
	
	HashMap<String,Candado> getMapaCandados = (HashMap<String,Candado>) request.getAttribute("getMapaCandados");
	HashMap<String,CandTipo> mapTipo		= (HashMap<String,CandTipo>) request.getAttribute("mapTipo");
	
	String resultado 		= "";
%>
<div class="container-fluid">
	<h2>Assign Locks</h2>
	<form name="form1" method="post" action="cand_alum">
	<div class="alert alert-info">	
		<select name="tipoId" id="tipoId" onChange="javascritp:document.form1.submit()" class="form-select" style="width:18rem">
<%	for (CandTipo tipo : lisTipo) { %>
			<option value="<%=tipo.getTipoId()%>" <%=tipo.getTipoId().equals(tipoId)?"selected":""%>><%=tipo.getNombreTipo()%></option>
<%	}	%>
		</select>	
		</div>
	</form>	
	<h5><b><%=codigoPersonal%></b> - <%=nombreAlumno%></h5>
	<form action="grabar" method="post" name="frmcandado" target="_self">
	<div class="mb-3">
		<input name="accion" type="hidden" id="accion">
		<input name="tipoId" type="hidden" value="<%=tipoId%>">
		<input name="folio" type="hidden" id="folio" value="<%=candAlumno.getFolio()%>">
		<input name="estado" type="hidden" id="estado" value="A"></td>
		<div class="mb-2">
			<spring:message code='aca.Folio'/>:</b>
			<%=candAlumno.getFolio()%>
		</div>
		<div class="mb-2">
			<b>Lock:</b>
			<select name="candadoId" id="candadoId" class="form-select" style="width:18rem">
<%			for (Candado candado : lisCandados) { %>
					<option value="<%=candado.getCandadoId()%>" <%=candado.getCandadoId().equals(candAlumno.getCandadoId())?"selected":""%>><%=candado.getNombreCandado()%></option>
<%			}%>
			</select>
		</div>
		<div class="mb-2">
			<b>Active:</b>
			<b><spring:message code="aca.Fecha"/>&nbsp;</b><span class="badge rounder-pill bg-secondary"><%=candAlumno.getfCreado()%></span> 
			<b><spring:message code="aca.Usuario"/>&nbsp;</b><span class="badge rounder-pill bg-secondary"><%=candAlumno.getUsAlta()%></span>
		</div>
		<div class="mb-2">
			<b>Inactive:</b>
			<b><spring:message code="aca.Fecha"/></b>&nbsp;</b><span class="badge rounder-pill bg-secondary"><%=candAlumno.getfBorrado() == null ? "" : candAlumno.getfBorrado()%></span> 
			<b><spring:message code="aca.Usuario"/></b>&nbsp;</b><span class="badge rounder-pill bg-secondary"><%=candAlumno.getUsBaja() == null ? "" : candAlumno.getUsBaja()%></span>
		</div>
		<div class="mb-2">
			<strong><spring:message code="aca.Comentario"/>:</strong>
			<textarea class="form-control mb-4" id="comentario" name="comentario" rows="3" style="width: 25rem;"  maxlength="60"></textarea>
		</div>

		<%=resultado%>

<%	if (accion.equals("1")) {%>
		<input class="btn btn-primary" type="button" onClick="javascript:Grabar()" value="Save" /> &nbsp; 
<%	}
	if (existe){%>
		<input class="btn btn-primary" type="button" onClick="javascript:Desactivar('<%=tipoId%>','<%=candAlumno.getFolio()%>')" value="Deactivate" />&nbsp;
<%	}%>
	</div>
	</form>

	<!-- Tabla que muestra el listado de candados del alumno -->
	<table id="table" class="table table-sm table-bordered">
	<thead>	 
		<tr>
			<td class="titulo2" colspan="6">Student Locks</td>
		</tr>
	</thead>
	<thead class="table-info">	 
		<tr>
			<th width="5%" height="21"><spring:message code="aca.Numero"/></th>
			<th width="25%" height="21">Lock</th>
			<th width="12%" height="21">Activated</th>
			<th width="11%" height="21">User</th>
			<th width="39%" height="21"><spring:message code="aca.Comentario"/></th>
			<th width="8%" height="21"><spring:message code="aca.Status"/></th>
		</tr>
	</thead>
<%		
	String nombreCandado = "";
	for (CandAlumno candAlum :  lisDelAlumno) {
		if(getMapaCandados.containsKey(candAlum.getCandadoId()+tipoId)){
			nombreCandado = getMapaCandados.get(candAlum.getCandadoId()+tipoId).getNombreCandado();
		}%>
		<tr>
			<td><%=candAlum.getFolio()%></td>
			<td>
				<a href="javascript:Consultar('<%=tipoId%>','<%=candAlum.getFolio()%>')"><%=nombreCandado%></a>
			</td>
			<td><%=candAlum.getfCreado()%></td>
			<td><%=candAlum.getUsAlta()%></td>
			<td><%=candAlum.getComentario()%></td>
			<td><%=candAlum.getEstado().equals("A")?"<span class='badge bg-success'>Active</span>":"<span class='badge bg-warning text-dark'>Inactive</span>"%></td>
		</tr>
<%	}%>
	</table>
	<table id="table" class="table table-sm table-bordered">
	<thead>	 
		<tr>
			<td class="titulo2" colspan="6">Other locks</td>
		</tr>
	</thead>
	<thead class="table-info">	 
		<tr>
			<th width="3%" height="21"><spring:message code="aca.Numero"/></th>
			<th width="18%" height="21">Lock</th>
			<th width="18%" height="21">Cat.</th>
			<th width="10%" height="21">Activated</th>
			<th width="10%" height="21">User</th>
			<th width="39%" height="21"><spring:message code="aca.Comentario"/></th>
			<th width="8%" height="21"><spring:message code="aca.Status"/></th>
		</tr>
	</thead>
<%		
	int cont = 0;
	String nombreTipo = "";
	for(CandAlumno candAlum : lisTodos) {
		if(!candAlum.getTipoId().equals(tipoId)){
			cont++;
			if(getMapaCandados.containsKey(candAlum.getCandadoId()+candAlum.getTipoId())){
				nombreCandado = getMapaCandados.get(candAlum.getCandadoId()+candAlum.getTipoId()).getNombreCandado();
			}
			if(mapTipo.containsKey(candAlum.getTipoId())){
				nombreTipo = mapTipo.get(candAlum.getTipoId()).getNombreTipo();
			}
%>
		<tr>
			<td><%=candAlum.getFolio()%></td>
			<td><%=nombreCandado%></td>
			<td><%=nombreTipo%></td>
			<td><%=candAlum.getfCreado()%></td>
			<td><%=candAlum.getUsAlta()%></td>
			<td><%=candAlum.getComentario()%></td>
			<td><%=candAlum.getEstado().equals("A")?"<span class='badge bg-success'>Active</span>":"<span class='badge bg-warning text-dark'>Inactive</span>"%></td>
		</tr>
<%		}
	}
	%>
	</table>
</div>