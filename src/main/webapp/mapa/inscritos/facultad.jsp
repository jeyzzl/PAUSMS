<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.carga.spring.Carga"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%  
	String periodoId						= (String)request.getAttribute("periodoId");
	String cargaId							= (String)request.getAttribute("cargaId");
	List<CatPeriodo> lisPeriodos 			= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 					= (List<Carga>) request.getAttribute("lisCargas");
	List<CatFacultad> lisFacultades 		= (List<CatFacultad>) request.getAttribute("lisFacultades");	
	HashMap<String,String> mapaDirectores	= (HashMap<String,String>) request.getAttribute("mapaDirectores");
%>
<div class="container-fluid">
	<h1><spring:message code="aca.Facultades"/></h1>
	<form name="frmCarga" action="facultad" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<spring:message code="aca.Periodo"/>:&nbsp;
		<select name="PeriodoId"   class="form-select" onchange="document.frmCarga.submit();" style="width:200px;">		
<%	for (CatPeriodo periodo: lisPeriodos){%>
			<option value="<%=periodo.getPeriodoId()%>" <%=periodoId.equals(periodo.getPeriodoId())?"selected":""%>><%=periodo.getPeriodoId()%>-<%=periodo.getNombre()%></option>
<%	} %>			
		</select>
		&nbsp;&nbsp;&nbsp;
		<spring:message code="aca.Carga"/>:
		&nbsp;
		<select name="CargaId"  class="form-select" onchange="document.frmCarga.submit();" style="width:335px;">		
<%	for (Carga carga: lisCargas){%>
			<option value="<%=carga.getCargaId()%>" <%=cargaId.equals(carga.getCargaId())?"selected":""%>><%=carga.getCargaId()%>-<%=carga.getNombreCarga()%></option>
<%	} %>
		</select>
	</div>
	</form>
 	<table  class="table table-bordered table-stiped">
	<thead>
	<tr> 
		<th class="table-info" width="5%"><spring:message code="aca.Numero"/></th>
		<th class="table-info" width="10%"><spring:message code="aca.Corto"/></th>
		<th class="table-info"width="35%"><spring:message code="aca.Facultad"/></th>
		<th class="table-info"width="10%"><spring:message code="aca.Clave"/></th>
		<th class="table-info"width="20%"><spring:message code="aca.Director"/></th>
	</tr>
	</thead>
	<tbody>
<%
	for (CatFacultad facultad : lisFacultades){
		
		String nombreEmpleado = "";
		if (mapaDirectores.containsKey(facultad.getCodigoPersonal())){
			nombreEmpleado = mapaDirectores.get(facultad.getCodigoPersonal());
		}
%>
  	<tr>
	    <td align="left"><%=facultad.getFacultadId()%></font></td>
	    <td align="left"><%=facultad.getNombreCorto()%></font></td>
	    <td align="left"><a href="listado?facultad=<%=facultad.getFacultadId()%>"><%=facultad.getNombreFacultad()%></a></td>
	    <td align="left"><%=facultad.getCodigoPersonal()%></font></td>
	    <td align="left"><%=nombreEmpleado%></td>
	 </tr>
<%
	}
%> 
	</tbody>
	</table>			
</div>