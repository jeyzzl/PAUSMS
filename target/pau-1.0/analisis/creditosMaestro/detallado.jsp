<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "java.util.HashMap"%>

<jsp:useBean id="MaestrosU" scope="page" class="aca.vista.MaestrosUtil"/>
<jsp:useBean id="CarreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="CargaAcademicaU" scope="page" class="aca.vista.CargaAcaUtil"/>
<jsp:useBean id="AlumnoCursoU" scope="page" class="aca.vista.AlumnoCursoUtil"/>

<script type="text/javascript">
	function Mostrar(){		
		document.forma.submit();
	}
</script>

<%
	java.text.DecimalFormat formato	= new java.text.DecimalFormat("###,##0.00;(###,##0.00)");

	String cargas			= (String)session.getAttribute("cargas") == null?aca.carga.CargaUtil.getCargasActivas(conEnoc, aca.util.Fecha.getHoy()):session.getAttribute("cargas").toString();
	String modalidades		= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();
	
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	if(cargas.equals("")) cargas="''";
	if(modalidades.equals("")) modalidades="''";
	String lisModo[] 		= modalidades.replace("'", "").split(",");
%>

<div class="container-fluid">
	<h1>Créditos por maestro</h1>
	<form id="forma" name="forma" action="maestros?Accion=1" method="post">
	<div class="alert alert-info">		
		<a class="btn btn-primary btn-small" href="maestros"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;&nbsp;
		<b>Cargas:</b> <%= cargas.replace("'", "")%>&nbsp;&nbsp;&nbsp;
		<b>Modalidades:</b>
<%
		for(String mod:lisModo){
			String nombreModalidad = aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, mod);
			out.print("["+nombreModalidad+"] ");	
		}		
%>					
	</div>
	</form>
