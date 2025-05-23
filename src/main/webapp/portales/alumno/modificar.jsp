<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%@ page import="java.util.Date,java.text.SimpleDateFormat" %>
<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="academico" scope="page" class="aca.alumno.AlumAcademico"/>
<jsp:useBean id="religion" scope="page" class="aca.catalogo.CatReligionDao"/>
<jsp:useBean id="estado" scope="page" class="aca.alumno.AlumEstado"/>
<jsp:useBean id="alumnoUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="CargaBloque" scope="page" class="aca.carga.CargaBloque"/>
<jsp:useBean id="Tutor" scope="page" class="aca.alumno.AlumUbicacion"/>
<jsp:useBean id="Direccion" scope="page" class="aca.alumno.AlumDireccion"/>
<jsp:useBean id="DireccionU" scope="page" class="aca.alumno.AlumDireccionUtil"/>

<%-- <jsp:include page="../menu.jsp" /> --%>
<%@ include file= "menu.jsp" %>

<script type="text/javascript" src="../../js/popcalendar.js"></script>
<script type="text/javascript">	
	
	function Modificar(){
		document.frmModificar.Accion.value="1";
		document.frmModificar.submit();
	}
	
	function PEC( Pec ){		
		document.frmModificar.Accion.value="2";		
		document.frmModificar.Pec.value 	= Pec;
		document.frmModificar.submit();
	}		
		
</script>
<%
	// Declaracion de variables	
	int nAccion			= Integer.parseInt(request.getParameter("Accion"));
	String sResultado	= "";	
			
	// Operaciones a realizar en la pantalla
	String matricula 	= (String) session.getAttribute("codigoAlumno");
	//String nombre		= aca.alumno.AlumUtil.getNombreAlumno(conEnoc, matricula, "NOMBRE");
	alumno = AlumUtil.mapeaRegId(conEnoc,matricula);
	
	ArrayList lisPais		= new ArrayList();
	ArrayList lisEstado		= new ArrayList();
	ArrayList lisCiudad		= new ArrayList();
	
	Direccion.mapeaRegId(conEnoc, matricula);	
	
	boolean validaCurp 	= aca.alumno.AlumUtil.validarCurp(alumno.getCurp());
			
	switch (nAccion){
		
		case 1: { // Grabar
			alumno.setCurp(request.getParameter("Curp").toUpperCase());
			alumno.setFNacimiento(request.getParameter("FechaNac"));
			alumno.setEmail(request.getParameter("Email"));
			
			Direccion.setCodigoPersonal(matricula);
			Direccion.setPaisId(request.getParameter("PaisId"));
			Direccion.setEstadoId(request.getParameter("EstadoId"));
			Direccion.setCiudadId(request.getParameter("CiudadId"));
			Direccion.setColonia(request.getParameter("Colonia"));
			Direccion.setCalle(request.getParameter("Calle"));
			Direccion.setCodPostal(request.getParameter("CodPostal"));
			Direccion.setApartado(request.getParameter("Apartado"));
			Direccion.setTelefono(request.getParameter("Telefono"));
			Direccion.setNumero(request.getParameter("Numero"));
			
			conEnoc.setAutoCommit(false);
			if (aca.alumno.AlumUtil.validarCurp(request.getParameter("Curp"))){
			
				if (AlumUtil.updateReg(conEnoc, alumno)){
					if (DireccionU.existeReg(conEnoc, matricula)){ 
						if (DireccionU.updateReg(conEnoc, Direccion)){
							sResultado = "¡ Gracias "+alumno.getNombre()+" por actualizar tus datos !";
							conEnoc.commit();
						}else{
							conEnoc.rollback();
							sResultado = "La actualización no pudo ser realizada";
						}						
					}else{
						if (DireccionU.insertReg(conEnoc, Direccion)){
							sResultado = "¡ Gracias "+alumno.getNombre()+" por actualizar tus datos !";
							conEnoc.commit();
						}else{
							conEnoc.rollback();
							sResultado = "La actualización no pudo ser realizada";
						}
					}					
				}else{
					sResultado = "La actualización no pudo ser realizada";
				}
			}else{
				sResultado = "El CURP no es válido: "+alumno.getCurp()+" ¡Captúralo correctamente!";
			}
			conEnoc.setAutoCommit(true);
			
			break;
		}
		
		case 2: { // Modificar
			alumno.setCurp(request.getParameter("Curp").toUpperCase());
			alumno.setFNacimiento(request.getParameter("FechaNac"));
			alumno.setEmail(request.getParameter("Email"));
			Direccion.setPaisId(request.getParameter("PaisId"));
			Direccion.setEstadoId(request.getParameter("EstadoId"));
			Direccion.setCiudadId(request.getParameter("CiudadId"));
			Direccion.setColonia(request.getParameter("Colonia"));
			Direccion.setCalle(request.getParameter("Calle"));
			Direccion.setCodPostal(request.getParameter("CodPostal"));
			Direccion.setApartado(request.getParameter("Apartado"));
			Direccion.setTelefono(request.getParameter("Telefono"));
			Direccion.setNumero(request.getParameter("Numero"));
			Direccion.setFecha(aca.util.Fecha.getHoy());
			break;
		}		
	}
