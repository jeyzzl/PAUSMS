<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.musica.spring.MusiAlumno"%>
<%@page import="aca.musica.spring.MusiPadres"%>
<%@page import="aca.catalogo.spring.CatReligion"%>

<%
	String codigo 				= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
	String estado 				= request.getParameter("Estado")==null?"S":request.getParameter("Estado");
	String mensaje 				= request.getParameter("mensaje")==null?"-":request.getParameter("mensaje");
	
	boolean tienePadres 		= (boolean) request.getAttribute("tienePadres");
	MusiAlumno musiAlumno 		= (MusiAlumno) request.getAttribute("musiAlumno");
	MusiPadres musiPadres 		= (MusiPadres) request.getAttribute("musiPadres");
	String institucionNombre	= (String) request.getAttribute("institucionNombre");
	String paisNombre			= (String) request.getAttribute("paisNombre");
	String nacionalidad			= (String) request.getAttribute("nacionalidad");
	String estadoNombre			= (String) request.getAttribute("estadoNombre");
	String ciudadNombre			= (String) request.getAttribute("ciudadNombre");
	
	HashMap<String, CatReligion> mapaReligiones 	= (HashMap<String, CatReligion>) request.getAttribute("mapaReligiones");	
%>
<div class="container-fluid">
	<h2>Traspasar datos personales</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="alumnos?Estado=<%=estado%>">Regresar</a>
		<%if (!mensaje.equals("-")) out.print(mensaje);%>
	</div>
	<div class="row" style="margin: 0px 0px 0 0px;">
		<div class="col-md-4">
		<form id="frmAlumno" name="frmAlumno" action="grabarAlumno" method="post">
			<table style="width:100%">
			<tr>
   				<th align="center" class="alert alert-success" colspan="2">Datos del Alumno</th>
   			</tr>
			<tr>
				<td><strong>Código:</strong></td>
				<td><%=musiAlumno.getCodigoId()%></td>
			</tr>
			<tr>
				<td><strong>Nombre:</strong></td>
				<td id="Nombre" name="Nombre"><%=musiAlumno.getNombre()%></td>
			</tr>
			<tr>
				<td><strong>A. Paterno:</strong></td>
				<td id="ApellidoPaterno"><%=musiAlumno.getApellidoPaterno()%></td>
			</tr>
			<tr>
				<td><strong>A. Materno:</strong></td>
				<td id="ApellidoMaterno"><%=musiAlumno.getApellidoMaterno()%></td>
			</tr>
			<tr>
				<td><strong>Fecha Nac.:</strong></td>
				<td id="FNacimiento"><%=musiAlumno.getFechaNac()%></td>
			</tr>
			<tr>
				<td><strong>Sexo:</strong></td>
				<td id="Genero"><%=musiAlumno.getGenero()%></td>
			</tr>
			<%
				String religionNombre = "-";
				if (mapaReligiones.containsKey(musiAlumno.getReligionId()) ){
					religionNombre = mapaReligiones.get(musiAlumno.getReligionId()).getNombreReligion(); 
				}
			%>
			<tr>
				<td><strong>Religi&oacute;n:</strong></td>
				<td><%=religionNombre%></td>
			</tr>
			<tr>
				<td><strong>Instituci&oacute;n:</strong></td>
				<td><%=institucionNombre%></td>
			</tr>			
			<tr>
				<td><strong>Tutor:</strong></td>
				<td><%=musiAlumno.getTutor()%></td>
			</tr>
			<tr>
				<td><strong>Tel&eacute;fono:</strong></td>
				<td id="Telefono"><%=musiAlumno.getTelefono()%></td>
			</tr>
			<tr>
				<td><strong>Celular:</strong></td>
				<td><%=musiAlumno.getCelular()%></td>
			</tr>
			<tr>
				<td><strong>Email:</strong></td>
				<td id="Email"><%=musiAlumno.getEmail()%></td>
			</tr>					
			<tr>
				<td><strong>Pa&iacute;s:</strong></td>
				<td><%=paisNombre%></td>
			</tr>
			<tr>
				<td><strong>Estado:</strong></td>
				<td><%=estadoNombre%></td>
			</tr>
			<tr>
				<td><strong>Ciudad:</strong></td>
				<td><%=ciudadNombre%></td>
			</tr>
			<tr>
				<td><strong>Nacionalidad:</strong></td>
				<td id="Nacionalidad"><%=nacionalidad%></td>
			</tr>
			<tr>
				<td><strong>Tel. Trabajo:</strong></td>
				<td><%=musiAlumno.getTelTrabajo()%></td>
			</tr>			
			</table>
		</form>
		</div>
