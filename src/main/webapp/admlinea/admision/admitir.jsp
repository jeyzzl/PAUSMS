<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.alumno.AlumPersonal"%>
<%@page import="aca.alumno.AlumUtil"%>
<%@page import="aca.catalogo.CatTipoAlumno"%>
<%@page import="aca.catalogo.CatModalidad"%>
<%@page import="aca.util.Fecha"%>

<jsp:useBean id="solicitud" scope="page" class="adm.alumno.Solicitud"/>
<jsp:useBean id="alumPersonal" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="alumUbicacion" scope="page" class="aca.alumno.AlumUbicacion"/>
<jsp:useBean id="AlumUbicacionU" scope="page" class="aca.alumno.UbicacionUtil"/>
<jsp:useBean id="alumAcademico" scope="page" class="aca.alumno.AlumAcademico"/>
<jsp:useBean id="AlumAcademicoU" scope="page" class="aca.alumno.AcademicoUtil"/>
<jsp:useBean id="catTipoAlumno" scope="page" class="aca.catalogo.CatTipoAlumno"/>
<jsp:useBean id="catTipoAlumnoU" scope="page" class="aca.catalogo.TipoAlumnoUtil"/>
<jsp:useBean id="catModalidad" scope="page" class="aca.catalogo.CatModalidad"/>
<jsp:useBean id="catModalidadU" scope="page" class="aca.catalogo.ModalidadUtil"/>
<%
	String codigoUsuario		= (String) session.getAttribute("codigoPersonal");
	int accion					= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
	String folio				= request.getParameter("Folio");
	String respuesta			= "";	
	
	ArrayList<AlumPersonal> lisSimilares	= null;
	
	solicitud.mapeaRegId(conEnoc, folio);
	
	lisSimilares = aca.alumno.AlumUtil.BuscaDuplicados(conEnoc, solicitud.getPerNombre(), solicitud.getPerPaterno(), solicitud.getPerMaterno(),70);
	
	switch(accion){
		case 1:{//Alumno ya existente
			conEnoc.setAutoCommit(false);
			String codigoPersonal = request.getParameter("codigoPersonal");
			
			alumPersonal = AlumUtil.mapeaRegId(conEnoc, codigoPersonal);
			if(alumPersonal.getCotejado().equals("N")){
				alumPersonal.setNombre(solicitud.getPerNombre());
				alumPersonal.setApellidoPaterno(solicitud.getPerPaterno());
				alumPersonal.setApellidoMaterno(solicitud.getPerMaterno());
				alumPersonal.setNombreLegal(solicitud.getPerNombre()+" "+solicitud.getPerPaterno()+" "+solicitud.getPerMaterno());
				alumPersonal.setFNacimiento(solicitud.getPerNacimiento());
			}
			alumPersonal.setSexo(solicitud.getPerGenero());
			alumPersonal.setEstadoCivil(solicitud.getPerEdocivil());
			alumPersonal.setReligionId(solicitud.getPerReligion());
			alumPersonal.setBautizado(solicitud.getPerBautizado());
			alumPersonal.setPaisId(solicitud.getNacPais());
			alumPersonal.setEstadoId(solicitud.getNacEdo());
			alumPersonal.setCiudadId(solicitud.getNacCiudad());
			alumPersonal.setNacionalidad(solicitud.getNacNacionalidad());
			alumPersonal.setEmail(solicitud.getPerEmail());
			alumPersonal.setEstado("A");
			
			if(AlumUtil.updateReg(conEnoc, alumPersonal)){
				alumUbicacion = AlumUbicacionU.mapeaRegId(conEnoc, codigoPersonal);				
				alumUbicacion.setTDireccion(solicitud.getPerCalle()+" "+solicitud.getPerNumero());
				alumUbicacion.setTColonia(solicitud.getPerColonia());
				alumUbicacion.setTCodigo(solicitud.getPerCp());
				alumUbicacion.setTApartado("X");
				alumUbicacion.setTTelefono(solicitud.getPerTel());
				alumUbicacion.setTPais(solicitud.getPerPais());
				alumUbicacion.setTEstado(solicitud.getPerEdo());
				alumUbicacion.setTCiudad(solicitud.getPerCiudad());
				if(AlumUbicacionU.updateReg(conEnoc, alumUbicacion)){
					alumAcademico = AlumAcademicoU.mapeaRegId(conEnoc, codigoPersonal);
					alumAcademico.setTipoAlumno(request.getParameter("tipoAlumno"));
					alumAcademico.setResidenciaId("I");
					alumAcademico.setRequerido("S");
					alumAcademico.setDormitorio("0");
					////////Fechas de solicitud, admision y alta...
					alumAcademico.setModalidadId(request.getParameter("modalidadId"));			
					//HO_status???
					if(AlumAcademicoU.updateReg(conEnoc, alumAcademico)){
						solicitud.setEstado("7");
						solicitud.setMatricula(alumPersonal.getCodigoPersonal());
						if(solicitud.updateReg(conEnoc)){
							conEnoc.commit();
							session.setAttribute("codigoAlumno", codigoPersonal);
							//response.sendRedirect("../../admision/datos/alumno.jsp");
							out.print("<div class='alert alert-success'><b>Grabado... <a class='btn btn-primary' href='../../admision/datos/alumno'>Regresar</a></div>");
						}else{
							conEnoc.rollback();
							respuesta = "<font size=2 color=red>Ocurri&oacute; un error(4). Int&eacute;ntelo de nuevo</font>";
						}
					}else{
						conEnoc.rollback();
						respuesta = "<font size=2 color=red>Ocurri&oacute; un error(3). Int&eacute;ntelo de nuevo</font>";
					}
				}else{
					conEnoc.rollback();
					respuesta = "<font size=2 color=red>Ocurri&oacute; un error(2). Int&eacute;ntelo de nuevo</font>";
				}
			}else{
				conEnoc.rollback();
				respuesta = "<font size=2 color=red>Ocurri&oacute; un error(1). Int&eacute;ntelo de nuevo</font>";
			}
			
			conEnoc.setAutoCommit(true);
		}break;
		case 2:{//Nuevo Alumno
			conEnoc.setAutoCommit(false);
			alumPersonal.setCodigoPersonal(AlumUtil.maximoReg(conEnoc, "UM"));
			alumPersonal.setNombre(solicitud.getPerNombre());
			alumPersonal.setApellidoPaterno(solicitud.getPerPaterno());
			alumPersonal.setApellidoMaterno(solicitud.getPerMaterno().equals("")?"-":solicitud.getPerMaterno());
			alumPersonal.setNombreLegal(solicitud.getPerNombre()+" "+solicitud.getPerPaterno()+" "+solicitud.getPerMaterno());
			alumPersonal.setFNacimiento(solicitud.getPerNacimiento());
			alumPersonal.setSexo(solicitud.getPerGenero());
			alumPersonal.setEstadoCivil(solicitud.getPerEdocivil());
			alumPersonal.setReligionId(solicitud.getPerReligion());
			alumPersonal.setBautizado(solicitud.getPerBautizado());
			alumPersonal.setPaisId(solicitud.getNacPais());
			alumPersonal.setEstadoId(solicitud.getNacEdo());
			alumPersonal.setCiudadId(solicitud.getNacCiudad());
			alumPersonal.setNacionalidad(solicitud.getNacNacionalidad());
			alumPersonal.setEmail(solicitud.getPerEmail());
			alumPersonal.setCurp("");			
			alumPersonal.setEstado("A");
			alumPersonal.setCotejado("N");
			alumPersonal.setCodigoSe("");
			alumPersonal.setUsAlta(codigoUsuario);
			alumPersonal.setFCreado(aca.util.Fecha.getHoy());			
			
			if(AlumUtil.insertReg(conEnoc, alumPersonal)){
				alumUbicacion.setCodigoPersonal(alumPersonal.getCodigoPersonal());				
				alumUbicacion.setTDireccion(solicitud.getPerCalle()+" "+solicitud.getPerNumero());
				alumUbicacion.setTColonia(solicitud.getPerColonia());
				alumUbicacion.setTCodigo(solicitud.getPerCp());
				alumUbicacion.setTApartado("X");
				alumUbicacion.setTTelefono(solicitud.getPerTel());
				alumUbicacion.setTPais(solicitud.getPerPais());
				alumUbicacion.setTEstado(solicitud.getPerEdo());
				alumUbicacion.setTCiudad(solicitud.getPerCiudad());
				if(AlumUbicacionU.insertReg(conEnoc, alumUbicacion)){
					alumAcademico.setCodigoPersonal(alumPersonal.getCodigoPersonal());
					alumAcademico.setTipoAlumno(request.getParameter("tipoAlumno"));
					alumAcademico.setClasFin(solicitud.getPerReligion().equals("1")?"1":"2");
					alumAcademico.setObrero("N");
					alumAcademico.setObreroInstitucion("0");
					alumAcademico.setResidenciaId("I");
					alumAcademico.setDormitorio("X");
					alumAcademico.setFSolicitud(solicitud.getFecha());
					alumAcademico.setFAdmision(Fecha.getHoy());
					alumAcademico.setFAlta(Fecha.getHoy());					
					alumAcademico.setModalidadId(request.getParameter("modalidadId"));
					alumAcademico.setExtensionId("10");					
					if(AlumAcademicoU.insertReg(conEnoc, alumAcademico)){
						solicitud.setEstado("7");
						solicitud.setMatricula(alumPersonal.getCodigoPersonal());
						if(solicitud.updateReg(conEnoc)){
							conEnoc.commit();
							session.setAttribute("codigoAlumno", alumPersonal.getCodigoPersonal());
							out.print("<div class='alert alert-success'><b>Grabado... <a class='btn btn-primary' href='../../admision/datos/alumno.jsp'>Regresar</a></div>");
							//response.sendRedirect("../../admision/datos/alumno.jsp");
						}else{
							conEnoc.rollback();
							respuesta = "<font size=2 color=red>Ocurri&oacute; un error(4-"+alumPersonal.getCodigoPersonal()+"). Int&eacute;ntelo de nuevo</font>";
						}
					}else{
						conEnoc.rollback();
						respuesta = "<font size=2 color=red>Ocurri&oacute; un error(3-"+alumPersonal.getCodigoPersonal()+"). Int&eacute;ntelo de nuevo</font>";
					}
				}else{
					conEnoc.rollback();
					respuesta = "<font size=2 color=red>Ocurri&oacute; un error(2-"+alumPersonal.getCodigoPersonal()+"). Int&eacute;ntelo de nuevo</font>";
				}
			}else{
				conEnoc.rollback();
				respuesta = "<font size=2 color=red>Ocurri&oacute; un error(1-"+alumPersonal.getCodigoPersonal()+"). Int&eacute;ntelo de nuevo</font>";
			}
			
			conEnoc.setAutoCommit(true);
		}break;
	}
