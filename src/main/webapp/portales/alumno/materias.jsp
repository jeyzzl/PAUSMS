<%@page import="java.util.*"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.alumno.spring.CancelaEstudio"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.carga.spring.CargaGrupoProgramacion"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.carga.spring.CargaGrupo"%>
<%@page import="aca.edo.spring.Edo"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.vista.spring.Estadistica"%>
<%@page import="aca.internado.spring.ComAutorizacion"%>
<%@page import="aca.objeto.spring.Hora"%>
<%@page import="aca.util.Fecha"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<html>
<head>
	<style>
		.linea{ 
		  text-decoration:line-through;
		}
	</style>		
</head>
<%@ include file= "menu.jsp" %>
<script type="text/javascript">
	function ActualizarCarga(){	
  		document.frmMaterias.BloqueId.value = "0";
  		document.frmMaterias.submit();
	}	
	function ActualizarBloque(){  		
  		document.frmMaterias.submit();
	}	
</script>	
<%
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	java.text.DecimalFormat getEntero	= new java.text.DecimalFormat("###,##0;-###,##0");
	
	String cancelaPortal 				= session.getAttribute("cancelaPortal")==null?"N":(String) session.getAttribute("cancelaPortal");
	String cierraPortal 				= (String)session.getAttribute("cancelaPortal")==null?"N":(String)session.getAttribute("cancelaPortal");
	AlumAcademico alum 					= (AlumAcademico)request.getAttribute("alum");
	CargaGrupo cargaGrupoModo			= (CargaGrupo)request.getAttribute("cargaGrupoModo");
	
	String fechaHoy 					= aca.util.Fecha.getHoy();
	
	String matricula 					= (String) session.getAttribute("codigoAlumno");
	String codigoPersonal				= (String) session.getAttribute("codigoPersonal");
	String cargaId						= (String) session.getAttribute("cargaId");
	String bloqueId						= (String) session.getAttribute("bloqueId");
	String esMovil						= (String) session.getAttribute("esMovil");
	
	boolean estudiosCancelados 			= (boolean) request.getAttribute("estudiosCancelados");
		
	Estadistica estadistica				= (Estadistica) request.getAttribute("estadistica");
	String planId						= (String) request.getAttribute("planId");	
	String carreraNombre 				= (String) request.getAttribute("carreraNombre");
	String mod							= (String) request.getAttribute("mod");
	String fInicio 						= (String) request.getAttribute("fInicio");
	
	String cursoCargaId					= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");	
	String materia	 					= request.getParameter("Materia")==null?"-":request.getParameter("Materia");
	String seguroAcceso					= request.getParameter("seguro")==null?"Restringido":request.getParameter("seguro");	
	
	AlumPersonal alumno 				= (AlumPersonal) request.getAttribute("alumno");
	AlumAcademico academico 			= (AlumAcademico) request.getAttribute("academico");
	AlumPlan plan						= (AlumPlan) request.getAttribute("plan");
	CancelaEstudio cancelaEstudio	 	= (CancelaEstudio) request.getAttribute("cancelaEstudio");
	boolean bloquear					= (boolean) request.getAttribute("bloquear");
	
	ComAutorizacion comAutorizacion		= (ComAutorizacion) request.getAttribute("comAutorizacion");	

	List<CargaGrupoProgramacion> lisProgra 			= (List<CargaGrupoProgramacion>) request.getAttribute("lisProgra");
	List<AlumnoCurso> lisAlumnos 					= (List<AlumnoCurso>) request.getAttribute("lisAlumnos");
	List<String> materiasElegibles					= (List<String>) request.getAttribute("materiasElegibles");
	List<Carga> lisCarga 							= (List<Carga>) request.getAttribute("lisCarga");
	List<CargaBloque> lisBloques					= (List<CargaBloque>) request.getAttribute("lisBloques");
	
	// Lista de cursos del alumno
	List<AlumnoCurso> lisCursos 					= (List<AlumnoCurso>) request.getAttribute("lisCursos");
	List<Edo> lisEdoActual 							= (List<Edo>) request.getAttribute("lisEdoActual");
	List<Hora> lisHoras		 						= (List<Hora>)request.getAttribute("lisHoras");
	
	// Consulta la asistencia de los alumnos en cada clase
	HashMap<String,String> mapAsistencia 			= (HashMap<String,String>) request.getAttribute("mapAsistencia");
	HashMap<String,String> mapAsistenciaTotal 		= (HashMap<String,String>) request.getAttribute("mapAsistenciaTotal");
	
	// Mapa de cursos de origen
	HashMap<String,String> mapMateriasOrigen 		= (HashMap<String,String>) request.getAttribute("mapMateriasOrigen");
	// Map de nombres de maestros
	HashMap<String,String> mapMaestroNombre 		= (HashMap<String,String>) request.getAttribute("mapMaestroNombre");	
	// Map de tipos de cursos
	HashMap<String,String> mapTipoCurso 			= (HashMap<String,String>) request.getAttribute("mapTipoCurso");	
	// Map de tipo cal
	HashMap<String,String> mapTipoCal 				= (HashMap<String,String>) request.getAttribute("mapTipoCal");	
	// Map de cursos
	HashMap<String,MapaCurso> mapCursos 			= (HashMap<String,MapaCurso>) request.getAttribute("mapCursos");	
	// Map de cargaGrupos
	HashMap<String,CargaGrupo> mapCargaGrupo 		= (HashMap<String,CargaGrupo>) request.getAttribute("mapCargaGrupo");	
	// Map de Edo
	HashMap<String,Edo> mapAllEdo 					= (HashMap<String,Edo>) request.getAttribute("mapAllEdo");	
	// Map de nombres de carreras
	HashMap<String,String> mapCarreraNombre 		= (HashMap<String,String>) request.getAttribute("mapCarreraNombre");
		
	HashMap<String,String> mapTieneGrupo 			= (HashMap<String,String>) request.getAttribute("mapTieneGrupo");
	HashMap<String,String> mapaGruposDelAlumno 		= (HashMap<String,String>) request.getAttribute("mapaGruposDelAlumno");
	HashMap<String,String> mapGrupoActivoTodos 		= (HashMap<String,String>) request.getAttribute("mapGrupoActivoTodos");
	HashMap<String,String> mapGrupoActivoNuevos		= (HashMap<String,String>) request.getAttribute("mapGrupoActivoNuevos");
	HashMap<String,String> mapPuntosEvaluados 		= (HashMap<String,String>) request.getAttribute("mapPuntosEvaluados");
	HashMap<String,String> mapPuntosGanados 		= (HashMap<String,String>) request.getAttribute("mapPuntosGanados");
	HashMap<String,String> mapContestadas 			= (HashMap<String,String>) request.getAttribute("mapContestadas");
	HashMap<String,String> mapAvanceEvaluaciones 	= (HashMap<String,String>) request.getAttribute("mapAvanceEvaluaciones");
	
	// Map de cargas autorizadas del alumno
	HashMap<String, String> mapCargasAlumno		= (HashMap<String,String>) request.getAttribute("mapCargasAlumno");
	// Mapa de letras para calificacion
	HashMap<String, String> mapaLetrasDeNotas	= (HashMap<String,String>) request.getAttribute("mapaLetrasDeNotas");
	
	String nombreMaestro	= "";
	String sesionId			= session.getId();	
	
	// Variables para calcular el promedio
	int nota 			= 0;
	float creditos		= 0;
	int sumaCredXNota 	= 0; 
	int sumaCred		= 0;
	int sumaCredTotal	= 0;
	int row				= 0;
	int sumaNotas		= 0;
	double ponderado 	= 0;
	double promedio 	= 0;
	boolean promedia 	= false;
	boolean acceso 		= false;	
		
	String colorPortal = (String)session.getAttribute("colorPortal");
	if(colorPortal==null)colorPortal="";
	
	if ( cierraPortal.equals("NO") /*|| seguroAcceso.equals("mercy")*/){
		acceso = true;
	}else{
		acceso = false;
	}
	
	boolean mostrar = false;
	
	// Verificar si tiene la cargaId
	boolean tieneCarga = false;
	for(Carga carga : lisCarga){						
		if (carga.getCargaId().equals(cargaId)){
			tieneCarga = true;				
		}
	}	
	if (!tieneCarga && lisCarga.size() > 0) cargaId = lisCarga.get(0).getCargaId();

	String escala 			= plan.getEscala();		
