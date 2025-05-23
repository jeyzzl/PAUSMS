<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.financiero.spring.FesCcobro"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.carga.spring.CargaFinanciero"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@ page import="aca.attache.spring.AttacheCustomer"%>


<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function cambioPeriodo(){
		var periodoId = document.getElementById("PeriodoId").value;
		location.href = "listado?PeriodoId="+periodoId;
	}
	
	function cambioCarga(){
		var periodoId = document.getElementById("PeriodoId").value;
		var cargaId = document.getElementById("CargaId").value;
		location.href = "listado?PeriodoId="+periodoId+"&CargaId="+cargaId;
	}
	
	function cambioBloque(){
		var periodoId = document.getElementById("PeriodoId").value;
		var cargaId = document.getElementById("CargaId").value;
		var bloqueId = document.getElementById("BloqueId").value;
		location.href = "listado?PeriodoId="+periodoId+"&CargaId="+cargaId+"&BloqueId="+bloqueId;
	}
	
	function Borrar(codigoPersonal,cargaId,bloqueId){
		if(confirm("<spring:message code="aca.JSEliminar"/> " ) == true) {
			location.href = "borrar?CodigoAlumno="+codigoPersonal+"&CargaId="+cargaId+"&BloqueId="+bloqueId;
		}
	}
</script>

<%
	AlumAcademico alumno		= (AlumAcademico)request.getAttribute("alumno");
	
	String codigoAlumno			= (String)request.getAttribute("codigoAlumno");
	String alumnoNombre 		= (String)request.getAttribute("alumnoNombre");
	String periodoId			= (String)request.getAttribute("PeriodoId");
	String cargaId					= (String)request.getAttribute("CargaId");
	String bloqueId					= (String)request.getAttribute("BloqueId");
	String mensaje					= (String)request.getAttribute("Mensaje");
	String grabados					= (String)request.getAttribute("grabados");
	String errores					= (String)request.getAttribute("errores");
	
	
	List<FesCcobro> lisCalculos 						= (List<FesCcobro>)request.getAttribute("lisCalculos");
	List<CatPeriodo> lisPeriodos 						= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
	List<Carga> lisCargas		 						= (List<Carga>)request.getAttribute("lisCargas");
	List<CargaBloque> lisBloques						= (List<CargaBloque>)request.getAttribute("lisBloques");
	List<CargaFinanciero> lisAlum						= (List<CargaFinanciero>)request.getAttribute("lisAlum");
	
	HashMap<String,String> mapaAlumPlan 						= (HashMap<String,String>)request.getAttribute("mapaAlumPlan");
	HashMap<String,AlumPersonal> mapaAlumPersonal 				= (HashMap<String,AlumPersonal>)request.getAttribute("mapaAlumPersonal");
	HashMap<String,String> mapaCreditos 						= (HashMap<String,String>)request.getAttribute("mapaCreditos");
	HashMap<String,CargaBloque> mapaBloques						= (HashMap<String,CargaBloque>)request.getAttribute("mapaBloques");
	HashMap<String,CargaBloque> mapaBloquesActivos				= (HashMap<String,CargaBloque>)request.getAttribute("mapaBloquesActivos");

	HashMap<String,AttacheCustomer> mapaCustomer				= (HashMap<String,AttacheCustomer>)request.getAttribute("mapaCustomer");
	HashMap<String,String> mapaBalances							= (HashMap<String,String>)request.getAttribute("mapaBalances");
	
%>
<div class="container-fluid">
<!-- TITULO -->
	<h2>Financial Clearance <small class="text-muted fs-5">( <b><%=codigoAlumno%></b> <%=alumnoNombre%>&nbsp; <b>Residence:</b> <%=alumno.getResidenciaId().equals("I")?"Boarding Student":"Day Student" %>  )</small></h2>
	
		
<!-- FILTROS -->
	<div class="alert alert-info d-flex align-items-center">
		<spring:message code="aca.Ciclo"/>:&nbsp; 
		<select id="PeriodoId" name="PeriodoId" onchange="javascript:cambioPeriodo();" class="form-select" style="width:150px">
		<% for(CatPeriodo periodo: lisPeriodos){ %>
			<option value="<%= periodo.getPeriodoId() %>" <%=periodo.getPeriodoId().equals(periodoId)?"selected": ""%>   ><%= periodo.getNombre() %></option>
			<% } %>
		</select>&nbsp;&nbsp;
		<spring:message code="aca.Carga"/>:&nbsp; 
		<select id="CargaId" name="CargaId" onchange="javascript:cambioCarga();" class="form-select" style="width:250px">
			<% for(Carga carga: lisCargas){ %>
			<option value="<%= carga.getCargaId() %>" <%=carga.getCargaId().equals(cargaId)?"selected": ""%>   ><%= carga.getCargaId() %>-<%=carga.getNombreCarga()%></option>
			<% } %>
		</select>&nbsp;&nbsp;
		<spring:message code="aca.Bloque"/>:&nbsp; 
		<select id="BloqueId" name="BloqueId" onchange="javascript:cambioBloque();" class="form-select" style="width:200px">
			<% for(CargaBloque bloque: lisBloques){ %>
			<option value="<%= bloque.getBloqueId() %>" <%=bloque.getBloqueId().equals(bloqueId)?"selected": ""%>   ><%=bloque.getBloqueId()%>-<%=bloque.getNombreBloque()%></option>
			<% } %>
		</select>
	</div>
	<% if(mensaje.equals("1")){ %>
	<div class="alert alert-success">
			<%=codigoAlumno %> saved Succesfully!
		</div>
		<% } else if(mensaje.equals("2")){ %>
	<div class="alert alert-danger">
			There has been an error in Saving <%=codigoAlumno %>!
		</div>
		<% } else if(mensaje.equals("3")){ %>
	<div class="alert alert-success">
			<%=codigoAlumno %> has been updated Succesfully!
		</div>
		<% } else if(mensaje.equals("4")){ %>
	<div class="alert alert-danger">
			There has been an error in Updating <%=codigoAlumno %>!
		</div>
		<% } else if(mensaje.equals("5")){ %>
	<div class="alert alert-success">
			<%=codigoAlumno %> has been deleted Succesfully!
		</div>
		<% } else if(mensaje.equals("6")){ %>
		<div class="alert alert-danger">
			There has been an error deleting file for <%=codigoAlumno %>! 
		</div>
		<% } else if(mensaje.equals("7")){ %>
		<div class="alert alert-success">
			There has been an error saving Image for <%=codigoAlumno %>! 
		</div>
		<% } else if(grabados.equals("0") == false){ %>
		<div class="alert alert-success">
			<%=grabados %> students have been added Succesfully!
		</div>
		<% } else if(errores.equals("0") == false){ %>
		<div class="alert alert-danger">
			There has been an error deleting <%=errores %> registers! 
		</div>
		<% } %>
		
