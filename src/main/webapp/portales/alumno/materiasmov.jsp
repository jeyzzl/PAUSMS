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
<%@ include file= "menumov.jsp" %>
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
	
	String cierraPortal 				= (String)session.getAttribute("cancelaPortal")==null?"N":(String)session.getAttribute("cancelaPortal");
	
	String fechaHoy 					= aca.util.Fecha.getHoy();
	
	String matricula 					= (String) session.getAttribute("codigoAlumno");
	String codigoPersonal				= (String) session.getAttribute("codigoPersonal");
	String cargaId						= (String) session.getAttribute("cargaId");
	String bloqueId						= (String) session.getAttribute("bloqueId");
	
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
	
	ComAutorizacion comAutorizacion		= (ComAutorizacion) request.getAttribute("comAutorizacion");

	List<CargaGrupoProgramacion> lisProgra 			= (List<CargaGrupoProgramacion>) request.getAttribute("lisProgra");
	List<AlumnoCurso> lisAlumnos 					= (List<AlumnoCurso>) request.getAttribute("lisAlumnos");
	List<String> materiasElegibles					= (List<String>) request.getAttribute("materiasElegibles");
	List<Carga> lisCarga 							= (List<Carga>) request.getAttribute("lisCarga");
	List<CargaBloque> lisBloques					= (List<CargaBloque>) request.getAttribute("lisBloques");
	
	// Lista de cursos del alumno
	List<AlumnoCurso> lisCursos 					= (List<AlumnoCurso>) request.getAttribute("lisCursos");
	List<Edo> lisEdoActual 							= (List<Edo>) request.getAttribute("lisEdoActual");
	
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
	HashMap<String,String> mapAvanceEvaluaciones 	= (HashMap<String,String>) request.getAttribute("mapAvanceEvaluaciones");
	
	// Map de cargas autorizadas del alumno
	HashMap<String, String> mapCargasAlumno		= (HashMap<String,String>) request.getAttribute("mapCargasAlumno");
	
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
	<h1>Materias Registradas 
	<small class="text-muted"style="font-size:30px"> 
	(<%=matricula%> - <%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%> ) 
	</small>
	</h1>
	<form name="frmMaterias" action="materiasmov" mthod="post">
	<div class="alert alert-info" style="font-size:25px">
		Carga:
		<select name="CargaId" onChange="javascritp:ActualizarCarga()" class="input" style="width:370px;">
