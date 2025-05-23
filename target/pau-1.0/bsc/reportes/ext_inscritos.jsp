<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.carga.Carga"%>
<%@page import="aca.catalogo.CatModalidad"%>
<%@page import="aca.util.Fecha"%>
<%@ page import="java.util.Date,java.text.SimpleDateFormat" %>

<jsp:useBean id="EstadisticaUtil" scope="page" class="aca.vista.EstadisticaUtil" />
<jsp:useBean id="carga" scope="page" class="aca.carga.Carga" />
<jsp:useBean id="cargaU" scope="page" class="aca.carga.CargaUtil" />
<jsp:useBean id="catModalidad" scope="page" class="aca.catalogo.CatModalidad" />
<jsp:useBean id="catModalidadU" scope="page" class="aca.catalogo.ModalidadUtil" />
<jsp:useBean id="acceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil"/>

<html>
	<head></head>
<%
	String codigo			= (String) session.getAttribute("codigoPersonal");	
	String sCarga			= request.getParameter("f_carga");
	
	Date fechav;
	String s_matricula 			= "";
	String s_nombre_alumno 		= "";
	String s_facultad 			= "";
	String s_facultadtemp 		= "X";
	String s_carrera 			= "";
	String s_carrera_temp		= "X";
	String s_nombre_facultad 	= "";
	String s_nombre_carrera		= "";
	String s_nombre_pais		= "";
	String s_fecha_vence		= "";
	String s_fm3_vence			= "";
	String s_pasaporte_vence	= "";
	String s_iddocumento		= "";
	String s_sexo 				= "";
	String s_registrado			= "";
	String s_auto_sector		= "";
	String modalidad			= "";
	String tipo					= "";
	
	String diasr="",color="";
	Date hoy = new Date();	
	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	int dias=0;
	int n_total = 0;
	
	acceso = AccesoU.mapeaRegId(conEnoc, codigo);	
	if(AccesoU.existeReg(conEnoc, codigo)==true){
		String cargas						= "";
		String modalidades					= "";
		String fInscripcion					= request.getParameter("fecha")==null?Fecha.getHoy():request.getParameter("fecha");
		ArrayList<CatModalidad> lisModalidad = catModalidadU.getListAll(conEnoc, "ORDER BY MODALIDAD_ID");
		ArrayList<Carga> lisCarga = cargaU.getListPorFecha(conEnoc, fInscripcion, "ORDER BY CARGA_ID");
		
		for(int i = 0; i < lisCarga.size(); i++){
			carga = (Carga) lisCarga.get(i);
			if(request.getParameter("Accion")==null){
				if(cargas.equals("")) cargas = "'"+carga.getCargaId()+"'";
				else cargas += ",'"+carga.getCargaId()+"'";	
			}
			else{
				if(request.getParameter(carga.getCargaId()) != null){
					if(cargas.equals("")) cargas = "'"+carga.getCargaId()+"'";
					else cargas += ",'"+carga.getCargaId()+"'";	
				}
			}
		}
		
		for(int i = 0; i < lisModalidad.size(); i++){
			catModalidad = (CatModalidad) lisModalidad.get(i);
			if(request.getParameter("Accion")==null){
				if(modalidades.equals("")) modalidades = "'"+catModalidad.getModalidadId()+"'";
				else modalidades += ",'"+catModalidad.getModalidadId()+"'";	
			}
			else{
				if(request.getParameter("mod-"+catModalidad.getModalidadId()) != null){
					if(modalidades.equals("")) modalidades = "'"+catModalidad.getModalidadId()+"'";
					else modalidades += ",'"+catModalidad.getModalidadId()+"'";	
				}
			}
		}
		
		if(modalidades.equals("")) modalidades="' '";
		if(cargas.equals("")) cargas="' '";
%>
		<body>
		<div class="container-fluid">
		<h1>Listado de extranjeros inscritos</h1>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="menu"><i class="fas fa-arrow-left"></i></a>
		</div>
			<form id="forma" name="forma" action="ext_inscritos?Accion=1" method="post">
				<table class="table table-bordered">
					<tr>
						<td align="center">
							<table class="table table-bordered">
								<thead class="table-info">
								<tr>
									<th style="font-size:14px;" width="60%"><spring:message code="aca.Cargas"/></th>
									<th style="font-size:14px;"><spring:message code="aca.Modalidades"/></th>
								</tr>
								</thead>
								<tr>
									<td align="center" valign="top">
										<table class="table table-bordered">
											<tr>
												<td><spring:message code="aca.Seleccionar"/>:</i>
											  		<a onclick="jQuery('.checkboxCarga').prop('checked', true)" class="btn btn-info"><spring:message code='aca.Todos'/></a> 
											  		<a class="btn btn-info" onclick="jQuery('.checkboxCarga').prop('checked', false)"><spring:message code='aca.Ninguno'/></a>
										  		</td>
											</tr>
										<%
											String checkCarga = "";
											for(int i = 0; i < lisCarga.size(); i++){
												carga = (Carga) lisCarga.get(i);
												if ( cargas.indexOf("'"+carga.getCargaId()+"'") != -1 ) checkCarga = "checked"; else checkCarga = ""; %>
												<tr>
													<td>
														<input class="checkboxCarga" type="checkbox" id="<%=carga.getCargaId() %>" name="<%=carga.getCargaId() %>" value="<%=carga.getCargaId() %>" <%=checkCarga%>/>
														<%=carga.getCargaId() %> | <b><i><%=carga.getNombreCarga() %></i></b> [<%=carga.getFInicio() %> - <%=carga.getFFinal() %>																					
													</td>
												</tr>
										<%	} %>
										</table>
									</td>
									<td align="center" valign="top">
										<table class="table table-bordered">
											<tr>
												<td><spring:message code="aca.Seleccionar"/>:
											  		<a onclick="jQuery('.checkboxMod').prop('checked', true)" class="btn btn-info">Todos</a> 
											  		<a class="btn btn-info" onclick="jQuery('.checkboxMod').prop('checked', false)"><spring:message code='aca.Ninguno'/></a>
										  		</td>
											</tr>
										<%
											String checkModalidad = "";
											for(int i = 0; i < lisModalidad.size(); i++){
												catModalidad = (CatModalidad) lisModalidad.get(i);
												if ( modalidades.indexOf("'"+catModalidad.getModalidadId()+"'") != -1 ) checkModalidad = "checked"; else checkModalidad = ""; %>
												<tr>
													<td>
														<input class="checkboxMod" type="checkbox" id="mod-<%=catModalidad.getModalidadId() %>" name="mod-<%=catModalidad.getModalidadId() %>" value="<%=catModalidad.getModalidadId() %>" <%=checkModalidad%>/>
														<%=catModalidad.getNombreModalidad() %>
														<%%>
													</td>
												</tr>
										<%	} %>
										</table>
									</td>
								</tr>
							</table>
					<tr>
						<td align="center" style="text-align:center;">
							<input class="btn btn-primary" type="submit" value="Mostrar" onclick="document.forma.submit();"/>
						</td>
					</tr>
				</table>
			<%
				String tmpFacultadId 	= ""; 
				String tmpCarreraId 	= ""; 
				int cont = 1;
				int hombres = 0;
				int mujeres = 0;
				int totalHombres = 0;
				int totalMujeres = 0;
				ArrayList<aca.vista.Estadistica> listaExtranjerosInscritos = EstadisticaUtil.getListaExtranjerosInscritosCargaYModalidad(conEnoc, cargas, modalidades, "ORDER BY FACULTAD_ID, CARRERA_ID, NOMBRE_LEGAL");
				for(int i=0; i<listaExtranjerosInscritos.size(); i++){
					aca.vista.Estadistica insc = listaExtranjerosInscritos.get(i);
					if(acceso.getAccesos().indexOf(insc.getCarreraId())!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S")){
						String facultadId = insc.getFacultadId();
						String carreraId = insc.getCarreraId();
						
						if(!tmpFacultadId.equals(facultadId)){
							tmpFacultadId = facultadId; %>
							<br>
							<table class="table table-bordered">
							<thead>
								<tr>
							    	<td align="center" colspan="10"><b><font size="3"><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, facultadId) %></font></b></td>
							  	</tr>
							</thead>
					<%	}
						
						if(!tmpCarreraId.equals(carreraId)){
							cont = 1;
							tmpCarreraId = carreraId; %>
							<thead>
							<tr><td colspan="10">&nbsp;</td></tr>
							</thead>
							<thead>
							<tr> 
								<td colspan="10" class="titulo2"><b><font size="2"><%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, carreraId) %></font></b></td>
						  	</tr>
							</thead>
							<thead class="table-info">
							<tr> 
								<th width="4%"><b><spring:message code="aca.Numero"/></b></th>
							    <th width="10%"><b><spring:message code="aca.Matricula"/></b></th>
							    <th width="30%"><b><spring:message code="aca.Nombre"/></b></th>
							    <th width="3%"><b><spring:message code="aca.Ciclo"/></b></th>
							    <th width="6%"><b><spring:message code="aca.Pais"/></b></th>
							    <th width="3%"><b><spring:message code="aca.Genero"/></b></th>
							    <th width="6%"><b><spring:message code="aca.Modalidad"/></b></th>
							    <th width="10%"><b><spring:message code="aca.Tipo"/></b></th>
						  	</tr>
							</thead>
					<%	}
						s_fm3_vence = aca.leg.LegExtdocUtil.getFechaVenceFM3(conEnoc, insc.getCodigoPersonal());
						s_pasaporte_vence = aca.leg.LegExtdocUtil.getFechaVencePasaporte(conEnoc, insc.getCodigoPersonal());
						dias=0;
						if(s_pasaporte_vence.equals("00/00/0000")&&s_fm3_vence.equals("00/00/0000")){
							diasr = "-";
						}
						else{
							if(s_fm3_vence==null||s_fm3_vence.equals(" ")||s_fm3_vence.equals(""))
								diasr="-";
							else{
								fechav 	= df.parse(s_fm3_vence);
								dias 	= new Long((fechav.getTime()-hoy.getTime())/1000/60/60/24).intValue();
								diasr 	= dias+"";
							}
						}
						if(dias>0 && dias<30) color="#F39603";
						else if(dias<=0) color="#CE0000";
						else color="";
						
						if(insc.getSexo().equals("M")){
							hombres++;
						}else{
							mujeres++;
						}
					%>
						<tr> 
					    	<td style="text-align:center"><%=cont %></td>
						    <td style="text-align:center"><%=insc.getCodigoPersonal() %></td>
						    <td><font color="<%=color%>"><%=insc.getNombreLegal() %></font></td>
						    <td style="text-align:center"><%=aca.alumno.PlanUtil.getSem(conEnoc, insc.getCodigoPersonal(), insc.getPlanId()) %></td>
						    <td><%=aca.catalogo.PaisUtil.getNombrePais(conEnoc, insc.getPaisId()) %></td>
						    <td style="text-align:center"><%=insc.getSexo() %></td>
						    <td style="text-align:center"><%=aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, insc.getModalidadId()) %></td>
						    <td style="text-align:center"><%=aca.alumno.AcademicoUtil.getTipoAlumno(conEnoc, insc.getCodigoPersonal()) %></td>
					  	</tr>
				<%		
						if((i+1)!=listaExtranjerosInscritos.size()){
							if(!listaExtranjerosInscritos.get(i+1).getCarreraId().equals(carreraId)){
				%>
						<tr>
							<td colspan="8">
							<h4>TOTAL</h4> Hombres: <%=hombres %> Mujeres: <%=mujeres %> 
							</td>
						</tr>
				<%
								mujeres=0;hombres=0;
							}
						}else{
				%>
						<tr>
							<td colspan="8">
							<h4>TOTAL</h4>Hombres: <%=hombres %> Mujeres: <%=mujeres %> 
							</td>
						</tr>
				<%
						}
				
						totalHombres+=hombres;
						totalMujeres+=mujeres;
						cont++;
					}
				}
			%>
					<tr>
						<td colspan="8" style="text-align:center;"><br>
						<h4>TOTAL GENERAL</h4>Hombres: <%=totalHombres %> <br>Mujeres: <%=totalMujeres %> 
						</td>
					</tr>
				</table>
				
			</form>
		  </div>
		</body>
<%	}
	else{ %>
		<br>
		<b><font color="#000099">No tiene acceso</font></b>
<%	} %>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>