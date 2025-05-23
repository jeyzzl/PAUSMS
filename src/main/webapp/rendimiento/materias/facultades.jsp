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
</div>