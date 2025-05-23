<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.carga.spring.Carga"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar( cargaId ){
		if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
	  		document.location="borrarCarga?CargaId="+cargaId;
	  		
	  	}
	}
</script>
<%		
	System.out.println("Vista 1");
	List<Carga> lisCargas 				= (List<Carga>) request.getAttribute("lisCargas");
	HashMap<String,String> mapaBloques 	= (HashMap<String,String>)request.getAttribute("mapaBloques");

	String sBgcolor			= "";
%>

<div class="container-fluid">
	<h2><spring:message code="cargaGrupo.Titulo"/></h2> 		
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editarCarga"><spring:message code="aca.Anadir"/></a>
	</div>
	<table class="table table-bordered">
	<thead class="table-info">
   	<tr>
		<th>Op.</th>
	  	<th><spring:message code="aca.Numero"/></th>
	  	<th><spring:message code="aca.Carga"/> Key</th>
	  	<th><spring:message code="aca.Nombre"/></th>
	  	<th class="text-center">Blocks</th>
	    <th><spring:message code="aca.Ciclo"/></th>
	    <th><spring:message code="aca.Inicio"/></th>
	    <th><spring:message code="aca.Fin"/></th>
		<th>Remedial</th>
		<th class="text-center"><spring:message code="aca.Status"/></th>
		<th class="text-center">Evaluated</th>
		<th class="text-center">Student Access</th>
	</tr>	
	</thead>
<%
	int row=0;
	for (Carga carga : lisCargas){
		row++;
		
		String total = "0";
		if (mapaBloques.containsKey(carga.getCargaId())){
			total = mapaBloques.get(carga.getCargaId());
		}
%>				
	<tr>
  		<td align="center">
  			<a class="fas fa-edit" title="<spring:message code="aca.Editar"/>"  href="editarCarga?CargaId=<%=carga.getCargaId()%>" ></a>&nbsp;
<% 		if (total.equals("0")){ %>  			
	    	<a class="fas fa-trash-alt" title="<spring:message code="aca.Eliminar"/>" href="javascript:Borrar('<%=carga.getCargaId()%>')" ></a>
<% 		} %>	
		</td>
    	<td align="center">	  
	  		<%=row%>
		</td>
	    <td align="center"><%=carga.getCargaId()%></td>
	    <td><%=carga.getNombreCarga()%></a></td>
	     <td align="center"><a href="bloques?CargaId=<%=carga.getCargaId() %>"><%=total.equals("0")?"<span class='badge bg-warning rounded-pill'>"+total+"</span>":"<span class='badge bg-dark rounded-pill'>"+total+"</span>"%></a></td>
		<td><%=carga.getPeriodo()%></td>
		<td><%=carga.getFInicio()%></td>
		<td><%=carga.getFFinal()%></td>
		<td><%=carga.getFExtra()%></td>
		<td align="center">
	<%	if (carga.getEstado().equals("1")) {%>
			<spring:message code="aca.Activa"/>
	<%	}
		else{%> 
			<spring:message code="aca.Cerrada"/>
	<%	}
	%>
		</td>		
		<td align="center">
		<% if (carga.getEvalua().equals("S")){%>
			YES
		<% }else{%>	
			<spring:message code='aca.NO'/>
		<%	} %>
		</td>
		<td class="text-center"><%=carga.getBloqueo().equals("0")?"Open":"Restricted"%></td>
  	</tr>  
<%  
	}
%>
	</table>
</div>