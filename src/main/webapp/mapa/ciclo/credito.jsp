<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.plan.spring.MapaCredito"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<script>
	function Grabar(){		
		document.frmcreditos.submit();		
	}	
</script>
<!-- inicio de estructura -->
<%	
	String planId			= request.getParameter("planId");
	String facultad			= request.getParameter("facultad");
	int numCiclos			= (int)request.getAttribute("numCiclos");
	String carreraNombre	= (String)request.getAttribute("carreraNombre");
	String titulo			= "";
	String mensaje 			= request.getParameter("mensaje")==null?"-":request.getParameter("mensaje");
	
	HashMap<String,MapaCredito> mapaCreditos = (HashMap<String,MapaCredito>) request.getAttribute("mapaCreditos");
	HashMap<String,String> mapaCursos		 = (HashMap<String,String>)request.getAttribute("mapaCursos");

%>
<div class="container-fluid">
	<h3><%=carreraNombre%> &nbsp;&nbsp; <small class="text-secondary"><b>Plan : <i><%= planId %></i></b></small></h3>
	<form name="frmcreditos" method="post"  action="grabar">
	<div class="alert alert-info">
		<a class="btn btn-primary " href="listado?facultad=<%=facultad%>" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
	</div>		
	<table class="table table-bordered table-sm">
	<thead class="table-info">	
	<tr>
		<th><spring:message code="aca.Op"/></th>
		<th class="text-center">Checked</th>
	    <th class="text-center"><spring:message code="aca.Semestre"/></th>
	    <th>Cycle <spring:message code="aca.Nombre"/></th>
	    <th><spring:message code="aca.Status"/></th>
	    <th><spring:message code="mapa.ciclo.NumCursos"/></th>
		</tr>
	</thead>
<%	
	for(int i = 1; i <= numCiclos; i++){
		titulo = "";
		String estado = "";
		boolean existe = false;
		if (mapaCreditos.containsKey(planId+String.valueOf(i))){
		  titulo = mapaCreditos.get(planId+String.valueOf(i)).getTitulo();
		  estado = mapaCreditos.get(planId+String.valueOf(i)).getEstado();
		  existe = true;
		}
		String cursos = "-";
		if(mapaCursos.containsKey(String.valueOf(i))){
			cursos = mapaCursos.get(String.valueOf(i));
		}		
%>
	<tr>
		<td>
	<%	if(existe && cursos.equals("0")){	%>
			<a href="borrar?PlanId=<%=planId%>&Ciclo=<%=String.valueOf(i)%>&facultad=<%=facultad%>" title="Eliminar"><i class="fas fa-trash" ></i></a>
	<%	}else{	%>
			&nbsp;
	<%	} %>	
		</td>
		<td class="text-center">
	<% 	if(existe){	%>
			<i class="fas fa-check-square fa-1x"></i>
	<% 	}else{	%>
			<i class="fas fa-times-circle fa-1x"></i>
	<% 	}		%>			
		</td>
		<td class="text-center"><%=i%></td>			
		<td class="text-start"><input name="titulo<%=i%>" type="text" class="form-control" value="<%=titulo%>" size="30" maxlength="70"></td>
		<td>
			<select name="Estado<%=i%>" id="Estado" class="form-select">
				<option value="O" <%if(estado.equals("O"))out.print("selected");%>>Official</option>
				<option value="A" <%if(estado.equals("A"))out.print("selected");%>>Aditional</option>
			</select>
		</td>	
		<td><span class="badge bg-info"><%=cursos%></span></td>
	</tr>
<%	} %>			
	</table>	
	<div class="alert alert-info d-flex">
		<input class="btn btn-primary" type="button" name="Submit" value="<spring:message code='aca.Grabar'/>" onClick="javascript:Grabar();"> &nbsp;&nbsp;
		<p><%=!mensaje.equals("-")?mensaje:""%></p>
		<input name="planId" type="hidden" id="planId" value="<%=planId%>">
		<input name="facultad" type="hidden" id="facultad" value="<%=facultad%>">			      
	</div>
	</form>		
</div>
<!-- fin de estructura -->