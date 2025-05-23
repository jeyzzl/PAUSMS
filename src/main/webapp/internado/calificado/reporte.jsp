<%@page import="aca.alumno.AlumPlan"%>
<%@page import="aca.catalogo.CatCarrera"%>
<%@page import="aca.alumno.AlumPersonal"%>
<%@page import="aca.vista.Inscritos"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="inscritos" scope="page" class="aca.vista.Inscritos"/>
<jsp:useBean id="inscritosU" scope="page" class="aca.vista.InscritosUtil"/>
<jsp:useBean id="comAutorizacion" scope="page" class="aca.internado.ComAutorizacion"/>
<jsp:useBean id="comAutorizacionU" scope="page" class="aca.internado.ComAutorizacionUtil"/>
<jsp:useBean id="catModalidadU" scope="page" class="aca.catalogo.ModalidadUtil"/>

<script type="text/javascript">
	
	function Mostrar(){	
		document.frmComidas.submit();
	}

</script>
<%	
	String cargas			= (String)session.getAttribute("cargas") == null?aca.carga.CargaUtil.getCargasActivas(conEnoc, aca.util.Fecha.getHoy()):session.getAttribute("cargas").toString();
	String modalidades		= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();
	
	String fechaIni			= request.getParameter("FechaIni")==null?session.getAttribute("fechaIni").toString():request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFin");
	
	if ((String)session.getAttribute("cargas") == null){
		session.setAttribute("cargas", aca.carga.CargaUtil.getCargasActivas(conEnoc, aca.util.Fecha.getHoy()));
	}
	
	if(cargas.equals(""))cargas="''";

	String matricula, matriculaAnterior = "", colorFila = "", colorLetra = "";
	String strComidas		= "0";
	int sinComida 			= 0;
	int unaComida 			= 0;
	int dosComidas 			= 0;
	int tresComidas			= 0;
	int tSinComida 			= 0;
	int tUnaComida 			= 0;
	int tDosComidas	 		= 0;
	int tTresComidas 		= 0;
	
	ArrayList<Inscritos> lisDormi 		= new ArrayList<Inscritos>();
	ArrayList<Inscritos> lisSinDormi 	= new ArrayList<Inscritos>();
	String lisModo[] 		= modalidades.replace("'", "").split(",");
%>
<div class="container-fluid">
	<h2>Customized Boarding</h2>
	<form name="frmComidas" action="reporte" method="post">
	<div class="alert alert-info d-flex align-items-center">
		Loads: <%= cargas.replace("'", "")%>&nbsp;&nbsp; <a href="cargas" class="btn btn-primary"><spring:message code="aca.Elegir"/></a>&nbsp;&nbsp;
		Modalities:
<%
		for(String mod:lisModo){
			String nombreModalidad = aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, mod);
			out.print("["+nombreModalidad+"] ");	
		}		
%>		&nbsp; <a href="modalidades" class="btn btn-primary"><spring:message code="aca.Elegir"/></a>
		&nbsp;&nbsp;&nbsp;
	</div>
	<div class="alert alert-info">
		Start Date: <input id="FechaIni" name="FechaIni" type="text" class="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" size="10" /> (DD/MM/YYYY)
		&nbsp;&nbsp;
		End Date: <input id="FechaFin" name="FechaFin" type="text" class="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" size="10" /> (DD/MM/YYYY)&nbsp; &nbsp;
		<a href="javascript:Mostrar()" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>
	</div>
	</form>
	<table style="width:80%"   >
