<%@ page import= "aca.kardex.CursosImportados"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>

<jsp:useBean id="alumnoUtil"  class="aca.alumno.AlumUtil" scope="page"/>
<jsp:useBean id="carreraUtil"  class="aca.catalogo.CarreraUtil" scope="page"/>
<jsp:useBean id="cursoUtil"  class="aca.plan.CursoUtil" scope="page"/>
<jsp:useBean id="importadoUtil"  class="aca.kardex.ImportadoUtil" scope="page"/>
<jsp:useBean id="importar"  class="aca.kardex.KrdxCursoImp" scope="page"/>
<jsp:useBean id="MapaCursoU" scope="page" class="aca.plan.CursoUtil"/>
<script>
	function eliminar(folio){
		if(confirm("Estas seguro de eliminar el registro!")==true){
			document.frmcursos.Accion.value = "eliminar";
			document.frmcursos.folio.value = folio;
			document.frmcursos.submit();
		}	
	}
</script>
<%
	//variables
	String codigoAlumno		= (String)session.getAttribute("codigoAlumno");
	String NombreAlumno		= alumnoUtil.getNombre(conEnoc,codigoAlumno, "NOMBRE");
	String PlanId			= alumnoUtil.getPlanActivo(conEnoc,codigoAlumno);
	String CarreraId		= alumnoUtil.getCarreraId(conEnoc,PlanId );
	String NombreCarrera 	= carreraUtil.getNombreCarrera(conEnoc, CarreraId, "1");	
	String Nota 			= "0";
	
	ArrayList<aca.kardex.CursosImportados> lisImport		= importadoUtil.getListImportados(conEnoc, codigoAlumno , "ORDER BY MP.PLAN_ID,MP.CICLO,MP.CURSO_ID,KCI.NOTA");	 

	if(request.getParameter("Accion")!=null){
		if(request.getParameter("Accion").equals("eliminar")){
			importar.setCodigoPersonal(codigoAlumno);
			importar.setFolio(request.getParameter("folio"));		
			if(importar.existeReg(conEnoc)==true){			
				if(importar.deleteReg(conEnoc)){					
%>
<script>alert("Registro eliminado");</script>					
<%
				}else{
%>
<script>alert("El registro no pudo ser eliminado");</script>				
<%
				}	
							
			}						
		}
	}
	
	
%>
<html>
<head>
<title>Documento sin t&iacute;tulo</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">
<!--
.Estilo5 {font-size: xx-small}
-->
</style>
</head>

<body>
<form name="frmcursos" action="cursos" method="post">
<table style="width:100%"   align="center">
  <tr align="center">
    <td colspan="11">[<%=codigoAlumno%>] [<%=NombreAlumno%>] -- [<%=PlanId%>] [<%=NombreCarrera%>]</td>
  </tr>
  <tr align="center">
    <td colspan="11"><strong>CURSOS IMPORTADOS [<a href="accion">A&ntilde;adir</a>]</strong></td>
  </tr>
  <tr align="center">
    <td colspan="11">&nbsp;</td>
  </tr>
  <tr>
    <th width="3%"><span class="Estilo5"><spring:message code="aca.Numero"/></span></th>
    <th width="5%"><span class="Estilo5"><spring:message code="aca.Operacion"/></span></th>
    <th width="7%"><span class="Estilo5">Plan / Sem.</span></th>
    <th width="5%"><span class="Estilo5">CursoId</span></th>
    <th width="30%"><span class="Estilo5"><spring:message code="aca.Nombre"/> Materia</span></th>
    <th width="6%"><span class="Estilo5"><spring:message code="aca.Fecha"/></span></th>
    <th width="4%"><span class="Estilo5"><spring:message code="aca.Nota"/></span></th>
    <th width="3%"><span class="Estilo5"><spring:message code="aca.Tipo"/></span></th>
    <th width="3%"><span class="Estilo5">Conv.</span></th>
    <th width="15%"><span class="Estilo5">Observaciones</span></th>
  </tr>
<%
	aca.plan.MapaCurso MapaCurso = new aca.plan.MapaCurso();
	for(int i=0; i < lisImport.size(); i ++){
		CursosImportados cursoImport = (CursosImportados) lisImport.get(i);
		MapaCurso = MapaCursoU.mapeaRegId(conEnoc, cursoImport.cursoId);
		
		if (cursoImport.convalidacion.equals("S")){
			Nota 	= cursoImport.notaConva;
		}else{
			Nota 	= String.valueOf(cursoImport.nota); 
		}
		
%>  
  <tr>
    <td align="center"><span class="Estilo5"><%=i+1%></span></td>
    <td align="center"><span class="Estilo5"><a href="curso_update?folio=<%=cursoImport.folio%>&codigoPersonal=<%=codigoAlumno%>"><img title="Editar" src="../../imagenes/editar.gif" alt="Actualizar" width="12" height="12" ></a><a href="javascript:eliminar(<%=cursoImport.folio%>)"><img title="Eliminar" src="../../imagenes/no.png" alt="Eliminar" ></a>
    </span></td>
    <td><span class="Estilo5"><%=MapaCurso.getPlanId()%> / <%=MapaCurso.getCiclo()%></span></td>
    <td><span class="Estilo5"><%=MapaCurso.getCursoClave()%></span></td>
    <td><span class="Estilo5"><%=MapaCurso.getNombreCurso()%></span></td>
    <td align="center"><span class="Estilo5"><%=cursoImport.fCreada%></span></td>
    <td align="center"><span class="Estilo5"><%=Nota%></span></td>
    <td align="center"><span class="Estilo5"><%=cursoImport.tipocalId%></span></td>
    <td align="center"><span class="Estilo5"><%=cursoImport.convalidacion%></span></td>
    <td><span class="Estilo5"><%=cursoImport.observaciones%></span></td>
  </tr>
<%
	}
%>
  <tr align="center">
    <td colspan="11"><strong>
      <span class="Estilo5">
      <input name="folio" type="hidden" id="folio">
      </span>
      <input name="Accion" type="hidden" id="Accion">
      Fin del listado</strong></td>
  </tr>
</table>
</form>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %> 