<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.salida.spring.SalProposito"%>

<%@ include file="../../headPortal.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<head>	
	<script type="text/javascript">
		function Mostrar(){
			document.frmBusqueda.submit();
		}
	</script>
</head>	
<%	
	List<SalProposito> lisPropositos 		= (List<SalProposito>)request.getAttribute("lisPropositos");
	HashMap<String,String> mapaPropositos 	= (HashMap<String,String>)request.getAttribute("mapaPropositos"); 
%>
<body>
<div class="container-fluid">
	<h2>Propósitos de salidas</h2>	
	<div class="alert alert-info">
		<a href="editar" class="btn btn-primary"><i class="fas fa-plus-circle"></i> Nuevo</a>
	</div>
	<table class="table table-sm table-bordered">
	<tr class="table-info">
		<th width="5%" class="text-center">Op.</th>
		<th width="5%" class="text-center">Clave</th>
		<th width="90%">Propósito</th>					
	</tr>				
<%	 
	String usuario = "";
	for (SalProposito proposito : lisPropositos){
		String numAlta = "0";
		if (mapaPropositos.containsKey(proposito.getPropositoId())){
			numAlta = mapaPropositos.get(proposito.getPropositoId());
		}
%>
	<tr>
		<td class="text-center">
			<a href="editar?PropositoId=<%=proposito.getPropositoId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>&nbsp;
<%		if(numAlta.equals("0")){%>				
			<a href="borrar?PropositoId=<%=proposito.getPropositoId()%>" title="<spring:message code='aca.Eliminar'/>"><i class="fas fa-trash-alt"></i></a>
<%		} %>			
		</td>
		<td class="text-center"><%=proposito.getPropositoId()%></td>
		<td ><%=proposito.getPropositoNombre()%></td>	
	</tr>				
<%	} %>
	</table>
</div>
</body>