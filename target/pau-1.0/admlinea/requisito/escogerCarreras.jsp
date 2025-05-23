<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AdmRequisito"  class="aca.admision.AdmRequisito" scope="page"/>
<html>
<head></head>
<%
	String carreraDestino		= request.getParameter("CarreraDestino")==null?"0":request.getParameter("CarreraDestino");

	List<CatCarrera> lisCarreras 					= (List<CatCarrera>) request.getAttribute("lisCarreras");
	HashMap<String,String> mapaRequisitos 			= (HashMap<String,String>) request.getAttribute("mapaRequisitos");
	HashMap<String,CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	
	String tmpFac = "";
%>
<body>
<div class="container-fluid">
	<h2><spring:message code='aca.ListaDeCarreras'/> <small> ( <spring:message code="aca.Copiar"/> <spring:message code="aca.Requisitos"/> )</small></h2>	
	<div class="alert alert-info"><a href="carrera" class="btn btn-primary"><spring:message code="aca.Regresar"/></a></div>
	<table class="table table_condensed" style="width:50%">
<%	for(CatCarrera carrera : lisCarreras){
	
		String totalRequisitos = "0";
		if (mapaRequisitos.containsKey(carrera.getCarreraId())){
			totalRequisitos = mapaRequisitos.get(carrera.getCarreraId());
		}
		
		if(!carrera.getFacultadId().equals(tmpFac)){
			tmpFac = carrera.getFacultadId();
			String facultadNombre = "";
			if (mapaFacultades.containsKey(carrera.getFacultadId())){
				facultadNombre = mapaFacultades.get(carrera.getFacultadId()).getNombreFacultad();
			}
%>
		<tr><th colspan="2"><%=facultadNombre%></th></tr>
		<tr>
			<th align="center"><spring:message code="aca.Copiar"/></th>
			<th align="center"><spring:message code="aca.Carrera"/></th>	
			<th align="center">#<spring:message code="aca.Requisitos"/></th>
		</tr>
		<%	}%>
		<tr class="tr2">
			<td align="center"><a href="javascript:Copiar('<%=carrera.getCarreraId()%>','<%=carreraDestino%>');"><i class="fas fa-copy"></i></a></td>
			<td style="cursor: pointer;">
				&nbsp;&nbsp;&nbsp;<%=carrera.getNombreCarrera()%>
			</td>			
			<td align="center"><%=totalRequisitos%></td>
		</tr>
  	<%	} %>
	</table>
</div>
</body>
<script type="text/javascript">
	function Copiar(carreraOrigen, carreraDestino){
		if (confirm("¿Estás seguro de copiar los requisitos de esta carrera?")){
			document.location.href="copiar?CarreraOrigen="+carreraOrigen+"&CarreraDestino="+carreraDestino;
		}
	}
</script>
</html>