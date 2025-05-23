<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.musica.spring.MusiAutoriza"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	String mensaje					= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");

	String codigoPersonal			= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno				= (String) session.getAttribute("codigoAlumno");
	String periodoId				= (String) request.getAttribute("periodoId");
	String cargaId					= (String) request.getAttribute("cargaId");
	boolean esAlumno				= (boolean) request.getAttribute("esAlumno");
	String alumnoNombre				= (String) request.getAttribute("alumnoNombre");
	boolean encuentraCarga			= false;
	
	List<CatPeriodo> lisPeriodos	= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 			= (List<Carga>) request.getAttribute("lisCargas");	
	List<MusiAutoriza> lisAlumnos 	= (List<MusiAutoriza>) request.getAttribute("lisAlumnos");
	HashMap<String,String> mapaAlumnos 			= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
%>		
<div class="container-fluid">
	<h2>Authorize Timetable</h2>
	<form name="forma" action="horario" method="post">
	<div class="alert alert-info">		
		<b><spring:message code="aca.Periodo"/>:</b>
		<select onchange="javascript:document.forma.submit();" name="PeriodoId" class="input input-medium">
	<%	for(CatPeriodo periodo : lisPeriodos){%>
		<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
	<%	}%>
    	</select>
		&nbsp;&nbsp;
		<b><spring:message code="aca.Carga"/>: </b>
		<select onchange='javascript:document.forma.submit();' name="CargaId" style="width:350px;" class="input input-xlarge">
			<option <%=cargaId.equals("000000")?" Selected":""%> value="000000">[000000] Select a Load</option>
	<%	for(Carga carga : lisCargas){
			if (carga.getCargaId().equals(cargaId)) encuentraCarga = true;
	%>
			<option <%if(cargaId.equals(carga.getCargaId()))out.print(" Selected ");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
	<%	}%>
		</select>
		&nbsp;&nbsp;&nbsp;
		<%=mensaje.equals("-")?"":mensaje%>	
	</div>		
	</form>
<%	if (esAlumno){%>		
	<div class="alert alert-info">
		Student <%=codigoAlumno%> - <%=alumnoNombre%> &nbsp;&nbsp; <a href="grabar?CargaId=<%=cargaId%>" class="btn btn-success"><i class="fas fa-save"></i></a> 		
	</div>
<% 	}%>	
	<table class="table table-sm table-bordered" id="table">
	<thead class="table-info">
	<tr>
		<th>Op.</th>
		<th>#</th>
		<th>Id</th>
		<th>Name</th>
		<th>Date</th>
		<th>User</th>
	</tr>
	</thead>
	<tbody>
<%
	int row=0;
	for (MusiAutoriza autoriza : lisAlumnos){
		row++;
		String nombre = "-";
		if (mapaAlumnos.containsKey(autoriza.getCodigoPersonal())){
			nombre = mapaAlumnos.get(autoriza.getCodigoPersonal());
		}
%>
	<tr>
		<td><a href="javascript:Borrar('<%=autoriza.getCodigoPersonal()%>','<%=autoriza.getCargaId()%>')"><i class="fas fa-trash" ></i></a></td>
		<td><%=row%></td>
		<td><%=autoriza.getCodigoPersonal()%></td>
		<td><%=nombre%></td>
		<td><%=autoriza.getFecha()%></td>
		<td><%=autoriza.getUsuario()%></td>	
	</tr>
<%	} %>	
	</tbody>
	</table>
</div>
<script type="text/javascript">
	function Borrar(codigoAlumno,cargaId){
		if (confirm("Are you sure you want to delete this record?")){
			document.location.href="borrar?CodigoAlumno="+codigoAlumno+"&CargaId="+cargaId;
		}
	}
</script>