<%		
	for(int i = 1; i <= 4; i++){
		sinComida = 0; unaComida = 0; dosComidas = 0; tresComidas = 0;
%></table>
	<table style="width:80%"   class="table table-sm table-bordered">
		<tr>
			<td colspan="5"><font size='4'><b>Dormitory <%=i %></b></font></td>
		</tr>
		<tr class="table-info">
			<th width="5%"><spring:message code="aca.Numero"/></th>
			<th align="center">Student ID</th>
			<th align="center"><spring:message code="aca.Nombre"/></th>
			<th align="center">School</th>
			<th align="center"><spring:message code="aca.Carrera"/></th>
			<th align="center">Breakfast</th>
			<th align="center">Lunch</th>
			<th align="center">Dinner</th>
			<th align="center">N° Meals</th>			
			<th align="center"><spring:message code="aca.Carga"/></th>
			<th align="center"><spring:message code="aca.Bloque"/></th>
			<th align="center">Gender</th>		
		</tr>
<%
		lisDormi = inscritosU.getListDormitorio(conEnoc, String.valueOf(i), cargas, modalidades, fechaIni, fechaFin, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");		
		for(int j = 0; j < lisDormi.size(); j++){
			inscritos = (Inscritos) lisDormi.get(j);
			matricula = inscritos.getCodigoPersonal();
			int desayuno 	= 0;
			int comida		= 0;
			int cena		= 0;
			
			if(matricula.equals(matriculaAnterior)){
				colorLetra = " color=\"#FF0000\" title=\"Repeated Student\"";
			}else
				colorLetra = "";
			if (comAutorizacionU.existeReg(conEnoc,matricula,inscritos.getCargaId(), inscritos.getBloqueId())){
				comAutorizacion.mapeaRegId(conEnoc, matricula, inscritos.getCargaId(), inscritos.getBloqueId());
				strComidas = comAutorizacion.getNumComidas();
				
				if (comAutorizacion.getTipoComida().substring(0,1).equals("1")) desayuno	= 1;
				if (comAutorizacion.getTipoComida().substring(1,2).equals("1")) comida 		= 1;
				if (comAutorizacion.getTipoComida().substring(2,3).equals("1")) cena 		= 1;
				
				if(strComidas.equals("0")){
					sinComida++;
				}else if(strComidas.equals("1")){
					unaComida++;
				}else if(strComidas.equals("2")){
					dosComidas++;
				}else if(strComidas.equals("3")){
					tresComidas++;
				}
				
			}else{
				strComidas = "0";
				sinComida++;
			}			
			String facultadNombre = aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, inscritos.getCarreraId()));
%>
		<tr>
			<td><font <%=colorLetra %>><%=j+1 %></font></td>
			<td><font <%=colorLetra %>><%=inscritos.getCodigoPersonal() %></font></td>
			<td><font <%=colorLetra %>><%=inscritos.getApellidoPaterno() %> <%=inscritos.getApellidoMaterno() %> <%=inscritos.getNombre() %></font></td>
			<td><font <%=colorLetra %>><%=facultadNombre%></font></td>
			<td><font <%=colorLetra %>><%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, inscritos.getCarreraId()) %></font></td>
			<td align="center"><font <%=colorLetra %>><%=desayuno%></font></td>
			<td align="center"><font <%=colorLetra %>><%=comida%></font></td>
			<td align="center"><font <%=colorLetra %>><%=cena%></font></td>
			<td align="center"><font <%=colorLetra %>><%=strComidas %></font></td>
			<td><font <%=colorLetra %>><%=inscritos.getCargaId() %></font></td>
			<td align="center"><font <%=colorLetra %>><%=inscritos.getBloqueId() %></font></td>
			<td align="center"><font <%=colorLetra %>><%=inscritos.getSexo() %></font></td>
		</tr>
<%
			matriculaAnterior = matricula;
		}
		tSinComida += sinComida;
		tUnaComida += unaComida;
		tDosComidas += dosComidas;
		tTresComidas += tresComidas;
%>
		<tr>
			<td colspan="8" align="right">
				<table>
					<tr bgcolor="#CCCCCC">
						<td><b> Total without meals:</b> <%=sinComida %> </td>
						<td><b> Total with 1 meal:</b> <%=unaComida %> </td>
						<td><b> Total with 2 meal:</b> <%=dosComidas %> </td>
						<td><b> Total with 3 meal:</b> <%=tresComidas %> </td>
					</tr>
				</table>
			</td>
		</tr>
