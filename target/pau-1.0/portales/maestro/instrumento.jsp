<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CatInstrumentoU" scope="page" class="aca.catalogo.InstrumentoUtil"/>
<jsp:useBean id="Instrumento" scope="page" class="aca.carga.CargaUnidadInstrumento"/>
<jsp:useBean id="InstrumentoU" scope="page" class="aca.carga.CargaUnidadInstrumentoUtil"/>
<head>
<script type="text/javascript">	
	 function guardar(){
			if(document.frmInstrumento.Instrumento.value != ""){
			  document.frmInstrumento.action += "&Accion=2";
				return true;
			}else{
				alert("Fill in the required fields (*) in order to save");
			}
			return false;		
	}	
</script>
</head>
<% 
	String cursoCargaId 	= (String) session.getAttribute("CursoCargaId");
	String maestro 			= (String) session.getAttribute("Maestro");
	String materia 			= (String) session.getAttribute("Materia");
	String unidadId     	= request.getParameter("Unidad");
	String temaId       	= request.getParameter("Tema");
	String actId        	= request.getParameter("Actividad");
	String criterioId   	= request.getParameter("Criterio");
	String instrumentoId 	= request.getParameter("Instrumento");
	
	int accion 		    = request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
	String Resultado    = "";
	
	ArrayList<aca.catalogo.CatInstrumento> lisInstrumento = CatInstrumentoU.getListAll(conEnoc, "ORDER BY INSTRUMENTO_ID");
	
	 //operaciones a realizar 
	 switch (accion){
	    
	  case 2: { //Grabar los criterios
		    for (int j=0; j<lisInstrumento.size(); j++){
		    aca.catalogo.CatInstrumento ins = (aca.catalogo.CatInstrumento) lisInstrumento.get(j);
	    	String instrumento = request.getParameter("Instrumento"+ (criterioId+ins.getInstrumentoId()));
	    	if (instrumento!= null){
	    		Instrumento.setCursoCargaId(cursoCargaId);
	    		Instrumento.setInstrumentoId(criterioId+ins.getInstrumentoId());
	    		if(!ins.getInstrumentoId().equals("15")){
	    		    Instrumento.setInstrumentoNombre(ins.getDescripcion());
	    		}else{
	    			Instrumento.setInstrumentoNombre(request.getParameter("Descripcion")); 	
	    		}
	    	
	    	if (InstrumentoU.existeReg(conEnoc, cursoCargaId, criterioId+ins.getInstrumentoId()) == false){
				if (InstrumentoU.insertReg(conEnoc, Instrumento)){
					Resultado = "Saved: "+Instrumento.getCursoCargaId();
				}else{
					Resultado = "Error saving: "+Instrumento.getCursoCargaId();
				}
			}else{	
				if (InstrumentoU.updateReg(conEnoc, Instrumento)){ 
					Resultado = "Strategy:"+Instrumento.getCursoCargaId()+" updated";
				}else{
					Resultado = "Error updating: "+Instrumento.getCursoCargaId();
				}
			}
	       }
	      }
	    	break;
	    }
	  
	  case 3:{//borrar
			
			Instrumento.setCursoCargaId(cursoCargaId);
			Instrumento.setInstrumentoId(instrumentoId);
		  
			if (InstrumentoU.existeReg(conEnoc, cursoCargaId, instrumentoId) == true){
				if (InstrumentoU.deleteReg(conEnoc, cursoCargaId, instrumentoId)){
					Resultado = "Deleted: "+Instrumento.getCursoCargaId();
				}else{
					Resultado = "Error deleting: "+Instrumento.getCursoCargaId();
				}	
			}else{
				    Resultado = "Not found: "+Instrumento.getCursoCargaId();
			} 
			break;
     }
		
	   
	 }
%>
<body>
<table style="width:100%">      
   <tr>
     <td><a href="planEvaluacion.jsp">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
   </tr>
   <tr>
     <td colspan="2" align="center" style="font-size:10pt"><strong><%= Resultado %></strong></td>
   </tr>
   <tr><td style="font-size:12pt;" align="center" colspan="2">Subject: <%=materia%></td></tr>
   <tr><td style="font-size:11pt; color:black;" align="center" colspan="2"><%= aca.carga.CargaUnidadUtil.getUnidadNombre(conEnoc, cursoCargaId, unidadId)%></td></tr>
    <tr>
      <td style="font-size:11pt; color:black;" align="center" colspan="2">
        <%= aca.carga.CargaUnidadTemaUtil.getNombreTema(conEnoc, cursoCargaId, temaId)%> &nbsp; - &nbsp; <%= aca.carga.CargaUnidadActividadUtil.getNombreActividad(conEnoc, cursoCargaId, actId)%>
      </td>
    </tr>
    <tr><td style="font-size:11pt; color:black;" align="center" colspan="2"><%= aca.carga.CargaUnidadCriterioUtil.getNombreCriterio(conEnoc, cursoCargaId, criterioId)%></td></tr>
</table>
<form name="frmInstrumento" method="post" action="instrumento?CursoCargaId=<%=cursoCargaId%>&Unidad=<%= unidadId %>&Tema=<%=temaId %>&Actividad=<%=actId %>&Criterio=<%= criterioId %>">
  <input type="hidden" name="Accion">     
  <table style="margin: 0 auto; width:50%"> 
    <tr><td>&nbsp;</td></tr>     
    <tr>
      <td style="background-color: #5FB404;font-size: 10pt; color:white;" align="center" colspan="2" align="center" width="50%"><b>Instruments</b></td>     
    </tr>
    <tr><td>&nbsp;</td></tr> 
    <tr>
      <td colspan="2" align="left" style="font-size:9pt"><strong>Select instruments: </strong></td>
    </tr> 
<% 
     for (int j=0; j<lisInstrumento.size(); j++){
        aca.catalogo.CatInstrumento ins = (aca.catalogo.CatInstrumento) lisInstrumento.get(j);
        
        String strCheck = "";
        Instrumento.setCursoCargaId(cursoCargaId);
        Instrumento.setInstrumentoId(criterioId+ins.getInstrumentoId());
        if (InstrumentoU.existeReg(conEnoc, cursoCargaId, criterioId+ins.getInstrumentoId())){
        	Instrumento.mapeaRegId(conEnoc, cursoCargaId, criterioId+ins.getInstrumentoId());
       	 strCheck = "checked";
        }   
%>
    	<tr>
		  <td>
		  <%if (!ins.getInstrumentoId().equals("15")){ %>
		    <input type="checkbox" id="Instrumento" name="Instrumento<%=criterioId+ins.getInstrumentoId()%>" value="<%= ins.getInstrumentoId() %>" <%=strCheck%> /> 
		    <input style="background-color:#E6E6E6" type="text" id="Descripcion" name="Descripcion<%=criterioId+ins.getInstrumentoId()%>" value="<%= ins.getDescripcion()%>" size="35" Disabled/>
		  <% }else{ %>
		    <input type="checkbox" id="Instrumento" name="Instrumento<%=criterioId+ins.getInstrumentoId()%>" value="<%= ins.getInstrumentoId() %>" <%=strCheck%> />
		    <input type="text" id="Descripcion" name="Descripcion" value="<%=Instrumento.getInstrumentoNombre() %>" size="35" maxlength="50"/>&nbsp;&nbsp;(Other)
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