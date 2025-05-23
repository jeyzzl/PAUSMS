<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="UnidadTema" scope="page" class="aca.carga.CargaUnidadTema"/>
<jsp:useBean id="UnidadTemaU" scope="page" class="aca.carga.CargaUnidadTemaUtil"/>

<head>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">	
	function Guardar(){
			if(document.frmUnidad.TemaId.value != "" && 
			   document.frmUnidad.Nombre.value != "" && 
			   document.frmUnidad.Fecha.value != "" &&
			   document.frmUnidad.Orden.value != ""  ){
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
	 String cursoCargaId   	= (String) session.getAttribute("CursoCargaId");
	 String maestro 	   	= (String) session.getAttribute("Maestro");
	 String materia 	   	= (String) session.getAttribute("Materia");
     String unidadId       	= request.getParameter("Unidad");
     String temaId		   	= request.getParameter("Tema");
     String unidadNombre	= aca.carga.CargaUnidadUtil.getUnidadNombre(conEnoc, cursoCargaId, temaId);
     
     int accion 		   	= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
     String Resultado      	= "";
     
     if (accion == 1){ 
    	    UnidadTema.setCursoCargaId(cursoCargaId);
    	    UnidadTema.setTemaId(UnidadTemaU.getMaximo(conEnoc, cursoCargaId, unidadId));
	 }else{
		    UnidadTema.setTemaId(request.getParameter("TemaId"));
	 }
     
     //operaciones a realizar 
	 switch (accion){
	 
	     case 0: { // Consulta
	    	Resultado = "Query..¡¡";			
	    	UnidadTema.mapeaRegId(conEnoc, cursoCargaId, temaId);
			break;
	     }
	       
	    case 2: { // Grabar y modificar
	    	UnidadTema.setCursoCargaId(cursoCargaId);
	    	UnidadTema.setTemaId(request.getParameter("TemaId"));
	    	UnidadTema.setTemaNombre(request.getParameter("Nombre"));
	    	UnidadTema.setFecha(request.getParameter("Fecha"));
	    	UnidadTema.setOrden(request.getParameter("Orden"));
	    	
	    	if (UnidadTemaU.existeReg(conEnoc, request.getParameter("TemaId"),cursoCargaId) == false){
				if (UnidadTemaU.insertReg(conEnoc, UnidadTema)){
					Resultado = "Saved: "+UnidadTema.getCursoCargaId();
				}else{
					Resultado = "Not saved: "+UnidadTema.getCursoCargaId();
				}
			}else{	
				if (UnidadTemaU.updateReg(conEnoc, UnidadTema)){ 
					Resultado = "Topic:"+" "+UnidadTema.getTemaId()+" "+"updated";
				}else{
					Resultado = "Error updating: "+UnidadTema.getCursoCargaId();
				}
			}
			break;	
			
		}
	    
		case 3:{//borrar
			
			UnidadTema.setCursoCargaId(cursoCargaId);
			UnidadTema.setTemaId(request.getParameter("Tema"));			
			
			if (UnidadTemaU.existeReg(conEnoc, request.getParameter("TemaId"),cursoCargaId) == true){
				if (UnidadTemaU.deleteReg(conEnoc, request.getParameter("TemaId"),cursoCargaId)){
					Resultado = "Deleted: "+UnidadTema.getCursoCargaId();
				}else{
					Resultado = "Error deleting: "+UnidadTema.getCursoCargaId();
				}	
			}else{
					Resultado = "Not found: "+UnidadTema.getCursoCargaId();
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
   <tr><td colspan="2" align="center" style="font-size:12pt"><strong>Topic Record</strong></td></tr>  
   <tr><td colspan="2" align="center" style="font-size:10pt; color:black;"><strong>Subject: <%= materia %></strong></td></tr>
   <tr><td colspan="2" align="center" style="font-size:10pt; color:black;"><strong><%= aca.carga.CargaUnidadUtil.getUnidadNombre(conEnoc, cursoCargaId, unidadId)%></strong></td></tr>
</table>
<form name="frmUnidad" method="post" action="tema?CursoCargaId=<%=cursoCargaId%>&Unidad<%= unidadId %>">
  <input type="hidden" name="Accion">
  <table style="margin: 0 auto;  width:50%"> 
    <tr><td>&nbsp;</td></tr>     
    <tr>
      <td style="background-color: #5FB404;font-size: 10pt; color:white;" align="center" colspan="2" align="center" width="50%"><b>Topic</b></td>     
    </tr>
     <tr><td>&nbsp;</td></tr> 
    <tr>
      <td><strong><spring:message code="aca.Clave"/></strong></td>
      <td>
      <% if (accion==1 ){%>         
        <input name="TemaId" type="text" class="text" id="TemaId" value="<%= unidadId+UnidadTema.getTemaId()%>" size="5" maxlength="2">        
      <%}else{ %>
        <input name="TemaId" type="text" class="text" id="TemaId" value="<%= UnidadTema.getTemaId()%>" size="5" maxlength="2">
      <% }%> 
      </td>         
    </tr>
    <tr> 
      <td><strong>Topic</strong></td>
      <td>         
        <input name="Nombre" type="text" class="text" id="Nombre" value="<%=UnidadTema.getTemaNombre() %>" size="30" maxlength="200">        
      </td>          
    </tr>
     <tr> 
      <td><strong><spring:message code="aca.Fecha"/></strong></td>
      <td>         
        <input name="Fecha" type="text" class="text" id="Fecha" size="10" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%= UnidadTema.getFecha()%>">
        (DD/MM/YYYY)        
      </td>          
    </tr>
    <tr> 
      <td><strong>Order</strong></td>
      <td>         
        <input name="Orden" type="text" class="text" id="Orden" value="<%=UnidadTema.getOrden()%>" size="3" maxlength="2">        
      </td>          
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr><td colspan="4" align="center"><input type="submit" value="Save" onclick="return Guardar();" /></td></tr>
    <tr><td>&nbsp;</td></tr>
	<tr><td style="background-color: #5FB404;" align="center" colspan="4" align="center" >&nbsp;</td></tr>
  </table>
</form>
</body>
<script>
	jQuery('#Fecha').datepicker();
</script>   
<%@ include file= "../../cierra_enoc.jsp" %>