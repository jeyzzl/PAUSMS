<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumEstadoUtil"  class="aca.alumno.EstadoUtil" scope="page"/>
<jsp:useBean id="alumCurso"  class="aca.vista.AlumnoCurso" scope="page"/>
<jsp:useBean id="planCicloU"  class="aca.vista.PlanCicloUtil" scope="page"/>
<jsp:useBean id="Carga" scope="page" class="aca.carga.Carga"/>
<jsp:useBean id="CatPeriodoUtil" scope="page" class="aca.catalogo.CatPeriodoUtil"/>

<script>

	function grabaPeriodo(periodoId){
		document.location.href="actualizar?cambioPeriodo=1&PeriodoId="+periodoId;
	}
	
	function grabaCarga(){
  		document.location.href="actualizar?accion=nuevaCarga&CargaId="+document.forma.carga.value+"&cambioCarga=S";
	}
	
</script>
<%	
	ArrayList<aca.catalogo.CatPeriodo> listaPeriodos = CatPeriodoUtil.getListAll(conEnoc, "ORDER BY PERIODO_ID");
	
	if(request.getParameter("cambioPeriodo")!=null && !request.getParameter("cambioPeriodo").equals("")){
		session.setAttribute("periodo", request.getParameter("PeriodoId"));
	}
	
	String periodoId = (String)session.getAttribute("periodo");
	
	ArrayList<aca.carga.Carga> lisCarga = new aca.carga.CargaUtil().getListAll(conEnoc,"WHERE PERIODO IN ('"+periodoId+"') AND ESTADO = '1' ORDER BY CARGA_ID");
	
	if(request.getParameter("cambioPeriodo")!=null&&!request.getParameter("cambioPeriodo").equals("")&&!lisCarga.isEmpty()){
		session.setAttribute("cargaId", lisCarga.get(0).getCargaId());		
	}
	else if(request.getParameter("cambioCarga")!=null&&!request.getParameter("cambioCarga").equals("")){
		session.setAttribute("cargaId", request.getParameter("CargaId"));		
	}
	String cargaId = (String)session.getAttribute("cargaId");	
	if(lisCarga.isEmpty()){
		session.setAttribute("cargaId", "");
	}
	
	/* LISTADO DE ALUMNOS INSCRITOS EN LA CARGA ACADEMICA */
	ArrayList<aca.alumno.AlumEstado> alumnos = alumEstadoUtil.getLista(conEnoc, cargaId, " AND ESTADO = 'I' "); 
	
	/*  BUSCA LA FECHA DE INICIO DE LA CARGA_ACADEMICA */ 
	String fInicio = aca.carga.CargaUtil.getFInicio(conEnoc, cargaId);	
	String accion = request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
%>

<style>
	body{
		background:white;
	}
</style>
<div class="container-fluid">
<h1>Actualizar Semestre Histórico</h1>
<form name="forma">
<div class="alert alert-info">	
	<font size="2"><b>Elegir periodo: </b>
		<select onchange="javascript:grabaPeriodo(this.value);" name="PeriodoId" class="input input-medium">
		<%	for(int j=0; j<listaPeriodos.size(); j++){
			aca.catalogo.CatPeriodo periodo = listaPeriodos.get(j);%>
			<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
						<%	}%>
		</select>
	</font>&nbsp;&nbsp;&nbsp;&nbsp;<b>Elegir carga:
		<select onchange='javascript:grabaCarga()' name="carga" style="width:350px;" class="input input-xlarge">
			<%	for(int i=0; i<lisCarga.size(); i++){
					Carga = lisCarga.get(i);%>
					<option <%if(cargaId.equals(Carga.getCargaId()))out.print(" Selected ");%> value="<%=Carga.getCargaId()%>">[<%=Carga.getCargaId() %>] <%=Carga.getNombreCarga()%></option>
						<%	}%>
		</select>
			<a href="actualizar?Accion=2" class="btn btn-info" align="center"><i class="icon-white icon-arrow-up"></i> Actualizar</a>
</div>
	<table style="width:60%">
		<tr>
			<td align="center"  colspan="7">
			</td>
		</tr>
	</table>
