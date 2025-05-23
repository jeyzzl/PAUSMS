<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="GrupoBiblio" scope="page" class="aca.carga.CargaGrupoBiblio"/>
<jsp:useBean id="GrupoBiblioU" scope="page" class="aca.carga.CargaGrupoBiblioUtil"/>
<head>
<script type="text/javascript">	
	function guardar(){
			if(document.frmAlta.BiblioId.value != "" && document.frmAlta.Bibliografia.value != ""  && document.frmAlta.Orden.value != "" ){
			  document.frmAlta.action += "&Accion=2";
				return true;
			}else{
				alert("Fill in the required fields (*) in order to save");
			}
			return false;
	}
	
	function Nuevo(){		
		document.frmAlta.BiblioId.value 		= " ";
		document.frmAlta.Bibliografia.value 	= " ";
		document.frmAlta.Orden.value 	        = " ";
		document.frmAlta.action += "&Accion=1";
			
	}
</script>
</head>
<% 
	 String cursoCargaId    = (String) session.getAttribute("CursoCargaId");
	 String maestro 	    = (String) session.getAttribute("Maestro");
	 String materia 	    = (String) session.getAttribute("Materia");
 
     int accion 		 = request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
     String Resultado    = ""; 
     
 	if ( accion == 1 ){
 		GrupoBiblio.setCursoCargaId(cursoCargaId);
 		GrupoBiblio.setBiblioId(GrupoBiblioU.maximoReg(conEnoc, cursoCargaId));
	}else{
		GrupoBiblio.setBiblioId(request.getParameter("BiblioId"));
 	}     
     
    //operaciones a realizar 
	switch (accion){
	 
		case 0: { // Consulta
	    	GrupoBiblio.mapeaRegId(conEnoc, cursoCargaId, request.getParameter("BiblioId"));
			break;
		}
	    
	    case 1: { // Nuevo			
			Resultado = "Fill out the entire form¡";
			break;
		}    
	       
	    case 2: { // Grabar y modificar
	    	GrupoBiblio.setCursoCargaId(cursoCargaId);
	    	GrupoBiblio.setBiblioId(request.getParameter("BiblioId"));
	    	GrupoBiblio.setBibliografia(request.getParameter("Bibliografia"));
	    	GrupoBiblio.setOrden(request.getParameter("Orden"));
	    	
	    	if (GrupoBiblioU.existeReg(conEnoc, cursoCargaId, request.getParameter("BiblioId")) == false){
				if (GrupoBiblioU.insertReg(conEnoc, GrupoBiblio)){
					Resultado = "Saved";
				}else{
					Resultado = "Error saving: "+GrupoBiblio.getCursoCargaId();
				}
			}else{	
				if (GrupoBiblioU.updateReg(conEnoc, GrupoBiblio)){ 
					Resultado = "Updated";
				}else{
					Resultado = "Error udpating: "+GrupoBiblio.getCursoCargaId();
				}
			}
			break;	
			
		}
	    
		case 3:{//borrar
			
			GrupoBiblio.setCursoCargaId(cursoCargaId);
			GrupoBiblio.setBiblioId(request.getParameter("BiblioId"));			
			
			if (GrupoBiblioU.existeReg(conEnoc, cursoCargaId, request.getParameter("BiblioId") ) == true){
				if (GrupoBiblioU.deleteReg(conEnoc, cursoCargaId, request.getParameter("BiblioId"))){
					Resultado = "Deleted:"+request.getParameter("BiblioId");
				}else{
					Resultado = "Error deleting:";
				}	
			}else{
					Resultado = "Not found:";
			}
			break;
    	}
	      
	}
	 
     ArrayList<aca.carga.CargaGrupoBiblio> biblio =  GrupoBiblioU.getListBiblio(conEnoc, cursoCargaId,"ORDER BY ORDEN") ;
%>
<body>
<table style="width:100%">
   <tr>
     <td>&nbsp;</td>
   </tr>
   <tr>
     <td><a href="plan">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
   </tr>  
   <tr>
     <td colspan="4" align="center" style="font-size:9pt"><strong>&nbsp;</strong></td>
   </tr>
    <tr>
     <td colspan="4" align="center" style="font-size:12pt"><strong><%= Resultado %></strong></td>
   </tr> 
   <tr> 
</table>
<form name="frmAlta" method="post" action="bibliografia?CursoCargaId=<%=cursoCargaId%>&maestro=<%=maestro%>&materia=<%=materia%>">
  <input type="hidden" name="Accion">
  <table style="margin: 0 auto; width:50%" class="tabla"> 
    <tr>
      <th colspan="2"><b>Bibliography</b></th>     
    </tr>
     <tr><td>&nbsp;</td></tr>
    <tr>
      <td><strong><spring:message code="aca.Clave"/></strong></td>
      <td>
        <input name="BiblioId" type="text" class="text" id="BiblioId" value="<%= GrupoBiblio.getBiblioId() %>" size="5" maxlength="2">
      </td>         
    </tr>
    <tr> 
      <td><strong>Bibliography</strong></td>
      <td>         
        <textarea id="Bibliografia" name="Bibliografia" cols="100" rows="7"><%= GrupoBiblio.getBibliografia()%></textarea>        
      </td>          
    </tr>
    <tr> 
      <td><strong>Order</strong></td>
      <td>         
        <input name="Orden" type="text" class="text" id="Orden" value="<%= GrupoBiblio.getOrden()%>" size="3" maxlength="2">        
      </td>          
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr><td colspan="4" align="center">
          <input type="submit" value="Save" onclick="return guardar();" />
          <input type="submit" value="New" onclick="return Nuevo();" />
        </td>
    </tr>
    <tr><td>&nbsp;</td></tr>
	<tr><th colspan="4">&nbsp;</th></tr>
  </table>
</form>
<table style="margin: 0 auto; width:70%">
  <tr>
    <td class="th2" align="center" width="10%"><spring:message code="aca.Operacion"/></td>
     <td class="th2" align="center" width="5%">Order</td>
    <td class="th2" align="center" width="30%">Bibliography</td>
  </tr>
<%		
		for (int i=0; i<biblio.size(); i++){
		  aca.carga.CargaGrupoBiblio bibliografia = (aca.carga.CargaGrupoBiblio) biblio.get(i);
%>
  <tr>
    <td align="center">
      <a href="bibliografia?Accion=0&BiblioId=<%= bibliografia.getBiblioId() %>">
        <img title="Edit" src="../../imagenes/editar.gif" alt="Modificar" >
      </a>
      <a href="bibliografia?Accion=3&BiblioId=<%= bibliografia.getBiblioId() %>"> 
       <img src="../../imagenes/no.png" alt="Eliminar" >
      </a>
    </td>
    <td align="left"><%= bibliografia.getOrden() %></td>
    <td align="left"><%= bibliografia.getBibliografia()%></td>
  </tr>
<% 		}%>
</table>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>