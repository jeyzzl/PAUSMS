<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@page import="aca.carga.Carga"%>
<%@page import="aca.vista.CargaAcademica"%>
<%@page import="aca.hca.HcaActividad"%>
<%@page import="aca.hca.HcaMaestroActividad"%>
<%@page import="aca.hca.HcaRango"%>
<%@page import="aca.alumno.AlumPlan"%>
<%@page import="aca.plan.MapaCurso"%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.catalogo.CatModalidad"%>
<%@ page import = "java.text.*"%>

<jsp:useBean id="alumU" class="aca.alumno.AlumUtil" scope="page"/>
<jsp:useBean id="carga" class="aca.carga.Carga" scope="page"/>
<jsp:useBean id="cargaU" class="aca.carga.CargaUtil" scope="page"/>
<jsp:useBean id="cargaAcaU" class="aca.vista.CargaAcaUtil" scope="page"/>
<jsp:useBean id="cargaAcademica" class="aca.vista.CargaAcademica" scope="page"/>
<jsp:useBean id="alumnoCursoU" class="aca.vista.AlumnoCursoUtil" scope="page"/>
<jsp:useBean id="cargaGrupo" class="aca.carga.CargaGrupo" scope="page"/>
<jsp:useBean id="hcaMaestro" class="aca.hca.HcaMaestro" scope="page"/>
<jsp:useBean id="hcaMaestroU" class="aca.hca.HcaMaestroUtil" scope="page"/>
<jsp:useBean id="acceso" class="aca.acceso.Acceso" scope="page"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil" />
<jsp:useBean id="hcaMaestroActividad" class="aca.hca.HcaMaestroActividad" scope="page"/>
<jsp:useBean id="hcaMAU" class="aca.hca.HcaMAUtil" scope="page"/>
<jsp:useBean id="hcaActividad" class="aca.hca.HcaActividad" scope="page"/>
<jsp:useBean id="hcaActividadU" class="aca.hca.HcaActividadUtil" scope="page"/>
<jsp:useBean id="hcaTipo" class="aca.hca.HcaTipo" scope="page"/>
<jsp:useBean id="accesoU"  class="aca.acceso.AccesoUtil" scope="page"/>
<jsp:useBean id="fecha"  class="aca.util.Fecha" scope="page"/>
<%
	String codigoEmpleado 		= (String) session.getAttribute("codigoEmpleado");
	String maestroNombre		= (String) request.getAttribute("maestroNombre"); 	
	String year					= request.getParameter("year");
	String accion				= request.getParameter("Accion");
	
	float totalSemestral		= 0F;
	float totalSemestralFinal	= 0F;
	float totalSemanalFinal		= 0F;
	float totalSemanas			= 0F;
	
	DecimalFormat getformato	= new DecimalFormat("#####0.00;(#####0.00)");
	
	if(year == null)
		year = fecha.getYear(Fecha.getHoy());
	
	if(accion == null)
		accion = "0";

	hcaMaestro = hcaMaestroU.mapeaRegId(conEnoc, codigoEmpleado);	
	acceso = AccesoU.mapeaRegId(conEnoc, (String) session.getAttribute("codigoPersonal"));
	if((acceso.getAccesos().compareTo(hcaMaestro.getCarreraId()) != -1 && !hcaMaestro.getCarreraId().equals("")) || Boolean.parseBoolean(session.getAttribute("admin")+"") || aca.acceso.AccesoUtil.esSupervisor(conEnoc, (String) session.getAttribute("codigoPersonal"))){
%>
<div class="container">
	<h2>Lecturer's Load</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="cursos"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table class="goback"><tr><td></td></tr></table>
	<br>
	<form id="forma1" name="forma1" action="carga_docente" method="post">
		<table style="margin: 0 auto;">
			<tr>
				<th>Lecturer: [ <%=codigoEmpleado %> ] - [ <%=alumU.getNombre(conEnoc, codigoEmpleado,"NOMBRE") %> ]</th>
			</tr>
			<tr>
				<td align="center">
					<select id="year" name="year" onchange="document.forma1.submit();">
<%
			for(int i = 2007; i <= Integer.parseInt(fecha.getYear(Fecha.getHoy())); i++){
%>
						<option value="<%=i %>"<%=year.equals(String.valueOf(i))?" Selected":"" %>><%=i %> - <%=i+1 %></option>
<%
			}
%>
					</select>
				</td>
			</tr>
		</table>
<%
		ArrayList lisCarga = cargaU.getListMaestroPeriodo(conEnoc, codigoEmpleado, year);
		for(int j = 0; j < lisCarga.size(); j++){
			carga = (Carga) lisCarga.get(j);
			ArrayList lisCursos = cargaAcaU.getListaMaestro(conEnoc, carga.getCargaId(), codigoEmpleado, "ORDER BY NOMBRE_CURSO");
			
			totalSemanas = 0F;
			totalSemestral = 0F;
%>
		<table style="margin: 0 auto;  width:100%">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<th><%=carga.getNombreCarga() %></th>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
		<table style="margin: 0 auto;  width:100%">
			<tr><th colspan="10" style="background: white;">Subjects</th></tr>
			<tr bgcolor="#000090">
				<td align="center" width="35%"><font color='#ffffff'><b><spring:message code="aca.Nombre"/></b></font></td>
				<td align="center" width="15%"><font color='#ffffff'><b>Level</b></font></td>
				<td align="center" width="7%"><font color='#ffffff'><b><spring:message code="aca.Modalidad"/></b></font></td>
				<td align="center" width="7%"><font color='#ffffff'><b># Students</b></font></td>
				<td align="center" width="5%"><font color='#ffffff'><b>Weight</b></font></td>
				<td align="center" width="5%"class="ayuda <%=idJsp %> 001"><font color='#ffffff'><b>FS</b></font></td>
				<td align="center" width="7%"><font color='#ffffff'><b>Weeks</b></font></td>
				<td align="center" width="8%"><font color='#ffffff'><b>Weekly hours</b></font></td>
				<td align="center" width="8%"><font color='#ffffff'><b>Semestral hours</b></font></td>
			</tr>
<%
			int contador = 0;
			for(int i = 0; i < lisCursos.size(); i++){
				cargaAcademica = (CargaAcademica) lisCursos.get(i);
				String valor = aca.hca.HcaRangoUtil.getValor(conEnoc, aca.alumno.PlanUtil.getCarreraNivel(conEnoc, cargaAcademica.getCarreraId()), cargaAcademica.getModalidadId(), cargaAcademica.getNumAlum());
				int fs = Integer.parseInt(aca.plan.CursoUtil.getFS(conEnoc, cargaAcademica.getCursoId()));
				if(cargaAcademica.getValeucas().equals("S")){
					totalSemestral += ((Float.parseFloat(valor) * fs) * Float.parseFloat(cargaAcademica.getSemanas()));
					if((Float.parseFloat(valor) * fs) > 0){
						totalSemanas += Integer.parseInt(cargaAcademica.getSemanas());
						contador++;
					}
%>
			<tr>
				<td><%=cargaAcademica.getNombreCurso()%></td>        
				<td><%=aca.catalogo.CatCarreraUtil.getNombreCorto(conEnoc,cargaAcademica.getCarreraId())%></td>
				<td align="center"><%=aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, cargaAcademica.getModalidadId()) %></td>
				<td align="center">
					<%=cargaAcademica.getNumAlum() %>
				</td>
				<td align="center"><%=valor %></td>
				<td align="center"><%=fs %></td>
				<td align="center"><%=cargaAcademica.getSemanas() %></td>
				<td align="center"><%=cargaAcademica.getValeucas().equals("S")?(Float.parseFloat(valor) * fs):"-" %></td>
				<td align="center"><%=cargaAcademica.getValeucas().equals("S")?((Float.parseFloat(valor) * fs) * Float.parseFloat(cargaAcademica.getSemanas())):"-" %></td>
			</tr>
<%
				}
			}
%>
			<tr>
				<td colspan="7" align="right"><b><spring:message code="aca.Total"/></b></td>
				<td align="center"><b><%=getformato.format(totalSemestral/(totalSemanas/contador)) %></b></td>
				<td align="center"><b><%=getformato.format(totalSemestral) %></b></td>
			</tr>
<%
			totalSemestralFinal += totalSemestral;
			totalSemanalFinal	+= totalSemestral/(totalSemanas/contador);
			totalSemanas = 0F;
			totalSemestral = 0F;
%>
		</table>
	</form>
	<table style="width:100%">
		<tr>
			<th style="background: white;">Lecturer's Activities</th>
		</tr>
	</table>
	<form id="forma3" name="forma3" action="docente" method="post">
		<table style="margin: 0 auto;  width:100%">
			<tr>
				<td align="center">
					<table>
<%
			ArrayList lisMA = hcaMAU.getListMaestroCarga(conEnoc, codigoEmpleado, carga.getCargaId(), "ORDER BY HCA_ACTORDEN(ACTIVIDAD_ID)");
			String tipoId = "";
			for(int i = 0; i < lisMA.size(); i++){
				hcaMaestroActividad = (HcaMaestroActividad) lisMA.get(i);
				hcaActividad.mapeaRegId(conEnoc, hcaMaestroActividad.getActividadId());
				if(!tipoId.equals(hcaActividad.getTipoId())){
					tipoId = hcaActividad.getTipoId();
					hcaTipo.mapeaRegId(conEnoc, tipoId);
					if(i != 0){
%>
						<tr>
							<td colspan="2" align="right"><b>Weekly Total</b></td>
							<td align="center"><b><%=getformato.format(totalSemestral/(totalSemanas/contador)) %></b></td>
							<td align="right"><b><spring:message code="aca.Total"/></b></td>
							<td align="center"><b><%=getformato.format(totalSemestral) %></b></td>
						</tr>
<%
						totalSemestralFinal += totalSemestral;
						totalSemanalFinal += totalSemestral/(totalSemanas/contador);
						totalSemestral = 0F;
						totalSemanas = 0F;
					}
					contador = 0;
%>
						<tr>
							<td colspan="5">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="5"><font><b><%=hcaTipo.getTipoNombre() %></b></font></td>
						</tr>
						<tr bgcolor="#000090">
							<td width="10%">&nbsp;</td>
							<td align="center" width="60%"><font color='#ffffff'><b><spring:message code="aca.Nombre"/></b></font></td>
							<td align="center" width="10%"><font color='#ffffff'><b>Weight</b></font></td>
							<td align="center" width="10%"><font color='#ffffff'><b>Weeks</b></font></td>
							<td align="center" width="10%"><font color='#ffffff'><b>Tot. Hrs.</b></font></td>
						</tr>
<%
				}
				contador++;
%>
						<tr>
							<td align="center">
								<img src="../../imagenes/editar.gif" title="Edit" onclick="modificaActividad('<%=hcaActividad.getActividadId() %>');" style="cursor: pointer;" />
								<img src="../../imagenes/no.png" title="Delete" onclick="eliminaActividad('<%=hcaActividad.getActividadId() %>');" style="cursor: pointer;" />
							</td>
							<td><%=hcaActividad.getActividadNombre() %></td>
							<td align="center"><%=hcaActividad.getValor() %></td>
							<td align="center"><%=hcaMaestroActividad.getSemanas() %></td>
							<td align="center"><%=Float.parseFloat(hcaActividad.getValor()) * Float.parseFloat(hcaMaestroActividad.getSemanas()) %></td>
						</tr>
<%
				totalSemestral += Float.parseFloat(hcaActividad.getValor()) * Float.parseFloat(hcaMaestroActividad.getSemanas());
				totalSemanas += Float.parseFloat(hcaMaestroActividad.getSemanas());
			}
%>
						<tr>
							<td colspan="2" align="right"><b>Weekly Total</b></td>
							<td align="center"><b><%=getformato.format(totalSemestral/(totalSemanas/contador)) %></b></td>
							<td align="right"><b><spring:message code="aca.Total"/></b></td>
							<td align="center"><b><%=getformato.format(totalSemestral) %></b></td>
						</tr>
<%
			totalSemestralFinal += totalSemestral;
			totalSemanalFinal += totalSemestral/(totalSemanas/contador);
			//System.out.println(totalSemestral+"/("+totalSemanas+"/"+contador+")");
%>
						<tr>
							<td colspan="5">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="4" align="right"><b>Weekly Total</b></td>
							<td align="center"><b><%=getformato.format(totalSemanalFinal) %></b></td>
						</tr>
						<tr>
							<td colspan="4" align="right"><b>Semestral Total</b></td>
							<td align="center"><b><%=getformato.format(totalSemestralFinal) %></b></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
<%
		}
	}else{
%>
	<table style="margin: 0 auto;">
		<tr>
			<td align="center">
				<a href="javascript:buscar('buscar')"><b>[ Buscar Empleado ]</b></a>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center"><b>No tiene acceso para ver al empleado <%=codigoEmpleado %></b></td>
		</tr>
	</table>
</div>
<%
	}
%>
<%@ include file= "../../cierra_enoc.jsp" %>