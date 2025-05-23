<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>

<link rel="stylesheet" type="text/css" media="all" href="../../jscalendar-1.0/calendar-blue2.css" title="win2k-cold-1" /> 
<script type="text/javascript" src="../../jscalendar-1.0/calendar.js"></script>
<script type="text/javascript" src="../../jscalendar-1.0/lang/calendar-es.js"></script>
<script type="text/javascript" src="../../jscalendar-1.0/calendar-setup.js"></script>

<jsp:useBean id="nivelL" scope="page" class="aca.catalogo.CatNivelUtil"/>
<jsp:useBean id="modalidadL" scope="page" class="aca.catalogo.ModalidadUtil"/>
<jsp:useBean id="vacacion" scope="page" class="aca.alumno.AlumVacacion" />
<jsp:useBean id="vacacionL" scope="page" class="aca.alumno.AlumVacacionLista" />

<%
	String nivelId				= request.getParameter("NivelId");
	String modalidadId			= request.getParameter("ModalidadId");

	String sResultado			= "";
	String strAccion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
	int nAccion					= Integer.parseInt(strAccion);
	
	// Lista de niveles
	ArrayList<aca.catalogo.CatNivel> lisNivel		= nivelL.getListAll(conEnoc, "ORDER BY 1");
	
	// Lista de modalidades
	ArrayList<aca.catalogo.CatModalidad> lisModo	= modalidadL.getListAll(conEnoc, "ORDER BY 1");		

	switch(nAccion){
		case 1:{ // Nuevo
			sResultado = "Llene el formulario correctamente!";
		break;
		}
		
		case 2:{ // Grabar
			vacacion.setNivelId(request.getParameter("NivelId").trim());
			vacacion.setModalidadId(request.getParameter("ModalidadId").trim());
			vacacion.setFExamen(request.getParameter("fExamen"));
			vacacion.setFInicio(request.getParameter("fInicio"));
			vacacion.setFFinal(request.getParameter("fFinal"));
			if(vacacionL.existeReg(conEnoc, request.getParameter("NivelId").trim(), request.getParameter("ModalidadId").trim()) == false){
				if(vacacionL.insertReg(conEnoc, vacacion)){
					sResultado = "Grabado: "+aca.catalogo.CatNivelUtil.getNivelNombre(conEnoc, vacacion.getNivelId())+
					" - "+aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc,vacacion.getModalidadId());					
				}else{
					sResultado = "No Grabo: "+aca.catalogo.CatNivelUtil.getNivelNombre(conEnoc, vacacion.getNivelId())+
					" - "+aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc,vacacion.getModalidadId());
				}
			}else{
				sResultado = "Ya Existe: "+aca.catalogo.CatNivelUtil.getNivelNombre(conEnoc, vacacion.getNivelId())+
				" - "+aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc,vacacion.getModalidadId());
			}
		break;
		}
		
		case 3:{ // Modificar			
			vacacion.setNivelId(request.getParameter("NivelId").trim());
			vacacion.setModalidadId(request.getParameter("ModalidadId").trim());
			vacacion.setFExamen(request.getParameter("fExamen"));
			vacacion.setFInicio(request.getParameter("fInicio"));
			vacacion.setFFinal(request.getParameter("fFinal"));
			if(vacacionL.existeReg(conEnoc, request.getParameter("NivelId").trim(), request.getParameter("ModalidadId").trim()) == true){
				if(vacacionL.updateReg(conEnoc, vacacion)){
					sResultado = "Modificado: "+aca.catalogo.CatNivelUtil.getNivelNombre(conEnoc, vacacion.getNivelId())+
					" - "+aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc,vacacion.getModalidadId());
					conEnoc.commit();
				}else{
					sResultado = "No Cambio: "+aca.catalogo.CatNivelUtil.getNivelNombre(conEnoc, vacacion.getNivelId())+
					" - "+aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc,vacacion.getModalidadId());
				}
			}else{
				sResultado = "Ya Existe: "+aca.catalogo.CatNivelUtil.getNivelNombre(conEnoc, vacacion.getNivelId())+
				" - "+aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc,vacacion.getModalidadId());
			}
		break;
		}
		
		case 4:{ // Consultar
			vacacion.setNivelId(request.getParameter("NivelId").trim());
			vacacion.setModalidadId(request.getParameter("ModalidadId").trim());
			vacacion.setFExamen(request.getParameter("fExamen"));
			vacacion.setFInicio(request.getParameter("fInicio"));
			vacacion.setFFinal(request.getParameter("fFinal"));
			if(vacacionL.existeReg(conEnoc, request.getParameter("NivelId").trim(), request.getParameter("ModalidadId").trim()) == true){
				vacacion = vacacionL.mapeaRegId(conEnoc, request.getParameter("NivelId").trim(), request.getParameter("ModalidadId").trim());
				sResultado = "Consulta";
			}else{
				sResultado = "No Existe: "+aca.catalogo.CatNivelUtil.getNivelNombre(conEnoc, vacacion.getNivelId())+
				" - "+aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc,vacacion.getModalidadId());
			}
		break;
		}
		
		case 5:{ // Borrar
			vacacion.setNivelId(request.getParameter("NivelId").trim());
			vacacion.setModalidadId(request.getParameter("ModalidadId").trim());
			vacacion.setFExamen(request.getParameter("fExamen"));
			vacacion.setFInicio(request.getParameter("fInicio"));
			vacacion.setFFinal(request.getParameter("fFinal"));
			if(vacacionL.existeReg(conEnoc, request.getParameter("NivelId").trim(), request.getParameter("ModalidadId").trim()) == true){
				if(vacacionL.deleteReg(conEnoc, request.getParameter("NivelId").trim(), request.getParameter("ModalidadId").trim())){
					sResultado = "Borrado: "+aca.catalogo.CatNivelUtil.getNivelNombre(conEnoc, vacacion.getNivelId())+
					" - "+aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc,vacacion.getModalidadId());
					conEnoc.commit();
				}else{
					sResultado = "No Borro: "+aca.catalogo.CatNivelUtil.getNivelNombre(conEnoc, vacacion.getNivelId())+
					" - "+aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc,vacacion.getModalidadId());
				}
			}else{
				sResultado = "No Existe: "+aca.catalogo.CatNivelUtil.getNivelNombre(conEnoc, vacacion.getNivelId())+
				" - "+aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc,vacacion.getModalidadId());
			}
		break;
		}
		
	}
	ArrayList<aca.alumno.AlumVacacion> lisPeriodos			= vacacionL.getListAll(conEnoc, "ORDER BY 1");
