<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="cargaU" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="estU" scope="page" class="aca.vista.EstadisticaUtil"/>
<jsp:useBean id="alumPlan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="alumEgreso" scope="page" class="aca.alumno.AlumEgreso"/>
<jsp:useBean id="AlumEgresoU" scope="page" class="aca.alumno.AlumEgresoUtil"/>
<jsp:useBean id="alumTit" scope="page" class="aca.tit.DocAlumno"/>
<jsp:useBean id="alumEventoU" scope="page" class="aca.alumno.AlumEventoUtil"/>


<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.DecimalFormat"%>

<head>
<script>
	function refrescar(){
		document.location.href="terminal?carga="+document.forma.Cargas.value;
	}
</script>
<%
	DecimalFormat getFormato	= new DecimalFormat("###,##0.00;(###,##0.00)");
	ArrayList<aca.carga.Carga> cargas = cargaU.cargas(conEnoc, "");

	String cargaId = request.getParameter("carga")==null?cargas.get(0).getCargaId():request.getParameter("carga");
	
	ArrayList<aca.vista.Estadistica> alumnos = estU.getAlumnos(conEnoc, cargaId, "ORDER BY NOMBRE");
	
	HashMap<String, String> eventos = alumEventoU.getMapEventos(conEnoc, "");

%>
<div class="container-fluid">
<h1>Reporte de eficiencia terminal</h1>
<form name="forma" >
<div class="alert alert-info">
	Carga:&nbsp;
	<select id="Cargas" name="Cargas" onchange="javascript:refrescar()" >
<%			
	for(aca.carga.Carga carga : cargas){
%>
		<option value="<%=carga.getCargaId() %>" <%if(carga.getCargaId().equals(cargaId))out.print("selected"); %>><%=carga.getCargaId() %> [<%=carga.getFInicio().split(" ")[0] %>]</option>
					
<%	} %>
	</select>	
</div>
<link rel="stylesheet" href="../../css/style.css" />
<style>
	.tinytable .head:hover, .tinytable .desc:hover, .tinytable .asc:hover {background: <%=colorTablas%>;}
