<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="EjeUtil" scope="page" class="aca.catalogo.CatEjeUtil"/>
<jsp:useBean id="GrupoEje" scope="page" class="aca.carga.CargaGrupoEje"/>
<jsp:useBean id="GrupoEjeU" scope="page" class="aca.carga.CargaGrupoEjeUtil"/>
<head>
<script type="text/javascript">	
	
	function Guardar(){
			if(document.frmAlta.Descripcion.value != ""){
			  document.frmAlta.action += "&Accion=2";
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
     //En base a carrera trae el nivelId
     String carreraId	 = aca.carga.CargaGrupoUtil.getCarreraId(conEnoc, cursoCargaId);
     String nivelId   	 = aca.catalogo.CatCarreraUtil.getNivelId(conEnoc, carreraId);
     
     int accion 		 = request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
     String Resultado    = "";

     ArrayList<aca.catalogo.CatEje> lisEjes = EjeUtil.getListEjeNivel(conEnoc, nivelId, "ORDER BY EJE_ID");
      
     //operaciones a realizar 
	 switch (accion){
	     	    
	    case 2: { //Grabar los ejes transversales
	    	
	    	for (int i=0; i<lisEjes.size();i++){
	    	aca.catalogo.CatEje eje = (aca.catalogo.CatEje) lisEjes.get(i);
	    	String descripcion = request.getParameter("Descripcion"+eje.getEjeId());
	    	if (descripcion!= null){
	    	GrupoEje.setCursoCargaId(cursoCargaId);
	    	GrupoEje.setEjeId(eje.getEjeId());
	    	GrupoEje.setDescripcion(request.getParameter("Descripcion"+eje.getEjeId()));
	    	
	    	
	    	if (GrupoEjeU.existeReg(conEnoc, eje.getEjeId(), cursoCargaId ) == false){
				if (GrupoEjeU.insertReg(conEnoc,GrupoEje)){
					Resultado = "Saved";
				}else{
					Resultado = "Error saving! : "+GrupoEje.getCursoCargaId();
				}
			}else{	
				if (GrupoEjeU.updateReg(conEnoc,GrupoEje)){ 
					Resultado = "Updated";
				}else{
					Resultado = "Error updating! : "+GrupoEje.getCursoCargaId();
				}
			}
	       }
	      }
	    	break;
	    }
	  
	 }
	     
%>
<table style="width:90%">
   <tr>
     <td colspan="4" align="center" style="font-size:12pt"><strong>Axis Capture</strong></td>
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
<form name="frmAlta" method="post" action="ejes?CursoCargaId=<%=cursoCargaId%>&maestro=<%=maestro%>&materia=<%=materia%>">
  	  <input type="hidden" name="Accion">
  	   <table style="margin: 0 auto;  width:80%" class="tabla"> 
  	    <tr>
          <th colspan="4"><b>Addressing the Transversal Axis</b></th>
        </tr>
<%      for (int i=0; i<lisEjes.size(); i++ ){ 
	       aca.catalogo.CatEje eje = (aca.catalogo.CatEje) lisEjes.get(i);
	       GrupoEje.mapeaRegId(conEnoc, cursoCargaId, eje.getEjeId());
%> 
        <tr>
          <td colspan="2"><b><%= eje.getEjeNombre()%></b>
          <td colspan="2" title=" <%= eje.getDescripcion()%>">
            <textarea id="Descripcion" name="Descripcion<%= eje.getEjeId()%>" cols="40" rows="4"><%= GrupoEje.getDescripcion()%></textarea> 
          </td>
        </tr>
<% 
      
       }
%>    
      <tr><td colspan="4" align="center"><input type="submit" value="Save" onclick="return Guardar();" /></td></tr>
      <tr><td>&nbsp;</td></tr>
	  <tr><th colspan="4">&nbsp;</th></tr>
    </table>
   </form>
<%@ include file= "../../cierra_enoc.jsp" %>