%>
<html>
<head><title><spring:message code='aca.DocumentoSinTitulo'/></title></head>
<form action="modificar" method="post" name="frmModificar" target="_self">
<input type="hidden" name="Accion">
<input type="hidden" name="Pec">		 
<br><br>
<table style="margin: 0 auto;  width:70%">
  <tr><td><spring:message code="datosAlumno.portal.Nota3"/><a href="http://consultas.curp.gob.mx/CurpSP/" target="_blank">http://consultas.curp.gob.mx/CurpSP/</a></td></tr>
  <tr><td>Si no tienes CURP puedes consultar este instructivo <a href="manualCURP.pdf"> "manualCURP.pdf" </a> para generar tu clave, pero debes colocar un cero(0) en los campos de homoclave(posición 17) y digito verificador(posición 18).</td></tr>
  <tr><td>&nbsp;</td></tr>
  <tr><td ALIGN="center"><spring:message code="aca.Alumno"/>: <%= alumno.getNombre()%> <%= alumno.getApellidoPaterno() %> <%= alumno.getApellidoMaterno() %> - [<%=alumno.getFNacimiento()%>]</td></tr>
</table>
<table class="table table-nobordered table-fullcondensed table-nohover" width="70%" border="1" align="center"  >  
  <tr> 
  	  <th align="center"><font size="2"><spring:message code="datosAlumno.portal.Titulo16"/> </font></th>
  </tr>
   <tr>
    <td>
	  <br>
	  <table style="width:100%" >
        <tr> 
          <td ><strong><spring:message code="aca.Curp"/>:</strong></td>
          <td>
<% 	if (!alumno.getCotejado().equals("S") || validaCurp==false){ %>         
            <input name="Curp" type="text" class="text" id="Curp" size="40"  maxlength="30" value="<%=alumno.getCurp()%>">
<%	}else if (alumno.getCotejado().equals("S") && validaCurp==true){
		out.println(alumno.getCurp()+"  ¡ Este Dato está cotejado !"); %>
		<input type="hidden" name="Curp" value="<%= alumno.getCurp() %>">
<% }
%>
            
          </td>			
        </tr>
        <tr> 
          <td><strong><spring:message code="aca.Fecha"/> <spring:message code="aca.De"/> <spring:message code="aca.Nacimiento"/>:</strong></td>
          <td>
<% 	if (!alumno.getCotejado().equals("S")){ 
	out.println(alumno.getFNacimiento());
%>          
<!--             <input name="FechaNac" type="text" class="text" id="FechaNac" size="12" maxlength="10" value="<%=alumno.getFNacimiento()%>">
            <img class="button" alt="calendario" id="fotoFecha" src="../../imagenes/calendario.gif" onClick="javascript:showCalendar(this, document.getElementById('FechaNac'), 'dd/mm/yyyy',null,1,-1,-1);"> (DD/MM/AAAA) -->
<% 	}else{
		out.println(alumno.getFNacimiento()+"  ¡ Este Dato está cotejado con tu acta de nacimiento !");%>
		<input type="hidden" name="FechaNac" value="<%= alumno.getFNacimiento() %>">	
<%	} %>            
          </td>
        </tr>
        <tr> 
          <td><strong><spring:message code="aca.Email"/>:</strong></td>
          <td><input name="Email" type="text" class="text" id="Email" size="40" maxlength="40" value="<%=alumno.getEmail()%>"></td>
        </tr>
        <tr> 
          <td><strong><spring:message code="aca.Pais"/></strong></td>
          <td>            
			<select name="PaisId" id="PaisId" onchange = "PEC('1')">
