<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar( FacultadId ){
		if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
	  		document.location="borrarFacultad?FacultadId="+FacultadId;
	  	}
	}
</script>
<%
	String areaId 		= (String)session.getAttribute("areaId");
	// Lista de facultades
	List<CatFacultad> lisFacultades			= (List<CatFacultad>) request.getAttribute("lisFacultades"); 
	HashMap<String,String> mapaDirectores			= (HashMap<String,String>) request.getAttribute("mapaDirectores");
	HashMap<String,String> mapaCarrerasPorFacultad	= (HashMap<String,String>) request.getAttribute("mapaCarrerasPorFacultad");
%>
<div class="container-fluid">
	<h1><spring:message code="catalogos.area.Titulo3"/></h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="areas"><i class="fas fa-arrow-left"></i></a>
		<a class="btn btn-success" href="editarFacultad"><spring:message code="aca.Anadir"/></a>
	</div>
	<table class="table table-sm" style="margin: 0 auto; style="text-align: left"  width:60%">  
  	<tr> 
	    <th width="5%"><spring:message code="aca.Operacion"/></th>
	    <th><spring:message code="aca.Numero"/></th>
	    <th><spring:message code="aca.Facultad"/></th>
	    <th><spring:message code="catalogos.area.Directores"/></th>
  	</tr>
  <%
  	String numCarreras = "0";
  	for (CatFacultad facultad : lisFacultades){
  		
  		String director = "";
  		if (mapaDirectores.containsKey(facultad.getCodigoPersonal())){
  			director = mapaDirectores.get(facultad.getCodigoPersonal());
  		}
  		
  		if(mapaCarrerasPorFacultad.containsKey(facultad.getFacultadId())){
  			numCarreras = mapaCarrerasPorFacultad.get(facultad.getFacultadId());
  		}
%>
  	<tr class="tr2"> 
    	<td style="text-align: center"> 
    		<a href="editarFacultad?FacultadId=<%=facultad.getFacultadId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>&nbsp;
<%
	if(numCarreras.equals("0")){
%>      	
      	<a href="javascript:Borrar('<%=facultad.getFacultadId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
<%
	}	
%>
      	</td>
	    <td align="center"><%=facultad.getFacultadId()%></td>
	    <td>
	    	<a href="carrera?FacultadId=<%=facultad.getFacultadId()%>&AreaId=<%=areaId%>"><%=facultad.getNombreFacultad()%>
	    	</a>
	    </td>
	    <td title="<%=facultad.getCodigoPersonal() %>"><%=director%></td>
  	</tr>
<%
	}	
%>
	</table>
</div>