<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CancelaEstudio" scope="page" class="aca.alumno.CancelaEstudio" />
<jsp:useBean id="CancelaEstudioUtil" scope="page" class="aca.alumno.CancelaEstudioUtil" />
<jsp:useBean id="AlumPersonal" scope="page" class="aca.alumno.AlumPersonal" />
<jsp:useBean id="AlumPlan" scope="page" class="aca.alumno.AlumPlan" />
<jsp:useBean id="AlumPlanU" scope="page" class="aca.alumno.PlanUtil" />
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil" />

<html>
	<head>
		<script>
			function guardar(){
				if(form.PlanId.value!=""){
					if(form.Comentario.value!=""){
						form.Accion.value = "1";
						form.submit();
					}
					else{
						alert("Escriba un comentario");
					}
				}
				else{
					alert("El alumno no tiene plan de estudio");
				}
			}
		</script>
	</head>
<%
	String codigoAlumno 	= session.getAttribute("codigoAlumno").toString();
	boolean admin 			= Boolean.parseBoolean(session.getAttribute("admin").toString());

	if(admin){
		String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");
		
		AlumPersonal = AlumUtil.mapeaRegId(conEnoc, codigoAlumno);
		AlumPlan = AlumPlanU.mapeaRegId(conEnoc, codigoAlumno);
		
		ArrayList<String> listaPlanes = AlumPlanU.getPlanesAlumno(conEnoc, codigoAlumno);

		if(accion.equals("1")){
			CancelaEstudio.setCodigoPersonal(codigoAlumno);
			CancelaEstudio.setPlanId(request.getParameter("PlanId"));
			CancelaEstudio.setUsuario(session.getAttribute("codigoPersonal").toString());
			CancelaEstudio.setComentario(request.getParameter("Comentario"));
			CancelaEstudio.setEstado(request.getParameter("Estado"));
			if(!CancelaEstudioUtil.existeReg(conEnoc, codigoAlumno, request.getParameter("PlanId"))) 
				CancelaEstudioUtil.insertReg(conEnoc, CancelaEstudio);
			else 
				CancelaEstudioUtil.updateReg(conEnoc, CancelaEstudio);
		}
		else if(accion.equals("2")){
			CancelaEstudio = CancelaEstudioUtil.mapeaRegId(conEnoc, codigoAlumno, request.getParameter("PlanId"));
		}
		else if(accion.equals("3")){			
			CancelaEstudioUtil.deleteReg(conEnoc, codigoAlumno, request.getParameter("PlanId"));
		}
		
		ArrayList<aca.alumno.CancelaEstudio> listaCancelaEstudios = CancelaEstudioUtil.getListAll(conEnoc, "WHERE CODIGO_PERSONAL='"+codigoAlumno+"'");
%>
		<body>
		<div class="container-fluid">
		<h2>Cancela estudios</h2>
		<div class="alert alert-info d-flex align-items-center">
			[<%=codigoAlumno%>]
			[<%=AlumPersonal.getNombre()%> <%=AlumPersonal.getApellidoPaterno()%> <%=AlumPersonal.getApellidoMaterno()==null?"":AlumPersonal.getApellidoMaterno()%>]
			[<%=AlumPlan.getPlanId()%>] [<%=AlumUtil.getCarrera(conEnoc, codigoAlumno)%>]
		</div>
			<form name="form" action="cancelaAlumno" method="post">
				<input name="Accion" type="hidden">
				<table class="table table-sm" style="width:50%">
					<tr>
						<td><h3><spring:message code="aca.Plan"/>:</h3></td>
						<td>
						<%	if(CancelaEstudio.getPlanId().equals("")){%>
								<select name="PlanId" class="form-select">
								<%	for(String plan : listaPlanes){
										boolean tieneCancelado = false;
										for(aca.alumno.CancelaEstudio cancelaEstudio : listaCancelaEstudios){
											if(cancelaEstudio.getPlanId().equals(plan)){
												tieneCancelado = true;
												break;
											}
										}
										if(!tieneCancelado){%>
											<option value="<%=plan%>"><%=plan%></option>
								<%		}
									}%>
								</select>
						<%	}
							else{%>
								<input name="PlanId" type="hidden" class="form-control" value="<%=CancelaEstudio.getPlanId()%>">
								<%=CancelaEstudio.getPlanId()%>
						<%	}%>
						</td>
					</tr>
					<tr>
						<td><h3><spring:message code="aca.Comentario"/>:</h3></td>
						<td><textarea id="Comentario" name="Comentario" class="form-control" ><%=CancelaEstudio.getComentario()%></textarea></td>
					</tr>
					<tr>
						<td><h3><spring:message code="aca.Estado"/>:</h3></td>
						<td>
							<select name="Estado" class="form-select">
								<option value="A" <%=CancelaEstudio.getEstado().equals("A")?"Selected":""%>><spring:message code='aca.Activo'/></option>
								<option value="P" <%=CancelaEstudio.getEstado().equals("P")?"Selected":""%>><spring:message code='aca.Pendiente'/></option>
								<option value="I" <%=CancelaEstudio.getEstado().equals("I")?"Selected":""%>><spring:message code='aca.Inactivo'/></option>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<%	if(!CancelaEstudio.getPlanId().equals("")){%>
								<input class="btn btn-danger" type="button" onclick="document.location.href='cancelaAlumno';" value="Nuevo">
						<%	}%>
							<input class="btn btn-primary" type="button" onclick="guardar();" value="Guardar">
						</td>
					</tr>
				</table>
			</form>
			<table class="table table-sm" style="width: 50%">
				<tr>
					<th>Opción</th>
					<th><spring:message code="aca.Plan"/></th>
					<th><spring:message code="aca.Comentario"/></th>
					<th><spring:message code="aca.Fecha"/></th>
					<th><spring:message code="aca.Estado"/></th>
				</tr>
				<%	for(aca.alumno.CancelaEstudio cancelaEstudio : listaCancelaEstudios){
						String estado = "<font color='green'><b>Activo</b></font>";
						if(cancelaEstudio.getEstado().equals("P")) estado = "<font color='#777'><b>Pendiente</b></font>";
						else if(cancelaEstudio.getEstado().equals("I")) estado = "<font color='#AE2113'><b>Inactivo</b></font>";%>
						<tr>
							<td style="text-align:center;">
								<a href="javascript:document.location.href='cancelaAlumno?Accion=2&PlanId=<%=cancelaEstudio.getPlanId()%>';"><i class="fas fa-pencil-alt"></i></a>
							<%	if(session.getAttribute("usuario").toString().equals("etorres")){%>
									&nbsp;
									<i class="fas fa-trash-alt" style="cursor:pointer;" onclick="if(confirm('¿Desea eliminar el registro?')){document.location.href='cancelaAlumno?Accion=3&PlanId=<%=cancelaEstudio.getPlanId()%>';}"></i>
							<%	}%>
							</td>
							<td><%=cancelaEstudio.getPlanId()%></td>
							<td><%=cancelaEstudio.getComentario()%></td>
							<td><%=cancelaEstudio.getFecha()%></td>
							<td><%=estado%></td>
						</tr>
				<%	}%>
			</table>
		  </div>
		</body>
<%	}
	else{%>
		<table>
			<tr>
				<td class="titulo2">Solo un administrador tiene permiso para
				accesar a esta p&aacute;gina</td>
			</tr>
		</table>
<%	}%>
</html>

<%@ include file="../../cierra_enoc.jsp"%>