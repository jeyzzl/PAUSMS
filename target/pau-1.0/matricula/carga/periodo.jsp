<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="java.util.ArrayList"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
<script>
	function recargar(){
		document.frmPeriodo.Accion.value= "0";
		document.frmPeriodo.submit();
	}	
	
	function grabar(){
		document.frmPeriodo.Accion.value= "1";
		document.frmPeriodo.submit();
	}
</script>
<body>
<div class="container-fluid">
<%
	String periodoId			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
	String cargaId				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId"); 
	String bloqueId				= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
	String accion				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
	CatPeriodo per 				= (CatPeriodo) request.getAttribute("periodo");
	Carga car 					= (Carga) request.getAttribute("carga");
	CargaBloque bloq			= (CargaBloque) request.getAttribute("bloque");
	
	ArrayList<CatPeriodo> lisPeriodos 		= (ArrayList<CatPeriodo>)request.getAttribute("lisPeriodos");
	ArrayList<Carga> lisCargas 				= (ArrayList<Carga>)request.getAttribute("lisCargas");
	ArrayList<CargaBloque> lisBloques		= (ArrayList<CargaBloque>)request.getAttribute("lisBloques");
	
	String mensajePeriodo		= "-";
	String mensajeCarga			= "-";
	String mensajeBloque		= "-";
	
	// Inicializar valores de listas
	if (periodoId.equals("0") && lisPeriodos.size() >= 1 ) periodoId = lisPeriodos.get(0).getPeriodoId();
	if (cargaId.equals("0") && lisCargas.size() >= 1){
		for (aca.carga.spring.Carga carga : lisCargas){
			if (periodoId.equals(carga.getPeriodo())){
				cargaId = carga.getCargaId();
				break;
			}
		}
	}
	
	if (!cargaId.equals("0") && bloqueId.equals("0") && lisBloques.size() >= 1){
		for (aca.carga.spring.CargaBloque bloque : lisBloques){
			if (cargaId.equals(bloque.getCargaId())){
				bloqueId = bloque.getBloqueId();
				break;
			}
		}
	}
	
	// Mensaje  
	if (accion.equals("1")){
		String estado = per.getEstado().equals("A")?"Active":"Inactive";
		mensajePeriodo 	= "<b>Cycle:</b> "+periodoId+"-"+per.getNombre()+"  <b>Start:</b> "+per.getFechaIni()+"  <b>End:</b> "+per.getFechaFin()+"  <b>Status:</b> "+estado;
		mensajeCarga	= "<b>Load:</b> "+cargaId+"-"+car.getNombreCarga()+"  <b>Start:</b> "+car.getFInicio()+"  <b>End:</b> "+car.getFFinal()+"  <b>Remedial:</b> "+car.getFExtra();
		mensajeBloque	= "<b>Block:</b> "+bloqueId+"-"+bloq.getNombreBloque()+"  <b>Start:</b> "+bloq.getFInicio()+"  <b>Enr. Close:</b> "+bloq.getFCierre()+"  <b>End:</b> "+bloq.getFFinal();
	}	
%>
	<h2>Select Cycle</h2>
	<form name="frmPeriodo" action="periodo" method="post">
	<input type="hidden" name="Accion">
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="alumno"><i class="fas fa-arrow-left"></i></a>
		&nbsp;&nbsp;
		Cycle:&nbsp;
		<select name="PeriodoId" onchange="javascript:recargar()" class="form-select" style="width:350px">
<%
	for (aca.catalogo.spring.CatPeriodo periodo : lisPeriodos){
%>		
			<option value="<%=periodo.getPeriodoId()%>" <%=periodoId.equals(periodo.getPeriodoId())?" Selected":""%>>[<%=periodo.getPeriodoId()%>] <%=periodo.getNombre()%></option>
<% 	}%>			
		</select>
		&nbsp; &nbsp;
		Load:&nbsp;
		<select name="CargaId" onchange="javascript:recargar()" class="form-select" style="width:350px">
<%
	for (aca.carga.spring.Carga carga : lisCargas){
		if (carga.getPeriodo().equals(periodoId)){
%>		
			<option value="<%=carga.getCargaId()%>" <%=cargaId.equals(carga.getCargaId())?" Selected":""%>><%=carga.getCargaId()%>-<%=carga.getNombreCarga()%></option>
<%	
		}
	} %>			
		</select>		
		&nbsp; &nbsp;
		Block:&nbsp;
		<select name="BloqueId" onchange="javascript:recargar()" class="form-select" style="width:350px">
<%
	for (aca.carga.spring.CargaBloque bloque : lisBloques){
		if (bloque.getCargaId().equals(cargaId)){
%>		
			<option value="<%=bloque.getBloqueId()%>" <%=bloqueId.equals(bloque.getBloqueId())?" Selected":""%>><%=bloque.getBloqueId()%>-<%=bloque.getNombreBloque()%></option>
<%	
		}
	} %>			
		</select>
		&nbsp;&nbsp;
<%	if ( !periodoId.equals("0") && !cargaId.equals("0") && !bloqueId.equals("0")){%>	
		<a class="btn btn-primary" href="javascript:grabar()"><i class="fas fa-cloud" ></i></a>
<%	} %>		
	</div>
<%	
	if (!mensajePeriodo.equals("-")){
		out.print("<div class='alert alert-success'>"+mensajePeriodo+"</div>");
		out.print("<div class='alert alert-success'>"+mensajeCarga+"</div>");
		out.print("<div class='alert alert-success'>"+mensajeBloque+"</div>");
	}
%>		
	</form>
</div>
</body>
</html>