%>

<head>
	<script type="text/javascript">
		function Nuevo(){		
			document.frmVacaciones.NivelId.value	= "";	
			document.frmVacaciones.ModalidadId.value= "";
			document.frmVacaciones.fExamen.value	= "";
			document.frmVacaciones.fInicio.value	= "";
			document.frmVacaciones.fFinal.value		= "";
			document.frmVacaciones.Accion.value		= "1";
			document.frmVacaciones.submit();			
		}
		
		function Grabar(){		
			if(document.frmVacaciones.NivelId.value	!= "" ){
				document.frmVacaciones.Accion.value	= "2";
				document.frmVacaciones.submit();
			}else{
				alert('Ingresa todos los datos');
			}
		}
		
		function Modificar(){
			document.frmVacaciones.Accion.value	= "3";
			document.frmVacaciones.submit();
		}
		
		function Consultar(){
			document.frmVacaciones.Accion.value	= "4";
			document.frmVacaciones.submit();	
		}
	</script>
</head>
<body>
<font color="black">
<h1 align="center">Periodo de Vacaciones</h1>
<p align="center">* Examen = Fecha del examen final <font color="blue">|</font>  
* Inicio = Fecha en que inicia vacaciones  <font color="blue">|</font>  
* Final = Fecha en que termina vacaciones</p>
</font>
<form action="vacaciones" method="post" name="frmVacaciones" target="_self">
<input type="hidden" name="Accion" id="Accion">
<table style="width:40%" border="1" align="center"   bordercolor="#000000">
  <tr>
  	  <th align="center"><font size="2">Vacaciones</font></th>
  </tr>
  <tr>
    <td>
	  <table style="width:100%" >
		 <td width="14%" height="26"><strong>Nivel:</strong></td>
            <td>
            	<select name="NivelId" id="NivelId">
<%
			for(int i=0;i< lisNivel.size(); i++){
				aca.catalogo.CatNivel nivel = (aca.catalogo.CatNivel) lisNivel.get(i);
%>            	
            		<option value="<%=nivel.getNivelId()%>" <%if(nivel.getNivelId().equals(nivelId)) out.print("selected");%>><%=nivel.getNombreNivel()%></option>
<%}%>
            	</select> &nbsp; &nbsp;
            	<select name="ModalidadId" id="ModalidadId">
