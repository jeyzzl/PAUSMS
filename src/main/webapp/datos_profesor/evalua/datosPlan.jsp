<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="GrupoPlan" scope="page" class="aca.carga.CargaGrupoPlan"/>
<jsp:useBean id="GrupoPlanU" scope="page" class="aca.carga.CargaGrupoPlanUtil"/>

<head>
<script type="text/javascript">
	function guardar(){
			if(document.frmAlta.Lugar.value != "" &&
			  document.frmAlta.Estudios.value != "" &&
			  document.frmAlta.Horario.value != "" &&
			  document.frmAlta.Ocupacion.value != "" &&
			  document.frmAlta.Tiempo.value != "" &&
			  document.frmAlta.Oficina.value != "" &&
			  document.frmAlta.Telefono.value != "" &&
			  document.frmAlta.Atencion.value != "" &&
			  document.frmAlta.Correo.value != "" &&
			  document.frmAlta.Descripcion.value != "" &&
			  document.frmAlta.Perspectiva.value != "" ){
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
	 String cursoCargaId = (String) session.getAttribute("CursoCargaId");
	 String maestro 	    = (String) session.getAttribute("Maestro");
	 String materia 	    = (String) session.getAttribute("Materia");
     int accion 		 = request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
     String Resultado    = "";
   
     //operaciones a realizar 
	 switch (accion){
	 
	    case 1: { // Consulta
			GrupoPlan.mapeaRegId(conEnoc, cursoCargaId);
			break;
		}	
	       
	    case 2: { // Grabar y modificar
	    	GrupoPlan.setCursoCargaId(cursoCargaId);
	    	GrupoPlan.setEstudios(request.getParameter("Estudios"));
	    	GrupoPlan.setHorario(request.getParameter("Horario"));
	    	GrupoPlan.setOcupacion(request.getParameter("Ocupacion"));
	    	GrupoPlan.setTiempo(request.getParameter("Tiempo"));
	    	GrupoPlan.setLugar(request.getParameter("Lugar"));
	    	GrupoPlan.setOficina(request.getParameter("Oficina"));
	    	GrupoPlan.setTelefono(request.getParameter("Telefono"));
	    	GrupoPlan.setAtencion(request.getParameter("Atencion"));
	    	GrupoPlan.setCorreo(request.getParameter("Correo"));
	    	GrupoPlan.setDescripcion(request.getParameter("Descripcion"));
	    	GrupoPlan.setPerspectiva(request.getParameter("Perspectiva"));
			GrupoPlan.setEstado("A");
	    	
	    	if (GrupoPlanU.existeReg(conEnoc, cursoCargaId) == false){
				if (GrupoPlanU.insertReg(conEnoc, GrupoPlan)){
					Resultado = "!! Los Datos han sido Guardados Correctamente !!";					
				}else{
					Resultado = "No Grabó: "+GrupoPlan.getCursoCargaId();
				}
			}else{	
				if (GrupoPlanU.updateReg(conEnoc, GrupoPlan)){ 
					Resultado = "!! Los Datos han sido Modificados Correctamente !!";				
				}else{
					Resultado = "No Cambió: "+GrupoPlan.getCursoCargaId();
				}
			}
			break;	
			
		}
	  
	 }
	     
%>
<body>
<table style="width:100%">
   <tr>
     <td colspan="4" align="center" style="font-size:12pt"><strong>Datos Generales del Curso:</strong></td>
   </tr>
   <tr>
     <td colspan="4" align="center" style="font-size:9pt"><strong><%= materia %></strong></td>
   </tr>  
   <tr>
     <td colspan="4" align="center" style="font-size:9pt"><strong>&nbsp;</strong></td>
   </tr>
    <tr>
     <td colspan="4" align="center" style="font-size:12pt"><strong><%= Resultado %></strong></td>
   </tr> 
   <tr>
     <td><a href="plan">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
   </tr>
</table>
<form name="frmAlta" method="post" action="datosPlan?CursoCargaId=<%=cursoCargaId%>&maestro=<%=maestro%>&materia=<%=materia%>">
  	  <input type="hidden" name="Accion">
  	   <table style="margin: 0 auto;  width:90%"> 
  	    <tr><td>&nbsp;</td></tr>
        <tr><td>&nbsp;</td></tr> 
        <tr>
          <td style="background-color: #5FB404;font-size: 10pt; color:white;" align="center" colspan="2" align="center" width="50%"><b><spring:message code="aca.Materia"/></b></td>
          <td style="background-color: #5FB404;font-size: 10pt; color:white;" align="center" colspan="2" align="center" width="50%"><b>Instructor</b></td>
        </tr>
        <tr>
           <td><strong>Aula</strong></td>
        <td>         
            <input name="Lugar" type="text" class="text" id="Lugar" value="<%= GrupoPlan.getLugar()%>" size="30" maxlength="70">        
          </td>          
          <td ><strong>Estudios Realizados</strong></td>
          <td > 
            <textarea id="Estudios" name="Estudios" cols="60" rows="4"><%= GrupoPlan.getEstudios() %></textarea>
          </td>
        </tr>
        <tr> 
          <td><strong>Horario de Clases</strong></td>
          <td>         
            <input name="Horario" type="text" class="text" id="Horario" value="<%= GrupoPlan.getHorario()%>" size="30" maxlength="100">        
          </td>          
          <td ><strong>Ocupación Actual</strong></td>
          <td > 
            <textarea id="Ocupacion" name="Ocupacion" cols="60" rows="4"><%= GrupoPlan.getOcupacion() %></textarea>
          </td>
        </tr>
        <tr> 
          <td><strong>Tiempo fuera del aula<strong></td>
          <td>         
            <input name="Tiempo" type="text" class="text" id="Tiempo" value="<%= GrupoPlan.getTiempo()%>" size="30" maxlength="100">        
          </td>          
          <td><strong>Cubículo</strong></td>
          <td>
           <input name="Oficina" type="text" class="text" id="Oficina" value="<%= GrupoPlan.getOficina()%>" size="25" maxlength="100">
           <strong>y Tel de Oficina</strong>&nbsp;
           <input name="Telefono" type="text" class="text" id="Telefono" value="<%= GrupoPlan.getTelefono() %>" size="25" maxlength="12">
          </td>
        </tr>
        <tr>
          <td colspan="2">&nbsp;</td>
          <td><strong>Horario de Atención</strong></td>
          <td> 
            <input name="Atencion" type="text" class="text" id="Atencion" value="<%= GrupoPlan.getAtencion() %>" size="30" maxlength="50">
          </td>
        </tr>
        <tr>
           <td colspan="2">&nbsp;</td>
          <td><strong>Correo Electrónico<strong></td>
          <td>         
            <input name="Correo" type="text" class="text" id="Correo" value="<%= GrupoPlan.getCorreo() %>" size="30" maxlength="100">        
          </td>     
        </tr>
        <tr>
          <td><strong>Descripción del Curso</strong></td>
          <td>         
            <textarea id="Descripcion" name="Descripcion" cols="40" rows="4"><%= GrupoPlan.getDescripcion()%></textarea>        
          </td>   
          <td colspan="2">&nbsp;</td>   
        </tr>
        <tr>
          <td><strong>Perspectiva del Curso</strong></td>
          <td>         
            <textarea id="Perspectiva" name="Perspectiva" cols="40" rows="4"><%=GrupoPlan.getPerspectiva()%></textarea>        
          </td>   
          <td colspan="2">&nbsp;</td>   
        </tr>
      <tr><td>&nbsp;</td></tr>
      <tr><td colspan="4" align="center"><input type="submit" value="Guardar" onclick="return guardar();" /></td></tr>
      <tr><td>&nbsp;</td></tr>
	  <tr><td style="background-color: #5FB404;" align="center" colspan="4" align="center" >&nbsp;</td></tr>
    </table>
   </form>
</body>   
<%@ include file= "../../cierra_enoc.jsp" %>