<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Asociacion" scope="page" class="aca.catalogo.CatAsociacion"/>
<jsp:useBean id="AsociacionU" scope="page" class="aca.catalogo.AsociacionUtil"/>

<script type="text/javascript">
	
	function Nuevo()	{	
		document.frmasociacion.AsociacionId.value 		= " ";
		document.frmasociacion.NombreAsociacion.value 	= " ";
		document.frmasociacion.Direccion.value 			= " ";
		document.frmasociacion.Colonia.value 			= " ";
		document.frmasociacion.CodPostal.value 			= " ";
		document.frmasociacion.Telefono.value 			= " ";
		document.frmasociacion.Fax.value 				= " ";
		document.frmasociacion.Email.value 				= " ";
		document.frmasociacion.PaisId.value 			= " ";
		document.frmasociacion.EstadoId.value 			= " ";
		document.frmasociacion.CiudadId.value 			= " ";
		document.frmasociacion.Accion.value				="1";
		document.frmasociacion.submit();		
	}
	
	function Grabar(){
		if(document.frmasociacion.AsociacionId.value!="" && document.frmasociacion.NombreAsociacion!="" ){			
			document.frmasociacion.Accion.value="2";
			document.frmasociacion.submit();			
		}else{
			alert("Complete el formulario ..! ");
		}
	}
	
	function Modificar(){
		document.frmasociacion.Accion.value="3";
		document.frmasociacion.submit();
	}
	
	function Borrar( ){
		if(document.frmasociacion.AsociacionId.value!=""){
			if(confirm("Estas seguro de eliminar el registro!")==true){
	  			document.frmasociacion.Accion.value="4";
				document.frmasociacion.submit();
			}			
		}else{
			alert("Escriba la Clave !");
			document.frmasociacion.AsociacionId.focus(); 
	  	}
	}
	
	function Consultar(){
		document.frmasociacion.Accion.value="5";
		document.frmasociacion.submit();		
	}

	function PEC( Pec){		
		document.frmasociacion.Accion.value	="6";
		document.frmasociacion.Pec.value 	= Pec;
		document.frmasociacion.submit();
	}
	
</script>
<%
	// Declaracion de variables	
	String sResultado		= "";
	int nAccion			= Integer.parseInt(request.getParameter("Accion"));	
	int i					=0;

	Asociacion.setDivisionId(request.getParameter("DivisionId"));
	Asociacion.setUnionId(request.getParameter("UnionId"));
	ArrayList listor			= new ArrayList();
	ArrayList lisPais			= new ArrayList();

	if ( nAccion == 1 ){
		Asociacion.setAsociacionId(AsociacionU.maximoReg(conEnoc, Asociacion.getDivisionId(), Asociacion.getUnionId()));
	}else{
		Asociacion.setAsociacionId(request.getParameter("AsociacionId"));
	}
	if (nAccion == 6){
		Asociacion.setAsociacionId(AsociacionU.maximoReg(conEnoc, Asociacion.getDivisionId(), Asociacion.getUnionId()));		
	}
	
	if (nAccion == 2 || nAccion == 3 || nAccion == 6){	
		Asociacion.setNombreAsociacion(request.getParameter("NombreAsociacion"));
		Asociacion.setDireccion(request.getParameter("Direccion"));
		Asociacion.setColonia(request.getParameter("Colonia"));
		Asociacion.setCodPostal(request.getParameter("CodPostal"));
		Asociacion.setTelefono(request.getParameter("Telefono"));
		Asociacion.setFax(request.getParameter("Fax"));
		Asociacion.setEmail(request.getParameter("Email"));
		Asociacion.setPaisId(request.getParameter("PaisId"));
	}
	
		// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 1: { // Nuevo
			sResultado = "Llene el formulario correctamente ..¡¡";
			break;
			}		
		case 2: { // Grabar
			Asociacion.setEstadoId(request.getParameter("EstadoId"));
			Asociacion.setCiudadId(request.getParameter("CiudadId"));
				if (AsociacionU.existeReg(conEnoc, Asociacion.getDivisionId(),Asociacion.getUnionId(), Asociacion.getAsociacionId()) == false){
					if (AsociacionU.insertReg(conEnoc, Asociacion)){
						sResultado = "Grabado: " +Asociacion.getAsociacionId();
					}else{
						sResultado = "No Grabó: "+Asociacion.getAsociacionId();
					}
				}else{
					sResultado = "Ya existe: "+Asociacion.getAsociacionId();
				}
			break;
		}
		case 3: { // Modificar
			Asociacion.setEstadoId(request.getParameter("EstadoId"));
			Asociacion.setCiudadId(request.getParameter("CiudadId"));
			if (AsociacionU.existeReg(conEnoc, Asociacion.getDivisionId(),Asociacion.getUnionId(), Asociacion.getAsociacionId()) == true){
				if (AsociacionU.updateReg(conEnoc, Asociacion)){
					sResultado = "Modificado: "+Asociacion.getAsociacionId();
				}else{
					sResultado = "No Cambió: "+Asociacion.getAsociacionId();
				}
			}else{
				sResultado = "No existe: "+Asociacion.getAsociacionId();
			}
			break;
		}
		case 4: { // Borrar
			if (AsociacionU.existeReg(conEnoc, Asociacion.getDivisionId(),Asociacion.getUnionId(), Asociacion.getAsociacionId()) == true){
				if (AsociacionU.deleteReg(conEnoc, Asociacion.getDivisionId(),Asociacion.getUnionId(), Asociacion.getAsociacionId())){
					sResultado = "Borrado: "+Asociacion.getAsociacionId();
				}else{
					sResultado = "No Borró: "+Asociacion.getAsociacionId();
				}
			}else{
				sResultado = "No existe: "+Asociacion.getAsociacionId();
			}		
			break;
		}
		case 5: { // Consultar			
				if (AsociacionU.existeReg(conEnoc, Asociacion.getDivisionId(),Asociacion.getUnionId(), Asociacion.getAsociacionId()) == true){
				AsociacionU.mapeaRegId(conEnoc, request.getParameter("DivisionId"), request.getParameter("UnionId"), Asociacion.getAsociacionId());
				sResultado = "Consulta";
			}else{
				sResultado = "No existe: "+Asociacion.getAsociacionId(); 
			}	
			break;			
		}
		case 6: { // Refrescar combos PEC			
			if (request.getParameter("Pec").equals("2")){
				Asociacion.setEstadoId(request.getParameter("EstadoId"));
			}else{
				Asociacion.setEstadoId("0");
				Asociacion.setCiudadId("0");
			}
			break;						
		}
	}	
