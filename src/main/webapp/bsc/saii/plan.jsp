<%@ page import= "java.util.HashMap"%>
<%@page import="aca.saii.spring.SaiiPeriodo"%>
<%@page import="aca.saii.spring.SaiiDatosExtra"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	SaiiDatosExtra datosExtra		= (SaiiDatosExtra)request.getAttribute("datosExtra");
%>
<style>
.table th,
.table td {
	padding: 1em 2em;
}
.table th {
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

</style>
<script>
</script>
<%
	String periodoId 		= (String)session.getAttribute("periodoId");
	String planId 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
	String fecha 			= request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");
	String grupoId			= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");
	String planNombre 		= (String)request.getAttribute("planNombre");
	float promedioTotalM	= (float)request.getAttribute("promedioTotalM");
	float promedioTotalF	= (float)request.getAttribute("promedioTotalF");
	float promedioTotal	    = (float)request.getAttribute("promedioTotal");
	HashMap<String, Integer> mapaReporte = (HashMap<String, Integer>) request.getAttribute("mapaReporte");
	
%>
<body>
<div class="container-fluid">
<div class="lds-dual-ring"></div>
	<h2>Programa académico <small class="text-muted fs-4"> (<%=planId%> <%=planNombre%>)</small></h2>
	<div class="alert alert-info">
		<a href="reporte?PeriodoId=<%=periodoId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		<a href="datos?PlanId=<%=planId%>&Fecha=<%=fecha%>&GrupoId=<%=grupoId%>" class="btn btn-success">Datos Coordinador</a>
	</div>
	<h1>Datos Generales</h1>
	<br>
	
	<h3>Información del Plan y/o Programas</h3>	
	<br>
	<table class="table">
	<thead>
	<tr>
		<th>Total de estudiantes inscritos</th>
		<th>Estudiantes Hombres</th>
		<th>Estudiantes Mujeres</th>
		<th>Total de estudiantes por edad</th>
		<th>de 19 años o menos</th>
		<th>de 20 - 24 años</th>
		<th>de 25 - 29 años</th>
		<th>de 30 años o mas</th>
		<th>Estudiantes inscritos con discapacidad</th>
		<th>Estudiantes inscritos del extranjero</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>	
		<td><%=mapaReporte.get("total")%></td>
		<td><%=mapaReporte.get("totalMascu")%></td>
		<td><%=mapaReporte.get("totalFem")%></td>
		<td><%=mapaReporte.get("total")%></td>
		<td><%=mapaReporte.get("total19")%></td>
		<td><%=mapaReporte.get("total2024")%></td>
		<td><%=mapaReporte.get("total2529")%></td>
		<td><%=mapaReporte.get("total30")%></td>
		<td><%=datosExtra.getDIS_TOTAL()%></td>
		<td><%=mapaReporte.get("totalExtranjeros")%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	<br>
	<table class="table">
	<thead>
	<tr>
		<th>Total de estudiantes de nuevo ingreso</th>
		<th>Estudiantes Hombres</th>
		<th>Estudiantes Mujeres</th>
		<th>Total de estudiantes de nuevo ingreso por edad</th>
		<th>de 19 años o menos</th>
		<th>de 20 - 24 años</th>
		<th>de 25 - 29 años</th>
		<th>de 30 años o mas</th>
		<th>Estudiantes de nuevo ingreso con discapacidad</th>
		<th>Estudiantes de nuevo ingreso del extranjero</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=mapaReporte.get("inTotal")%></td>
		<td><%=mapaReporte.get("inTotalMasc")%></td>
		<td><%=mapaReporte.get("inTotalFem")%></td>
		<td><%=mapaReporte.get("inTotal")%></td>
		<td><%=mapaReporte.get("inTotal19")%></td>
		<td><%=mapaReporte.get("inTotal2024")%></td>
		<td><%=mapaReporte.get("inTotal2529")%></td>
		<td><%=mapaReporte.get("inTotal30")%></td>
		<td><%=datosExtra.getDIS_N_ING()%></td>
		<td><%=mapaReporte.get("inTotalExtranjeros")%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	<br>
	<table class="table">
	<thead>
	<tr>
		<th>Estudiantes provenientes de un nivel previo de la institución</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getNIVEL_PREV()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	<br>
	<h3>Planta Docente-Programas</h3>
	<br>
	<table class="table">
	<thead>
	<tr>
		<th>Total de personal docente asignado al programa</th>
		<th>Docentes hombres</th>
		<th>Docentes mujeres</th>
		<th>Total de docentes por edad</th>
		<th>de 30 años o menos</th>
		<th>de 31 a 49 años</th>
		<th>de 50 años o más</th>
		<th>Personal docente con discapacidad</th>
		<th>Personal docente extranjeros</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getDOCENTES_T()%></td>
		<td><%=datosExtra.getDOCENTES_H()%></td>
		<td><%=datosExtra.getDOCENTES_M()%></td>
		<td><%=datosExtra.getTOTAL_D_E()%></td>
		<td><%=datosExtra.getDOCENTES_30()%></td>
		<td><%=datosExtra.getDOCENTES_31_49()%></td>
		<td><%=datosExtra.getDOCENTES_50()%></td>
		<td><%=datosExtra.getDOCENTES_DISC()%></td>
		<td><%=datosExtra.getDOCENTES_EXT()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	<table class="table">
	<thead>
	<tr>
		<th>Total de personal docente por antigüedad</th>
		<th>de 0 a 5 años de antigüedad</th>
		<th>de 6 a 15 años de antigüedad</th>
		<th>de 16 a 25 años de antigüedad</th>
		<th>de 26 años o más de antigüedad</th>
		<th>Total de personal docente por formación</th>
		<th>Formación acádemica - Doctorado</th>
		<th>Formación acádemica - Maestría</th>
		<th>Formación acádemica - Especialidad</th>
		<th>Formación acádemica - Licenciatura</th>
		<th>Formación acádemica - Otro</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getTOTAL_D_A()%></td>
		<td><%=datosExtra.getDOCENTES_0_5()%></td>
		<td><%=datosExtra.getDOCENTES_6_15()%></td>
		<td><%=datosExtra.getDOCENTES_16_25()%></td>
		<td><%=datosExtra.getDOCENTES_26()%></td>
		<td><%=datosExtra.getTOTAL_D_F()%></td>
		<td><%=datosExtra.getDOCENTES_F_D()%></td>
		<td><%=datosExtra.getDOCENTES_F_M()%></td>
		<td><%=datosExtra.getDOCENTES_F_ESP()%></td>
		<td><%=datosExtra.getDOCENTES_F_LIC()%></td>
		<td><%=datosExtra.getDOCENTES_F_OTRO()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	<table class="table">
	<thead>
	<tr>
		<th>Total de personal docente por tiempo completo</th>
		<th>Docentes hombres</th>
		<th>Docentes mujeres</th>
		<th>Total de personal docente por medio tiempo</th>
		<th>Docentes hombres</th>
		<th>Docentes mujeres</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getDOCENTES_T_COMP()%></td>
		<td><%=datosExtra.getDOCENTES_COMP_H()%></td>
		<td><%=datosExtra.getDOCENTES_COMP_M()%></td>
		<td><%=datosExtra.getDOCENTES_MED_T()%></td>
		<td><%=datosExtra.getDOCENTES_MED_H()%></td>
		<td><%=datosExtra.getDOCENTES_MED_M()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	<table class="table">
	<thead>
	<tr>
		<th>Docentes que participaron en el Sistema Nacional de Investigación (S.N.I.)</th>
		<th>Con formación profesional en la docencia</th>
		<th>Docentes que colaboran en gestión acádemica</th>
		<th>Docentes en movilidad nacional</th>
		<th>Docentes en movilidad internacional</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getDOCENTES_SNI()%></td>
		<td><%=datosExtra.getDOCENTES_FPD()%></td>
		<td><%=datosExtra.getDOCENTES_CGA()%></td>
		<td><%=datosExtra.getDOCENTES_MOV_NAC()%></td>
		<td><%=datosExtra.getDOCENTES_MOV_INT()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	<br>
	<h3>Proceso Educativo</h3>
	<br>
	<table class="table">
	<thead>
	<tr>
		<th>¿El programa cuenta con exámen de admisión para primer ingreso?</th>
		<th>Aspirantes admitidos a la institución</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getQUEST_1()%></td>
		<td><%=datosExtra.getASPIRANTES_ADM()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	
	<table class="table">
	<thead>
	<tr>	
		<th>Total de estudiantes de reingreso</th>
		<th>Estudiantes Hombres</th>
		<th>Estudiantes Mujeres</th>
		<th>Total de estudiantes de reingreso por edad</th>
		<th>de 19 años o menos</th>
		<th>de 20 - 24 años</th>
		<th>de 25 - 29 años</th>
		<th>de 30 años o mas</th>
		<th>Estudiantes de reingreso con discapacidad</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=mapaReporte.get("reTotal")%></td>
		<td><%=mapaReporte.get("reTotalMasc")%></td>
		<td><%=mapaReporte.get("reTotalFem")%></td>
		<td><%=mapaReporte.get("reTotal")%></td>
		<td><%=mapaReporte.get("reTotal19")%></td>
		<td><%=mapaReporte.get("reTotal2024")%></td>
		<td><%=mapaReporte.get("reTotal2529")%></td>
		<td><%=mapaReporte.get("reTotal30")%></td>
		<td><%=datosExtra.getDIS_R_ING()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	
	<table class="table">
	<thead>
	<tr>	
		<th>Total de Estudiantes regulares</th>
		<th>Estudiantes Hombres</th>
		<th>Estudiantes Mujeres</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getREG_ALUM_T()%></td>
		<td><%=datosExtra.getREG_ALUM_H()%></td>
		<td><%=datosExtra.getREG_ALUM_M()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	
	<table class="table">
	<thead>
	<tr>
		<th>Total de Estudiantes becados o con ayuda financiera institucional</th>
		<th>Estudiantes hombres</th>
		<th>Estudiantes mujeres</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getBECADOS_T()%></td>
		<td><%=datosExtra.getBECADOS_H()%></td>
		<td><%=datosExtra.getBECADOS_M()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	
	<table class="table">
	<thead>
	<tr>
		<th>Total de Estudiantes en cursos remediales</th>
		<th>Estudiantes Hombres</th>
		<th>Estudiantes Mujeres</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getCUR_REM_T()%></td>
		<td><%=datosExtra.getCUR_REM_H()%></td>
		<td><%=datosExtra.getCUR_REM_M()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
		
	<table class="table">
	<thead>
	<tr>
		<th>Total de Estudiantes retenidos en segundo año</th>
		<th>Estudiantes Hombres</th>
		<th>Estudiantes Mujeres</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getRET_SEG_T()%></td>
		<td><%=datosExtra.getRET_SEG_H()%></td>
		<td><%=datosExtra.getRET_SEG_M()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
		
	<table class="table">
	<thead>
	<tr>	
		<th>Deserción total</th>
		<th>Estudiantes Hombres</th>
		<th>Estudiantes Mujeres</th>
		<th>Deserción total por edad</th>
		<th>de 19 años o menos</th>
		<th>de 20 - 24 años</th>
		<th>de 25 - 29 años</th>
		<th>de 30 años o mas</th>
		<th>Deserción con discapacidad</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getDESERT_T()%></td>
		<td><%=datosExtra.getDESERT_H()%></td>
		<td><%=datosExtra.getDESERT_M()%></td>
		<td><%=datosExtra.getTOTAL_DES_E()%></td>
		<td><%=datosExtra.getDESERT_19()%></td>
		<td><%=datosExtra.getDESERT_20_24()%></td>
		<td><%=datosExtra.getDESERT_25_29()%></td>
		<td><%=datosExtra.getDESERT_30()%></td>
		<td><%=datosExtra.getDESERT_DIS()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	
	<table class="table">
	<thead>
	<tr>	
		<th>Total de Estudiantes inscritos que reprobaron al menos una materia en el ciclo</th>
		<th>Estudiantes Hombres</th>
		<th>Estudiantes Mujeres</th>
		<th>Promedio general de calificaciones por ciclo escolar</th>
		<th>Promedio de estudiantes hombres</th>
		<th>Promedio de estudiantes mujeres</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>	
		<td><%=mapaReporte.get("reprobados")%></td>
		<td><%=mapaReporte.get("reprobadosMasc")%></td>
		<td><%=mapaReporte.get("reprobadosFem")%></td>
		<td><%=promedioTotal%></td>
		<td><%=promedioTotalM%></td>
		<td><%=promedioTotalF%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
		
	<table class="table">
	<thead>
	<tr>	
		<th>Total de Estudiantes que trabajan de manera simultanea a sus estudios</th>
		<th>Estudiantes Hombres</th>
		<th>Estudiantes Mujeres</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getTRAB_SIM_T()%></td>
		<td><%=datosExtra.getTRAB_SIM_H()%></td>
		<td><%=datosExtra.getTRAB_SIM_M()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
		
	<table class="table">
	<thead>
	<tr>	
		<th>Total de estudiantes en situación de movilidad nacional</th>
		<th>Estudiantes Hombres</th>
		<th>Estudiantes Mujeres</th>
		<th>Total de estudiantes en situación de movilidad nacional por edad</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getMOVIL_NAC_T()%></td>
		<td><%=datosExtra.getMOVIL_NAC_H()%></td>
		<td><%=datosExtra.getMOVIL_NAC_M()%></td>
		<td><%=datosExtra.getTOTAL_MN_E()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	<table class="table">
	<thead>
	<tr>	
		<th>de 19 años o menos</th>
		<th>de 20 - 24 años</th>
		<th>de 25 - 29 años</th>
		<th>de 30 años o mas</th>
		<th>Movilidad nacional con discapacidad</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getMOVIL_NAC_19()%></td>
		<td><%=datosExtra.getMOVIL_NAC_20_24()%></td>
		<td><%=datosExtra.getMOVIL_NAC_25_29()%></td>
		<td><%=datosExtra.getMOVIL_NAC_30()%></td>
		<td><%=datosExtra.getMOVIL_NAC_DIS()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	
	<table class="table">
	<thead>
	<tr>	
		<th>Total de Estudiantes en situación de movilidad internacional</th>
		<th>Estudiantes Hombres</th>
		<th>Estudiantes Mujeres</th>
		<th>Total de Estudiantes en situación de movilidad internacional por edad</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getMOVIL_INT_T()%></td>
		<td><%=datosExtra.getMOVIL_INT_H()%></td>
		<td><%=datosExtra.getMOVIL_INT_M()%></td>
		<td><%=datosExtra.getTOTAL_MI_E()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	<table class="table">
	<thead>
	<tr>	
		<th>de 19 años o menos</th>
		<th>de 20 - 24 años</th>
		<th>de 25 - 29 años</th>
		<th>de 30 años o mas</th>
		<th>Movilidad internacional con discapacidad</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getMOVIL_INT_19()%></td>
		<td><%=datosExtra.getMOVIL_INT_20_24()%></td>
		<td><%=datosExtra.getMOVIL_INT_25_29()%></td>
		<td><%=datosExtra.getMOVIL_INT_30()%></td>
		<td><%=datosExtra.getMOVIL_INT_DIS()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	
	<table class="table">
	<thead>
	<tr>	
		<th>Total de Estudiantes que participan en servicio a la comunidad</th>
		<th>Estudiantes Hombres</th>
		<th>Aumnos Mujeres</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getSERV_COM_T()%></td>
		<td><%=datosExtra.getSERV_COM_H()%></td>
		<td><%=datosExtra.getSERV_COM_M()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	
	<table class="table">
	<thead>
	<tr>	
		<th>Total de Estudiantes que han realizado su servicio social al momento de concluir sus creditos</th>
		<th>Estudiantes Hombres</th>
		<th>Estudiantes Mujeres</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getSERV_SOC_T()%></td>
		<td><%=datosExtra.getSERV_SOC_H()%></td>
		<td><%=datosExtra.getSERV_SOC_M()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	
	<table class="table">
	<thead>
	<tr>
		<th>¿Para acreditar el programa se requiere de prácticas profesionales?</th>
		<th>Estudiantes de reingreso del extranjero</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getQUEST_2()%></td>
		<td><%=mapaReporte.get("reTotalExtranjeros")%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	<br>
	<h3>Resultados Educativos</h3>
	<br>
	<table class="table">
	<thead>
	<tr>	
		<th>Total de Estudiantes inscritos en el último grado del nivel del programa acádemico</th>
		<th>Estudiantes Hombres</th>
		<th>Estudiantes Mujeres</th>
		<th>Total de Estudiantes inscritos en el último grado del nivel por edad</th>
		<th>de 19 años o menos</th>
		<th>de 20 - 24 años</th>
		<th>de 25 - 29 años</th>
		<th>de 30 años o mas</th>
		<th>Estudiantes inscritos en el último grado del nivel con discapacidad</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>	
		<td><%=mapaReporte.get("ultimoAñoTotal")%></td>
		<td><%=mapaReporte.get("ultimoAñoTotalMascu")%></td>
		<td><%=mapaReporte.get("ultimoAñoTotalFem")%></td>
		<td><%=mapaReporte.get("ultimoAñoTotal")%></td>
		<td><%=mapaReporte.get("ultimoAñoTotal19")%></td>
		<td><%=mapaReporte.get("ultimoAñoTotal2024")%></td>
		<td><%=mapaReporte.get("ultimoAñoTotal2529")%></td>
		<td><%=mapaReporte.get("ultimoAñoTotal30")%></td>
		<td><%=datosExtra.getULT_GRAD_DIS()%></td>
		<td></td>
		
	</tr>
	</tbody>
	</table>
	
	<table class="table">
	<thead>
	<tr>
		<th>Total de estudiantes egresados</th>
		<th>Estudiantes Hombres</th>
		<th>Estudiantes Mujeres</th>
		<th>Total de estudiantes egresados por edad</th>
		<th>de 19 años o menos</th>
		<th>de 20 - 24 años</th>
		<th>de 25 - 29 años</th>
		<th>de 30 años o mas</th>
		<th>Estudiantes egresados con discapacidad</th>
		<th>Promedio total de calificaciones de los egresados</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getEGRESADOS_T()%></td>
		<td><%=datosExtra.getEGRESADOS_H()%></td>
		<td><%=datosExtra.getEGRESADOS_M()%></td>
		<td><%=datosExtra.getTOTAL_EGRE_E()%></td>
		<td><%=datosExtra.getEGRESADOS_19()%></td>
		<td><%=datosExtra.getEGRESADOS_20_24()%></td>
		<td><%=datosExtra.getEGRESADOS_25_29()%></td>
		<td><%=datosExtra.getEGRESADOS_30()%></td>
		<td><%=datosExtra.getEGRESADOS_DIS()%></td>
		<td><%=datosExtra.getPROM_EGRE()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	
	<table class="table">
	<thead>
	<tr>	
		<th>Estudiantes que realizan EGEL o equivalente</th>
		<th>Estudiantes que aprueban EGEL o equivalente con puntje sobresaliente</th>
		<th>Estudiantes que se titulan del programa</th>
		<th>Estudiantes que concluyen el programa(100% de creditos, servicio social, etc.)</th>
		<th>¿Es un programa con doble titulación?</th>
		<th>¿Cuenta con programa de seguimiento de egresados?</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getEGEL()%></td>
		<td><%=datosExtra.getEGEL_SOB()%></td>
		<td><%=datosExtra.getTITU_PROG()%></td>
		<td><%=datosExtra.getCONC()%></td>
		<td><%=datosExtra.getQUEST_3()%></td>
		<td><%=datosExtra.getQUEST_4()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	
	<table class="table">
	<thead>
	<tr>	
		<th>Total de egresados que han conseguido trabajo en un lapso no mayor a 9 meses posteriores a su egreso</th>
		<th>Egresados Hombres</th>
		<th>Egresados Mujeres</th>
		<th>Total de egresados que han conseguido trabajo por edad</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getEGRE_TRAB_T()%></td>
		<td><%=datosExtra.getEGRE_TRAB_H()%></td>
		<td><%=datosExtra.getEGRE_TRAB_M()%></td>
		<td><%=datosExtra.getTOTAL_TRAB_E()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
	<table class="table">
	<thead>
	<tr>	
		<th>de 19 años o menos</th>
		<th>de 20 - 24 años</th>
		<th>de 25 - 29 años</th>
		<th>de 30 años o mas</th>
		<th>Egresados con discapacidad que han conseguido trabajo</th>
		<th>Porcentaje de egresados del programa que trabajan afines a sus estudios</th>
		<th></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=datosExtra.getEGRE_TRAB_19()%></td>
		<td><%=datosExtra.getEGRE_TRAB_20_24()%></td>
		<td><%=datosExtra.getEGRE_TRAB_25_29()%></td>
		<td><%=datosExtra.getEGRE_TRAB_30()%></td>
		<td><%=datosExtra.getEGRE_TRAB_DIS()%></td>
		<td><%=datosExtra.getPOR_EGRE_TRAB()%></td>
		<td></td>
	</tr>
	</tbody>
	</table>
		
</div>
</body>