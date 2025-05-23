<%@ page import="java.text.*" %>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="BorraNotas" scope="page" class="aca.carga.CargaGrupoBorra"/>
<jsp:useBean id="BorraNotasU" scope="page" class="aca.carga.CargaGrupoBorraUtil"/>

<script type="text/javascript">	
	function Borrar( ){
		if(confirm("Estas seguro de eliminar todas las notas!")==true){
	  		document.frmGrupo.Accion.value="4";
			document.frmGrupo.submit();
		}			
		
	}	
</script>

<%
	DecimalFormat getformato= new DecimalFormat("##0.00;(##0.00)");

 	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
 	String codigoEmpleado	= (String) session.getAttribute("codigoEmpleado");
	String cursoCargaId		= request.getParameter("CursoCargaId");
	String cargaId 			= cursoCargaId.substring(0,6);
	String folio 			= "";
	
	int numAccion 			= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
	
	String cursoId			= aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conEnoc, cursoCargaId);
	String nombreMaestro 	= aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, codigoEmpleado,"Nombre");
	String nombreMateria 	= aca.plan.CursoUtil.getMateria(conEnoc,cursoId);	
	String resultado		= "";
	
	int numInsc				= aca.kardex.KrdxCursoAct.numAlumGrupo(conEnoc, cursoCargaId, "'I','1','2'");
	int numBaja				= aca.kardex.KrdxCursoAct.numAlumGrupo(conEnoc, cursoCargaId, "'3'");
	
	int numEst				= aca.carga.CargaGrupoEvaluacionUtil.getNumEst(conEnoc,cursoCargaId,"TODAS");
	int numEstReg			= aca.kardex.KrdxAlumnoEval.getNumEst(conEnoc,cursoCargaId);
	
	int numAct				= aca.carga.CargaGrupoActividadUtil.getNumAct(conEnoc,cursoCargaId);
	int numActReg			= aca.kardex.KrdxAlumnoActiv.getNumActividades(conEnoc, cursoCargaId);
	
	int delEst=0,delEstReg=0,delAct=0,delActReg=0;	
	boolean bolDelete=false, bolEst=false, bolEstReg = false, bolAct=false, bolActReg=false;
	
	// Operaciones a realizar en la pantalla
	switch (numAccion){
		case 4: {// Borrar Notas de la materia
			
			conEnoc.setAutoCommit(false);
		
			// Borra las actividades calificadas de la materia
			if ( numActReg > 0 ){
				if ( aca.kardex.KrdxAlumnoActiv.deleteAlumAct( conEnoc,cursoCargaId )==numActReg ){
					delActReg = numActReg;
					bolActReg = true;			
				}
			}else{
				bolActReg = true;
			}
		
			// Borra las actividades de la materia
			if ( bolActReg){
				if ( numAct > 0 ){
					if ( aca.carga.CargaGrupoActividadUtil.deleteGpoAct(conEnoc,cursoCargaId)==numAct){
						delAct = numAct; 
						bolAct = true;
					}
				}else{
					bolAct = true;
				}
			}
			
			// Borra las evaluaciones calificadas en la materiaBorraNotas.setFecha( aca.util.Fecha.getHoy() );
			if (bolAct){
				if ( numEstReg > 0 ){
					if ( aca.kardex.KrdxAlumnoEval.deleteAlumEval(conEnoc,cursoCargaId)==numEstReg ){
						delEstReg = numEstReg;
						bolEstReg = true;
					}
				}else{
					bolEstReg = true;
				}
			}
			
			// Borra las evaluaciones de la materia
			if ( bolEstReg ){
				if ( numEst > 0 ){
					if ( aca.carga.CargaGrupoEvaluacionUtil.deleteGpoEval(conEnoc, cursoCargaId )==numEst ){
						delEst = numEst;
						bolEst = true;
					}
				}else{
					bolEst = true;
				}
			}
			
			// Registra los datos de quien borro las notas de la materia 
			if (bolActReg && bolAct && bolEst && bolEstReg){
				
				BorraNotas.setCursoCargaId(cursoCargaId);
				folio = BorraNotasU.maximoReg(conEnoc, cursoCargaId);
				BorraNotas.setFolio(folio);
				BorraNotas.setFecha( aca.util.Fecha.getHoy() );
				BorraNotas.setUsuario( codigoPersonal );
				BorraNotas.setIp(request.getRemoteAddr());
				BorraNotas.setNumEst( String.valueOf(numEst) );
				BorraNotas.setNumAct( String.valueOf(numAct) );
				if ( BorraNotasU.insertReg(conEnoc, BorraNotas) ){	
					conEnoc.commit();
					bolDelete = true;					
					BorraNotas = BorraNotasU.mapeaRegId(conEnoc,cursoCargaId,folio);
					if (folio.length()==1) folio = 0+folio;
					resultado = "Folio de la operación: <b>"+cursoCargaId+"-"+folio+"</b>";
				}else{
					conEnoc.rollback();
				}
				
			}
			
			conEnoc.setAutoCommit(true);	
			
			break;
		}		
		
	}		
