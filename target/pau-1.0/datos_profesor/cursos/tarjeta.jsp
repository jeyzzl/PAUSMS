<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatTipoCal"%>
<%@ page import= "aca.catalogo.spring.CatPais"%>
<%@ page import= "aca.catalogo.spring.CatEstado"%>
<%@ page import= "aca.catalogo.spring.CatCiudad"%>
<%@ page import= "aca.catalogo.spring.CatReligion"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.alumno.spring.AlumAcademico"%>
<%@ page import= "aca.vista.spring.AlumnoCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String cursoCargaId			= request.getParameter("CursoCargaId");
	String maestro 				= request.getParameter("Maestro");
	String materia 				= request.getParameter("Materia");
	String matricula 			= "";
	
	List<AlumnoCurso> lisAlumnos					= (List<AlumnoCurso>) request.getAttribute("lisAlumnos");
	
	// Map de tipos de califiacaciones
	HashMap <String, CatTipoCal> mapaCat 			= (HashMap <String, CatTipoCal>)request.getAttribute("mapaCat");
	HashMap <String, CatPais> mapaPaises 			= (HashMap <String, CatPais>)request.getAttribute("mapaPaises");
	HashMap <String, CatEstado> mapaEstados 		= (HashMap <String, CatEstado>)request.getAttribute("mapaEstados");
	HashMap <String, CatCiudad> mapaCiudades		= (HashMap <String, CatCiudad>)request.getAttribute("mapaCiudades");
	HashMap <String, CatReligion> mapaReligiones	= (HashMap <String, CatReligion>)request.getAttribute("mapaReligiones");
	HashMap <String, AlumPersonal> mapaPersonal		= (HashMap <String, AlumPersonal>)request.getAttribute("mapaPersonal");
	HashMap <String, AlumAcademico> mapaAcademico	= (HashMap <String, AlumAcademico>)request.getAttribute("mapaAcademico");
%>
<div class="container-fluid">
	<h2>Students in the Subject:<small class="text-muted fs-6"> ( <%=materia%> - <%=maestro%> ) </small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="cursos"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table style="width:100%" align="center">
	  <tr>
<%	
	int row = 0;
	for(AlumnoCurso ac : lisAlumnos){		
		matricula = ac.getCodigoPersonal();
		if ((row % 3) == 0 && row!=0) { %> </tr><tr><% } //OJO AQU� POR LA J QUE ERA DEL FOR		
		// Obtiene la informaci�n de los alumnos		
		AlumPersonal personal = new AlumPersonal();
		if (mapaPersonal.containsKey(matricula)){
			personal = mapaPersonal.get(matricula);	
		}
		
		AlumAcademico academico = new AlumAcademico();
		if (mapaAcademico.containsKey(matricula)){
			academico = mapaAcademico.get(matricula);	
		}
		
		String paisNombre = "-";
		if (mapaPaises.containsKey(personal.getPaisId())){
			paisNombre = mapaPaises.get(personal.getPaisId()).getNombrePais();
		}
		
		String estadoNombre = "-";
		if (mapaEstados.containsKey(personal.getPaisId()+personal.getEstadoId())){
			estadoNombre = mapaEstados.get(personal.getPaisId()+personal.getEstadoId()).getNombreEstado();
		}
		
		String ciudadNombre = "-";
		if (mapaCiudades.containsKey(personal.getPaisId()+personal.getEstadoId()+personal.getCiudadId())){
			ciudadNombre = mapaCiudades.get(personal.getPaisId()+personal.getEstadoId()+personal.getCiudadId()).getNombreCiudad();
		}		
		
		String religionNombre = "-";
		if (mapaReligiones.containsKey(personal.getReligionId())){
			religionNombre = mapaReligiones.get(personal.getReligionId()).getNombreReligion();
		}
		
		String nombre = personal.getNombre()+" "+personal.getApellidoPaterno()+" "+personal.getApellidoMaterno();
		String nom[]= nombre.split(" ");
		nombre = "";
		for (int i=0;i<nom.length;i++){
			if  (nom[i]!=null&&!nom[i].equals(""))
			nombre+= nom[i].substring(0,1) + nom[i].substring(1).toLowerCase() + " ";
		}
%>
	<td>
	<table width=200 height=100 class="table" >
	<tr valign='top' height='120'>
		<td></td>
		<td width='70' align='center'>
			<table>
				<tr>&nbsp;<td bordercolor='#8596CA'><img src="../../foto?Codigo=<%=matricula%>" width="70"></td></tr>
			</table> 	
			<%=matricula%>
		</td>
		<td>
			<table width='100%' cellspacing='8'>
				<tr><td><font size=2><b><%=nombre%></b></font></td></tr>
				<tr><td><%=ac.getCursoId().substring(0,8) %></td></tr>
				<tr><td><%=religionNombre%></td></tr>
				<tr><td>(<%=personal.getFNacimiento()%>)</td></tr>
				<tr><td><%=ciudadNombre%>, <%=estadoNombre%>, <%=paisNombre%></td></tr>
				<tr>
					<td>
						<% if(academico.getResidenciaId().equals("E"))out.print("Day Student");else out.print("Boarding Stdn.");%>, 
						<%
						if(mapaCat.containsKey(ac.getTipoCalId())){
						%><b><% out.print(mapaCat.get(ac.getTipoCalId()).getNombreTipoCal()); %></b>
						<%
						}
						%>
					</td>
				</tr>
			</table> 	
		</td>
	</tr>
	</table>
	</td>
<%
		row++;
	}
%>
	  </tr>
	</table>
</div>