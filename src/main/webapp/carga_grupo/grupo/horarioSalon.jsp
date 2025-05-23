<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Date"%>

<%@page import= "aca.carga.spring.CargaGrupo"%>
<%@page import= "aca.carga.spring.CargaBloque"%>
<%@page import= "aca.carga.spring.CargaGrupoHora"%>
<%@page import= "aca.catalogo.spring.CatFacultad"%>
<%@page import= "aca.catalogo.spring.CatEdificio"%>
<%@page import= "aca.catalogo.spring.CatSalon"%>
<%@page import= "aca.catalogo.spring.CatHorarioFacultad"%>
<%@page import= "aca.vista.spring.CargaAcademica"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<html>
<head></head>
<%	
	String cargaId 			= (String)session.getAttribute("cargaId");
	String horarioId 		= (String)request.getAttribute("horarioId");
	String cargaNombre 		= (String)request.getAttribute("cargaNombre");
	String carreraId 		= (String)request.getAttribute("carreraId");
	String bloqueId 		= (String)request.getAttribute("bloqueId");	
	String edificioId 		= (String)request.getAttribute("edificioId");
	String salonId	 		= (String)request.getAttribute("salonId");	
	String planId			= request.getParameter("PlanId")==null ? "0" : request.getParameter("PlanId");
	
	List<CargaBloque> lisBloques 				= (List<CargaBloque>)request.getAttribute("lisBloques");
	List<CatEdificio> lisEdificios 				= (List<CatEdificio>)request.getAttribute("lisEdificios");	
	List<CatSalon> lisSalones 					= (List<CatSalon>)request.getAttribute("lisSalones");
	List<String> lisTurnos 						= (List<String>)request.getAttribute("lisTurnos");
	List<CatHorarioFacultad> lisHorarios		= (List<CatHorarioFacultad>)request.getAttribute("lisHorarios");	
	List<CargaGrupoHora> lisClases				= (List<CargaGrupoHora>)request.getAttribute("lisClases");
	HashMap<String,CargaAcademica> mapaMaterias = (HashMap<String,CargaAcademica>)request.getAttribute("mapaMaterias");
	HashMap<String,String> mapaMateriasEnPlan	= (HashMap<String,String>)request.getAttribute("mapaMateriasEnPlan");
	HashMap<String,String> mapaOcupados			= (HashMap<String,String>) request.getAttribute("mapaOcupados");
	HashMap<String,String> mapaEdificiosHoras	= (HashMap<String,String>) request.getAttribute("mapaEdificiosHoras");
	HashMap<String,String> mapaSalonesHoras		= (HashMap<String,String>) request.getAttribute("mapaSalonesHoras");
%>
<body>
<div class="container-fluid">
	<h2>Timetable by Classroom<small class="text-muted fs-5">( <%=cargaId%> - <%=cargaNombre%> - <%=planId%> )</small></h2>
	<form id="forma" name="forma" action="horarioSalon?CargaId=<%=cargaId %>" method='post'>
	<input name = "PlanId" type="hidden" value="<%=planId%>">
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="grupo?CarreraId=<%=carreraId%>&PlanId=<%=planId%>"><i class="fas fa-arrow-left"></i></a>
		&nbsp;&nbsp;		
		<strong>Block:</strong>
		<select name="BloqueId" class="form-select" onchange="document.forma.submit()" style="width:250px;">
<%						
		for(CargaBloque bloque : lisBloques){			
			out.print("<option value='"+bloque.getBloqueId()+"' "+ (bloqueId.equals(bloque.getBloqueId())?"selected":"")+">["+bloque.getBloqueId()+"] "+bloque.getNombreBloque()+"</option>");					
		}
