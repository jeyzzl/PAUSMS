<%@ page import= "aca.catalogo.CatTipoCal"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>

<jsp:useBean id="alumnoUtil"  class="aca.alumno.AlumUtil" scope="page"/>
<jsp:useBean id="carreraUtil"  class="aca.catalogo.CarreraUtil" scope="page"/>
<jsp:useBean id="importadoUtil"  class="aca.kardex.ImportadoUtil" scope="page"/>
<jsp:useBean id="importar" scope="page"  class="aca.kardex.KrdxCursoImp">
<jsp:setProperty name="importar" property="*"/></jsp:useBean> 

<script type="text/javascript"> src ="../../validacion.js"> </script> 


<script>
	function actualiza(){
		var fecha = document.frmImport.fCreada.value;
		var nota = document.frmImport.nota.value;
		
		if(fecha==""){
			alert("La fecha de creacion no puede ir vacia");		
			document.frmImport.fCreada.focus();
		}else if(nota ==""){
			alert("La nota no puede ir vacia");			
			document.frmImport.nota.focus();
		}else{
		document.frmImport.Accion.value = "actualizar"
		document.frmImport.submit();
		}		
	}
	
</script>
<%
//variables
	String codigoPersonal	= (String)session.getAttribute("codigoAlumno");
	String folio			= request.getParameter("folio");
	String mensaje			= " ";
	String CarreraId		= "";
	String PlanId			= "";
	String NombreCarrera 	= "";
	String NombreAlumno		= "";
	String convalidacion	= "N";
	String titulo			= "N";
	String optativa			= "N";
	String notaExtra		= "";
	String fechaExtra		= "";
	String notaConva		= "";
	String observaciones	= "";
	String nota				= "";
	
	ArrayList lisCondicion	= new ArrayList();
%>


<%	// utilizacion de las variables

	PlanId			= alumnoUtil.getPlanActivo(conEnoc,codigoPersonal);
	NombreAlumno	= alumnoUtil.getNombre(conEnoc,codigoPersonal, "NOMBRE");
	CarreraId		= alumnoUtil.getCarreraId(conEnoc,PlanId );
	NombreCarrera	= carreraUtil.getNombreCarrera(conEnoc, CarreraId, "1");
	lisCondicion	= importadoUtil.getListTipoCalificacion(conEnoc);
