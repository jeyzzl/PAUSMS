<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.financiero.spring.ContCcosto"%>
<%@page import="aca.financiero.spring.ContEjercicio"%>
<%@page import="aca.bec.spring.BecTipo"%>
<%@page import="aca.bec.spring.BecAcceso"%>
<%@page import="aca.bec.spring.BecAcuerdo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%			
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal");
	String ejercicioId 		= (String)request.getAttribute("ejercicioId");
	String tipoTemp 		= (String)request.getAttribute("tipoTemp");
	String usuarioNombre	= (String)request.getAttribute("usuarioNombre");	
	boolean admin			= (boolean)request.getAttribute("esAdmin");
	String tipoId 			= request.getParameter("Tipo")==null?tipoTemp:request.getParameter("Tipo");
	String estado 			= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
	String vigente 			= request.getParameter("Vigente")==null?"S":request.getParameter("Vigente");
	String accion 			= request.getParameter("Accion")==null?"":request.getParameter("Accion");
	
	// Lista de ejercicios
	List<ContEjercicio> lisEjercicios 		= (List<ContEjercicio>)request.getAttribute("lisEjercicios");
	List<BecTipo> lisTipos 					= (List<BecTipo>)request.getAttribute("lisTipos");	 
	List<BecAcceso> lisAccesos 				= (List<BecAcceso>)request.getAttribute("lisAccesos");
	List<BecAcuerdo> lisAcuerdos 			= (List<BecAcuerdo>)request.getAttribute("lisAcuerdos");
	HashMap<String,String> mapaInscritos 	= (HashMap<String,String>)request.getAttribute("mapaInscritos");
	HashMap<String,String> mapaAlumnos 		= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
		
	String fechaInscrito 	= "";	
	String msj 				= "";
%>
<style>
	body{
		background:white;
	}
</style>

<div class="container-fluid">
	<h2 style="margin-bottom:10px;">Acuerdo de Becas<small class="text-muted fs-4">( <%=codigoPersonal%>-<%=usuarioNombre%> )</small></h2>
	<%=msj%>	
	<form action="acuerdo" name="forma" method="post">
		<div class="alert alert-info">			
			<select name="EjercicioId" id="EjercicioId" style="width:150px;" onchange="document.forma.submit()">
			<%
				for(ContEjercicio ejercicio: lisEjercicios){	
			%>
					<option value="<%=ejercicio.getIdEjercicio() %>" <%if(ejercicioId.equals(ejercicio.getIdEjercicio()))out.print("selected"); %>><%=ejercicio.getIdEjercicio() %></option>
			<%
				}
			%>
			</select>			
			
			<select name="Tipo" id="Tipo" style="width:300px;" onchange="document.forma.submit()">
			<%
				for(BecTipo tipo: lisTipos){
					if(tipo.getDepartamentos()==null)continue;
					boolean permiso = false;
					for(BecAcceso acces: lisAccesos){
						if( tipo.getDepartamentos().contains("-"+acces.getIdCcosto()) ){
							permiso = true;
							break;
						}
					}					
					if(permiso == true || admin == true){						
					
			%>
					<option value="<%=tipo.getTipo() %>" <%if(tipoId.equals(tipo.getTipo()))out.print("selected"); %>><%=tipo.getNombre() %></option>
			<%
					}
				}
			%>
			</select>
			&nbsp;&nbsp;
			<select name="Estado" id="Estado" style="width:100px;" onchange="document.forma.submit()">
				<option value="A" <%if(estado.equals("A"))out.print("selected"); %>>Altas</option>
				<option value="I" <%if(estado.equals("I"))out.print("selected"); %>>Ejercidos</option>
			</select>
			&nbsp;&nbsp;
			<select name="Vigente" id="Vigente" style="width:100px;" onchange="document.forma.submit()">
				<option value="S" <%if(vigente.equals("S"))out.print("selected"); %>>Vigentes</option>
				<option value="T" <%if(vigente.equals("T"))out.print("selected"); %>>Todos</option>
			</select>		
			&nbsp;&nbsp;
			<a href="editar?EjercicioId=<%=ejercicioId%>&Tipo=<%=tipoId %>" class="btn btn-success"><i class="fas fa-plus"></i> Agregar Acuerdo </a>			
		</div>
	</form>
	
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<th>#</th>
			<th>&nbsp;</th>
			<th><spring:message code="aca.Codigo"/></th>
			<th><spring:message code="aca.Alumno"/></th>
			<th><spring:message code="aca.Fecha"/></th>
			<th><spring:message code="aca.Matricula"/></th>
			<th>Enseñanza</th>
			<th>Internado</th>
			<th>Valor</th>
			<th>Vigencia</th>
			<th><spring:message code="aca.Estado"/></th>
			<th><spring:message code="aca.Inscrito"/></th>
			<th><spring:message code='aca.Usuario'/></th>
		</tr>
		</thead>
<%		 
	int cont = 0;		
	for(BecAcuerdo acuerdo: lisAcuerdos){
		cont++;
		
		if (mapaInscritos.containsKey(acuerdo.getCodigoPersonal()) ) 
			fechaInscrito = mapaInscritos.get(acuerdo.getCodigoPersonal());
		else
			fechaInscrito = "X";
		
		String nombreAlumno = "-";
		if (mapaAlumnos.containsKey(acuerdo.getCodigoPersonal())){
			nombreAlumno = mapaAlumnos.get(acuerdo.getCodigoPersonal());
		}
%>
		<tr>
			<td><%=cont %></td>
			<td>
			 	<%
			 	if(acuerdo.getPuestoId()==null || acuerdo.getPuestoId().equals("0")){
			 	%>
			 		<a href="editar?EjercicioId=<%=ejercicioId%>&Tipo=<%=tipoId %>&CodigoAlumno=<%=acuerdo.getCodigoPersonal() %>&Folio=<%=acuerdo.getFolio() %>" class="btn btn-info btn-sm"><i class="fas fa-pencil-alt"></i></a>
					<a onclick="borrar('<%=ejercicioId%>', '<%=tipoId %>', '<%=acuerdo.getCodigoPersonal()%>', '<%=acuerdo.getFolio()%>');" class="btn btn-danger btn-sm"><i class="fas fa-trash-alt icon-white"></i></a>
			 	<%
			 	}
			 	%>
			 </td>
			<td><%=acuerdo.getCodigoPersonal() %></td>
			<td><%=nombreAlumno%></td>
			<td><%=acuerdo.getFecha() %></td>
			<td><%=acuerdo.getMatricula() %></td>
			<td><%=acuerdo.getEnsenanza() %> </td>
			<td><%=acuerdo.getInternado() %></td>
			<td><%=acuerdo.getValor().equals("C")?"Cantidad":"Porcentaje" %></td>
			<td><%=acuerdo.getVigencia() %></td>
			<td><%=acuerdo.getEstado().equals("I")?"Ejercido":"Alta" %></td>
			<td><%=fechaInscrito.equals("X")?" ":fechaInscrito%></td>
			<td><%= acuerdo.getUsuario()  %></td>
		</tr>
<%
	}
%>
	</table>		
</div>
<script>

	function borrar(ejercicioId, tipoId, codigoAlumno, folio){
		if(confirm("¿Estás seguro que quieres elimnar el folio:"+folio+" del alumno:"+codigoAlumno+"?")){
			document.location.href="borrarAcuerdo?EjercicioId="+ejercicioId+"&Tipo="+tipoId+"&CodigoAlumno="+codigoAlumno+"&Folio="+folio;
		}
	}

</script>