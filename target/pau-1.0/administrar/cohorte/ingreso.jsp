<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.alumno.AlumPlan"%>
<%@page import="aca.plan.MapaPlan"%>
<%@page import="aca.alumno.AlumPersonal"%>
<%@page import="aca.kardex.KrdxCursoAct"%>
<%@page import="aca.kardex.KrdxCursoImp"%>
<%@page import="java.text.*" %>
<%@page import="aca.alumno.AlumAcademico"%>

<jsp:useBean id="PlanU" scope="page" class="aca.plan.PlanUtil"/>
<jsp:useBean id="alumPlanU" scope="page" class="aca.alumno.PlanUtil"/>
<jsp:useBean id="catCarreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="catFacultad" scope="page" class="aca.catalogo.CatFacultad"/>
<jsp:useBean id="catFacultadUtil" scope="page" class="aca.catalogo.CatFacultadUtil"/>
<%
	String yearIni 		= request.getParameter("year")==null?"1950":request.getParameter("year");
	String yearFin 		= String.valueOf(Integer.parseInt(yearIni)+1);
	String periodo 		= "";
	String carreras		= "X";
	String carrera		= "";
	
	java.util.TreeMap<String,aca.plan.MapaPlan> treePlan	= PlanU.getTreePlan(conEnoc,"ORDER BY PLAN_ID");
%>
<div class="container-fluid">
	<h1>Admission Cohort</h1>
	<form id="forma" name="forma" action="ingreso" method="post">
	<div class="alert alert-info d-flex align-items-center">
		Select the year the cohort began: 1/Agosto/
		<input type="text" class="text" id="year" name="year" value="<%=yearIni%>" maxlength="4" size="4" /><b>[AAAA]</b>
		<input type="submit" class="btn btn-primary" value="Show" />
	</div>
		<table style="width:60%">
			<tr></tr>