%>

<div  class="container-fluid">
	<h4><spring:message code="portal.alumno.materias.MateriasRegistradas"/> <small class="text-muted fs-5"> (<%=matricula%> - <%=alumno.getNombre()%> <%=alumno.getApellidoMaterno()%> <%=alumno.getApellidoPaterno()%> ) </small></h4>
	<form name="frmMaterias" action="materias" mthod="post">
		<div class="alert alert-info d-flex align-items-center">
			<b><spring:message code="portal.alumno.materias.Carga"/>:</b>&nbsp;	
			<select name="CargaId" onChange="javascritp:ActualizarCarga()" class="form-select" style="width:350px;">
<%				
			int i=0;		
			for( i=0;i<lisCarga.size();i++){
				Carga carga = (Carga) lisCarga.get(i);			
				if (cargaId.equals(carga.getCargaId())){				
					out.print(" <option value='"+carga.getCargaId()+"' Selected> ["+carga.getCargaId()+"] "+ carga.getNombreCarga()+"</option>");
				}else{
					out.print(" <option value='"+carga.getCargaId()+"'> ["+carga.getCargaId()+"] "+ carga.getNombreCarga()+"</option>");
				}				
			}	
			
			aca.util.Fecha fecha 	= new aca.util.Fecha();
			String fechaLimite 		= fecha.getDiaSiguiente(!fInicio.equals("")?fInicio:"01/01/2000", 19);//26 dias despues de la fecha de inicio de la carga
			int diasRestantes 		= aca.util.Fecha.getDiasEntreFechas(aca.util.Fecha.getHoy(), fechaLimite);		
			if(diasRestantes>0)mostrar=true;
