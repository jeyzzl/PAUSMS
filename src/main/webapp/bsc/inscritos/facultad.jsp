<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="facUtil"  class="aca.catalogo.FacultadUtil" scope="page"/>
 <link rel="stylesheet" href="../../css/style.css" />
<style>
	.tinytable .head:hover, .tinytable .desc:hover, .tinytable .asc:hover {background: <%=colorTablas%>;}
</style>
<%  
	ArrayList<aca.catalogo.CatFacultad> lisFac			= facUtil.getListAll(conEnoc,"ORDER BY 1");
	String sBgcolor	= "";
	int cont		= 0;
%>
<style type="text/css"></style>
<div id='content'>
	<h2><spring:message code="aca.ListaDeFacultades"/></h2>
	<div class='well'>
		<a class="btn btn-primary" href="um"><spring:message code="aca.Regresar"/></a>
	</div>
	<table style="width:57%" class="tabla"  >
		<tr>
			<td>
				<table style="width:100%" >
					<tr>
						<td colspan="15" align="center">
							<div id="tableheader" style="border:1px solid gray;height:46px;background:#ecf2f6;">
					        	<div class="search" style="background:transparent;">
					        		<table style="width:100%; height:100%" >
					                	<tr>
					                		<td><select style="border-radius: 5px;border:2px solid black;" id="columns" onchange="sorter.search('query')"></select></td>
					                		<td>&nbsp;</td>
					                		<td>
					                			<div style="border:2px solid black;border-radius:5px;width:185px;height:24px;background:url('../../imagenes/search-box.gif') no-repeat top left;">
					                				<table style="width:100%; height:100%">
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
					        	<div>
						            <span class="details">
										<spring:message code="aca.Registros"/> <span id="startrecord"></span>-<span id="endrecord"></span> <spring:message code="aca.De"/> <span id="totalrecords"></span>
						        	</span>
					        	</div>
					        	<div>
						            <span class="details">
						        		<a href="javascript:sorter.reset()"><spring:message code="aca.Reiniciar"/></a>&nbsp;&nbsp;
						        	</span>
					        	</div>
					        </div>
					  		<table id="table" class="tinytable" style="width:100%; margin: 0 auto;" style="border:1px solid gray;border-top:0px solid gray;">
						     	<thead>
								  	<tr> 
									    <th width="5%" align="center"><h3><spring:message code="aca.Numero"/></h3></th>
									    <th width="50%" align="center"><h3><spring:message code="aca.Facultad"/></h3></th>
									    <th width="45%" align="center"><h3><spring:message code="aca.Director"/></h3></th>
								  	</tr>
								</thead>
							<tbody>
							<%
								for (int i=0; i< lisFac.size(); i++){	cont++;
									aca.catalogo.CatFacultad fac = (aca.catalogo.CatFacultad) lisFac.get(i);
									if ((cont % 2) == 1 ) sBgcolor = sColor; else sBgcolor = "";
							%>
							  <tr class="tr2" <%=sBgcolor%>>
							    <td align="left"><font size="2"><%=fac.getFacultadId()%></font></td>
							    <td align="left"><a href="estCarreras?FacultadId=<%=fac.getFacultadId()%>"><font size="2"><%=fac.getTitulo()%> de <%=fac.getNombreFacultad()%></font></a></td>
							    <td align="left"><font size="1"><%=aca.vista.MaestrosUtil.getNombreMaestro(conEnoc,fac.getCodigoPersonal(),"NOMBRE")%></font></td>
							  </tr>
							<%
								}	
								lisFac	= null;
							%> 
							</tbody>
							</table>
							<div id="tablefooter" style="border:1px solid gray;border-top:0px solid gray;height:30px;background:#ecf2f6;">
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
				                    <span><spring:message code="aca.EntradasPagina"/></span>
				                </div>
				                <div class="page"><spring:message code="aca.Pagina"/> <span id="currentpage"></span> <spring:message code="aca.De"/> <span id="totalpages"></span></div>
				            </div>
				        </div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<table style="width:50%; margin: 0 auto;">
<tr> 
  <td colspan="4"><strong>¡ <spring:message code="aca.EligeUnaFacultad"/> !</strong></td>
</tr>
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
 </div>
<%@ include file= "../../cierra_enoc.jsp" %>