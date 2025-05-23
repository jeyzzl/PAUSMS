<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.disciplina.spring.CondAlumno"%>
<%@page import="aca.disciplina.spring.CondReporte"%>

<script type="text/javascript">
	function Borrar( codigo, periodo, folio){
		if(confirm("Are you sure you want to delete this record? : "+folio)==true){
	  		document.location="borrar?codigoPersonal="+codigo+"&Periodo="+periodo+"&folio="+folio;
	  	}
	}
</script>

<%
	String sCodigo 			= (String)request.getAttribute("codigo");
	String sPeriodo 		= (String)request.getAttribute("periodo");
	String sNombre 			= (String)request.getAttribute("nombre");

	String sEmpleado 		= "";
	String sTipo			= "";
	String sTitle			= "";
	String sBgcolor			= "";	
	String nombreReporte	= "";	
	String nombreLugar		= "";	
	int nDescontar			= 0;
	int nUnidad				= 0;
	int nTotal				= 0;
	
	List<CatPeriodo> lisPeriodo = (List<CatPeriodo>)request.getAttribute("lisPeriodo");
	List<CondAlumno> lisAlumno 	= (List<CondAlumno>)request.getAttribute("lisAlumno");
	
	HashMap<String,String> mapMaestroNombre 	= (HashMap<String,String>) request.getAttribute("mapMaestroNombre");
	HashMap<String, CondReporte> mapaReportes 	= (HashMap<String,CondReporte>) request.getAttribute("mapaReportes");
	HashMap<String,String> mapaJuez 			= (HashMap<String,String>) request.getAttribute("mapaJuez");
	HashMap<String,String> mapaLugar 			= (HashMap<String,String>) request.getAttribute("mapaLugar");
	HashMap<String,String> mapaMovimientos	 	= (HashMap<String,String>) request.getAttribute("mapaMovimientos");
%>
<div class="container-fluid">
	<h2>Report Entry<small class="text-muted fs-5">( <%=sCodigo%> - <%=sNombre%> )</small></h2>
	<form id="forma" name="forma" action="unidad" method="post">
		<div class="alert alert-info d-flex align-items-center">
			<select id="periodo" name="periodo" class="form-select" onchange="document.forma.submit();" style="width:250px;">
<%			for(CatPeriodo periodo : lisPeriodo){
				String numMovimientos = "";				
				if (mapaMovimientos.containsKey(periodo.getPeriodoId())){
					numMovimientos = "("+mapaMovimientos.get(periodo.getPeriodoId())+") -";					
				}
%>
				<option value="<%=periodo.getPeriodoId() %>"<%=periodo.getPeriodoId().equals(sPeriodo)?" Selected":"" %>><%=numMovimientos%></span> <%=periodo.getNombre() %></option>
<%			}%>
    		</select>
    		&nbsp;
    		<a class="btn btn-primary" href="grabar?Periodo=<%=sPeriodo%>&codigoPersonal=<%=sCodigo%>&Accion=1"><i class="fas fa-plus-circle"></i> <spring:message code='aca.Añadir'/> Report</a>	      
		</div>
		<table class="table table-sm table-bordered table-striped"> 
		  <tr class="table-info"> 
		    <th width="55" height="21"><font size="1"></th>
		    <th width="46"><spring:message code="aca.Folio"/></th>
		    <th width="193">Report</th>
		    <th width="194">Location</th>
		    <th width="264" class="text-center">Reported</th>
		    <th width="75" class="text-center">Date</th>
		    <th width="64" class="text-end">Misconduct</th>
		    <th width="58" class="text-end">Praise</th>
		  </tr>
		<%	for(int i=0; i<lisAlumno.size(); i++){
				CondAlumno alumno = lisAlumno.get(i);
				if(alumno.getEmpleado() == null){ 
					if(mapaJuez.containsKey(alumno.getIdJuez())){
						sEmpleado = mapaJuez.get(alumno.getIdJuez()); 	
					}
				}else{ 
					if(mapMaestroNombre.containsKey(alumno.getEmpleado())){
						sEmpleado = mapMaestroNombre.get(alumno.getEmpleado()); 	
					}
				}
				
				if(mapaReportes.containsKey(alumno.getIdReporte())){
					sTipo = mapaReportes.get(alumno.getIdReporte()).getTipo();
				}
				
				if(mapaReportes.containsKey(alumno.getIdReporte())){
					nombreReporte = mapaReportes.get(alumno.getIdReporte()).getNombre();
				}
			
				if(mapaLugar.containsKey(alumno.getIdLugar())){
					nombreLugar = mapaLugar.get(alumno.getIdLugar());
				}				
		%>
		  <tr class="tr2"> 
		  	<td height="24" class="text-center">
		    	<a href="grabar?Periodo=<%=sPeriodo%>&codigoPersonal=<%=sCodigo%>&folio=<%=alumno.getFolio()%>&Accion=5"><i class="fas fa-pen"></i></a>
		    	<a href="javascript:Borrar('<%=sCodigo%>','<%=sPeriodo%>','<%=alumno.getFolio()%>')"><i class="fas fa-trash"></i></a> 
		    </td>
		    <td align="center" ><%=alumno.getFolio()%></td>
		    <%	sTitle = "title= '"+alumno.getComentario()+"' "; %>
		    <td <%=sTitle%>><%=nombreReporte%></td>
		    <td ><%=nombreLugar%></td>
		    <%	sTitle = "title= '"+nombreLugar+"' "; %>
		    <td  <%=sTitle%>><%=sEmpleado%></td>
		    <td class="text-center"><%=alumno.getFecha()%></td>
		    <td class="text-end" >
		<%		if(sTipo.equals("D")){%>
		      		<%=alumno.getCantidad()%> 
		<% 			nUnidad = nUnidad + Integer.parseInt(alumno.getCantidad());
				}
		%>		  
		    </td>
		    <td class="text-end"> 
		<%		if(sTipo.equals("C")){%>
					<%=alumno.getCantidad()%> 
		<%			nDescontar = nDescontar + Integer.parseInt(alumno.getCantidad());
				}%>
		  	</td>
		  </tr>
		  
		<%		}
				nTotal = nUnidad - nDescontar;				
		%>
		  <tr class="table-info"> 
		    <td height="21" colspan="4">&nbsp;</td>
		    <td colspan="2" class="text-end"><strong>Sub-Total</strong></td>
		    <td class="text-end"><strong><%=nUnidad%></strong></td>
		    <td class="text-end"><strong><%=nDescontar%></strong></td>
		  </tr>
		  <tr class="table-secondary">		    
		    <td colspan="6" align="center"><strong><spring:message code="aca.Total"/></strong></td>
		    <td class="text-end"><strong><%=nTotal>=0?nTotal:""%></strong></td>
		    <td class="text-end"><strong><%=nTotal<0?Math.abs(nTotal):""%></td>
		  </tr>
		</table>
	</form>
</div>