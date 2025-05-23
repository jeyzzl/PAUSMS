<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.plan.MapaCurso"%>
<%@page import="aca.plan.MapaCursoPre"%>
<%@page import="aca.vista.CargaAcademica"%>
<%@page import="aca.vista.AlumnoCurso"%>
<%@page import="aca.conva.ConvMateria"%>
<%@page import="aca.alumno.AlumPlan"%>

<jsp:useBean id="alumPersonal" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="alumPlan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="AlumPlanU" scope="page" class="aca.alumno.PlanUtil"/>
<jsp:useBean id="alumAcademico" scope="page" class="aca.alumno.AlumAcademico"/>
<jsp:useBean id="AcademicoU" scope="page" class="aca.alumno.AcademicoUtil"/>
<jsp:useBean id="carga" scope="page" class="aca.carga.Carga"/>
<jsp:useBean id="CargaU" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="alumU" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="cursoUtil" scope="page" class="aca.plan.CursoUtil"/>
<jsp:useBean id="mapaCurso" scope="page" class="aca.plan.MapaCurso"/>
<jsp:useBean id="alumnoCursoU" scope="page" class="aca.vista.AlumnoCursoUtil"/>
<jsp:useBean id="alumnoCurso" scope="page" class="aca.vista.AlumnoCurso"/>
<jsp:useBean id="prerrequisitoU" scope="page" class="aca.plan.PrerrequisitoUtil"/>
<jsp:useBean id="mapaCursoPre" scope="page" class="aca.plan.MapaCursoPre"/>
<jsp:useBean id="convMateriaU" scope="page" class="aca.conva.ConvMateriaUtil"/>
<jsp:useBean id="convMateria" scope="page" class="aca.conva.ConvMateria"/>
<jsp:useBean id="cargaPermiso" scope="page" class="aca.carga.CargaPermiso"/>
<link rel="stylesheet" href="../../css/style.css" />
<%
	//if(request.getParameter("Matricula")!=null) session.setAttribute("codigoAlumno", request.getParameter("Matricula"));
	String codigoAlumno = (String)session.getAttribute("codigoAlumno");
	String cargaId		= request.getParameter("carga");
	String pagina 		= request.getParameter("pag");
	
	alumPersonal = AlumUtil.mapeaRegId(conEnoc, codigoAlumno);
	alumPlan.mapeaRegId(conEnoc, codigoAlumno);
	alumAcademico = AcademicoU.mapeaRegId(conEnoc, codigoAlumno);
	carga = CargaU.mapeaRegId(conEnoc, cargaId);
	cargaPermiso.mapeaRegId(conEnoc, cargaId, AlumPlanU.getCarreraId(conEnoc, codigoAlumno));
	
	ArrayList<aca.plan.MapaCurso> lisCursos 				= cursoUtil.getLista(conEnoc, alumPlan.getPlanId(), "ORDER BY CICLO, NOMBRE_CURSO");
	ArrayList<aca.vista.AlumnoCurso> lisCursosAlumno		= alumnoCursoU.getListAlumno(conEnoc, codigoAlumno," AND PLAN_ID='"+alumPlan.getPlanId()+"' AND TIPOCAL_ID != '2' ORDER BY CICLO, NOMBRE_CURSO");
	ArrayList<aca.plan.MapaCursoPre> lisPrerrequisitos 		= prerrequisitoU.getLista(conEnoc, alumPlan.getPlanId(), "ORDER BY CURSO_ID, CURSO_ID_PRE");
	ArrayList<aca.conva.ConvMateria> lisConvalidaciones		= convMateriaU.getListAlumno(conEnoc, codigoAlumno, alumPlan.getPlanId(), "ORDER BY CURSO_ID");
%>
<div class="container-fluid">
	<h1><spring:message code="aca.CargaAcademica"/></h1>
	<div class="alert alert-info">
		Alumno: [<%=codigoAlumno %>] - <%=alumPersonal.getNombreLegal() %> <br/>
		[<%=alumU.getCarrera(conEnoc, codigoAlumno)%>] [Plan: <%=alumPlan.getPlanId() %>] [ <%=alumAcademico.getModalidad() %> ]
	</div>
<form id="forma" name="forma">
	<table>
		<tr>
			<th align="center"><font size="2"> <%=carga.getNombreCarga() %> <%=cargaPermiso.getRecuperacion().equals("S")?"- <font color=red>Recuperaci&oacute;n</font>":"" %></font></th>
		</tr>
		<tr>
			<td align="center">
				<font color="#000066" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
					</strong>
	        	</font>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center">
				<table class="tabla">
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
					 <table id="table" class="table table-sm table-bordered">
						<thead class="table-info">	 
							<tr>
								<th><h3><spring:message code="aca.Clave"/></h3></th>
								<th><h3><%=alumU.getCarreraId(conEnoc, alumPlan.getPlanId()).substring(0,3).equals("107")?"Tetra":"Sem." %></h3></th>
								<th><h3><spring:message code="aca.Materia"/></h3></th>
								<th><h3>Cr.</h3></th>
								<th class="ayuda <%=idJsp %> 002"><h3><spring:message code="aca.Estado"/></h3></th>
								<th class="ayuda <%=idJsp %> 004"><h3>Asignadas</h3></th>
								<th class="ayuda <%=idJsp %> 005"><h3>Convalidacion</h3></th>
								<th class="ayuda <%=idJsp %> 006"><h3>Prerre.</h3></th>
								<th class="ayuda <%=idJsp %> 007"><h3>Ver Pre.</h3></th>
							</tr>
							</thead>
							<tbody>