%>
<div class="container-fluid">
<h1>Borrar Notas</h1>
<div class="alert alert-info">
	<a class="btn btn-primary" href="cursos"><spring:message code="aca.Regresar"/></a>
</div>
<table style="width:70%" align="CENTER">
<tr><td>&nbsp;</td></tr>
<tr>
  <td align="center" style="font-size:11pt;">
    Periodo: <b>[ <%=cargaId%> ]</b> - <%=aca.carga.CargaUtil.getNombreCarga(conEnoc, cargaId)%>
  </td>
</tr>
<tr>
  <td align="center" style="font-size:10pt;">
  <b>Acta:</b> [ <%=cursoCargaId%> ] - <b>Materia:</b> <%=nombreMateria%> - <b>Maestro:</b> <%=nombreMaestro%>
</td>
</tr>
</table>
<form action="borrarNotas?CursoCargaId=<%= cursoCargaId %>" method="post" name="frmGrupo" target="_self">
<input type="hidden" name="Accion">
<table style="width:60%" class="table table-condensed" align="center"  >
<tr>
  <th align="center"> Información de la Materia</th>
</tr>
<tr>
  <td>
	<table style="margin: 0 auto;"   >
	  <tr>
	    <td align="center">
	      <b>Alumnos:</b>&nbsp; &nbsp;Inscritos[ <b><%=numInsc%></b> ] &nbsp; Bajas[ <b><%=numBaja%></b> ] &nbsp; &nbsp; &nbsp;
	      <b>Estrategias:</b>&nbsp; &nbsp;Alta[ <b><%= numEst%></b> ] &nbsp; Registros[ <b><%= numEstReg%></b> ] &nbsp; &nbsp; &nbsp;
	      <b>Actividades:</b>&nbsp; &nbsp;Alta[ <b><%= numAct%></b> ] &nbsp; Registros[ <b><%= numActReg%></b> ]
	    </td>
	  </tr>	  
	</table>  
  </td>
</tr>
</table>
<table style="margin: 0 auto;">
  <tr>    
	<td align="center">
<% 	if ( numEst > 0 ){%>
	  <input class="btn btn-primary" name="Borrar Notas" onClick="javascript:Borrar()" type="button" value="Borrar Notas" ></td>
<% 	}else{
	out.print("¡ No hay notas registradas !");
}%> 	  
  </tr> 
</table>
<br>
<%	if ( bolDelete ){ %>
<table style="margin: 0 auto;">
  <tr>    
	<td align="center">
	  Se han eliminado: <b><%= BorraNotas.getNumEst() %></b> Estrategias y <%= BorraNotas.getNumAct() %> Actividades <br>
	  <%= resultado %>
	</td>
  </tr>
</table>
<%	} %>
</form>
</div>
<!-- fin de estructura -->
<%@ include file= "../../cierra_enoc.jsp" %>