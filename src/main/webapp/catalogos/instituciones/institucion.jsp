<%@ page import="java.util.List"%>
<%@ page import="aca.catalogo.spring.CatInstitucion"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar(InstitucionId) {
		if(confirm("<spring:message code="aca.JSEliminar"/> " ) == true) {
			document.location.href = "borrarInstitucion?InstitucionId="+InstitucionId;
		}
	}
</script>
<%
	List<CatInstitucion> lisInstituciones 	= (List<CatInstitucion>) request.getAttribute("lisInstituciones");
%>
<body>
<div class="container-fluid">
	<h1><spring:message code="catalogos.instituciones.Titulo"/></h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editarInstitucion"><spring:message code="aca.Anadir"/></a>
	</div>
	
	<table class="table table-sm table-bordered">  
	<tr>
		<th width="5%"><spring:message code="aca.Operacion"/></th>
		<th width="5%"><spring:message code="aca.Numero"/></th>
		<th width="89%">Sponsorship</th>
	</tr>
<%
	for (CatInstitucion institucion : lisInstituciones){

%>
	<tr>
		<td style="text-align: left">
			<a  href="editarInstitucion?InstitucionId=<%=institucion.getInstitucionId()%>" title="<spring:message code="aca.Editar"/>"><i class="fas fa-edit"></i></a>&nbsp;
	    	<a href="javascript:Borrar('<%=institucion.getInstitucionId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
		</td>
		<td align="left"><%=institucion.getInstitucionId()%></td>
		<td><%=institucion.getNombreInstitucion()%></td>
	</tr>
<%
	}				
%>
	</table>
</div>
</body>