<%
			for(int i=0;i< lisModo.size(); i++){
				aca.catalogo.CatModalidad modo = (aca.catalogo.CatModalidad) lisModo.get(i);
%>            	
            		<option value="<%=modo.getModalidadId()%>" <%if(modo.getModalidadId().equals(modalidadId)) out.print("selected");%>><%=modo.getNombreModalidad()%></option>
<%}%>
            	</select>
			</td>            
		  	<tr>	  		
		  		<td height="20"><strong>Examen:</strong></td>
		  		<td><input type="text" class="text" name="fExamen" id="dateTest" value="<%=vacacion.getFExamen()%>"/>
					<input type="image" src="../../jscalendar-1.0/calendario.gif" id="lanzador1" value="<%=vacacion.getFExamen()%>" />(DD/MM/AAAA)</td>
		  	</tr>
		  	<tr>
		  		<td height="28"><strong>Inicio:</strong></td>
	            <td><input type="text" class="text" name="fInicio" id="dateStart" value="<%=vacacion.getFInicio() %>"/>
					<input type="image" src="../../jscalendar-1.0/calendario.gif" id="lanzador2" value="<%=vacacion.getFInicio() %>" />(DD/MM/AAAA)</td>
		  	</tr>
		  	<tr>
		  		<td height="28"><strong>Final:</strong></td>
	            <td><input type="text" class="text" name="fFinal" id="dateEnd" value="<%=vacacion.getFFinal() %>"/>
					<input type="image" src="../../jscalendar-1.0/calendario.gif" id="lanzador3" value="<%=vacacion.getFFinal() %>" />(DD/MM/AAAA)</td>
		  	</tr>
		  	 <tr> 
            <td colspan="2" align="center"><%=sResultado%></td>
          </tr>		  	
          <tr> 
            <td colspan="4" align="center">             
            	<input type="button" value="Nuevo" id="nuevo" onclick="javascript:Nuevo()" style="cursor:pointer"/>
            	<input type="button" value="Grabar" id="grabar" onclick="javascript:Grabar()" style="cursor:pointer"/>              
            	<input type="button" value="Modificar" id="modificar" onclick="javascript:Modificar()" style="cursor:pointer"/>
            </td>
          </tr>	
	  </table>
	</td>
  </tr>
</table>
</form>
<br>
<br>
<% if(lisPeriodos.size() > 0){%>

<table style="width:40%"  align="center">
  <tr align="center"> 
    <td colspan="5"><strong><font size="3">Listado de Periodos</strong> </td>
  </tr>
  <tr> 
    <th width="1%"><spring:message code="aca.Operacion"/></th>
    <th width="15%">Nivel</th>
    <th width="15%"><spring:message code="aca.Modalidad"/></th>
    <th width="30%">F.Examen</th> 
    <th width="10%">F.Inicio</th>    
    <th width="10%">F.Final</th>
 </tr>
<%
		for(int i=0;i< lisPeriodos.size(); i++){
			aca.alumno.AlumVacacion vacaciones = (aca.alumno.AlumVacacion) lisPeriodos.get(i);
%>
  
  <tr> 
    <td align="center"> <a href="vacaciones?Accion=4&NivelId=<%=vacaciones.getNivelId()%>&ModalidadId=<%=vacaciones.getModalidadId()%>&fExamen=<%=vacaciones.getFExamen()%>&fInicio=<%=vacaciones.getFInicio()%>&fFinal=<%=vacaciones.getFFinal()%>"> 
      <img title="Editar" src="../../imagenes/editar.gif" alt="Modificar" ></a> 
      <a href="vacaciones?Accion=5&NivelId=<%=vacaciones.getNivelId()%>&ModalidadId=<%=vacaciones.getModalidadId()%>&fExamen=<%=vacaciones.getFExamen()%>&fInicio=<%=vacaciones.getFInicio()%>&fFinal=<%=vacaciones.getFFinal()%>">
        <img src="../../imagenes/no.png" alt="Eliminar" >
      </a>      
    </td>
    <td align="center"><%=aca.catalogo.CatNivelUtil.getNivelNombre(conEnoc, vacaciones.getNivelId())%></td>
    <td align="center"><%=aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc,vacaciones.getModalidadId())%></td>
    <td align="center"><%=vacaciones.getFExamen()%></td>    
    <td align="center"><%=vacaciones.getFInicio()%></td>
    <td align="center"><%=vacaciones.getFFinal()%></td>
    </tr>
<%} %>
</table>
<%}else{%>
<font color="black">
	<h3 align="center">No hay Periodos agregados!</h3>
</font>

<%} %>
		<script type="text/javascript">
		   Calendar.setup({
		    inputField     :    "dateTest",     // id del campo de texto
		     ifFormat     :     "%d/%m/%Y",     // formato de la fecha que se escriba en el campo de texto
		     button     :    "lanzador1"     // el id del botón que lanzará el calendario
		});
		
		 Calendar.setup({
		    inputField     :    "dateStart",     // id del campo de texto
		     ifFormat     :     "%d/%m/%Y",     // formato de la fecha que se escriba en el campo de texto
		     button     :    "lanzador2"     // el id del botón que lanzará el calendario
		});
		
		Calendar.setup({
		    inputField     :    "dateEnd",     // id del campo de texto
		     ifFormat     :     "%d/%m/%Y",     // formato de la fecha que se escriba en el campo de texto
		     button     :    "lanzador3"     // el id del botón que lanzará el calendario
		});
		</script>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>