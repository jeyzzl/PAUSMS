<%@ page import= "aca.plan.MapaCurso"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumnoUtil"  class="aca.alumno.AlumUtil" scope="page"/>
<jsp:useBean id="carreraUtil"  class="aca.catalogo.CarreraUtil" scope="page"/>
<jsp:useBean id="MapaCursoU"  class="aca.plan.CursoUtil" scope="page"/>
<jsp:useBean id="planUtil"  class="aca.plan.PlanUtil" scope="page"/>
<jsp:useBean id="convSolicitud" scope="page" class="aca.conva.ConvSolicitud"/>
<jsp:useBean id="convMateria" scope="page" class="aca.conva.ConvMateria"/>
<jsp:useBean id="convMateriaU" scope="page" class="aca.conva.ConvMateriaUtil"/>
<script type="text/javascript"> src ="../../validacion.js"> </script>
<%
//variables
	String codigoAlumno		= (String)session.getAttribute("codigoAlumno");
	String planId			= request.getParameter("PlanId");
	String carreraId		= "";
	String NombreCarrera 	= "";
	String NombreAlumno		= "";
	String check			= "";
	String cursoId			= "";
	String tipo				= "";
	String convalidacionId	= request.getParameter("convalidacionId");
	
	// Lista de cursos
	ArrayList lisMaterias	= MapaCursoU.getListConvalida(conEnoc,planId,codigoAlumno,"ORDER BY CICLO, NOMBRE_CURSO" );
	
	int cicloTem			= 0;
	int ciclo				= 0;
	
	if(request.getParameter("Accion")!=null){
		if(request.getParameter("Accion").equals("1")){
		
			for(int i= 0; i < lisMaterias.size();  i++){	
				check			= request.getParameter("check"+i);
				cursoId			= request.getParameter("cursoId"+i);
				tipo			= request.getParameter("tipo"+i);
				if(check != null){
						
						convMateria.setConvalidacionId(convalidacionId);
						convMateria.setCursoId(cursoId);
						convMateria.setFecha(aca.util.Fecha.getHoy());
						convMateria.setTipo(tipo);
						convMateria.setEstado("-");
						if(convMateriaU.existeReg(conEnoc, convalidacionId, cursoId)){
							convMateriaU.updateReg(conEnoc, convMateria);
						}else convMateriaU.insertReg(conEnoc, convMateria);
				}			
			}		
			lisMaterias	= MapaCursoU.getListConvalida(conEnoc,planId,codigoAlumno,"ORDER BY CICLO, NOMBRE_CURSO" );
		}		
	}
%>
	
<%	System.out.println("Va a revisar si plan Id tiene algo");
	// utilizacion de las variables
	
	if(planId==null){  // si el planId viene igual  a null le asignamos el plan de alumno para que muestre el las materias del plan
		planId = alumnoUtil.getPlanActivo(conEnoc,codigoAlumno);	
	}	
	NombreAlumno	= alumnoUtil.getNombre(conEnoc,codigoAlumno, "NOMBRE");
	carreraId		= alumnoUtil.getCarreraId(conEnoc,alumnoUtil.getPlanActivo(conEnoc,codigoAlumno) );	
	NombreCarrera	= carreraUtil.getNombreCarrera(conEnoc, carreraId, "1");
%>
<script>
	function grabar(){
		document.frmimportcalif.Accion.value = 1;
		document.frmimportcalif.submit();
	}
</script>
<html>
<head>
<title><spring:message code='aca.DocumentoSinTitulo'/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">
<!--
.Estilo1 {font-size: 9px}
.Estilo3 {color: #993300}
-->
</style>
</head>

<body>
<table style="width:70%"   align="center">
  <tr align="center">
    <td colspan="12">[<%=codigoAlumno%>] [<%=NombreAlumno%>] -- [<%=alumnoUtil.getPlanActivo(conEnoc,codigoAlumno)%>] [<%=NombreCarrera%>]</td>
  </tr>
  <tr align="center">
  </tr>
<form name="frmimportcalif" method="post" action="accion">
<input name="Accion" type="hidden" value="">
    <td colspan="12"><a href="solicitud"><strong>&lsaquo;&lsaquo; Regresar</strong></a></td>
<input name="PlanId" type="hidden" value="<%=planId%>">
<input name="convalidacionId" type="hidden" value="<%=convalidacionId%>">
<% 
	for(int i=0; i < lisMaterias.size(); i ++){
		MapaCurso curso = (MapaCurso) lisMaterias.get(i);
		ciclo	= Integer.parseInt(curso.getCiclo());
		if(ciclo != cicloTem){
			cicloTem = ciclo;
%>
  <tr>
    <td colspan="12"><strong>Semestre <%=ciclo%></strong></td>
  </tr>
  <tr>
    <th width="4%" height="16"><span class="Estilo5">Aplicar</span></th>
    <th width="7%"><span class="Estilo5"><spring:message code="aca.Clave"/></th>
    <th width="30%"><span class="Estilo5"><spring:message code="aca.Materia"/></span></th>
    <th width="12%"><span class="Estilo5 ">Tipo Conv.</span></th>
  </tr>
<%
		}
%>  
  <tr>
    <td align="center"><input name="check<%=i%>" id="check<%=i%>" type="checkbox"  value="SI"></td>
    <td><%=curso.getCursoId().substring(0,7)%></td>
    <td><%=curso.getNombreCurso()%><input name="cursoId<%=i%>" type="hidden" id="cursoId<%=i%>" value="<%=curso.getCursoId()%>"></td>    
	<td align="center">
	  <select name="tipo<%=i%>" id="tipo<%=i%>">
	    <option value="I" selected>Interna</option>
	    <option value="E" >Externa</option>
	  </select>
	</td>
  </tr>
<% 
	}
	alumnoUtil 		= null;
	carreraUtil 	= null;	
	planUtil 		= null;
	lisMaterias		= null;
%>
  <tr align="center">
    <td colspan="12"><input type="button" name="Submit" value="Grabar" onClick="grabar()">
      <input name="planId" type="hidden" id="planId" value="<%=planId%>"></td>
  </tr>
</form>
</table>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %> 