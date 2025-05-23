<%@ page import="java.text.*" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.util.Fecha"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.kardex.spring.KrdxCursoAct"%>
<%@page import="aca.kardex.spring.KrdxCursoCal"%>

<head>
<script type="text/javascript" src="../../js/popcalendar.js"></script>
<script type="text/javascript">	

	function Eliminar(codigoAlumno, cursoCargaId, cursoId){
		if( confirm("¿Desea eliminar la calificación del alumno "+codigoAlumno+"?") ){			
			document.location.href="borrarNota?CursoCargaId="+cursoCargaId+"&CursoId="+cursoId+"&CodigoAlumno="+codigoAlumno;
		}
	}
	
	function PonerFecha(checkbox, fecha){
		if(checkbox.checked){
			document.getElementById("Nota"+checkbox.name.substr(6)).disabled = false;
			document.getElementById("Fecha"+checkbox.name.substr(6)).hidden = false;
			if(document.getElementById("FechaValue"+codigo).value!=null){
				document.getElementById("Fecha"+checkbox.name.substr(6)).innerHTML = document.getElementById("FechaValue"+codigo).value;				
			}
			else{
				document.getElementById("Fecha"+checkbox.name.substr(6)).innerHTML = fecha;	
				document.getElementById("FechaValue"+checkbox.name.substr(6)).value = fecha;
			}
		}
		else{
			document.getElementById("Fecha"+checkbox.name.substr(6)).hidden = true;
			document.getElementById("Nota"+checkbox.name.substr(6)).disabled = true;
		}
	}
	
</script>
</head>
<%
	String cursoCargaId		= (String)request.getAttribute("cursoCargaId");
	String materia			= (String)request.getAttribute("materia");
	String maestro			= (String)request.getAttribute("maestro");
	String cursoId			= (String)request.getAttribute("cursoId");
	String mensaje			= (String) request.getAttribute("mensaje");

	String nombreFacultad	= (String)request.getAttribute("nombreFacultad");
	String nombreCarrera	= (String)request.getAttribute("nombreCarrera");
	String nombreModalidad	= (String)request.getAttribute("nombreModalidad");
	String yearName			= (String)request.getAttribute("yearName");
    aca.util.Fecha fecha	= (aca.util.Fecha)request.getAttribute("fecha");
	
	int ciclo				= (int)request.getAttribute("ciclo");

	List<KrdxCursoAct> lisAlumnos 						= (List<KrdxCursoAct>)request.getAttribute("lisAlumnos");
    HashMap<String, KrdxCursoCal> mapaCursoCargaCarga 	= (HashMap<String, KrdxCursoCal>)request.getAttribute("mapaCursoCargaCarga");
    HashMap<String, String> mapTipoCal 					= (HashMap<String, String>)request.getAttribute("mapTipoCal");
    HashMap<String, String> mapAlumnosMateria 			= (HashMap<String, String>)request.getAttribute("mapAlumnosMateria");
	
