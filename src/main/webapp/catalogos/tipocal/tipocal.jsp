<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatTipoCal"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar(tipocalId) {
		if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
			document.location = "borrarTipo?TipoCalId=" + tipocalId;
		}
	}
</script>

<%
	String mensaje 		= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");

	List<CatTipoCal> lisTipos  			= (List<CatTipoCal>) request.getAttribute("lisTipos");
	HashMap<String,String> mapaCatUsadas	= (HashMap<String,String>) request.getAttribute("mapaCatUsadas");
%>
<div class="container-fluid">
	<h1><spring:message code="catalogos.tipoCalif.Titulo"/></h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editarTipo"><spring:message code="aca.Anadir"/></a>&nbsp;<%=mensaje.equals("-")?"":mensaje%>
	</div>
	<table class="table table-sm table-bordered">  
	<tr>
		<th width="10%"><spring:message code="aca.Operacion"/></th>
		<th width="10%"><spring:message code="aca.Clave"/></th>
		<th width="60%"><spring:message code="catalogos.tipoCalif.Tipo"/></th>
		<th width="10%">Short name</th>
		<th width="10%">#Reg.</th>
	</tr>
<%		
	for(CatTipoCal tipo : lisTipos){
		String total = "0";
		if (mapaCatUsadas.containsKey(tipo.getTipoCalId())){
			total 	= mapaCatUsadas.get(tipo.getTipoCalId());
		}
%>
	<tr>
		<td style="text-align: center">
			<a href="editarTipo?TipoCalId=<%=tipo.getTipoCalId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>
			&nbsp;
<%			if(total.equals("0")){ %>
			<a href="javascript:Borrar('<%=tipo.getTipoCalId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
<%			} %>
		</td>
		<td align="center"><%=tipo.getTipoCalId()%></td>
		<td><%=tipo.getNombreTipoCal()%></td>
		<td><%=tipo.getNombreCorto()%></td>
		<td class="text-end"><%=total.equals("0")?"<span class='badge bg-warning rounded-pill'>0</span>":"<span class='badge bg-dark rounded-pill'>"+total+"</span>"%></td>
	</tr>
<%
	}	
%>
	</table>
</div>