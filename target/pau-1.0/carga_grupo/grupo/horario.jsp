<%@page import="aca.carga.CargaBloqueUtil"%>
<%@page import="aca.catalogo.CatHorarioUtil"%>
<%@page import="java.util.HashMap"%>

<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="CatSalon" scope="page" class="aca.catalogo.CatSalon"/>
<jsp:useBean id="CatSalonU" scope="page" class="aca.catalogo.SalonUtil"/>
<jsp:useBean id="HorarioFacuU" scope="page" class="aca.catalogo.CatHorarioFacultadUtil"/>
<jsp:useBean id="HorarioU" scope="page" class="aca.catalogo.CatHorarioUtil"/>
<jsp:useBean id="CargaGrupoHoraU" scope="page" class="aca.carga.CargaGrupoHoraUtil"/>
<jsp:useBean id="CargaGrupoU" scope="page" class="aca.carga.CargaGrupoUtil"/>
<jsp:useBean id="salonU" scope="page" class="aca.catalogo.SalonUtil"/>
<jsp:useBean id="edificioU" scope="page" class="aca.catalogo.EdificioUtil"/>
<jsp:useBean id="CargaBloqueU" scope="page" class="aca.carga.CargaBloqueUtil"/>
<jsp:useBean id="Curso" scope="page" class="aca.plan.MapaCurso"/>
<jsp:useBean id="MapaCursoU" scope="page" class="aca.plan.CursoUtil"/>
<jsp:useBean id="Grupo" scope="page" class="aca.carga.CargaGrupo"/>
<jsp:useBean id="GrupoU" scope="page" class="aca.carga.CargaGrupoUtil"/>

<html>
<head>
	<script type="text/javascript">
		function Guardar(){
			document.forma.Accion.value="1";
			document.forma.submit();
		}
	</script>
