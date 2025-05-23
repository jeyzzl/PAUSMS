<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="aca.carga.spring.Carga"%>
<%@ page import="aca.vista.spring.AlumnoCurso"%>
<%@ page import="aca.catalogo.spring.CatTipoCal"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../css/style.css" />
<% 
	String cargaId 								= (String)request.getAttribute("cargaId");	
	List<AlumnoCurso> listaAlumnos				= (List<AlumnoCurso>) request.getAttribute("listaAlumnos");
	List<Carga> listaCarga 						= (List<Carga>) request.getAttribute("listaCarga");
	HashMap<String,String> mapaTipo 			= (HashMap<String,String>) request.getAttribute("mapaTipo");
	HashMap<String,CatCarrera> mapaCarreras 	= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,AlumPersonal> mapaAlumnos	= (HashMap<String,AlumPersonal>)request.getAttribute("mapaAlumnos");
%>
<div class="container-fluid">
	<h1>Alumnos con Extraordinarios</h1>
	<form id="noayuda" name="forma" action="extra" method='post'>
	<div class="alert alert-info">
		Carga: [ <%=cargaId%> ] 
		<select name="cargaId" style="width:350px" onchange='document.forma.submit()'>
<%		
		for(Carga carga : listaCarga){
%>			<option value='<%=carga.getCargaId() %>' <%if (cargaId.equals(carga.getCargaId()))out.print("selected");%>>
				<%=carga.getCargaId()%>-<%=carga.getNombreCarga()%>
			</option>
<%		}
%>		</select>
	</div>	
	<table style="width:100%" class="tabla">
	<tr>
		<td colspan="15" align="center">
		<div id="tableheader" style="border:1px solid gray;height:46px;background:#ecf2f6;">
	        	<div class="search" style="background:transparent;">
	                <table style="width:100%; height:100%">
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
	       	<table class="table table-bordered">
			<thead class="table-info">
  			  <tr>
  			    <th><h3><spring:message code="aca.Numero"/></h3></th>
  			    <th><h3><spring:message code="aca.Matricula"/></h3></th>
  			    <th><h3><spring:message code="aca.Carrera"/></h3></th>
  			    <th><h3><spring:message code="aca.Nombre"/></h3></th>
  			    <th><h3><spring:message code="aca.Materia"/></h3></th>  			    
  			    <th><h3>Nota Ordinario</h3></th>
  			    <th><h3>Nota Extra</h3></th>
  			    <th><h3><spring:message code="aca.Estado"/></h3></th>
  		    </thead>
			<tbody>
<%		
		int row = 0;
		for(AlumnoCurso curso : listaAlumnos){
			row++;
			String tipoNombre = "-";
			if (mapaTipo.containsKey(curso.getTipoCalId())){
				tipoNombre = mapaTipo.get(curso.getTipoCalId());
			}
			String nombreCarrera = "-";
			if(mapaCarreras.containsKey(curso.getCarreraId())){
				nombreCarrera = mapaCarreras.get(curso.getCarreraId()).getNombreCarrera();
			}
			String nombreAlumno = "-";
			if(mapaAlumnos.containsKey(curso.getCodigoPersonal())){
				nombreAlumno = mapaAlumnos.get(curso.getCodigoPersonal()).getNombre()+" "+mapaAlumnos.get(curso.getCodigoPersonal()).getApellidoPaterno()+" "+mapaAlumnos.get(curso.getCodigoPersonal()).getApellidoMaterno();
			}
%>
			<tr>
			  <td><%=row+1%></td>
			  <td><%=curso.getCodigoPersonal()%></td>
			  <td><%=nombreCarrera%></td>
			  <td><%=nombreAlumno%></td>
			  <td><%=curso.getNombreCurso()%></td>			  			  
			  <td><%=curso.getNota() %></td>
			  <td><%=curso.getNotaExtra()%></td>
			  <td><%=tipoNombre %></td>
			</tr>
<% 		}%>
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
</form>
</div>