<%@page import="java.util.HashMap"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CatPeriodoU" scope="page" class="aca.catalogo.CatPeriodoUtil"/>
<jsp:useBean id="CargaU" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="CatFacultadU" scope="page" class="aca.catalogo.CatFacultadUtil"/>
<jsp:useBean id="CatCarreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="FinTablaU" scope="page" class="aca.vista.FinTablaUtil"/>

<html>
<head>
<script type="text/javascript">

	function cambiaPeriodo(periodoId){
		document.location.href="reporte?PeriodoId="+periodoId+"&cambioPeriodo=1";
	}
	
	function cambiaCarga(cargaId){		
		document.location.href="reporte?CargaId="+cargaId+"&cambioCarga=S";
	}
	
	function Show( ){
		document.forma.Accion.value = "1"
		document.forma.submit();
	}
	
</script>
</head>
<%
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;(###,##0.00)");

	String codigoPersonal	= (String)session.getAttribute("codigoPersonal");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	HashMap<String, aca.catalogo.CatFacultad> mapFacultad		= CatFacultadU.getMapFacultad(conEnoc, "");
	HashMap<String, aca.catalogo.CatCarrera> mapCarrera 		= CatCarreraU.getMapAll(conEnoc, "");
	HashMap<String, aca.catalogo.CatModalidad> mapModalidad		= aca.catalogo.ModalidadUtil.getMapAll(conEnoc, "");
	
	// Catálogo de periodos
	ArrayList<aca.catalogo.CatPeriodo> listaPeriodos = CatPeriodoU.getListAll(conEnoc, "ORDER BY PERIODO_ID");
	
	if(request.getParameter("cambioPeriodo")!=null && !request.getParameter("cambioPeriodo").equals("")){
		session.setAttribute("periodo", request.getParameter("PeriodoId"));
	}
	
	String periodoId = (String)session.getAttribute("periodo");
	
	ArrayList<aca.carga.Carga> lisCarga = CargaU.getListAll(conEnoc,"WHERE PERIODO IN ('"+periodoId+"') AND ESTADO = '1' ORDER BY CARGA_ID");
	
	if(request.getParameter("cambioPeriodo")!=null&&!request.getParameter("cambioPeriodo").equals("")&&!lisCarga.isEmpty()){
		session.setAttribute("cargaId", lisCarga.get(0).getCargaId());
	}else if(request.getParameter("cambioCarga")!=null&&!request.getParameter("cambioCarga").equals("")){
		session.setAttribute("cargaId", request.getParameter("CargaId"));
	}
	
	String cargaId = (String)session.getAttribute("cargaId");
	
	if(lisCarga.isEmpty()){
		session.setAttribute("cargaId", "");
	}
	
	// Lista de costos en la tabla financiera	
	ArrayList<aca.vista.FinTabla> lisCostos = FinTablaU.listCarga(conEnoc, cargaId, " ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID");
%>
<body>
	<div class="container-fluid">
		<h1> Reporte de Tabla Financiera</h1>
		<form name="forma" action="reporte">
		<input name="Accion" type="hidden"/>
		<div class="alert alert-info">	
			<b>Período:</b>&nbsp;
       		<select onchange='javascript:cambiaPeriodo(this.value);' name="PeriodoId" class="input input-medium">
<%		for(int j=0; j<listaPeriodos.size(); j++){
			aca.catalogo.CatPeriodo periodo = listaPeriodos.get(j);%>
				<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print("Selected");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%		}%>
			</select> &nbsp;
        	<b>Carga:</b>&nbsp;
        	<select onchange='javascript:cambiaCarga(this.value);' name="CargaId" style="width:350px;" class="input input-xlarge">
<%		for(int i=0; i<lisCarga.size(); i++){
			aca.carga.Carga carga = lisCarga.get(i);
%>
			<option <%if(cargaId.equals(carga.getCargaId()))out.print("Selected");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
<%		}%>
				
        </select> &nbsp;
        <input type="text" align="left" class="input-medium search-query" placeholder="Buscar..." id="buscar">
        <p id="txt" valign="middle" style=" letter-spacing:1px;"></p>	
		</div>
			
		<table class="table table-bordered">
		<thead class="table-info">
			<tr>
				<th>#</th>
				<th><spring:message code="aca.Facultad"/></th>
				<th><spring:message code="aca.Carrera"/></th>
				<th>Nom. Carrera</th>
				<th><spring:message code="aca.Modalidad"/></th>
				<th>Mat.Tot.</th>
				<th>Mat. %</th>
				<th>Mat.Carr.</th>
				<th>Inter.Tot..</th>
				<th>Inter. %</th>
				<th>Inter. Carr.</th>
				<th>Legales Tot.</th>
				<th>Legales %</th>
				<th>Legales Carr.</th>
				<th>$ ACFE</th>
				<th>$ NO ACFE</th>
			<tr>
		</thead>
<%
		int row=0;
		String facultad = "", nFacultad = "", carrera = "", modalidad = "";
		double matricula = 0.0, internado = 0.0, legales = 0.0;
		double acfe = 0.0, noacfe = 0.0;
		for (aca.vista.FinTabla tabla : lisCostos ){
			row++;
			matricula = Double.parseDouble(tabla.getMatricula())* Double.parseDouble(tabla.getPorMatricula());
			internado = Double.parseDouble(tabla.getInternado())* Double.parseDouble(tabla.getPorInternado());
			legales	 = Double.parseDouble(tabla.getLegales())* Double.parseDouble(tabla.getPorLegales());
			if(mapCarrera.containsKey(tabla.getCarreraId())){
				carrera 	= mapCarrera.get(tabla.getCarreraId()).getNombreCarrera();
				facultad 	= mapCarrera.get(tabla.getCarreraId()).getFacultadId();
			}
			if(mapFacultad.containsKey(facultad)){
				nFacultad 	= mapFacultad.get(facultad).getNombreCorto();
			}
			if(mapModalidad.containsKey(tabla.getModalidadId())){
				modalidad 	= mapModalidad.get(tabla.getModalidadId()).getNombreModalidad();
			}
			acfe 	= Double.parseDouble(tabla.getAcfe()) * Double.parseDouble(tabla.getPorCredito()) * Double.parseDouble(tabla.getPorCredito());
			noacfe 	= Double.parseDouble(tabla.getNoAcfe()) * Double.parseDouble(tabla.getPorCredito()) * Double.parseDouble(tabla.getPorCredito());
			
%>
			<tr>
				<td><%= row%></td>
				<td><%= nFacultad %></td>
				<td><%= tabla.getCarreraId() %></td>
				<td><%= carrera%></td>
				<td><%= modalidad %></td>
				<td style="text-align:right"><%= getFormato.format(Double.parseDouble(tabla.getMatricula())) %></td>
				<td style="text-align:right"><%= getFormato.format(Double.parseDouble(tabla.getPorMatricula())) %></td>
				<td style="text-align:right"><%= getFormato.format(matricula) %></td>
				<td style="text-align:right"><%= getFormato.format(Double.parseDouble(tabla.getInternado())) %></td>
				<td style="text-align:right"><%= getFormato.format(Double.parseDouble(tabla.getPorInternado())) %></td>
				<td style="text-align:right"><%= getFormato.format(internado) %></td>
				<td style="text-align:right"><%= getFormato.format(Double.parseDouble(tabla.getLegales())) %></td>
				<td style="text-align:right"><%= getFormato.format(Double.parseDouble(tabla.getPorLegales())) %></td>
				<td style="text-align:right"><%= getFormato.format(legales) %></td>
				<td style="text-align:right"><%= getFormato.format(acfe) %></td>
				<td style="text-align:right"><%= getFormato.format(noacfe) %></td>
			<tr>
<%
		}
%>	
		</table>		
	</form>		
	</div>
</body>
<script src="../../js/jquery-1.9.1.min.js"></script>
<script src="../../js/search.js"></script>
<script type="text/javascript">
$('#buscar').search();
</script>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>