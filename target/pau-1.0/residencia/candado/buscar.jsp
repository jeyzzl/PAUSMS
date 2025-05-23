<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="bOp" scope="page" class="aca.portal.Alumno"/>
<jsp:useBean id="UsuariosU" scope="page" class="aca.vista.UsuariosUtil"/>
<jsp:useBean id="Usuarios" scope="page" class="aca.vista.Usuarios"/>
<%
	String origen			= request.getParameter("Origen")==null?"X":request.getParameter("Origen");		
	String sCarpeta		= request.getParameter("carpeta");
%> 
<head>
<script type="text/javascript">
	
	function recarga(){
		document.frmnombre.Accion.value = "0";
		document.frmnombre.submit();
	}

	function Consultar(){
		document.frmnombre.Accion.value="1";
		document.frmnombre.submit();
	}
	
	function BuscarNombre(opcion){
		if (document.frmnombre.Nombre.value!="" || document.frmnombre.Paterno.value!="" || document.frmnombre.Materno.value!=""){
			document.frmnombre.Opcion.value=opcion;
			document.frmnombre.Accion.value="1";
			document.frmnombre.submit();
		}else{
			alert("Fill out at least one field");
			document.frmnombre.Nombre.focus();
		}
	}
	
	function BuscarCodigo( ){
		if(document.frmcodigo.CodigoPersonal.value!=""){
			document.frmcodigo.Accion.value="2";
			document.frmcodigo.submit();
		}else{
			alert("Enter the code");
			document.frmcodigo.CodigoPersonal.focus();
		}
	}
	
	function SubirCodigo( CodigoPersonal ){
	  		document.location="buscar?Accion=3&CodigoPersonal="+CodigoPersonal+"&Origen=<%=origen%>&carpeta=<%=sCarpeta%>";
	}	
	
	function nombreKeyPress(event){
		var key;
		if(window.event){ // IE
			key = event.keyCode;
		}else if(event.which){ // Netscape/Firefox/Opera
			key = event.which;
		}
		if(key == 13)
			if (document.frmnombre.Nombre.value!="" || document.frmnombre.Paterno.value!="" || document.frmnombre.Materno.value!=""){
				document.frmnombre.Accion.value="1";
				document.frmnombre.submit();
			}else{
				alert("Fill out at least one field");
				document.frmnombre.Nombre.focus();
			}
	}
</script>
</head>
<%
	ArrayList<aca.vista.Usuarios> lisUsuarios	 	= new ArrayList<aca.vista.Usuarios>();	
	
	String sAccion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String opcion			= request.getParameter("Opcion")==null?"Alumno":request.getParameter("Opcion");
	
	int nAccion 			= Integer.parseInt(sAccion);
	String sResultado		= "Select the query option";
	String sBgcolor			= "";
	String sNombre 			= ""; 
	String sPaterno			= "";
	String sMaterno			= "";	
	
	switch (nAccion){
		case 1:{
			sNombre			= request.getParameter("Nombre");
			sPaterno		= request.getParameter("Paterno");
			sMaterno		= request.getParameter("Materno");
			if (sNombre == null) sNombre = "";
			if (sPaterno== null) sPaterno = "";
			if (sMaterno== null) sMaterno = "";
			lisUsuarios = UsuariosU.getLista(conEnoc, sNombre, sPaterno, sMaterno, opcion,"ORDER BY 2,3,4");
			if (lisUsuarios.size() > 0)
				sResultado	= "Click on the student's name";
			else
				sResultado = "Not found";
			break;
		} 
		case 2:{
			Usuarios.setCodigoPersonal(request.getParameter("CodigoPersonal"));
			if (Usuarios.existeReg(conEnoc) == true){
				Usuarios.mapeaRegId(conEnoc, request.getParameter("CodigoPersonal"));
				sResultado = "Click on the student's name";
			}else{
				sResultado = "Not found: "+Usuarios.getCodigoPersonal();
			}
			break;			
		}
		case 3:{
			if (request.getParameter("CodigoPersonal").substring(0,1).equals("9")){
				session.setAttribute("codigoEmpleado", request.getParameter("CodigoPersonal"));
				session.setAttribute("codigoUltimo", request.getParameter("CodigoPersonal"));
			}else{
				session.setAttribute("codigoAlumno", request.getParameter("CodigoPersonal"));
				session.setAttribute("colorPortal",bOp.obtenColor(conEnoc,request.getParameter("CodigoPersonal")));
				session.setAttribute("codigoUltimo", request.getParameter("CodigoPersonal"));
			}			
			sResultado = "Loaded to session: "+request.getParameter("CodigoPersonal");
			response.sendRedirect("candado");
		}
	}	
%>
<div class="container-fluid">
<br>
<div class="alert alert-info d-flex align-items-center">
		<a href="candado" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