<%
	if(accion.equals("2")){
		//System.out.println("ENTRA 1");
		for(aca.alumno.AlumEstado alumno: alumnos){
			int contador = 0;
			//System.out.println("ENTRA 2");	
			/* CARGAS NO VIGENTES PARA LA FECHA DE INICIO */
			ArrayList<String> cargasNoVigentes = alumEstadoUtil.getListCargasNoVigentes(conEnoc, alumno.getCodigoPersonal(), fInicio, "");
			String strCargasNoVigentes = "";
			for(String carga: cargasNoVigentes){
				strCargasNoVigentes += "'" + carga + "',";
			}
			if(strCargasNoVigentes.length() > 0){
				strCargasNoVigentes = strCargasNoVigentes.substring(0, strCargasNoVigentes.length()-1);
			}
			
			/* OBTIENE EL PLAN DE ESTUDIOS QUE TENIA EL ALUMNO EN LA CARGA */
			String planId =  aca.vista.Estadistica.getPlanId(conEnoc, cargaId, alumno.getCodigoPersonal());
			
			/* SUMA EL NUMERO DE CREDITOS PASADOS HASTA LA CARGA CONSIDERADA */
			String sql = "";
			if(strCargasNoVigentes.length() > 0){
				sql = "AND CARGA_ID NOT IN ("+strCargasNoVigentes+")";
			}
			String creditos 	= aca.vista.AlumnoCursoUtil.getNumCreditos(conEnoc, alumno.getCodigoPersonal(), planId, sql);
			Double nCreditos 	= Double.parseDouble(creditos);
			
			/* OBTIENE EL NUMERO DE CREDITOS REQUERIDOS EN CADA CICLO DEL PLAN */
			ArrayList<aca.vista.PlanCiclo> planCiclos = planCicloU.getListCiclosPlan(conEnoc, planId, " ORDER BY CICLO ");
			String semestre = "1";
			double sumCreditosCiclo = 0;
			for(aca.vista.PlanCiclo planCiclo: planCiclos){
				double creditosCiclo = Double.parseDouble( planCiclo.getCreditos() );
				
				sumCreditosCiclo += creditosCiclo;
				
				if( (nCreditos - sumCreditosCiclo) >= 0){
					//Aun alcanza	
				}else{
					//Ya no alcanzo :/
					break;
				}
				
				semestre = planCiclo.getCiclo();
			}
			
			String grado = "0";
			String facultad = aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, aca.plan.PlanUtil.getCarreraId(conEnoc, planId));
			if (facultad.equals("107")){
				if (semestre.equals("1")||semestre.equals("2")||semestre.equals("3"))
					grado = "1";
				else
					grado = "2";				
			}else{
				if (semestre.equals("1") || semestre.equals("2")) grado = "1";
				if (semestre.equals("3") || semestre.equals("4")) grado = "2";
				if (semestre.equals("5") || semestre.equals("6")) grado = "3";
				if (semestre.equals("7") || semestre.equals("8")) grado = "4";
				if (semestre.equals("9") || semestre.equals("10"))grado = "5";
				if (semestre.equals("11")|| semestre.equals("12"))grado = "6";				
			}
			
			
			conEnoc.setAutoCommit(false);
			if (aca.financiero.FesCcobro.updateInscrito(conEnoc, alumno.getCodigoPersonal(), cargaId, alumno.getBloqueId(), "?")
			&& aca.financiero.FesCcobro.updateSemestre(conEnoc, alumno.getCodigoPersonal(), cargaId, alumno.getBloqueId(), semestre)
			&& aca.financiero.FesCcobro.updateGrado(conEnoc, alumno.getCodigoPersonal(), cargaId, alumno.getBloqueId(), grado)
			&& aca.financiero.FesCcobro.updateInscrito(conEnoc, alumno.getCodigoPersonal(), cargaId, alumno.getBloqueId(), "S"))
			{
				conEnoc.commit();
				System.out.println("Actualizó:"+alumno.getCodigoPersonal()+":"+alumno.getCargaId()+":"+alumno.getBloqueId());
			}else{
				conEnoc.rollback();
				System.out.println("Error:"+alumno.getCodigoPersonal()+":"+alumno.getCargaId()+":"+alumno.getBloqueId());
			}			
			conEnoc.setAutoCommit(true);
			//System.out.println(alumno.getCodigoPersonal()+ " - " +semestre+ " - "+grado);		
%>		
<%
		}
%>
	<div class="alert alert-info"><h3>Se terminó de actualizar</h3></div>
<%
	}else{}
%>
</form>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>