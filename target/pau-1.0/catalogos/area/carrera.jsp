<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar( facultadId, carreraId ){
		if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
	  		document.location="borrarCarrera?FacultadId="+facultadId+"&CarreraId="+carreraId;
	  	}
	}
</script>

<%
	String areaId			= (String)session.getAttribute("carreraId");
	String facultadId		= (String)session.getAttribute("facultadId");
	String fechaHoy 		= aca.util.Fecha.getHoy();
	
	// Lista de carreras
	List<CatCarrera> lisCarreras				= (List<CatCarrera>)request.getAttribute("lisCarreras");
	HashMap<String,String> mapaCoordinadores	= (HashMap<String,String>) request.getAttribute("mapaCoordinadores");
	HashMap<String,String> mapaFunciones		= (HashMap<String,String>) request.getAttribute("mapaFunciones");
	HashMap<String,String> mapaPlanesPorCarrera	= (HashMap<String,String>) request.getAttribute("mapaPlanesPorCarrera");
%>

<link rel="stylesheet" href="../../js/jtip/css/jtip.css">
<script src='../../js/jtip/js/jtip.js'></script>
<div class="container-fluid">
	<h2><spring:message code="catalogos.area.Titulo5"/></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="facultad"><i class="fas fa-arrow-left"></i></a>
		<a class="btn btn-success" href="editarCarrera"><spring:message code="aca.Anadir"/></a>
	</div>
	<table style="margin: 0 auto; style="text-align: left"  width:80%" class="table">  
  	<tr> 
    	<th><spring:message code="aca.Operacion"/></th>
    	<th><spring:message code="aca.Numero"/></th>
    	<th><spring:message code="catalogos.area.Carreras"/></th>
    	<th><spring:message code="catalogos.area.Coordinadores"/></th>
<%--     	<th><spring:message code="catalogos.area.CentroDeCosto"/></th> --%>
    	<th><spring:message code="catalogos.area.Funcion"/></th>
  	</tr>
  <%

	for (CatCarrera carrera : lisCarreras){
		String ccosto = "";
		String numPlanes = "0";
		
		String coordinador = "";
  		if (mapaCoordinadores.containsKey(carrera.getCodigoPersonal())){
  			coordinador 	= mapaCoordinadores.get(carrera.getCodigoPersonal());
  		}	
  		
  		String funcion = "";
  		if (mapaFunciones.containsKey(carrera.getCcostoId())){
  			funcion 	= mapaFunciones.get(carrera.getCcostoId());
  		}
  		
  		if(mapaPlanesPorCarrera.containsKey(carrera.getCarreraId())){
  			numPlanes = mapaPlanesPorCarrera.get(carrera.getCarreraId());
  		}
  		
%>	
	<tr class="tr2"> 
		<td style="text-align: left">
			<a href="editarCarrera?CarreraId=<%=carrera.getCarreraId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>&nbsp;
<%
	if(numPlanes.equals("0")){
%>			
			<a href="javascript:Borrar ('<%=carrera.getFacultadId()%>','<%=carrera.getCarreraId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
<%	
	}	
%>
		</td>
		<td align="left"><%=carrera.getCarreraId()%></td>
		<td>&nbsp;&nbsp;&nbsp;<%=carrera.getNombreCarrera()%></td>
		<td title="<%=carrera.getCodigoPersonal()%>"><%=coordinador%></td>
<%-- 		<td class="jtip" data-position="left" data-text="<%=ccosto%>"><%=carrera.getCcostoId()%></td> --%>
		<td align="left"><%=funcion%></td>
	</tr>
<%	
	}	
%>
</table>
</div>