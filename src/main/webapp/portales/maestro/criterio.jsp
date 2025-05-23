<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CriterioU" scope="page" class="aca.catalogo.CriterioUtil"/>
<jsp:useBean id="UnidadCriterio" scope="page" class="aca.carga.CargaUnidadCriterio"/>
<jsp:useBean id="UnidadCriterioU" scope="page" class="aca.carga.CargaUnidadCriterioUtil"/>

<head>
<script type="text/javascript">	
	 function guardar(){
			if(document.frmCriterio.Criterio.value != ""){
			  document.frmCriterio.action += "&Accion=2";
				return true;
			}else{
				alert("Fill in the required fields (*) in order to save");
			}
			return false;		
	}	
</script>
</head>
<% 
	String cursoCargaId = (String) session.getAttribute("CursoCargaId");
	String maestro 	    = (String) session.getAttribute("Maestro");
	String materia 	    = (String) session.getAttribute("Materia");
	String unidadId     = request.getParameter("Unidad");
	String temaId       = request.getParameter("Tema");
	String actId        = request.getParameter("Actividad");
	String criterioId   = request.getParameter("Criterio");
	
	int accion 		    = request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
	String Resultado    = "";
	
	ArrayList<aca.catalogo.CatCriterio> lisCriterio = CriterioU.getListAll(conEnoc, "ORDER BY CRITERIO_ID");
	
	 //operaciones a realizar 
	 switch (accion){
	    
	  case 2: { //Grabar los criterios
		    for (int j=0; j<lisCriterio.size(); j++){
		    aca.catalogo.CatCriterio criterio = (aca.catalogo.CatCriterio) lisCriterio.get(j);
	    	String criterios = request.getParameter("Criterio"+ (actId+criterio.getCriterioId()));
	    	if (criterios!= null){
	    		UnidadCriterio.setCursoCargaId(cursoCargaId);
	    		UnidadCriterio.setCriterioId(actId+criterio.getCriterioId());
	    		if(!criterio.getCriterioId().equals("26")){
	    		UnidadCriterio.setCriterioNombre(criterio.getDescripcion());
	    		}else{
	    			UnidadCriterio.setCriterioNombre(request.getParameter("Descripcion"));
	    		}
	    	
	    	
	    	if (UnidadCriterioU.existeReg(conEnoc, actId+criterio.getCriterioId(), cursoCargaId) == false){
				if (UnidadCriterioU.insertReg(conEnoc, UnidadCriterio)){
					Resultado = "Saved: "+UnidadCriterio.getCursoCargaId();
				}else{
					Resultado = "Error saving: "+UnidadCriterio.getCursoCargaId();
				}
			}else{	
				if (UnidadCriterioU.updateReg(conEnoc, UnidadCriterio)){ 
					Resultado = "The strategy:"+UnidadCriterio.getCursoCargaId()+" updated";
				}else{
					Resultado = "Error updating: "+UnidadCriterio.getCursoCargaId();
				}
			}
	       }
	      }
	    	break;
	    }
		
	  case 3:{//borrar
			
		  UnidadCriterio.setCursoCargaId(cursoCargaId);
		  UnidadCriterio.setCriterioId(criterioId);
		  
			if (UnidadCriterioU.existeReg(conEnoc, cursoCargaId, criterioId) == true){
				if (UnidadCriterioU.deleteReg(conEnoc,cursoCargaId, criterioId)){
					Resultado = "Deleted: "+UnidadCriterio.getCursoCargaId();
				}else{
					Resultado = "Error deleting: "+UnidadCriterio.getCursoCargaId();
				}	
			}else{
				    Resultado = "Not found: "+UnidadCriterio.getCursoCargaId();
			} 
			break;
     }
	   
	 }
%>
<body>
<table style="width:100%">
   <tr>
     <td colspan="2" align="center" style="font-size:10pt"><strong><%= Resultado %></strong></td>
   </tr>
   <tr>
     <td><a href="planEvaluacion.jsp?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestro %>&Materia=<%=materia%>">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
   </tr>  
</table>
<form name="frmCriterio" method="post" action="criterio?CursoCargaId=<%=cursoCargaId%>&Unidad=<%= unidadId %>&Tema=<%=temaId %>&Actividad=<%=actId %>">
  <input type="hidden" name="Accion"> 
  <table style="margin: 0 auto; width:50%">
    <tr><td style="font-size:12pt;" align="center" colspan="2">Subject: <%=materia%></td></tr>  
    <tr><td style="font-size:11pt; color:black;" align="center" colspan="2"><%= aca.carga.CargaUnidadUtil.getUnidadNombre(conEnoc, cursoCargaId, unidadId)%></td></tr>
    <tr>
      <td style="font-size:11pt; color:black;" align="center" colspan="2">
        <%= aca.carga.CargaUnidadTemaUtil.getNombreTema(conEnoc, cursoCargaId, temaId)%> &nbsp; - &nbsp; <%= aca.carga.CargaUnidadActividadUtil.getNombreActividad(conEnoc, cursoCargaId, actId)%>
      </td>
    </tr> 
    
    <tr><td colspan="2"></td></tr>
    <tr>
      <td style="background-color: #5FB404;font-size: 10pt; color:white;" align="center" colspan="2" align="center" width="50%"><b>Criteria</b></td>     
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr>
      <td colspan="2" align="left" style="font-size:10pt"><strong>Select criteria</strong></td>
    </tr>  
<% 
       for (int z=0; z<lisCriterio.size(); z++){
         aca.catalogo.CatCriterio crit = (aca.catalogo.CatCriterio) lisCriterio.get(z);
         
         String strCheck = "";
         UnidadCriterio.setCursoCargaId(cursoCargaId);
         UnidadCriterio.setCriterioId(actId+crit.getCriterioId());
         if (UnidadCriterioU.existeReg(conEnoc, actId+crit.getCriterioId(), cursoCargaId)){
        	 UnidadCriterio.mapeaRegId(conEnoc, cursoCargaId, actId+crit.getCriterioId());
        	 strCheck = "checked";
         }    
%>
    	<tr>
		  <td>
		  <%if(!crit.getCriterioId().equals("26")){ %>
		    <input type="checkbox" id="Criterio" name="Criterio<%=actId+crit.getCriterioId()%>" value="<%= crit.getCriterioId() %>" <%=strCheck%> /> 
		    <input style="background-color:#E6E6E6" type="text" id="Descripcion" name="Descripcion<%=actId+crit.getCriterioId()%>" value="<%= crit.getDescripcion()%>" size="35" Disabled/>
		  <%}else {%>
		    <input type="checkbox" id="Criterio" name="Criterio<%=actId+crit.getCriterioId()%>" value="<%= crit.getCriterioId() %>" <%=strCheck%> /> 
		    <input type="text" id="Descripcion" name="Descripcion" value="<%=UnidadCriterio.getCriterioNombre()%>" size="35" maxlength="70" />&nbsp;&nbsp;(Other)
		  <% }%>
		  </td>
		</tr>
<%   }%>
  
    <tr><td>&nbsp;</td></tr>
    <tr><td colspan="4" align="center"><input type="submit" value="Save" onclick="return guardar();" /></td></tr>
    <tr><td>&nbsp;</td></tr>
	<tr><td style="background-color: #5FB404;" align="center" colspan="4" align="center" >&nbsp;</td></tr>
  </table>
</form>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>