</head>
<%
	String cursoCargaId 	= request.getParameter("CursoCargaId");
	String cargaId			= aca.carga.CargaGrupoUtil.getCargaId(conEnoc,cursoCargaId);
	String carreraId 		= request.getParameter("CarreraId");
	String materia 			= request.getParameter("Materia");
	String grupo 			= request.getParameter("Grupo")==null?"-":request.getParameter("Grupo");
	String profesor			= request.getParameter("Profesor");
	String accion			= request.getParameter("Accion")==null ? "0" : request.getParameter("Accion");	
	String ver				= request.getParameter("Ver")==null ? "0" : request.getParameter("Ver");
	String resultado 		= "Select the amount of hours for  the subject.";
	String planId 		    = request.getParameter("PlanId");
	String bloque			= request.getParameter("bloqueId")==null?"0":request.getParameter("bloqueId");
	String horarioId        = request.getParameter("horarioId")==null?"1":request.getParameter("horarioId");
	
	Grupo.setCursoCargaId(cursoCargaId);
	if(GrupoU.existeReg(conEnoc, cursoCargaId)){
		Grupo = GrupoU.mapeaRegId(conEnoc,cursoCargaId);
		if(bloque.equals("0")){
			bloque = Grupo.getBloqueId().trim();
		}
	}
	
	String materiaChoca = "";
	String nombreChoca	= "";

	String facultadId		= aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, carreraId);
	String nombreFacultad	= aca.catalogo.CatFacultadUtil.getNombreCorto(conEnoc, facultadId);	
	String horarioId2		= request.getParameter("horarioId2")==null?horarioId:request.getParameter("horarioId2");
	String dia 				= request.getParameter("dia")==null?"1":request.getParameter("dia");
	
	//System.out.println("Horarios"+horarioId+":"+horarioId2);
	String periodo 			= request.getParameter("periodo")==null?"1":request.getParameter("periodo");	
	
	String edificioId 		= (request.getParameter("EdificioId")==null || request.getParameter("EdificioId").equals("")) ? "0" : request.getParameter("EdificioId");
	String salonId 			= (request.getParameter("SalonId")==null || edificioId.equals("")) ? "0" : request.getParameter("SalonId");
	
	ArrayList<aca.catalogo.CatEdificio> lisEdificio = edificioU.getListAll(conEnoc, "ORDER BY 1");
	ArrayList<aca.catalogo.CatSalon> lisSalon 		= salonU.getListaActivos(conEnoc, edificioId, "ORDER BY 2");
	ArrayList<aca.catalogo.CatHorarioFacultad> lisPeriodos = HorarioFacuU.getPeriodos(conEnoc, horarioId, "");
	ArrayList<String> listaSalonesMateria 			= CargaGrupoHoraU.salonesDelGrupo(conEnoc, cursoCargaId, "");
	ArrayList<String> listaHorarios 				= CargaGrupoHoraU.horariosSalonFacultades(conEnoc, cargaId, horarioId, salonId, "");
	ArrayList<aca.carga.CargaBloque> listaBloques					= CargaBloqueU.getListBloqueCarga(conEnoc, cargaId, "ORDER BY BLOQUE_ID");
	//ArrayList<aca.carga.CargaGrupo> materiaRestriccion			= CargaGrupoU.getListaCarga(conEnoc, cargaId, "");
	
	if (listaHorarios.size()==1) horarioId2 = listaHorarios.get(0);
	
	HashMap <String, String>  materiaRestriccion = CargaGrupoU.getRestriccionMateria(conEnoc, cargaId);
	
	// Lista de los periodos registrados en el salón
	ArrayList<String> horariosSalon = aca.carga.CargaGrupoHoraUtil.horariosSalon(conEnoc, cargaId, salonId, bloque);	
	
	aca.carga.CargaGrupoHora cgh = new aca.carga.CargaGrupoHora();
		
	if(accion.equals("1")){
		
		// Datos del nuevo periodo a registrar
		String periodoNuevo 	= aca.carga.CargaGrupoHoraUtil.getInicioFinal(conEnoc, horarioId, periodo);
		int periodoInicio 		= Integer.parseInt( dia+periodoNuevo.split(",")[0] );
		int periodoFin    		= Integer.parseInt( dia+periodoNuevo.split(",")[1] );
		
		boolean chocaMateria 	= false;
		String diaSem 			= "";
		
		for(String salon : horariosSalon){
			// Elementos de la lista son [0]=CursoCargaId [1]=Inicio y [2]=Fin
			String cursoCargaIdA = salon.split(",")[0];
			int salonInicio = Integer.parseInt(salon.split(",")[1]);
			int salonFin 	= Integer.parseInt(salon.split(",")[2]);

			String restriccion  = "";
			String restriccionA = "";
			
			if(materiaRestriccion.containsKey(cursoCargaIdA)){
				
				restriccionA = materiaRestriccion.get(cursoCargaIdA);
				restriccion = materiaRestriccion.get(cursoCargaId);
			}
			
			
			if ( 
				
					( ( periodoInicio >= salonInicio && periodoInicio < salonFin ) ||
					( periodoFin > salonInicio && periodoFin <= salonFin ) ||
					( periodoInicio <= salonInicio && periodoFin >= salonFin )||
					( periodoInicio <= salonInicio && periodoFin >= salonFin ) ) &&
					( (restriccion.equals("S")) || (restriccionA.equals("S")) )
				){				
				
					if( (restriccion.equals("N")) && (restriccionA.equals("N")) ){
						chocaMateria = false;
					}
				
					if(salon.split(",")[1].substring(0, 1).equals("1")){
						diaSem = "Sunday";
					}else if (salon.split(",")[1].substring(0, 1).equals("2")){
						diaSem = "Monday";
					}else if (salon.split(",")[1].substring(0, 1).equals("3")){
						diaSem = "Tuesday";
					}else if (salon.split(",")[1].substring(0, 1).equals("4")){
						diaSem = "Wednesday";
					}else if (salon.split(",")[1].substring(0, 1).equals("5")){
						diaSem = "Thursday";
					}else if (salon.split(",")[1].substring(0, 1).equals("6")){
						diaSem = "Friday";
					}else if (salon.split(",")[1].substring(0, 1).equals("7")){
						diaSem = "Saturday";
					}
					
					String materiaClave = aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conEnoc,salon.split(",")[0]) ;
					String materiaNombre = aca.plan.CursoUtil.getMateria(conEnoc, materiaClave);
					
					resultado = "The timetable clashes with <b>"+materiaNombre+"</b> ("+materiaClave.substring(0,8)+"-"+salon.split(",")[0]+" ) on the day "+diaSem+" at time: "
						+ salon.split(",")[1].substring(1, 3)+":"+salon.split(",")[1].substring(3, 5)+" - "+salon.split(",")[2].substring(1, 3)+":"+salon.split(",")[2].substring(3, 5);
					chocaMateria = true;
					break;
			}	
			
		}
		
		if(chocaMateria == false){
			cgh.setCursoCargaId(cursoCargaId);
			cgh.setBloqueId(bloque);
			cgh.setDia(dia);
			cgh.setPeriodo(periodo);
			cgh.setHorarioId(horarioId);
			cgh.setSalonId(salonId);		
			
			if(!CargaGrupoHoraU.existeReg(conEnoc, cursoCargaId, salonId, horarioId, periodo, bloque, dia)){
				CargaGrupoHoraU.insertReg(conEnoc, cgh);
				resultado = "Timetable saved correctly";
			}else{				
				resultado = "The timetable clashes with antoher subejct.";			
			}				
		}
		
	}else if(accion.equals("2")){		
		cgh.setCursoCargaId(cursoCargaId);
		cgh.setDia(dia);
		cgh.setBloqueId(bloque);
		cgh.setPeriodo(periodo);
		cgh.setHorarioId(horarioId);
		cgh.setSalonId(salonId);
		if(CargaGrupoHoraU.existeReg(conEnoc, cursoCargaId, salonId, horarioId, periodo, bloque, dia)){			
			if(CargaGrupoHoraU.deleteReg(conEnoc, cursoCargaId, salonId, horarioId, periodo, bloque, dia)){				
				resultado = "Deleted";
			}else{				
				System.out.println("Error deleting...");
			}
		}
	}
	// Busca los datos de la materia
	int horasReq 		= 0;
	int horasReg		= 0;
	int horasFal		= 0;
	String cursoId	= aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conEnoc, cursoCargaId);	
	Curso.setCursoId(cursoId);
	if (MapaCursoU.existeReg(conEnoc, cursoId)){
		Curso = MapaCursoU.mapeaRegId(conEnoc, cursoId);
		horasReq = Integer.parseInt(Curso.getHh());
		horasReg = Integer.parseInt(aca.carga.CargaGrupoHoraUtil.numPeriodosMateria(conEnoc, cursoCargaId));	
		horasFal = horasReq-horasReg; 
	}
	
	