<!-- OPCIONES DE SELECCION -->
	<div class="container-fluid p-0">
	<a href= "editar?CargaId=<%=cargaId %>&BloqueId=<%=	bloqueId %>" class="btn btn-sm btn-primary">Add</a>
		<a href="editarMult?CargaId=<%=cargaId %>&BloqueId=<%=bloqueId %>" class="btn btn-sm btn-primary">Select Multiple</a>
	</div>
	<br>
<!-- TABLA -->
	<table class="table table-sm table-bordered">
	<!-- titulos -->
		<tr class="table-info">
			<th width="3%">No.</th>
			<th width="4%"></th>
			<th width="6%">Student ID</th>
			<th width="15%"><spring:message code="aca.Nombre"/></th>
			<th width="12%"><spring:message code="aca.Plan"/></th>
			<th width="20%">Comments</th>
<%	if(session.getAttribute("institucion").equals("Pacific Adventist University")){%>
			<th width="6%">Openbal</th>
			<%-- <th>Cust.Bal</th> --%>
			<th width="6%">Unallocbal </th>
<%	}%>	
			<th width="8%"><spring:message code="aca.Status"/></th>
			<th width="12%"><spring:message code="aca.Fecha"/></th>
			<th width="5%"><spring:message code="aca.Usuario"/></th>
		</tr>
<%		
%>
	<!-- campos -->
	<% 
		int row = 0;
		for(CargaFinanciero alum: lisAlum){ 
			row++;
		String status = "bg-warning";
		if(alum.getStatus().equals("A")){
			status = "bg-success";
		}

		Double openbal = 0.0;
		Double unallocbal = 0.0;
		Double custbal = 0.0;
		String colorOpenBal = "";
		String colorUnallocBal = "";
		String colorCustbal = "";
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
		if(session.getAttribute("institucion").equals("Pacific Adventist University")){
			if(mapaCustomer.containsKey(alum.getCodigoPersonal())){
				openbal = mapaCustomer.get(alum.getCodigoPersonal()).getOpenbal();
				if(openbal > 0) colorOpenBal = "table-danger";
				unallocbal = mapaCustomer.get(alum.getCodigoPersonal()).getUnallocbal();
				if(unallocbal < 0) colorUnallocBal = "table-warning";
			}
			if(mapaBalances.containsKey(alum.getCodigoPersonal())){
				custbal = Double.parseDouble(mapaBalances.get(alum.getCodigoPersonal()));
			}
		}
		String formattedOpenBal = currencyFormatter.format(openbal);
		String formattedUnallocBal = currencyFormatter.format(unallocbal);
		
	%>
		<tr>
			<td class="text-center"><%=row%></td>
			<td class="d-flex justify-content-around">
<!-- 				<input class="form-check-input" type="checkbox" id="checkboxNoLabel" value="" aria-label="..."> -->
				<a href="javascript:Borrar('<%=alum.getCodigoPersonal()%>','<%=cargaId%>','<%=bloqueId%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
				<a href="editar?CodigoAlumno=<%=alum.getCodigoPersonal() %>&CargaId=<%=cargaId%>&BloqueId=<%=bloqueId%>"><i class="fas fa-edit"></i></a>
			</td>
			<td><%=alum.getCodigoPersonal() %></td>
			<td><%=mapaAlumPersonal.get(alum.getCodigoPersonal()).getNombre() %> <%= mapaAlumPersonal.get(alum.getCodigoPersonal()).getApellidoPaterno()%></td>
			<td><%=mapaAlumPlan.containsKey(alum.getCodigoPersonal())?mapaAlumPlan.get(alum.getCodigoPersonal()): "" %></td>
			<td><%= alum.getComentario() %></td>
<%	if(session.getAttribute("institucion").equals("Pacific Adventist University")){%>
			<td class="<%=colorOpenBal%>"><%=formattedOpenBal%></td>
			<td class="<%=colorUnallocBal%>"><%=formattedUnallocBal%></td>
<%	}%>	
			<td>
				<a href="quickEdit?CargaId=<%=cargaId%>&BloqueId=<%=bloqueId%>&Status=<%=alum.getStatus().equals("A")?"I":"A"%>&CodigoAlumno=<%=alum.getCodigoPersonal()%>"><span class="badge rounded-pill <%=status%>"><%=alum.getStatus().equals("A")?"CLEARED":"NOT CLEARED"%></span></a>
			</td>
			<td><%=alum.getFecha() %></td>
			<td><%=alum.getUsuario() %></td>
		</tr>
		<%	}%>
	</table>
</div>