<form name="frmnombre" method="POST" action="buscar?Origen=<%=origen%>&carpeta=<%=sCarpeta%>">
<input name="Accion" type="hidden">
<table id="table" class="table table-sm table-bordered">
<%if(!origen.equals("X")){%>
  <tr>
    <td align="CENTER"><a href="../../<%=origen%>&carpeta=<%=sCarpeta%>"><strong>Return</strong></a></td>
  </tr>
<%}%>
  <tr align='CENTER'>
    <td>Filter results by:
      <select name="Opcion" onchange='javascript:recarga()'>
        <option value='Alumno' <% if(opcion.equals("Alumno")) out.println("Selected");%>>Students</option>
        <option value='Empleado' <% if(opcion.equals("Empleado")) out.println("Selected");%>>Employees</option>
      </select>
    </td>
  </tr>
  <tr>
    <th align="CENTER">Search by name</th>
  </tr>
  <tr align='CENTER'>
    <td>Name.  
			<input type="Text" class="text" name="Nombre" size="3" maxlength="15" onkeypress="nombreKeyPress(event);">
		Sur. 
			<input type="Text" class="text" name="Paterno" size="3" maxlength="15" onkeypress="nombreKeyPress(event);">
		Mad. N.
			<input type="Text" class="text" name="Materno" size="3" maxlength="15" onkeypress="nombreKeyPress(event);">
			<a class="btn btn-primary" href="javascript:BuscarNombre('<%=opcion%>')">Search</a>
	</td>
  </tr>
</table>
</form>
<form name="frmcodigo" method="POST" action="buscar?Accion=2&Origen=<%=origen%>&carpeta=<%=sCarpeta%>">
<input name="Accion" type="hidden">
<table id="table" class="table table-sm table-bordered">
  <tr>
    <th align="CENTER">Search by User ID</th>
  </tr>
  	<tr align="CENTER">
    <td> User ID: 
      <input type="Text" class="text" name="CodigoPersonal" id="CodigoPersonal" size="8" maxlength="7">
		<a class="btn btn-primary" href="javascript:BuscarCodigo()">Search</a>
	</td>
  </tr>
</table>
</form>
<br>
<table id="table" class="table table-sm table-bordered">
<tr>
  <td width="7%" align="center" colspan="3"><font size="2"><strong>Message: </strong> <%=sResultado%></font></tr>
<tr>
  <th width="7%" align="center"><spring:message code="aca.Numero"/></th>
  <th width="18%" align="center"><spring:message code="aca.Codigo"/></th>
  <th width="75%" align="center"><spring:message code="aca.Nombre"/></th>
</tr>
<%
	switch (nAccion){
		case 1:{
			for (int i=0; i< lisUsuarios.size(); i++){
				Usuarios = (aca.vista.Usuarios) lisUsuarios.get(i);
%>				
  <tr >
    <td align="center">
	  <%=i+1%>
	</td>
    <td align="center"><%=Usuarios.getCodigoPersonal()%></td>
    <td>
	  <a href="javascript:SubirCodigo('<%=Usuarios.getCodigoPersonal()%>')">
	  	<%=Usuarios.getNombre()%>&nbsp;<%=Usuarios.getApellidoPaterno()%>&nbsp;<%=Usuarios.getApellidoMaterno()%>
	  </a>
	</td>
  </tr>  
<%				if(lisUsuarios.size()==1){
					Usuarios = (aca.vista.Usuarios) lisUsuarios.get(0);
					if (Usuarios.getCodigoPersonal().substring(0,1).equals("9")){
						session.setAttribute("codigoEmpleado", Usuarios.getCodigoPersonal());
						session.setAttribute("codigoUltimo", Usuarios.getCodigoPersonal());
					}else{
						session.setAttribute("codigoAlumno", Usuarios.getCodigoPersonal());
						session.setAttribute("colorPortal",bOp.obtenColor(conEnoc,Usuarios.getCodigoPersonal()));
						session.setAttribute("codigoUltimo", Usuarios.getCodigoPersonal());
					}			
					sResultado = "Loaded to session: "+request.getParameter("CodigoPersonal");
					response.sendRedirect("candado");
				}
			}	
			break;
		} 
		case 2:{
			Usuarios.setCodigoPersonal(request.getParameter("CodigoPersonal"));
			if (!Usuarios.existeReg(conEnoc))break;
%>		
  <tr> 
    <td align="center">	  
	  <%out.print("1"); %>
	</td>
    <td align="center"><%=Usuarios.getCodigoPersonal()%></td>
    <td>
	  <a href="javascript:SubirCodigo('<%=Usuarios.getCodigoPersonal()%>')">
	  	<%=Usuarios.getNombre()%>&nbsp;<%=Usuarios.getApellidoPaterno()%>&nbsp;<%=Usuarios.getApellidoMaterno()%>
	  </a>
	</td>
  </tr>	
<%  
			if (request.getParameter("CodigoPersonal").substring(0,1).equals("9")){
				session.setAttribute("codigoEmpleado", request.getParameter("CodigoPersonal"));
				session.setAttribute("codigoUltimo", request.getParameter("CodigoPersonal"));
			}else{
				session.setAttribute("codigoAlumno", request.getParameter("CodigoPersonal"));
				session.setAttribute("colorPortal",bOp.obtenColor(conEnoc,request.getParameter("CodigoPersonal")));
				session.setAttribute("codigoUltimo", request.getParameter("CodigoPersonal"));
			}			
			sResultado = "Loaded to session: "+request.getParameter("CodigoPersonal");
			response.sendRedirect("candado");
			break;
		}
	}
%>  
</table> 
</div>
<%
	// borra la lista
	lisUsuarios 	= null;	
%>
<!-- fin de estructura -->
<%@ include file= "../../cierra_enoc.jsp" %>