<%
	String ciclo = "";
	boolean cursado;
	for(int i = 0; i < lisCursos.size(); i++){
		mapaCurso = (MapaCurso) lisCursos.get(i);
			ciclo = mapaCurso.getCiclo();
%>
					
<%
		
//---------------------- Para los prerrequisitos ----------------------
		boolean cumplePrerrequisitos = true;
		boolean tienePrerrequisitos = false;
		String prerrequisitos = "";
		for(int j = 0; j < lisPrerrequisitos.size(); j++){
			mapaCursoPre = (MapaCursoPre) lisPrerrequisitos.get(j);
			if(mapaCurso.getCursoId().equals(mapaCursoPre.getCursoId())){
				tienePrerrequisitos = true;
				prerrequisitos += "> "+aca.plan.CursoUtil.getMateria(conEnoc, mapaCursoPre.getCursoIdPre()) + "  \n";
				boolean cursoLaMateria = false;
				for(int k = 0; k < lisCursosAlumno.size(); k++){
					alumnoCurso = (aca.vista.AlumnoCurso) lisCursosAlumno.get(k);
					if(mapaCursoPre.getCursoIdPre().equals(alumnoCurso.getCursoId())){
						cursoLaMateria = true;
						if(Float.parseFloat(alumnoCurso.getNota()) < Float.parseFloat(alumnoCurso.getNotaAprobatoria()) && Float.parseFloat(alumnoCurso.getNotaExtra()) < Float.parseFloat(alumnoCurso.getNotaAprobatoria())){
							cumplePrerrequisitos &= false;
						}
					}
				}
				if(!cursoLaMateria){
					cumplePrerrequisitos &= false;
				}
			}
		}
		if(!tienePrerrequisitos)
			prerrequisitos = "No tiene prerrequisitos";
//---------------------------------------------------------------------
		cursado = false;
		alumnoCurso = new AlumnoCurso();
		for(int j = 0; j < lisCursosAlumno.size(); j++){//Ciclo que ubica el curso con el mismo ya cursado por el alumno
			alumnoCurso = (aca.vista.AlumnoCurso) lisCursosAlumno.get(j);
			if(mapaCurso.getCursoId().equals(alumnoCurso.getCursoId())){
				j = lisCursosAlumno.size();
				cursado = true;
			}
		}
		boolean convalidada = false;
		for(int j = 0; j < lisConvalidaciones.size(); j++){
			convMateria = (ConvMateria) lisConvalidaciones.get(j);
			if(mapaCurso.getCursoId().equals(convMateria.getCursoId())){
				j = lisConvalidaciones.size();
				convalidada = true;
			}
		}
		if(cursado){
%>
					<tr>
						<td><%=mapaCurso.getCursoId() %></td>
						<td align="center"><%=ciclo %></td>
						<td><%=mapaCurso.getNombreCurso() %></td>
						<td><%= mapaCurso.getCreditos() %></td>
						<td align="center"><%=alumnoCurso.getTipoCalId().equals("1")?(((Float.parseFloat(alumnoCurso.getNota()) >= Float.parseFloat(alumnoCurso.getNotaAprobatoria()))?alumnoCurso.getNota():alumnoCurso.getNotaExtra())+"-AC"):alumnoCurso.getTipoCalId() %></td>
						<td align="center">
<%
			if(alumnoCurso.getTipoCalId().equals("M")){
%>
							<img src="../../imagenes/check.png" width="15px" />
<%
			}else{
				out.println("&nbsp;");
			}
%>
						</td>
						<td align="center">
<%
			if(convalidada){
%>
							<img src="../../imagenes/check.png" width="15px" />
<%
			}else{
				out.println("&nbsp;");
			}
%>
						</td>
						<td align="center"><%if(cumplePrerrequisitos){%><img src="../../imagenes/check.png" width="15px" /><%} %></td>
						<td align="center"><a class="ayuda mensaje <%=prerrequisitos %>" style="cursor:pointer;">Ver</a></td>
					</tr>
<%
		}else{
%>
					<tr>
						<td><%=mapaCurso.getCursoId() %></td>
						<td align="center"><%=ciclo %></td>
						<td><%=mapaCurso.getNombreCurso() %></td>
						<td><%= mapaCurso.getCreditos() %></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td align="center">
<%
			if(convalidada){
%>
							<img src="../../imagenes/check.png" width="15px" />
<%
			}else{
				out.println("&nbsp;");
			}
%>
						</td>
						
						<td align="center"><%if(cumplePrerrequisitos){%><img src="../../imagenes/check.png" width="15px" /><%} %></td>
						<td align="center"><a class="ayuda mensaje <%=prerrequisitos %>" style="cursor:pointer;">Ver</a></td>
					</tr>
<%
		}
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
	sortcolumn:1,
	sortdir:1,
	init:true
});
 </script>
<%@ include file= "../../cierra_enoc.jsp" %>