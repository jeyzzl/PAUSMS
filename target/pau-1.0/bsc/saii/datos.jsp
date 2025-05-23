<%@page import="aca.saii.spring.SaiiPeriodo"%>
<%@page import="aca.saii.spring.SaiiDatosExtra"%>
<%@ page import= "java.util.HashMap"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<%
	String mensaje 				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	SaiiDatosExtra datosExtra	= (SaiiDatosExtra)request.getAttribute("datosExtra");
	
	String periodoId 			= (String)session.getAttribute("periodoId");
	String planId 				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
	String fecha 				= request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");
	String grupoId				= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");
	String planNombre			= (String)request.getAttribute("planNombre");
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

<body>
<div class="container-fluid">
	<form name="frmDatos" action="guardar" method="POST">
	<input id="planId" type="text" name="PlanId" style="display:none" value="<%=planId%>">
	<input id="Fecha" type="text" name="Fecha" style="display:none" value="<%=fecha%>">		
	<input id="GrupoId" type="text" name="GrupoId" style="display:none" value="<%=grupoId%>">
	<h2>Insertar Datos<small class="text-muted fs-4"> (<%=planId%> - <%=planNombre%> - <%=fecha%>)</small></h2>
	<div class="alert alert-info">
		<a href="plan?PlanId=<%=planId%>&Fecha=<%=fecha%>&PeriodoId=<%=periodoId%>&GrupoId=<%=grupoId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		<input type="submit"  value="Guardar">
	</div>		
	<div style="display:none" id="mensaje" class="alert alert-info"></div>
	
		<h3>Información del Plan y/o Programas</h3>
		<table class="table">
			<thead>
			<tr>
				<th>Estudiantes inscritos con discapacidad</th>
				<th>Estudiantes de nuevo ingreso con discapacidad</th>
				<th>Estudiantes provenientes de un nivel previo de la institución</th>
				<th></th>
			</tr>
			</thead>
			<tbody>
			<tr>	
				<td><input id="DIS_TOTAL" type="text" name="DIS_TOTAL" value="<%=datosExtra.getDIS_TOTAL()%>"></td>			
				<td><input id="DIS_N_ING" type="text" name="DIS_N_ING" value="<%=datosExtra.getDIS_N_ING()%>"></td>
				<td><input id="NIVEL_PREV" type="text" name="NIVEL_PREV" value="<%=datosExtra.getNIVEL_PREV()%>"></td>
				<td></td>
			</tr>
			</tbody>
		</table>
		
		<h3>Planta Docente-Programas</h3>
		<table class="table">
		<thead>
		<tr>
			<th><b>Total de personal docente asignado al programa</b></th>
			<th>Docentes hombres</th>
			<th>Docentes mujeres</th>
			<th><b>Total de docentes por edad</b></th>
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
			<td><input id="DOCENTES_T" type="text" name="DOCENTES_T" value="<%=datosExtra.getDOCENTES_T()%>"></td>
			<td><input id="DOCENTES_H" type="text" name="DOCENTES_H" value="<%=datosExtra.getDOCENTES_H()%>"></td>
			<td><input id="DOCENTES_M" type="text" name="DOCENTES_M" value="<%=datosExtra.getDOCENTES_M()%>"></td>
			<td><input id="TOTAL_D_E" type="text" name="TOTAL_D_E" value="<%=datosExtra.getTOTAL_D_E()%>"></td>
			<td><input id="DOCENTES_30" type="text" name="DOCENTES_30" value="<%=datosExtra.getDOCENTES_30()%>"></td>
			<td><input id="DOCENTES_31_49" type="text" name="DOCENTES_31_49" value="<%=datosExtra.getDOCENTES_31_49()%>"></td>
			<td><input id="DOCENTES_50" type="text" name="DOCENTES_50" value="<%=datosExtra.getDOCENTES_50()%>"></td>
			<td><input id="DOCENTES_DISC" type="text" name="DOCENTES_DISC" value="<%=datosExtra.getDOCENTES_DISC()%>"></td>
			<td><input id="DOCENTES_EXT" type="text" name="DOCENTES_EXT" value="<%=datosExtra.getDOCENTES_EXT()%>"></td>
			<td></td>
		</tr>
		</tbody>
		</table>
		<table class="table">
		<thead>
		<tr>
			<th><b>Total de personal docente por antigüedad</b></th>
			<th>de 0 a 5 años de antigüedad</th>
			<th>de 6 a 15 años de antigüedad</th>
			<th>de 16 a 25 años de antigüedad</th>
			<th>de 26 años o más de antigüedad</th>
			<th><b>Total de personal docente por formación</b></th>
			<th>Formación acádemica - Doctorado</th>
			<th>Formación acádemica - Maestría</th>
			<th>Formación acádemica - Especialidad</th>
			<th></th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td><input id="TOTAL_D_A" type="text" name="TOTAL_D_A" value="<%=datosExtra.getTOTAL_D_A()%>"></td>
			<td><input id="DOCENTES_0_5" type="text" name="DOCENTES_0_5" value="<%=datosExtra.getDOCENTES_0_5()%>"></td>
			<td><input id="DOCENTES_6_15" type="text" name="DOCENTES_6_15" value="<%=datosExtra.getDOCENTES_6_15()%>"></td>
			<td><input id="DOCENTES_16_25" type="text" name="DOCENTES_16_25" value="<%=datosExtra.getDOCENTES_16_25()%>"></td>
			<td><input id="DOCENTES_26" type="text" name="DOCENTES_26" value="<%=datosExtra.getDOCENTES_26()%>"></td>
			<td><input id="TOTAL_D_F" type="text" name="TOTAL_D_F" value="<%=datosExtra.getTOTAL_D_F()%>"></td>
			<td><input id="DOCENTES_F_D" type="text" name="DOCENTES_F_D" value="<%=datosExtra.getDOCENTES_F_D()%>"></td>
			<td><input id="DOCENTES_F_M" type="text" name="DOCENTES_F_M" value="<%=datosExtra.getDOCENTES_F_M()%>"></td>
			<td><input id="DOCENTES_F_ESP" type="text" name="DOCENTES_F_ESP" value="<%=datosExtra.getDOCENTES_F_ESP()%>"></td>
			
			<td></td>
		</tr>
		</tbody>
		</table>
		<table class="table">
		<thead>
		<tr>
			<th>Formación acádemica - Licenciatura</th>
			<th>Formación acádemica - Otro</th>
			<th><b>Total de personal docente por tiempo completo</b></th>
			<th>Docentes hombres</th>
			<th>Docentes mujeres</th>
			<th><b>Total de personal docente por medio tiempo</b></th>
			<th>Docentes hombres</th>
			
			<th></th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td><input id="DOCENTES_F_LIC" type="text" name="DOCENTES_F_LIC" value="<%=datosExtra.getDOCENTES_F_LIC()%>"></td>
			<td><input id="DOCENTES_F_OTRO" type="text" name="DOCENTES_F_OTRO" value="<%=datosExtra.getDOCENTES_F_OTRO()%>"></td>
			<td><input id="DOCENTES_T_COMP" type="text" name="DOCENTES_T_COMP" value="<%=datosExtra.getDOCENTES_T_COMP()%>"></td>
			<td><input id="DOCENTES_COMP_H" type="text" name="DOCENTES_COMP_H" value="<%=datosExtra.getDOCENTES_COMP_H()%>"></td>
			<td><input id="DOCENTES_COMP_M" type="text" name="DOCENTES_COMP_M" value="<%=datosExtra.getDOCENTES_COMP_M()%>"></td>
			<td><input id="DOCENTES_MED_T" type="text" name="DOCENTES_MED_T" value="<%=datosExtra.getDOCENTES_MED_T()%>"></td>
			<td><input id="DOCENTES_MED_H" type="text" name="DOCENTES_MED_H" value="<%=datosExtra.getDOCENTES_MED_H()%>"></td>
			
			<td></td>
		</tr>
		</tbody>
		</table>
		<table class="table">
		<thead>
		<tr>
			<th>Docentes mujeres</th>
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
			<td><input id="DOCENTES_MED_M" type="text" name="DOCENTES_MED_M" value="<%=datosExtra.getDOCENTES_MED_M()%>"></td>
			<td><input id="DOCENTES_SNI" type=text" name="DOCENTES_SNI" value="<%=datosExtra.getDOCENTES_SNI()%>"></td>
			<td><input id="DOCENTES_FPD" type=text" name="DOCENTES_FPD" value="<%=datosExtra.getDOCENTES_FPD()%>"></td>
			<td><input id="DOCENTES_CGA" type=text" name="DOCENTES_CGA" value="<%=datosExtra.getDOCENTES_CGA()%>"></td>
			<td><input id="DOCENTES_MOV_NAC" type=text" name="DOCENTES_MOV_NAC" value="<%=datosExtra.getDOCENTES_MOV_NAC()%>"></td>
			<td><input id="DOCENTES_MOV_INT" type=text" name="DOCENTES_MOV_INT" value="<%=datosExtra.getDOCENTES_MOV_INT()%>"></td>
			<td></td>
		</tr>
		</tbody>
		</table>
		
		<h3>Proceso Educativo</h3>
		<table class="table">
		<thead>
		<tr>
			<th><b>¿El programa cuenta con exámen de admisión para primer ingreso? (SI / NO)</b></th>
			<th>Aspirantes admitidos</th>
			<th>Estudiantes de reingreso con discapacidad</th>
			<th><b>Total de estudiantes regulares</b></th>
			<th>Estudiantes Hombres</th>
			<th>Estudiantes Mujeres</th>
			<th></th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td><input id="QUEST_1" type=text" name="QUEST_1" value="<%=datosExtra.getQUEST_1()%>"></td>
			<td><input id="ASPIRANTES_ADM" type=text" name="ASPIRANTES_ADM" value="<%=datosExtra.getASPIRANTES_ADM()%>"></td>
			<td><input id="DIS_R_ING" type=text" name="DIS_R_ING" value="<%=datosExtra.getDIS_R_ING()%>"></td>
			<td><input id="REG_ALUM_T" type="text" name="REG_ALUM_T" value="<%=datosExtra.getREG_ALUM_T()%>"></td>
			<td><input id="REG_ALUM_H" type="text" name="REG_ALUM_H" value="<%=datosExtra.getREG_ALUM_H()%>"></td>
			<td><input id="REG_ALUM_M" type="text" name="REG_ALUM_M" value="<%=datosExtra.getREG_ALUM_M()%>"></td>
			<td></td>
		</tr>
		</tbody>
		</table>
		<table class="table">
		<thead>
		<tr>	
			<th><b>Total de Estudiantes becados o con ayuda financiera institucional</b></th>
			<th>Estudiantes hombres</th>
			<th>Estudiantes mujeres</th>
			<th><b>Total de Estudiantes en cursos remediales</b></th>
			<th>Estudiantes Hombres</th>
			<th>Estudiantes Mujeres</th>
			<th></th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td><input id="BECADOS_T" type=text" name="BECADOS_T" value="<%=datosExtra.getBECADOS_T()%>"></td>
			<td><input id="BECADOS_H" type=text" name="BECADOS_H" value="<%=datosExtra.getBECADOS_H()%>"></td>
			<td><input id="BECADOS_M" type=text" name="BECADOS_M" value="<%=datosExtra.getBECADOS_M()%>"></td>
			<td><input id="CUR_REM_T" type=text" name="CUR_REM_T" value="<%=datosExtra.getCUR_REM_T()%>"></td>
			<td><input id="CUR_REM_H" type=text" name="CUR_REM_H" value="<%=datosExtra.getCUR_REM_H()%>"></td>
			<td><input id="CUR_REM_M" type=text" name="CUR_REM_M" value="<%=datosExtra.getCUR_REM_M()%>"></td>
			<td></td>
		</tr>
		</tbody>
		</table>
		<table class="table">
		<thead>
		<tr>	
			<th><b>Total de Estudiantes retenidos en segundo año</b></th>
			<th>Estudiantes Hombres</th>
			<th>Estudiantes Mujeres</th>
			<th><b>Deserción total</b></th>
			<th>Estudiantes Hombres</th>
			<th>Estudiantes Mujeres</th>
			<th></th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td><input id="RET_SEG_T" type="text" name="RET_SEG_T" value="<%=datosExtra.getRET_SEG_T()%>"></td>
			<td><input id="RET_SEG_H" type="text" name="RET_SEG_H" value="<%=datosExtra.getRET_SEG_H()%>"></td>
			<td><input id="RET_SEG_M" type="text" name="RET_SEG_M" value="<%=datosExtra.getRET_SEG_M()%>"></td>
			<td><input id="DESERT_T" type="text" name="DESERT_T" value="<%=datosExtra.getDESERT_T()%>"></td>
			<td><input id="DESERT_H" type="text" name="DESERT_H" value="<%=datosExtra.getDESERT_H()%>"></td>
			<td><input id="DESERT_M" type="text" name="DESERT_M" value="<%=datosExtra.getDESERT_M()%>"></td>
			<td></td>
		</tr>
		</tbody>
		</table>
		<table class="table">
		<thead>
		<tr>
			<th><b>Deserción total por edad</b></th>
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
			<td><input id="TOTAL_DES_E" type=text" name="TOTAL_DES_E" value="<%=datosExtra.getTOTAL_DES_E()%>"></td>
			<td><input id="DESERT_19" type="text" name="DESERT_19" value="<%=datosExtra.getDESERT_19()%>"></td>
			<td><input id="DESERT_20_24" type="text" name="DESERT_20_24" value="<%=datosExtra.getDESERT_20_24()%>"></td>
			<td><input id="DESERT_25_29" type="text" name="DESERT_25_29" value="<%=datosExtra.getDESERT_25_29()%>"></td>
			<td><input id="DESERT_30" type="text" name="DESERT_30" value="<%=datosExtra.getDESERT_30()%>"></td>
			<td><input id="DESERT_DIS" type=text" name="DESERT_DIS" value="<%=datosExtra.getDESERT_DIS()%>"></td>
			<td></td>
		</tr>
		</tbody>
		</table>
		<table class="table">
		<thead>
		<tr>
			<th><b>Total de Estudiantes que trabajan de manera simultanea a sus estudios</b></th>
			<th>Estudiantes Hombres</th>
			<th>Estudiantes Mujeres</th>
			<th><b>Total de estudiantes en situación de movilidad nacional</b></th>
			<th>Estudiantes Hombres</th>
			<th>Estudiantes Mujeres</th>
			<th><b>Total de estudiantes en situación de movilidad nacional por edad</b></th>
			<th></th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td><input id="TRAB_SIM_T" type="text" name="TRAB_SIM_T" value="<%=datosExtra.getTRAB_SIM_T()%>"></td>
			<td><input id="TRAB_SIM_H" type="text" name="TRAB_SIM_H" value="<%=datosExtra.getTRAB_SIM_H()%>"></td>
			<td><input id="TRAB_SIM_M" type="text" name="TRAB_SIM_M" value="<%=datosExtra.getTRAB_SIM_M()%>"></td>
			<td><input id="MOVIL_NAC_T" type="text" name="MOVIL_NAC_T" value="<%=datosExtra.getMOVIL_NAC_T()%>"></td>
			<td><input id="MOVIL_NAC_H" type="text" name="MOVIL_NAC_H" value="<%=datosExtra.getMOVIL_NAC_H()%>"></td>
			<td><input id="MOVIL_NAC_M" type="text" name="MOVIL_NAC_M" value="<%=datosExtra.getMOVIL_NAC_M()%>"></td>
			<td><input id="TOTAL_MN_E" type=text" name="TOTAL_MN_E" value="<%=datosExtra.getTOTAL_MN_E()%>"></td>
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
			<td><input id="MOVIL_NAC_19" type="text" name="MOVIL_NAC_19" value="<%=datosExtra.getMOVIL_NAC_19()%>"></td>
			<td><input id="MOVIL_NAC_20_24" type="text" name="MOVIL_NAC_20_24" value="<%=datosExtra.getMOVIL_NAC_20_24()%>"></td>
			<td><input id="MOVIL_NAC_25_29" type="text" name="MOVIL_NAC_25_29" value="<%=datosExtra.getMOVIL_NAC_25_29()%>"></td>
			<td><input id="MOVIL_NAC_30" type="text" name="MOVIL_NAC_30" value="<%=datosExtra.getMOVIL_NAC_30()%>"></td>
			<td><input id="MOVIL_NAC_DIS" type="text" name="MOVIL_NAC_DIS" value="<%=datosExtra.getMOVIL_NAC_DIS()%>"></td>
			<td></td>
		</tr>
		</tbody>
		</table>
		<table class="table">
		<thead>
		<tr>
			<th><b>Total de Estudiantes en situación de movilidad internacional</b></th>
			<th>Estudiantes Hombres</th>
			<th>Estudiantes Mujeres</th>
			<th><b>Total de Estudiantes en situación de movilidad internacional por edad</b></th>
			<th></th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td><input id="MOVIL_INT_T" type="text" name="MOVIL_INT_T" value="<%=datosExtra.getMOVIL_INT_T()%>"></td>
			<td><input id="MOVIL_INT_H" type="text" name="MOVIL_INT_H" value="<%=datosExtra.getMOVIL_INT_H()%>"></td>
			<td><input id="MOVIL_INT_M" type="text" name="MOVIL_INT_M" value="<%=datosExtra.getMOVIL_INT_M()%>"></td>
			<td><input id="TOTAL_MI_E" type=text" name="TOTAL_MI_E" value="<%=datosExtra.getTOTAL_MI_E()%>"></td>
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
			<td><input id="MOVIL_INT_19" type="text" name="MOVIL_INT_19" value="<%=datosExtra.getMOVIL_INT_19()%>"></td>
			<td><input id="MOVIL_INT_20_24" type="text" name="MOVIL_INT_20_24" value="<%=datosExtra.getMOVIL_INT_20_24()%>"></td>
			<td><input id="MOVIL_INT_25_29" type="text" name="MOVIL_INT_25_29" value="<%=datosExtra.getMOVIL_INT_25_29()%>"></td>
			<td><input id="MOVIL_INT_30" type="text" name="MOVIL_INT_30" value="<%=datosExtra.getMOVIL_INT_30()%>"></td>
			<td><input id="MOVIL_INT_DIS" type="text" name="MOVIL_INT_DIS" value="<%=datosExtra.getMOVIL_INT_DIS()%>"></td>
			<td></td>
		</tr>
		</tbody>
		</table>
		<table class="table">
		<thead>
		<tr>
			<th><b>Total de Estudiantes que participan en servicio a la comunidad</b></th>
			<th>Estudiantes Hombres</th>
			<th>Aumnos Mujeres</th>
			<th><b>Total de Estudiantes que han realizado su servicio social al momento de concluir sus creditos</b></th>
			<th>Estudiantes Hombres</th>
			<th>Estudiantes Mujeres</th>
			<th><b>¿Para acreditar el programa se requiere de prácticas profesionales? (SI / NO)</b></th>
			<th></th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td><input id="SERV_COM_T" type="text" name="SERV_COM_T" value="<%=datosExtra.getSERV_COM_T()%>"></td>
			<td><input id="SERV_COM_H" type="text" name="SERV_COM_H" value="<%=datosExtra.getSERV_COM_H()%>"></td>
			<td><input id="SERV_COM_M" type="text" name="SERV_COM_M" value="<%=datosExtra.getSERV_COM_M()%>"></td>
			<td><input id="SERV_SOC_T" type="text" name="SERV_SOC_T" value="<%=datosExtra.getSERV_SOC_T()%>"></td>
			<td><input id="SERV_SOC_H" type="text" name="SERV_SOC_H" value="<%=datosExtra.getSERV_SOC_H()%>"></td>
			<td><input id="SERV_SOC_M" type="text" name="SERV_SOC_M" value="<%=datosExtra.getSERV_SOC_M()%>"></td>
			<td><input id="QUEST_2" type=text" name="QUEST_2" value="<%=datosExtra.getQUEST_2()%>"></td>
			<td></td>
		</tr>
		</tbody>
		</table>
		
		<h3>Resultados Educativos</h3>
		
		<table class="table">
		<thead>
		<tr>
			<th>Estudiantes inscritos en el último grado de nivel con discapacidad</th>
			<th><b>Total de estudiantes egresados</b></th>
			<th>Estudiantes Hombres</th>
			<th>Estudiantes Mujeres</th>
			<th><b>Total de estudiantes egresados por edad</b></th>
			<th></th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td><input id="ULT_GRAD_DIS" type=text" name="ULT_GRAD_DIS" value="<%=datosExtra.getULT_GRAD_DIS()%>"></td>
			<td><input id="EGRESADOS_T" type=text" name="EGRESADOS_T" value="<%=datosExtra.getEGRESADOS_T()%>"></td>
			<td><input id="EGRESADOS_H" type=text" name="EGRESADOS_H" value="<%=datosExtra.getEGRESADOS_H()%>"></td>
			<td><input id="EGRESADOS_M" type=text" name="EGRESADOS_M" value="<%=datosExtra.getEGRESADOS_M()%>"></td>
			<td><input id="TOTAL_EGRE_E" type=text" name="TOTAL_EGRE_E" value="<%=datosExtra.getTOTAL_EGRE_E()%>"></td>
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
			<th>Estudiantes egresados con discapacidad</th>
			<th>Promedio total de calificaciones de los egresados</th>
			<th></th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td><input id="EGRESADOS_19" type=text" name="EGRESADOS_19" value="<%=datosExtra.getEGRESADOS_19()%>"></td>
			<td><input id="EGRESADOS_20_24" type=text" name="EGRESADOS_20_24" value="<%=datosExtra.getEGRESADOS_20_24()%>"></td>
			<td><input id="EGRESADOS_25_29" type=text" name="EGRESADOS_25_29" value="<%=datosExtra.getEGRESADOS_25_29()%>"></td>
			<td><input id="EGRESADOS_30" type=text" name="EGRESADOS_30" value="<%=datosExtra.getEGRESADOS_30()%>"></td>
			<td><input id="EGRESADOS_DIS" type=text" name="EGRESADOS_DIS" value="<%=datosExtra.getEGRESADOS_DIS()%>"></td>
			<td><div title="Ingresar número decimal, ejemplo: 5.0, 4.7, 8.6"><input id="PROM_EGRE" type=text" name="PROM_EGRE" value="<%=datosExtra.getPROM_EGRE()%>"></div></td>
			<td></td>
		</tr>
		</tbody>
		</table>
		<table class="table">
		<thead>
		<tr>
			<th>Estudiantes que realizan EGEL o equivalente</th>
			<th>Estudiantes que aprueban EGEL o equivalente con puntaje sobresaliente</th>
			<th>Estudiantes que se titulan del programa</th>
			<th>Estudiantes que concluyen el programa(100% de creditos, servicio social, etc.)</th>
			<th><b>¿Es un programa con doble titulación? (SI / NO)</b></th>
			<th><b>¿Cuenta con programa de seguimiento de egresados? (SI / NO)</b></th>
			<th></th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td><input id="EGEL" type="text" name="EGEL" value="<%=datosExtra.getEGEL()%>"></td>
			<td><input id="EGEL_SOB" type="text" name="EGEL_SOB" value="<%=datosExtra.getEGEL_SOB()%>"></td>
			<td><input id="TITU_PROG" type="text" name="TITU_PROG" value="<%=datosExtra.getTITU_PROG()%>"></td>
			<td><input id="CONC" type="text" name="CONC" value="<%=datosExtra.getCONC()%>"></td>
			<td><input id="QUEST_3" type=text" name="QUEST_3" value="<%=datosExtra.getQUEST_3()%>"></td>
			<td><input id="QUEST_4" type=text" name="QUEST_4" value="<%=datosExtra.getQUEST_4()%>"></td>
			<td></td>
		</tr>
		</tbody>
		</table>
		<table class="table">
		<thead>
		<tr>
			<th><b>Total de egresados que han conseguido trabajo en un lapso no mayor a 9 meses posteriores a su egreso</b></th>
			<th>Egresados Hombres</th>
			<th>Egresados Mujeres</th>
			<th><b>Total de egresados que han conseguido trabajo por edad</b></th>
			<th></th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td><input id="EGRE_TRAB_T" type="text" name="EGRE_TRAB_T" value="<%=datosExtra.getEGRE_TRAB_T()%>"></td>
			<td><input id="EGRE_TRAB_H" type="text" name="EGRE_TRAB_H" value="<%=datosExtra.getEGRE_TRAB_H()%>"></td>
			<td><input id="EGRE_TRAB_M" type="text" name="EGRE_TRAB_M" value="<%=datosExtra.getEGRE_TRAB_M()%>"></td>
			<td><input id="TOTAL_TRAB_E" type="text" name="TOTAL_TRAB_E" value="<%=datosExtra.getTOTAL_TRAB_E()%>"></td>
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
			<td><input id="EGRE_TRAB_19" type="text" name="EGRE_TRAB_19" value="<%=datosExtra.getEGRE_TRAB_19()%>"></td>
			<td><input id="EGRE_TRAB_20_24" type="text" name="EGRE_TRAB_20_24" value="<%=datosExtra.getEGRE_TRAB_20_24()%>"></td>
			<td><input id="EGRE_TRAB_25_29" type="text" name="EGRE_TRAB_25_29" value="<%=datosExtra.getEGRE_TRAB_25_29()%>"></td>
			<td><input id="EGRE_TRAB_30" type="text" name="EGRE_TRAB_30" value="<%=datosExtra.getEGRE_TRAB_30()%>"></td>
			<td><input id="EGRE_TRAB_DIS" type="text" name="EGRE_TRAB_DIS" value="<%=datosExtra.getEGRE_TRAB_DIS()%>"></td>
			<td><div title="Ingresar número entero sin signo %"><input id="POR_EGRE_TRAB" type=text" name="POR_EGRE_TRAB" value="<%=datosExtra.getPOR_EGRE_TRAB()%>"></div></td>
			<td></td>
		</tr>
		</tbody>
		</table>
		</form>
</div>
</body>