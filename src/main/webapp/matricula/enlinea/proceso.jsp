<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="acceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil" />
<jsp:useBean id="vistaAlumno" scope="page" class="aca.vista.Alumno"/>
<jsp:useBean id="cargasEnlineaU" scope="page" class="aca.carga.CargaEnlineaUtil"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="AlumEnLineaU" scope="page" class="aca.alumno.AlumEnlineaUtil"/>

<html>
  <head>
    <script type="text/javascript">
		function Carga(){
			document.forma.submit();		
		}
	</script>
  </head>
<%
	String codigoUsuario = (String) session.getAttribute("codigoPersonal");	
	acceso = AccesoU.mapeaRegId(conEnoc, codigoUsuario);
	String filtro = request.getParameter("Filtro")==null ? "0" : request.getParameter("Filtro");
	String facultadElegida = request.getParameter("Facultad")==null ? "0" : request.getParameter("Facultad");
	
	ArrayList<aca.carga.CargaEnlinea> cargas 	= cargasEnlineaU.getListActivas(conEnoc, "ORDER BY 1");
	String cargaId = request.getParameter("carga")==null ? cargas.get(0).getCargaId() : request.getParameter("carga"); 
	
	ArrayList<aca.alumno.AlumEnlinea> alumnosInscripcion = aca.alumno.AlumEnlineaUtil.getListCargas(conEnoc, cargaId, "ORDER BY ENOC.FACULTAD(ALUM_CARRERA_ID(CODIGO_PERSONAL)), ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
%>
	<body>
		<br>
		<form name="forma" action="proceso" method="post">
			<table class="tabla" width="60%" align="center">			
				<tr>
				  <td style="text-align:center">
				    <select id="carga" name="carga" onChange="Carga()">
	<%				for(int i=0; i<cargas.size(); i++){						
						aca.carga.CargaEnlinea carga = cargas.get(i);%>
							<option value="<%=carga.getCargaId()%>" <%if(cargaId.equals(carga.getCargaId())){out.print("selected");} %>><%=carga.getNombre()%></option>
						<%}%>
					</select>
				  </td>
				</tr>
				<tr><td style="text-align:center"><font size="5">Proceso de inscripción en línea</font></td></tr>
				<tr>
					<td>
						<table class="tabla" width="90%" align="center">
							<tr>
								<th><spring:message code="aca.Facultad"/></th>
								<th>Sin responder</th>
								<th>Autorizados</th>
								<th>No autorizados</th>
								<th>Cálculo E.</th>
								<th><spring:message code="aca.Inscritos"/></th>
								<th><spring:message code="aca.Total"/></th>
							</tr>
						<%	String facultadId = "";
							int totalPeticiones 	= 0;
							int totalAutorrizados 	= 0;
							int totalNoAutorrizados = 0;
							int totalPagos 			= 0;
							int totalInscritos 		= 0;
							int totalCont 			= 0;
							int peticiones 			= 0;
							int autorrizados 		= 0;
							int noAutorrizados 		= 0;
							int pagos 				= 0;
							int inscritos 			= 0;
							int cont 				= 0;
							for(int i=0; i<alumnosInscripcion.size(); i++){
								aca.alumno.AlumEnlinea alumno = alumnosInscripcion.get(i);
								vistaAlumno.mapeaRegId(conEnoc, alumno.getCodigoPersonal());
								
								String tmpFacId = aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, aca.alumno.PlanUtil.getCarreraId(conEnoc, alumno.getCodigoPersonal()));
								if(acceso.getAccesos().indexOf(vistaAlumno.getCarreraId())!= -1 || acceso.getAdministrador().equals("S")){
									if(!facultadId.equals(tmpFacId)){
										if(!facultadId.equals("")){%>
											<tr class="tr2">
												<td class="th2"><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, facultadId) %></td>
												<td align="center" onclick="location='proceso?Facultad=<%=facultadId %>&Filtro=1'" style="cursor:pointer;"><%=peticiones %></td>
												<td align="center" onclick="location='proceso?Facultad=<%=facultadId %>&Filtro=2'" style="cursor:pointer;"><%=autorrizados %></td>
												<td align="center" onclick="location='proceso?Facultad=<%=facultadId %>&Filtro=3'" style="cursor:pointer;"><%=noAutorrizados %></td>
												<td align="center" onclick="location='proceso?Facultad=<%=facultadId %>&Filtro=4'" style="cursor:pointer;"><%=pagos %></td>
												<td align="center" onclick="location='proceso?Facultad=<%=facultadId %>&Filtro=5'" style="cursor:pointer;"><%=inscritos %></td>
												<td align="center" onclick="location='proceso?Facultad=<%=facultadId %>&Filtro=6'" style="cursor:pointer;"><%=cont %></td>
											</tr>
									<%	}
										facultadId = tmpFacId;
										totalPeticiones 	+= peticiones;
										totalAutorrizados 	+= autorrizados;
										totalNoAutorrizados += noAutorrizados;
										totalPagos			+= pagos;
										totalInscritos 		+= inscritos;
										totalCont 			+= cont;
										peticiones 			= 0;
										autorrizados 		= 0;
										noAutorrizados 		= 0;
										pagos				= 0;
										inscritos 			= 0;
										cont				= 0;
									}
	
									cont++;
					//----------------------Agregar el bloque para Matricula de Verano-----------------------------------------------
									boolean ins 	= aca.financiero.FesCcobroUtil.getIncritoEnLinea(conEnoc, alumno.getCodigoPersonal(), cargaId, "1");
									boolean calc 	= AlumEnLineaU.getStatusFinanciero(conEnoc, alumno.getCodigoPersonal()).equals("I");
									if(ins && calc){
										inscritos++;
										ins = true;
									}
									if(!ins && calc){
										pagos++;
										calc = true;
									}
									String coor = alumno.getCoordinador();
									if(coor.equals("0000000") && !ins && !calc) peticiones++;
									else if(alumno.getSolicitud().equals("N") && !ins && !calc) noAutorrizados++;
									else if(!ins && !calc) autorrizados++;
								}
							}
							totalPeticiones 	+= peticiones;
							totalAutorrizados 	+= autorrizados;
							totalNoAutorrizados += noAutorrizados;
							totalPagos			+= pagos;
							totalInscritos 		+= inscritos;
							totalCont 			+= cont;
							%>
							<tr class="tr2">
								<td class="th2"><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, facultadId) %></td>
								<td align="center" onclick="location='proceso?Facultad=<%=facultadId %>&Filtro=1'" style="cursor:pointer;"><%=peticiones %></td>
								<td align="center" onclick="location='proceso?Facultad=<%=facultadId %>&Filtro=2'" style="cursor:pointer;"><%=autorrizados %></td>
								<td align="center" onclick="location='proceso?Facultad=<%=facultadId %>&Filtro=3'" style="cursor:pointer;"><%=noAutorrizados %></td>
								<td align="center" onclick="location='proceso?Facultad=<%=facultadId %>&Filtro=4'" style="cursor:pointer;"><%=pagos %></td>
								<td align="center" onclick="location='proceso?Facultad=<%=facultadId %>&Filtro=5'" style="cursor:pointer;"><%=inscritos %></td>
								<td align="center" onclick="location='proceso?Facultad=<%=facultadId %>&Filtro=6'" style="cursor:pointer;"><%=cont %></td>
							</tr>
							<tr class="th2">
								<th>Totales</th>
								<td align="center" onclick="location='proceso?Facultad=todas&Filtro=1'" style="cursor:pointer;"><%=totalPeticiones %></td>
								<td align="center" onclick="location='proceso?Facultad=todas&Filtro=2'" style="cursor:pointer;"><font size="3"><b><%=totalAutorrizados %></b></font></td>
								<td align="center" onclick="location='proceso?Facultad=todas&Filtro=3'" style="cursor:pointer;"><%=totalNoAutorrizados %></td>
								<td align="center" onclick="location='proceso?Facultad=todas&Filtro=4'" style="cursor:pointer;"><%=totalPagos %></td>
								<td align="center" onclick="location='proceso?Facultad=todas&Filtro=5'" style="cursor:pointer;"><font size="3"><b><%=totalInscritos %></b></font></td>
								<td align="center" onclick="location='proceso?Facultad=todas&Filtro=6'" style="cursor:pointer;"><font size="3"><b><%=totalCont %></b></font></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
		<br>
		<%	if(!filtro.equals("0")){%>
				<table style="width:80%" class="table table-condensed" align="center">
				<%	facultadId = "";
					int cont2 = 0;
					for(int i=0; i<alumnosInscripcion.size(); i++){
						aca.alumno.AlumEnlinea alumno = alumnosInscripcion.get(i);
						vistaAlumno.mapeaRegId(conEnoc, alumno.getCodigoPersonal());
						String tmpFacId = aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, aca.alumno.PlanUtil.getCarreraId(conEnoc, alumno.getCodigoPersonal()));
						if(acceso.getAccesos().indexOf(vistaAlumno.getCarreraId())!= -1 || acceso.getAdministrador().equals("S")){
							boolean mostrar = false;
							boolean ins 	= aca.financiero.FesCcobroUtil.getIncritoEnLinea(conEnoc, alumno.getCodigoPersonal(), cargaId, "1");
							boolean calc 	= AlumEnLineaU.getStatusFinanciero(conEnoc, alumno.getCodigoPersonal()).equals("I");
							if(filtro.equals("1")){
								if(alumno.getCoordinador().equals("0000000") && alumno.getSolicitud().equals("S") && !ins && !calc) mostrar = true;
							}
							else if(filtro.equals("2")){
								if(!alumno.getCoordinador().equals("0000000") && alumno.getSolicitud().equals("S") && !ins && !calc) mostrar = true;
							}
							else if(filtro.equals("3")){
								if(alumno.getSolicitud().equals("N") && !ins && !calc) mostrar = true;
							}
							else if(filtro.equals("4") && calc && !ins){
								mostrar = true;
							}
							else if(filtro.equals("5") && ins && calc){
								mostrar = true;
							}
							else if(filtro.equals("6")){
								mostrar = true;
							}
							
							if((facultadElegida.equals(tmpFacId)||facultadElegida.equals("todas")) && mostrar){
								if(!facultadId.equals(tmpFacId)){
									facultadId = tmpFacId;%>
									<tr><td class="th2" colspan="7" align="center" height="25px"><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, facultadId) %></td></tr>
									<tr>
										<th width="2%"><spring:message code="aca.Numero"/></th>
										<th width="7%"><spring:message code="aca.Matricula"/></th>
										<th width="22%"><spring:message code="aca.Nombre"/></th>
										<th width="10%">Plan de estudios</th>
										<th width="10%"><spring:message code="aca.Correo"/></th>
										<th width="12%"><spring:message code="aca.Estado"/></th>
										<th width="22%">Autorizó</th>
									</tr>
							<%	}
								aca.alumno.AlumPersonal alumPersonal = new aca.alumno.AlumPersonal();
								alumPersonal = AlumUtil.mapeaRegId(conEnoc, alumno.getCodigoPersonal());
								cont2++;%>
								<tr class="tr2">
									<td align="center"><%=cont2 %></td>
									<td align="center"><%=alumno.getCodigoPersonal() %></td>
									<td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, alumno.getCodigoPersonal(), "APELLIDO") %></td>
									<td align="center"><%=aca.alumno.PlanUtil.getPlanActual(conEnoc, alumno.getCodigoPersonal()) %></td>
									<td><%=alumPersonal.getEmail() %></td>
								<%	String coor = alumno.getCoordinador();
									if(coor.equals("0000000")){%>
										<td align="center">-</td>
								<%	}
									else if(alumno.getSolicitud().equals("N")){%>
										<td align="center">No Autorizado</td>
								<%	}
									else{%>
										<td align="center"><spring:message code='aca.Autorizado'/></td>
								<%	}
									String usu = aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, alumno.getCoordinador(), "APELLIDO");
									if(usu.equals("0000000")){%>
										<td align="center">-</td>
								<%	}
									else{%>
										<td align="left"><%=usu %></td>
								<%	}%>
								</tr>
						<%	}
						}
					}%>
				</table>
		<%	} %>
	</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>