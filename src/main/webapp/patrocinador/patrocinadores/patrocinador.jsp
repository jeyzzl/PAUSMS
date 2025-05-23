<%@ page import="java.util.List"%>
<%@ page import="aca.catalogo.spring.CatPatrocinador"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar(PatrocinadorId) {
		if(confirm("<spring:message code="aca.JSEliminar"/> " ) == true) {
			document.location.href = "borrarPatrocinador?PatrocinadorId="+PatrocinadorId;
		} 
	}
</script>
<%
	List<CatPatrocinador> lisPatrocinadores	= (List<CatPatrocinador>) request.getAttribute("lisPatrocinadores");
%>
<body>
<div class="container-fluid">
	<h1><spring:message code="catalogos.instituciones.Titulo"/></h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editarPatrocinador"><spring:message code="aca.Anadir"/></a>
	</div>
	
	<table class="table table-sm table-bordered">  
	<tr>
		<th width="5%"><spring:message code="aca.Operacion"/></th>
		<th width="5%"><spring:message code="aca.Numero"/></th>
		<th width="30%">Sponsorship</th>
		<th width="15%">Details</th>
		<th width="20%">Address</th>
		<th width="10%">Phone Number</th>
		<th wdith="15%">Email</th>
		<th>Type</th>
	</tr>
<%
	for (CatPatrocinador patrocinador : lisPatrocinadores){

%>
	<tr>
		<td style="text-align: left">
			<a  href="editarPatrocinador?PatrocinadorId=<%=patrocinador.getPatrocinadorId()%>" title="<spring:message code="aca.Editar"/>"><i class="fas fa-edit"></i></a>&nbsp;
	    	<a href="javascript:Borrar('<%=patrocinador.getPatrocinadorId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
		</td>
		<td align="left"><%=patrocinador.getPatrocinadorId()%></td>
		<td><%=patrocinador.getNombrePatrocinador()%></td>
		<td><%=patrocinador.getDetalles()%></td>
		<td><%=patrocinador.getDireccion()%></td>
		<td><%=patrocinador.getTelefono()%></td>
		<td><%=patrocinador.getEmail()%></td>
		<td><%=patrocinador.getTipo().equals("C")?"Corporate":"Non-corporate"%></td>
	</tr>
<%
	}				
%>
	</table>
</div>
</body>