%>
<head>
<script type="text/javascript">
	function reingreso(codigoPersonal, folio){
		if(confirm("Esta seguro de que la matricula "+codigoPersonal+" es la del solicitante?")){
			document.forma.action = "admitir?Accion=1&codigoPersonal="+codigoPersonal+"&Folio="+folio;
			document.forma.submit();
		}
	}
	
	function nuevoIngreso(folio){
		document.forma.action = "admitir?Accion=2&Folio="+folio;
		document.forma.submit();
	}
</script>
</head>
<body>
<table class="goback">
	<tr>
		<td>
			<a href="proceso">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a>
		</td>
	</tr>
</table>
<%
	if(!respuesta.equals("")){
%>
<table style=align:center>
	<tr>
		<td>
			<%=respuesta %>
		</td>
	</tr>
</table>
<%
	}
%>
<table style=align:center;width:80%>
	<tr>
		<td class="titulo">Admitir a <%=solicitud.getPerNombre() %> <%=solicitud.getPerPaterno() %> <%=solicitud.getPerMaterno() %></td>
	</tr>
</table>
<br>
<table style=align:center;width:60% class="tabla">
	<tr class="th2">
		<td>1. Datos Acad&eacute;micos</td>
	</tr>
	<tr>
		<td>
			<form id="forma" name="forma" action="admitir" method="post">
			<table style=align:center>
				<tr>
					<td><b><spring:message code="aca.TipoAlumno"/></b></td>
					<td>
						<select id="tipoAlumno" name="tipoAlumno">
