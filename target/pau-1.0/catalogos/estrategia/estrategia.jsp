<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatEstrategia"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar( estrategiaId ){
		if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
	  		document.location="borrarEstrategia?EstrategiaId="+estrategiaId;
	  	}
	}
</script>
<%
	List<CatEstrategia> lisEstrategias	= (List<CatEstrategia>)request.getAttribute("lisEstrategias");
	HashMap<String,String> mapaUsadas	= (HashMap<String,String>)request.getAttribute("mapaUsadas");
%>
<div class="container-fluid">
	<h2><spring:message code="catalogos.estrategias.Titulo"/></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editarEstrategia"><spring:message code="aca.Anadir"/></a>	
	</div>
	<table class="table table-sm table-bordered">
	<thead class="table-dark">  
  	<tr> 
  		<th width="5%"><spring:message code="aca.Operacion"/></th>
  		<th width="10%"><spring:message code="aca.Numero"/></th>
  		<th width="75%"><spring:message code="catalogos.estrategias.Estrategias"/></th>
  		<th width="10%" class="text-end">#Reg.</th>
  	</tr>
  	</thead>
  	<tbody>
<%	
	for(CatEstrategia estrategia : lisEstrategias){
		String numUsada 	= "0";
		String textUsada 	= "<span class='badge bg-warning rounded-pill'>0</span>"; 
		if (mapaUsadas.containsKey(estrategia.getEstrategiaId())){
			numUsada = mapaUsadas.get(estrategia.getEstrategiaId());
			textUsada 	= "<span class='badge bg-dark rounded-pill'>"+numUsada+"</span>";
		}
%>  
	<tr> 
		<td style="text-align: center">
			<a title="<spring:message code="aca.Modificar"/>" href="editarEstrategia?EstrategiaId=<%=estrategia.getEstrategiaId()%>"><i class="fas fa-edit"></i></a>
<%		if (numUsada.equals("0")){%>			
			<a title="<spring:message code="aca.Eliminar"/>" href="javascript:Borrar('<%=estrategia.getEstrategiaId()%>')"><i class="fas fa-trash-alt" style="color:red"></i></a>
<%		} %>	  
		</td>
    	<td align="center" readonly><%=estrategia.getEstrategiaId()%></td>
    	<td><%=estrategia.getNombreEstrategia()%></td>
    	<td class="text-end"><%=textUsada%></td>
  	</tr>
<%	} %>  
	</table>
</div>