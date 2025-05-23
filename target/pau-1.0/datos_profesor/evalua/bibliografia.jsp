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
			if(document.frmAlta.Bibliografia.value != "" ){
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
	 String cursoCargaId 	= (String) session.getAttribute("CursoCargaId");
	 String maestro 	    = (String) session.getAttribute("Maestro");
	 String materia 	    = (String) session.getAttribute("Materia");
 
     int accion 		 = request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
     String Resultado    = ""; 
     
     //operaciones a realizar 
	 switch (accion){
	 
	    case 1: { // Consulta	    	
	    	GrupoBiblio = GrupoBiblioU.mapeaRegId(conEnoc, cursoCargaId, request.getParameter("Bibliografia"));
			break;
		}
	       
	    case 2: { // Grabar y modificar
	    	GrupoBiblio.setCursoCargaId(cursoCargaId);
	    	GrupoBiblio.setBibliografia(request.getParameter("Bibliografia"));
	    	
	    	if (GrupoBiblioU.existeReg(conEnoc, cursoCargaId, request.getParameter("Bibliografia")) == false){
				if (GrupoBiblioU.insertReg(conEnoc, GrupoBiblio)){
					Resultado = "!! Los Datos han sido Guardados Correctamente !!";					
				}else{
					Resultado = "No Grabó: "+GrupoBiblio.getCursoCargaId();
				}
			}else{	
				if (GrupoBiblioU.updateReg(conEnoc, GrupoBiblio)){ 
					Resultado = "!! Los Datos han sido Modificados Correctamente !!";					
				}else{
					Resultado = "No Cambió: "+GrupoBiblio.getCursoCargaId();
				}
			}
			break;	
			
		}
	      
	 }
	     
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
  <table style="margin: 0 auto;  width:50%"> 
    <tr><td>&nbsp;</td></tr>
    <tr><td>&nbsp;</td></tr> 
    <tr>
      <td style="background-color: #5FB404;font-size: 10pt; color:white;" align="center" colspan="2" align="center" width="50%"><b>Bibliografía</b></td>     
    </tr>
     <tr><td>&nbsp;</td></tr> 
    <tr> 
      <td>         
        <textarea id="Bibliografia" name="Bibliografia" cols="80" rows="20"><%= GrupoBiblio.getBibliografia()%></textarea>        
      </td>          
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr><td colspan="4" align="center"><input type="submit" value="Guardar" onclick="return guardar();" /></td></tr>
    <tr><td>&nbsp;</td></tr>
	<tr><td style="background-color: #5FB404;" align="center" colspan="4" align="center" >&nbsp;</td></tr>
  </table>
</form>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>