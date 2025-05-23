<%@ page import= "aca.plan.MapaPlan"%>
<jsp:useBean id="planUtil"  class="aca.plan.PlanUtil" scope="page"/>
<jsp:useBean id="carreraUtil"  class="aca.catalogo.CarreraUtil" scope="page"/>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../css/style.css" />
<style>
	.tinytable .head:hover, .tinytable .desc:hover, .tinytable .asc:hover {background: <%=colorTablas%>;}
</style>
<%
	String facultad 	= request.getParameter("facultad");
	ArrayList lisCarr	= carreraUtil.getLista(conEnoc,facultad,"ORDER BY ENOC.CARRERA_NIVEL(CARRERA_ID), NOMBRE_CARRERA");
	ArrayList lisPlan	= planUtil.getListPlanFac(conEnoc,facultad,"ORDER BY PLAN_ID");
	if (request.getParameter("facultad")!=null){
		session.setAttribute("fac",facultad);
	}else{
		facultad = (String)session.getAttribute("fac");
	}
	
	String sBgcolor		= "";
	int cont			= 0;
%>
<div class="container-fluid">
<h1><strong>Planes de: <%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc,facultad)%></strong></h1>
<div class="alert alert-info">
	<a class="btn btn-primary" href="facultad.jsp">Regresar</a>
</div>
<table style="width:90%"  >
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
					<div>Registros <span id="startrecord"></span>-<span id="endrecord"></span> de <span id="totalrecords"></span></div>
	        		<div><a href="javascript:sorter.reset()">Reiniciar</a>&nbsp;&nbsp;</div>
	        	</span>
	        </div>
		  <table id="table" class="tinytable" width="100%" style="border:1px solid gray;border-top:0px solid gray;">
		  	
		     <thead>
				  <tr>
				    <th width="4%"><h3><spring:message code="aca.Numero"/></h3></th>
				    <th width="4%"><h3><spring:message code="aca.Clv"/></h3></th>
				    <th width="35%"><h3><spring:message code="aca.Nombre"/></h3></th>
				    <th width="35%"><h3><spring:message code="aca.Planes"/></h3></th>
				    <th width="25%"><h3><spring:message code="aca.Coordinador"/></h3></th>
				  </tr>
			</thead>
			<tbody>
				<%
					for (int i=0; i< lisCarr.size(); i++){ cont++;
						aca.catalogo.CatCarrera carrera = (aca.catalogo.CatCarrera) lisCarr.get(i);
						if ((cont % 2) == 1 ) sBgcolor = sColor; else sBgcolor = "";
				%>  
				  <tr <%=sBgcolor%>>
				    <td align="center"><strong><%=i+1%></strong></td>
				    <td align="center"><strong><%=carrera.getCarreraId()%></strong></td>
				    <td><strong>
				<%		if (carrera.getNombreCarrera().length()>70){
							out.print(carrera.getNombreCarrera().substring(0,69));
						}else{
							out.print(carrera.getNombreCarrera());
						}
				%>  </strong></td>
					<td>
						<%
						for (int j=0; j< lisPlan.size(); j++){
							MapaPlan mplan = (MapaPlan) lisPlan.get(j);
							if ( mplan.getCarreraId().equals(carrera.getCarreraId())){ %>
					<a href="avance.jsp?planId=<%= mplan.getPlanId()%>&facultad=<%= facultad%>&carrera=<%=carrera.getCarreraId()%>"><%=mplan.getPlanId()%></a>&nbsp;&nbsp;&nbsp;
				<%			}
						}
				%>  
					</td>
				    <td><strong><%=aca.vista.MaestrosUtil.getNombreMaestro( conEnoc, carrera.getCodigoPersonal(), "NOMBRE")%></strong></td>
				  </tr>
				<%	} %>
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
                	<a href="javascript:sorter.showall()">Mostrar todos los registros</a>
                </div>
            </div>
			<div id="tablelocation" style="position:relative;right:11px;">
            	<div>
                    <select onchange="sorter.size(this.value)">
                    <option value="5">5</option>
                        <option value="10">10</option>
                        <option value="20">20</option>
                        <option value="50" selected="selected">50</option>
                        <option value="100">100</option>
                    </select>
                    <span>Entradas Por Página</span>
                </div>
                <div class="page"><spring:message code='aca.Pagina'/><span id="currentpage"></span> de <span id="totalpages"></span></div>
            </div>
        </div>
		</td>
	</tr>	
<%
	planUtil 	= null;
	lisPlan	= null;
%>
</table>
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
	size:50,
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
	sortdir:1,
	init:true
});
 </script>
<%@ include file= "../../cierra_enoc.jsp" %>