<%
	ArrayList<CatTipoAlumno> lisTipoAlumno = catTipoAlumnoU.getListAll(conEnoc, "ORDER BY TIPO_ID");
	for(int i = 0; i < lisTipoAlumno.size(); i++){
		catTipoAlumno = (CatTipoAlumno) lisTipoAlumno.get(i);
%>
							<option value="<%=catTipoAlumno.getTipoId() %>"><%=catTipoAlumno.getNombreTipo() %></option>
<%
	}
%>
						</select>
					</td>
				</tr>
				<tr>
					<td><b><spring:message code="aca.Modalidad"/></b></td>
					<td>
						<select id="modalidadId" name="modalidadId">
<%
	ArrayList<CatModalidad> lisModalidad = catModalidadU.getListAll(conEnoc, "ORDER BY MODALIDAD_ID");
	for(int i = 0; i < lisModalidad.size(); i++){
		catModalidad = (CatModalidad) lisModalidad.get(i);
%>
							<option value="<%=catModalidad.getModalidadId() %>"><%=catModalidad.getNombreModalidad() %></option>
<%
	}
%>
						</select>
					</td>
				</tr>
			</table>
			</form>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr class="th2">
		<td>2. Reingreso &oacute; Primer Ingreso</td>
	</tr>
	<tr>
		<td>
			<table style=align:center>
