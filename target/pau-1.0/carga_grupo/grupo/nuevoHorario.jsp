<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Date"%>
<%@page import= "aca.carga.CargaGrupo"%>

<jsp:useBean id="CatSalon" scope="page" class="aca.catalogo.CatSalon"/>
<jsp:useBean id="CatSalonU" scope="page" class="aca.catalogo.SalonUtil"/>
<jsp:useBean id="HorarioFacU" scope="page" class="aca.catalogo.CatHorarioFacUtil"/>
<jsp:useBean id="CargaGrupoHorarioU" scope="page" class="aca.carga.CargaGrupoHorarioUtil"/>
<jsp:useBean id="salonU" scope="page" class="aca.catalogo.SalonUtil" />
<jsp:useBean id="edificioU" scope="page" class="aca.catalogo.EdificioUtil" />
<jsp:useBean id="CargaGrupoU" scope="page" class="aca.carga.CargaGrupoUtil" />

<html>
	<head>
		<script type="text/javascript">
			function Guardar(){
				document.forma.Accion.value="1";
				document.forma.submit();
			}
			
			function CambiarVista(vista){
				document.forma.Vista.value=vista;
				document.forma.submit();
			}
			
			function ModificarCheckbox(num){
				var input = document.getElementById("p"+num);
				input.checked = !input.checked;
				
				if(!input.checked){
					document.getElementById("td"+num).className="tr2";
					var imagen = document.getElementById("i"+num);
					imagen.width = 0;
					imagen.src="";
				}
				else{
					document.getElementById("td"+num).className="th4";
					var imagen = document.getElementById("i"+num);
					imagen.width = 30;
					imagen.src="../../imagenes/activa.png";
				}
			}
		</script>
	</head>
<%
	String cursoCargaId 	= request.getParameter("CursoCargaId");
	String carreraId 		= request.getParameter("CarreraId");
	String materia 			= request.getParameter("Materia");
	String grupo 			= request.getParameter("Grupo");
	String profesor			= request.getParameter("Profesor");
	String accion			= request.getParameter("Accion")==null ? "0" : request.getParameter("Accion");
	String vista			= request.getParameter("Vista")==null ? "0" : request.getParameter("Vista");
	String ver				= request.getParameter("Ver")==null ? "0" : request.getParameter("Ver");
	String resultado 		= "Select the hours assigned for the subjects";
	String planId 		    = request.getParameter("PlanId");

	ArrayList<aca.catalogo.CatEdificio> lisEdificio = edificioU.getListAll(conEnoc, "ORDER BY 1");
	String edificioId = (request.getParameter("EdificioId")==null || request.getParameter("EdificioId").equals("")) ? "" : request.getParameter("EdificioId");
	
	ArrayList<aca.catalogo.CatSalon> lisSalon = salonU.getLista(conEnoc, edificioId, "ORDER BY 2");
	String salonId = (request.getParameter("SalonId")==null || edificioId.equals("")) ? "" : request.getParameter("SalonId");
	
	ArrayList<aca.carga.CargaGrupoHorario> listaSalonesMateria = null;
	
	if(accion.equals("1")){
		aca.carga.CargaGrupoHorario cgh = new aca.carga.CargaGrupoHorario();
		aca.carga.CargaGrupoHora cgH = new aca.carga.CargaGrupoHora();
		
		String nuevoHorarioMat = "";
		for(int i=0; i<210; i++){
			if(request.getParameter("p"+i)!=null){
				nuevoHorarioMat+="1";
			}
			else{
				nuevoHorarioMat+="0";
			}
		}
		String horarioId = "1";
		cgh.setCursoCargaId(cursoCargaId);
		cgh.setSalonId(salonId);
		cgh.setHorario(nuevoHorarioMat);
		cgH.setHorarioId(horarioId);
		cgH.setCursoCargaId(cursoCargaId);
		cgH.setSalonId(salonId);
		/*cgH.set*/

		for(int k=0; k<7; k++){
			for(int l=0; l<30; l++){
			
					System.out.println("period "+l);
					System.out.println("day "+k);
					cgH.setDia(Integer.toString(k));
					cgH.setPeriodo(Integer.toString(l));
					CargaGrupoHorarioU.insertReg(conEnoc, cgh);
				
			}
		}
		
		if(!CargaGrupoHorarioU.existeReg(conEnoc, cursoCargaId, salonId)){
			CargaGrupoHorarioU.insertReg(conEnoc, cgh);
		}
		if(CargaGrupoHorarioU.updateReg(conEnoc, cgh)){
			String tmpHorario = "";
			listaSalonesMateria = CargaGrupoHorarioU.getLista(conEnoc, cursoCargaId, "ORDER BY 2");
			for(int cont=0; cont<210; cont++){
				String p = "0";
				//System.out.println(cont);
				for(aca.carga.CargaGrupoHorario tmpCgh : listaSalonesMateria){
					tmpCgh.getHorario();
					if(tmpCgh.getHorario().charAt(cont)=='1'){
						//System.out.println("materia");
						p="1";
						break;
					}
				}
				tmpHorario+=p;
			}
			
			if(!new aca.carga.CargaGrupoUtil().updateHorario(conEnoc, cursoCargaId, tmpHorario)){
				resultado = "Error saving the timetable";
			}
			else{
				resultado = "Timetable saved";
			}
		}
		else{
			resultado = "Error saving the timetable, make sure you selected a class room";
		}
	}		
