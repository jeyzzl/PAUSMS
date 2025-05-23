<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="UnidadAct" scope="page" class="aca.carga.CargaUnidadActividad"/>
<jsp:useBean id="UnidadActU" scope="page" class="aca.carga.CargaUnidadActividadUtil"/>

<head>
<script type="text/javascript">
	 function Guardar(){
			if(document.frmActividad.Comentario.value != "" && 
			   document.frmActividad.Valor.value != "" ){  
			   document.frmActividad.action += "&Accion=2";
				return true;
			}else{
				alert("Complete las casillas requeridas (*) para poder guardar");
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
	String actividadId  = request.getParameter("Actividad");
	
	String strTexto		= " ";
	
	int accion 		    = request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
		
	UnidadAct.mapeaRegId(conEnoc, cursoCargaId, actividadId);
	
	String Resultado    = "";	
	
	 //operaciones a realizar 
	 switch (accion){
	 
	 case 2: { //Modificar la actividad	 	
		
	    if(actividadId.substring(4,5).equals("28")){	    	
	    	UnidadAct.setActividadNombre(request.getParameter("Nombre"));		
	    }
	    UnidadAct.setComentario(request.getParameter("Comentario"));
	    UnidadAct.setValor(request.getParameter("Valor"));	    	
	    if (UnidadActU.existeReg(conEnoc, cursoCargaId, actividadId)){
			if (UnidadActU.updateReg(conEnoc, UnidadAct)){ 
				Resultado = "La actividad:"+UnidadAct.getCursoCargaId()+"ha sido modificada";				
			}else{
				Resultado = "No Cambió: "+UnidadAct.getCursoCargaId();
			}
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
</table>
<form name="frmActividad" method="post" action="unidadActEdita?CursoCargaId=<%=cursoCargaId%>&Unidad=<%= unidadId %>&Tema=<%=temaId%>">
  <input type="hidden" name="Accion">
  <input type="hidden" name="Actividad" value=<%=actividadId%>>
  <input type="hidden" name="Maestro" value=<%=maestro%>>
  <input type="hidden" name="Materia" value=<%=materia%>>
  
  <table style="margin: 0 auto;  width:70%">
    <tr><td style="font-size: 12pt;"><%= aca.catalogo.CatActividad.getDescripcion(conEnoc,actividadId.substring(4,6)) %></td></tr>
    <tr>
      <td style="background-color: #5FB404;font-size: 10pt; color:white;" align="center" colspan="3" align="center" width="50%"><b> *<spring:message code="aca.Actividades"/></b></td>     
    </tr>    
    <tr> 
      <td align="center"><spring:message code="aca.Nombre"/></td>   
      <td align="center"><spring:message code="aca.Comentario"/></td>
      <td align="center">Valor</td>
    </tr>
  	<tr>
<% 	if (!actividadId.substring(4,6).equals("28")) strTexto = "readonly"; %>
  	  <td align="center"> 
		<input type="text" id="Nombre" name="Nombre" size="30" maxlength="70" value="<%=UnidadAct.getActividadNombre()%>" <%=strTexto%>> 
	  </td>	  
	  <td align="center"> 
		<textarea id="Comentario" name="Comentario" cols="30" rows="4"><%= UnidadAct.getComentario() %></textarea>
	  </td>
	  <td align="center">
	    <input type="text" id="Valor" name="Valor" value="<%= UnidadAct.getValor() %>" size="5" maxlength="3" />
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