<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.rotaciones.spring.RotInstitucion"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	List<RotInstitucion> instituciones 			= (List<RotInstitucion>) request.getAttribute("instituciones");
	HashMap<String,String> mapaHospitales		= (HashMap<String,String>) request.getAttribute("mapaHospitales");

	String mensaje						= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<head>
	<script type="text/javascript">
		function borrar(institucionId){
			if(confirm("¿Está seguro que desea eliminar la institución?")){
				document.location.href = 'borrarInstitucion?InstitucionId='+institucionId;
			}
		}		
	</script>
</head>
<div class="container-fluid">
	<h1>Catálogo de Instituciones</h1>
	<form name="forma" action="institucion" methos="post">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editar"><spring:message code='aca.Nuevo'/></a>
	</div>		
<%	
	if (!mensaje.equals("-")){
		out.print("<div class='alert alert-info'>"+mensaje+"</div>");		
	}	
%>	
		
	<table   class="table table-condensed" width="100%">
	<thead>
		<tr>
			<th>Acción</th>
			<th>Id</th>
			<th><spring:message code="aca.Nombre"/> de la Instución</th>
 			<th>Hospitales</th>
		</tr>
	</thead>	
<%
	int row = 0;
	for(RotInstitucion inst: instituciones){
		row++;
		
		String total = "0";
		if (mapaHospitales.containsKey(inst.getInstitucionId())){
			total = mapaHospitales.get(inst.getInstitucionId());
		}
%>		
	<tbody>
		<tr>
			<td>
				<a href="editar?InstitucionId=<%=inst.getInstitucionId()%>" title="<spring:message code="aca.Modificar"/>"> <i class = "fas fa-pencil-alt" ></i></a>
				&nbsp;&nbsp;&nbsp;
<%		if(total.equals("0")){ %>		
				<a href="javascript:borrar('<%=inst.getInstitucionId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash" ></i></a>
<%		} %>			
			</td>
			<td class="id"><%=inst.getInstitucionId() %></td>
			<td class="name"><%=inst.getInstitucionNombre() %></td>
			<td><%=total%></td>
		</tr>	
<%	} %>
		</table>
	</tbody>	
	</form>
</div>
