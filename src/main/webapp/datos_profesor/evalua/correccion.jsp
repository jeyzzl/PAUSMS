<%@ page import="java.text.*" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.kardex.spring.KrdxCursoAct"%>
<%@page import="aca.kardex.spring.KrdxCursoCal"%>

<head>
<script type="text/javascript" src="../../js/popcalendar.js"></script>
<script type="text/javascript">
	
	function EliminarNota(codigoAlumno, cursoCargaId, cursoId){
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
		}else{
			document.getElementById("Fecha"+checkbox.name.substr(6)).hidden = true;
			document.getElementById("Nota"+checkbox.name.substr(6)).disabled = true;
		}
	}
	
</script>
</head>
<%
	String evaluacionId 	= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");
	String cursoCargaId		= (String) request.getAttribute("cursoCargaId");
	String materia			= (String) request.getAttribute("materia");
	String maestro			= (String) request.getAttribute("maestro");
	String cursoId			= (String) request.getAttribute("cursoId");
	String yearName			= (String) request.getAttribute("yearName");	
	String carreraId		= (String) request.getAttribute("carreraId");
	String mensaje			= (String) request.getAttribute("mensaje");
	
	String nombreFacultad	= (String)request.getAttribute("nombreFacultad");
	String nombreCarrera	= (String)request.getAttribute("nombreCarrera");
	String nombreModalidad	= (String)request.getAttribute("nombreModalidad");
	
	int ciclo		= 1;	
	Fecha fecha		= (Fecha) request.getAttribute("fecha");	
	List<KrdxCursoAct> lisAlumnos	= (List<KrdxCursoAct>) request.getAttribute("lisAlumnos");
	
	
	HashMap<String, KrdxCursoCal> mapaCursoCargaCarga 	= (HashMap<String, KrdxCursoCal>)request.getAttribute("mapaCursoCargaCarga");
	
	HashMap<String, String> mapTipoCal 					= (HashMap<String, String>)request.getAttribute("mapTipoCal");	
	HashMap<String, String> mapAlumnosMateria 			= (HashMap<String, String>)request.getAttribute("mapAlumnosMateria");	
%>
<div class="container-fluid">
	<h3>Corrección de calificaciones<small class="text-muted fs-5"> ( <%=materia%> - <%=maestro%> )</small></h3>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="evaluar?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestro%>&Materia=<%=materia%>&CursoId=<%=cursoId%>&EvaluacionId=<%=evaluacionId%>"><spring:message code="aca.Regresar"/></a>
	</div>	
	<table class="table" style="width:100%" align="center">	      
	<tr>
		<td colspan="4">
			<b>Facultad:</b>&nbsp;<u><%= nombreFacultad%></u>&nbsp;&nbsp;
			<b>Especialidad:</b>&nbsp;<u><%= nombreCarrera%></u>&nbsp;&nbsp;&nbsp;&nbsp; <b>Semestre:</b>&nbsp;<u><%=ciclo%></u>&nbsp;&nbsp;&nbsp;
			<b>Modalidad:</b>&nbsp;<%= nombreModalidad%>&nbsp;&nbsp;&nbsp;
			<b>Ciclo escolar:</b>&nbsp;&nbsp;<u><%= "20"+ cursoCargaId.substring(0,2)+" - "+ "20"+ cursoCargaId.substring(2,4)%></u>
		</td>
	</tr>	
	</table>
	<h5 style="color: red;">*Recuerda que la calificación es de 0 a 100. No se permiten decimales.</h5>
	<form action="correccion?Accion=2" method="post" name="frmCorreccion" target="_self">
	<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
	<input type="hidden" name="CursoId" value="<%=cursoId%>">
	<input type="hidden" name="Maestro" value="<%=maestro%>">
	<input type="hidden" name="Materia" value="<%=materia%>">
	<table class="table table-sm table-bordered">
	<thead class="table-dark">   
	<tr>
      	<th width="2%">Op.</th>
        <th width="8%">N° Mat.</th>
        <th width="20%"><spring:message code="aca.NombreDelAlumno"/></th>
        <th width="10%">Tipo Calif.</th>
        <th width="10%">Calif. Ordinaria</th>
      	<th width="10%">Calif. Extraordinaria</th>
      	<th width="10%">Calif.</th>
        <th width="10%">Tipo Nota</th>
        <th width="15%"><spring:message code="aca.Fecha"/></th>
    </tr>
    </thead>
