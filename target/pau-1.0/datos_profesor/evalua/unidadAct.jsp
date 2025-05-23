<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="UnidadAct" scope="page" class="aca.carga.CargaUnidadActividad"/>
<jsp:useBean id="UnidadActU" scope="page" class="aca.carga.CargaUnidadActividadUtil"/>
<jsp:useBean id="CatActividadU" scope="page" class="aca.catalogo.ActividadUtil"/>

<head>
<script type="text/javascript">
	 function Guardar(){
		if(document.frmActividad.Actividad.value != "" &&
		   document.frmActividad.Comentario.value != "" && 
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
	String cursoCargaId 	= (String) session.getAttribute("CursoCargaId");
	String maestro 	    	= (String) session.getAttribute("Maestro");
	String materia 	    	= (String) session.getAttribute("Materia");
    String unidadId     	= request.getParameter("Unidad");
	String temaId       	= request.getParameter("Tema");
	String actividadId  	= request.getParameter("Actividad");
	String comentario		= "";
	
	int accion 		    	= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
	String Resultado    	= "";
	
	ArrayList<aca.catalogo.CatActividad> lisActividades = CatActividadU.getListAll(conEnoc,  "ORDER BY ACTIVIDAD_ID");
	
	 //operaciones a realizar 
	switch (accion){	 
		case 2: { //Grabar las actividades
			for (int j=0; j<lisActividades.size(); j++){
				aca.catalogo.CatActividad act = (aca.catalogo.CatActividad) lisActividades.get(j);
		    	String actividad = request.getParameter("Actividad"+ (temaId+act.getActividadId()));
		    	if (actividad!= null){
		    		UnidadAct.setCursoCargaId(cursoCargaId);
		    		UnidadAct.setActividadId(temaId+act.getActividadId());
		    		if(!act.getActividadId().equals("28")){
		    			UnidadAct.setActividadNombre(act.getDescripcion());
		    		}else{
		    			UnidadAct.setActividadNombre(request.getParameter("Descripcion"));
		    		}	    		
		    		
		    		comentario = request.getParameter("Comentario"+(temaId+act.getActividadId()))==null?"-":request.getParameter("Comentario"+(temaId+act.getActividadId()));		    		
		    		UnidadAct.setComentario(comentario);
		    		UnidadAct.setValor(request.getParameter("Valor"+(temaId+act.getActividadId())));	    	
		    	
			    	if (UnidadActU.existeReg(conEnoc, cursoCargaId, temaId+act.getActividadId()) == false){
						if (UnidadActU.insertReg(conEnoc, UnidadAct)){
							Resultado = "Grabado: "+UnidadAct.getCursoCargaId();							
						}else{
							Resultado = "No Grabó: "+UnidadAct.getCursoCargaId();
						}
					}else{	
						if (UnidadActU.updateReg(conEnoc, UnidadAct)){ 
							Resultado = "La estrategia:"+UnidadAct.getCursoCargaId()+"ha sido modificada";							
						}else{
							Resultado = "No Cambió: "+UnidadAct.getCursoCargaId();
						}
					}
	       		}else{
	       			
	       		}
	   		}
			break;
		}
		
		case 3:{//borrar			
			UnidadAct.setCursoCargaId(cursoCargaId);
			UnidadAct.setActividadId(actividadId);
			
			if (UnidadActU.existeReg(conEnoc, cursoCargaId, actividadId) == true){
				if (UnidadActU.deleteReg(conEnoc, cursoCargaId, actividadId)){
					Resultado = "Borrado: "+UnidadAct.getCursoCargaId();					
				}else{
					Resultado = "No Borró: "+UnidadAct.getCursoCargaId();
				}	
			}else{
				Resultado = "No existe: "+UnidadAct.getCursoCargaId();
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
<form name="frmActividad" method="post" action="unidadAct?CursoCargaId=<%=cursoCargaId%>&Unidad=<%= unidadId %>&Tema=<%=temaId%>">
  <input type="hidden" name="Accion">
  <table style="margin: 0 auto;  width:70%"> 
    <tr><td style="font-size:12pt;" align="center" colspan="3">Materia: <%=materia%></td></tr>  
    <tr><td style="font-size:11pt; color:black;" align="center" colspan="3"><%= aca.carga.CargaUnidadUtil.getUnidadNombre(conEnoc, cursoCargaId, unidadId)%></td></tr>
    <tr><td style="font-size:11pt; color:black;" align="center" colspan="3"><%= aca.carga.CargaUnidadTemaUtil.getNombreTema(conEnoc, cursoCargaId, temaId)%></td></tr>
    <tr>
      <td style="background-color: #5FB404;font-size: 10pt; color:white;" align="center" colspan="3" align="center" width="50%"><b> *<spring:message code="aca.Actividades"/></b></td>     
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr>
      <td align="center">Actividad</td>
      <td align="center"><spring:message code="aca.Comentario"/></td>
      <td align="center">Valor</td>
    </tr>
<% 
	for (int j=0; j < lisActividades.size(); j++){
		aca.catalogo.CatActividad actividad = (aca.catalogo.CatActividad) lisActividades.get(j);
		aca.carga.CargaUnidadActividad unidAct =  new aca.carga.CargaUnidadActividad();
	
		String strCheck = "";	
		unidAct.setCursoCargaId(cursoCargaId);
		unidAct.setActividadId(temaId+actividad.getActividadId());
    	if (UnidadActU.existeReg(conEnoc, cursoCargaId, temaId+actividad.getActividadId())){
    		unidAct.mapeaRegId(conEnoc, cursoCargaId, temaId+actividad.getActividadId());
    		strCheck = "checked";
    	}
%>    	<tr>
		  <td align="left">
		   <%if (!actividad.getActividadId().equals("28")){ %>
		    <input type="checkbox" id="Actividad" name="Actividad<%= temaId+actividad.getActividadId()%>" value="<%= actividad.getActividadId() %>" <%= strCheck%> /> 
		    <input style="background-color:#E6E6E6" type="text" id="Descripcion" name="Descripcion<%= temaId+actividad.getActividadId()%>" value="<%= actividad.getDescripcion() %>" size="35" Disabled />   
		  <%}else{ %>
		    <input type="checkbox" id="Actividad" name="Actividad<%= temaId+actividad.getActividadId()%>" value="<%= UnidadAct.getActividadId()%>" <%= strCheck%> size="35" maxlength="50" /> 
		    <input type="text" id="Descripcion" name="Descripcion" value="<%= unidAct.getActividadNombre() %>" />(Otro)
		  <%} %> 
		  </td>
		  <td align="center"> 
		     <textarea id="Comentario" name="Comentario<%=temaId+actividad.getActividadId()%>" cols="30" rows="4"><%= unidAct.getComentario() %></textarea> 
		  </td>
		  <td align="center">
		    <input type="text" id="Valor" name="Valor<%= temaId+actividad.getActividadId()%>" value="<%= unidAct.getValor() %>" size="6" maxlength="5" />
		  </td>
		</tr>
<%	}	%>  
    <tr><td>&nbsp;</td></tr>
    <tr><td colspan="4" align="center"><input type="submit" value="Guardar" onclick="return Guardar();" /></td></tr>
    <tr><td>&nbsp;</td></tr>
	<tr><td style="background-color: #5FB404;" align="center" colspan="4" align="center" >&nbsp;</td></tr>
  </table>
</form>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>