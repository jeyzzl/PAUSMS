<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="cred" scope="page" class="aca.cred.CredHsm"/>
<jsp:useBean id="credU" scope="page" class="aca.cred.CredHsmUtil"/>

<script type="text/javascript">
	
	function Nuevo(){	
		document.forma.CodigoEmpleado.value = "";
		document.forma.Nombre.value 		= "";
		document.forma.Accion.value			= "0";
		document.forma.submit();
	}
	
	function Grabar(){
		if(document.forma.CodigoEmpleado.value!="" && document.forma.Nombre.value!=""){
			document.forma.Accion.value	="1";
			document.forma.submit();			
		}else{
			alert("Favor de completar todos los campos");
		}
	}
	
	function Borrar(){
		if(confirm("Estas seguro de eliminar el registro!")==true){
			document.forma.Accion.value	="2";
			document.forma.submit();			
		}
	}
	
	function consultar(){
		if(document.forma.CodigoEmpleado.value!=""){
			document.getElementById('consultar').style.visibility = "visible";
		}
	}
	
	function Consultar(){	
		document.forma.Accion.value	="3";
		document.forma.submit();
	}
	
	function camara(){
		location.href="tomarfoto.jsp";
	}
</script>
<%!
	private String getRandomString(){
        String alphaNum="1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sbRan = new StringBuffer(11);
        int num;
        for(int i = 0; i < 11; i++){
          num = (int)(Math.random()* (alphaNum.length() - 1));
          sbRan.append(alphaNum.substring(num, num+1));
        }
        return sbRan.toString();
      }
%>
<%
	String accion  = request.getParameter("Accion")==null?"":request.getParameter("Accion");
	String mensaje = "";	
	String clave   = request.getParameter("CodigoEmpleado")==null?"":request.getParameter("CodigoEmpleado");
	
	if(cred.getClave().equals("")){
		cred.mapeaRegId(conEnoc, clave);
	}	
	session.setAttribute("codigoEmpleado", clave);
	
//  ---NUEVO----
	if(accion.equals("0")){
		cred.setClave(credU.maxReg(conEnoc,"9899"));
		
//   ---GRABAR----		
	}else if(accion.equals("1")){ 
		cred.setClave(request.getParameter("CodigoEmpleado"));
		cred.setNombre(request.getParameter("Nombre"));
		cred.setArea(request.getParameter("Area"));
		cred.setFondo(request.getParameter("Fondo"));
		cred.setEstado(request.getParameter("Estado"));
		
		if(credU.existeReg(conEnoc, request.getParameter("CodigoEmpleado"))){
			if(credU.updateReg(conEnoc, cred)){
				mensaje = "<font color='blue' size='2'>Se Modificó Correctamente</font>";
			}else{
				mensaje = "<font color='red' size='2'>Ocurrió un error al modificar</font>";
			}
		}else{
			if(credU.insertReg(conEnoc, cred)){
				mensaje = "<font color='blue' size='2'>Se Grabó Correctamnte</font>";
			}else{
				mensaje = "<font color='red' size='2'>Ocurrió un error al grabar</font>";
			}
		}
	}else if(accion.equals("2")){//   ---BORRAR----
		cred.setClave(request.getParameter("CodigoEmpleado"));
		if(credU.deleteReg(conEnoc, request.getParameter("CodigoEmpleado"))){
			mensaje = "<font color='blue' size='2'>Se Borró Correctamnte</font>";
			cred.setClave("");
		}else{
			mensaje = "<font color='red' size='2'>Ocurrió un error al borrar</font>";
		}
	}else if(accion.equals("3")){//   ---CONSULTAR----
		cred.mapeaRegId(conEnoc,request.getParameter("CodigoEmpleado"));
		session.setAttribute("codigoEmpleado", clave);
	}
	
	boolean tieneFoto 			= false; 
	
	// Verifica si existe la imagen	
	String dirFoto = application.getRealPath("/WEB-INF/fotos/"+clave+".jpg");
	java.io.File foto = new java.io.File(dirFoto);
	if (foto.exists()){
		tieneFoto = true;
	}
%>
<style type="text/css">
	#consultar{
		visibility:hidden;
	}
	
	
	#sombra {
		float:left;
		padding:0 5px 5px 0; /*Esta es la profundidad de nuestra sombra, sí haces más grandes estos valores, el efecto de sombra es mayor también */
		background: url(../../imagenes/sombra.gif) no-repeat bottom right; /*Aquí es donde ponemos la imagen como fondo colocando su ubicación*/
		height: 185px;
		
		position:relative;
		top:4px;
		left: 9px;
	} 
	
	#sombra img {
		display:block;
		position:relative;
		top: 0px; /* Desfasamos la imagen hacia arriba */
		left: 0px; /*Desfasamos la imagen hacia la izquierda */
		padding:5px;
		background:#FFFFFF; /*Definimos un color de fondo */
		border:1px solid;
		border-radius:.4em;
		border-color: #CCCCCC #666666 #666666 #CCCCCC Creamos un marco para acentuar el efecto */
	}
