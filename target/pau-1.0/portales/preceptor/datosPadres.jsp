<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<% idJsp="070_u";%>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="Ubicacion" scope="page" class="aca.alumno.AlumUbicacion"/>
<jsp:useBean id="UbicacionU" scope="page" class="aca.alumno.UbicacionUtil"/>

<script type="text/javascript">
	function Modificar(){
		document.frmubicacion.Accion.value="3";
		document.frmubicacion.submit();
	}
	
	function Consultar(){
		document.frmubicacion.Accion.value="5";
		document.frmubicacion.submit();		
	}
	
	function PEC( Pec, tipo){
		document.frmubicacion.Accion.value	="6";
		document.frmubicacion.tipo.value 	= tipo;
		document.frmubicacion.Pec.value 		= Pec;
		document.frmubicacion.Dua.value 		= "3";
		document.frmubicacion.submit();
	}
	
	function DUA( Dua, tipo){
		document.frmubicacion.Accion.value	="7";
		document.frmubicacion.tipo.value 	= tipo;
		document.frmubicacion.Dua.value 		= Dua;
		document.frmubicacion.Pec.value 		= "3";
		document.frmubicacion.submit();
	}	
</script>
<%
	String sCodigo			= (String) session.getAttribute("codigoAlumno");

	// Declaracion de variables
	String sTipo			= request.getParameter("tipo");	
	String sAccion 			= request.getParameter("Accion")==null?"5":request.getParameter("Accion");	
	int nAccion				= Integer.parseInt(sAccion);
	int i 					= 0;
	String sResultado		= "";		 
	
	
	// Llenado de listores
	aca.catalogo.CatReligionDao religionDao = new aca.catalogo.CatReligionDao();
	ArrayList<aca.catalogo.CatReligion> lisRel = religionDao.getListAll(conEnoc,"ORDER BY 2");
	
	aca.catalogo.PaisUtil paisU = new aca.catalogo.PaisUtil();
	ArrayList<aca.catalogo.CatPais> lisPais = paisU.getListAll(conEnoc,"ORDER BY 2");			

	Ubicacion.setCodigoPersonal(sCodigo);
	
	if (nAccion!= 1 && nAccion!=5 && nAccion!= 4){
		Ubicacion.setPNombre(request.getParameter("PNombre"));
		Ubicacion.setPReligion(request.getParameter("PReligion"));
		Ubicacion.setPNacionalidad(request.getParameter("PNacionalidad"));		
		
		Ubicacion.setMNombre(request.getParameter("MNombre"));
		Ubicacion.setMReligion(request.getParameter("MReligion"));
		Ubicacion.setMNacionalidad(request.getParameter("MNacionalidad"));
		
		Ubicacion.setTNombre(request.getParameter("TNombre"));
		Ubicacion.setTDireccion(request.getParameter("TDireccion"));
		Ubicacion.setTColonia(request.getParameter("TColonia"));
		Ubicacion.setTCodigo(request.getParameter("TCodigo"));
		Ubicacion.setTApartado(request.getParameter("TApartado"));
		Ubicacion.setTTelefono(request.getParameter("TTelefono"));
		Ubicacion.setTEmail(request.getParameter("TEmail"));		
	}
	
	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 3: { // Modificar
		
			Ubicacion.setTPais(request.getParameter("TPais"));
			Ubicacion.setTEstado(request.getParameter("TEstado"));
			Ubicacion.setTCiudad(request.getParameter("TCiudad"));			
							
			if (UbicacionU.existeReg(conEnoc, sCodigo) == true){
				if (UbicacionU.updateReg(conEnoc, Ubicacion)){
					sResultado = "Changes saved: "+Ubicacion.getCodigoPersonal();
				}else{
					sResultado = "Error saving: "+Ubicacion.getCodigoPersonal();
				}
			}else{
				sResultado = "Not found: "+Ubicacion.getCodigoPersonal();
			}
			break;
		}
		case 5: { // Consultar			
			if (UbicacionU.existeReg(conEnoc, sCodigo) == true){
				Ubicacion.mapeaRegId(conEnoc, sCodigo);
				sResultado = "Search";
			}	
			break;			
		}
		case 6: { // Refrescar combos PEC
			
			Ubicacion.setTPais(request.getParameter("TPais"));
			if (request.getParameter("Pec").equals("2")){
				Ubicacion.setTEstado(request.getParameter("TEstado"));
			}else if(request.getParameter("Pec").equals("3")){
				Ubicacion.setTEstado(request.getParameter("TEstado"));
				Ubicacion.setTCiudad(request.getParameter("TCiudad"));
			}else{
				Ubicacion.setTEstado("0");
				Ubicacion.setTCiudad("0");
			}			
												
			sResultado = "Fill out the entire form"; 
		}		
		case 7: { // Refrescar combos DUA
			Ubicacion.setTPais(request.getParameter("TPais"));
			if (request.getParameter("Pec").equals("2")){
				Ubicacion.setTEstado(request.getParameter("TEstado"));
			}else if(request.getParameter("Pec").equals("3")){
				Ubicacion.setTEstado(request.getParameter("TEstado"));
				Ubicacion.setTCiudad(request.getParameter("TCiudad"));
			}else{
				Ubicacion.setTEstado("0");
				Ubicacion.setTCiudad("0");
			}	
												
			sResultado = "Fill out the entire form"; 
		}		
	}	
