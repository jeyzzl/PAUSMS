<%@ page import="java.text.*"%>
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.carga.spring.Carga"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script>
	function grabaPeriodo(periodoId) {
		document.location.href = "materias?cambioPeriodo=1&PeriodoId="
				+ periodoId;
	}
	function grabaCarga(cargaId) {
		document.location.href = "materias?cambioCarga=1&CargaId="+cargaId;
	}
</script>
<%@ page import="java.util.HashMap"%>
<%	
	DecimalFormat getFormato	= new DecimalFormat("###,##0.00;(###,##0.00)");

	String periodoId 			= (String)session.getAttribute("periodo");
	String cargaId 				= (String)session.getAttribute("cargaId");
	
	String cambioPeriodo 		= request.getParameter("cambioPeriodo")==null?"0":request.getParameter("cambioPeriodo");
	String cambioCarga 			= request.getParameter("cambioCarga")==null?"0":request.getParameter("cambioCarga");
	
	if (cambioPeriodo.equals("1")){
		periodoId = request.getParameter("PeriodoId");
		session.setAttribute("periodo", periodoId);
	}
	if (cambioCarga.equals("1")){
		cargaId = request.getParameter("CargaId");
		session.setAttribute("cargaId", cargaId);
	}
	int totMaterias		= 0;
	int totReprobadas	= 0;	 
	
	List<CatPeriodo> lisPeriodos 				= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 						= (List<Carga>) request.getAttribute("lisCargas");
	List<CatFacultad> lisFacultades				= (List<CatFacultad>) request.getAttribute("lisFacultades");
	
	HashMap<String,String> mapaMaterias 		= (HashMap<String,String>) request.getAttribute("mapaMaterias");	
	HashMap<String,String> mapaReprobadas 		= (HashMap<String,String>) request.getAttribute("mapaReprobadas");
%>
<div class="container-fluid">
	<h2>Facultades con alumnos reprobados</h2>
	<form id="noayuda" name="forma" action="facultades.jsp" method='post'>
		<div class="alert alert-info">
			<a href="menu.jsp" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
			<spring:message code="aca.Periodo" />:
			<select onchange="javascript:grabaPeriodo(this.value);" name="PeriodoId" class="input input-medium">
				<%		for(CatPeriodo periodo : lisPeriodos){ %>
				<option
					<%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%>
					value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
				<%		}%>
			</select>&nbsp;&nbsp; Carga:
			<select onchange='javascript:grabaCarga(this.value)' name="CargaId" style="width: 350px;">
<%		
		for(Carga carga :lisCargas){		
%>
				<option value='<%=carga.getCargaId()%>'
					<%if (cargaId.equals(carga.getCargaId()))out.print("selected");%>>
					[<%=carga.getCargaId()%>] -
					<%=carga.getNombreCarga()%>
				</option>
				<%		}%>
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
			<td><a href="listadoMaterias?cargaId=<%=cargaId%>&facultadId=<%=facultad.getFacultadId()%>"><%=facultad.getNombreFacultad()%></a></td>
			<td style="text-align: right"><%=numMat%></td>
			<td style="text-align: right"><%=numRep%></td>
			<td style="text-align: right"><%=getFormato.format(porRep)%></td>
		</tr>
		<%		
	}
		float porTot = (float)(totReprobadas*100)/totMaterias;
%>
		<tr>
			<td colspan="3">T O T A L E S</td>
			<td style="text-align: right"><%=totMaterias%></td>
			<td style="text-align: right"><%=totReprobadas%></td>
			<td style="text-align: right"><%=getFormato.format(porTot)%></td>
		</tr>
	</table>
</div>