</style>
<form action="empleado.jsp" method="post" name="forma" target="_self">
<input type="hidden" name="Accion">
<table  width="80%" align="center">
	<tr>
		<td><a href="clinica.jsp">R e g r e s a r</td>
	</tr>
</table>
<br>
<table style="width:40%" align="center" class="tabla" >
	<tr>
		<th colspan="2">&nbsp;</th>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td valign="top">
			<table style="margin: 0 auto;" >
				<tr>
					<td align='center' width="129" rowspan="11" nowrap valign="bottom">
                		<div id="sombra"><img src='../../foto?Codigo=<%=clave%>&Tipo=O' width="130"><br></div>
					</td>
				</tr>
				<tr>
					<td align="center" width="50" rowspan="11">
	                	<a href="tomarfoto.jsp?CodigoEmpleado=<%=cred.getClave() %>" title="Tomar la Foto"><img src='../../imagenes/camaraweb.png' width="20" ></a>&nbsp;            	
	                	<a href="subir.jsp?CodigoEmpleado=<%=clave%>" title="Subir Foto de un archivo"><img src='../../imagenes/upload.png' width="30" ></a>
		<% 				if (tieneFoto){%>                	                	
		                	<a href="../../fotoBajar.jsp?mat=<%=clave%>" title="Descargar la Foto"><img src='../../imagenes/descargar.png' width="25" ></a>&nbsp;
		<%				} %>
                	</td>
				</tr>
			</table>
		</td>
		<td valign="top">
			<table align="left">
				<tr>
					<td><spring:message code="aca.Clave"/>:</td>
					<td><input onkeyup='consultar();' name="CodigoEmpleado" id="CodigoEmpleado" type="text" class="text" size="10" maxlength="10" value="<%=cred.getClave()%>" >
					<a id="consultar" href="javascript:Consultar()">&nbsp;Consultar</a></td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td><spring:message code="aca.Nombre"/>:</td><td><input name="Nombre" id="Nombre" type="text" class="text" size="40" maxlength="40" value="<%=cred.getNombre()%>"></td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td>Area:</td>
					<td>
						 <select name="Area" id="Area">
						    <option value="Área Administrativa" <%if(cred.getArea().equals("Área Administrativa")){out.print("selected");}%>>Área Administrativa</option>
						    <option value="Área Clínica" <%if(cred.getArea().equals("Área Clínica")){out.print("selected");}%>>Área Clínica</option>
				    		<option value="Área de Servicios" <%if(cred.getArea().equals("Área de Servicios")){out.print("selected");}%>>Área de Servicios</option>
			  		  </select>  
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td>Fondo:</td>
					<td>
						 <select name="Fondo" id="Fondo">
						    <option value="hospital" <%if(cred.getFondo().equals("hospital")){out.print("selected");}%>>Hospital</option>
						    <option value="vision" <%if(cred.getFondo().equals("vision")){out.print("selected");}%>>Vision</option>
				    		<option value="dental" <%if(cred.getFondo().equals("dental")){out.print("selected");}%>>Dental</option>
				    		<option value="vidasana" <%if(cred.getFondo().equals("vidasana")){out.print("selected");}%>>Vida Sana</option>
			  		  </select>  
					</td>
				</tr>
				<tr>
					<td><spring:message code="aca.Estado"/>:</td>
					<td>
					  <select name="Estado" id="Estado">
					    <option value="0" <%if(cred.getEstado().equals("0")){out.print("selected");}%>>Anterior</option>
						<option value="1" <%if(cred.getEstado().equals("1")){out.print("selected");}%>><spring:message code='aca.Nuevo'/></option>
						<option value="2" <%if(cred.getEstado().equals("2")){out.print("selected");}%>>Foto</option>
				    	<option value="3" <%if(cred.getEstado().equals("3")){out.print("selected");}%>>Impreso</option>
			  		  </select>  
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr><td align="center" colspan="2"><%=mensaje%>&nbsp;</td></tr>
	<tr>
		<th align="center" colspan="2">
					   <a href="javascript:Nuevo()"><spring:message code='aca.Nuevo'/></a> 
		        &nbsp; <a href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> 
              	&nbsp; <a href="javascript:Borrar()"><spring:message code='aca.Borrar'/></a>
		</th>
	</tr>
</table>
</form>
<%@ include file= "../../cierra_enoc.jsp" %>