<%	} %>
	</table>
<%
	lisSinDormi = inscritosU.getListSinDormitorio(conEnoc, cargas, modalidades, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");	
	if (lisSinDormi.size()>0){
%>		
	<table class="table table-sm table-bordered" style="width:50%">		
		<tr>
		  <td colspan="9"><font size='3'>Enrolled Students with no assigned Dormitory</font></td>
		</tr>
		<tr class="table-info">
		  <th width="5%"><spring:message code="aca.Numero"/></th>
		  <th align="center">ID</th>
		  <th align="center"><spring:message code="aca.Nombre"/></th>
		  <th align="center"><spring:message code="aca.Carrera"/></th>
		  <th align="center">N° Meals</th>
		  <th align="center"><spring:message code="aca.Carga"/></th>
		  <th align="center"><spring:message code="aca.Bloque"/></th>
		  <th align="center">Gender</th>		
		</tr>
<%		
	
		for(int j = 0; j < lisSinDormi.size(); j++){
			inscritos = (Inscritos) lisSinDormi.get(j);
			matricula = inscritos.getCodigoPersonal();
			if (comAutorizacionU.existeReg(conEnoc,matricula,inscritos.getCargaId(), inscritos.getBloqueId())){
				comAutorizacion.mapeaRegId(conEnoc, matricula, inscritos.getCargaId(), inscritos.getBloqueId());
				strComidas = comAutorizacion.getNumComidas();
				if(strComidas.equals("0")){
					sinComida++;
				}else if(strComidas.equals("1")){
					unaComida++;
				}else if(strComidas.equals("2")){
					dosComidas++;
				}else if(strComidas.equals("3")){
					tresComidas++;			
				}	
			}else{
				strComidas = "0";
			}		
%>		
		<tr>
			<td><font <%=colorLetra %>><%=j+1 %></font></td>
			<td><font <%=colorLetra %>><%=inscritos.getCodigoPersonal() %></font></td>
			<td><font <%=colorLetra %>><%=inscritos.getApellidoPaterno() %> <%=inscritos.getApellidoMaterno() %> <%=inscritos.getNombre() %></font></td>
			<td><font <%=colorLetra %>><%=aca.catalogo.CatCarreraUtil.getNombreCorto(conEnoc, inscritos.getCarreraId()) %></font></td>
			<td align="center"><font <%=colorLetra %>><%=strComidas %></font></td>
			<td><font <%=colorLetra %>><%=inscritos.getCargaId() %></font></td>
			<td align="center"><font <%=colorLetra %>><%=inscritos.getBloqueId() %></font></td>
			<td align="center"><font <%=colorLetra %>><%=inscritos.getSexo() %></font></td>
		</tr>
<%		}
	}
%>		
		</table>
		<table class="table table-bordered table-sm" style="width:50%">
		<tr class="table-info">
			<th colspan="2">Grand Total</th>
		</tr>
		<tr>
			<td><b>Without Meals:</b></td>
			<td align="right"><%=tSinComida %></td>
		</tr>
		<tr>
			<td><b>1 Meal:</b></td>
			<td align="right"><%=tUnaComida %></td>
		</tr>
		<tr>
			<td><b>2 Meals:</b></td>
			<td align="right"><%=tDosComidas %></td>
		</tr>
		<tr>
			<td><b>3 Meals:</b></td>
			<td align="right"><%=tTresComidas %></td>
		</tr>
<% int total= tSinComida+tUnaComida+tDosComidas+tTresComidas; %>
		<tr>
			<td><b><spring:message code="aca.Total"/></b></td>
			<td align="right"><%=total %></td>
		</tr>			
	</table>			   
</div>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>
<%@ include file= "../../cierra_enoc.jsp" %>