%>
<%
	if(request.getParameter("Accion") != null){
		if(request.getParameter("Accion").equals("actualizar")){
			if(importar.existeReg(conEnoc)==true){
				importar.setFCreada(request.getParameter("fCreada"));
				importar.setFExtra(request.getParameter("fExtra"));				
				convalidacion 	= request.getParameter("convalidacion")==null?"N":request.getParameter("convalidacion");		
				titulo 			= request.getParameter("titulo")==null?"N":request.getParameter("titulo");			
				optativa 		= request.getParameter("optativa")==null?"N":request.getParameter("optativa");
				if ( convalidacion.equals("S") ){ 
					importar.setNotaConva(importar.getNota());
					importar.setNota("0");
				}
				importar.setConvalidacion(convalidacion);
				importar.setTitulo(titulo);
				importar.setOptativa(optativa);		
				importar.setOptativaNombre(request.getParameter("nombreOptativa"));
				
				if(importar.updateReg(conEnoc)){					
					mensaje = "Datos Actualizados";
				}else{
					mensaje = "Datos no actualizados";
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
.Estilo1 {
	font-size: 9px;
	color: #FF0000;
}
-->
</style>
</head>
<body>
<table style="width:75%"   align="center">
  <form name="frmImport" action="curso_update" method="post">
  <tr align="center">
    <td colspan="3">[<%=codigoPersonal%>] [<%=NombreAlumno%>] -- [<%=PlanId%>] [<%=NombreCarrera%>]</td>
  </tr>
    <tr align="center">
    <td colspan="3"><p><a href="cursos.jsp"><strong>&lsaquo;&lsaquo; Regresar</strong></a></p>      </td>
  </tr>
    <tr>
    <th colspan="3">CURSOS IMPORTADOS </th>
  </tr>
<%
		// buscamos el los datos del  registro que se esta actualizando
		
		if(importar.existeReg(conEnoc)==true){
		
			importar.mapeaRegId(conEnoc, codigoPersonal, folio);
	
			if(importar.getConvalidacion()!=null )	convalidacion = importar.getConvalidacion();
			if (convalidacion.equals("S")){
				nota = importar.getNotaConva();				
			}else{
				nota = importar.getNota();				
			}
			if(importar.getTitulo()!=null)		titulo = importar.getTitulo();			 
			if(importar.getOptativa()!=null) 	optativa = importar.getOptativa();
			if(importar.getNotaExtra()!=null) 	notaExtra = importar.getNotaExtra();
			if(importar.getFExtra()!=null)	 	fechaExtra = importar.getFExtra();	
			if(importar.getObservaciones()!=null) observaciones = importar.getObservaciones();
			 
		}

%>  
  <tr>
    <td width="19%">&nbsp;</td>
    <td width="23%"><strong><spring:message code="aca.Matricula"/></strong></td>
    <td width="58%"><input name="codigoPersonal" type="text" class="text" id="codigoPersonal" value="<%=importar.getCodigoPersonal()%>" size="6" maxlength="7" readonly=""></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><strong><spring:message code="aca.Materia"/></strong></td>
    <td><input name="cursoId" type="text" class="text" id="cursoId" value="<%=importar.getCursoId()%>" size="17" maxlength="15" readonly=""></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><strong>CursoId 2 </strong></td>
    <td><input name="cursoId2" type="text" class="text" id="cursoId2" value="<%=importar.getCursoId2()%>" size="17" maxlength="15" readonly=""></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><strong>Fecha creacion </strong></td>
    <td><input name="fCreada" type="text" class="text" id="fCreada" onChange="validaFecha(this.value); this.value=borrar" value="<%=importar.getFCreada()%>"  size="9" maxlength="10">
    <span class="Estilo1">(DD/MM/AAAA)</span></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><strong>Convalidacion</strong></td>
    <td>
    	<select name="convalidacion" id="convalidacion">
			<option value="N" <%=convalidacion.equals("N")? "selected=\"selected\"" : "" %>>No</option>
			<option value="I" <%=convalidacion.equals("I")? "selected=\"selected\"" : "" %>>Int</option>
			<option value="S" <%=convalidacion.equals("S")? "selected=\"selected\"" : "" %>>Ext</option>
		</select>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><strong>Titulo</strong></td>
    <td><input name="titulo" type="checkbox" id="titulo" value="S" <%if(titulo.equals("S")){%>checked<%}%>></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><strong>Optativa</strong></td>
    <td><input name="optativa" type="checkbox" id="optativa" value="S" <%if(optativa.equals("S")){%>checked<%}%>></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><strong>Tipo calificacion</strong></td>
    <td><select name="tipoCalId" id="tipoCalId">
<%
		for(int j= 0; j<lisCondicion.size(); j++){
			CatTipoCal tipocalf	= (CatTipoCal) lisCondicion.get(j);
			if(tipocalf.getTipoCalId().equals(importar.getTipoCalId())){
				out.print(" <option value='"+tipocalf.getTipoCalId()+"'");			
				out.print("Selected>"+ tipocalf.getNombreTipoCal()+"</option>");
			}else{
				out.print(" <option value='"+tipocalf.getTipoCalId()+"'");			
				out.print(" >"+ tipocalf.getNombreTipoCal()+"</option>");
			}		
		}	
%>	
    </select></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><strong><spring:message code="aca.Nota"/></strong></td>
    <td><input name="nota" type="text" class="text" id="nota" value="<%=nota%>" size="3" maxlength="4" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;"></td>
  </tr>  
  <tr>
    <td>&nbsp;</td>
    <td><strong>Nota Extra </strong></td>
    <td><input name="notaExtra" type="text" class="text" id="notaExtra" value="<%=notaExtra%>" size="3" maxlength="3" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;"></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><strong><spring:message code="aca.Folio"/></strong></td>
    <td><input name="folio" type="text" class="text" id="folio" value="<%=importar.getFolio()%>" size="3" maxlength="3" readonly=""></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><strong>Fecha Extra </strong></td>
    <td><input name="fExtra" type="text" class="text" id="fExtra" value="<%=fechaExtra%>" size="9" maxlength="10" onChange="validaFecha(this.value); this.value=borrar">
      <span class="Estilo1">(DD/MM/AAAA)</span></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><strong>Observaciones</strong></td>
    <td><input name="observaciones" type="text" class="text" id="observaciones" value="<%=observaciones%>" size="25" maxlength="200"></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><strong><spring:message code="aca.Nombre"/> Optativa:</strong></td>
    <td><input name="nombreOptativa" type="text" class="text" id="nombreOptativa" value="<%=importar.getOptativaNombre() %>" size="25" maxlength="30"></td>
  </tr>
  <tr align="center">
    <td colspan="3"><%=mensaje%></td>
    </tr>
  <tr align="center">
    <td colspan="3"><input name="folio" type="hidden" id="folio" value="<%=folio%>">
      <input name="Accion" type="hidden" id="Accion">
      <input type="button" name="Submit" value="Actualizar" onClick="actualiza()"></td>
  </tr>
  </form> 
</table>
<%
	lisCondicion 	= null;
	carreraUtil		= null;
	alumnoUtil		= null;
	importar 	= null;
%>

</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %> 