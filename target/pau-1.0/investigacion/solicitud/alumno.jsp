<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.Arrays "%>
<%@page import="java.util.List "%>

<jsp:useBean id="Usuario" scope="page" class="aca.vista.Usuarios"/>
<jsp:useBean id="InvEvento" scope="page" class="aca.investiga.InvEvento"/>
<jsp:useBean id="InvEventoU" scope="page" class="aca.investiga.InvEventoUtil"/>

<%
	String codigo				= (String) session.getAttribute("codigoPersonal");
	String codigoAlum			= (String) session.getAttribute("codigoAlumno");
	String nombreUsuario		= " ";
	String msj					= " ";
	
	Usuario.setCodigoPersonal(codigoAlum);
	if (Usuario.existeReg(conEnoc)){
		Usuario.mapeaRegId(conEnoc, codigoAlum);
		nombreUsuario	= Usuario.getNombre()+" "+Usuario.getApellidoPaterno()+" "+Usuario.getApellidoMaterno();
	}else{
		nombreUsuario = "¡ No existe !";
	}
	
	String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	ArrayList<String> lisAlumnos 	= new ArrayList<String>();
	
	String alumnos 			= InvEventoU.getAlumnosSol(conEnoc,codigo,folio);
	
	if (accion.equals("0") && !alumnos.equals("0")){
		// Llenar lista
		for (String alumno: alumnos.split("-")){			
	    	lisAlumnos.add(alumno);
	    }
	}else if(accion.equals("1")){
		
		if(!alumnos.contains(codigoAlum)){
			// Quita el valor cero
			if (alumnos.equals("0")) alumnos = codigoAlum+"-"; else alumnos = alumnos + codigoAlum+"-";
			
			InvEvento.setAlumnos(alumnos);
			InvEvento.setFolio(folio);
			InvEvento.setCodigoPersonal(codigo);
			if(InvEventoU.updateAlumnos(conEnoc, alumnos, folio, codigo)){
				msj = "<div class='alert alert-success'><button class='close' data-dismiss='alert'>&times;</button> El Registro se guardó correctamente</div>";								
			}else{
				msj = "<div class='alert alert-error'><button class='close' data-dismiss='alert'>&times;</button> Ocurrió un error al guardar el registro</div>";
			}
		}else{
				msj = "<div class='alert alert-error'><button class='close' data-dismiss='alert'>&times;</button> Ya agregaste este alumno</div>";
		}
		
		// Llenar lista
		for (String alumno: alumnos.split("-")){
	    	lisAlumnos.add(alumno);
	    }
	}else if(accion.equals("2")){
		String codAlum = request.getParameter("codigoAlumno");
		
		//llenar lista
		for (String alumno: alumnos.split("-")){
	    	lisAlumnos.add(alumno);
	    }
		// Quitar el alumno
		lisAlumnos.remove(codAlum);
		
		// Extraer los alumnos de la lista 
		alumnos = "";
		for(String alumno:lisAlumnos){
			alumnos = alumnos+"-";
		}
		
		// Modificar el registro
		InvEvento.setAlumnos(alumnos);
		InvEvento.setFolio(folio);
		InvEvento.setCodigoPersonal(codigo);
		if(InvEventoU.updateAlumnos(conEnoc, alumnos, folio, codigo)){
			msj = "<div class='alert alert-success'><button class='close' data-dismiss='alert'>&times;</button> El Registro se eliminó correctamente</div>";			
		}else{
			msj = "<div class='alert alert-error'><button class='close' data-dismiss='alert'>&times;</button> Ocurrió un error al eliminar el registro</div>";
		}
	}
%>
<html>
<head>
<body>
	<div class="container-fluid">
		<h1>Listado de eventos</h1> 
		<br />
	<%=msj %>
	<div class="alert alert-info">
			<a href="evento" class="btn btn-primary"><i class="fas fa-arrow-left"></a>&nbsp;			 	
	</div>
	<table class="table table-sm table-bordered">  
		<tr>				
			<th width="3%">Opción</th>
			<th width="6%"><spring:message code="aca.Clave"/></th>
			<th width="15%"><spring:message code="aca.Nombre"/></th>
		</tr>
		<tr>					
			<td>
<%  if(!codigoAlum.substring(0,2).equals("98")){%>						
				<a href="alumno?Accion=1&Folio=<%=folio%>" class="btn btn-success btn-sm" title="Agregar"><i class="icon-plus icon-white"></i></a>&nbsp;
<%  }%>			
			</td>
			<td><%= codigoAlum %></td>
			<td><%= nombreUsuario %></td>
		</tr>
	</table>
	<br />
	<h3>Listado de alumnos</h3>
	<table class="table table-condensed table-nohover table-striped" width="40%">
		<tr>				
			<th width="3%">Opción</th>
			<th width="6%"><spring:message code="aca.Clave"/></th>
			<th width="15%"><spring:message code="aca.Nombre"/></th>
		</tr>
<% 			
		for(String alumno:lisAlumnos){
%>			
		<tr>
			<td>				
				<a href="alumno?Accion=2&Folio=<%=folio%>&codigoAlumno=<%=alumno%>" class="btn btn-danger btn-sm" title="Eliminar"><i class="fas fa-minus"></i></a>&nbsp;
			</td>			
			<td><%= alumno %></td>
			<td><%= aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, alumno, "NOMBRE") %></td>
		</tr>
<% 		}
		%>			
	</table>
	</div>	
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>