<%
	if(!yearIni.equals("1950")){
%>
			<tr>
				<td><h3>Admission cohort of school year <%=yearIni %> - <%=yearFin %></h3></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<table style="width:100%">
<%
		ArrayList<aca.alumno.AlumPlan> lisAlumnoPlan = alumPlanU.getListYear(conEnoc, yearIni, "ORDER BY ENOC.ALUMNO_MODALIDAD(CODIGO_PERSONAL), ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");

		// Busca las carreras representadas en la lista de ingreso de los alumnos
		for (int i=0; i< lisAlumnoPlan.size();i++){
			aca.alumno.AlumPlan plan = (aca.alumno.AlumPlan) lisAlumnoPlan.get(i);
			if (treePlan.containsKey(plan.getPlanId())){
				aca.plan.MapaPlan mapaPlan = (aca.plan.MapaPlan) treePlan.get(plan.getPlanId());
				if (carreras.indexOf(mapaPlan.getCarreraId())==-1){
					if (carreras.equals("X")){
						carreras = "'"+mapaPlan.getCarreraId()+"'";
					}else{
						carreras += ",'"+mapaPlan.getCarreraId()+"'";
					}					
				}
			}			
		}
		//System.out.println(carreras);
		
		ArrayList<aca.catalogo.CatCarrera> lisCarrera = catCarreraU.getListAll(conEnoc, "WHERE CARRERA_ID IN ("+carreras+") AND NIVEL_ID BETWEEN 1 AND 4 ORDER BY ENOC.FACULTAD_NOMBRE(FACULTAD_ID), ENOC.NOMBRE_CARRERA(CARRERA_ID)");
		String facultad = "";
		int contador = 0;
		for(aca.catalogo.CatCarrera catCarrera : lisCarrera){
			if(!facultad.equals(catCarrera.getFacultadId())){
				facultad = catCarrera.getFacultadId();
				catFacultad = catFacultadUtil.mapeaRegId(conEnoc, catCarrera.getFacultadId());
%>
					</table>
					<table class="table table-sm">
						<tr>
							<td colspan="9"><font size="3"><b><%=catFacultad.getTitulo() %> <%=catCarrera.getFacultadId().equals("107")?"":"de " %><%=catFacultad.getNombreFacultad() %></b></font></td>
						</tr>
<%
			}
			contador = 0;
%>
						<tr>
							<td colspan="9">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="9"><b><%=catCarrera.getNombreCarrera() %></b></td>
						</tr>
						<tr>
							<th><spring:message code="aca.Numero"/></th>
							<th><spring:message code="aca.Matricula"/></th>
							<th><spring:message code="aca.Nombre"/></th>
							<th><spring:message code="aca.Plan"/></th>
							<th><spring:message code="aca.Modalidad"/></th>
							<th><%=yearIni %> - <%=Integer.parseInt(yearIni)+1 %></th>
							<th><%=Integer.parseInt(yearIni)+1 %> - <%=Integer.parseInt(yearIni)+2 %></th>
							<th><%=Integer.parseInt(yearIni)+2 %> - <%=Integer.parseInt(yearIni)+3 %></th>
							<th><%=Integer.parseInt(yearIni)+3 %> - <%=Integer.parseInt(yearIni)+4 %></th>
							<th><%=Integer.parseInt(yearIni)+4 %> - <%=Integer.parseInt(yearIni)+5 %></th>
							<th><%=Integer.parseInt(yearIni)+5 %> - <%=Integer.parseInt(yearIni)+6 %></th>
							<th><%=Integer.parseInt(yearIni)+6 %> - <%=Integer.parseInt(yearIni)+7 %></th>
							<th><spring:message code="aca.Avance"/></th>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>
								<table style="width:100%">
									<tr>
										<th width="25%">1</th>
										<th width="25%">2</th>
										<th width="25%">3</th>
										<th width="25%">4</th>
									</tr>
								</table>
							</td>
							<td>
								<table style="width:100%">
									<tr>
										<th width="25%">1</th>
										<th width="25%">2</th>
										<th width="25%">3</th>
										<th width="25%">4</th>
									</tr>
								</table>
							</td>
							<td>
								<table style="width:100%">
									<tr>
										<th width="25%">1</th>
										<th width="25%">2</th>
										<th width="25%">3</th>
										<th width="25%">4</th>
									</tr>
								</table>
							</td>
							<td>
								<table style="width:100%">
									<tr>
										<th width="25%">1</th>
										<th width="25%">2</th>
										<th width="25%">3</th>
										<th width="25%">4</th>
									</tr>
								</table>
							</td>
							<td>
								<table style="width:100%">
									<tr>
										<th width="25%">1</th>
										<th width="25%">2</th>
										<th width="25%">3</th>
										<th width="25%">4</th>
									</tr>
								</table>
							</td>
							<td>
								<table style="width:100%">
									<tr>
										<th width="25%">1</th>
										<th width="25%">2</th>
										<th width="25%">3</th>
										<th width="25%">4</th>
									</tr>
								</table>
							</td>
							<td>
								<table style="width:100%">
									<tr>
										<th width="25%">1</th>
										<th width="25%">2</th>
										<th width="25%">3</th>
										<th width="25%">4</th>
									</tr>
								</table>
							</td>
							<td>&nbsp;</td>
						</tr>
<%			
			for(aca.alumno.AlumPlan alumPlan : lisAlumnoPlan){				
				carrera = "";
				if (treePlan.containsKey(alumPlan.getPlanId())){
					aca.plan.MapaPlan mapaPlan = (aca.plan.MapaPlan) treePlan.get(alumPlan.getPlanId());
					carrera = mapaPlan.getCarreraId();
				}	
				if(carrera.equals(catCarrera.getCarreraId())){
					contador++;
					
					periodo = yearIni.substring(2,4)+String.valueOf(Integer.parseInt(yearFin)).substring(2,4);
%>
						<tr>
							<td><%=contador %></td>
							<td><%=alumPlan.getCodigoPersonal() %></td>
							<td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, alumPlan.getCodigoPersonal(), "APELLIDO") %></td>
							<td align="center"><%=alumPlan.getPlanId() %></td>
							<td><%=aca.alumno.AcademicoUtil.getModalidad(conEnoc, alumPlan.getCodigoPersonal()) %></td>
							<td align="center">
<%				
					if(KrdxCursoImp.tieneImportados(conEnoc, alumPlan.getCodigoPersonal(), yearIni, alumPlan.getPlanId())){
%>
								<table><tr><td>
									<font color="green"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), yearIni, "1','2','3','4", alumPlan.getPlanId(),periodo) %>&nbsp;</font>
								</td></tr></table>
<%
					}else{
%>
								<table style="width:100%">
								
									<tr>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), yearIni, "1", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), yearIni, "2", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), yearIni, "3", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), yearIni, "4", alumPlan.getPlanId(),periodo) %></td>
									</tr>
								</table>
<%
					}					
%>
							</td>
							<td align="center">
<%					
					// Aumenta al siguiente ciclo escolar
					periodo = aca.catalogo.CatPeriodoUtil.getSigPeriodo(periodo);

					if(KrdxCursoImp.tieneImportados(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+1), alumPlan.getPlanId())){
%>
								<table><tr><td>
									<font color="green"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+1), "1','2','3','4", alumPlan.getPlanId(),periodo) %>&nbsp;</font>
								</td></tr></table>
<%
					}else{
%>
								<table style="width:100%">
									<tr>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+1), "1", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+1), "2", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+1), "3", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+1), "4", alumPlan.getPlanId(),periodo) %></td>
									</tr>
								</table>
