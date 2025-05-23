<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="HsmU" scope="page" class="aca.cred.CredHsmUtil"/>
<% 
	String area				= request.getParameter("Area");
	String nombre[]  		= area.split("\\.");
	ArrayList lisEmpleados 	= HsmU.getListClinica(conEnoc, area, " ORDER BY NOMBRE");
	lisEmpleados.size();
	

%> 
<link rel="stylesheet" href="../../css/style.css" />
<style>
	.tinytable .head:hover, .tinytable .desc:hover, .tinytable .asc:hover {background: <%=colorTablas%>;}
</style>
<body>
<table style="margin: 0 auto;  width:40%">
  <tr><td align="center" style="font-size:12pt;">Lista de Empleados</td></tr>
</table>
<br>
<table style="margin: 0 auto;  width:60%" class="tabla">
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
				    <th align="center"><h3><spring:message code="aca.Numero"/></h3></th>
				    <th align="center"><h3><spring:message code="aca.Clave"/></h3></th>
				    <th align="center"><h3><%= nombre[0].toUpperCase() %></h3></th>
				  	<th align="center"><h3>Foto</h3></th>
				  	<th align="center"><h3><spring:message code="aca.Estado"/></h3></th>
				  </tr>
			</thead>
			<tbody>
				<%	for(int i=0; i<lisEmpleados.size();i++){
						aca.cred.CredHsm hsm = (aca.cred.CredHsm) lisEmpleados.get(i);
				%>
				  <tr class="button" onMouseOut=this.style.backgroundColor='style='cursor:pointer;' onClick="document.location.href='empleado.jsp?CodigoEmpleado=<%= hsm.getClave()%>'">
				    <td><%= i+1 %></td>
				    <td><%= hsm.getClave() %></td>
				    <td><%=hsm.getNombre() %></td>
				     <%
					  // Verifica si existe la imagen
						String dirFoto 		= application.getRealPath("/WEB-INF/fotos/"+hsm.getClave()+".jpg");
						java.io.File foto 	= new java.io.File(dirFoto);
						if (foto.exists()){%>
					<td align="center"><img src="../../imagenes/activa.png" title="Tiene Foto" width="20px"/></td>
					<%	}else{%>
					<td align="center">
					  <a href="tomarfoto.jsp?CodigoEmpleado=<%=hsm.getClave()%>" title="Tomar la Foto"><img src='../../imagenes/camaraweb.png' width="18" ></a>&nbsp;            	
				      <a href="subir.jsp?CodigoEmpleado=<%=hsm.getClave()%>" title="Subir Foto de un archivo"><img src='../../imagenes/upload.png' width="25" ></a>
				    </td>
					<%	} %>
				    <td>
				<% 
				       	if (hsm.getEstado().equals("0"))
				       		out.print("Anterior");
				       	else if (hsm.getEstado().equals("1"))
				       		out.print("Nuevo");
				       	else if (hsm.getEstado().equals("2"))
				       		out.print("Foto");
				       	else if (hsm.getEstado().equals("3"))
				       		out.print("Impreso");
				%>
				    </td>
				  </tr>
<% }%>  
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