%>
			</select> &nbsp; &nbsp;	
			<b><spring:message code="aca.Bloque"/>:</b>&nbsp;	
			<select class="form-select" name="BloqueId" style="width:200px;" onChange="javascritp:ActualizarBloque()">
<%
		for (CargaBloque bloque: lisBloques){
%>
				<option value="<%=bloque.getBloqueId()%>" <%=bloqueId.equals(bloque.getBloqueId())?"selected":""%>><%=bloque.getBloqueId()%>-<%=bloque.getNombreBloque()%></option>
<%		
		}	
%>			
			</select>		
<%		if(!fInicio.equals("")){%>
			&nbsp;&nbsp;
	 		<a href="horario?CargaId=<%=cargaId%>&BloqueId=<%=bloqueId%>"><img style="height:30px;margin-bottom:0px;" src="img/horario.png" data-bs-toggle="tooltip" data-bs-placement="right"  title="Schedule" class="button"/></a>
<%		}%>

<!-- BOLETAS PARCIALES Y OFICIALES  -->

<!-- 			&nbsp;&nbsp; -->
<%-- 			<b><spring:message code="portal.alumno.materias.Boletas"/>:</b>&nbsp;			 --%>
<%-- 			<a href="boletaParcial?CargaId=<%=cargaId%>&BloqueId=<%=bloqueId%>" class="btn btn-sm btn-success" target="_blank"> --%>
<%-- 				<i class="icon-print icon-white"></i> <spring:message code="portal.alumno.materias.Parcial"/> --%>
<!-- 			</a> -->
<!-- 			&nbsp; -->
<%-- 			<a href="boleta?CargaId=<%=cargaId%>&BloqueId=<%=bloqueId%>" class="btn btn-sm btn-primary" target="_blank"> --%>
<%-- 				<i class="icon-print icon-white"></i> <spring:message code="portal.alumno.materias.Oficial"/> --%>
<!-- 			</a> -->

		</div>
	</form>