%>
<body onselectstart="return false">
<div class="container-fluid">
	<h2>
		Assign Timetable <small class="text-muted fs-5"> (<%=nombreFacultad%> - <%=materia %> -Group[<%=grupo %>] - <%=profesor %>)</small>
	</h2>
	<form id="forma" name="forma" action="horario?Ver=<%=ver %>&CarreraId=<%=carreraId%>&PlanId=<%=planId %>&CursoCargaId=<%=cursoCargaId%>&Materia=<%=materia%>&Grupo=<%=grupo %>&Profesor=<%=profesor %>" method='post'>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="grupo?CarreraId=<%=carreraId%>&PlanId=<%=planId %>"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		<strong><spring:message code="aca.Edificio"/>:</strong>
			<select name="EdificioId" onchange='document.forma.submit()'>
	<%	for (int i=0; i<lisEdificio.size(); i++) {
			if(i==0){%>
				<option value="<%=""%>" <%if(edificioId.equals("")) out.print("selected");%>><spring:message code="aca.Seleccionar"/></option>
	<%		}
			aca.catalogo.CatEdificio edificio = lisEdificio.get(i);
			if(edificio.getUsuarios().contains((String)session.getAttribute("codigoPersonal"))){%>
				<option value="<%=edificio.getEdificioId()%>" <%if(edificio.getEdificioId().equals(edificioId)) out.print("selected");%>><%=edificio.getNombreEdificio()%></option>
	<%		}
		} %>
			</select>
			&nbsp;
			<strong><spring:message code="aca.Salon"/>:</strong> 
			<select name="SalonId" onchange='document.forma.submit()'>
	<%	String tmpSalon = "";
		for (int i = 0; i<lisSalon.size(); i++) {
			aca.catalogo.CatSalon salon = lisSalon.get(i);%>
				<option value="<%=salon.getSalonId()%>" <%if(salon.getSalonId().equals(salonId)){ out.print("selected"); tmpSalon = salon.getSalonId();}%>><%=salon.getNombreSalon()%></option>
	<%	}
		if(tmpSalon.equals("") && !lisSalon.isEmpty()) salonId = lisSalon.get(0).getSalonId();
	%>
			</select>
			&nbsp;
			<strong>Timetable:</strong>
			<select name="horarioId2" onchange='document.forma.submit()'>			
		<%
			for(String horario: listaHorarios){	
		%>
				<option value="<%=horario %>" <%if(horarioId2.equals(horario))out.print("selected"); %>><%=aca.catalogo.CatFacultadUtil.getNombreHorarioId(conEnoc, horario) %></option>
		<%
			}
		%>
			</select>
			&nbsp;
			<strong>Block:</strong>
			<select name="bloqueId" onchange='document.forma.submit()'>			
		<%
			for(aca.carga.CargaBloque bloques: listaBloques){	
		%>
				<option value="<%=bloques.getBloqueId()%>" <%if(bloque.equals(bloques.getBloqueId()))out.print("selected"); %>><%=bloques.getNombreBloque() %></option>
		<%
			}
		%>
			</select>	
	</div>	
	<input type="hidden" name="Accion">		
	<input type="hidden" name="horarioId" value="<%=horarioId%>">
	<div class="alert alert-info">
		<b>Hours:</b> Required <b>[ <%=horasReq%> ]</b> Recorded <b>[ <%=horasReg%> ]</b> Missing <b>[ <%=horasFal%> ]</b>&nbsp;&nbsp;
		<spring:message code="cargasGrupo.grupo.SalonesAsignadosMateria"/>:
