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
	String stipoId 			= (String) request.getAttribute("tipoId");
	String accion 			= (String) request.getAttribute("accion");
	boolean existe 			= (boolean) request.getAttribute("existe");
	int i = 0;
	
	String fechaHoy 		= aca.util.Fecha.getHoy();
	String usuario 			= (String) request.getAttribute("usuario");	
	String nombreAlumno	 	= (String) request.getAttribute("nombreAlumno");	
	CandAlumno candAlumno 	= (CandAlumno) request.getAttribute("candAlumno");
	
	List<CandTipo> lisTipo = (List<CandTipo>) request.getAttribute("lisTipo");
	CandTipo tipou = new CandTipo();
	if (lisTipo.size() > 0){
		tipou = lisTipo.get(0);
	}
		
	if (stipoId == null){
		stipoId = tipou.getTipoId();
	}
	
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
<%	for (i = 0; i < lisTipo.size(); i++) {
		CandTipo tipo = lisTipo.get(i);
		if (tipo.getTipoId().equals(stipoId)) {
			out.print(" <option value='" + tipo.getTipoId() + "' Selected>" + tipo.getNombreTipo() + "</option>");
		}else{
			out.print(" <option value='" + tipo.getTipoId() + "'>" + tipo.getNombreTipo() + "</option>");
		}
	}%>
		</select>	
		</div>
	</form>	
	<table style="width:50%" class="table table-condensed">
		<tr>
			<th>Student: <%=codigoPersonal%> - <%=nombreAlumno%></th>
		</tr>	
		<tr>
			<td align="center" valign="bottom">
				<form action="grabar" method="post" name="frmcandado" target="_self">
				<input name="accion" type="hidden" id="accion">
				<input name="tipoId" type="hidden" value="<%=stipoId%>">
				<table style="width:100%">
					<tr>
						<td><b><spring:message code='aca.Folio'/>:</b></td>
						<td><%=candAlumno.getFolio()%> <input name="folio" type="hidden" id="folio" value="<%=candAlumno.getFolio()%>"></td>
					</tr>
					<tr>
						<td width="22%"><b>Lock:</b></td>
						<td width="78%"><select name="candadoId" id="candadoId" class="form-select" style="width:18rem">
	<%					for (i = 0; i < lisCandados.size(); i++) {
							Candado candado = lisCandados .get(i);
							if (candado.getCandadoId().equals(candAlumno.getCandadoId())) {
								out.print(" <option value='" + candado.getCandadoId() + "' Selected>" + candado.getNombreCandado() + "</option>");
							} else {
								out.print(" <option value='" + candado.getCandadoId() + "'>" + candado.getNombreCandado() + "</option>");
							}
						}
						lisCandados = null;%>
						</select></td>
					</tr>	
					<tr>
						<td><b>Active:</b></td>
						<td><b><spring:message code="aca.Fecha"/>&nbsp;</b><span class="badge rounder-pill bg-secondary"><%=candAlumno.getfCreado()%></span> <b><spring:message code="aca.Usuario"/>&nbsp;</b><span class="badge rounder-pill bg-secondary"><%=candAlumno.getUsAlta()%></span></td>
					</tr>
					<tr>
						<td><b>Inactive:</b></td>
						<td>
							<b><spring:message code="aca.Fecha"/></b>&nbsp;<span class="badge rounder-pill bg-secondary"><%=candAlumno.getfBorrado() == null ? "" : candAlumno.getfBorrado()%></span> 
							<b><spring:message code="aca.Usuario"/></b>&nbsp;<span class="badge rounder-pill bg-secondary"><%=candAlumno.getUsBaja() == null ? "" : candAlumno.getUsBaja()%></span>
					</tr>
					<tr>
						<td><strong><spring:message code="aca.Comentario"/>:</strong></td>
						<td>
							<input name="comentario" type="text" id="comentario" value="<%=candAlumno.getComentario()%>" class="form-control" size="50" maxlength="60">
							<input name="estado" type="hidden" id="estado" value="A"></td>
					</tr>
					<tr>
						<td colspan="2" style="text-align:center;"><b><font color="#330099" size="2"><%=resultado%></font></b></td>
					</tr>
				</table>
				</form>
			</td>
		</tr>
	<tr>
		<th>
