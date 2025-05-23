<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.vista.spring.Estadistica"%>
<%@ page import= "aca.vista.spring.AlumnoCurso"%>
<%@ page import= "aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script>
	function grabaPeriodo(periodoId){
		document.location.href="listado?cambioPeriodo=1&PeriodoId="+periodoId;
	}	
	function grabaCarga(cargaId){
 		document.location.href="listado?cambioCarga=1&CargaId="+cargaId;
	}	
</script>

<link rel="stylesheet" href="../../css/style.css" />
<!-- inicio de estructura -->
<%
	String codigoUsuario	= (String) session.getAttribute("codigoPersonal");

	String periodoId 		= (String)session.getAttribute("periodo");
	String cargaId 			= (String)session.getAttribute("cargaId");
	
	String cambioPeriodo 	= request.getParameter("cambioPeriodo")==null?"0":request.getParameter("cambioPeriodo");
	String cambioCarga 		= request.getParameter("cambioCarga")==null?"0":request.getParameter("cambioCarga");
	
	if (cambioPeriodo.equals("1")){
		periodoId = request.getParameter("PeriodoId");
		session.setAttribute("periodo", periodoId);
	}
	if (cambioCarga.equals("1")){
		cargaId = request.getParameter("CargaId");
		session.setAttribute("cargaId", cargaId);
	}
	
	Acceso acceso 								= (Acceso) request.getAttribute("acceso");
	List<CatPeriodo> lisPeriodos 				= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<Carga> lisCargas						= (List<Carga>) request.getAttribute("lisCargas");   	
	List<Estadistica> lisReprobados	 			= (List<Estadistica>) request.getAttribute("lisReprobados");   	
	List<AlumnoCurso> lisCursos	 				= (List<AlumnoCurso>) request.getAttribute("lisCursos");
	
	HashMap<String,CatFacultad> mapaFacultades	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	
	String codigoAlumno		= "X";	
	String facultad			= "X";	
	String carreraTemp		= "X";
	String sBgcolor			= "";
	int cont = 0; 
%>
<div class="container-fluid">
	<h1>Alumnos con Materias reprobadas</h1>
	<form id="noayuda" name="forma" action="listado.jsp" method='post'>
	<div class="alert alert-info">
		<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<spring:message code="aca.Periodo"/>: 
		<select onchange="javascript:grabaPeriodo(this.value);" name="PeriodoId" class="input input-medium">
<%		for(CatPeriodo periodo : lisPeriodos){ %>
			<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%		}%>
		</select>
		Carga: [ <%=cargaId%> ] 
    	<select name="CargaId" onchange="javascript:grabaCarga(this.value);" style="width:350px;">
<%		
		for( Carga carga: lisCargas){			
%>			
			<option value="<%=carga.getCargaId()%>" <%=cargaId.equals(carga.getCargaId())?"selected":""%>>
				<%=carga.getCargaId()%> - <%=carga.getNombreCarga()%>
			</option>
<%		}
%>		</select>		
	</div>
	</form>
	<table style="width:100%">
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
		 	<table class="table table-bordered">
			<thead class="table-info">
				  <tr> 
				    <th><h3>#</h3></th>
				    <th><h3><spring:message code="aca.Facultad"/></h3></th>
				    <th><h3><spring:message code="aca.Carrera"/></h3></th>
				    <th><h3><spring:message code="aca.Matricula"/></h3></th>
				    <th><h3><spring:message code="aca.Nombre"/></h3></th>
				    <th><h3>INS</h3></th>
				    <th><h3>AC</h3></th>
				    <th><h3>CD</h3></th>
				    <th><h3>BA</h3></th>
				    <th><h3>NA.OR.</h3></th>
				    <th><h3>EX</h3></th>
				    <th><h3>NA</h3></th>
				  </tr>
			</thead>
			<tbody>
<%
		
	java.util.Iterator iter = lisReprobados.iterator();
	while(iter.hasNext()){
		Estadistica inscrito = (Estadistica)iter.next();
		cont++;
		
		int numCursos = 0;
		int AC 		  = 0;
		int BA 		  = 0;
		int NA 		  = 0;
		int CD 		  = 0;
		int EXTRA 	  = 0;
		int NAOR 	  = 0;
		
		codigoAlumno = inscrito.getCodigoPersonal();
		
		String facultadNombre = "-";
		if (mapaFacultades.containsKey(inscrito.getFacultadId())){
			facultadNombre = mapaFacultades.get(inscrito.getFacultadId()).getNombreFacultad();
		}
		
		String carreraNombre = "-";
		if (mapaCarreras.containsKey(inscrito.getCarreraId())){
			carreraNombre = mapaCarreras.get(inscrito.getCarreraId()).getNombreCarrera();
		}
				
		if( (acceso.getAccesos().indexOf(inscrito.getCarreraId()) != -1) || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){
			//CONTADORES
			
			for  (int j=0; j<lisCursos.size(); j++){
				AlumnoCurso alumnoCurso = (AlumnoCurso) lisCursos.get(j);
				if ( alumnoCurso.getCodigoPersonal().equals(inscrito.getCodigoPersonal()) ){
					numCursos++;
					if(alumnoCurso.getTipoCalId().equals("1")){
						AC++;
						if(!alumnoCurso.getNotaExtra().equals("0")){//SE FUE A EXTRAORDINARIO
							NAOR++;
						}
					}else if(alumnoCurso.getTipoCalId().equals("3")){
						BA++;
					}else if(alumnoCurso.getTipoCalId().equals("2") || alumnoCurso.getTipoCalId().equals("4")){
						NA++;
						NAOR++;
					}else if(alumnoCurso.getTipoCalId().equals("5") || alumnoCurso.getTipoCalId().equals("6")){
						CD++;
					}
					
					if(!alumnoCurso.getNotaExtra().equals("0")){//SE FUE A EXTRAORDINARIO
						EXTRA++;
					}	
				}
			}				
%>				
				  <tr> 
					<td width="4%" height="21"><%=cont%></td>
					<td><%=facultadNombre%></td>
					<td><%=carreraNombre%></td>
					<td><%=codigoAlumno %></td>
					<td><%=inscrito.getApellidoPaterno()+" "+inscrito.getApellidoMaterno()%> <%=inscrito.getNombre() %></td>
					<td><%=numCursos %></td>
					<td><%=AC %></td>
					<td><%=CD %></td>
					<td><%=BA %></td>
					<td><%=NAOR %></td>
					<td><%=EXTRA %></td>
					<td><%=NA %></td>
				  </tr> 
<%				
		} // fin de if esta autorizado a ver la carrera 
	}// fin del while		
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
                	<a href="javascript:sorter.showall()">Mostrar todos los registros</a>
                </div>
            </div>
			<div id="tablelocation" style="position:relative;right:11px;">
            	<div>
                    <select onchange="sorter.size(this.value)">
                    <option value="5">5</option>
                        <option value="10" >10</option>
                        <option value="20">20</option>
                        <option value="50" selected>50</option>
                        <option value="100">100</option>
                    </select>
                    <span>Entradas Por Pagina</span>
                </div>
                <div class="page"><spring:message code='aca.Pagina'/><span id="currentpage"></span> de <span id="totalpages"></span></div>
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
	sortdir:0,
	init:true
});
 </script>