%>
<div class="container-fluid">
	<h2><spring:message code="portal.maestro.correccion.CorreccionCalificaciones"/><small class="text-muted fs-5"> ( <%=materia%> - <%=maestro%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="evaluar?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestro%>&Materia=<%=materia%>&CursoId=<%=cursoId%>&EvaluacionId=<%=request.getParameter("EvaluacionId")%>"><spring:message code="aca.Regresar"/></a>
	</div>
	<table style="width:100%; margin:0 auto;">
		<tr>
			<td colspan="4">
				<b><spring:message code="portal.maestro.correccion.Facultad"/>:</b>&nbsp;<u><%= nombreFacultad%></u>&nbsp;&nbsp;
				<b><spring:message code="portal.maestro.correccion.Especialidad"/>:</b>&nbsp;<u><%= nombreCarrera%></u>&nbsp;&nbsp;&nbsp;&nbsp; <b><spring:message code="portal.maestro.correccion.Semestre"/>:</b>&nbsp;<u><%=ciclo%></u>&nbsp;&nbsp;&nbsp;
				<b><spring:message code="portal.maestro.correccion.Modalidad"/>:</b>&nbsp;<%= nombreModalidad%>&nbsp;&nbsp;&nbsp;
				<b><spring:message code="portal.maestro.correccion.CicloEscolar"/>:</b>&nbsp;&nbsp;<u><%= "20"+ cursoCargaId.substring(0,2)+" - "+ "20"+ cursoCargaId.substring(2,4)%></u>
			</td>
		</tr>	
	</table>
	<br>
	<h5 style="color: red;">*<spring:message code="portal.maestro.correccion.MensajeUno"/>.</h5>
	<form action="correccion?Accion=2" method="post" name="frmCorreccion" target="_self">
		<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">		
		<table class="table table-sm table-bordered">
		<thead class="table-dark">     
   			<tr>
		      	<th width="2%"><spring:message code="portal.maestro.correccion.Op"/></th>
		        <th width="8%">N° <spring:message code="portal.maestro.correccion.Mat"/></th>
		        <th width="20%"><spring:message code="aca.NombreDelAlumno"/></th>
		        <th width="10%"><spring:message code="portal.maestro.correccion.TipoCalif"/></th>
		        <th width="10%"><spring:message code="portal.maestro.correccion.CalifOrdinaria"/></th>
		        <th width="10%"><spring:message code="portal.maestro.correccion.CalifExtraordinaria"/></th>
		        <th width="10%"><spring:message code="portal.maestro.correccion.Calif"/></th>
		        <th width="10%"><spring:message code="portal.maestro.correccion.TipoNota"/></th>
		        <th width="15%"><spring:message code="aca.Fecha"/></th>
		    </tr>
		</thead>    
<%	for(int i=0;i<lisAlumnos.size();i++){
		KrdxCursoAct alumno = lisAlumnos.get(i);
		KrdxCursoCal krdxCursoCal = new KrdxCursoCal();
		boolean existe = false;
		if(mapaCursoCargaCarga.containsKey(alumno.getCodigoPersonal())){
			krdxCursoCal = mapaCursoCargaCarga.get(alumno.getCodigoPersonal());
			existe = true;
		}
	
		String strCheck = "";
		String nota = "";
		
		if(existe & (!krdxCursoCal.getTipo().equals("D"))){
			strCheck = "checked";
			nota = krdxCursoCal.getNota();
		}

		String tipoCal = "";
		if(mapTipoCal.containsKey(alumno.getTipoCalId())){
			tipoCal = mapTipoCal.get(alumno.getTipoCalId());
		}
		
		String nombreAlumno = "";
		if(mapAlumnosMateria.containsKey(alumno.getCodigoPersonal())){
			nombreAlumno = mapAlumnosMateria.get(alumno.getCodigoPersonal());
		}
%>	
			<tr>
   				<td align="center"><%if(existe){ %><img id="Eliminar<%=alumno.getCodigoPersonal()%>" class="button" src="../../imagenes/no.png" alt="Eliminar" onclick="Eliminar('<%=alumno.getCodigoPersonal() %>', '<%=cursoCargaId %>', '<%= cursoId%>')" ><%} %></td>
       			<td align="center">
        			<input type="checkbox" id="Codigo<%=alumno.getCodigoPersonal()%>" name="Codigo<%=alumno.getCodigoPersonal()%>" value="<%= alumno.getCodigoPersonal() %>" <%=strCheck%> onclick="PonerFecha(this, '<%=Fecha.getHoy() %>');" /> 
        			<%= alumno.getCodigoPersonal()%></td>
       			<td align="left"><%= nombreAlumno%></td>
       			<input type="hidden" value="<%=alumno.getTipoCalId()%>" name="tipoCalId<%= alumno.getCodigoPersonal() %>">
		        <td align="left"><%=tipoCal%></td>
		        <td align="left"><%=alumno.getNota() %></td>
		        <td align="left"><%=alumno.getNotaExtra() %></td>
		        <td align="center">
       				<input type="text" id="Nota<%= alumno.getCodigoPersonal() %>" name="Nota<%= alumno.getCodigoPersonal() %>" value="<%= nota%>" <%=existe ? "" : "disabled" %> size="5" maxlength="3"/>
  				</td>
       			<td>
   					<select id="tipoNota<%= alumno.getCodigoPersonal() %>" name="tipoNota<%= alumno.getCodigoPersonal() %>">
<%
	       			if(alumno.getNotaExtra()==null){
	       				alumno.setNotaExtra("0");
	       			}
%>
	       		<%if(Integer.parseInt(alumno.getNotaExtra()) == 0){%>
						<option value="O" <%if(!existe || krdxCursoCal.getTipoNota().equals("O")){out.print("selected");}%>><spring:message code="aca.Ordinario"/></option>
				<%}else if(Integer.parseInt(alumno.getNotaExtra()) > 0){ %>
						<option value="E" <%if(existe && krdxCursoCal.getTipoNota().equals("E") ){out.print("selected");}%>><spring:message code="portal.maestro.correccion.Extraordinario"/></option>
				<%} %>
					</select>
       			</td>
		        <td>
	        		<div id="Fecha<%=alumno.getCodigoPersonal()%>" <%=!existe ? "hidden" : "" %>><%=existe ? krdxCursoCal.getFecha() : Fecha.getHoy()%>
<%					if(existe && !krdxCursoCal.getFecha().equals(Fecha.getHoy()) && !krdxCursoCal.getTipo().equals("D")){ %>
        				<input value="Today" type="button" onclick="document.getElementById('Fecha<%=alumno.getCodigoPersonal()%>').innerHTML='<%=Fecha.getHoy() %>';
						document.getElementById('FechaValue<%=alumno.getCodigoPersonal()%>').value='<%=Fecha.getHoy() %>';" />
<%					} %>
		        	</div>
		        </td>
       			<input type="hidden" value="<%=existe ? krdxCursoCal.getFecha() : Fecha.getHoy()%>" id="FechaValue<%=alumno.getCodigoPersonal()%>" name="FechaValue<%=alumno.getCodigoPersonal()%>">
			</tr>
<% 
	}
%>
		</table>
		<div class="alert alert-info"> 				
   			<input class="btn btn-primary" type="submit" value="Save" onclick="return Guardar();" />
    		<input class="btn btn-info" type="button" value="PDF" onclick="location='correccionPDF?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestro%>&Materia=<%=materia%>&CursoId=<%=cursoId %>'"/>
<% 	if (!mensaje.equals("-")){ out.print(mensaje); }%>    		 		
		</div>
	</form> 
</div>