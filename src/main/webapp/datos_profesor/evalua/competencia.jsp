<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<jsp:useBean id="GrupoCompetencia" scope="page" class="aca.carga.CargaGrupoCompetencia"/>
<jsp:useBean id="CompetenciaU" scope="page" class="aca.carga.CargaGrupoCompetenciaUtil"/>
<head>
<script type="text/javascript">
	function guardar(){
			if(document.frmAlta.Competencia.value != "" &&
			  document.frmAlta.Descripcion.value != "" ){
			  document.frmAlta.action += "&Accion=2";
				return true;
			}else{
				alert("Complete las casillas requeridas (*) para poder guardar");
			}
			return false;
	}
	
</script>
</head>
<% 
	 String cursoCargaId    = (String) session.getAttribute("CursoCargaId");
	 String maestro 	    = (String) session.getAttribute("Maestro");
	 String materia 	    = (String) session.getAttribute("Materia");
 
     int accion 		 = request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
     String Resultado    = ""; 
     
     ArrayList lisCompetencias =  new ArrayList();
     
     //operaciones a realizar 
	 switch (accion){
	 
	    case 1: { // Consulta
	    	Resultado = "Consulta..¡¡";			
			GrupoCompetencia.mapeaRegId(conEnoc, cursoCargaId, request.getParameter("Competencia"));
			break;
		}
	       
	    case 2: { // Grabar y modificar
	    	GrupoCompetencia.setCursoCargaId(cursoCargaId);
	    	GrupoCompetencia.setCompetenciaId(request.getParameter("Competencia").toUpperCase());
	    	GrupoCompetencia.setDescripcion(request.getParameter("Descripcion"));
	    	
	    	if (CompetenciaU.existeReg(conEnoc, cursoCargaId, request.getParameter("Competencia").toUpperCase()) == false){
				if (CompetenciaU.insertReg(conEnoc, GrupoCompetencia)){
					Resultado = "Grabado: " +GrupoCompetencia.getCursoCargaId();					
				}else{
					Resultado = "No Grabó: "+GrupoCompetencia.getCursoCargaId();
				}
			}else{	
				if (CompetenciaU.updateReg(conEnoc, GrupoCompetencia)){ 
					Resultado = "La estrategia:"+GrupoCompetencia.getCursoCargaId()+"ha sido modificada";					
				}else{
					Resultado = "No Cambió: "+GrupoCompetencia.getCursoCargaId();
				}
			}
			break;	
			
		}
	    
		case 3:{//borrar
			GrupoCompetencia.setCursoCargaId(cursoCargaId);
			GrupoCompetencia.setCompetenciaId(request.getParameter("Competencia"));	
			
			if (CompetenciaU.existeReg(conEnoc, cursoCargaId, request.getParameter("Competencia")) == true){
				if (CompetenciaU.deleteReg(conEnoc, cursoCargaId, request.getParameter("Competencia"))){
					Resultado = "Borrado: "+GrupoCompetencia.getCursoCargaId();					
				}else{
					Resultado = "No Borró: "+GrupoCompetencia.getCursoCargaId();
				}	
			}else{
					Resultado = "No existe: "+GrupoCompetencia.getCursoCargaId();
			}
			break;
}	
	  
	 }
	  
	 lisCompetencias      = CompetenciaU.getListAll(conEnoc, "ORDER BY COMPETENCIA_ID");
%>
<body>
<table style="width:100%">
   <tr>
     <td colspan="2" align="center" style="font-size:12pt"><strong>Competencias</strong></td>
   </tr>
   <tr>
     <td><a href="plan">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
   </tr>  
</table>
<form name="frmAlta" method="post" action="competencia?CursoCargaId=<%=cursoCargaId%>&maestro=<%=maestro%>&materia=<%=materia%>">
  <input type="hidden" name="Accion">
  <table style="margin: 0 auto;  width:50%"> 
    <tr><td>&nbsp;</td></tr>
    <tr><td>&nbsp;</td></tr> 
    <tr>
      <td style="background-color: #5FB404;font-size: 10pt; color:white;" align="center" colspan="2" align="center" width="50%"><b>Competencias Parciales</b></td>     
    </tr>
     <tr><td>&nbsp;</td></tr> 
    <tr>
      <td><strong>Inciso</strong></td>
      <td>         
        <input name="Competencia" type="text" class="text" id="Competencia" value="<%= GrupoCompetencia.getCompetenciaId()%>" size="5" maxlength="1">        
      </td>          
    </tr>
    <tr> 
      <td><strong><spring:message code='aca.Descripcion'/></strong></td>
      <td>         
        <input name="Descripcion" type="text" class="text" id="Descripcion" value="<%= GrupoCompetencia.getDescripcion() %>" size="30" maxlength="1000">        
      </td>          
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr><td colspan="4" align="center"><input type="submit" value="Guardar" onclick="return guardar();" /></td></tr>
    <tr><td>&nbsp;</td></tr>
	<tr><td style="background-color: #5FB404;" align="center" colspan="4" align="center" >&nbsp;</td></tr>
  </table>
</form>

<table style="margin: 0 auto;  width:50%"> 
  <tr>
    <td colspan="3" align="center" style="font-size:12pt"><strong><spring:message code="aca.Listado"/>:</strong></td>
  </tr>
  <tr>
    <td width="10%" style="background-color: #D0F5A9;font-weight: bold; font-size:10pt" align="center"><spring:message code="aca.Operacion"/></td>
    <td width="10%" style="background-color: #D0F5A9;font-weight: bold; font-size:10pt" align="center">Inciso</td>
    <td width="20%" style="background-color: #D0F5A9;font-weight: bold; font-size:10pt" align="center"><spring:message code='aca.Descripcion'/></td>
  </tr>
<% 
    for (int i=0;i<lisCompetencias.size(); i++){
     	aca.carga.CargaGrupoCompetencia competencia = (aca.carga.CargaGrupoCompetencia) lisCompetencias.get(i);
%>    	
  <tr>
     <td align="center"> <a href="competencia?Accion=1&CursoCargaId=<%= cursoCargaId%>&Competencia=<%= competencia.getCompetenciaId()%>"> 
       <img title="Editar" src="../../imagenes/editar.gif" alt="Modificar" ></a>
       
<% 	/********** Permite borrar si no estan registradas las competencias en alguna unidad *****/
		if (aca.carga.CargaUnidadCompUtil.numUnidadComp(conEnoc, cursoCargaId, competencia.getCompetenciaId()) == 0){ %>
       <a href="competencia?Accion=3&CursoCargaId=<%= cursoCargaId%>&Competencia=<%= competencia.getCompetenciaId()%>">
       <img src="../../imagenes/no.png" alt="Eliminar" ></a>
<% 		}%>        
     </td>
    <td align="center"><%= competencia.getCompetenciaId()%></td>
    <td align="left"><%= competencia.getDescripcion() %></td>
  </tr> 
<%  
    }
%> 
</table>
</body>   
<%@ include file= "../../cierra_enoc.jsp" %>