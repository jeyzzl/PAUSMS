<%@ page import= "aca.plan.MapaPlan"%>
<%@ page import= "aca.plan.MapaCurso"%>
<%@ page import= "aca.catalogo.CatTipoCal"%>
<%@ page import= "aca.conva.*"%>
<%@ page import= "aca.util.*"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AlumnoPersonal" scope="page" class="aca.alumno.AlumPersonal"/>

<jsp:useBean id="alumnoUtil"  class="aca.alumno.AlumUtil" scope="page"/>
<jsp:useBean id="carreraUtil"  class="aca.catalogo.CarreraUtil" scope="page"/>
<jsp:useBean id="importadoUtil"  class="aca.kardex.ImportadoUtil" scope="page"/>
<jsp:useBean id="csu"  class="aca.conva.ConvSolicitudUtil" scope="page"/>
<jsp:useBean id="cs"  class="aca.conva.ConvSolicitud" scope="page"/>
<jsp:useBean id="cu"  class="aca.plan.CursoUtil" scope="page"/>
<jsp:useBean id="planUtil"  class="aca.plan.PlanUtil" scope="page"/>
<jsp:useBean id="krdImport" scope="page" class="aca.kardex.KrdxCursoImp"/>
<jsp:useBean id="convEventoUtil" scope="page" class="aca.conva.ConvEventoUtil"/>
<jsp:useBean id="convMateriaUtil" scope="page" class="aca.conva.ConvMateriaUtil"/>
<jsp:useBean id="convHistorial" scope="page" class="aca.conva.ConvHistorial"/>
<jsp:useBean id="convHistorialU" scope="page" class="aca.conva.ConvHistorialUtil"/>
<jsp:useBean id="convEventoU" scope="page" class="aca.conva.ConvEventoUtil"/>
<jsp:useBean id="convMateriaU" scope="page" class="aca.conva.ConvMateriaUtil"/>

<jsp:setProperty name="krdImport" property="*"/> 

<script>
	function borrar(mat,id){
		if (confirm("¿Esta seguro que desea borrar el registro?")){
			document.frmimportcalif.convalidacionId.value=id
			document.frmimportcalif.Accion.value=1
			document.frmimportcalif.submit();
		}
	}
	
	function nuevo(){
		document.frmimportcalif.Accion.value=2
		document.frmimportcalif.submit();
	}
	
	function grabaCom(convalidacionId){
		document.frmimportcalif.convalidacionId.value=convalidacionId
		document.frmimportcalif.Accion.value=3
		document.frmimportcalif.numeroCom.value=convalidacionId
		document.frmimportcalif.submit();
	}
	
	function cerrar(convalidacionId){
		document.frmimportcalif.convalidacionId.value=convalidacionId
		document.frmimportcalif.Accion.value=4
		document.frmimportcalif.submit();
	}
	
	function borraMateria(convalidacionId,cursoId){
		if (confirm("¿Esta seguro que desea borrar la materia?")){
			document.frmimportcalif.convalidacionId.value=convalidacionId;
			document.frmimportcalif.cursoId.value=cursoId;
			document.frmimportcalif.Accion.value=5;
			document.frmimportcalif.submit();
		}
	}
</script>
<%
//variables
	String codigoAlumno		= (String)session.getAttribute("codigoAlumno");
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal");
	String cursoId			= request.getParameter("cursoId");
	String CarreraId		= "";
	String PlanId			= alumnoUtil.getPlanActivo(conEnoc,codigoAlumno); 
	String NombreCarrera 	= "";
	String NombreAlumno		= "";
	String escribir			= "";
	String convalidacionId	= request.getParameter("convalidacionId");
	String comentarioNum	= request.getParameter("numeroCom");
	if(comentarioNum == null || comentarioNum.equals(""))
		comentarioNum = "0";
	String comentario		= request.getParameter("comentario"+Integer.parseInt(comentarioNum));
	if(comentario == null || comentario.equals("null"))
		comentario = "";
	
	int Accion				= 0;
	ArrayList lisMaterias		= new ArrayList();
	ArrayList lisConv			= new ArrayList();
	ConvMateria convMateria = new ConvMateria();
	ConvEvento convEvento	= new ConvEvento();
	
	if(request.getParameter("Accion")!=null)
		Accion=Integer.parseInt(request.getParameter("Accion"));