<%	
	for(int i=0;i<lisAlumnos.size();i++){
		
		KrdxCursoAct alumno = lisAlumnos.get(i);
		KrdxCursoCal krdxCursoCal = new KrdxCursoCal();
		boolean existe = false;
		if(mapaCursoCargaCarga.containsKey(alumno.getCodigoPersonal())){
			krdxCursoCal = mapaCursoCargaCarga.get(alumno.getCodigoPersonal());
			existe = true;
		}
		
		String strCheck = "";
		krdxCursoCal.setCursoCargaId(cursoCargaId);
		krdxCursoCal.setCursoId(cursoId);
		krdxCursoCal.setCodigoPersonal(alumno.getCodigoPersonal());				
		
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
    	<td align="center">
    <%	if(existe){ %>
    		<i class="fas fa-trash-alt" style="color:red" onclick="EliminarNota('<%=alumno.getCodigoPersonal() %>', '<%=cursoCargaId %>', '<%=cursoId%>');"></i>&nbsp;    		
    <%	} %></td>
        <td align="center">
	        <input type="checkbox" id="Codigo<%=alumno.getCodigoPersonal()%>" name="Codigo<%=alumno.getCodigoPersonal()%>" value="<%= alumno.getCodigoPersonal() %>" <%=strCheck%> onclick="PonerFecha(this, '<%=Fecha.getHoy() %>');" /> 
	        <%= alumno.getCodigoPersonal()%>
	    </td>
        <td align="left"><%=nombreAlumno%></td>
        <input type="hidden" value="<%=alumno.getTipoCalId()%>" name="tipoCalId<%=alumno.getCodigoPersonal() %>">
        <td align="center"><%=tipoCal%></td>
        <td align="center"><%=alumno.getNota() %></td>
         <td align="center"><%=alumno.getNotaExtra() %></td>
        <td>
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
				<option value="E" <%if(existe && krdxCursoCal.getTipoNota().equals("E")){out.print("selected");}%>>Extraordinario</option>
			<%} %>
			</select>
        </td>
        <td>
        	<div id="Fecha<%=alumno.getCodigoPersonal()%>" <%=!existe ? "hidden" : "" %>><%=existe ? krdxCursoCal.getFecha() : Fecha.getHoy()%>
       		<%	if(existe && !krdxCursoCal.getFecha().equals(Fecha.getHoy()) && !krdxCursoCal.getTipo().equals("D")){ %>
       				<input value="Hoy" type="button" onclick="document.getElementById('Fecha<%=alumno.getCodigoPersonal()%>').innerHTML='<%=Fecha.getHoy() %>';
       															document.getElementById('FechaValue<%=alumno.getCodigoPersonal()%>').value='<%=Fecha.getHoy() %>';" />
      			<%	} %>
        	</div>
        </td>
        <input type="hidden" value="<%=existe ? krdxCursoCal.getFecha() : Fecha.getHoy()%>" id="FechaValue<%=alumno.getCodigoPersonal()%>" name="FechaValue<%=alumno.getCodigoPersonal()%>">
	</tr>
<% 	} %>
	</table>
	<div class="alert alert-info">
    	<input class="btn btn-primary" type="submit" value="Guardar" />
      	<input class="btn btn-info" type="button" value="PDF" onclick="location='correccionPDF?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestro%>&Materia=<%=materia%>&CursoId=<%=cursoId %>'" />
<% 	if (!mensaje.equals("-")){ out.print(mensaje); }%>      	
    </div>    
	</form>  
</div>