<%	if (tienePadres){%>		
		<div class="col-md-4">
			<table style="width:100%">
			<tr>
	    		<th align="center" class="alert alert-success" colspan="2">Datos del Padre</th>
	    	</tr>
			<tr>
				<td><strong>C&oacute;digo:</strong></td>
				<td><%=musiAlumno.getCodigoId()%></td>
			</tr>
			<tr>
				<td><strong>Vive:</strong></td>
				<td><%=musiPadres.getPadVive()%></td>
			</tr>
			<tr>
				<td><strong>Nombre:</strong></td>
				<td><%=musiPadres.getPadNombre()%></td>
			</tr>
			<tr>
				<td><strong>A. Paterno:</strong></td>
				<td><%=musiPadres.getPadPaterno()%></td>
			</tr>
			<tr>
				<td><strong>A. Materno:</strong></td>
				<td><%=musiPadres.getPadMaterno()%></td>
			</tr>
			<tr>
				<td><strong>Ocupaci&oacute;n:</strong></td>
				<td><%=musiPadres.getPadOcupacion()%></td>
			</tr>
			<tr>
				<td><strong>Direcci&oacute;n:</strong></td>
				<td><%=musiPadres.getPadDireccion()%></td>
			</tr>
			<tr>
				<td><strong>Correo:</strong></td>
				<td><%=musiPadres.getPadCorreo()%></td>
			</tr>
			<tr>
				<td><strong>Tel. Casa:</strong></td>
				<td><%=musiPadres.getPadTelcasa()%></td>
			</tr>
			<tr>
				<td><strong>Tel. Trabajo:</strong></td>
				<td><%=musiPadres.getPadTeltrabajo()%></td>
			</tr>
			<tr>
				<td><strong>Celular:</strong></td>
				<td><%=musiPadres.getPadTelcelular()%></td>
			</tr>
			<%
				religionNombre = "-";
				if (mapaReligiones.containsKey(musiAlumno.getReligionId()) ){
					religionNombre = mapaReligiones.get(musiPadres.getPadReligionId()).getNombreReligion(); 
				}
			 %>
			<tr>
				<td><strong>Religi&oacute;n:</strong></td>
				<td><%=religionNombre%></td>
			</tr>			
			</table>
		</div>
		
		<div class="col-md-4">
			<table style="width:100%">
			<tr>
		   		<th align="center" class="alert alert-success" colspan="2">Datos de la Madre</th>
		  	</tr>
			<tr>
				<td><strong>C&oacute;digo:</strong></td>
				<td><%=musiAlumno.getCodigoId()%></td>
			</tr>
			<tr>
				<td><strong>Vive:</strong></td>
				<td><%=musiPadres.getMadVive()%></td>
			</tr>
			<tr>
				<td><strong>Nombre:</strong></td>
				<td><%=musiPadres.getMadNombre()%></td>
			</tr>
			<tr>
				<td><strong>A. Paterno:</strong></td>
				<td><%=musiPadres.getMadPaterno()%></td>
			</tr>
			<tr>
				<td><strong>A. Materno:</strong></td>
				<td><%=musiPadres.getMadMaterno()%></td>
			</tr>
			<tr>
				<td><strong>Ocupaci&oacute;n:</strong></td>
				<td><%=musiPadres.getMadOcupacion()%></td>
			</tr>
			<tr>
				<td><strong>Direcci&oacute;n:</strong></td>
				<td><%=musiPadres.getMadDireccion()%></td>
			</tr>
			<tr>
				<td><strong>Correo:</strong></td>
				<td><%=musiPadres.getMadCorreo()%></td>
			</tr>
			<tr>
				<td><strong>Tel. Casa:</strong></td>
				<td><%=musiPadres.getMadTelcasa()%></td>
			</tr>
			<tr>
				<td><strong>Tel. Trabajo:</strong></td>
				<td><%=musiPadres.getMadTeltrabajo()%></td>
			</tr>
			<tr>
				<td><strong>Celular:</strong></td>
				<td><%=musiPadres.getMadTelcelular()%></td>
			</tr>
			<%
				religionNombre = "-";
				if (mapaReligiones.containsKey(musiAlumno.getReligionId()) ){
					religionNombre = mapaReligiones.get(musiPadres.getMadReligionId()).getNombreReligion(); 
				}
			 %>
			<tr>
				<td><strong>Religi&oacute;n:</strong></td>
				<td><%=religionNombre%></td>
			</tr>			
       		</table>
		</div>
<%	}%>		
	</div>
<%	
	if(tienePadres){
%>

	<div class="alert alert-info">
		<a class="btn btn-primary" onclick="javascript:GrabarAlumno();" href="grabarAlumno?Codigo=<%=codigo%>&Estado=<%=estado%>">Grabar Alumno</a>
	</div>
<%
	}
%>
</div>
<script>
	function GrabarAlumno(){
		document.frmAlumno.submit();
	}
</script>