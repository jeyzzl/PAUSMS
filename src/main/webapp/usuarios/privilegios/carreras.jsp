<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.acceso.spring.Acceso"%>
<%@ page import= "aca.acceso.spring.AccesoPlan"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>
<%@ page import= "aca.alumno.AlumUtil"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Grabar(){
		document.frmaccesos.submit();
	}
</script>
<%
	List<MapaPlan> lisPlanes							= (List<MapaPlan>) request.getAttribute("lisPlanes");
	List<AccesoPlan> lisPorUsuario 						= (List<AccesoPlan>)request.getAttribute("lisPorUsuario");

	HashMap<String, CatFacultad> mapaFacultadNombre		= (HashMap<String, CatFacultad>) request.getAttribute("mapaFacultadNombre");
	HashMap<String, CatCarrera> mapaCarreraNombre		= (HashMap<String, CatCarrera>) request.getAttribute("mapaCarreraNombre");
	HashMap<String,String> mapaPracticasCarreras	= (HashMap<String,String>) request.getAttribute("mapaPracticasCarreras");
	HashMap<String,CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");

	String usuarioNombre	= (String) request.getAttribute("usuarioNombre");
	String codigoUsuario	= request.getParameter("Codigo");				
	String checkOk			= "";
%>
<div class="container-fluid">
	<h2>Career Privileges<small class="text-muted fs-5">( <%=codigoUsuario%> - <%= usuarioNombre%> )</small></h2>
	<div class="alert alert-info  d-flex">	
		<a class="btn btn-primary" href="usuario?Codigo=<%=codigoUsuario%>">Return</a>&nbsp;&nbsp;
		  <input type="text" class="form-control search-query" placeholder="Search..." id="buscar" style="width:250px;">&nbsp;&nbsp;
	</div>
	<form name="frmaccesos" method="post" action="grabarCarreras">
	<input name="Codigo" type="hidden" value="<%=codigoUsuario%>">		    	 
	<table class="table table-sm table-bordered" style="width:100%">        	
    <tr class="table-info">
       	<th width="9%" height="23" align="center"><h5><b><spring:message code="aca.Elegir"/></b></h5></th>
       	<th width="10%" align="center"><h5><b><spring:message code="aca.Carrera"/></b></h5></th>
	   	<th width="41%" align="center"><h5><b><spring:message code="aca.Nombre"/></b></h5></th>
	   	<th width="41%" align="center"><h5><b>Plans</b></h5></th>
    </tr>
<%			
	       int row = 0;
	String facTemp = "X";
	for (MapaPlan mapaPlan : lisPlanes){
		row++;
		if(lisPorUsuario.contains(mapaPlan.getPlanId())){
			checkOk = "checked";
		}else{
			checkOk = "";
		}
		String facultadId		= "X";
		String facultadNombre 	= "-";
		if (mapaCarreras.containsKey(mapaPlan.getCarreraId())){
			facultadId = mapaCarreras.get(mapaPlan.getCarreraId()).getFacultadId();
			if (mapaFacultades.containsKey(facultadId)){
				facultadNombre = mapaFacultades.get(facultadId).getNombreFacultad();
			}
		}			
		String estilo = "<span class='badge bg-secondary rounded-pill'>"+mapaPlan.getPlanId()+"</span>";
		if (mapaPlan.getEstado().equals("A")) estilo = "<span class='badge bg-info rounded-pill'>"+mapaPlan.getPlanId()+"</span>";
		if (mapaPlan.getEstado().equals("V")) estilo = "<span class='badge bg-success rounded-pill'>"+mapaPlan.getPlanId()+"</span>";
		if (mapaPlan.getEstado().equals("I")) estilo = "<span class='badge bg-warning rounded-pill'>"+mapaPlan.getPlanId()+"</span>";
		if (!facTemp.equals(facultadId)){
			facTemp = facultadId;
			out.print("<tr class='table-dark' align='center'><td colspan='4'>"+facultadNombre+"</td></tr>");
		}
%>
	<tr>
		<td class="text-center">
			<%=row%>
		</td>
		<td align="center">
			<input name="Check<%=mapaPlan.getPlanId()%>" type="checkbox" value="S" <%=checkOk%>>
		</td>				
		<td class="text-center">											
			<span style="text-decoration:none;"><%=estilo%></span>				
		</td>
		<td class="text-start">
			<%=mapaPlan.getCarreraSe()%>
		</td>
	</tr>
<%	}	%>     
	</table>
	<div class="alert alert-info d-flex">
	   <tr> 
 			<td height="27" colspan="3"> 
			<input name="Aceptar" type="button" id="Aceptar" class="btn btn-primary"value="Save" onClick="javascript:Grabar()"></td>
    	</tr>
    </div>	
	</form>	
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	$('#buscar').search();	
</script>