<%
	alumPersonal.setCodigoPersonal(solicitud.getMatricula());
	if(solicitud.getMatricula()!=null && solicitud.getMatricula().equals("")){
%>
				<tr>
					<td><b><spring:message code='aca.ElSolicitanteNoIngresoNingunaMatricula'/></b></td>
				</tr>
<%
	}else if(AlumUtil.existeReg(conEnoc, solicitud.getMatricula())){
		alumPersonal = AlumUtil.mapeaRegId(conEnoc, solicitud.getMatricula());
%>
				<tr>
					<td><b>La matricula que ingres&oacute; el solicitante coincide con la de un alumno</b></td>
				</tr>
				<tr>
					<td><a href="javascript:reingreso('<%=alumPersonal.getCodigoPersonal() %>', '<%=folio %>');"><%=alumPersonal.getCodigoPersonal() %> - <%=alumPersonal.getNombre() %> <%=alumPersonal.getApellidoPaterno() %> <%=alumPersonal.getApellidoMaterno() %></a></td>
				</tr>
<%
	}else{
%>
				<tr>
					<td><b>La matricula que ingres&oacute; el alumno [<%=solicitud.getMatricula() %>] no existe</b></td>
				</tr>
<%
	}
%>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<th><spring:message code="aca.AlumnosSimilares"/></th>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
<%
	if(lisSimilares != null)
		for(int i = 0; i < lisSimilares.size(); i++){
			alumPersonal = (AlumPersonal) lisSimilares.get(i);
%>
				<tr>
					<td><a href="javascript:reingreso('<%=alumPersonal.getCodigoPersonal() %>', '<%=folio %>');"><%=alumPersonal.getCodigoPersonal() %> - <%=alumPersonal.getNombre() %> <%=alumPersonal.getApellidoPaterno() %> <%=alumPersonal.getApellidoMaterno() %></a></td>
				</tr>
<%
		}
%>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td class="end"><spring:message code="aca.FinDelListado"/></td>
				</tr>
			</table>
			<br>
			<table style=align:center>
				<tr>
					<td>
						Si el solicitante ya era alumno elijalo de la lista de arriba.<br>
						Si no, elija &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:nuevoIngreso('<%=folio %>');"><b>Nuevo Alumno</b></a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>