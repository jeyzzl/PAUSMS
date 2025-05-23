<%@page import="aca.plan.MapaCurso"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="GrupoPlanU" scope="page" class="aca.carga.CargaGrupoPlanUtil"/>
<script type="text/javascript">
	function Carga(){
		document.frmcursos.Accion.value="2";
		document.frmcursos.submit();
	}
	
</script>
<%
	String cursoCargaId 	= "";
	String maestro 		    = "";
	String materia 		    = "";
	if (request.getParameter("CursoCargaId")!=null){
		session.setAttribute("CursoCargaId", request.getParameter("CursoCargaId"));
		session.setAttribute("Maestro", request.getParameter("Maestro"));
		session.setAttribute("Materia", request.getParameter("Materia"));
	}
	
	String sAccion 			= request.getParameter("Accion")==null?"":request.getParameter("Accion");	
	String sCargaId 		= (String) session.getAttribute("cargaId");
	
	ArrayList lisCarga		= new ArrayList();

	int cont = 0;
	String bgColor			= "";
	
	if(sAccion.equals("2")){
		sCargaId = request.getParameter("CargaId");
		session.setAttribute("cargaId", sCargaId );	
	}
	
	ArrayList lisPlan = new ArrayList();
	lisPlan = GrupoPlanU.getListPlanes(conEnoc, sCargaId,"ORDER BY 1");
%>
<div class="container-fluid">
<h1>Lista de Planes de Curso</h1>
<form action="lista" method="post" name="frmcursos">
<div class="alert alert-info">
	Carga: 
      <select name="CargaId" id="CargaId" onChange="Carga()">
<%
		aca.carga.CargaUtil cargaU = new aca.carga.CargaUtil();
		lisCarga = cargaU.getListPlanCurso(conEnoc, "ORDER BY 1");

		for(int i=0;i<lisCarga.size();i++){
			aca.carga.Carga carga = (aca.carga.Carga) lisCarga.get(i);
			if ( lisCarga.size()==1 ){	sCargaId = carga.getCargaId(); 	}
			if (carga.getCargaId().equals(sCargaId)){
				out.print(" <option value='"+carga.getCargaId()+"' Selected>"+ carga.getNombreCarga()+"</option>");
			}else{
				out.print(" <option value='"+carga.getCargaId()+"'>"+ carga.getNombreCarga()+"</option>");
			}				
		}
		lisCarga 	= null;				
		cargaU		= null;
	  %>
      </select>
</div>
  <input type="hidden" name="Accion">
  <table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
  <tr>
	<th align="center" width="3%"><spring:message code="aca.Numero"/></th>
    <th align="left" width="25%"><spring:message code="aca.Facultad"/></th>
    <th align="left" width="25%"><spring:message code="aca.Plan"/></th>
    <th align="left" width="25%"><spring:message code="aca.Materia"/></th>
    <th align="left" width="30%"><spring:message code="aca.Maestro"/></th>
    <th align="left" width="5%">Link</th>
  </tr>
  </thead>
  <% 
  	  for (int i=0; i<lisPlan.size(); i++) {cont++;
	   aca.carga.CargaGrupoPlan plan = ( aca.carga.CargaGrupoPlan) lisPlan.get(i);	
	   
	   if(i%2 == 0){ bgColor = "bgcolor='#CCCCCC'"; }else{ bgColor = "";}
	   
		/**** Obtiene los datos del curso en la tabla Mapa_Nuevo_Curso*/
	
  %>

  <tr class="tr2" <%=bgColor%>>
	<td align="center"><%= cont %></td>  
	<td align="left"><%= aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, aca.carga.CargaGrupoUtil.getCarreraId(conEnoc, plan.getCursoCargaId())))%></td>  
	<td align="left"><%= aca.plan.PlanUtil.getNombrePlan(conEnoc, aca.plan.CursoUtil.getPlanId(conEnoc, aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conEnoc, plan.getCursoCargaId())))%></td>  	
  	<td align="left"><%=aca.plan.CursoUtil.getMateria(conEnoc, aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conEnoc, plan.getCursoCargaId()))%></td>
  	<td align="left"><%= aca.vista.MaestrosUtil.getNombreMaestro(conEnoc,aca.carga.CargaGrupoUtil.getCodigoPersonal(conEnoc, plan.getCursoCargaId()), "NOMBRE")%></td>
  	<td align="center"><a class="btn btn-primary" href="../evalua/planPDF?CursoCargaId=<%=plan.getCursoCargaId()%>&Maestro=<%=aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, aca.carga.CargaGrupoUtil.getCodigoPersonal(conEnoc, plan.getCursoCargaId()), "NOMBRE")%>&Materia=<%=aca.plan.CursoUtil.getMateria(conEnoc, aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conEnoc, plan.getCursoCargaId()))%>">PDF</a></td>
  </tr>	

<%    } %>  
  </table>
  </form>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>