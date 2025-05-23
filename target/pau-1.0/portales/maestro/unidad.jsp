<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CargaUnidad" scope="page" class="aca.carga.CargaUnidad"/>
<jsp:useBean id="CargaUnidadU" scope="page" class="aca.carga.CargaUnidadUtil"/>

<head>
<script type="text/javascript">	
	function Guardar(){
			if(document.frmUnidad.Unidad.value != "" && 
			   document.frmUnidad.Nombre.value != "" && 
			   document.frmUnidad.Orden.value != "" ){
			   document.frmUnidad.action += "&Accion=2";
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
	
	int accion 			 	= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
	String unidad			= "";
    String Resultado    	= "";
     
	if (accion == 1){ 
    	CargaUnidad.setCursoCargaId(cursoCargaId);
    	CargaUnidad.setUnidadId(CargaUnidadU.maximoReg(conEnoc, cursoCargaId));
	 }else{
		CargaUnidad.setUnidadId(request.getParameter("Unidad"));
	 }
	
     //operaciones a realizar 
	 switch (accion){
	 
	    case 0: { // Consulta
	    	Resultado = "Query..¡¡";			
	    	CargaUnidad.mapeaRegId(conEnoc, cursoCargaId, request.getParameter("Unidad"));
			break;
		}
	       
	    case 2: { // Grabar y modificar
	    	unidad = request.getParameter("Unidad").trim().length()==1?"0"+request.getParameter("Unidad").trim():request.getParameter("Unidad");
	    	CargaUnidad.setCursoCargaId(cursoCargaId);
	    	CargaUnidad.setUnidadId(unidad);
	    	CargaUnidad.setUnidadNombre(request.getParameter("Nombre"));
	    	CargaUnidad.setOrden(request.getParameter("Orden"));
	    	
	    	if (CargaUnidadU.existeReg(conEnoc,unidad,cursoCargaId) == false){
				if (CargaUnidadU.insertReg(conEnoc, CargaUnidad)){
					Resultado = "Saved: "+CargaUnidad.getCursoCargaId();
				}else{
					Resultado = "Error saving: "+CargaUnidad.getCursoCargaId();
				}
			}else{	
				if (CargaUnidadU.updateReg(conEnoc, CargaUnidad)){ 
					Resultado = "Strategy:"+CargaUnidad.getCursoCargaId()+" updated";
				}else{
					Resultado = "Error updating: "+CargaUnidad.getCursoCargaId();
				}
			}
			break;	
		}
	    
		case 3:{//borrar
			
			CargaUnidad.setCursoCargaId(cursoCargaId);
			CargaUnidad.setUnidadId(request.getParameter("Unidad"));			
			if (CargaUnidadU.existeReg(conEnoc, request.getParameter("Unidad"), cursoCargaId) == true){				
				if (CargaUnidadU.deleteReg(conEnoc, request.getParameter("Unidad"), cursoCargaId)){
					Resultado = "Deleted: "+CargaUnidad.getCursoCargaId();
				}else{
					Resultado = "Error deleting: "+CargaUnidad.getCursoCargaId();
				}	
			}else{
					Resultado = "Not fund: "+CargaUnidad.getCursoCargaId();
			}
			break;
       }	
	 }
%>
<body>
<table style="width:100%">
   <tr><td colspan="2" align="center" style="font-size:12pt"><strong>Unit Registration...</strong></td></tr>
   <tr><td colspan="2" align="center" style="font-size:10pt; color:black;"><strong>Subject: <%=materia%> </strong></td></tr>   
   <tr>
     <td><a href="planEvaluacion.jsp?">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
   </tr>
</table>
<form name="frmUnidad" method="post" action="unidad?CursoCargaId=<%=cursoCargaId%>">
  <input type="hidden" name="Accion">
  <table style="margin: 0 auto;  width:50%"> 
    <tr><td>&nbsp;</td></tr>     
    <tr>
      <td style="background-color: #5FB404;font-size: 10pt; color:white;" align="center" colspan="2" align="center" width="50%"><b>Units</b></td>     
    </tr>
     <tr><td>&nbsp;</td></tr> 
    <tr>
      <td><strong>Unit</strong></td>
      <td>         
        <input name="Unidad" type="text" class="text" id="Unidad" value="<%= CargaUnidad.getUnidadId()%>" size="5" maxlength="2">        
      </td>          
    </tr>
    <tr> 
      <td><strong><spring:message code="aca.Nombre"/></strong></td>
      <td>         
        <input name="Nombre" type="text" class="text" id="Nombre" value="<%= CargaUnidad.getUnidadNombre() %>" size="30" maxlength="100">        
      </td>          
    </tr>
    <tr> 
      <td><strong>Order</strong></td>
      <td>         
        <input name="Orden" type="text" class="text" id="Orden" value="<%= CargaUnidad.getOrden() %>" size="3" maxlength="2">        
      </td>          
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr><td colspan="4" align="center"><input type="submit" value="Save" onclick="return Guardar();" /></td></tr>
    <tr><td>&nbsp;</td></tr>
	<tr><td style="background-color: #5FB404;" align="center" colspan="4" align="center" ><%=Resultado%></td></tr>
  </table>
</form>
</body>   
<%@ include file= "../../cierra_enoc.jsp" %>