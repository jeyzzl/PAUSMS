<%@ include file="../../con_enoc.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../academico.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />
<script src="typeahead/jquery.js"></script>

<jsp:useBean id="AdminTareas" scope="page" class="aca.util.AdminTareas"/>
<jsp:useBean id="AdminTareasU" scope="page" class="aca.util.AdminTareasUtil"/>
<script>
	function Borrar(folio){
		if(confirm("Esta seguro que desea eliminar esta tarea?")==true){
			location.href="tareas?folio="+folio+"&Accion=1";
		}
	}
</script>
<%
	String sistema = request.getParameter("sistema")==null?"academico":request.getParameter("sistema");
	
	String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");
	
	if(accion.equals("1")){
		AdminTareas.setFolio(request.getParameter("folio"));
		AdminTareas.deleteReg(conEnoc);
	}
	
	ArrayList<aca.util.AdminTareas> tareas = AdminTareasU.getListAll(conEnoc, "WHERE MODULO = '"+sistema+"' ORDER BY TO_DATE(F_CREA, 'DD/MM/YYYY') DESC, FOLIO DESC");
	
%>
<style>
	h6{
		text-transform: capitalize;
	}
	.descripcion{
		display: none;
	}
	.Inactivo td{
		border: 1px solid #DB8080;
	}
	.Activo td{
		border: 1px solid #D9D560;
	}
	.Terminado td{
		border: 1px solid #6DC26A;
	}
</style>
<table style="width:80%; margin:0 auto;">
	<tr>
		<td align="center">
			<h2>Tareas del Departamento de Sistemas UM</h2>
			<a class="btn btn-primary" href="add">Añadir Tarea</a>
		</td>
	</tr>
</table>
<table style="margin: 0 auto;  width:95%">
	<tr>
		<td>
			<ul class="nav nav-pills" style="float:right;margin:0;padding:0;">
			  <li class="<%if(sistema.equals("academico"))out.print("active"); %>">
			    <a href="tareas?sistema=academico"><spring:message code="aca.Academico"/></a>
			  </li>
			  <li class="<%if(sistema.equals("escuelas"))out.print("active"); %>">
			  	<a href="tareas?sistema=escuelas">Escuelas</a>
			  </li>
			</ul>
		</td>
	</tr>
</table>

<% 
	String mes = "";
	String dia = "";

	for(aca.util.AdminTareas tarea: tareas){
		
		if(!tarea.getfCrea().split("/")[1].equals(mes)){
%>
		<table style="width:95%" align="center">
			<tr>
				<td style="background:#D8D8D8;border-top:1px solid gray;"><h2><%=aca.util.Fecha.getMesNombre(Integer.parseInt(tarea.getfCrea().split("/")[1])) +" "+tarea.getfCrea().split("/")[2]%></h2></td>
			</tr>
		</table>
<%
		}
		
		
		if(!tarea.getfCrea().split("/")[0].equals(dia)){
			dia = tarea.getfCrea().split("/")[0];
%>
		</table>
		<table style="width:95%" align="center">
			<tr>
				<td><h3>Dia: <%=dia%></h3></td>
			</tr>
		</table>
		<table class="table table-condensed table-nohover table-fontsmall" width="95%" align="center">
		<tr>
			<th width="4%">Acción</th>
			<th width="4%"><spring:message code="aca.Folio"/></th>
			<th width="20%">Cliente</th>
			<th width="40%"><spring:message code="aca.Nombre"/></th>
			<th width="8%"><spring:message code="aca.Estado"/></th>
			<th width="8%"><spring:message code="aca.Creado"/></th>
			<th width="8%">Iniciado</th>
			<th width="8%">Terminado</th>
		</tr>
<%		
			mes = tarea.getfCrea().split("/")[1];
		}
		
		
		String desarrolladores = tarea.getDesarrollador()==null?"":tarea.getDesarrollador().replaceAll(",", ", ");
		
		String estado = "";
		if(tarea.getEstado().equals("I"))estado="Inactivo";
		else if(tarea.getEstado().equals("A"))estado="Activo";
		else if(tarea.getEstado().equals("T"))estado="Terminado";
%>
	<tr class="<%=estado%>">
		<td>
			<a href="javascript:Borrar('<%=tarea.getFolio()%>');"><i class="fas fa-trash-alt"></i></a>
			<a href="add?folio=<%=tarea.getFolio()%>"><i class="fas fa-pencil-alt"></i></a>
		</td>
		<td><%=tarea.getFolio() %></td>
		<td><%=tarea.getCliente() %></td>
		<td class="btn title">
			<%=tarea.getNombre() %>
			<div class="descripcion well" style="margin-bottom:0;">
				<h4>Descripción:</h4>
				<%=tarea.getDescripcion() %>
				<br>
				<h6>Desarrollador(es): <%=desarrolladores%></h6>
			</div>
		</td>
		<td><%=estado%></td>
		<td><%=tarea.getfCrea() %></td>
		<td><%=tarea.getfInicio()==null?"":tarea.getfInicio() %></td>
		<td><%=tarea.getfTerminado()==null?"":tarea.getfTerminado() %></td>
	</tr>
<%
	}
%>
</table>
<script>
(function(){
	$('.title').on('click', function(){$(this).find('.descripcion').slideToggle();});
})();
</script>
<%@ include file="../../cierra_enoc.jsp"%>