<%
					}
%>
							</td>
							<td align="center">
<%
					//Aumenta al siguiente ciclo escolar
					periodo = aca.catalogo.CatPeriodoUtil.getSigPeriodo(periodo);					
					
					if(KrdxCursoImp.tieneImportados(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+2), alumPlan.getPlanId())){
%>
								<table><tr><td>
									<font color="green"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+2), "1','2','3','4", alumPlan.getPlanId(),periodo) %>&nbsp;</font>
								</td></tr></table>
<%
					}else{
%>
								<table style="width:100%">
									<tr>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+2), "1", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+2), "2", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+2), "3", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+2), "4", alumPlan.getPlanId(),periodo) %></td>
									</tr>
								</table>
<%
					}
%>
							</td>
							<td align="center">
<%
					//Aumenta al siguiente ciclo escolar
					periodo = aca.catalogo.CatPeriodoUtil.getSigPeriodo(periodo);

					if(KrdxCursoImp.tieneImportados(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+3), alumPlan.getPlanId())){
%>
								<table><tr><td>
									<font color="green"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+3), "1','2','3','4", alumPlan.getPlanId(),periodo) %>&nbsp;</font>
								</td></tr></table>
<%
					}else{
%>
								<table style="width:100%">
									<tr>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+3), "1", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+3), "2", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+3), "3", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+3), "4", alumPlan.getPlanId(),periodo) %></td>
									</tr>
								</table>
<%
					}
%>
							</td>
							<td align="center">
<%
					//Aumenta al siguiente ciclo escolar
					periodo = aca.catalogo.CatPeriodoUtil.getSigPeriodo(periodo);				
					
					if(KrdxCursoImp.tieneImportados(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+4), alumPlan.getPlanId())){
%>
								<table><tr><td>
									<font color="green"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+4), "1','2','3','4", alumPlan.getPlanId(),periodo) %>&nbsp;</font>
								</td></tr></table>
<%
					}else{
%>
								<table style="width:100%">
									<tr>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+4), "1", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+4), "2", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+4), "3", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+4), "4", alumPlan.getPlanId(),periodo) %></td>
									</tr>
								</table>
<%
					}
%>
							</td>
							<td align="center">
<%
					//Aumenta al siguiente ciclo escolar
					periodo = aca.catalogo.CatPeriodoUtil.getSigPeriodo(periodo);

					if(KrdxCursoImp.tieneImportados(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+5), alumPlan.getPlanId())){
%>
								<table><tr><td>
									<font color="green"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+5), "1','2','3','4", alumPlan.getPlanId(),periodo) %>&nbsp;</font>
								</td></tr></table>
<%
					}else{
%>
								<table style="width:100%">
									<tr>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+5), "1", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+5), "2", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+5), "3", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+5), "4", alumPlan.getPlanId(),periodo) %></td>
									</tr>
								</table>
<%
					}
%>
							</td>
							<td align="center">
<%
					//Aumenta al siguiente ciclo escolar
					periodo = aca.catalogo.CatPeriodoUtil.getSigPeriodo(periodo);
					
					if(KrdxCursoImp.tieneImportados(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+6), alumPlan.getPlanId())){
%>
								<table><tr><td>
									<font color="green"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+6), "1','2','3','4", alumPlan.getPlanId(),periodo) %>&nbsp;</font>
								</td></tr></table>
							
<%
					}else{
%>
								<table style="width:100%">
									<tr>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+6), "1", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+6), "2", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+6), "3", alumPlan.getPlanId(),periodo) %></td>
										<td width="25%"><%=KrdxCursoAct.alumCredCarga(conEnoc, alumPlan.getCodigoPersonal(), String.valueOf(Integer.parseInt(yearIni)+6), "4", alumPlan.getPlanId(),periodo) %></td>
									</tr>
								</table>
<%
					}
%>
							</td>
<%
						float crdPlan 		= aca.plan.CreditoUtil.creditosPlan(conEnoc, alumPlan.getPlanId());
						float crdAlumno 	= aca.vista.AlumnoCursoUtil.creditosAprobados(conEnoc, alumPlan.getCodigoPersonal(), alumPlan.getPlanId());
						double porcentaje 	= (double) crdAlumno / crdPlan * 100;
						java.util.Locale.setDefault(java.util.Locale.US);
						DecimalFormat getformato= new DecimalFormat("###,##0.00;-###,##0.00");	
%>
							<td align="center"><%=getformato.format(porcentaje) %> %</td>
						</tr>
<%
				}
			}
		}
%>
					</table>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td align="right"><b>Total: </b><%=lisAlumnoPlan.size() %></td>
			</tr>
<%
	}
%>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="end"></td>
			</tr>
		</table>
	</form>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>