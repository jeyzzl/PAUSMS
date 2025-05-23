<%@ page import="java.util.HashMap"%>
<%@ page import= "java.util.ArrayList"%>
<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "aca.plan.MapaCurso"%>
<%@page import="aca.plan.MapaPlan"%>
<%@page import="aca.plan.MapaCursoElec"%>
<jsp:useBean id="MapaCursoU" class="aca.plan.CursoUtil" scope="page"/>
<jsp:useBean id="cursoElec"  scope="page" class="aca.plan.MapaCursoElec"/>
<jsp:useBean id="cursoElecU"  scope="page" class="aca.plan.ElectivaUtil"/>
<jsp:useBean id="optativa"  scope="page" class="aca.plan.MapaOptativa"/>
<jsp:useBean id="optativaU"  scope="page" class="aca.plan.OptativaUtil"/>
<jsp:useBean id="planUtil"  class="aca.plan.PlanUtil" scope="page"/>
<jsp:useBean id="TipoCursoUtil"  class="aca.catalogo.TipoCursoUtil" scope="page"/>

<script type="text/javascript">
	function Semestre(){
		document.opta.Accion.value = "1";
		document.opta.submit();
	}

	function Grabar(){
		document.opta.Accion.value = "2";
		document.opta.submit();
	}
	
</script>

<% 	String sCursoId 	= request.getParameter("Curso");
	String sPlanId		= request.getParameter("Plan");
	String sSem			= request.getParameter("Semestre");
	String sAccion		= request.getParameter("Accion")==null?"0":request.getParameter("Accion"); 
	String sCic			= request.getParameter("semestres")==null?"0":request.getParameter("semestres");
	
	String sResultado 	= "";
	String sValCheck	= "";
	String sCheck		= "";
	String sOptativa	= "";
	String sCiclo		= "X";
	String sMat			= "";
	int  nCiclo			= 0;	
	
	// Lista de materias
	ArrayList<aca.plan.MapaCurso> lisCurso	= MapaCursoU.getLista(conEnoc,sPlanId,"ORDER BY CICLO, NOMBRE_CURSO"); 
		
	
	if(sAccion.equals("2")){	
		for(int i=0; i<lisCurso.size(); i++){
			if(request.getParameter("check"+i)==null) 
				sCheck ="X"; 
			else 
				sCheck = request.getParameter("check"+i);
			if(sCheck != "X"){			
				aca.plan.MapaCurso mcurso = (aca.plan.MapaCurso) lisCurso.get(i);
				cursoElec.setCursoId(sCursoId);
				cursoElec.setCursoElec(mcurso.getCursoId());		
				cursoElec.setFolio(cursoElecU.maximoReg(conEnoc, sCursoId));
				
				if(cursoElecU.existeReg(conEnoc, sCursoId, mcurso.getCursoId() ) == false){
					if(cursoElecU.insertReg(conEnoc, cursoElec)){
						System.out.println("Los Datos fueron grabados Exitosamente!");						
					}else{
						System.out.println("No grabo...");
					}				
				}else{
					System.out.println("Ya Existe...");
				}
			}
		}
	}
	
	ArrayList lisPlan	= planUtil.getListAll(conEnoc, " ORDER BY 3");
	
	HashMap<String, aca.catalogo.CatTipoCurso> mapaTipoCurso = TipoCursoUtil.getMapAll(conEnoc, "");
%>
<div class="container-fluid">
	<h2>Elective Subjects
		<small class="text-muted fs-4">(<strong>Sub. Id:</strong>&nbsp;<%=sCursoId%>- <strong>Sub.:</strong>&nbsp;<%=aca.plan.CursoUtil.getMateria(conEnoc, sPlanId, sCursoId)%> - <strong>Cycle:</strong>&nbsp;<%=request.getParameter("Semestre")%> )</small>
	</h2>
	<div class="alert alert-info d-flex">	
		<a class="btn btn-primary" href="optativa?Plan=<%=sPlanId%>&Semestre=<%=sSem%>&Curso=<%=sCursoId%>"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp; 
		<select class="form-select" style="width:40rem" name="planId" onchange="javascript:document.location.href='agregar?Semestre=<%=sSem%>&Curso=<%=sCursoId%>&Plan='+encodeURIComponent(this.options[this.selectedIndex].value);">
<%						
	for(int i=0;i<lisPlan.size();i++){				
		MapaPlan plan = (MapaPlan) lisPlan.get(i);			
%>	    	
	    		<option value="<%=plan.getPlanId()%>" <% if(plan.getPlanId().equals(sPlanId)) out.print("selected");%> ><%=plan.getNombrePlan() %></option>
<%	} %>
	    </select>
	</div>   
	<br>
	<form name="opta" method="post" action="agregar?Curso=<%=sCursoId%>&Plan=<%=sPlanId%>&Semestre=<%=sSem%>">
	<input name="Accion" type="hidden" value = "1">  	
  	<table style="width:65%" class="table table-condensed table-bordered table-striped">
    <tr> 
      <th width="10%"><spring:message code="aca.Numero"/></th>
      <th width="10%"><spring:message code="aca.Ciclo"/></th>
      <th width="55%"><spring:message code="aca.Materia"/></th>
      <th width="10%"><spring:message code="aca.TipoCurso"/></th>
      <th width="35%"><spring:message code="aca.Datos"/></th>
    </tr>
    <tr>    
<%		// for que te acomoda las materias que son optativas
	for(int i=0; i<lisCurso.size(); i++){
		aca.plan.MapaCurso mcurso = (aca.plan.MapaCurso) lisCurso.get(i);
		cursoElec.setCursoId(sCursoId);
		cursoElec.setCursoElec(mcurso.getCursoId());
		if (cursoElecU.existeReg(conEnoc, sCursoId, mcurso.getCursoId())==true){	
			sValCheck= "S"; 
			sCheck = "checked";
		}else{
			sValCheck= "N"; 
			sCheck=""; 
		}	
%>  
	  <td><%=i+1%> - &nbsp;&nbsp;<input name="check<%=i%>" type="checkbox" value="<%=sValCheck%>" <%=sCheck%>> </td>
	  <td><%=mcurso.getCiclo()%></td>
	  <td><%=mcurso.getNombreCurso()%></td>
	  <td><%=mapaTipoCurso.get(mcurso.getTipoCursoId()).getNombreTipoCurso()%></td>
	  <td><%=mcurso.getCreditos()%></td>
   </tr>
<% }%>
    <tr><td colspan="5">&nbsp;</td></tr>
    <tr> 
      <td colspan="5"><div align="center"><a class="btn btn-primary" href="javascript:Grabar()"><strong><spring:message code="aca.Grabar"/></strong></a></div></div></td>
    </tr>
	</table>
	</form>
</div>	
<%@ include file= "../../cierra_enoc.jsp"%>