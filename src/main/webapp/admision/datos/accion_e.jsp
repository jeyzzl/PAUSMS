<%@ page import= "java.util.List"%>
<%@page import= "aca.alumno.spring.AlumEstudio"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%	idJsp = "070_e"; %>
<%@ include file="../../idioma.jsp"%>

<script type = "text/javascript">
	function Borrar(id){
		if(confirm("You are sure to delete the record?")==true){
	  			document.location.href="accion_e?Accion=2&Id="+id;				
		}
	}
</script>	
<%
	String id	 				= request.getParameter("Id")==null?"0":request.getParameter("Id");
	String codigoPersonal		= (String)session.getAttribute("codigoPersonal");
	String nombreUsuario 		= (String) request.getAttribute("nombreUsuario");
	String mensaje 				= (String) request.getAttribute("mensaje");

	String codigoAlumno 		= (String) request.getAttribute("codigoAlumno");
	AlumEstudio alumEstudio 	= (AlumEstudio) request.getAttribute("alumEstudio");

	
	List<AlumEstudio> lisAdmisiones	= (List<AlumEstudio>) request.getAttribute("lisAdmisiones");
%>
<div class="container-fluid">
	<h2>Background Studies Data <small class="text-muted fs-5">(Student: <%=codigoAlumno%>&nbsp; <%=nombreUsuario%>)</small></h2>
	<div class="alert alert-info">
		<a href="alumno" title="Return"><i class="fas fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="alumno" title="Personal Data"><i class="fas fa-user fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_u" title="Provenance Data"><i class="fas fa-globe fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_a" title="Academic Data"><i class="fas fa-book fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_e" title="Background Data"><i class="fas fa-file-signature fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_o" title="Bank Data"><i class="fas fa-credit-card fa-lg"></i></a>	
	</div>
	<form name="forma" action="accion_e" method="post">
		<input type="hidden" name="Accion" value="1">
		<input type="hidden" name="Id" value="<%=id%>">
		<div class="row">
			<div class="col-3">
				<label for="Titulo"><strong>Title</strong></label>
			    <input type="text" class="form-control" name="Titlulo" id="Titlulo" required value="<%=alumEstudio.getTitulo()%>" maxlength="100">
			    <br>
			    <label for="Institucion"><strong>Institution</strong></label>
			    <input type="text" class="form-control" name="Institucion" id="Institucion" required value="<%=alumEstudio.getInstitucion()%>" maxlength="100">
			    <br>
			    <label for="Completo"><strong>Studies Completed</strong></label>
			    <select name="Completo" class="form-select" style="width:200px;">
			    	<option value="S" <%=alumEstudio.getCompleto().equals("S")?"Selected":""%>>YES</option>
			    	<option value="N" <%=alumEstudio.getCompleto().equals("N")?"Selected":""%>>NO</option>
			    </select>
			    <br>
			    <label for="Convalida"><strong>Validated</strong></label>
			    <select name="Convalida" class="form-select" style="width:200px;">
			    	<option value="S" <%=alumEstudio.getConvalida().equals("S")?"Selected":""%>>YES</option>
			    	<option value="N" <%=alumEstudio.getConvalida().equals("N")?"Selected":""%>>NO</option>
			    </select>
			    <br>			      	    
			</div>
			<div class="col-3">
				<label for="Dependencia"><strong>Dependency</strong></label>
			    <input type="text" class="form-control" name="Dependencia" id="Dependencia" required value="<%=alumEstudio.getDependencia()%>" maxlength="70">
			    <br>
			    <label for="Inicio"><strong>Started</strong></label>
			    <input type="text" class="form-control" name="Inicio" id="Inicio" required value="<%=alumEstudio.getInicio()%>" maxlength="30">			    			        
			    <br>
				<label for="Fin"><strong>Finished</strong></label>
			    <input type="text" class="form-control" name="Fin" id="Fin" required value="<%=alumEstudio.getFin()%>" maxlength="30">
			    <br>			    	    
			</div>
		</div>
		<div class="alert alert-info">		
			<%-- <a class="btn btn-primary" href="accion_e?Id=0&Accion=0">New</a>&nbsp;&nbsp; --%>
			<input class="btn btn-primary" type="submit" value="Save">
			<span><%=mensaje %></span>
		</div>	
			 
	</form>
<%
	if(lisAdmisiones.size() > 0){
%>
	<table class="table table-sm table-bordered">
	<tr class="table-info">
		<td width="5%">&nbsp;</td>
		<td><strong>Id</strong></td>
		<td><strong>Title</strong></td>
		<td><strong>Institution</strong></td>
		<td><strong>Completed</strong></td>
		<td><strong>Started</strong></td>
		<td><strong>Finished</strong></td>
		<td><strong>Dependency</strong></td>
		<td><strong>Validated</strong></td>
	</tr>
<%
		for(AlumEstudio estudio : lisAdmisiones){
%>
	<tr>
		<td>
			<a href="javascript:Borrar('<%=estudio.getId()%>')"><i class="fas fa-trash-alt"></i></a>&nbsp;&nbsp;
			<a href="accion_e?Id=<%=estudio.getId()%>&Accion=3"><i class="fas fa-pencil-alt"></i></a>
		</td>
		<td><%=estudio.getId()%></td>
		<td><%=estudio.getTitulo()%></td>
		<td><%=estudio.getInstitucion()%></td>
		<td><%=estudio.getCompleto().equals("S")?"YES":"NO"%></td>
		<td><%=estudio.getInicio()==null?"<span class='badge bg-warning rounded-pill'>MISSING</span>":estudio.getInicio()%></td>
		<td><%=estudio.getFin()==null?"<span class='badge bg-warning rounded-pill'>MISSING</span>":estudio.getFin()%></td>
		<td><%=estudio.getDependencia()==null?"<span class='badge bg-warning rounded-pill'>MISSING</span>":estudio.getDependencia()%></td>
		<td><%=estudio.getConvalida().equals("S")?"YES":"NO"%></td>
	</tr>
<%
		}
%>
	</table>
<%
	}
%>
</div>