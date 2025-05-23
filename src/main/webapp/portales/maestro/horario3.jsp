<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page contentType="text/html; charset=iso-8859-1" pageEncoding="iso-8859-1"%>
<%@ include file="../../con_enoc.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Date"%>
<%@page import="aca.util.Fecha"%>
<%@page import="java.util.Calendar"%>

<jsp:useBean id="colorEmpleado" scope="page" class="aca.portal.Alumno"/>
<jsp:useBean id="CatHorarioFacUtil" scope="page" class="aca.catalogo.CatHorarioFacUtil"/>
<jsp:useBean id="HorarioU" scope="page" class="aca.carga.CargaGrupoHorarioUtil"/>
<jsp:useBean id="Edificio" scope="page" class="aca.catalogo.CatEdificio"/>
<jsp:useBean id="Salon" scope="page" class="aca.catalogo.CatSalon"/>
<jsp:useBean id="Maestros" scope="page" class="aca.vista.Maestros"/>
<jsp:useBean id="HorarioFacuU" scope="page" class="aca.catalogo.CatHorarioFacultadUtil"/>

<%
	String codigoEmpleado 	= (String) session.getAttribute("codigoPersonal");
	String cargaId 			= request.getParameter("CargaId");
	String horarioId 		= request.getParameter("horarioId");
	String horarioId2		= request.getParameter("horarioId2");
	
	ArrayList<aca.carga.CargaBloque> listaCargaBloque =  new aca.carga.CargaBloqueUtil().getListBloqueCarga(conEnoc, cargaId, "AND BLOQUE_ID IN (SELECT BLOQUE_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID='"+cargaId+"' AND CODIGO_PERSONAL='"+codigoEmpleado+"' AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_HORA WHERE CARGA_ID='"+cargaId+"'))");
	String bloqueId = (request.getParameter("BloqueId")==null||request.getParameter("BloqueId").equals("null"))?(listaCargaBloque.get(0)==null?"1":listaCargaBloque.get(0).getBloqueId()):request.getParameter("BloqueId");
	String colorTablas = "#396CB8";
	
	String currentColor = session.getAttribute("colorTablas").equals("")?"default":(String)session.getAttribute("colorTablas");
	if(!currentColor.equals("default")){
		colorTablas = currentColor;
	}
	
	ArrayList<String> listaHorarios = aca.objeto.Hora.listaHorariosDelMaestro(conEnoc, codigoEmpleado, cargaId, bloqueId);
	
	
	String colorM2 = colorEmpleado.modificarColor(colorTablas, 105);
	
	String regresar = request.getParameter("Regresar")==null ? "0" : request.getParameter("Regresar");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style>
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
	colorM2=colorEmpleado.modificarColor(colorTablas, 20);

	Maestros.mapeaRegId(conEnoc, codigoEmpleado);