%>
	<body onselectstart="return false">
		<table class="goback"><tr><td><a class="btn btn-primary" href="grupo?CarreraId=<%=carreraId%>&PlanId=<%=planId %>">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td></tr></table>
		<form id="forma" name="forma" action="nuevoHorario?Ver=<%=ver %>&CarreraId=<%=carreraId%>&PlanId=<%=planId %>&CursoCargaId=<%=cursoCargaId%>&Materia=<%=materia%>&Grupo=<%=grupo %>&Profesor=<%=profesor %>" method='post'>
			<input type="hidden" name="Accion">
			<input type="hidden" name="Vista" value=<%=vista%>>
			<table style="margin: 0 auto;">
				<tr><td colspan="2" class="titulo"><spring:message code="cargasGrupos.grupo.Titulo5"/></td></tr>
				<tr><td colspan="2" class="titulo2"><spring:message code="aca.Materia"/>: <b><%=materia %> - <%=grupo %></b></td></tr>
				<tr><td colspan="2" class="titulo2"><spring:message code="aca.Maestro"/>: <b><%=profesor %></b></td></tr>
				<tr><td>&nbsp;</td></tr>
			<%	int vHorario = aca.carga.CargaGrupoUtil.verificaHorario(conEnoc, cursoCargaId);
				if(vHorario!=0){%>
					<tr><td colspan="2" align="center"><font color="#AE2113"><%=vHorario==1 ? "Missing" : "Missing"%> <b><%=vHorario%> Selected</b></font></td></tr>
			<%	}
				else{%>
					<tr><td colspan="2" align="center"><font color="green"><b><spring:message code="cargasGrupo.grupo.HorarioCompleto"/></b></font></td></tr>	
			<%	} %>
			</table>
			<table style="width:100%; margin:0 auto">
				<tr>
					<td width="33%">&nbsp;</td>
					<td width="33%" align="right">
						<table style="margin: 0 auto;" class="tabla">
							<tr>
								<td colspan="2">
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
								</td>
							</tr>
							<tr>
								<td colspan="2" align="left">
									<strong><spring:message code="aca.Salon"/>:&nbsp;&nbsp;&nbsp;</strong> 
									<select name="SalonId" onchange='document.forma.submit()'>
									<%	String tmpSalon = "";
										for (int i = 0; i<lisSalon.size(); i++) {
											aca.catalogo.CatSalon salon = lisSalon.get(i);%>
											<option value="<%=salon.getSalonId()%>" <%if(salon.getSalonId().equals(salonId)){ out.print("selected"); tmpSalon = salon.getSalonId();}%>><%=salon.getNombreSalon()%></option>
									<%	}
										if(tmpSalon.equals("") && !lisSalon.isEmpty()) salonId = lisSalon.get(0).getSalonId();
									%>
									</select>
								</td>
							</tr>
						</table>
					</td>
					<td width="33%">
						<table style="margin: 0 auto;" class="tabla">
							<tr>
								<th><spring:message code="cargasGrupo.grupo.SalonesAsignadosMateria"/></th>
							</tr>
		<%						listaSalonesMateria = CargaGrupoHorarioU.getLista(conEnoc, cursoCargaId, "ORDER BY 2");
								for(aca.carga.CargaGrupoHorario tmpCgh : listaSalonesMateria){
									if(!tmpCgh.getHorario().contains("1")){
										tmpCgh.setCursoCargaId(cursoCargaId);
										tmpCgh.setSalonId(tmpCgh.getSalonId());
										CargaGrupoHorarioU.deleteReg(conEnoc, cursoCargaId);
									}
									else{
										CatSalon = CatSalonU.mapeaRegId(conEnoc, tmpCgh.getSalonId());%>
										<tr><td class="tr2"><a href="nuevoHorario?Ver=<%=ver %>&CarreraId=<%=carreraId%>&PlanId=<%=planId %>&CursoCargaId=<%=cursoCargaId%>&Materia=<%=materia%>&Grupo=<%=grupo %>&Profesor=<%=profesor %>&EdificioId=<%=CatSalon.getEdificioId()%>&SalonId=<%=CatSalon.getSalonId()%>"><%=CatSalon.getNombreSalon()%></a></td></tr>
		<%							}
								}%>
						</table>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr><td align="center" colspan="3"><input class="btn btn-info" type="button" value="<%=vista.equals("0") ? "View" : "Hide"%> Materias" onclick="javascript:CambiarVista(<%=vista.equals("0") ? "1" : "0"%>)" class="tr2"></td></tr>
			</table>
		<%	if(ver.equals("0")){%>
				<table style="width:95%; margin: 0 auto;">
					<tr>
						<td><input class="btn btn-primary" type="button" value="Saved" onclick="javascript:Guardar()" style="font-size:15; width: 100px; height: 40px;" class="tr2"></td>
						<td width="15%">&nbsp;</td>
						<td align="left"><font class="th2"><%=resultado%></font></td>					
					</tr>
				</table>
		<%	} %>
			<table style="width:95%; margin: 0 auto;"   class="tabla" border="1">
				<tr height="30px">
					<th width="11%"><spring:message code="aca.Hora"/></th>
					<th width="12%"><spring:message code="aca.Domingo"/></th>
					<th width="12%"><spring:message code="aca.Lunes"/></th>
					<th width="12%"><spring:message code="aca.Martes"/></th>
					<th width="12%"><spring:message code="aca.Miercoles"/></th>
					<th width="12%"><spring:message code="aca.Jueves"/></th>
					<th width="12%"><spring:message code="aca.Viernes"/></th>
					<th width="12%"><spring:message code="aca.Sabado"/></th>
				</tr>
			<%
				CargaGrupo carGrupo = new CargaGrupo();
				carGrupo = CargaGrupoU.mapeaRegId(conEnoc, cursoCargaId);
				ArrayList<aca.carga.CargaGrupoHorario> listaSalonesOcupados = CargaGrupoHorarioU.getListaSalonesCargaIdBloque(conEnoc, cursoCargaId, carGrupo.getBloqueId(), salonId, "ORDER BY 1");
				String facActual = aca.carga.CargaGrupoUtil.getFacultadGrupo(conEnoc, cursoCargaId); 
			
				int cont = 0;
				int j = 0;
				
				for(int k=0; k<3; k++){
					String tmpTurno = "";
					String tmpT = "";
					if(k==0){
						tmpT = "M";
						tmpTurno = "Morning";
					}
					else if(k==1){
						tmpT = "V";
						tmpTurno = "Afternoon";
					}
					else if(k==2){
						tmpT = "N";
						tmpTurno = "Night";
					}
					//pageContext.setAttribute("tmpTurno", tmpTurno);

					HashMap<Integer, aca.catalogo.CatHorarioFac> lisHorario = HorarioFacU.getListaTurnoHashMap(conEnoc, facActual, tmpT, "ORDER BY PERIODO");
					
					if(!lisHorario.isEmpty()){
						int i=0;
						if(k==1) i+=10;
						else if(k==2) i+=20;
						
						int tmpI = i+10;
						while(++i<=tmpI){
							aca.catalogo.CatHorarioFac horario = lisHorario.get(i);
	
							if(i%10==1){%>
								<tr>
									<td colspan="8" align="center" class="th2"><%=tmpTurno%></td>
								</tr>
		<%					}
							
							if(horario!=null){
								String tiempoIni = horario.getHoraIni();
				  				String horaComIni = tiempoIni.substring(11, 16)+" <b>am</b>";
				  				int horaIni = Integer.parseInt(tiempoIni.substring(11,13));
				  				int minIni 	= Integer.parseInt(tiempoIni.substring(14,16));
				  				
				  				String tiempoFin = horario.getHoraFin();
				  				String horaComFin = tiempoFin.substring(11, 16)+" <b>am</b>";
				  				int horaFin = Integer.parseInt(tiempoFin.substring(11,13));
				  				int minFin 	= Integer.parseInt(tiempoFin.substring(14,16));
				  				
				  				if(horaIni>=12){
				  					if(horaIni==12) horaComIni = horaIni<10?"0"+horaIni:horaIni+":"+(minIni<10?"0"+minIni:minIni)+" <b>pm</b>";
				  					else horaComIni = ((horaIni-12)<10?"0"+(horaIni-12):(horaIni-12))+":"+(minIni<10?"0"+minIni:minIni)+" <b>pm</b>";
				  				}
				  				if(horaFin>=12){
				  					if(horaIni==12) horaComFin = horaFin<10?"0"+horaFin:horaFin+":"+(minFin<10?"0"+minFin:minFin)+" <b>pm</b>";
				  					else horaComFin = ((horaFin-12)<10?"0"+(horaFin-12):(horaFin-12))+":"+(minFin<10?"0"+minFin:minFin)+" <b>pm</b>";
				  				}
				  				
				  				if(horaIni==0){
				  					horaComIni = "12"+":"+(minIni<10?"0"+minIni:minIni)+" <b>am</b>";
				  				}
				  				if(horaFin==0){
				  					horaComFin = "12"+":"+(minFin<10?"0"+minFin:minFin)+" <b>am</b>";
				  				}
		%>
				  				<tr height="40px">
									<td align="center" class="th4"><%=horaComIni%> - <%=horaComFin%></td>
		<%
								for(int x=1; x<=7; x++){
									String tmpFac = "";
									String tmpMat = "";
									ArrayList<aca.carga.CargaGrupoHorario> horariosChocan = new ArrayList<aca.carga.CargaGrupoHorario>();
									for(aca.carga.CargaGrupoHorario tmpCgh : listaSalonesOcupados){
										String facTmp = aca.carga.CargaGrupoUtil.getFacultadGrupo(conEnoc, tmpCgh.getCursoCargaId());
										if(facActual.equals(facTmp)){
											if(tmpCgh.getHorario().charAt(cont)=='1'){
												horariosChocan.add(tmpCgh);
											}
										}
										else{
											ArrayList<aca.catalogo.CatHorarioFac> tmpLisHorario = HorarioFacU.getLista(conEnoc, facTmp, "ORDER BY PERIODO");
											
											for(int t=1; t<=tmpLisHorario.size(); t++){
												aca.catalogo.CatHorarioFac tmpHorario = tmpLisHorario.get(t-1);
												int tmpTP = Integer.parseInt(tmpHorario.getPeriodo());
												
												String tmpTiempoIni = tmpHorario.getHoraIni();
								  				int tmpHoraIni = Integer.parseInt(tmpTiempoIni.substring(11,13));
								  				int tmpMinIni 	= Integer.parseInt(tmpTiempoIni.substring(14,16));
								  				
								  				String tmpTiempoFin = tmpHorario.getHoraFin();
								  				int tmpHoraFin = Integer.parseInt(tmpTiempoFin.substring(11,13));
								  				int tmpMinFin 	= Integer.parseInt(tmpTiempoFin.substring(14,16));
	
								  				if(new Date(2000, 1, 1, tmpHoraIni, tmpMinIni).equals(new Date(2000, 1, 1, horaIni, minIni)) && new Date(2000, 1, 1, tmpHoraFin, tmpMinFin).equals(new Date(2000, 1, 1, horaFin, minFin))){
								  					int tmpCont = 0;
								  					if(tmpTP<i) tmpCont = cont-(7*(i-tmpTP));
								  					else tmpCont = cont+(7*(tmpTP-i));
								  					
								  					if(tmpCont>=0 && tmpCont<=210){
									  					if(tmpCgh.getHorario().charAt(tmpCont)=='1'){
									  						horariosChocan.add(tmpCgh);
									  					}
								  					}
								  				}
								  				else if((new Date(2000, 1, 1, tmpHoraIni, tmpMinIni).before(new Date(2000, 1, 1, horaFin, minFin))) && (new Date(2000, 1, 1, tmpHoraFin, tmpMinFin).after(new Date(2000, 1, 1, horaIni, minIni)))){
								  					int tmpCont = 0;
								  					if(tmpTP<i) tmpCont = cont-(7*(i-tmpTP));
								  					else tmpCont = cont+(7*(tmpTP-i));
								  					
								  					if(tmpCont>=0 && tmpCont<=210){
									  					if(tmpCgh.getHorario().charAt(tmpCont)=='1'){
									  						horariosChocan.add(tmpCgh);
									  					}
								  					}
								  				}
											}
										}
									}

									if(horariosChocan.size()>1){
										boolean selecciona = false;
										for(aca.carga.CargaGrupoHorario tmpCgh : horariosChocan){
											if(cursoCargaId.equals(tmpCgh.getCursoCargaId())){
												selecciona = true;
												break;
											}
										}
										if(selecciona){%>
											<td align="center" class="th4"><img src="../../imagenes/horarioOcupado.png" title="Occupied by <%=horariosChocan.size()%>"></td>
		<%								}
										else{%>
											<td align="center"><img src="../../imagenes/horarioOcupado.png" title="Occupied by <%=horariosChocan.size()%>"></td>
		<%								}
									}
									else{
										boolean noOcupado = true;
										boolean selecciona = false;
										if(!horariosChocan.isEmpty()){
											aca.carga.CargaGrupoHorario cgh = horariosChocan.get(0);
											String planMatOrigen = aca.plan.CursoUtil.getPlanId(conEnoc, aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conEnoc, cgh.getCursoCargaId()));
											String facTmp = aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, aca.plan.PlanUtil.getCarreraId(conEnoc, planMatOrigen));
											
											if(cursoCargaId.equals(cgh.getCursoCargaId())){
												selecciona = true;
											}
											else{
												if(!facActual.equals(facTmp)){
													tmpFac = facTmp;
								  					noOcupado = false;
												}
												else{
													tmpMat = aca.vista.CargaAcademica.getNombreMateria(conEnoc, cgh.getCursoCargaId())+" ("+planMatOrigen+")";
													tmpFac = facActual;
													noOcupado = false;
												}
											}
										}
		%>
										<td align="center" id="td<%=cont %>"
			<%								if(selecciona){%>
												class="th4" <%	if(ver.equals("0")){%>style="cursor: pointer;" onclick="javascript:ModificarCheckbox('<%=cont %>')"<%} %>>
													<img width="30px" src="../../imagenes/activa.png" id="i<%=cont %>">
													<input type='Checkbox' name="p<%=cont%>" id="p<%=cont %>" style="visibility:hidden;" checked><%
											}
											else if(noOcupado){%>
												class="tr2" <%	if(ver.equals("0")){%>style="cursor: pointer;" onclick="javascript:ModificarCheckbox('<%=cont %>')"<%} %>>
													<img width="0px" src="" id="i<%=cont %>">
													<input type='Checkbox' name="p<%=cont %>" id="p<%=cont %>" style="visibility:hidden;">
			<%								}
											else{
												String mensaje = "Occupied by: ";
												if(!tmpMat.equals("")){
													mensaje += " subject from "+tmpMat;
												}
												else{
													mensaje += ": "+aca.catalogo.CatFacultadUtil.getNombreCorto(conEnoc, tmpFac);
												}
												
												if(vista.equals("0")){%>
													><img src="../../imagenes/horarioOcupado.png" title="<%=mensaje%>">
			<%									}
												else{
													mensaje = !tmpMat.equals("") ? tmpMat : aca.catalogo.CatFacultadUtil.getNombreCorto(conEnoc, tmpFac);
												%>
													><font style="color:<%=colorTablas%>;"><%=mensaje%></font>
			<%									}
											}%>
										</td>
		<%
									}
									cont++;
								}%>
								</tr>
			<%				}
							else{
								cont+=7;
							}
						}
					}
					else{
						cont+=70;
					}
				}
			%>	
			</table>
		</form>
	</body>
</html>
<%@ include file="../../cierra_enoc.jsp"%>