</style>
</head>
<body onLoad="document.getElementById('tablaT').innerHTML=document.getElementById('tablaTotales').innerHTML">
	<div id="tablaT"></div>
	<table style="width:80%" class="tabla">
		<tr>
  		<td colspan="15" align="center">
  			<div id="tableheader" style="border:1px solid gray;height:46px;background:#ecf2f6;">
	        	<div class="search" style="background:transparent;">
	                <table style="width:100%" height="100%">
	                	<tr>
	                		<td><select style="border-radius: 5px;border:2px solid black;" id="columns" onchange="sorter.search('query')"></select></td>
	                		<td>&nbsp;</td>
	                		<td>
	                			<div style="border:2px solid black;border-radius:5px;width:185px;height:24px;background:url('../../imagenes/search-box.gif') no-repeat top left;">
	                				<table style="width:100%" height="100%">
	                			 		<tr>
	                			 			<td width="25px"></td>
	                			 			<td>
	                			 				<input style=" background-color:transparent;height:20px;width:158px;text-align: left;outline: none;border-width:0px;border-color:white;" 
	                			 	type="text" id="query" onkeyup="sorter.search('query')" />
	                			 			</td>
	                			 		</tr>
	                				</table>
	                			</div>
	                		</td>
	                	</tr>
	                </table>
	            </div>
	            <span class="details">
					<div><spring:message code="aca.Registros"/> <span id="startrecord"></span>-<span id="endrecord"></span> <spring:message code="aca.De"/> <span id="totalrecords"></span></div>
	        		<div><a href="javascript:sorter.reset()"><spring:message code="aca.Reiniciar"/></a>&nbsp;&nbsp;</div>
	        	</span>
	        </div>
		<table id="table" class="tinytable" width="100%" style="border:1px solid gray;border-top:0px solid gray;">
		  <thead>  	
			<tr>
				<th><h3><spring:message code="aca.Numero"/></h3></th>
				<th><h3><spring:message code="aca.Matricula"/></h3></th>
				<th><h3><spring:message code="aca.Nombre"/></h3></th>
				<th><h3><spring:message code="aca.Plan"/></h3></th>
				<th><h3>Primer Matricula</h3></th>
				<th><h3>Graduado</h3></th>
				<th><h3>Trámite</h3></th>
				<th><h3><spring:message code="aca.Fecha"/></h3></th>
				<th><h3>Titulado</h3></th>
				<th><h3><spring:message code="aca.Fecha"/></h3></th>
			</tr>
		</thead>
		<tbody>
			<%
			int contador = 1;
			int contGraduandos 	= 0;
			int contTramites 	= 0;
			int contTitulados 	= 0;
			for(aca.vista.Estadistica alumno : alumnos){ 
				alumPlan.mapeaRegId(conEnoc, alumno.getCodigoPersonal(), alumno.getPlanId());
				
	
				String graduando = AlumEgresoU.existeAlumnoPlan(conEnoc, alumno.getCodigoPersonal(), alumno.getPlanId())==true?"Si":"No";
				String eventoId  = aca.alumno.AlumEgresoUtil.getEvento(conEnoc, alumno.getCodigoPersonal(), alumno.getPlanId());
				
				
				String titulado = "No";
				String tramite 	= "No";
				
				String tituloId = aca.tit.Titulo.getTituloId(conEnoc, alumno.getCodigoPersonal(), alumno.getPlanId());
				
				String fechaTitulacion = "";
				if(!tituloId.equals("")){
					ArrayList<aca.tit.DocAlumVO> alu = alumTit.getDocumentos(conEnoc, tituloId);
					
					for(aca.tit.DocAlumVO al: alu){
						if(al.getDocumentoId()==32 || al.getDocumentoId()==42 || al.getDocumentoId()==48 ){
							titulado = "Si";
							fechaTitulacion = al.getFecha();
							graduando = "Si";
							break;
						}
					}
					
					if(alu.size()>0){
						tramite = "Si";
						graduando = "Si";
					}
				}
				
				if(graduando.equals("Si")) contGraduandos++;
				if(tramite.equals("Si")) contTramites++;
				if(titulado.equals("Si")) contTitulados++;
			%>
				
				<tr>
					<td align="center"><%=contador %></td>
					<td align="center"><%=alumno.getCodigoPersonal() %></td>
					<td><%=alumno.getNombre() +" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno() %></td>
					<td align="center"><%=alumno.getPlanId() %></td>
					<td align="center"><%=alumPlan.getPrimerMatricula() %></td>
					<td align="center"><%=graduando %></td>
					<td align="center"><%=tramite %></td>
					<td align="center"><%=eventos.get(eventoId)==null?"-":eventos.get(eventoId) %></td>
					<td align="center"><%=titulado %></td>
					<td align="center"><%=fechaTitulacion %></td>
				</tr>
			
			<%
				contador++;
			} %>
			 </tbody>
			    </table>
			    <div id="tablefooter" style="border:1px solid gray;border-top:0px solid gray;height:30px;background:#ecf2f6;"">
		          <div id="tablenav" style="position:relative;right:-8px;">
		            	<div>
		                    <img src="../../css/images/first.gif" width="16" height="16" alt="First Page" onclick="sorter.move(-1,true)" />
		                    <img src="../../css/images/previous.gif" width="16" height="16" alt="First Page" onclick="sorter.move(-1)" />
		                    <img src="../../css/images/next.gif" width="16" height="16" alt="First Page" onclick="sorter.move(1)" />
		                    <img src="../../css/images/last.gif" width="16" height="16" alt="Last Page" onclick="sorter.move(1,true)" />
		                </div>
		                <div style="position:relative;top:0px;">
		                	<select id="pagedropdown"></select>
						</div>
		                <div style="position:relative;top:6px;">
		                	<a href="javascript:sorter.showall()"><spring:message code="aca.MostrarTodosRegistros"/></a>
		                </div>
		            </div>
					<div id="tablelocation" style="position:relative;right:11px;">
		            	<div>
		                    <select onchange="sorter.size(this.value)">
		                    <option value="5">5</option>
		                        <option value="10" >10</option>
		                        <option value="20" >20</option>
		                        <option value="50">50</option>
		                        <option value="100">100</option>
		                        <option value="600" selected="selected">600</option>
		                    </select>
		                    <span><spring:message code="aca.EntradasPagina"/></span>
		                </div>
		                <div class="page"><spring:message code="aca.Pagina"/> <span id="currentpage"></span> <spring:message code="aca.De"/> <span id="totalpages"></span></div>
		            </div>
		        </div>
			 </td>
		  </tr>
	</table>
	<div id="tablaTotales">
		<table class="tabla" align="left" width="80%">
			<tr>
				<th>Total de alumnos</th>
				<th colspan="1">Graduandos</th>
				<th colspan="1">% Tot</th>
				<th colspan="1">Trámites</th>
				<th colspan="1">% Tot.</th>
				<th colspan="1">% Grad.</th>
				<th colspan="1">Titulados</th>
				<th colspan="1">% Tot.</th>
				<th colspan="1">% Grad.</th>
			</tr>
			<tr class="tr2">
				<td align="center"><b><%=alumnos.size() %></b></td>
				<td align="center"><b><%=contGraduandos %></b></td>
				<td align="center"><b><font color="#AE2113"><%=getFormato.format((float)contGraduandos*100/(float)alumnos.size()) %>%</font></b></td>
				<td align="center"><b><%=contTramites %></b></td>
				<td align="center"><b><font color="#AE2113"><%=getFormato.format((float)contTramites*100/(float)alumnos.size()) %>%</font></b></td>
				<td align="center"><b><font color="#AE2113"><%=getFormato.format((float)contTramites*100/(float)contGraduandos) %>%</font></b></td>
				<td align="center"><b><%=contTitulados %></b></td>
				<td align="center"><b><font color="#AE2113"><%=getFormato.format((float)contTitulados*100/(float)alumnos.size()) %>%</font></b></td>
				<td align="center"><b><font color="#AE2113"><%=getFormato.format((float)contTitulados*100/(float)contGraduandos) %>%</font></b></td>
			</tr>
		</table>
</form>
	</div>
</body>
<script type="text/javascript" src="../../js/script.js"></script>
<script type="text/javascript">
var sorter = new TINY.table.sorter('sorter','table',{
	headclass:'head',
	ascclass:'asc',
	descclass:'desc',
	evenclass:'evenrow',
	oddclass:'oddrow',
	evenselclass:'evenselected',
	oddselclass:'oddselected',
	paginate:true,
	size:600,
	colddid:'columns',
	currentid:'currentpage',
	totalid:'totalpages',
	startingrecid:'startrecord',
	endingrecid:'endrecord',
	totalrecid:'totalrecords',
	hoverid:'selectedrow',
	pageddid:'pagedropdown',
	navid:'tablenav',
	sortcolumn:0,
	sortdir:0,
	init:true
});
 </script>
<%@ include file= "../../cierra_enoc.jsp" %>