<% if(!bloquear){%>
	<div class="alert alert-success d-flex align-items-center alert-alto-m mt-2">
		<span class="badge bg-success p-2"><%=planId%></span>&nbsp;<i class="fas fa-question-circle" data-bs-container="body" data-bs-toggle="popover" data-bs-placement="left" data-bs-content="<%=carreraNombre%>"></i>&nbsp;<h4>|</h4>&nbsp;
<%	if (esMovil.equals("N")){%>
		<b><%=carreraNombre%></b>&nbsp;<h4>|</h4>&nbsp;
<%	} %>	
		<b><%=academico.getModalidadId().equals("1")?"Face-to-Face":"Online"%></b>&nbsp;<h4>|</h4>&nbsp;
		<b><% if (alum.getResidenciaId().equals("E"))out.print("Day Student"); else out.print("Boarding Student");%></b>&nbsp;<h4>|</h4>&nbsp;
<%-- 		<b>Meals: <%=comAutorizacion.getNumComidas()==""?"0":comAutorizacion.getNumComidas()%></b> --%>
	</div>	
<%
	if(estudiosCancelados){
%>	
	<div class="alert alert-danger">
		<b><spring:message code="datosAlumno.portal.Nota13"/>:</b><br><%=cancelaEstudio.getComentario() %>
	</div>		
<% 	}%>	
	<table class="table table-fullcondensed table-bordered">
		<tr>	
			<th width="5%"><h6>#</h6></th>
     		<th width="20%" style="font-size:1rem;"><spring:message code="aca.Cursos"/></th>
          	<th width="5%" style="font-size:1rem;" class="text-right"><spring:message code="portal.alumno.materias.Cred"/></th>
          	<th width="20%" style="font-size:1rem;"><spring:message code="portal.alumno.materias.Avance"/></th>          	
          	<th width="10%" style="font-size:1rem;" class="text-right"><spring:message code="aca.Nota"/></th> 
          	<th width="10%" style="font-size:1rem;" class="text-right"><spring:message code="portal.alumno.materias.Maximo"/></th>       	
          	<th width="10%" style="font-size:1rem;" class="text-right"><spring:message code="aca.Extra"/></th>          	
          	<th width="10%" style="font-size:1rem;"><spring:message code="aca.Status"/></th>
<%--           	<th width="7%" style="font-size:1rem;"><spring:message code="portal.alumno.materias.ApFisica"/></th> --%>
		</tr>
<%
	String notaMateria 		= "";
	double notaMateriaGPA 	= 0;
	AlumnoCurso alumnoCurso = new AlumnoCurso();
	for(i=0; i<lisCursos.size(); i++){
		alumnoCurso = (AlumnoCurso) lisCursos.get(i);			
		nombreMaestro = mapMaestroNombre.get(alumnoCurso.getMaestro());			
		notaMateria = alumnoCurso.getNota();
		float notaMaxima = 0;
		String textoMaximo = "<sapan class='badge bg-inverse'>"+notaMaxima+"</span>";
		if (alumnoCurso.getTipoCalId().equals("I")){
			float puntosEvaluados = 0;
			if (mapPuntosEvaluados.containsKey(alumnoCurso.getCursoCargaId())){
				puntosEvaluados = Float.valueOf(mapPuntosEvaluados.get(alumnoCurso.getCursoCargaId()));
			}
			
			float puntosGanados = 0;
			if (mapPuntosGanados.containsKey(alumnoCurso.getCodigoPersonal()+alumnoCurso.getCursoCargaId())){
				puntosGanados = Float.valueOf(mapPuntosGanados.get(alumnoCurso.getCodigoPersonal()+alumnoCurso.getCursoCargaId()));
			}
			float eficiencia = 0;
			if (puntosEvaluados > 0){
				eficiencia = (puntosGanados*100) /puntosEvaluados;
			}
			notaMateria = getEntero.format(eficiencia);
			
			notaMaxima = 100-puntosEvaluados + puntosGanados;
			// if (notaMaxima>0) notaMaxima = notaMaxima/10;			
		}

		// CALCULA LA LETRA DE LA NOTA MAXIMA OBTENIBLE
		String letraPromMax = "";
		int iNotaMax 		= Math.round(notaMaxima);
		String sNotaMax 	= String.valueOf(iNotaMax);

		if(mapaLetrasDeNotas.containsKey(sNotaMax)){
			letraPromMax = mapaLetrasDeNotas.get(sNotaMax);
		}
		
		if (notaMaxima >= 85){
			textoMaximo = "<span class='badge' style='background-color:#0EE8B0; color:black;'>"+letraPromMax+"</span>";
		}else if  (notaMaxima >=70 && notaMaxima < 85){
			textoMaximo = "<span class='badge' style='background-color:#FFFF85; color:black;'>"+letraPromMax+"</span>";
		}else{
			textoMaximo = "<span class='badge' style='background-color:#FF6A4D; color:black;'>"+letraPromMax+"</span>";
		}		
		
		// Calculo del promedio
		promedia = false; nota = 0; creditos = 0;
		
		String tipoCurso = "0";
		if (mapTipoCurso.containsKey(alumnoCurso.getCursoId())){
			tipoCurso = mapTipoCurso.get(alumnoCurso.getCursoId());
		}
		
		if (tipoCurso.equals("1") || tipoCurso.equals("2") || tipoCurso.equals("7")){
			if (alumnoCurso.getTipoCalId().equals("1")||alumnoCurso.getTipoCalId().equals("2")||alumnoCurso.getTipoCalId().equals("4")){				
				row++; promedia = true;
				//Obtiene la nota a considerar en el c�lculo del promedio 
				
				if ( (alumnoCurso.getNotaExtra()==null||alumnoCurso.getNotaExtra().trim().equals("0") )){				
					if (alumnoCurso.getNota() != null) nota = Integer.parseInt(alumnoCurso.getNota().trim());				
				}else{
					nota =  Integer.parseInt(alumnoCurso.getNotaExtra().trim());				
				}
				
				creditos = Float.parseFloat(alumnoCurso.getCreditos().trim());
				
				// Promedio Ponderado
				sumaCredXNota 	+=  creditos * nota;
				sumaCred		+= creditos;

				// Media Aritm�tica
				sumaNotas 		+= nota;
			}
			//System.out.println("Promedia:"+alumnoCurso.getNota()+":"+nota+":"+sumaCredXNota+":"+sumaCred);
		}
		
		sumaCredTotal += Float.parseFloat(alumnoCurso.getCreditos().trim()); 
		
		String colorBien 	= "#37781B";
		String colorMal 	= "#840000";
		String color23 		= "#437EC5";

		String tipoCalNombre 	= mapTipoCal.get(alumnoCurso.getTipoCalId());		
		String atributosTDIns 	= "";
													
		MapaCurso mapaCurso = new MapaCurso();
		mapaCurso = mapCursos.get(alumnoCurso.getCursoId());
		Float notaAprobatoria = 0.0f;
		
		String extraT 	= "";
/*		
		if(mapTipoCurso.get(alumnoCurso.getCursoId()).equals("1") || mapTipoCurso.get(alumnoCurso.getCursoId()).equals("2") || mapTipoCurso.get(alumnoCurso.getCursoId()).equals("7")){			
			notaT = Float.parseFloat(notaMateria)/10+"";
			notaAprobatoria = Float.parseFloat(mapaCurso.getNotaAprobatoria().trim())/10;			
		}else{
			notaT = tipoCalNombre+"";
		}
*/		
		if(!alumnoCurso.getNotaExtra().equals("0") && alumnoCurso.getNotaExtra()!=null){
			if(escala.equals("10")){
				extraT = ""+Float.parseFloat(alumnoCurso.getNotaExtra().trim())/10;
			}else{
				extraT = ""+alumnoCurso.getNotaExtra();
			}
		}
		
		if(tipoCalNombre.equals("Passed")){
			atributosTDIns = "style='color:"+colorBien+";font-weight:bold;font-size: 130%;'";
		}
		else if(tipoCalNombre.equals("Failed")){
			atributosTDIns = "style='color:"+colorMal+";font-weight:bold;font-size: 130%;'";
		}
		else if(tipoCalNombre.equals("Enrolled")){
			atributosTDIns = "style='color:"+colorBien+";font-weight:bold;font-size: 130%;'";
		}
		else if(tipoCalNombre.equals("BA")){
			atributosTDIns = "class='linea'";
		}
		String cursoOrigen 			= "-";
		String cursoOrigenNombre 	= "-";
		if (mapMateriasOrigen.containsKey(alumnoCurso.getCursoCargaId())){
			cursoOrigen = mapMateriasOrigen.get(alumnoCurso.getCursoCargaId());
			if (!alumnoCurso.getCursoId().equals(cursoOrigen)){
				if (mapCursos.containsKey(cursoOrigen)){
					cursoOrigenNombre = "("+mapCursos.get(cursoOrigen).getNombreCurso()+")";
				}
			}else{
				cursoOrigenNombre = "-";
			}
		}	
		
		 String horarioMateria 	= "";
		 String horaInicio 		= "-";
		 String horaFinal 		= "-";
		 String nombreDia 		= "-";		 
		for(Hora hora : lisHoras){
			if(hora.getCursoCargaId().equals(alumnoCurso.getCursoCargaId())){
				if(hora.getDia().equals("2")){
					nombreDia = "Monday";					
				}else if(hora.getDia().equals("3")){
					nombreDia = "Tuesday";					
				}else if(hora.getDia().equals("4")){
					nombreDia = "Wednesday";					
				}else if(hora.getDia().equals("5")){
					nombreDia = "Thursday";					
				}else if(hora.getDia().equals("6")){
					nombreDia = "Friday";					
				}else if(hora.getDia().equals("7")){
					nombreDia = "Saturday";					
				}else{
					nombreDia = "Sunday";					
				}
				horaInicio 		= hora.getInicio().substring(1, 3)+":"+hora.getInicio().substring(3, 5);
				horaFinal 		= hora.getFin().substring(1, 3)+":"+hora.getFin().substring(3, 5);				
				horarioMateria 	+= nombreDia+"-->"+horaInicio+"-"+horaFinal+" ";			
			}
		}	
%>
		<tr style='cursor:pointer;'>
			<td><span class="badge bg-info" style="font-size:0.7rem;"><%=i+1%></span></td>
			<td width="40%">							
<%
		String optativa = alumnoCurso.getOptativa();
		if((diasRestantes<=0)&&(!optativa.equals("-"))){ %>
				<span class="badge bg-dark" data-bs-toggle="tooltip" data-bs-placement="right" title="Semester/Load" style="font-size:0.7rem;"><%=alumnoCurso.getCiclo()%></span>&nbsp;
				<span style="font-size:1rem;"><%=alumnoCurso.getNombreCurso()%> (<%=optativa%>)</span>&nbsp;
				<span class="badge bg-dark" data-bs-toggle="tooltip" data-bs-placement="left" title="Group" style="font-size:0.7rem;"><%=alumnoCurso.getGrupo()%></span>				 
				<span class="badge bg-dark" data-bs-toggle="tooltip" data-bs-placement="left" title="Block" style="font-size:0.7rem;"><%=alumnoCurso.getBloqueId()%></span>
				<span data-bs-toggle="tooltip" data-bs-placement="left" title="<%=horarioMateria%>" style="font-size:0.7rem;"><i class="far fa-calendar-alt"></i></span>&nbsp;
				<span data-bs-toggle="popover" data-bs-container="body" data-bs-placement="left" data-bs-content="<%=alumnoCurso.getCursoCargaId()%> - <%=cursoOrigenNombre%>"><i class="fas fa-barcode" style="color:blue"></i></span>&nbsp;				
		<% }else{ %>
				<span class="badge bg-dark" data-bs-toggle="tooltip" data-bs-placement="right" title="Semester/Load" style="font-size:0.7rem;"><%=alumnoCurso.getCiclo()%></span>&nbsp;
				<span style="font-size:1rem;"><%=alumnoCurso.getNombreCurso()%></span>&nbsp;
				<span class="badge bg-dark" data-bs-toggle="tooltip" data-bs-placement="left" title="Group" style="font-size:0.7rem;"><%=alumnoCurso.getGrupo()%></span>				 
				<span class="badge bg-dark" data-bs-toggle="tooltip" data-bs-placement="left" title="Block" style="font-size:0.7rem;"><%=alumnoCurso.getBloqueId()%></span>
				<span data-bs-toggle="tooltip" data-bs-placement="left" title="<%=horarioMateria%>"><i class="far fa-calendar-alt"></i></span>&nbsp;
				<span data-bs-toggle="popover" data-bs-container="body" data-bs-placement="left" data-bs-content="<%=alumnoCurso.getCursoCargaId()%> - <%=cursoOrigenNombre%>"><i class="fas fa-barcode" style="color:blue"></i></span>&nbsp;				
		<% } 
		CargaGrupo cargaGrupo = new CargaGrupo();
		cargaGrupo = mapCargaGrupo.get(alumnoCurso.getCursoCargaId());

		if(cargaGrupo.getComentario()!=null && !cargaGrupo.getComentario().trim().equals("")){ 
%>
			<span data-bs-toggle="popover" data-bs-container="body" data-bs-placement="left" data-bs-content="<%=cargaGrupo.getComentario()%>"><i class="fas fa-comment" style="color:blue"></i></span>				
<%		}
		
		if(alumnoCurso.getCorreccion().equals("S")){
%>
			<span title="Correction" class="badge bg-warning" style="font-size:0.5rem;">C</span>
<%
		}
		
		if(mapCargasAlumno.containsKey(cargaId)){
%>
			<a href="horariocimum?CargaId=<%=cargaId%>&CursoCargaId=<%=alumnoCurso.getCursoCargaId()%>&CodigoMaestro=<%=alumnoCurso.getMaestro()%>" title="Schedule"><i class="fas fa-calendar-alt" ></i></a>
<%
		}
		
		if(mostrar && materiasElegibles.contains(alumnoCurso.getCursoId()) && alumnoCurso.getTipoCalId().equals("I")){ %>
				&nbsp;
				<a class="btn btn-primary" href="elegirMateria?cursoCargaId=<%=alumnoCurso.getCursoCargaId()%>&cursoId=<%=alumnoCurso.getCursoId()%>&materia=<%=alumnoCurso.getNombreCurso()%>&profesor=<%=nombreMaestro %>&cargaId=<%=cargaId%>">
					<i class="icon-random"></i> <spring:message code="portal.alumno.materias.CambiarDeGrupo"/> <font color="blue">(<%=diasRestantes%> Days remaining)</font>
				</a>
<%		} %>
				<!-- END Elegir materias opcionales -->
<%		String colorModo 	= "";
		String modo			= "";
		if(cargaGrupo.getModo().equals("P")){
			modo  		= "Face to Face";
			colorModo 	= "bg-info";
		}else if(cargaGrupo.getModo().equals("V")){
			modo 		= "Online";
			colorModo 	= "bg-success";
		}else if(cargaGrupo.getModo().equals("H")){
			modo 		= "Hybrid";
			colorModo 	= "bg-warning";
		}else{
			modo 		= "Mixed";
			colorModo 	= "bg-dark";
		}
%>
				&nbsp;<span style="font-size:1rem;" class="badge <%=colorModo%>"><%=modo%></span>						  							
				<li style="text-indent:20;">
					<span style="font-size:0.8rem;"><%=mapCarreraNombre.get(alumnoCurso.getCarreraId())%>&nbsp;-&nbsp;<%=nombreMaestro%></span>
<%		
		Edo edo = new Edo();
		String evaldocente = "0";
		if ( lisEdoActual.size() > 0 ){
			for (Edo edoTemp : lisEdoActual){
				if (edoTemp.getModalidad().contains("-"+academico.getModalidadId()+"-")){
					evaldocente = edoTemp.getEdoId();
				}
			}			
		}
		boolean contestada = false;
		if (mapContestadas.containsKey(alumnoCurso.getCursoCargaId())){
			contestada = true;
		}
		
		String avance 		= "0";
		String textoAvance 	= "";
		if (mapAvanceEvaluaciones.containsKey(alumnoCurso.getCursoCargaId())){
			avance =  mapAvanceEvaluaciones.get(alumnoCurso.getCursoCargaId());
		}
		if (Double.valueOf(avance) >= 100){			
			textoAvance = "bg-success";
		}else if (Double.valueOf(avance) >= 40 && Double.valueOf(avance) < 100){		
			textoAvance = "bg-warning";
		}else{		
			textoAvance = "bg-danger";
		}
		
		float notaNumero = Float.parseFloat(notaMateria);
		if (plan.getEscala().equals("10")) notaNumero = notaNumero / 10;
		
		// CALCULA EL GPA PARA EL PROMEDIO DE LA MATERIA
		notaMateriaGPA = (Double.valueOf(notaMateria) / 100) * 4;

		// OBTIENE LA LETRA DE CALIFICACION PARA EL PROMEDIO
		String letraProm = "";
		if(mapaLetrasDeNotas.containsKey(notaMateria)){
			letraProm = mapaLetrasDeNotas.get(notaMateria);
		}else{
			letraProm = "X";			
		}
		
		// MUESTRA LA NOTA / LETRA CON COLOR DEPENDIENDO DEL VALOR
		String textoNotaMateria = "";
		if (Float.valueOf(notaMateria) >= 85){
			textoNotaMateria = "<span class='badge' style='background-color:#0EE8B0; color:black;'>"+letraProm+"</span>";
		}else if (Float.valueOf(notaMateria) >= 70 && Float.valueOf(notaMateria) < 85){
			textoNotaMateria = "<span class='badge' style='background-color:#FFFF85; color:black;'>"+letraProm+"</span>";
		}else{
			textoNotaMateria = "<span class='badge' style='background-color:#FF6A4D; color:black;'>"+letraProm+"</span>";
		}
		
		if (!evaldocente.equals("0")){
			edo = mapAllEdo.get(evaldocente);			
			if(	((String)session.getAttribute("codigoPersonal")).equals((String)session.getAttribute("codigoAlumno")) && contestada==false &&
					(edo.getModalidad().indexOf("-"+academico.getModalidadId()+"-") != -1 || edo.getModalidad().indexOf("-0-") != -1) &&
					(edo.getCargas().indexOf("-"+ alumnoCurso.getCargaId() +"-") != -1 || edo.getCargas().indexOf("-0-") != -1)
			){				
%>
				<a href="evaluaMaestro?edo=<%=edo.getEdoId() %>&cursoCarga=<%=alumnoCurso.getCursoCargaId() %>"> (Evaluate Professor)</a>
				
<%
			}else{
%>	
				(Evaluation: Answered)
<%
			}
		}		
%>	
				</li>
				</td>
				<td class="text-right"><span style="font-size:0.8rem;"><%=alumnoCurso.getCreditos()%> &nbsp;<% if (promedia==true) out.println("*");%></span></td>
		  		<td>
		  			<div class="progress" data-bs-toggle="tooltip" data-bs-placement="left" title="<%=avance%>%" onClick="document.location.href='detallecal?CursoCargaId=<%=alumnoCurso.getCursoCargaId()%>&Tipo=<%=alumnoCurso.getTipoCalId()%>'">
  						<div class="progress-bar  progress-bar-striped <%=textoAvance%>" role="progressbar" aria-valuenow="<%=avance%>" aria-valuemin="0" aria-valuemax="100" style="width: <%=avance%>%">
  						<%=Float.parseFloat(avance)>=30?"<span class='font-weight-bold'>"+avance+"%</span>":""%>
  					 	</div>
  					 	<%=Float.parseFloat(avance)<30?"<span class='font-weight-bold'>"+avance+"%</span>":""%>
					</div>					
		  		</td>		  		
				<td class="text-right"><span style="font-size:1rem;" onClick="document.location.href='detallecal?CursoCargaId=<%=alumnoCurso.getCursoCargaId()%>&Tipo=<%=alumnoCurso.getTipoCalId()%>'"><%=textoNotaMateria%></span></td>
<% 		if(!tipoCalNombre.equals("Enrolled")){	%>
				<td class="text-right"><span style="font-size:1rem;" onClick="document.location.href='detallecal?CursoCargaId=<%=alumnoCurso.getCursoCargaId()%>&Tipo=<%=alumnoCurso.getTipoCalId()%>'">NA</span></td>	
<% 		}else{	%>		
				<td class="text-right"><span style="font-size:1rem;" onClick="document.location.href='detallecal?CursoCargaId=<%=alumnoCurso.getCursoCargaId()%>&Tipo=<%=alumnoCurso.getTipoCalId()%>'"><%=textoMaximo%></span></td>
<% 		}	%>
		  		<td class="text-right"><span style="font-size:1rem;"><%=extraT%></span></td>
				<td <%=atributosTDIns%>><%=tipoCalNombre%></td>  			
<%
		String claveMateria 		= alumnoCurso.getCursoId().substring(8, 15);
		boolean esAptitud 			= mapTieneGrupo.containsKey(alumnoCurso.getCargaId()+claveMateria);
%>	
<!-- COLUMNA DE APTITUD FISICA -->			
<!-- 				<td> -->
<%			

			if (esAptitud  && codigoPersonal.equals(matricula) && tipoCalNombre.equals("Enrolled")){		
				
				//FALTA!!!
				boolean tieneGrupo 			= false;
				boolean gruposParaTodos 	= true;
				boolean gruposParaNuevos	= true;
				String grupoId = "0";
				if (mapaGruposDelAlumno.containsKey(alumnoCurso.getCursoCargaId())){
					grupoId = mapaGruposDelAlumno.get(alumnoCurso.getCursoCargaId());
				}
				
				if( mapaGruposDelAlumno.containsKey(alumnoCurso.getCursoCargaId()) ) tieneGrupo = true;
				if(mapGrupoActivoTodos.containsKey(alumnoCurso.getCargaId()+claveMateria)) gruposParaTodos = true;
				if(mapGrupoActivoNuevos.containsKey(alumnoCurso.getCargaId()+claveMateria)) gruposParaNuevos = true;
				
				// Si hay grupos activos para todos o solo hay para alumnos sin registro y el alumno no tiene grupo
				if (gruposParaTodos || (gruposParaNuevos && !tieneGrupo)){
%>
<%-- 					<a href="grupos?CargaId=<%=alumnoCurso.getCargaId()%>&CursoId=<%=claveMateria%>&CursoCargaId=<%=alumnoCurso.getCursoCargaId()%>" class="btn btn-sm btn-primary">Select</a> --%>
<%
				}
				
				if (tieneGrupo){
%>
<%-- 					&nbsp;&nbsp;<a href="amigosGrupo?GrupoId=<%=grupoId%>" class="btn btn-sm btn-success"><i class="fas fa-search"></i></a> --%>
<%				}
 			}	
					%>
<!-- 				</td> -->
		</tr>
<%
	}
%>
		<tr>
			<th colspan="2"><span style="font-size:1rem"><spring:message code="portal.alumno.materias.TotalDeCreditos"/></sapan></th>
			<th class="text-right"><span style="font-size:1rem"><%=sumaCredTotal%></span></th>
			<th colspan="5">&nbsp;</th>
		</tr>	
	</table>
<%
	if (sumaCredXNota >= 1){
		ponderado 	= Double.valueOf(String.valueOf(sumaCredXNota)).doubleValue() / Double.valueOf(String.valueOf(sumaCred)).doubleValue(); 
		promedio 	= Double.valueOf(String.valueOf(sumaNotas)).doubleValue() / Double.valueOf(String.valueOf(row)).doubleValue();
	}else{
		ponderado = 0.0; promedio = 0.0;
	}
	//System.out.println("Promedio:"+getFormato.format(promedio)+" - Ponderado:"+getFormato.format(ponderado));
%>
	<div class="alert alert-success d-flex align-items-center alert-alto-m">
    	<spring:message code="portal.alumno.materias.CredPromediados"/>: <b>[ <%= sumaCred%> ]</b> &nbsp; - &nbsp;
        <spring:message code="portal.alumno.materias.Media"/>:
<%	if(escala.equals("10")){%>
			<b>[ <%= getFormato.format((promedio)/10)%> ]</b>&nbsp; - &nbsp;
<%	}else{%>
			<b>[ <%= getFormato.format(promedio)%> ]</b>&nbsp; - &nbsp;
<%  }%>
    	<spring:message code="portal.alumno.materias.Ponderado"/>:
<%	if(escala.equals("10")){%>
			<b>[ <%=getFormato.format(ponderado/10)%> ]</b>
<%	}else{%>
            <b>[ <%=getFormato.format(ponderado)%> ]</b>
<%  }%>	
	</div>
<% }else{%>
	<div class="alert alert-warning">
		The <i>Load</i> is under review by the Academic Office. You're subjects will become available once the review period ends.
	</div>
<% }%> 
</div>
<script>	
	$(function () {
  		$('[data-bs-toggle="tooltip"]').tooltip();
  		$('[data-bs-toggle="popover"]').popover();
	});
</script>