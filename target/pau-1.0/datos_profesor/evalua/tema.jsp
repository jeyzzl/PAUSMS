<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CargaUnidadTema" scope="page" class="aca.carga.CargaUnidadTema"/>
<jsp:useBean id="CargaUnidadTemaU" scope="page" class="aca.carga.CargaUnidadTemaUtil"/>
<head>
<script type="text/javascript" src="../../js/popcalendar.js"></script>
<script type="text/javascript">
	function Guardar(){
			if(document.frmUnidad.TemaId.value != "" && 
			   document.frmUnidad.Nombre.value != "" && 
			   document.frmUnidad.Fecha.value != "" &&
			   document.frmUnidad.Orden.value != ""  ){
			   document.frmUnidad.action += "&Accion=2";
				return true;
			}else{
				alert("Complete las casillas requeridas (*) para poder guardar");
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
    	    CargaUnidadTema.setCursoCargaId(cursoCargaId);
    	    CargaUnidadTema.setTemaId(CargaUnidadTemaU.getMaximo(conEnoc, cursoCargaId, unidadId));
	 }else{
		    CargaUnidadTema.setTemaId(request.getParameter("TemaId"));
	 }
     
     //operaciones a realizar 
	 switch (accion){
	 
	     case 0: { // Consulta
	    	Resultado = "Consulta..¡¡";			
	    	CargaUnidadTemaU.mapeaRegId(conEnoc, cursoCargaId, temaId);
			break;
	     }
	       
	    case 2: { // Grabar y modificar
	    	CargaUnidadTema.setCursoCargaId(cursoCargaId);
	    	CargaUnidadTema.setTemaId(request.getParameter("TemaId"));
	    	CargaUnidadTema.setTemaNombre(request.getParameter("Nombre"));
	    	CargaUnidadTema.setFecha(request.getParameter("Fecha"));
	    	CargaUnidadTema.setOrden(request.getParameter("Orden"));
	    	
	    	if (CargaUnidadTemaU.existeReg(conEnoc, temaId, cursoCargaId) == false){
				if (CargaUnidadTemaU.insertReg(conEnoc, CargaUnidadTema)){
					Resultado = "Grabado: "+CargaUnidadTema.getCursoCargaId();					
				}else{
					Resultado = "No Grabó: "+CargaUnidadTema.getCursoCargaId();
				}
			}else{	
				if (CargaUnidadTemaU.updateReg(conEnoc, CargaUnidadTema)){
					Resultado = "El tema:"+" "+CargaUnidadTema.getTemaId()+" "+"ha sido modificado";					
				}else{
					Resultado = "No Cambió: "+CargaUnidadTema.getCursoCargaId();
				}
			}
			break;	
			
		}
	    
		case 3:{//borrar
			
			CargaUnidadTema.setCursoCargaId(cursoCargaId);
			CargaUnidadTema.setTemaId(request.getParameter("Tema"));			
			
			if (CargaUnidadTemaU.existeReg(conEnoc, temaId, cursoCargaId) == true){
				if (CargaUnidadTemaU.deleteReg(conEnoc, temaId, cursoCargaId)){
					Resultado = "Borrado: "+CargaUnidadTema.getCursoCargaId();					
				}else{
					Resultado = "No Borró: "+CargaUnidadTema.getCursoCargaId();
				}	
			}else{
					Resultado = "No existe: "+CargaUnidadTema.getCursoCargaId();
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
     <td><a href="planEvaluacion?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestro %>&Materia=<%=materia%>">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
   </tr>
   <tr><td colspan="2" align="center" style="font-size:12pt"><strong>Captura de Tema</strong></td></tr>  
   <tr><td colspan="2" align="center" style="font-size:10pt; color:black;"><strong>Materia: <%= materia %></strong></td></tr>
   <tr><td colspan="2" align="center" style="font-size:10pt; color:black;"><strong><%= aca.carga.CargaUnidadUtil.getUnidadNombre(conEnoc, cursoCargaId, unidadId)%></strong></td></tr>
</table>
<form name="frmUnidad" method="post" action="tema?CursoCargaId=<%=cursoCargaId%>&Unidad<%= unidadId %>">
  <input type="hidden" name="Accion">
  <table style="margin: 0 auto;  width:50%"> 
    <tr><td>&nbsp;</td></tr>     
    <tr>
      <td style="background-color: #5FB404;font-size: 10pt; color:white;" align="center" colspan="2" align="center" width="50%"><b>Temas</b></td>     
    </tr>
     <tr><td>&nbsp;</td></tr> 
    <tr>
      <td><strong><spring:message code="aca.Clave"/></strong></td>
      <td>
      <% if (accion==1 ){%>         
        <input name="TemaId" type="text" class="text" id="TemaId" value="<%= unidadId+CargaUnidadTema.getTemaId()%>" size="5" maxlength="2">        
      <%}else{ %>
        <input name="TemaId" type="text" class="text" id="TemaId" value="<%= CargaUnidadTema.getTemaId()%>" size="5" maxlength="2">
      <% }%> 
      </td>         
    </tr>
    <tr> 
      <td><strong>Tema</strong></td>
      <td>         
        <input name="Nombre" type="text" class="text" id="Nombre" value="<%=CargaUnidadTema.getTemaNombre() %>" size="30" maxlength="70">        
      </td>          
    </tr>
     <tr> 
      <td><strong><spring:message code="aca.Fecha"/></strong></td>
      <td>         
        <input name="Fecha" type="text" class="text" id="Fecha" size="10" maxlength="10" onfocus="focusFecha(this);" value="<%= CargaUnidadTema.getFecha()%>">
        <img class="button" alt="calendario" id="fotoFecha" src="../../imagenes/calendario.gif" onClick="javascript:showCalendar(this, document.getElementById('Fecha'), 'dd/mm/yyyy',null,1,-1,-1);"> (DD/MM/AAAA)        
      </td>          
    </tr>
    <tr> 
      <td><strong>Orden</strong></td>
      <td>         
        <input name="Orden" type="text" class="text" id="Orden" value="<%=CargaUnidadTema.getOrden()%>" size="3" maxlength="2">        
      </td>          
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr><td colspan="4" align="center"><input type="submit" value="Guardar" onclick="return Guardar();" /></td></tr>
    <tr><td>&nbsp;</td></tr>
	<tr><td style="background-color: #5FB404;" align="center" colspan="4" align="center" >&nbsp;</td></tr>
  </table>
</form>
</body>   
<%@ include file= "../../cierra_enoc.jsp" %>