%>
<div class="container-fluid">
<form action="datosPadres" method="post" name="frmubicacion" target="_self">
<input type="hidden" name="Accion">
<input type="hidden" name="Pec">
<input type="hidden" name="Dua">
<input type="hidden" name="tipo" value="<%=sTipo%>">
<table class="tabbox" width="100%" height="100%">
<tr valign="top">
 <td>
	<table class="table table-condensed " style="width:100%"   align="center">
	<tr valign='top'>
		<td width='50%' align = 'left'>
			<fieldset style="padding-bottom:10; margin-bottom: 6;">
				<legend>Parents Data:</legend> 
				<table class="fieldbox" width="100%" >
					<tr valign='top'>
						<td>
							<table style="width:100%" cellspacing='6'>

          <tr> 
            <td width="15%" height="22"><strong><spring:message code="aca.Matricula"/>:</strong></td>
            <td width="32%"> <input name="CodigoPersonal" class="form-control" type="hidden" value="<%=Ubicacion.getCodigoPersonal()%>"> 
              <strong>[<%=Ubicacion.getCodigoPersonal()%>]</strong></td>
          </tr>
          <tr> 
            <td><strong><font color="">Father</font></strong></td>
            <td> <input name="PNombre" type="text"  class="form-control" id="PNombre2" size="40" maxlength="60" value="<%=Ubicacion.getPNombre()%>"></td>
          </tr>
          <tr> 
            <td><strong><spring:message code="aca.Religion"/>:</strong></td>
            <td><select  class="form-select" name="PReligion" id="PReligion" disabled>
                <%				
				for( i=0;i<lisRel.size();i++){
					aca.catalogo.CatReligion religion = (aca.catalogo.CatReligion) lisRel.get(i);
					if (religion.getReligionId().equals(Ubicacion.getPReligion())){
						out.print(" <option value='"+religion.getReligionId()+"' Selected>"+ religion.getNombreReligion()+"</option>");
					}else{
						out.print(" <option value='"+religion.getReligionId()+"'>"+ religion.getNombreReligion()+"</option>");
					}				
				}				
			  %>
              </select> </td>
          </tr>
          <tr> 
            <td><strong><spring:message code="aca.Nacionalidad"/>:</strong></td>
            <td> <select class="form-select" name="PNacionalidad" id="select2">
                <%				
				for( i=0;i<lisPais.size();i++){
					aca.catalogo.CatPais pais = (aca.catalogo.CatPais) lisPais.get(i);
					if (pais.getPaisId().equals(Ubicacion.getPNacionalidad())){
						out.print(" <option value='"+pais.getPaisId()+"' Selected>"+ pais.getNacionalidad()+"</option>");
					}else{
						out.print(" <option value='"+pais.getPaisId()+"'>"+ pais.getNacionalidad()+"</option>");
					}				
				}				
			  %>
              </select></td>
          </tr>          
          <tr> 
            <td><strong><font color="">Mother:</font></strong></td>
            <td><input name="MNombre" type="text" class="form-control" id="MNombre" value="<%=Ubicacion.getMNombre()%>" size="40" maxlength="60"> 
            </td>
          </tr>
          <tr> 
            <td><strong><spring:message code="aca.Religion"/>:</strong></td>
            <td> <select class="form-select" name="MReligion" id="MReligion">
                <%				
				for( i=0;i<lisRel.size();i++){
					aca.catalogo.CatReligion religion = (aca.catalogo.CatReligion) lisRel.get(i);
					if (religion.getReligionId().equals(Ubicacion.getMReligion())){
						out.print(" <option value='"+religion.getReligionId()+"' Selected>"+ religion.getNombreReligion()+"</option>");
					}else{
						out.print(" <option value='"+religion.getReligionId()+"'>"+ religion.getNombreReligion()+"</option>");
					}				
				}				
			  %>
              </select> </td>
          </tr>
          <tr> 
            <td><strong><spring:message code="aca.Nacionalidad"/>:</strong></td>
            <td><select class="form-select" name="MNacionalidad" id="select4">
                <%				
				for( i=0;i<lisPais.size();i++){
					aca.catalogo.CatPais pais = (aca.catalogo.CatPais) lisPais.get(i);
					if (pais.getPaisId().equals(Ubicacion.getMNacionalidad())){
						out.print(" <option value='"+pais.getPaisId()+"' Selected>"+ pais.getNacionalidad()+"</option>");
					}else{
						out.print(" <option value='"+pais.getPaisId()+"'>"+ pais.getNacionalidad()+"</option>");
					}				
				}				
			  %>
              </select> </td>
          </tr>


							</table>
						</td>
					</tr>
				</table>
			</fieldset>
		</td>
		<td width='50%' align = 'left'>
			<fieldset style="padding-bottom:10; margin-bottom: 6;">
				<legend>Mentor data:</legend> 
				<table class="fieldbox" width="100%" >
					<tr valign='top'>
						<td>
							<table style="width:100%" cellspacing='6'>



          <tr> 
            <td><strong><font color=""><spring:message code="aca.Tutor"/></font></strong></td>
            <td><input name="TNombre" type="text" class="form-control" id="PNombre3" size="40" maxlength="60" value="<%=Ubicacion.getTNombre()%>"></td>
          </tr>
          <tr> 
            <td><strong>Address</strong></td>
            <td><input name="TDireccion" type="text" class="form-control" id="TDireccion" value="<%=Ubicacion.getTDireccion()%>" size="40" maxlength="60"></td>
          </tr>
          <tr> 
            <td><strong>Neighborhood</strong></td>
            <td><input name="TColonia" type="text" class="form-control" id="TColonia" value="<%=Ubicacion.getTColonia()%>" size="20" maxlength="30"></td>
          </tr>
          <tr> 
            <td width="12%"><strong>ZIP Code</strong></td>
            <td><input name="TCodigo" type="text" class="form-control" id="TCodigo" value="<%=Ubicacion.getTCodigo()%>" size="10" maxlength="10"></td>
          </tr>
          <tr> 
            <td><strong>P.O. Box</strong></td>
            <td width="41%"><input name="TApartado" type="text" class="form-control" id="TApartado" value="<%=Ubicacion.getTApartado()%>" size="20" maxlength="20"></td>
          </tr>
          <tr> 
            <td><strong><spring:message code="aca.Telefono"/></strong></td>
            <td><input name="TTelefono" type="text" class="form-control" id="TTelefono" value="<%=Ubicacion.getTTelefono()%>" size="20" maxlength="20"></td>
          </tr>
          <tr> 
            <td><strong><spring:message code="aca.Email"/></strong></td>
            <td><input name="TEmail" type="text" class="form-control" id="TEmail" value="<%=Ubicacion.getTEmail()%>" size="40" maxlength="50"></td>
          </tr>
          <tr> 
            <td><strong>Country:</strong></td>
            <td> <select name="TPais" id="TPais" class="form-select" onchange = "PEC('1','<%=sTipo%>')">
                <%				
				for( i=0;i<lisPais.size();i++){
					aca.catalogo.CatPais pais = (aca.catalogo.CatPais) lisPais.get(i);
					if (pais.getPaisId().equals(Ubicacion.getTPais())){
						out.print(" <option value='"+pais.getPaisId()+"' Selected>"+ pais.getNombrePais()+"</option>");
					}else{
						out.print(" <option value='"+pais.getPaisId()+"'>"+ pais.getNombrePais()+"</option>");
					}				
				}				
			  %>
              </select> </td>
          </tr>
          <tr> 
            <td><strong><spring:message code="aca.Estado"/>:</strong></td>
            <td> <select name="TEstado" id="TEstado" class="form-select"  onChange= "javascript:PEC('2','<%=sTipo%>')">
                <%			  	
				aca.catalogo.EstadoUtil estadoU = new aca.catalogo.EstadoUtil();
				ArrayList<aca.catalogo.CatEstado> lisEstado = estadoU.getLista(conEnoc,Ubicacion.getTPais(),"ORDER BY 1,3");
				for( i=0;i<lisEstado.size();i++){
					aca.catalogo.CatEstado estado = (aca.catalogo.CatEstado) lisEstado.get(i);
					if (estado.getEstadoId().equals(Ubicacion.getTEstado())){
						out.print(" <option value='"+estado.getEstadoId()+"' Selected>"+ estado.getNombreEstado()+"</option>");
					}else{
						out.print(" <option value='"+estado.getEstadoId()+"'>"+ estado.getNombreEstado()+"</option>");
					}				
				}				
				estadoU	= null;
			  %>
              </select></td>
          </tr>
          <tr> 
            <td><strong>Town:</strong></td>
            <td> <select name="TCiudad" class="form-select" id="TCiudad">
                <%			  	
				aca.catalogo.CiudadUtil ciudadU = new aca.catalogo.CiudadUtil();
                ArrayList<aca.catalogo.CatCiudad> lisCiudad = ciudadU.getLista(conEnoc,Ubicacion.getTPais(),Ubicacion.getTEstado(),"ORDER BY 4");
				for( i=0;i<lisCiudad.size();i++){
					aca.catalogo.CatCiudad ciudad = (aca.catalogo.CatCiudad) lisCiudad.get(i);		
					if (ciudad.getCiudadId().equals(Ubicacion.getTCiudad())){
						out.print(" <option value='"+ciudad.getCiudadId()+"' Selected>"+ ciudad.getNombreCiudad()+"</option>");						
					}else{
						out.print(" <option value='"+ciudad.getCiudadId()+"'>"+ ciudad.getNombreCiudad()+"</option>");
					}				
				}								
				ciudadU	= null;
			  %>
              </select> </td>
          </tr>          
          
							</table>
						</td>
					</tr>
				</table>
			</fieldset>
		</td>
	</tr>
	  
	</table>
</td>
</tr>
</table>
</form>
</div>
<%	
	paisU		= null;
	lisPais	= null;
	lisRel 	= null;	
%>
<%@ include file= "../../cierra_enoc.jsp" %>