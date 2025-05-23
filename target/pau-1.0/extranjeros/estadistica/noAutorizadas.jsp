<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatPais"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.vista.spring.Estadistica"%>
<%@ page import= "aca.leg.spring.LegExtranjero"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String modalidades 			= (String) session.getAttribute("modalidadesReportes");	
	String color				= "";
	int contador				= 0;
	
	List<Estadistica> lisExtranjeros				= (List<Estadistica>) request.getAttribute("lisExtranjeros");	
	HashMap<String,CatPais> mapaPaises				= (HashMap<String,CatPais>) request.getAttribute("mapaPaises");
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,LegExtranjero> mapaExtranjeros	= (HashMap<String,LegExtranjero>)request.getAttribute("mapaExtranjeros");
%>

<div class="container-fluid">
	<h3>Extranjeros con diferentes carreras</h3>
	<div class="alert alert-info">
		<a href="elegir" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>

	<table class="table table-bordered table-striped">
  	<form name="formCarrera" method="post" action="">
    <tr> 
    	<th width="5%"><b><font face="Arial, Helvetica, sans-serif" size="2"><spring:message code="aca.Numero"/></font></b></th>
    	<th width="6%"><b><font face="Arial, Helvetica, sans-serif" size="2"><spring:message code="aca.Mat"/></font></b></th>
    	<th width="28%"><b><font face="Arial, Helvetica, sans-serif" size="2"><spring:message code="aca.Nombre"/></font></b></th>
    	<th width="14%"><b><font face="Arial, Helvetica, sans-serif" size="2"><spring:message code="aca.Pais"/></font></b></th>
		<th width="26%"><b><font face="Arial, Helvetica, sans-serif" size="2">Carrera Inscrito</font></b></th>
    	<th width="25%"><b><font face="Arial, Helvetica, sans-serif" size="2">Carrera FM3</font></b></th>
    </tr>
<%
	for(Estadistica estadistica : lisExtranjeros){
		
		String carreraNombre 	= "-";
		if (mapaCarreras.containsKey(estadistica.getCarreraId())){
			carreraNombre 		= mapaCarreras.get(estadistica.getCarreraId()).getNombreCarrera();
		}
		
		String carreraFM3 		= "0";
		String carreraNombreFM3	= "-";
		LegExtranjero extranjero = new LegExtranjero();
		if (mapaExtranjeros.containsKey(estadistica.getCodigoPersonal())){
			extranjero 	= mapaExtranjeros.get(estadistica.getCodigoPersonal());
			carreraFM3 	= extranjero.getCarrera();
			if (mapaCarreras.containsKey(carreraFM3)){
				carreraNombreFM3 = mapaCarreras.get(carreraFM3).getNombreCarrera();
			}
		}			
			
		String paisNombre = "-";
		if(mapaPaises.containsKey(estadistica.getNacionalidad())){
			paisNombre = mapaPaises.get(estadistica.getNacionalidad()).getNombrePais();
		}			
		
	
%>
	<tr>
		<td><%=contador%></td>
		<td><%=estadistica.getCodigoPersonal()%></td>
		<td><%=estadistica.getApellidoPaterno()%> <%=estadistica.getApellidoMaterno()%> <%=estadistica.getNombre()%></td>
		<td><%=paisNombre%></td>
		<td><%=carreraNombre%></td>
		<td><%=carreraNombreFM3%></td>
	</tr>
<%
	}		
%>
  </form>
</table>
</div>