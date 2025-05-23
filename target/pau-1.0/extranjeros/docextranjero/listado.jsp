<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.leg.spring.LegExtdoctos"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Borrar(codigo, idDocumento){
		if(confirm("Esta seguro que desea eliminar el registro!")){
			document.location="borrar?Codigo="+codigo+"&IdDocumento="+idDocumento;
		}
	}
</script>

<%
	String codigoAlumno 		= (String) request.getAttribute("codigoAlumno");
	String nombreNacionalidad	= (String) request.getAttribute("nombreNacionalidad");
	String sBgcolor				= "";
	String estatus				= (String) request.getAttribute("estatus");
	boolean esExtranjero		= (boolean) request.getAttribute("esExtranjero");
	String nombreAlumno			= (String) request.getAttribute("nombreAlumno");
	String nombreCarrera		= (String) request.getAttribute("nombreCarrera");
	
	List<LegExtdoctos> lisExtdoctos 		= (List<LegExtdoctos>) request.getAttribute("lisExtdoctos");
	HashMap<String, String> mapaDocumentos 	= (HashMap<String, String>) request.getAttribute("mapaDocumentos");
	HashMap<String, String> mapaEstados 	= (HashMap<String, String>) request.getAttribute("mapaEstados");
%>
<div class="container-fluid">
<h3>Foreigner Documents<small class="text-muted fs-4"> ( [ <%=codigoAlumno%> ] - <%=nombreAlumno%>)</small></h3>
<div class="alert alert-info">
	<a class="btn btn-primary" href="editar"><spring:message code="aca.Anadir"/></a>
	<strong>&nbsp;&nbsp;Degree Act: </strong><%=nombreCarrera%>
    <strong>&nbsp;Country: </strong><%=nombreNacionalidad%>
    <strong>&nbsp;Status: </strong><%=estatus%>
</div>
<%
	if(esExtranjero){
%>
<table class="table table-fullcondensed table-nobordered">
  <tr> 
    <th width="10%"><spring:message code="aca.Operacion"/></th>
    <th width="5%"><spring:message code="aca.Numero"/></th>
    <th width="10%"><spring:message code="aca.Fecha"/></th>
    <th width="35%"><spring:message code="aca.Documento"/></th>
    <th width="10%">N° Docto.</th>
    <th width="10%"><spring:message code="aca.Estado"/></th>    
    <th width="10%">Expiration Date</th>
    <th width="10%">Inquiry Date</th>
  </tr>
<%
		int i = 0;
		for(LegExtdoctos leg : lisExtdoctos){
			i++;
			String documento = "-";
			if(mapaDocumentos.containsKey(leg.getIdDocumento())){
				documento = mapaDocumentos.get(leg.getIdDocumento());
			}
			String estado = "-";
			if(mapaEstados.containsKey(leg.getEstado())){
				estado = mapaEstados.get(leg.getEstado());
			}
			
			if (leg.getFechaVence()==null) leg.setFechaVence("");
		
%>

    <td width="10%" align="center">
    	<a href="editar?IdDocumento=<%=leg.getIdDocumento()%>&Codigo=<%=codigoAlumno%>" class="fas fa-edit" title="Editar"></a>&nbsp;
    	<a href="javascript:Borrar('<%=codigoAlumno%>','<%=leg.getIdDocumento()%>')" class="fas fa-trash-alt" title="Borrar"></a>
    </td>
    <td align="center"><%=leg.getIdDocumento()%></td>
    <td align="center"><%=leg.getFecha()%></td>    
    <td><%=documento%></a></td>
    <td align="left"><%=leg.getNumDocto()%></td>
    <td align="left"><%=estado%></td>
    <td align="center"><%=leg.getFechaVence()%></td>
    <td align="center"><%=leg.getFechaTramite()%></td>
  </tr>
<%		}	%>
</table>
</div>
<%	}else{%>
<div><h4>This student is not a foreigner..!</h4></div>
<%	}%>