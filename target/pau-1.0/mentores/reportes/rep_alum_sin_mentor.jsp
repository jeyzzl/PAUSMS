<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.catalogo.spring.CatTipoAlumno"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.catalogo.spring.CatNivel"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.vista.spring.Inscritos"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String facultad		= request.getParameter("facultad");	
	String fechaHoy		= aca.util.Fecha.getHoy();
	String facultadNombre		= (String)request.getAttribute("facultadNombre");
	String periodoNombre		= (String)request.getAttribute("periodoNombre");
	
	String clasFin		= "X";	
	String carrTemp		= "X";
	String sBgcolor		= "";
	String colorRow		= "";
	int cont			= 1;	
	String nivelId 		= "0";
	
	List<Inscritos> lisAlumnos 						= (List<Inscritos>) request.getAttribute("lisAlumnos");	
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaAcademicos			= (HashMap<String,String>)request.getAttribute("mapaAcademicos");
	HashMap<String,CatTipoAlumno> mapaTipos			= (HashMap<String,CatTipoAlumno>)request.getAttribute("mapaTipos");
	HashMap<String,AlumPersonal> mapaAlumnos		= (HashMap<String,AlumPersonal>)request.getAttribute("mapaAlumnos");
	HashMap<String,CatNivel> mapaNiveles			= (HashMap<String,CatNivel>)request.getAttribute("mapaNiveles");
	HashMap<String,CatModalidad> mapaModalidades	= (HashMap<String,CatModalidad>)request.getAttribute("mapaModalidades");
	
	boolean esAconsejable = false;
%>
<div class="container-fluid">
	<h2>Alumnos Inscritos/sin Mentor <small class="text-muted fs-4">( Periodo: <%=periodoNombre%> )</small></h2>
	<div class="alert alert-info">
		<a href="facultad?Opcion=3" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<div class="alert alert-info"><h2><%=facultadNombre%></h2></div>
	<table id="table" class="table table-sm table-bordered">
<%	
	for (int i=0; i<lisAlumnos.size(); i++ ){	 	
		Inscritos alum	= (Inscritos) lisAlumnos.get(i);		
		
		String nombreCarrera = "";
		if (mapaCarreras.containsKey( alum.getCarreraId() )){
			nombreCarrera 	= mapaCarreras.get( alum.getCarreraId() ).getNombreCarrera();
			nivelId 		= mapaCarreras.get( alum.getCarreraId() ).getNivelId();
		}		
	
		if( !carrTemp.equals(alum.getCarreraId()) ){
			carrTemp = alum.getCarreraId();
			cont = 1;
%>		
		<thead>	
  		<tr>
  			<td colspan="9" align="left"><div align="left"><strong><%=nombreCarrera%></strong></div></td>
  		</tr>
  		</thead>
		<thead class="table-info">	
		<tr> 
		  	<th><spring:message code="aca.Numero"/></th>
		  	<th>Matr&iacute;cula</th>
		   	<th><spring:message code="aca.Nombre"/></th>
		   	<th><spring:message code="aca.Modalidad"/></th>
		   	<th><spring:message code="aca.Carga"/></th>
		   	<th>Tipo Alum.</th>	
		   	<th>E. Civil</th>
		   	<th>Nivel</th>
		   	<th>Aconsejable?</th>
		</tr>
		</thead>
<%		}		
		
		//Map tipo de Alumo
		String tipoAlumnoId	= "-";
		String nombreTipo 	= "-";
		
		 if(mapaAcademicos.containsKey(alum.getCodigoPersonal())){	
		
			tipoAlumnoId = mapaAcademicos.get(alum.getCodigoPersonal());
			
			if (mapaTipos.containsKey(tipoAlumnoId)){
				nombreTipo = mapaTipos.get(tipoAlumnoId).getNombreTipo();
			}			
		} 
		
		// Map Alumnos		
		String nombre		= "-";
		String matricula	= "-";
		String nivel		= "-";
		String nivelNombre	= "-";
		String modalidad	= "-";
		String modalidadId	= "";
		String estadoCivil	= "-";
		
		if(mapaAlumnos.containsKey(alum.getCodigoPersonal())){			
			nombre			= mapaAlumnos.get(alum.getCodigoPersonal()).getNombreLegal();
			matricula		= mapaAlumnos.get(alum.getCodigoPersonal()).getCodigoPersonal();
			estadoCivil		= mapaAlumnos.get(alum.getCodigoPersonal()).getEstadoCivil();
			nivelNombre		= mapaNiveles.get(nivelId).getNombreNivel();
		}
		
		// Map Modalidad
		
		if(mapaModalidades.containsKey(alum.getModalidadId())){
			modalidadId	= mapaModalidades.get(alum.getModalidadId()).getModalidadId() ;
			modalidad 	= mapaModalidades.get(alum.getModalidadId()).getNombreModalidad();			
		}
		
		//tipoAlumnoId 	= aca.alumno.AcademicoUtil.getTipoAlumnoId(conEnoc, alum.getCodigoPersonal());		
		if(estadoCivil.equals("S")) estadoCivil = "Soltero";
		if(estadoCivil.equals("C")) estadoCivil = "Casado";
		if(estadoCivil.equals("V")) estadoCivil = "Viudo";
		if(estadoCivil.equals("D")) estadoCivil = "Divorciado";
		
		esAconsejable = false; colorRow = "style='color:red'";
		
		if ( ( estadoCivil.equals("Soltero") ) && 
			 ( modalidadId.equals("1") || modalidadId.equals("4") ) &&
			 ( !tipoAlumnoId.equals("5") && !tipoAlumnoId.equals("6") ) &&
			 ( !alum.getCargaId().substring(5).equals("F") && !alum.getCargaId().substring(5).equals("G")) &&
			 ( nivelId.equals("1") || nivelId.equals("2"))
		){
			esAconsejable = true;
			colorRow = "";
		}		
%>
		<tr>
		  	<td align="center" <%=colorRow%>><%=cont%></td> 
		   	<td align="center" <%=colorRow%>><%=matricula %></td>
		   	<td <%=colorRow%>><%= nombre%></td>
		   	<td align="left" <%=colorRow%>><%=modalidad%></td>
		   	<td <%=colorRow%>><%=alum.getCargaId()%></td>
		   	<td align="center" <%=colorRow%>><%=nombreTipo%></td>
		   	<td align="center" <%=colorRow%>><%=estadoCivil%></td>
		   	<td align="center" <%=colorRow%>><%=nivelNombre %></td>
		   	<td align="center" <%=colorRow%>><%= esAconsejable?"SI":"NO"%></td>
		</tr>
<%		
		cont++;
	}	
%> 			
		<tr><td colspan="9" align="center"><b>Fin del Listado!!</b></td></tr>
	</table>
</div>	