<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.spring.Maestros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	String origen			= request.getParameter("Origen")==null?"X":request.getParameter("Origen");	
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
			alert(" Input at least one entry");
			document.frmnombre.Nombre.focus();
		}
	}
	
	function BuscarCodigo( ){
		if(document.frmcodigo.CodigoPersonal.value!=""){
			document.frmcodigo.Accion.value="2";
			document.frmcodigo.submit();
		}else{
			alert("Enter the ID");
			document.frmcodigo.CodigoPersonal.focus();
		}
	}
	
	function SubirCodigo( CodigoPersonal ,nombre){
	  		opener.forma.nomina.value=CodigoPersonal;
	  		opener.forma.nP.value=nombre;
	  		window.close();
	}	
</script>
<%
	String resultado 				= (String) request.getAttribute("resultado");
	Maestros maestro				= (Maestros) request.getAttribute("maestro");
	boolean existe					= (boolean) request.getAttribute("existe");
	List<Maestros> lisMaestros	 	= (List<Maestros>) request.getAttribute("lisMaestros");	
	
	String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
	int numAccion 			= Integer.parseInt(accion);
	String bgcolor			= "";
%>

<table style='margin:0 auto;' class="table table-sm">
<%if(!origen.equals("X")){%>
  <tr>
    <td align="CENTER"><a href="../../<%=origen%>&carpeta=<%=sCarpeta%>"><strong>Return</strong></a></td>
  </tr>
<%}%>
  <tr>
    <th align="CENTER">Search by Name</th>
  </tr>
<form name="frmnombre" method="POST" action="buscar?Accion=1&Origen=<%=origen%>&carpeta=<%=sCarpeta%>">
<input name="Accion" type="hidden">
  <tr align='CENTER'>
    <td>
    	Name. <input type="Text" class="input-small" name="Nombre" size="3" maxlength="15">&nbsp;
		Father's Name. <input type="Text" class="input-small" name="Paterno" size="3" maxlength="15">&nbsp;
		Mother's Name. <input type="Text" class="input-small" name="Materno" size="3" maxlength="15">&nbsp;
		<a href="javascript:BuscarNombre()" class="btn btn-primary">Search</a>
	</td>
  </tr>
</form>
<tr>
	<td>&nbsp;</td>
</tr>
  <tr>
    <th align="CENTER">Search by ID</th>
  </tr>
<form name="frmcodigo" method="POST" action="buscar?Accion=2&Origen=<%=origen%>&carpeta=<%=sCarpeta%>">
<input name="Accion" type="hidden">
  <tr align='CENTER'> 		
    <td> ID: 
      <input type="Text" class="text" name="CodigoPersonal" id="CodigoPersonal" size="6" maxlength="7">&nbsp;
      <a href="javascript:BuscarCodigo()" class="btn btn-primary">Search</a>
	</td>
  </tr>
</form>
</table>
<table style="width:50%"  align="center" class="table">
<tr>
  <td width="7%" align="center" colspan="3"><font size="2"><strong>Message: </strong> <%=resultado%></font>
</tr>
<tr>
  <th width="7%" align="center"><spring:message code="aca.Numero"/></th>
  <th width="18%" align="center"><spring:message code="aca.Codigo"/></th>
  <th width="75%" align="center"><spring:message code="aca.Nombre"/></th>
</tr>
<%
	switch (numAccion){
		case 1:{			
			for (int i=0; i < lisMaestros.size(); i++){
				maestro = lisMaestros.get(i);
				if ((i % 2) == 0 ) bgcolor = sColor; else bgcolor = "";
%>				
  <tr <%=bgcolor%>>
    <td align="center">
	  <%=i+1%>
	</td>
    <td align="center"><%=maestro.getCodigoPersonal()%></td>
    <td>
	  <a href="javascript:SubirCodigo('<%=maestro.getCodigoPersonal()%>','<%=maestro.getNombre()%> <%=maestro.getApellidoPaterno()%> <%=maestro.getApellidoMaterno()%>')">
	  	<%=maestro.getNombre()%>&nbsp;<%=maestro.getApellidoPaterno()%>&nbsp;<%=maestro.getApellidoMaterno()%>
	  </a>
	</td>
  </tr>  
<%				if(lisMaestros.size()==1){
					maestro = (Maestros) lisMaestros.get(0);
					if (maestro.getCodigoPersonal().substring(0,1).equals("9")){
						session.setAttribute("codigoEmpleado", maestro.getCodigoPersonal());
					}else{
						session.setAttribute("codigoAlumno", maestro.getCodigoPersonal());
					}			
					resultado = "Uploaded to session: "+request.getParameter("CodigoPersonal");
					if(!origen.equals("X"))response.sendRedirect("../../"+origen+"&carpeta="+sCarpeta);
				}
			}	
			break;
		} 
		case 2:{			
			if (!existe)
				break;
%>		
  <tr> 
    <td align="center">	  
	  <%out.print("1"); %>
	</td>
    <td align="center"><%=maestro.getCodigoPersonal()%></td>
    <td>
	  <a href="javascript:SubirCodigo('<%=maestro.getCodigoPersonal()%>','<%=maestro.getNombre()%> <%=maestro.getApellidoPaterno()%> <%=maestro.getApellidoMaterno()%>')">
	  	<%=maestro.getNombre()%>&nbsp;<%=maestro.getApellidoPaterno()%>&nbsp;<%=maestro.getApellidoMaterno()%>
	  </a>
	</td>
  </tr>	
<%  
			if (request.getParameter("CodigoPersonal").substring(0,1).equals("9")){
				session.setAttribute("codigoEmpleado", request.getParameter("CodigoPersonal"));
			}else{
				session.setAttribute("codigoAlumno", request.getParameter("CodigoPersonal"));
			}			
			resultado = "Uploaded to session: "+request.getParameter("CodigoPersonal");
			if(!origen.equals("X"))response.sendRedirect("../../"+origen+"&carpeta="+sCarpeta);
			break;
		}
	}
%>  
</table>
<!-- fin de estructura -->