<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@page import="aca.carga.spring.CargaGrupo"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.*"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script>
	function Refrescar(){
		document.forma.submit();			
	}	
</script>
<%	
	DecimalFormat getFormato	= new DecimalFormat("###,##0.00;(###,##0.00)");

	String periodoId 			= (String)request.getAttribute("periodoId");
	String cargaId 				= (String)request.getAttribute("cargaId");

	int totMaterias		= 0;
	int totReprobadas	= 0;	 
	
	List<CatPeriodo> lisPeriodos 				= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 						= (List<Carga>)request.getAttribute("lisCargas");
	List<CatFacultad> lisFacultades 			= (List<CatFacultad>)request.getAttribute("lisFacultades");
	HashMap<String,String> mapaMaterias 		= (HashMap<String,String>)request.getAttribute("mapaMaterias");
	HashMap<String,String> mapaReprobadas 		= (HashMap<String,String>)request.getAttribute("mapaReprobadas");
%>
<div class="container-fluid">
	<h2>Materias con alumnos reprobados</h2>
	<form name="forma" action="facultades" method='post'>
	<div class="alert alert-info">
		<a href="menu.jsp" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<spring:message code="aca.Periodo" />:&nbsp; 
		<select name="PeriodoId" class="input input-medium" onchange="javascript:Refrescar();">
<%	for(CatPeriodo periodo : lisPeriodos){ %>
			<option value="<%=periodo.getPeriodoId()%>" <%=periodoId.equals(periodo.getPeriodoId())?" Selected":""%>>
				<%=periodo.getNombre().replace("Periodo ", "")%>
			</option>
<%	}%>
		</select>
		&nbsp;&nbsp; Carga:
		<select name="CargaId" style="width: 350px;" onchange="javascript:Refrescar()">
<%	for(Carga carga :lisCargas){ %>
			<option value="<%=carga.getCargaId()%>"	<%=cargaId.equals(carga.getCargaId())?"selected":""%>>
				<%=carga.getCargaId()%> - <%=carga.getNombreCarga()%>
			</option>
<%	}%>
		</select>
	</div>
	</form>
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th>Clave</th>
		<th>Facultad</th>
		<th style="text-align: right">No.Mat.</th>
		<th style="text-align: right">Mat.Rep.</th>
		<th style="text-align: right">%</th>
	</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for (CatFacultad facultad : lisFacultades){
		row++;
		
		String numMat = "0";
		if (mapaMaterias.containsKey(facultad.getFacultadId())){
			numMat = mapaMaterias.get(facultad.getFacultadId());
			totMaterias = totMaterias + Integer.parseInt(numMat);
		}
		
		String numRep = "0";
		if (mapaReprobadas.containsKey(facultad.getFacultadId())){
			numRep = mapaReprobadas.get(facultad.getFacultadId());
			totReprobadas = totReprobadas + Integer.parseInt(numRep);
		}
		
		float porRep = (Float.parseFloat(numRep)*100)/Float.parseFloat(numMat);
%>
		<tr>
			<td><%=row%></td>
			<td><%=facultad.getFacultadId()%></td>
			<td><%=facultad.getNombreFacultad()%></td>
			<td style="text-align: right"><%=numMat%></td>
			<td style="text-align: right"><%=numRep%></td>
			<td style="text-align: right"><%=getFormato.format(porRep)%></td>
		</tr>
		<%		
	}
		float porTot = (float)(totReprobadas*100)/totMaterias;
%>
		<tr>
			<th colspan="3">T O T A L E S</th>
			<th style="text-align: right"><%=totMaterias%></th>
			<th style="text-align: right"><%=totReprobadas%></th>
			<th style="text-align: right"><%=getFormato.format(porTot)%></th>
		</tr>
	</tbody>	
	</table>
</div>