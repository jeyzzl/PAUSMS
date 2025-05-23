<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatHorarioFacultad"%>
<%@page import="aca.catalogo.spring.CatHorario"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.carga.spring.CargaGrupo"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="aca.objeto.spring.Hora"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<html>
<%
	String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");	
	String bloqueId			= (String) request.getAttribute("bloqueId");
	String noentra 			= request.getParameter("noentra");
	String regresar 		= request.getParameter("Regresar")==null ?"0":request.getParameter("Regresar");
	String horarioId		= (String) request.getAttribute("horarioId");

	Maestros maestro		= (Maestros) request.getAttribute("maestro");
	
	List<CargaBloque> lisBloques 	= (List<CargaBloque>) request.getAttribute("lisBloques");
	List<String> turno 				= (List<String>) request.getAttribute("turno");
	List<CatHorario> lisHorarios 	= (List<CatHorario>) request.getAttribute("lisHorarios");
	
	HashMap<String, String> mapaFacultadPorHorario 	= (HashMap<String, String>) request.getAttribute("mapaFacultadPorHorario");
	HashMap<String, Hora> mapHoras 					= (HashMap<String, Hora>) request.getAttribute("mapHoras");
	HashMap<String, String> mapCursos 				= (HashMap<String, String>) request.getAttribute("mapCursos");
	HashMap<String, CargaGrupo> mapGrupos 			= (HashMap<String, CargaGrupo>) request.getAttribute("mapGrupos");
	HashMap<String,String> mapaSalones 				= (HashMap<String, String>) request.getAttribute("mapaSalones");
	HashMap<String,CatHorarioFacultad> mapaHorario 	= (HashMap<String, CatHorarioFacultad>) request.getAttribute("mapaHorario");
	
	HashMap<String, List<CatHorarioFacultad>> mapaListaHorario 	= (HashMap<String, List<CatHorarioFacultad>>) request.getAttribute("mapaListaHorario");
	
	HashMap<String, List<Hora>> mapHorasPortalMaestro	= (HashMap<String, List<Hora>>) request.getAttribute("mapHorasPortalMaestro");
	String colorM2 = "#49e7f5";
%>

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
<div class="container-fluid">
	<h2><%=maestro.getNombre()+" "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno()%> <small class="text-muted h4">(Load: <%=cargaId%>)</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary"
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
						href="../evalua/cursos.jsp"
				<%	}%>
		><i class="fas fa-arrow-left"></i>&nbsp;<spring:message code="aca.Regresar"/></a>&nbsp;
		Block: 
		<select onchange="document.location.href='nuevoHorario?CargaId=<%=cargaId%>&HorarioId=<%=horarioId%>&Regresar=<%=regresar%>&noentra=1&BloqueId='+this.value;">
<%		for(CargaBloque bloque : lisBloques){%>
			<option value="<%=bloque.getBloqueId()%>" <%=bloque.getBloqueId().equals(bloqueId)?"Selected":""%>><%=bloque.getNombreBloque()%></option>
<%		}%>
		</select>&nbsp;
		Schedule: 
		<select onchange="document.location.href='nuevoHorario?CargaId=<%=cargaId%>&BloqueId=<%=bloqueId%>&Regresar=<%=regresar%>&noentra=1&HorarioId='+this.value;">
<%		for(CatHorario horario : lisHorarios){%>
			<option value="<%=horario.getHorarioId()%>" <%=horario.getHorarioId().equals(horarioId)?"Selected":""%>><%=horario.getDescripcion()%></option>
<%		}%>
		</select>
	</div>
			<table style="width:95%; margin:0 auto; text-align:center;" class="tabla" border="2">
			<tr height="30px">
				<th style="width:3%; text-align:center;">#</th>
				<th style="width:12%; text-align:center;">Hour</th>
				<th style="width:12%; text-align:center;">Sunday</th>
				<th style="width:12%; text-align:center;">Monday</th>	
				<th style="width:12%; text-align:center;">Tuesday</th>
				<th style="width:12%; text-align:center;">Wednesday</th>
				<th style="width:12%; text-align:center;">Thursday</th>
				<th style="width:12%; text-align:center;">Friday</th>
				<th style="width:12%; text-align:center;">Saturday</th>
			</tr>
<%
		for(int i = 0; i<turno.size(); i++){
			if(turno.get(i).equals("1")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Morning</h4></td></tr>");				
			}else if(turno.get(i).equals("2")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Afternoon</h4></td></tr>");
			}else if(turno.get(i).equals("3")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Evening</h4></td></tr>");
			}		
			
			List<CatHorarioFacultad> listHorario = new ArrayList<CatHorarioFacultad>();
			
			if(mapaListaHorario.containsKey(horarioId+turno.get(i))){
				listHorario = mapaListaHorario.get(horarioId+turno.get(i));
			}
			
			for(CatHorarioFacultad horario : listHorario){
				%>
				<tr height="40px">
					<td align=center class="th4"><b><%=horario.getPeriodo()%></b></td>
					<td align="center" class="th4"><b><%=horario.getHoraInicio()%>:<%=horario.getMinutoInicio()%> - <%=horario.getHoraFinal()%>:<%=horario.getMinutoFinal()%></b></td>
<%
				for(int j= 1; j<8; j++){
					
					// Buscar los datos de la materia
					if (mapHorasPortalMaestro.containsKey(horarioId+j+horario.getPeriodo())){
						List<Hora> horaDatos = mapHorasPortalMaestro.get(horarioId+j+horario.getPeriodo());						
						
						String grupo = "XX";
						String materia	= "";
						String nombreSalon = "";
	
						List<String> lisInfo = new ArrayList<String>();
						
						for(Hora hora : horaDatos){
							String info = "";
							if (mapCursos.containsKey(hora.getCursoCargaId())){
								materia = mapCursos.get(hora.getCursoCargaId());
							}
							if (mapaSalones.containsKey(hora.getSalon())){
								nombreSalon = mapaSalones.get(hora.getSalon());
							}
							if (mapGrupos.containsKey(hora.getCursoCargaId())){
								grupo = mapGrupos.get(hora.getCursoCargaId()).getGrupo();
							}
							
							info = materia+","+grupo+","+nombreSalon;
							lisInfo.add(info);
						}
%>
						<td align="center" class="tr2">
<%
						for(String datos : lisInfo){
							String[]arr = datos.split(",");
%>
							<%=arr[0]%>(<b><i><%=arr[1]%></i></b>)
<%							if(arr.length==3){%>
								<br><b><i><%=arr[2]%></i></b>
<%							}%>
<%						}%>
						</td>
<%
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
</div>
</html>