%>

	
<%	
	// utilizacion de las variables
	switch(Accion){
		case 1:{//Borrar
				convEvento.setConvalidacionId(convalidacionId);
				convHistorial.setConvalidacionId(convalidacionId);
				if(convEventoU.deleteReg(conEnoc, convalidacionId) && convHistorialU.deleteAllRegs(conEnoc, convalidacionId))
					escribir = "Convalidacion Eliminada";
			}break;
		case 2:{//Nuevo
				convEvento.setConvalidacionId(String.valueOf(convEventoU.getMaxReg(conEnoc)));
				convEvento.setFecha(Fecha.getHoy());
				convEvento.setUsuario(codigoPersonal);
				convEvento.setCodigoPersonal(codigoAlumno);
				convEvento.setPlanId(PlanId);
				convEvento.setEstado("S");
				convEvento.setComentario("");
				convHistorial.setConvalidacionId(convEvento.getConvalidacionId());
				convHistorial.setEstado("S");
				convHistorial.setFecha(Fecha.getHoy());
				convHistorial.setUsuario(codigoPersonal);
				if(!convEventoU.insertReg(conEnoc, convEvento) && !convHistorialU.insertReg(conEnoc, convHistorial))
					escribir = "No se pudo crear la convalidacion. Intentelo de nuevo.";
			}break;
		case 3:{//Grabar comentario
				convEvento.mapeaRegId(conEnoc,convalidacionId);
				convEvento.setComentario(comentario);
				convEventoU.updateReg(conEnoc, convEvento);
			}break;
		case 4:{//Confirmar
				convEvento.mapeaRegId(conEnoc,convalidacionId);
				convEvento.setEstado("C");
				convEvento.setFecha(Fecha.getHoy());
				
				if(convHistorialU.existeEstado(conEnoc, convalidacionId, "C")){
					convHistorial.mapeaRegId(conEnoc, convalidacionId, "C");
					convHistorial.setFecha(Fecha.getHoy());
					convHistorial.setUsuario(codigoPersonal);
					if(!convHistorialU.updateReg(conEnoc, convHistorial))
						escribir = "No grabó correctamente";
				}else{
					convHistorial.setConvalidacionId(convalidacionId);
					convHistorial.setEstado("C");
					convHistorial.setFolio(convHistorialU.getMaxReg(conEnoc, convalidacionId));
					convHistorial.setFecha(Fecha.getHoy());
					convHistorial.setUsuario(codigoPersonal);
					if(!convHistorialU.insertReg(conEnoc, convHistorial))
						escribir = "No grabó correctamente";
				}
				convEventoU.updateReg(conEnoc, convEvento);
			}break;
		case 5:{//Borrar materia
				convMateria.mapeaRegId(conEnoc,convalidacionId,cursoId);
				if(convMateriaU.deleteReg(conEnoc,convalidacionId,cursoId))
					escribir = "Materia Eliminada";
				else
					escribir = "Error eliminando la Materia";
			}break;
	}
	
	if(PlanId==null){  // si el planId viene igual  a null le asignamos el plan de alumno para que muestre el las materias del plan
		PlanId = alumnoUtil.getPlanActivo(conEnoc,codigoAlumno);	
	}
	
	NombreAlumno	= alumnoUtil.getNombre(conEnoc,codigoAlumno, "NOMBRE");
	CarreraId		= alumnoUtil.getCarreraId(conEnoc,alumnoUtil.getPlanActivo(conEnoc,codigoAlumno) );	
	NombreCarrera	= carreraUtil.getNombreCarrera(conEnoc, CarreraId, "1");
	lisConv			= convEventoUtil.getListPersonal(conEnoc, codigoAlumno, "ORDER BY CONVALIDACION_ID");
%>

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
<form name="ultimoform" method="post" action="solicitud">
  <tr align="center">
    <!--td colspan="12"><input type="button" name="submit" value="Agregar"></td-->
    <td colspan="12"><a onclick="nuevo();">Agregar</a></td>
  </tr>
