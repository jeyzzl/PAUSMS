<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../css/style.css" />
<%
	String facultad 					= request.getParameter("facultad");
	String facultadNombre 				= (String) request.getAttribute("facultadNombre");
	List<CatCarrera> lisCarreras 				= (List<CatCarrera>) request.getAttribute("lisCarreras");
	List<MapaPlan> lisPlanes 					= (List<MapaPlan>) request.getAttribute("lisPlanes");
	HashMap<String,String> mapaCoordinadores	= (HashMap<String,String>) request.getAttribute("mapaCoordinadores");
	HashMap<String,String> mapaCursosPorPlan	= (HashMap<String,String>) request.getAttribute("mapaCursosPorPlan");
	HashMap<String,String> mapaCursosEnLinea	= (HashMap<String,String>) request.getAttribute("mapaCursosEnLinea");
	
	String sBgcolor		= "";
%>
<div class="container-fluid">
<h3><spring:message code="mapa.materia.Titulo1"/> <%=facultadNombre%></h3>
<div class="alert alert-info">
	<a class="btn btn-primary" href="facultad"><i class="fas fa-arrow-left"></i></a>
</div>
<table style="width:100%"  >
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
				    <th width="4%"><h3><spring:message code='aca.Numero'/></h3></th>
				    <th width="4%"><h3><spring:message code="aca.Clave"/></h3></th>
				    <th width="35%"><h3><spring:message code="aca.Nombre"/></h3></th>
				    <th width="35%"><h3><spring:message code="aca.Planes"/></h3></th>
				    <th width="25%"><h3><spring:message code="aca.Coordinador"/></h3></th>
				  </tr>
			</thead>
			<tbody>
				<%
					int row = 0;
					for (CatCarrera carrera : lisCarreras){
						row++;
						
						String empleadoNombre = ""; 
						if (mapaCoordinadores.containsKey( carrera.getCodigoPersonal())){
							empleadoNombre = mapaCoordinadores.get( carrera.getCodigoPersonal());
						}
				%>  
				  <tr <%=sBgcolor%>>
				    <td align="center"><strong><%=row%></strong></td>
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
						for (MapaPlan plan : lisPlanes){
							if ( plan.getCarreraId().equals(carrera.getCarreraId())){
								
								String numCursosYEnLinea = "";
								String estilo = "";
								String numCursos = "0";
								String numCursosEnLinea = "0";
								if (mapaCursosPorPlan.containsKey(plan.getPlanId()) ){
									numCursos = mapaCursosPorPlan.get(plan.getPlanId());
									numCursosYEnLinea = "<span class='badge bg-dark'>"+numCursos+"-"+numCursosEnLinea+"</span>";
									if (mapaCursosEnLinea.containsKey(plan.getPlanId()) ){
										numCursosEnLinea = mapaCursosEnLinea.get(plan.getPlanId());
										numCursosYEnLinea = "<span class='badge bg-dark'>"+numCursos+"-"+numCursosEnLinea+"</span>";
									}
									estilo = "<span class='badge bg-secondary rounded-pill'>"+plan.getPlanId()+"</span>";
									if (plan.getEstado().equals("A")) estilo = "<span class='badge bg-info rounded-pill'>"+plan.getPlanId()+"</span>";
									if (plan.getEstado().equals("V")) estilo = "<span class='badge bg-success rounded-pill'>"+plan.getPlanId()+"</span>";
									if (plan.getEstado().equals("I")) estilo = "<span class='badge bg-warning rounded-pill'>"+plan.getPlanId()+"</span>";
								}
									
						%>
							
					<a href="show_plan?facultad=<%=facultad%>&planId=<%=plan.getPlanId()%>" style = "text-decoration:none;"><%=estilo%>-<%=numCursosYEnLinea%></a>&nbsp;&nbsp;&nbsp;
				<%			}
						}
				%>  
					</td>
				    <td><strong><%=empleadoNombre%></strong></td>
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
                	<a href="javascript:sorter.showall()"><spring:message code="aca.MostrarTodosRegistros"/></a>
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
                    <span>Entries by Page</span>
                </div>
                <div class="page"><spring:message code='aca.Pagina'/><span id="currentpage"></span> <spring:message code="aca.De"/> <span id="totalpages"></span></div>
            </div>
        </div>
		</td>
	</tr>	
</table>
</div>
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