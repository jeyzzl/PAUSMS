<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "aca.carga.CargaUtil" %>
<%
	ArrayList lisCarga 			= new ArrayList (); 
	CargaUtil cargaU			= new CargaUtil();
	lisCarga					= cargaU.getListAll(conEnoc, "ORDER BY 2");
	String sBgcolor				= "";
	int i						= 0;
%>
<link rel="stylesheet" href="../../css/style.css" />
<style>
	.tinytable .head:hover, .tinytable .desc:hover, .tinytable .asc:hover {background: <%=colorTablas%>;}
</style>
<table style="width:75%"  align="center">
  <tr> 
    <td>&nbsp;</td>
  </tr>
  <tr> 
    <td><div align="center"><strong><font size="3">ELEGIR UNA CARGA</font></strong></div></td>
  </tr>
</table>
<a>&nbsp;</a>
<table style="width:75%"  align="center" class="tabla">
  <tr> 
    <td colspan="3"><strong>Mensaje: </strong>Click sobre el nombre de la carga</td>
  </tr>
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
	        <table id="table" class="tinytable" width="100%" align="center" style="border:1px solid gray;border-top:0px solid gray;">
		  	
		     <thead>
			  <tr>	
			    <th width="10%"><h3><spring:message code="aca.Ciclo"/></h3></th>
			    <th width="26%"><h3><spring:message code="aca.Carga"/></h3></th>
			    <th width="64%"><h3><spring:message code="aca.Nombre"/></h3></th>
  			 </tr>
  			 </thead>
			<tbody>
  <% for(i=0; i<lisCarga.size(); i++){
		aca.carga.Carga carga = (aca.carga.Carga) lisCarga.get(i);
		if (carga.getEstado().equals("1")){
		if ((i % 2) == 0 ) sBgcolor = sColor; else sBgcolor = "";
%>
  <tr class="tr2" <%=sBgcolor%>> 
    <td><div align="center"><%=carga.getCiclo()%></div></td>
    <td><div align="center"><%=carga.getCargaId()%></div></td>
	<input name="CargaId" type="hidden" value="carga.getCargaId()">
    <td><a href="sinalumno?CargaId=<%=carga.getCargaId()%>"><%=carga.getNombreCarga()%></a></td>
  </tr>
  <%		} //fin del if 
 	} //fin del for 
%>
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
                    <span><spring:message code="aca.EntradasPagina"/></span>
                </div>
                <div class="page"><spring:message code="aca.Pagina"/> <span id="currentpage"></span> <spring:message code="aca.De"/> <span id="totalpages"></span></div>
            </div>
        </div>
		</td>
	</tr>
</table> 

<% 	
	lisCarga				= null;
	cargaU					= null;
%>
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