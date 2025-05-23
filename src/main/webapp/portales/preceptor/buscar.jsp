<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "aca.alumno.AlumPersonal"%>
<%@ page import= "aca.alumno.AlumUtil"%>

<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>

<%
	String origen			= request.getParameter("Origen");
	if (origen==null)origen="X";
	String sCarpeta 		= sCarpetab;
%>
<script type="text/javascript">

	function Consultar(){
		document.frmalumno.Accion.value="1";
		document.frmalumno.submit();
	}
	
	function BuscarNombre( ){
		if (document.frmnombre.Nombre.value!="" || document.frmnombre.Paterno.value!="" || document.frmnombre.Materno.value!=""){
			document.frmnombre.Accion.value="1";
			document.frmnombre.submit();
		}else{
			alert("Fill out at least one field");
			document.frmnombre.Nombre.focus();
		}
	}
	
	function BuscarCodigo( ){
		if(document.frmcodigo.codigo.value!=""){
			document.frmcodigo.Accion.value="2";
			document.frmcodigo.submit();
		}else{
			alert("Enter the User ID");
			document.frmcodigo.CodigoPersonal.focus();
		}
	}
	
	function SubirCodigo( Codigo ,nombre){
	  		opener.forma.codigo.value=Codigo;
	  		opener.forma.nombre.value=nombre;
	  		window.close();
	}	
</script>
<%
	ArrayList<aca.alumno.AlumPersonal> lisAlumno	 	= new ArrayList<aca.alumno.AlumPersonal>();

	AlumPersonal alumno		= new AlumPersonal();
	
	String sAccion			= request.getParameter("Accion");
	if (sAccion == null) sAccion = "0";
	int nAccion 			= Integer.parseInt(sAccion);	
	String sResultado		= "Select the Query Option";
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
			lisAlumno = AlumUtil.getLista(conEnoc, sNombre, sPaterno, sMaterno,"ORDER BY 2,3,4");
			if (lisAlumno.size() > 0)
				sResultado	= "Click on the student's name";
			else
				sResultado = "Not found";
			break;
		} 
		case 2:{
			alumno.setCodigoPersonal(request.getParameter("codigo"));
			if (AlumUtil.existeReg(conEnoc, request.getParameter("codigo")) == true){
				alumno = AlumUtil.mapeaRegId(conEnoc, request.getParameter("codigo"));
				sResultado = "Click on the student's name";
			}else{
				sResultado = "Not found: "+alumno.getCodigoPersonal();
			}
			break;			
		}
		case 3:{
			if (request.getParameter("CodigoPersonal").substring(0,1).equals("9")){
				session.setAttribute("codigoEmpleado", request.getParameter("CodigoPersonal"));
			}else{
				session.setAttribute("codigoAlumno", request.getParameter("CodigoPersonal"));
			}			
			sResultado = "Uploaded to session: "+request.getParameter("CodigoPersonal");
			if(!origen.equals("X"))response.sendRedirect("../../"+origen+"&carpeta="+sCarpetab);
		}
	}	
%>

<table style='margin:0 auto;' class="table">
<%if(!origen.equals("X")){%>
  <tr>
    <td align="CENTER"><a href="../../<%=origen%>&carpeta=<%=sCarpeta%>"><strong>Return</strong></a></td>
  </tr>
<%}%>
  <tr>
    <th align="CENTER">Search by name</th>
  </tr>
<form name="frmnombre" method="POST" action="buscar?Accion=1&Origen=<%=origen%>&carpeta=<%=sCarpeta%>">
<input name="Accion" type="hidden">
  <tr align='CENTER'>
    <td>
    	Name. <input type="Text" class="input-small" name="Nombre" size="3" maxlength="15">&nbsp;
		Surname. <input type="Text" class="input-small" name="Paterno" size="3" maxlength="15">&nbsp;
		Maiden name. <input type="Text" class="input-small" name="Materno" size="3" maxlength="15">&nbsp;
		<a href="javascript:BuscarNombre()" class="btn btn-primary">Search</a>
	</td>
  </tr>
</form>
<tr>
	<td>&nbsp;</td>
</tr>
 <tr>
	<th align="CENTER">Search by Student ID</th>
 </tr>
<form name="frmcodigo" method="POST" action="buscar?Accion=2&Origen=<%=origen%>&carpeta=<%=sCarpeta%>">
<input name="Accion" type="hidden">
  <tr align='CENTER'> 		
    <td> Student ID: 
      <input type="Text" class="text" name="codigo" id="codigo" size="6" maxlength="7">&nbsp;
      <a href="javascript:BuscarCodigo()" class="btn btn-primary">Search</a>
	</td>
  </tr>
</form>
</table>
<table style="width:50%; margin:0 auto;" class="table">
<tr>
  <td width="7%" align="center" colspan="3"><font size="2"><strong>Message: </strong> <%=sResultado%></font>
</tr>
<tr>
  <th width="7%" align="center"><spring:message code="aca.Numero"/></th>
  <th width="18%" align="center"><spring:message code="aca.Codigo"/></th>
  <th width="75%" align="center"><spring:message code="aca.Nombre"/></th>
</tr>
<%
	switch (nAccion){
		case 1:{
			for (int i=0; i< lisAlumno.size(); i++){
				alumno = (AlumPersonal) lisAlumno.get(i);
				if ((i % 2) == 0 ) sBgcolor = sColor; else sBgcolor = "";
%>				
  <tr <%=sBgcolor%>>
    <td align="center">
	  <%=i+1%>
	</td>
    <td align="center"><%=alumno.getCodigoPersonal()%></td>
    <td>
	  <a href="javascript:SubirCodigo('<%=alumno.getCodigoPersonal()%>','<%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%>')">
	  	<%=alumno.getNombre()%>&nbsp;<%=alumno.getApellidoPaterno()%>&nbsp;<%=alumno.getApellidoMaterno()%>
	  </a>
	</td>
  </tr>  
<%				if(lisAlumno.size()==1){
					alumno = (AlumPersonal) lisAlumno.get(0);
					if (alumno.getCodigoPersonal().substring(0,1).equals("9")){
						session.setAttribute("codigoEmpleado", alumno.getCodigoPersonal());
					}else{
						session.setAttribute("codigoAlumno", alumno.getCodigoPersonal());
					}			
					sResultado = "Uploaded to session: "+request.getParameter("CodigoPersonal");
					if(!origen.equals("X"))response.sendRedirect("../../"+origen+"&carpeta="+sCarpeta);
				}
			}	
			break;
		} 
		case 2:{
			alumno.setCodigoPersonal(request.getParameter("codigo"));
			if (!AlumUtil.existeReg(conEnoc, request.getParameter("codigo")))break;
%>		
  <tr> 
    <td align="center">	  
	  <%out.print("1"); %>
	</td>
    <td align="center"><%=alumno.getCodigoPersonal()%></td>
    <td>
	  <a href="javascript:SubirCodigo('<%=alumno.getCodigoPersonal()%>','<%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%>')">
	  	<%=alumno.getNombre()%>&nbsp;<%=alumno.getApellidoPaterno()%>&nbsp;<%=alumno.getApellidoMaterno()%>
	  </a>
	</td>
  </tr>	
<%  
			sResultado = "Uploaded to session: "+request.getParameter("codigo");
			if(!origen.equals("X"))response.sendRedirect("../../"+origen+"&carpeta="+sCarpeta);
			break;
		}
	}
%>  
</table> 
<%
	lisAlumno 	= null;
	alumno 		= null;	
%>
<!-- fin de estructura -->
<%@ include file= "../../cierra_enoc.jsp" %>