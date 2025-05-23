<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.edo.spring.EdoPeriodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<script type="text/javascript">
	function Borrar( PeriodoId ){
		if(confirm("Estas seguro de eliminar el registro: "+PeriodoId)==true){
	  		document.location="accion?Accion=4&PeriodoId="+PeriodoId;
	  	}
	}	
</script>
<%
	List<EdoPeriodo> lisPeriodos	 		= (List<EdoPeriodo>) request.getAttribute("lisPeriodos");
	HashMap<String,String> mapaEvaluaciones = (HashMap<String,String>) request.getAttribute("mapaEvaluaciones");
%>
<div class="container-fluid">
	<h2>Listado de Períodos de Evaluación</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="accion?Accion=1"><spring:message code='aca.Añadir'/></a>
	</div>
	<table class="table table-sm table-bordered">
	<thead class="table-info">
	<tr>
  		<th width="10%" class="text-center"><spring:message code="aca.Operacion"/></th>
  		<th width="5%" class="text-center"><spring:message code="aca.Numero"/></th>
  		<th width="5%" align="center">Periodo</th>
  		<th width="30%"><spring:message code="aca.Nombre"/></th>    
  		<th width="15%">Inicio</th>
  		<th width="15%">Fin</th> 
  		<th width="10%" align="center"><spring:message code="aca.Estado"/></th>
  		<th width="10%" class="text-center">#Eval.</th>
	</tr>
	</thead>
	<tbody>
<%
	for (int i=0; i< lisPeriodos.size(); i++){
		EdoPeriodo periodo = (EdoPeriodo) lisPeriodos.get(i);
		String numEval = "0";
		if (mapaEvaluaciones.containsKey( periodo.getPeriodoId()) ){
			numEval = mapaEvaluaciones.get( periodo.getPeriodoId());
		}
%>		
	<tr>
  		<td align="center">
	  		<a href="accion?Accion=5&PeriodoId=<%= periodo.getPeriodoId() %>"><i class="fas fa-edit"></i></a>
<%		if(numEval.equals("0")){%>	  		
	  		<a class="fas fa-trash-alt" href="javascript:Borrar('<%=periodo.getPeriodoId()%>')"></a>
<% 		}%>	  		
		</td>
    	<td align="center"><%=i+1%></td>
    	<td align="center"><%= periodo.getPeriodoId() %></td>
    	<td><%=periodo.getPeriodoNombre() %></td>	
		<td><%= periodo.getfInicio() %></td>
		<td> <%= periodo.getfFinal() %></td>	
		<td align="center"><%=periodo.getEstado().equals("A")?"Activa":"Cerrada"%></td>	
		<td class="text-center"><%=numEval%></td>
	</tr>  
<% 	} %>  
	</tbody>
	</table>
<!-- fin de estructura -->
</div>