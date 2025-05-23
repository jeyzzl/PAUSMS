<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.catalogo.spring.CatEstado"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script>	
	
	function Refrescar( ){
  		document.forma.Accion.value = "0";
  		document.forma.submit();
	}
	
	function Mostrar(){
  		document.forma.Accion.value = "1";
  		document.forma.submit();
	}
	
</script>
<%
	String modalidades			= (String)request.getAttribute("modalidades");
	String periodoId 			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
	String cargaId 				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	// Si no se eligio un periodo se toma su valor de la sesion
	if (periodoId.equals("0")){
		periodoId = (String)session.getAttribute("periodo");
	}
	
	// Si no se eligió una carga se toma el valor de la sesion
	if (cargaId.equals("0")){
		cargaId	= (String)session.getAttribute("cargaId");
	}
	
	// Subir el periodo y la carga seleccionados
	if (accion.equals("1")){
		session.setAttribute("periodo", periodoId);
		session.setAttribute("cargaId", cargaId);
	}
	
	// Lista de periodos
	List<CatPeriodo> listaPeriodos 		= (List<CatPeriodo>)request.getAttribute("listaPeriodos");
	
	// Lista de cargas
	List<Carga> lisCarga 				= (List<Carga>)request.getAttribute("lisCarga");
	
	// Lista de estados de México
	List<CatEstado> lisEstados 			= (List<CatEstado>)request.getAttribute("lisEstados");
	
	// Lista de carreras
	List<CatCarrera> lisCarreras 		= (List<CatCarrera>)request.getAttribute("lisCarreras");
	
	// Cuenta los alumnos inscritos en una carrera y estado
	HashMap<String,String> mapTotCarrera 		= (HashMap<String,String>)request.getAttribute("mapTotCarrera");
	
	// Cuenta los alumnos inscritos en un estado
	HashMap<String,String> mapTotEstado 				= (HashMap<String,String>)request.getAttribute("mapTotEstado");
	
	HashMap<String, String> mapModalidades 				= (HashMap<String, String>)request.getAttribute("mapNombreModalidad");
	HashMap<String, CatFacultad> mapNombreFacultad 		= (HashMap<String, CatFacultad>)request.getAttribute("mapNombreFacultad");
	
	String lisModo[] 		= modalidades.replace("'", "").split(",");
		
%>
<div class="container-fluid">
	<h1>Estadisticas por Estado</h1>
	<form name="forma" action="reporte">
	<input name="Accion" type="hidden">
	<input name="Opcion" type="hidden">
	<div class="alert alert-info">
		<b><spring:message code="aca.Periodo"/>: </b>
		<select name="PeriodoId" class="input input-medium" onchange="javascript:Refrescar();">
<%	for(CatPeriodo periodo : listaPeriodos){%>
		<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%	}%>
		</select>

		&nbsp;&nbsp;
		<b><spring:message code="aca.Carga"/>: </b>
		<select name="CargaId" style="width:350px;" onchange="javascript:Refrescar();">
<%	for(Carga carga : lisCarga){%>
		<option <%if(cargaId.equals(carga.getCargaId()))out.print(" Selected ");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
<%	}%>
		</select>
		&nbsp;&nbsp;&nbsp;
		<a href="modalidades" class="btn btn-success"><spring:message code="aca.Modalidad"/></a>&nbsp;
<%
		for(String mod:lisModo){
			String nombreModalidad = "-";
			if (mapModalidades.containsKey(mod)){
				nombreModalidad = mapModalidades.get(mod);
			}
			out.print("["+nombreModalidad+"] ");	
		}
%>						
		&nbsp;&nbsp;&nbsp;
		<a href="javascript:Mostrar();" class="btn btn-primary"><spring:message code="aca.Mostrar"/></a>
	</div>	
	</form>
<%	if (accion.equals("1")){ %>	
	<table style="width:90%;cellpadding:0; cellspacing:0;"  id="noayuda" class="table table-condensed">
  	<tr>
    	<th align="center" class="titulo"><spring:message code="analisis.est_estado.Titulo"/></th>
  	</tr>
	<tr>
		<td>
			<table style="width:90%; border:1; cellpadding:0; cellspacing:0; bordercolor:#000000;">
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
<%
		for(CatEstado estado : lisEstados){
%>
				<td><b><%=estado.getNombreEstado() %></b></td>
<%		} %>
				<td><b><spring:message code="aca.Total"/></b></td>
			</tr>
<%
		String nombreFacultad = "-";
		for(CatCarrera carrera : lisCarreras){
			if(mapNombreFacultad.containsKey(carrera.getFacultadId())){
				nombreFacultad = mapNombreFacultad.get(carrera.getFacultadId()).getNombreFacultad();
			}
%>
			<tr>
				<td><b><%=nombreFacultad%></b></td>
				<td><b><%=carrera.getNombreCarrera()%></b></td>
<%
			int sumaCarreras = 0;
			for(CatEstado catEstado : lisEstados){
				int carreraEstado = 0;
				if (mapTotCarrera.containsKey("91"+catEstado.getEstadoId()+carrera.getCarreraId())){
					carreraEstado = Integer.parseInt (mapTotCarrera.get("91"+catEstado.getEstadoId()+carrera.getCarreraId()));				 
				}
%>
				<td align="center"><%if(carreraEstado != 0) out.print(carreraEstado); else out.print("&nbsp;"); %></td>
<%
				sumaCarreras += carreraEstado;
			}
%>
				<td align="center"><b><%=sumaCarreras %></b></td>
			</tr>
<%		} %>
			<tr>
				<td align="center" colspan="2"><b><spring:message code="aca.Total"/></b></td>
<%
		int totalEstados = 0;
		for(CatEstado est : lisEstados){
			int sumaEstados = 0;	
			if (mapTotEstado.containsKey("91"+est.getEstadoId())){
				sumaEstados = Integer.parseInt (mapTotEstado.get("91"+est.getEstadoId()) );
			}
%>
				<td align="center"><b><%=sumaEstados%></b></td>
<%
			totalEstados += sumaEstados;
		}
%>
				<td align="center"><b><%=totalEstados %></b></td>
			</tr>
			</table>
		</td>
	</tr>		
	</table>
	</div>
<%	} %>