%>
<form action="accion_a" method="post" name="frmasociacion" target="_self">
<input type="hidden" name="Accion">
<input type="hidden" name="Pec">
<table style="width:50%" align="center" bordercolor="#000000">
  <tr>
      <th align="center"> <font size="2">Catalogo de Asociaciones [ <a href="asociacion?DivisionId=<%=Asociacion.getDivisionId()%>&UnionId=<%=Asociacion.getUnionId()%>"> 
        Listado</a>]</font></th>
    </tr>
  <tr>
      <td> 
        <table style="width:100%"  class="tabla">
          <tr> 
            <td width="30%"><strong><spring:message code="aca.Clave"/>:</strong></td>
             <td width="76%"><input type="hidden" name="DivisionId" value="<%=Asociacion.getDivisionId()%>">
			  <input type="hidden" name="UnionId" value="<%=Asociacion.getUnionId()%>">
              <input name="CiudadId" type="text" class="text" id="CiudadId" size="3" maxlength="3" value="<%=Asociacion.getAsociacionId()%>"></td>
          </tr>
          <tr> 
            <td><strong><spring:message code="aca.Nombre"/>:</strong></td>
            <td><input name="NombreAsociacion" type="text" class="text" id="NombreAsociacion" value="<%=Asociacion.getNombreAsociacion()%>" size="40" maxlength="40"></td>
		  </tr>
		   <tr> 
            <td><strong>Direccion:</strong></td>
            <td><input name="Direccion" type="text" class="text" id="Direccion" value="<%=Asociacion.getDireccion()%>" size="40" maxlength="40"></td>
		  </tr>
		   <tr> 
            <td><strong>Colonia:</strong></td>
            <td><input name="Colonia" type="text" class="text" id="Colonia" value="<%=Asociacion.getColonia()%>" size="40" maxlength="40"></td>
		  </tr>
		   <tr> 
            <td><strong>CodPostal:</strong></td>
            <td><input name="CodPostal" type="text" class="text" id="gCodPostal" value="<%=Asociacion.getCodPostal()%>" size="40" maxlength="40"></td>
		  </tr>
		   <tr> 
            <td><strong>><spring:message code='aca.Telefono'/>:</strong></td>
            <td><input name="Telefono" type="text" class="text" id="Telefono" value="<%=Asociacion.getTelefono()%>" size="40" maxlength="40"></td>
		  </tr>
		   <tr> 
            <td><strong>Fax:</strong></td>
            <td><input name="Fax" type="text" class="text" id="Fax" value="<%=Asociacion.getFax()%>" size="40" maxlength="40"></td>
		  </tr>
		   <tr> 
            <td><strong>Email:</strong></td>
            <td><input name="Email" type="text" class="text" id="Email" value="<%=Asociacion.getEmail()%>" size="40" maxlength="40"></td>
		  </tr>
		   <tr> 
            <td><strong>Pais:</strong></td>
            <td><select name="PaisId" id="PaisId" onchange = "PEC('1')">
                <%			  	
				aca.catalogo.PaisUtil paisU = new aca.catalogo.PaisUtil();
				lisPais = paisU.getListAll(conEnoc,"ORDER BY 2");
				for( i=0;i<lisPais.size();i++){
					aca.catalogo.CatPais pais = (aca.catalogo.CatPais) lisPais.get(i);
					if (pais.getPaisId().equals(Asociacion.getPaisId())){
						out.print(" <option value='"+pais.getPaisId()+"' Selected>"+ pais.getNombrePais()+"</option>");
					}else {
						out.print(" <option value='"+pais.getPaisId()+"'>"+ pais.getNombrePais()+"</option>");
					}
				}				
				paisU	= null;
			  %>
              </select></td>
		  </tr>
		   <tr> 
            <td><strong><spring:message code="aca.Estado"/>:</strong></td>
            <td><select name="EstadoId" id="EstadoId"  onChange= "javascript:PEC('2')">
                <%			  	
				aca.catalogo.EstadoUtil estadoU = new aca.catalogo.EstadoUtil();
				listor = estadoU.getLista(conEnoc,Asociacion.getPaisId(),"ORDER BY 1,3");				
				for( i=0;i<listor.size();i++){
					aca.catalogo.CatEstado estado = (aca.catalogo.CatEstado) listor.get(i);
					if (estado.getEstadoId().equals(Asociacion.getEstadoId())){
						out.print(" <option value='"+estado.getEstadoId()+"' Selected>"+ estado.getNombreEstado()+"</option>");
					}else {
						out.print(" <option value='"+estado.getEstadoId()+"'>"+ estado.getNombreEstado()+"</option>");
					}				
				}
				listor 		= null;
				estadoU	= null;
			  %>
              </select></td>
		  </tr>
		  <tr> 
            <td><strong>Ciudad:</strong></td>
            <td><select name="select" id="select2">
                <%			  	
				aca.catalogo.CiudadUtil ciudadU = new aca.catalogo.CiudadUtil();
				listor = ciudadU.getLista(conEnoc,Asociacion.getPaisId(),Asociacion.getEstadoId(),"ORDER BY 4");
				for( i=0;i<listor.size();i++){
					aca.catalogo.CatCiudad ciudad = (aca.catalogo.CatCiudad) listor.get(i);					
					if (ciudad.getCiudadId().equals(Asociacion.getCiudadId())){
						out.print(" <option value='"+ciudad.getCiudadId()+"' Selected>"+ ciudad.getNombreCiudad()+"</option>");						
					}else {
						out.print(" <option value='"+ciudad.getCiudadId()+"'>"+ ciudad.getNombreCiudad()+"</option>");
					}				
				}
				listor 		= null;				
				ciudadU	= null;
			  %>
              </select></td>
		  </tr>
          <tr> 
            <td colspan="2" align="center"><%=sResultado%></td>
          </tr>
		  <tr> 
		  
		  <%
		  out.println("Division: " +Asociacion.getDivisionId());
	out.println("Union: " +Asociacion.getUnionId());
	out.println("Asociacion: " +Asociacion.getAsociacionId());
	out.println("NombreAsociacion: " +Asociacion.getNombreAsociacion());
		  %>
            <th colspan="2" align="center"> <a href="javascript:Nuevo()"><spring:message code='aca.Nuevo'/></a> 
			  &nbsp;<a href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> &nbsp; <a href="javascript:Modificar()"><spring:message code='aca.Modificar'/></a> 
              &nbsp; <a href="javascript:Borrar()"><spring:message code='aca.Borrar'/></a> &nbsp; <a href="javascript:Consultar()"><spring:message code='aca.Consultar'/></a> 
            </th>
          </tr>
        </table>
	</td>
  </tr>
<%
	listor  = null;
	listor	= null;
%>
</table>
</form>
<%@ include file= "../../cierra_enoc.jsp" %>