%>		</select>
		&nbsp; &nbsp;		
		<strong><spring:message code="aca.Edificio"/>:</strong>&nbsp; 
		<select name="EdificioId" class="form-select" onchange="document.forma.submit()" style="width:250px;">
	<%	for(int i=0; i<lisEdificios.size(); i++){
			if(i==0){%>
			<option <%=edificioId.equals("0")?"selected":""%>><spring:message code="aca.Seleccionar"/></option>
	<%		}
			CatEdificio edificio = lisEdificios.get(i);
			String prefijo = "";
			if (mapaEdificiosHoras.containsKey(edificio.getEdificioId())){
				prefijo = "* ";
			}
			if(edificio.getUsuarios().contains((String)session.getAttribute("codigoPersonal"))){%>
			<option value="<%=edificio.getEdificioId()%>" <%=edificio.getEdificioId().equals(edificioId)?"selected":""%>><%=prefijo%><%=edificio.getEdificioId()%> - <%=edificio.getNombreEdificio()%></option>
	<%		}
		}%>
		</select>
		&nbsp;&nbsp;
		<strong><spring:message code="aca.Salon"/>:&nbsp;&nbsp;&nbsp;&nbsp;</strong> 
		<select name="SalonId" class="form-select" onchange="document.forma.submit()" style="width:300px;">
		<%	String tmpSalon = "";
			for(CatSalon salon : lisSalones) {				
				String salonOcupado = "0";
				String prefijo 		= "0"; 
				String colorOpcion 	= "style='color:gray'";
				if (mapaOcupados.containsKey(salon.getSalonId())){
					salonOcupado = mapaOcupados.get(salon.getSalonId());
					colorOpcion ="style='color:green'"; 
					prefijo = "{"+salonOcupado+"} ";
				}				
				if (mapaSalonesHoras.containsKey(salon.getSalonId())){
					prefijo = "{"+mapaSalonesHoras.get(salon.getSalonId())+"} ";
					colorOpcion ="style='color:blue'";					
				}
		%>
				<option value="<%=salon.getSalonId()%>" <%=salon.getSalonId().equals(salonId)?"selected":""%> <%=colorOpcion%>>
					<%=prefijo%><%=salon.getSalonId()%> - <%=salon.getNombreSalon()%>
				</option>
		<%		
				
			}
		%>
		</select>
	</div>		
	</form>	
	<table style="width:100%; margin: 0 auto;"  class="table table-bordered">
		<tr height="30px">
			<th width="5%"><h4><spring:message code="aca.Numero"/></h4></th>
			<th width="11%"><h4>Hour</h4></th>
			<th width="12%"><h4><spring:message code="aca.Domingo"/></h4></th>
			<th width="12%"><h4><spring:message code="aca.Lunes"/></h4></th>
			<th width="12%"><h4><spring:message code="aca.Martes"/></h4></th>
			<th width="12%"><h4><spring:message code="aca.Miercoles"/></h4></th>
			<th width="12%"><h4><spring:message code="aca.Jueves"/></h4></th>
			<th width="12%"><h4><spring:message code="aca.Viernes"/></h4></th>
			<th width="12%"><h4><spring:message code="aca.Sabado"/></h4></th>
		</tr>
<%			
		for(String turno : lisTurnos){			
				
			if(turno.equals("1")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Morning</h4></td></tr>");
			}else if(turno.equals("2")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Afternoon</h4></td></tr>");
			}else if(turno.equals("3")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Night</h4></td></tr>");
			}
			
			for(CatHorarioFacultad horario : lisHorarios){
				if (turno.equals(horario.getTurno())){
%>
		<tr height="40px">
			<td align=center class="th4"><b><%=horario.getPeriodo()%></b></td>
			<td align="center" class="th4"><b><%=horario.getHoraInicio()%>:<%=horario.getMinutoInicio()%> - <%=horario.getHoraFinal()%>:<%=horario.getMinutoFinal()%></b></td>
<%
				for(int j= 1; j<8; j++){
					String nombreMaterias = " ";
					String colorFondo = "";
					for (CargaGrupoHora clase : lisClases){
						if (clase.getDia().equals(String.valueOf(j)) && clase.getPeriodo().equals(horario.getPeriodo())){
							if (mapaMaterias.containsKey(clase.getCursoCargaId())){
								CargaAcademica ca = mapaMaterias.get(clase.getCursoCargaId());
								if (mapaMateriasEnPlan.containsKey(clase.getCursoCargaId())){
									if (mapaMateriasEnPlan.get(clase.getCursoCargaId()).equals("O")) colorFondo = "table-success"; else colorFondo = "table-warning"; 
								}
								nombreMaterias += "<span class='badge bg-dark rounded-pill' title='"+ca.getCursoCargaId()+"-"+ca.getPlanId()+"'>"+ca.getCiclo()+"</span> "+ca.getNombreCurso()+"("+ca.getGrupo()+") ";
							}
							
						}
					}
					
				%>
			<td class="text-center <%=colorFondo%>"><%=nombreMaterias%><br></td>
<%									
				}			
%>
		</tr>
<%		
				}
			}
		}			
%>	
	</table>
</div>	
</body>
</html>