%>
	<body>
		<table class="goback">
			<tr>
				<td><a class="btn btn-primary"
				<%	if(regresar.equals("0")){%>
						href="cursos"
				<%	}
					else if(regresar.equals("1")){%>
						href="../../portales/maestro/cursos"
				<%	}
					else if(regresar.equals("2")){%>
						href="../../portales/maestro2/cursos"
				<%	}
					else if(regresar.equals("3")){%>
						href="../evalua/cursos"
				<%	}%>
					><i class="fas fa-arrow-left"></i>&nbsp;<spring:message code="aca.Regresar"/></a>
				</td>
			</tr>
		</table>
		<br>
		<table style="margin: 0 auto;">
			<tr><td colspan="2" class="titulo" style="text-align:center;"><%=Maestros.getNombre()%> <%=Maestros.getApellidoPaterno()%> <%=Maestros.getApellidoMaterno()%></td></tr>
			<tr>
				<td class="titulo3" style="text-align:center;">Assigned Schedule - 
				Block:
				<%	if(listaCargaBloque.size()>1){%>
						<select onchange="document.location.href='nuevoHorario?CargaId=<%=cargaId%>&Regresar=<%=regresar%>&noentra=1&BloqueId='+this.value;" class="input input-mini" style="height:25;">
						<%	for(aca.carga.CargaBloque cargaBloque : listaCargaBloque){%>
								<option value="<%=cargaBloque.getBloqueId()%>" <%=cargaBloque.getBloqueId().equals(bloqueId)?"Selected":""%>><%=cargaBloque.getBloqueId()%></option>
						<%	}%>
						</select>
				<%	}
					else{out.print(bloqueId);}%>
				</td>
			</tr>
		</table>
			<div class="alert alert-info">
			<%if(listaHorarios.size()>1){ %>
				<select name="horarioId2" onchange="document.location.href='nuevoHorario?Regresar=<%=regresar%>&horarioId=<%=horarioId %>&CargaId=<%=cargaId%>&BloqueId=<%=bloqueId%>&noentra=2&horarioId2='+this.value;">			
					<%for(String horario: listaHorarios){%>
					  	<option value="<%=horario %>" <%if(horarioId2.equals(horario))out.print("selected"); %>><%=aca.catalogo.CatFacultadUtil.getNombreHorarioId(conEnoc, horario) %></option>
					<%}%>
				</select>
			<%}else{out.print(horarioId);} %>
			</div>
		<table style="width:95%; margin:0 auto; text-align:center;" class="tabla" border="1">
			<tr height="30px">
				<th style="width:3%; text-align:center;">#</th>
				<th style="width:12%; text-align:center;">Time</th>
				<th style="width:12%; text-align:center;">Sunday</th>
				<th style="width:12%; text-align:center;">Monday</th>
				<th style="width:12%; text-align:center;">Tuesday</th>
				<th style="width:12%; text-align:center;">Wednesday</th>
				<th style="width:12%; text-align:center;">Thursday</th>
				<th style="width:12%; text-align:center;">Friday</th>
				<th style="width:12%; text-align:center;">Saturday</th>
			</tr>
<%
		ArrayList<String> turno = HorarioFacuU.getTurno(conEnoc, horarioId2, "ORDER BY TURNO");
		java.util.HashMap<String, aca.objeto.Hora> mapHoras 	= aca.objeto.Hora.mapaHorasDelMaestro(conEnoc, codigoEmpleado, cargaId, bloqueId);
		java.util.HashMap<String, String> mapCursos 			= aca.objeto.Hora.mapaCursosDelMaestro(conEnoc, codigoEmpleado, cargaId);
		
		for(int i = 0; i<turno.size(); i++){
			if(turno.get(i).equals("1")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Morgning</h4></td></tr>");				
			}else if(turno.get(i).equals("2")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Evening</h4></td></tr>");
			}else if(turno.get(i).equals("3")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Night</h4></td></tr>");
			}		
			
			ArrayList<aca.catalogo.CatHorarioFacultad> listHorario = HorarioFacuU.getListaTurno(conEnoc, horarioId2, turno.get(i), " ORDER BY TURNO, PERIODO");
																		
			for(aca.catalogo.CatHorarioFacultad horario : listHorario){
				%>
				<tr height="40px">
					<td align=center class="th4"><b><%=horario.getPeriodo()%></b></td>
					<td align="center" class="th4"><b><%=horario.getHoraInicio()%>:<%=horario.getMinutoInicio()%> - <%=horario.getHoraFinal()%>:<%=horario.getMinutoFinal()%></b></td>
<%
				for(int j= 1; j<8; j++){
					
					// Buscar los datos de la materia
					if (mapHoras.containsKey(horarioId2+j+horario.getPeriodo())){
						aca.objeto.Hora horaDatos = mapHoras.get(horarioId2+j+horario.getPeriodo());
						String materia	= "";
						if (mapCursos.containsKey(horaDatos.getCursoCargaId())){
							materia = mapCursos.get(horaDatos.getCursoCargaId());
						}
						out.println("<td align='center' class='tr2'>"+materia+"<br><b><i>"+aca.catalogo.SalonUtil.nombreSalon(conEnoc, horaDatos.getSalon())+"</i></b></td>");
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
	</body>
</html>

<%@ include file="../../cierra_enoc2.jsf"%>