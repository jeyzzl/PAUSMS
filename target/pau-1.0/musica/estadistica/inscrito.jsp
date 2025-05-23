<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.musica.MusiPeriodo"%>

<%@page import="aca.musica.MusiAlumno"%>
<jsp:useBean id="PeriodoU" scope="page" class="aca.musica.MusiPeriodoUtil"/>
<jsp:useBean id="alumnoU" scope="page" class="aca.musica.MusiAlumnoUtil"/>
<jsp:useBean id="calculoDetU" scope="page" class="aca.musica.MusiCalculoDetUtil"/>

<script type="text/javascript" src="../../js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
	
	function recarga(){
		document.forma.submit();
	}	
	  
	$(document).ready(function() {  
	     $(".botonExcel").click(function(event) {  
	     	$("#datos_a_enviar").val( $("<div>").append( $("#Exportar_a_Excel").eq(0).clone()).html());  
	     	$("#FormularioExportacion").submit();  
		});  
	});	
</script>
<link rel="stylesheet" href="../../css/style.css" />
<style>
	.tinytable .head:hover, .tinytable .desc:hover, .tinytable .asc:hover {background: <%=colorTablas%>;}
</style>
<% 
	String codigoId			= (String) session.getAttribute("CodigoId");

	String periodoId 		= request.getParameter("PeriodoId");	
	if (periodoId==null || periodoId.equals("null") || periodoId.equals("")){
		periodoId 			= aca.musica.MusiPeriodo.getPeriodoActual(conEnoc);
	}
	String bgColor			= "";

	ArrayList lisPeriodos	= new ArrayList();
	lisPeriodos				= PeriodoU.getListAll(conEnoc,"ORDER BY PERIODO_ID");
	
	ArrayList lisInscritos	= new ArrayList();
	lisInscritos			= alumnoU.getListInscrito(conEnoc, periodoId, " ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE ");
	
	int cont = 0;
		

%> 
<div class="container-fluid">
<h1>Alumnos Inscritos</h1>
<div class="alert alert-info">
	<a href="opcion" class="btn btn-primary">Regresar</a>&nbsp;&nbsp;
	<form action="inscrito" name="forma">
		<b>Seleccione el Periodo:</b>
          <select onchange='javascript:recarga()' name="PeriodoId">
<%
	for (int i=0; i< lisPeriodos.size(); i++){
		aca.musica.MusiPeriodo periodo = (aca.musica.MusiPeriodo) lisPeriodos.get(i);
%>
			<option <%if( periodoId.equals(periodo.getPeriodoId() ))out.print(" Selected ");%> value="<%= periodo.getPeriodoId() %>">[<%= periodo.getPeriodoId() %>] <%= periodo.getPeriodoNombre() %></option>
<%
	}
%>
          </select>
    </form>
</div>

<form action="../../excel" method="post" id="FormularioExportacion">
  <input type="hidden" id="datos_a_enviar" name="datos_a_enviar" />
  <input type="hidden" id="archivo" name="archivo" value="AlumnosInscritos.xls"/>
  <table style="width:90%" >
    <tr><td><a href="#"><img src="../../imagenes/excel-chico.png" width="30px" class="botonExcel" onmouseover="this.src='../../imagenes/excel-chico-over.png'" onmouseout="this.src='../../imagenes/excel-chico.png'"/></a></td></tr>    
  </table>
</form>

<table class="tabla" width="90%" id="Exportar_a_Excel">
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
			    <th width="4%"><h3><spring:message code="aca.Numero"/></h3></th>
			    <th width="7%"><h3><spring:message code="aca.Matricula"/></h3></th>
			    <th width="20%"><h3><spring:message code="aca.Nombre"/> </h3></th>
			    <th width="20%"><h3><spring:message code="aca.Paterno"/> </h3></th>
			    <th width="20%"><h3><spring:message code="aca.Materno"/> </h3></th>
			    <th width="10%"><h3>F.Nac.</h3></th>
			    <th width="4%"><h3><spring:message code="aca.Edad"/></h3></th>
			    <th width="7%"><h3><spring:message code="aca.Seguro"/></h3></th>
			    <th width="7%"><h3><spring:message code="aca.Telefono"/></h3></th>
			    <th width="7%"><h3><spring:message code="aca.Instrumento"/></h3></th>
			    <th width="30%"><h3><spring:message code="aca.Maestro"/></h3></th>
			   
			  </tr>
		</thead>
		<tbody>
			<%
			
			for (int j=0; j<lisInscritos.size(); j++){cont++;
				aca.musica.MusiAlumno alumno = (aca.musica.MusiAlumno) lisInscritos.get(j);
				if(j%2 == 0)
					bgColor = "bgcolor='#CCCCCC'";
				else
					bgColor = "";
				String CodigoId = alumno.getCodigoId();
				String telefono = alumno.getTelefono();
				if(telefono==null){
					telefono = "-";
				}else if(telefono.equals("null")){
					telefono = "-";
				}
				String instrumento = "0";
					//aca.musica.MusiInstrumento.getNombreInstrumento(conEnoc, aca.musica.MusiCalculoDet.getInstrumentoAlumno(conEnoc, CodigoId, periodoId));
				if(instrumento.equals("null") || instrumento.equals(null))instrumento="-";
				String maestro = "0";
				
				ArrayList lista = calculoDetU.getListAsignados(conEnoc, CodigoId, periodoId, "ORDER BY 1");
				for (int k=0; k<lista.size(); k++){
					aca.musica.MusiCalculoDet alum = (aca.musica.MusiCalculoDet) lista.get(k);
					if(!alum.getMaestro().equals("0"))maestro=alum.getMaestro();
					//System.out.println(alum.getInstrumentoId());
					if(alum.getInstrumentoId()!=null)
						if(!alum.getInstrumentoId().equals("0"))instrumento=alum.getInstrumentoId();
				}
				instrumento = aca.musica.MusiInstrumento.getNombreInstrumento(conEnoc, instrumento);
				maestro = aca.musica.MusiMaestro.getNombre(conEnoc, maestro, "NOMBRE");
			%>
			
			  <tr class="tr2" <%=bgColor%>> 
			    <td align="center"><font size="1"><%=cont%></font></td>
			    <td align="center"><font size="1"><%=CodigoId%></font></td> 
			    <td align="left"><font size="1"><%=alumno.getNombre()%></font></td>			    
			    <td align="left"><font size="1"><%=alumno.getApellidoPaterno()%></font></td>
			    <td align="left"><font size="1"><%=alumno.getApellidoMaterno()%></font></td>
			    <td align="center"><font size="1"><%=alumno.getFechaNac()%></font></td>
			    <td align="center"><font size="1"><%=alumno.getEdad(conEnoc) %></font></td> 
			    <td align="center"><font size="1"><%=alumno.getSeguro()%></font></td>
			    <td align="center"><font size="1"><%=telefono %></font></td>    
			    <td align="center"><font size="1"><%=instrumento  %></font></td>    
			    <td align="left"><font size="1"><%=maestro %></font></td>        
			  </tr>
			<% 
			}
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
                        <option value="10" >10</option>
                        <option value="20" >20</option>
                        <option value="50">50</option>
                        <option value="100" selected="selected">100</option>
                    </select>
                    <span><spring:message code="aca.EntradasPagina"/></span>
                </div>
                <div class="page"><spring:message code="aca.Pagina"/> <span id="currentpage"></span> <spring:message code="aca.De"/> <span id="totalpages"></span></div>
            </div>
        </div>
	 </td>
  </tr>
</table>
</form>
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
	size:100,
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
<%@ include file = "../../cierra_enoc.jsp"%>