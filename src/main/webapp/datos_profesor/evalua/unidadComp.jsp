<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CompU" scope="page" class="aca.carga.CargaGrupoCompetenciaUtil"/>
<jsp:useBean id="UnidadComp" scope="page" class="aca.carga.CargaUnidadComp"/>
<jsp:useBean id="UnidadCompU" scope="page" class="aca.carga.CargaUnidadCompUtil"/>

<head>
<script type="text/javascript">
	 function guardar(){
			if(document.frmCompetencias.Competencia.value != ""){
			  document.frmCompetencias.action += "&Accion=2";
				return true;
			}else{
				alert("Complete las casillas requeridas (*) para poder guardar");
			}
			return false;	
	}
	
</script>
</head>
<% 
	String cursoCargaId  = (String) session.getAttribute("CursoCargaId");
	String maestro 	     = (String) session.getAttribute("Maestro");
	String materia 	     = (String) session.getAttribute("Materia");
    String unidadId      = request.getParameter("Unidad");
	String competenciaId = request.getParameter("Competencia");
	    
	int accion 		    = request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
	String Resultado    = "";
	
	ArrayList lisCompetencias = CompU.getListCompetencias(conEnoc, cursoCargaId, "ORDER BY COMPETENCIA_ID");
	
	 //operaciones a realizar 
	 switch (accion){
	    
	  case 2: { //Grabar las competencias
	    	
		    for (int j=0; j<lisCompetencias.size(); j++){
		    aca.carga.CargaGrupoCompetencia comp = (aca.carga.CargaGrupoCompetencia) lisCompetencias.get(j);
	    	String competencia = request.getParameter("Competencia"+ comp.getCompetenciaId());
	    	if (competencia!= null){
	    	UnidadComp.setCursoCargaId(cursoCargaId);
	    	UnidadComp.setUnidadId(request.getParameter("Unidad"));
	    	UnidadComp.setCompetenciaId(comp.getCompetenciaId());
	    	
	    	
	    	if (UnidadCompU.existeReg(conEnoc, cursoCargaId, request.getParameter("Unidad"), comp.getCompetenciaId()) == false){
				if (UnidadCompU.insertReg(conEnoc, UnidadComp)){
					Resultado = "Grabado: "+UnidadComp.getCursoCargaId();					
				}else{
					Resultado = "No Grabó: "+UnidadComp.getCursoCargaId();
				}
			}else{	
				if (UnidadCompU.updateReg(conEnoc, UnidadComp)){ 
					Resultado = "La estrategia:"+UnidadComp.getCursoCargaId()+"ha sido modificada";					
				}else{
					Resultado = "No Cambió: "+UnidadComp.getCursoCargaId();
				}
			}
	       }
	      }
	    	break;
	   }
	  
	  case 3:{//borrar
			
		  UnidadComp.setCursoCargaId(cursoCargaId);
		  UnidadComp.setUnidadId(unidadId);
		  UnidadComp.setCompetenciaId(competenciaId);
		  
			if (UnidadCompU.existeReg(conEnoc, cursoCargaId,unidadId,competenciaId ) == true){
				if (UnidadCompU.deleteReg(conEnoc, cursoCargaId,unidadId,competenciaId)){
					Resultado = "Borrado: "+UnidadComp.getCursoCargaId();					
				}else{
					Resultado = "No Borró: "+UnidadComp.getCursoCargaId();
				}	
			}else{
				    Resultado = "No existe: "+UnidadComp.getCursoCargaId();
			} 
			break;
     }
		
	   
	 }
%>
<body>
<table style="width:100%">
   <tr>
     <td align="center" colspan="2" align="left" style="font-size:10pt"><strong><%= Resultado %></strong></td>
   </tr>
   <tr>
     <td><a href="planEvaluacion">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
   </tr> 
</table>
<form name="frmCompetencias" method="post" action="unidadComp?CursoCargaId=<%=cursoCargaId%>&Unidad=<%= unidadId %>">
  <input type="hidden" name="Accion">
  <table style="margin: 0 auto;  width:50%"> 
    <tr><td>&nbsp;</td></tr>
    <tr><td>&nbsp;</td></tr>
    <tr>
      <td colspan="2" align="left" style="font-size:10pt"><strong>Selecciona las Competencias parciales para esta Unidad</strong></td>
    </tr>
    <tr>
      <td style="background-color: #5FB404;font-size: 10pt; color:white;" align="center" colspan="2" align="center" width="50%"><b>Competencias</b></td>     
    </tr>
     <tr><td>&nbsp;</td></tr> 
<% 
	for (int j=0; j<lisCompetencias.size(); j++){
	aca.carga.CargaGrupoCompetencia competencia = (aca.carga.CargaGrupoCompetencia) lisCompetencias.get(j);
	  
	  String strCheck = "";
	  UnidadComp.setCursoCargaId(cursoCargaId);
	  UnidadComp.setUnidadId(unidadId);
	  UnidadComp.setCompetenciaId(competencia.getCompetenciaId());
      if (UnidadCompU.existeReg(conEnoc, cursoCargaId,unidadId,competencia.getCompetenciaId())){
    	  UnidadComp.mapeaRegId(conEnoc, cursoCargaId, unidadId,competencia.getCompetenciaId());
     	 strCheck = "checked";
     	 
      }
%>
    	<tr>
		  <td>
		    <input type="checkbox" id="Competencia" name="Competencia<%=competencia.getCompetenciaId()%>" value="<%= UnidadComp.getCompetenciaId() %>" <%= strCheck%> /> 
		    <%= competencia.getDescripcion() %>
		  </td>
		</tr>
<% }%>
  
    <tr><td>&nbsp;</td></tr>
    <tr><td colspan="4" align="center"><input type="submit" value="Guardar" onclick="return guardar();" /></td></tr>
    <tr><td>&nbsp;</td></tr>
	<tr><td style="background-color: #5FB404;" align="center" colspan="4" align="center" >&nbsp;</td></tr>
  </table>
</form>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>