<%			if (accion.equals("1")) {%>
			  	<input class="btn btn-primary" type="button" onClick="javascript:Grabar()" value="Save" /> &nbsp; 
<%			}
			if (existe){%>
			  	<input class="btn btn-primary" type="button" onClick="javascript:Desactivar('<%=stipoId%>','<%=candAlumno.getFolio()%>')" value="Deactivate" />&nbsp;
<%			}%>
		</th>
	</tr>
	</table>
	<!-- Tabla que muestra el listado de candados del alumno -->
	<table id="table" class="table table-sm table-bordered">
	<thead>	 
		<tr>
			<td class="titulo2" colspan="6">Student Locks</td>
		</tr>
	</thead>
	<thead class="table-info">	 
		<tr>
			<th width="5%" height="21" align="center"><spring:message code="aca.Numero"/></th>
			<th width="25%" height="21" align="center">Lock</th>
			<th width="12%" height="21" align="center">Activated</th>
			<th width="11%" height="21" align="center">User</th>
			<th width="39%" height="21" align="center"><spring:message code="aca.Comentario"/></th>
			<th width="8%" height="21" align="center"><spring:message code="aca.Status"/></th>
		</tr>
	</thead>
<%		
	String nombreCandado = "";
	for (i = 0; i < lisDelAlumno.size(); i++) {
		CandAlumno candAlum = lisDelAlumno.get(i);
		
		if(getMapaCandados.containsKey(candAlum.getCandadoId())){
			nombreCandado = getMapaCandados.get(candAlum.getCandadoId()).getNombreCandado();
		}%>
		<tr>
			<td align="center"><%=candAlum.getFolio()%></td>
			<td align="left">
				<a href="javascript:Consultar('<%=stipoId%>','<%=candAlum.getFolio()%>')"><%=nombreCandado%></a>
			</td>
			<td align="center"><%=candAlum.getfCreado()%></td>
			<td align="center"><%=candAlum.getUsAlta()%></td>
			<td align="left"><%=candAlum.getComentario()%></td>
			<td align="center"><%=candAlum.getEstado().equals("A")?"<span class='badge bg-success'>Active</span>":"<span class='badge bg-warning text-dark'>Inactive</span>"%></td>
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
			<th width="3%" height="21" align="center"><spring:message code="aca.Numero"/></th>
			<th width="18%" height="21" align="center">Lock</th>
			<th width="18%" height="21" align="center">Cat.</th>
			<th width="10%" height="21" align="center">Activated</th>
			<th width="10%" height="21" align="center">User</th>
			<th width="39%" height="21" align="center"><spring:message code="aca.Comentario"/></th>
			<th width="8%" height="21" align="center"><spring:message code="aca.Status"/></th>
		</tr>
	</thead>
<%		
	int cont = 0;
	String nombreTipo = "";
	for(i=0; i<lisTodos.size(); i++) {
		CandAlumno candAlum = lisTodos.get(i);
		if (!candAlum.getCandadoId().substring(0, 2).equals(stipoId)) {
			cont++;
		if(getMapaCandados.containsKey(candAlum.getCandadoId())){
			nombreCandado = getMapaCandados.get(candAlum.getCandadoId()).getNombreCandado();
		}
		if(mapTipo.containsKey(candAlum.getCandadoId().substring(0, 2))){
			nombreTipo = mapTipo.get(candAlum.getCandadoId().substring(0, 2)).getNombreTipo();
		}%>
		<tr>
			<td align="center"><%=candAlum.getFolio()%></td>
			<td align="left"><%=nombreCandado%></td>
			<td align="left"><%=nombreTipo%></td>
			<td align="center"><%=candAlum.getfCreado()%></td>
			<td align="center"><%=candAlum.getUsAlta()%></td>
			<td align="left"><%=candAlum.getComentario()%></td>
			<td align="center"><%=candAlum.getEstado().equals("A")?"<span class='badge bg-success'>Active</span>":"<span class='badge bg-warning text-dark'>Inactive</span>"%></td>
		</tr>
<%		}
	}%>
	</table>
</div>