<%			
			for(String salon : listaSalonesMateria){
				CatSalon = CatSalonU.mapeaRegId(conEnoc, salon);%>				
				<a href="horario?Ver=<%=ver %>&CarreraId=<%=carreraId%>&PlanId=<%=planId %>&CursoCargaId=<%=cursoCargaId%>&Materia=<%=materia%>&Grupo=<%=grupo%>&Profesor=<%=profesor %>&EdificioId=<%=CatSalon.getEdificioId()%>&SalonId=<%=CatSalon.getSalonId()%>&horarioId=<%=horarioId%>">
				<%=CatSalon.getNombreSalon()%>
				</a>
				&nbsp;				
<%			}%>		
	</div>
<!-- 		<div class="alert alert-success"><h4 style="text-align:center;">Para agregar un horario:   Selecciona el edificio, el salon y dale clic al dia y periodo que quieres impartir las clases!</h4></div>		 -->
		<table style="margin: 0px auto;">
			<tr>
				<td>
				  <%=resultado%>
				</td>
			</tr>
		</table>
		
		<table style="width:95%; margin: 0 auto;"   class="tabla" border="1">
			<tr height="30px">
				<th width="3%">&nbsp;&nbsp;<spring:message code="aca.Numero"/></th>
				<th width="12%">&nbsp;&nbsp;<spring:message code="aca.Hora"/></th>
				<th width="12%">&nbsp;&nbsp;<spring:message code="aca.Domingo"/></th>
				<th width="12%">&nbsp;&nbsp;<spring:message code="aca.Lunes"/></th>
				<th width="12%">&nbsp;&nbsp;<spring:message code="aca.Martes"/></th>
				<th width="12%">&nbsp;&nbsp;<spring:message code="aca.Miercoles"/></th>
				<th width="12%">&nbsp;&nbsp;<spring:message code="aca.Jueves"/></th>
				<th width="12%">&nbsp;&nbsp;<spring:message code="aca.Viernes"/></th>
				<th width="12%">&nbsp;&nbsp;<spring:message code="aca.Sabado"/></th>
			</tr>