<%		
		aca.catalogo.PaisUtil paisU = new aca.catalogo.PaisUtil();
		lisPais = paisU.getListAll(conEnoc,"ORDER BY 2");
		for( int i=0;i<lisPais.size();i++){
			aca.catalogo.CatPais pais = (aca.catalogo.CatPais) lisPais.get(i);
			if (pais.getPaisId().equals( Direccion.getPaisId() )){
				out.print(" <option value='"+pais.getPaisId()+"' Selected>"+ pais.getNombrePais()+"</option>");
			}else{
				out.print(" <option value='"+pais.getPaisId()+"'>"+ pais.getNombrePais()+"</option>");
			}
		}
%>
            </select>        
          </td>
        </tr>
        <tr> 
          <td><strong><spring:message code="aca.Estado"/></strong></td>
          <td>
            <select name="EstadoId" id="EstadoId"  onChange="javascript:PEC('2')">
<% 			
			aca.catalogo.EstadoUtil estadoU = new aca.catalogo.EstadoUtil();
			lisEstado = estadoU.getLista(conEnoc,Direccion.getPaisId(),"ORDER BY 1,3");
			
			for( int i=0;i<lisEstado.size();i++){
				aca.catalogo.CatEstado edo = (aca.catalogo.CatEstado) lisEstado.get(i);
				if (edo.getEstadoId().equals(Direccion.getEstadoId())){
					out.print(" <option value='"+edo.getEstadoId()+"' Selected>"+ edo.getNombreEstado()+"</option>");
				}else{
					out.print(" <option value='"+edo.getEstadoId()+"'>"+ edo.getNombreEstado()+"</option>");
				}		
			}
 %>
              </select>            
          </td>
        </tr>
        <tr> 
          <td><strong><spring:message code="aca.Ciudad"/></strong></td>
          <td>
			<select name="CiudadId" id="CiudadId">
<%
			aca.catalogo.CiudadUtil ciudadU = new aca.catalogo.CiudadUtil();
			lisCiudad = ciudadU.getLista(conEnoc,Direccion.getPaisId(),Direccion.getEstadoId(),"ORDER BY 4");
			for(int  i=0;i<lisCiudad.size();i++){
				aca.catalogo.CatCiudad ciudad = (aca.catalogo.CatCiudad) lisCiudad.get(i);
				if (ciudad.getCiudadId().equals(Direccion.getCiudadId())){
					out.print(" <option value='"+ciudad.getCiudadId()+"' Selected>"+ ciudad.getNombreCiudad()+"</option>");
				}else{
					out.print(" <option value='"+ciudad.getCiudadId()+"'>"+ ciudad.getNombreCiudad()+"</option>");
				}				
			}
%>
          </td>
        </tr>
        <tr> 
          <td><strong><spring:message code="catalogos.extension.Colonia"/>:</strong></td>
          <td><input name="Colonia" type="text" class="text" id="Colonia" size="40" maxlength="40" value="<%=Direccion.getColonia() %>"></td>
        </tr>
        <tr> 
          <td><strong><spring:message code="aca.Calle"/>:</strong></td>
          <td><input name="Calle" type="text" class="text" id="Calle" size="40" maxlength="70" value="<%=Direccion.getCalle() %>"></td>          
        </tr>
        <tr> 
          <td><strong>N&uacute;mero:</strong></td>          
          <td><input name="Numero" type="text" class="text" id="Numero" size="10" maxlength="20" value="<%=Direccion.getNumero() %>"></td>
        </tr>
        <tr> 
          <td><strong><spring:message code="catalogos.division.CodigoPostal"/>:</strong></td>
          <td><input name="CodPostal" type="text" class="text" id="CodPostal" size="20" maxlength="20" value="<%=Direccion.getCodPostal() %>"></td>
        </tr>
         <tr> 
          <td><strong><spring:message code="datosAlumno.portal.DatosApartado"/>:</strong></td>
          <td><input name="Apartado" type="text" class="text" id="Apartado" size="20" maxlength="20" value="<%=Direccion.getApartado() %>"></td>
        </tr>       
         <tr> 
          <td><strong><spring:message code="aca.Telefono"/></strong></td>
          <td><input name="Telefono" type="text" class="text" id="Telefono" size="40" maxlength="40" value="<%=Direccion.getTelefono() %>"></td>
        </tr>
        <tr> 
          <td colspan="2" style="text-align:center;"><%=sResultado%></td>
        </tr>
        
        <tr>
          <th colspan="4" style="text-align:center;"> 
		  	  <a href="javascript:Modificar()" class="btn btn-primary"><spring:message code="aca.Modificar"/></a> &nbsp; 
		  </th>
        </tr>
      </table>
	</td>
  </tr>
</table>
</form>
</body>
</html>

<script>
	$('.nav-tabs').find('.datos').addClass('active');
</script>
<%@ include file= "../../cierra_enoc.jsp" %>