<%				
		int i=0;		
		for( i=0;i<lisCarga.size();i++){
			Carga carga = (Carga) lisCarga.get(i);			
			if (carga.getCargaId().equals(cargaId)){				
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
		<select name="BloqueId" style="width:350px;" onChange="javascritp:ActualizarBloque()">
<%
	for (CargaBloque bloque: lisBloques){
%>
			<option value="<%=bloque.getBloqueId()%>" <%=bloqueId.equals(bloque.getBloqueId())?"selected":""%>><%=bloque.getBloqueId()%>-<%=bloque.getNombreBloque()%></option>
<%		
	}	
%>			
		</select>			
	</div>
	</form>	
	<div class="alert alert-success d-flex align-items-center alert-alto-m mt-2" style="height:4%"><font size="5px">
		<spring:message code="aca.Plan" />: [<b><%=planId%></b>]&nbsp;&nbsp;
		<spring:message code="aca.Carrera" />: [<b><%=carreraNombre%></b>]&nbsp;&nbsp;
		<spring:message code="aca.Modalidad"/>: [<b><%=academico.getModalidad()%></b>]
	</font></div>
<%
	if(estudiosCancelados){
%>	
	<div class="alert alert-danger">
		<b><spring:message code="datosAlumno.portal.Nota13"/>:</b><br><%=cancelaEstudio.getComentario() %>
	</div>		
<% 	}%>	
	<table class="table table-fullcondensed table-bordered">
		<tr>
      		<th style="font-size:20pt;" colspan="2"><spring:message code="aca.Cursos"/></th>
<%--           	<th style="font-size:10pt;" class="right"><spring:message code="aca.Creditos"/></th> --%>
<!--           	<th style="font-size:10pt;">Avance</th>          	 -->
          	<th style="font-size:20pt;" class="right"><spring:message code="aca.Nota"/></th> 
<!--           	<th style="font-size:10pt;" class="right">Maximo</th>       	 -->
          	<th style="font-size:20pt;" class="right"><spring:message code="aca.Extra"/></th>          	
          	<th style="font-size:20pt;"><spring:message code="aca.Tipo"/></th>
<!--           	<th style="font-size:10pt;">Ap. Física</th> -->
		</tr>
<%
	String notaMateria 	= "";
	AlumnoCurso alumnoCurso = new AlumnoCurso();
	aca.kardex.EvaluacionUtil krdxEvaluacionUtil = new aca.kardex.EvaluacionUtil();			
	for(i=0; i<lisCursos.size(); i++){
		alumnoCurso = (AlumnoCurso) lisCursos.get(i);			
		nombreMaestro = mapMaestroNombre.get(alumnoCurso.getMaestro());			
		notaMateria = alumnoCurso.getNota();
		float notaMaxima = 0;
		String textoMaximo = "<sapan class='badge bg-dark'>"+notaMaxima+"</span>";
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
			
			notaMaxima = puntosGanados + (100-puntosEvaluados);
			if (notaMaxima>0) notaMaxima = notaMaxima/10;			
		}
		
		if (notaMaxima >= 8.5){
			textoMaximo = "<span class='badge' style='background-color:#0EE8B0; color:black;'>"+getFormato.format(notaMaxima)+"</span>";
		}else if  (notaMaxima >=7.0 && notaMaxima < 8.5){
			textoMaximo = "<span class='badge' style='background-color:#FFFF85; color:black;'>"+getFormato.format(notaMaxima)+"</span>";
		}else{
			textoMaximo = "<span class='badge' style='background-color:#FF6A4D; color:black;'>"+getFormato.format(notaMaxima)+"</span>";
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
				//Obtiene la nota a considerar en el cálculo del promedio 
				
				if ( (alumnoCurso.getNotaExtra()==null||alumnoCurso.getNotaExtra().trim().equals("0") )){				
					if (alumnoCurso.getNota() != null) nota = Integer.parseInt(alumnoCurso.getNota().trim());				
				}else{
					nota =  Integer.parseInt(alumnoCurso.getNotaExtra().trim());				
				}
				
				creditos = Float.parseFloat(alumnoCurso.getCreditos().trim());
				
				// Promedio Ponderado
				sumaCredXNota 	+=  creditos * nota;
				sumaCred		+= creditos;

				// Media Aritmética
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
		
		if(!alumnoCurso.getNotaExtra().equals("0") && alumnoCurso.getNotaExtra()!=null){
			if(escala.equals("10")){
				extraT = ""+Float.parseFloat(alumnoCurso.getNotaExtra().trim())/10;
			}else{
				extraT = ""+alumnoCurso.getNotaExtra();
			}
		}
		
		if(tipoCalNombre.equals("AC")){
			atributosTDIns = "style='color:"+colorBien+";font-weight:bold;font-size: 130%;'";
		}
		else if(tipoCalNombre.equals("NA")){
			atributosTDIns = "style='color:"+colorMal+";font-weight:bold;font-size: 130%;'";
		}
		else if(tipoCalNombre.equals("Inscrito")){
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
%>
		<tr>
			<td><span class="badge bg-info font22"><font face="Arial"><%=i+1%></font></span></td>
			<td width="40%">							
<%
		String optativa = alumnoCurso.getOptativa();
		if((diasRestantes<=0)&&(!optativa.equals("-"))){ %>
				<b><p class="font22"><%=alumnoCurso.getNombreCurso()%></p></b>
		<%}else{ %>
				<b><p class="font22"><%=alumnoCurso.getNombreCurso()%></p></b>
		<%}
		
		float notaNumero = Float.parseFloat(notaMateria);
		if (plan.getEscala().equals("10")) notaNumero = notaNumero / 10;
		
		String textoNotaMateria = "";
		if (Float.valueOf(notaMateria) >= 85){
			textoNotaMateria = "<span class='badge' style='background-color:#0EE8B0; color:black;'>"+getFormato.format(notaNumero)+"</span>";
		}else if (Float.valueOf(notaMateria) >= 70 && Float.valueOf(notaMateria) < 85){
			textoNotaMateria = "<span class='badge' style='background-color:#FFFF85; color:black;'>"+getFormato.format(notaNumero)+"</span>";
		}else{
			textoNotaMateria = "<span class='badge' style='background-color:#FF6A4D; color:black;'>"+getFormato.format(notaNumero)+"</span>";
		}		
%>				
				</td>
				<td width="7%" class="text-right font22"><%=textoNotaMateria%></td>
		  		<td width="7%" class="text-right font22"><%=extraT%></td>
				<td width="15%" class="font22"><%=tipoCalNombre%></td>	  			
<%
		String claveMateria 		= alumnoCurso.getCursoId().substring(8, 15);
		boolean esAptitud 			= mapTieneGrupo.containsKey(alumnoCurso.getCargaId()+claveMateria);
%>				
				<td>
<%			

			if (esAptitud  && codigoPersonal.equals(matricula) && tipoCalNombre.equals("IN")){		
				
				//FALTA!!!
				boolean tieneGrupo 			= false;
				boolean gruposParaTodos 	= true;
				boolean gruposParaNuevos	= true;
				
				if(mapaGruposDelAlumno.containsKey(alumnoCurso.getCursoCargaId()) && mapTieneGrupo.containsKey(alumnoCurso.getCargaId()+claveMateria) ) tieneGrupo = true;
				if(mapGrupoActivoTodos.containsKey(alumnoCurso.getCargaId()+claveMateria)) gruposParaTodos = true;
				if(mapGrupoActivoNuevos.containsKey(alumnoCurso.getCargaId()+claveMateria)) gruposParaNuevos = true;
				
				// Si hay grupos activos para todos o solo hay para alumnos sin registro y el alumno no tiene grupo
				if (gruposParaTodos || (gruposParaNuevos && !tieneGrupo)){
%>
<%-- 					<a href="grupos?CargaId=<%=alumnoCurso.getCargaId()%>&CursoId=<%=claveMateria%>" class="btn btn-primary"><i class="icon-white icon-hand-up"></i> Elegir Grupo</a> --%>
<%
				}
				
				if (tieneGrupo){
%>
					&nbsp;&nbsp;<a href="grupoInscrito?CargaId=<%=alumnoCurso.getCargaId()%>&CursoId=<%=claveMateria%>" class="btn btn-primary"><i class="icon-white icon-hand-up"></i>Grupo</a>
<%				}
			}	
					%>
				</td>
		</tr>
<%
	}
%>
	</table>
<%
	ponderado 	= Double.valueOf(String.valueOf(sumaCredXNota)).doubleValue()/ Double.valueOf(String.valueOf(sumaCred)).doubleValue(); 
	promedio 	= Double.valueOf(String.valueOf(sumaNotas)).doubleValue()/ Double.valueOf(String.valueOf(row)).doubleValue();		
	//System.out.println("Promedio:"+getFormato.format(promedio)+" - Ponderado:"+getFormato.format(ponderado));
%>
</div>
<script>
	$('.nav-tabs').find('.materias').addClass('active');
</script>
	
