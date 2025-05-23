<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page contentType="text/html; charset=iso-8859-1" pageEncoding="iso-8859-1"%>
<%@ include file="../../con_enoc.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
try{
%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Date"%>
<%@page import="aca.util.Fecha"%>
<%@page import="java.util.Calendar"%>

<jsp:useBean id="colorAlum" scope="page" class="aca.portal.Alumno"/>
<jsp:useBean id="CatHorarioFacUtil" scope="page" class="aca.catalogo.CatHorarioFacUtil"/>
<jsp:useBean id="CargaGrupoHoraU" scope="page" class="aca.carga.CargaGrupoHoraUtil"/>
<jsp:useBean id="HorarioU" scope="page" class="aca.carga.CargaGrupoHorarioUtil"/>
<jsp:useBean id="Edificio" scope="page" class="aca.catalogo.CatEdificio" />
<jsp:useBean id="Salon" scope="page" class="aca.catalogo.CatSalon"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="AlumPlan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="AlumPersonal" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="HorarioFacuU" scope="page" class="aca.catalogo.CatHorarioFacultadUtil"/>

<%
	Boolean inscripcion = Boolean.parseBoolean(request.getParameter("Inscripcion"));
	
	String matricula 	= (String) session.getAttribute("codigoAlumno");
	String cargaId 		= request.getParameter("CargaId");
	
	String horarioId 	= aca.carga.CargaGrupoHoraUtil.getHorarioPrincipal(conEnoc, matricula, cargaId);
	
	ArrayList<aca.carga.CargaBloque> listaCargaBloque =  new aca.carga.CargaBloqueUtil().getListBloqueCarga(conEnoc, cargaId, "AND BLOQUE_ID IN (SELECT BLOQUE_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID='"+cargaId+"' AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE CARGA_ID='"+cargaId+"' AND CODIGO_PERSONAL='"+matricula+"') AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_HORARIO WHERE CARGA_ID='"+cargaId+"'))");
	String bloqueId = (request.getParameter("BloqueId")==null||request.getParameter("BloqueId").equals("null"))?(listaCargaBloque.isEmpty()?"1":listaCargaBloque.get(0).getBloqueId()):request.getParameter("BloqueId");
	AlumPlan.mapeaRegId(conEnoc, matricula);
	String semTetra = AlumUtil.getCarreraId(conEnoc, AlumPlan.getPlanId()).substring(0,3).equals("107")?"Tetramestre":"Semestre";
	
	String colorTablas = "#396CB8";
	
	String currentColor = session.getAttribute("colorTablas").equals("")?"default":(String)session.getAttribute("colorTablas");
	if(!currentColor.equals("default")){
		colorTablas = currentColor;
	}
	String colorM2 = colorAlum.modificarColor(colorTablas, 105);	
	
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style>
			body{
				<%=!inscripcion?"background:#fff;":""%>
				font-size: 8pt;
			}
			.encabezado{
				font-size:10px;
			}
			.encabezado2{
				font-size:12px;
			}
			.encabezadoV {
				font-size: 7pt;
				font-size:11px;
			}
			.tabla td,th{
				border:1px solid <%=colorM2%>;
			}
		</style>
	</head>
<%
	colorM2=colorAlum.modificarColor(colorTablas, 20);

	AlumPersonal = AlumUtil.mapeaRegId(conEnoc, matricula);	
	
%>
	<body>
		<br>
		<table style="margin: 0 auto;">
			<tr><td colspan="2" class="titulo" style="text-align:center;"><%=AlumPersonal.getNombre()%> <%=AlumPersonal.getApellidoPaterno()%> <%=AlumPersonal.getApellidoMaterno()%></td></tr>
			<tr>
				<td class="titulo3" style="text-align:center;">Horario<%=inscripcion?" asignado":" "+semTetra+" "+AlumPlan.getCiclo() %> - 
				Bloque:
				<%	if(listaCargaBloque.size()>1){%>
						<select onchange="document.location.href='horario.jsp?CargaId=<%=cargaId%>&BloqueId='+this.value+'&Inscripcion=<%=inscripcion%>';" class="input input-mini" style="height:25;">
						<%	for(aca.carga.CargaBloque cargaBloque : listaCargaBloque){%>
								<option value="<%=cargaBloque.getBloqueId()%>" <%=cargaBloque.getBloqueId().equals(bloqueId)?"Selected":""%>><%=cargaBloque.getBloqueId()%></option>
						<%	}%>
						</select>
				<%	}
					else{out.print(bloqueId);}%>
				</td>
			</tr>
		</table>
		<table style="width:95%" align="center"   class="tabla" border="1">
			<tr height="30px">
				<th width="3%">#</th>
				<th width="12%">Hora</th>
				<th width="12%">Domingo</th>
				<th width="12%">Lunes</th>
				<th width="12%">Martes</th>
				<th width="12%">Miércoles</th>
				<th width="12%">Jueves</th>
				<th width="12%">Viernes</th>
				<th width="12%">Sábado</th>
			</tr>
<%
		ArrayList<String> turno = HorarioFacuU.getTurno(conEnoc, horarioId, "");
		java.util.HashMap<String, aca.objeto.Hora> mapHoras 	= aca.objeto.Hora.mapaHorasDelAlumno(conEnoc, matricula, cargaId, "'M','I'", bloqueId, horarioId);
		java.util.HashMap<String, String> mapCursos 			= aca.objeto.Hora.mapaCursosDelAlumnos(conEnoc, matricula, cargaId, "'M','I'");
		
		for(int i = 0; i<turno.size(); i++){
			if(turno.get(i).equals("1")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Matutino</h4></td></tr>");				
			}else if(turno.get(i).equals("2")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Vespertino</h4></td></tr>");
			}else if(turno.get(i).equals("3")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Nocturno</h4></td></tr>");
			}		
			
			ArrayList<aca.catalogo.CatHorarioFacultad> listHorario = HorarioFacuU.getListaTurno(conEnoc, horarioId, turno.get(i), " ORDER BY TURNO, PERIODO");
																		
			for(aca.catalogo.CatHorarioFacultad horario : listHorario){
				%>
				<tr height="40px">
					<td align=center class="th4"><b><%=horario.getPeriodo()%></b></td>
					<td align="center" class="th4"><b><%=horario.getHoraInicio()%>:<%=horario.getMinutoInicio()%> - <%=horario.getHoraFinal()%>:<%=horario.getMinutoFinal()%></b></td>
<%
				for(int j= 1; j<8; j++){
					
					// Buscar los datos de la materia
					if (mapHoras.containsKey(j+horario.getPeriodo())){
						aca.objeto.Hora horaDatos = mapHoras.get(j+horario.getPeriodo());
						String materia	= "";
						if (mapCursos.containsKey(horaDatos.getCursoCargaId())){
							materia = mapCursos.get(horaDatos.getCursoCargaId());
						}
						out.println("<td align='center' class='tr2'>"+materia+"</td>");
					}else{
						out.println("<td align='center' class='tr2'>&nbsp;</td>");
					}				
				}
				// cierra el renglón
				out.print("</tr>");
			}
		}
%>
		</table>
		<br><br>
		<table style="width:45%" align="center"  class="table table-condesed table-bordered" id="table"> 
			<thead>
		<tr>
			<th width="10%">Hora</th>
			<th width="15%">Dia</th>
			<th width="20%"><spring:message code="aca.Materia"/></th>
		</tr>
		</thead>
			<%ArrayList<String> lisHorarios = CargaGrupoHoraU.horariosAlumno(conEnoc, matricula, cargaId); 
			
			for(int horarios = 0; horarios<lisHorarios.size(); horarios++){
				
			if(!lisHorarios.get(horarios).equals(horarioId)){
				ArrayList<String> turnoDif = HorarioFacuU.getTurno(conEnoc, lisHorarios.get(horarios), "");
				java.util.HashMap<String, aca.objeto.Hora> mapHorasDif 	= aca.objeto.Hora.mapaHorasDelAlumno(conEnoc, matricula, cargaId, "'M','I'", bloqueId, lisHorarios.get(horarios));
				java.util.HashMap<String, String> mapCursosDif 			= aca.objeto.Hora.mapaCursosDelAlumnos(conEnoc, matricula, cargaId, "'M','I'");
				
					for(int turnos =0; turnos<turnoDif.size(); turnos++ ){
						ArrayList<aca.catalogo.CatHorarioFacultad> listHorario = HorarioFacuU.getListaTurno(conEnoc, lisHorarios.get(horarios), turnoDif.get(turnos), " ORDER BY TURNO, PERIODO");
						
						for(aca.catalogo.CatHorarioFacultad horario : listHorario){
							for(int j= 1; j<8; j++){
								String dia = "";
								if(j==1){
									dia = "Domingo";
								}else if(j==2){
									dia = "Lunes";
								}else if(j==3){
									dia = "Martes";
								}else if(j==4){
									dia = "Miércoles";
								}else if(j==5){
									dia = "Jueves";
								}else if(j==6){
									dia = "Viernes";
								}else if(j==7){
									dia = "Sábado";
								}
								
								// Buscar los datos de la materia
								if (mapHorasDif.containsKey(j+horario.getPeriodo())){
									aca.objeto.Hora horaDatos = mapHorasDif.get(j+horario.getPeriodo());
									String materia	= "";
									if (mapCursosDif.containsKey(horaDatos.getCursoCargaId())){
										materia = mapCursosDif.get(horaDatos.getCursoCargaId());
										out.println("<tr height='40px'>");
										out.println("<td align='center' class='tr2'>"+horario.getHoraInicio()+":"+horario.getMinutoInicio()+" - "+horario.getHoraFinal()+":"+horario.getMinutoFinal()+"</td>");
										out.println("<td align='center' class='tr2'>"+dia+"</td>");
										out.println("<td align='center' class='tr2'>"+materia+"</td>");
										out.print("</tr>");
									}
									
								}			
							}
						}
					}
				}
			}
			%>
		</table>
	</body>
</html>
<link rel="stylesheet" href="../../js/tablesorter/themes/blue/style.css" />
<script src="../../js/tablesorter/jquery.tablesorter.js"></script>
<script>
jQuery('#table').tablesorter();
</script>

<%	
}
catch(Exception e){
	out.print("Error");
}
%>