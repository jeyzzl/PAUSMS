<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.carga.spring.Carga"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
	<head></head>
<%
	String periodoId	= (String)request.getAttribute("periodoId");
	String cargaId 		= (String)request.getAttribute("cargaId");
	
	List<CatPeriodo> lisPeriodos 		= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 				= (List<Carga>)request.getAttribute("lisCargas");
	List<CatFacultad> lisFacultades 	= (List<CatFacultad>)request.getAttribute("lisFacultades");	
	
	//Mestros por facultad	
	HashMap<String,String> mapMaestrosEnCarga	=  (HashMap<String,String>)request.getAttribute("mapMaestrosEnCarga");
	HashMap<String,String> mapMaestrosRegistrados	=  (HashMap<String,String>)request.getAttribute("mapMaestrosRegistrados");

%>
	<body>
<div class="container-fluid">
	<h2>Informe de carga</h2>
	<form name="frmElige" action="elige" method="post">
	<div class="alert alert-info">
		Periodo:
			<select onchange="document.frmElige.submit();" name="PeriodoId" class="input input-medium">
			<%	for(CatPeriodo periodo : lisPeriodos){%>
				<option <%=periodoId.equals(periodo.getPeriodoId())?"Selected":""%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
			<%	}%>
			</select>
			&nbsp;
		Carga:
			<select name="CargaId" onchange="document.frmElige.submit();" style="width:350px;" class="input input-xlarge">
			<%	for(Carga carga : lisCargas){	%>
				<option <%=cargaId.equals(carga.getCargaId())?"Selected":""%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
			<%	}%>
			</select>
	</div>
	</form>
	<table class="table table-sm table-bordered">
		<tr>
			<th width="10%"><spring:message code="aca.Numero"/></th>
			<th><spring:message code="aca.Facultad"/></th>
			<th class="text-end">#Mae.con Mat.</th>
			<th class="text-end">#Mae.Reg.</th>
			<th class="text-end">#Mae.S.R.</th>
		</tr>
<%	
	for(CatFacultad facultad : lisFacultades){
		
		String maestrosEnCarga 		= "0";
		String maestrosRegistrados 	= "0";
		
		if(mapMaestrosEnCarga.containsKey(facultad.getFacultadId())){
			maestrosEnCarga = mapMaestrosEnCarga.get(facultad.getFacultadId());
		}
		
		if(mapMaestrosRegistrados.containsKey(facultad.getFacultadId())){
			maestrosRegistrados = mapMaestrosRegistrados.get(facultad.getFacultadId());
		}
		
		int totMaeFaltan 	= Integer.parseInt(maestrosEnCarga) - Integer.parseInt(maestrosRegistrados);		
%>
			<tr>
				<td><a href="carrera?FacultadId=<%=facultad.getFacultadId()%>&CargaId=<%=cargaId%>"><%=facultad.getFacultadId()%></a></td>
				<td><a href="carrera?FacultadId=<%=facultad.getFacultadId()%>&CargaId=<%=cargaId%>"><b><%=facultad.getTitulo()%> de <%=facultad.getNombreFacultad()%></b></td>				
				<td class="text-end"><a href="listaMaestros?CargaId=<%=cargaId%>&FacultadId=<%=facultad.getFacultadId()%>&Opcion=2"><b><%=maestrosEnCarga%></b></a></td>
				<td class="text-end"><b><%=maestrosRegistrados%></b></td>
				<td class="text-end"><a href="listaMaestros?CargaId=<%=cargaId%>&FacultadId=<%=facultad.getFacultadId()%>&Opcion=1"><b><%=totMaeFaltan%></b></a></td>
			</tr>
<%	}%>
		</table>
		</div>
	</body>
</html>