</form>  
<form name="frmimportcalif" method="post" action="solicitud">
<input name="Accion" type="hidden" value="">
<input name="codigoPersonal" type="hidden" value="">
<input name="convalidacionId" type="hidden" value="">
<input name="numeroCom" type="hidden" value="">
<input name="cursoId" type="hidden" value="">
<%if(!escribir.equals("")){%>
<tr align="center">
	<td width="100%" colspan="10">
		<table style="width:70%"   align="center">
			<tr>
				<td width="30%" colspan="2">
				<td bgcolor="#FF0000" colspan="2" align="center"><%=escribir%></td>
				<td width="30%" colspan="10">
			</tr>
		</table>
	</td>
</tr>
<%}%>
<% 
	for(int i=0; i < lisConv.size(); i++){
		convEvento = (ConvEvento) lisConv.get(i);
%>  
  <tr>
<%		convMateria.setConvalidacionId(convEvento.getConvalidacionId());
		if(!convMateriaU.existeConv(conEnoc, convEvento.getConvalidacionId())){ %>
  	<td align="center"><img onclick="borrar('<%=codigoAlumno%>','<%=convEvento.getConvalidacionId()%>');" class="button" alt='Eliminar'  src='../../imagenes/no.png'></img>
<%		}else{
%>
	<td>&nbsp;
<%
		} %>
<%		if(convEvento.getEstado().equals("S")){ %>
		<img onclick="document.location.href='accion?PlanId=<%=PlanId%>&convalidacionId=<%=convEvento.getConvalidacionId()%>'" class="button" alt='Modificar'  src='../../imagenes/editar.gif'></img></td>
    <td>[<%=convEvento.getUsuario() %>] [<%=convEvento.getFecha() %>] [<%=convEvento.getPlanId() %>] <b>[<%=convEvento.getEstado() %>]</b></td>   
    <td>Comentario: <input type=text name="comentario<%=convEvento.getConvalidacionId()%>" value="<%if(convEvento.getComentario() == null) out.print(""); else out.print(convEvento.getComentario());%>"/><input type=button value="Grabar" onclick="grabaCom(<%=convEvento.getConvalidacionId()%>);"/>
    	<%if(convMateriaU.existeConv(conEnoc, convEvento.getConvalidacionId())){ %><input type=button value="Confirmar" onclick="cerrar(<%=convEvento.getConvalidacionId()%>);"/><%} %>
<%		}else{ %>
    <td>[<%=convEvento.getUsuario() %>] [<%=convEvento.getFecha() %>] [<%=convEvento.getPlanId() %>] <b>[<%=convEvento.getEstado() %>]</b></td>   
    <td>Comentario: <input type=text name="comentario<%=convEvento.getConvalidacionId()%>" value="<%if(convEvento.getComentario() == null) out.print(""); else out.print(convEvento.getComentario());%>"/>
<%		} %>
    </td>
  </tr>
  <tr>
  	<td>&nbsp;</td>
  	<td colspan="2">
  		<table style="width:100%"   align="center">
<%		lisMaterias = convMateriaUtil.getListCicloNombre(conEnoc,convEvento.getConvalidacionId(),"ORDER BY CICLO_CURSO(CURSO_ID), ENOC.NOMBRE_MATERIA(CURSO_ID)");
		for(int j=0; j < lisMaterias.size(); j++){ 
			convMateria = (ConvMateria) lisMaterias.get(j);
		%>
  			<tr>
<%			if(convEvento.getEstado().equals("S")){ %>
  				<td align="right" width="5%"><img onclick="borraMateria('<%=convEvento.getConvalidacionId()%>' , '<%=convMateria.getCursoId()%>');" class="button" alt='Eliminar Materia'  src='../../imagenes/no.png'></img></td>
<%			}else{ %>
				<td align="right" width="5%"><img src="../../imagenes/g1.gif" /></td>
<%			} %>
  				<td><%=convMateria.getConvalidacionId()/*No es convalidacionId. Es el semestre y el cursoId*/%></td>
  				<td><%=cu.getNombreCurso(conEnoc,convMateria.getCursoId()) %></td>
  				<td><%=convMateria.getFecha() %></td>
  				<td><%=convMateria.getEstado() %></td>
  			</tr>
<% 		}%>
  		</table>
  	</td>
  </tr>
<% 
	}
	alumnoUtil 		= null;
	carreraUtil 	= null;
	importadoUtil 	= null;
	planUtil 		= null;
	lisMaterias		= null;
%>
  
</form>
</table>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>