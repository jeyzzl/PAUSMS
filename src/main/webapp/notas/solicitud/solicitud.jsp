<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CalU" scope="page" class="aca.kardex.KrdxCursoCalUtil"/>
<jsp:useBean id="CursoAct" scope="page" class="aca.kardex.KrdxCursoAct"/>
<jsp:useBean id="Cal" scope="page" class="aca.kardex.KrdxCursoCal"/>
<% 
	String cargaId 			= request.getParameter("CargaId");
	String codigoPersonal 	= request.getParameter("codigoPersonal");
	String cursoCargaId 	= request.getParameter("cursoCargaId");
	String cursoId		 	= request.getParameter("cursoId");
	String estado		 	= request.getParameter("estado")==null?"":request.getParameter("estado");
	String sAccion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");


	ArrayList lisCargas 	= null;
	String codigo			= "";
	String resultado		= "";
	
	if(sAccion.equals("1")){
		Cal.mapeaRegId(conEnoc, codigoPersonal,cursoCargaId, cursoId);
		conEnoc.setAutoCommit(false);
		
		if(Cal.getEstado().equals("S")){
			Cal.setEstado("A");
    	}else if(Cal.getEstado().equals("A")){
    		Cal.setEstado("S");
    	}
		
		if(Cal.updateReg(conEnoc)){
			resultado = "La correcion de notas ha sido Autorizada";
			conEnoc.commit();
		}else{
			resultado = "Ocurrio un error";
			conEnoc.rollback();
		}
		conEnoc.setAutoCommit(true);
	}
	
	lisCargas 	= CalU.getListAll(conEnoc, " WHERE TIPO = 'C' AND SUBSTR(CURSO_CARGA_ID,1,6)= '"+cargaId+"' ORDER BY CURSO_CARGA_ID");
	//Cal.mapeaRegId(conEnoc, codigoPersonal,cursoCargaId, cursoId);
	
%>
<body>
<div class="container-fluid">
<div class="alert alert-info">
	<a class="btn btn-primary" href="cargas"><i class="fas fa-arrow-left"></i></a>
</div>
<% for(int i=0; i<lisCargas.size();i++){
		aca.kardex.KrdxCursoCal cal = (aca.kardex.KrdxCursoCal) lisCargas.get(i);
		CursoAct.mapeaRegId(conEnoc, cal.getCodigoPersonal(),cal.getCursoCargaId());
%>
<form name="forma" action="solicitud" method='post' id="noayuda">
<table id="table" class="table table-sm table-bordered">
<thead>	 
<% 		
			if(!codigo.equals(cal.getCodigoPersonal())){
%>

  <tr><td colspan="6"><b><%= aca.alumno.AlumUtil.getNombreAlumno(conEnoc,cal.getCodigoPersonal(),"NOMBRE") %></b></td></tr>
</thead>
<thead class="table-info">	 
  <tr>
     <th width="20%"><spring:message code="aca.Materia"/></th>
    <th width="5%">Tipo Calif.</th>
    <th width="5%">Calif Ant.</th>
    <th width="5%">Calif</th>
    <th width="5%">Tipo Nota</th>
    <th width="5%"><spring:message code="aca.Fecha"/></th>
    <th width="10%"><spring:message code="aca.Estado"/></th>
  </tr>
</thead>
  <tr>
  	<td align="left"><%= aca.plan.CursoUtil.getMateria(conEnoc,cal.getCursoId())%></td>
    <td align="center"><%= aca.catalogo.TipoCalUtil.getNombreCorto(conEnoc, cal.getTipoCalId())%></td>
    <td align="center"><%= CursoAct.getNota() %></td>
    <td align="center"><%= cal.getNota()%></td>
    <td align="left"><% if(cal.getTipoNota().equals("O")){ out.print("Ordinario");}else{out.print("Extra"); }  %></td>
    <td align="center"><%= cal.getFecha() %></td>
    <td align="center">
      <a href="solicitud?codigoPersonal=<%=cal.getCodigoPersonal()%>&cursoCargaId=<%= cal.getCursoCargaId()%>&cursoId=<%= cal.getCursoId()%>&CargaId=<%=cargaId%>&Accion=1"><%= cal.getEstado() %></a>
	</td>
  </tr>  
<% 
			} 			
%>	
</table>
</form>
<% 
	} %>
	</div>	
</body>

<%@ include file= "../../cierra_enoc.jsp" %>