<%
		ArrayList<String> turno = HorarioFacuU.getTurno(conEnoc, horarioId2, "");
		/*System.out.println(cargaId);
		System.out.println(bloque);
		System.out.println(horarioId2);
		System.out.println(salonId);*/
		HashMap<String, ArrayList<aca.carga.CargaGrupoHora>> mapMateria  = CargaGrupoHoraU.getPeriodoHashMap(conEnoc, cargaId, bloque, horarioId2, salonId);
		
		for(int i = 0; i<turno.size(); i++){			
				
			if(turno.get(i).equals("1")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Morning</h4></td></tr>");				
			}else if(turno.get(i).equals("2")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Afternoon</h4></td></tr>");				
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
					for(int j=1; j<8; j++){
						String nombreMateria = "";
						if(mapMateria.containsKey(j+","+horario.getPeriodo())){
							ArrayList<aca.carga.CargaGrupoHora> cgh1 = mapMateria.get(j+","+horario.getPeriodo());
					%>
							<td align="center" class="tr2" >
								<% 
									String cursoCargaIdA ="";
									for(aca.carga.CargaGrupoHora obj : cgh1){
										nombreMateria =	aca.vista.CargaAcademica.getNombreMateria(conEnoc, obj.getCursoCargaId());
										cursoCargaIdA = obj.getCursoCargaId();
										
										if( obj.getCursoCargaId().equals(cursoCargaId) && ver.equals("0")){
								%>
											<a href="horario?Accion=2&Ver=<%=ver%>&CarreraId=<%=carreraId%>&PlanId=<%=planId %>&CursoCargaId=<%=cursoCargaId%>&Materia=<%=materia%>&Grupo=<%=grupo %>&Profesor=<%=profesor %>&EdificioId=<%=edificioId%>&SalonId=<%=salonId%>&horarioId=<%=horarioId%>&dia=<%=j%>&periodo=<%=horario.getPeriodo()%>&bloqueId=<%=bloque%>"><i class="fas fa-trash-alt"></i></a>
											<span style="color:red;"><%=nombreMateria %></span>
										 <%}else{ %>
										    <%=nombreMateria %>
										 <%} %>
										    <br>
								<% 
									} 
								%>
								<a href="materiasPorSalon?dia=<%=j%>&periodo=<%=horario.getPeriodo() %>&salonId=<%=salonId%>&cargaId=<%=cargaId%>&bloque=<%=bloque%>&horarioId=<%=horarioId2%>&Ver=<%=ver %>&CarreraId=<%=carreraId%>&PlanId=<%=planId %>&CursoCargaId=<%=cursoCargaId%>&Materia=<%=materia%>&Grupo=<%=grupo%>&Profesor=<%=profesor %>&EdificioId=<%=CatSalon.getEdificioId()%>"><i class="icon icon-search"></i></a>&nbsp;&nbsp;
								<%
									String restriccion  = "";
									String restriccionA = "";
									if(materiaRestriccion.containsKey(cursoCargaIdA)){
										
										restriccionA = materiaRestriccion.get(cursoCargaIdA);
										restriccion = materiaRestriccion.get(cursoCargaId);
									}
									if(restriccionA.equals("N") && restriccion.equals("N")){
								%>
								<a href="horario?dia=<%=j%>&periodo=<%=horario.getPeriodo() %>&Accion=1&horarioId=<%=horarioId%>&EdificioId=<%=edificioId%>&SalonId=<%=salonId%>&CursoCargaId=<%=cursoCargaId%>&CarreraId=<%=carreraId%>&PlanId=<%=planId %>&horarioId=<%=horarioId %>&Materia=<%=materia%>&Grupo=<%=grupo%>&Profesor=<%=profesor %>"><i class="icon icon-plus"></i></a>
									<%} %>								
						    </td>
					<%	}else{ %>
							<td align="center" class="tr2" onclick="window.location='horario?dia=<%=j%>&periodo=<%=horario.getPeriodo() %>&Accion=1&horarioId=<%=horarioId%>&EdificioId=<%=edificioId%>&SalonId=<%=salonId%>&CursoCargaId=<%=cursoCargaId%>&CarreraId=<%=carreraId%>&PlanId=<%=planId %>&horarioId=<%=horarioId %>&Materia=<%=materia%>&Grupo=<%=grupo%>&Profesor=<%=profesor%>'">&nbsp;</td>						
					<%		
					 	}
					}
					%>
				</tr>
<%		
			}
		}
%>
		</table>
	</form>
</div>	
</body>
</html>
<%@ include file="../../cierra_enoc.jsp"%>