<%	
	if (accion.equals("1")){
	
		// Lista de maestros con materias en las cargas y modalidades seleccionadas
		ArrayList<aca.vista.CargaAcademica> listaMaestros  =  CargaAcademicaU.getListaCargasyModalidades(conEnoc, cargas, modalidades, "O", "ORDER BY ENOC.EMP_APELLIDO(CODIGO_PERSONAL)");
		
		// Map del numero de alumnos por materia 
		java.util.HashMap<String, String> mapAlumnos 	= AlumnoCursoU.mapAlumPorMaestroyMateria(conEnoc, cargas, modalidades, "'I','1','2','4','5','6'");
		
		// Map de nombres de maestros 
		java.util.HashMap<String, String> mapMaestros 	= aca.vista.MaestrosUtil.mapMaestroNombre(conEnoc, "APELLIDO");
		
		// Map de carreras 
		java.util.HashMap<String, aca.catalogo.CatCarrera> mapCarreras 	= CarreraU.getMapAll(conEnoc, "");
		
		// Map de carreras 
		java.util.HashMap<String, aca.catalogo.CatModalidad> mapModalidad 	= aca.catalogo.ModalidadUtil.getMapAll(conEnoc, "");
%>	
	<table id="noayuda" width="90%"  class="table table-condensed">
		<tr>
			<th colspan="4" style="text-align:center">Datos del Maestro</th>
		    <th colspan="5" style="text-align:center">Datos de la materia</th>
		    <th colspan="7" style="text-align:center">Créditos del nivel</th>
		</tr>		
		<tr>
		    <th>#</th>
		    <th>Codigo</th>
		    <th>Maestro</th>
		    <th>Tipo Emp.</th>		    
		    <th>Materia</th>
		    <th>Carrera</th>
		    <th>Modalidad</th>
		    <th class="right">creditos</th>
		    <th class="right">Alumnos</th>	    
		    <th class="right" title="Preparatoria">Pre.</th>
		    <th class="right" title="Universidad">Uni.</th>
		    <th class="right" title="Especialidad">Esp.</th>
		    <th class="right" title="Maestría">Mae.</th>
		    <th class="right" title="Doctorado">Doc.</th>	    
		    <th class="right" title="Educación Continua">Ed.Cont.</th>
		    <th class="right" title="Total">Total</th>    
		  </tr>
<% 
	int row = 0;
	double totPrepa = 0, totLic = 0, totEsp=0, totMae=0, totDoc=0, totEdu=0, totGeneral=0;
	for(aca.vista.CargaAcademica materias : listaMaestros){
		row++;
		
		String nombreMaestro = "-";
		if ( mapMaestros.containsKey(materias.getCodigoPersonal()) ){
			nombreMaestro = mapMaestros.get(materias.getCodigoPersonal());
		}
		
		String modalidad = "-";
		if (mapModalidad.containsKey(materias.getModalidadId())){
			modalidad = mapModalidad.get(materias.getModalidadId()).getNombreModalidad();
		}
		
		String alumnos = "0";
		if ( mapAlumnos.containsKey(materias.getCodigoPersonal()+materias.getCursoCargaId()) ){
			alumnos = mapAlumnos.get(materias.getCodigoPersonal()+materias.getCursoCargaId());
		}
		
		String nivel = "0";
		String nombreCarrera = "-";
		if ( mapCarreras.containsKey(materias.getCarreraId()) ){
			nivel 			= mapCarreras.get(materias.getCarreraId()).getNivelId();
			nombreCarrera 	= mapCarreras.get(materias.getCarreraId()).getNombreCarrera(); 
		}
		
		String prepa 	= "0";	
		String lic 		= "0";	
		String esp 		= "0";
		String mae 		= "0";
		String doc 		= "0";		
		String educ 	= "0";
		if (nivel.equals("1")){
			prepa = String.valueOf( Double.parseDouble(alumnos)*Double.parseDouble(materias.getCreditos()) );
		}else if(nivel.equals("2")){
			lic = String.valueOf( Double.parseDouble(alumnos)*Double.parseDouble(materias.getCreditos()) );
		}else if(nivel.equals("3")){
			mae = String.valueOf( Double.parseDouble(alumnos)*Double.parseDouble(materias.getCreditos()) );
		}else if(nivel.equals("4")){
			doc = String.valueOf( Double.parseDouble(alumnos)*Double.parseDouble(materias.getCreditos()) );
		}else if (nivel.equals("5")){
			educ = String.valueOf( Double.parseDouble(alumnos)*Double.parseDouble(materias.getCreditos()) );
		}else{
			esp = String.valueOf( Double.parseDouble(alumnos)*Double.parseDouble(materias.getCreditos()) );
		}
		
		totPrepa += Double.parseDouble(prepa);
		totLic += Double.parseDouble(lic);
		totEsp += Double.parseDouble(esp);
		totMae += Double.parseDouble(mae);
		totDoc += Double.parseDouble(doc);
		totEdu += Double.parseDouble(educ);		 
		
		String idEmpleado		= aca.emp.EmpleadoUtil.getId(conEnoc, materias.getCodigoPersonal());
		String tipoEmpleado 	= aca.emp.EmpleadoUtil.getTipoEmpleado(conEnoc, idEmpleado);		
		String tipoNombre 		= "-";
		//if (tipoEmpleado.equals("0")&&maestro.getCodigoPersonal().substring(0, 3).equals("989")) tipoNombre = "Foraneo";
		if (tipoEmpleado.equals("1")) tipoNombre = "Denominacional";
		if (tipoEmpleado.equals("2")) tipoNombre = "Inter-División";
		if (tipoEmpleado.equals("3")) tipoNombre = "Inter-Unión";
		if (tipoEmpleado.equals("5")) tipoNombre = "Servicio misionero";
		if (tipoEmpleado.equals("6")) tipoNombre = "Jubilado";
		if (tipoEmpleado.equals("7")) tipoNombre = "Contrato";
		if (tipoEmpleado.equals("8")) tipoNombre = "Voluntario";
		if (tipoEmpleado.equals("9")) tipoNombre = "PorHoras";
		if (tipoEmpleado.equals("10")) tipoNombre = "Serv.Social";
		if (tipoEmpleado.equals("11")) tipoNombre = "Hospital";
		if (tipoEmpleado.equals("14")||tipoEmpleado.equals("0")) tipoNombre = "Otros";
		
		
		double total 	= Double.valueOf(prepa)+Double.valueOf(lic)+Double.valueOf(mae)+Double.valueOf(doc)+Double.valueOf(educ);
		totGeneral		+= total; 
%>
		<tr>
		    <td><%=row%></td>
		    <td><%=materias.getCodigoPersonal()%></td>
		    <td><%=nombreMaestro%></td>
		    <td><%=tipoNombre%></td>		    
		    <td><%=materias.getNombreCurso()%></td>
		    <td><%=nombreCarrera%></td>
		    <td><%=modalidad%></td>
		    <td class="right"><%=formato.format(Double.parseDouble(materias.getCreditos()))%></td>
		    <td class="right"><%=alumnos%></td>
		    <td class="right"><%=formato.format(Double.parseDouble(prepa))%></td>
		    <td class="right"><%=formato.format(Double.parseDouble(lic))%></td>
		    <td class="right"><%=formato.format(Double.parseDouble(esp))%></td>
		    <td class="right"><%=formato.format(Double.parseDouble(mae))%></td>
		    <td class="right"><%=formato.format(Double.parseDouble(doc))%></td>
		    <td class="right"><%=formato.format(Double.parseDouble(educ))%></td>		    
		    <td class="right"><%=formato.format(total)%></td>
		  </tr>
<%		
		}	
%>  
		<tr>
		    <th colspan="9" class="right">T O T A L E S --></th>		    
		    <th class="right"><%=formato.format(totPrepa)%></th>
		    <th class="right"><%=formato.format(totLic)%></th>
		    <th class="right"><%=formato.format(totEsp)%></th>
		    <th class="right"><%=formato.format(totMae)%></th>
		    <th class="right"><%=formato.format(totDoc)%></th>
		    <th class="right"><%=formato.format(totEdu)%></th>		    
		    <th class="right"><%=formato.format(totGeneral)%></th>
		</tr>
	</table>
<%	} // If de accion.equals("1")%>	
</div>	
<%@ include file= "../../cierra_enoc.jsp" %>