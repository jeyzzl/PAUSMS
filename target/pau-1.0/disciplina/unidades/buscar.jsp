<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="aca.alumno.AlumPersonal"%>

<jsp:useBean id="alumnoU" scope="page" class="aca.alumno.AlumUtil" />

<script type="text/javascript">
	function BuscarNombre(){
		if (document.frmnombre.Nombre.value!="" || document.frmnombre.Paterno.value!="" || document.frmnombre.Materno.value!=""){
			document.frmnombre.Accion.value="2";
			document.frmnombre.submit();
		}else{
			alert(" At least one field is required ");
			document.frmnombre.Nombre.focus();
		}
	}
	
	function BuscarCodigo(){
		if(frmcodigo.CodigoPersonal.value!=""){
			document.frmcodigo.Accion.value="3";
			document.frmcodigo.submit();
		}else{
			alert("Enter the ID");
			document.frmcodigo.CodigoPersonal.focus();
		}
	}
	
	function regresar(strID){
		self.opener.document.unidad.empleado.value = strID;
		close();
	}
</script>

<%	String sResultado		= "Select the query option";
	String sBgcolor			= "";
	String sNombre 			= "";
	String sPaterno			= "";
	String sMaterno			= "";
	String sAccion			= request.getParameter("Accion");
	
	if (sAccion == null){  sAccion = "1"; }
	int nAccion 			= Integer.parseInt(sAccion);	

	
	//System.out.println("Accion="+nAccion);
	ArrayList lisAlumno	 	= new ArrayList();
	AlumPersonal alumno		= new AlumPersonal();
	
	switch (nAccion){
		case 1:{
			sResultado = "Complete Query";
			break;
		}
		case 2:{
			sNombre			= request.getParameter("Nombre");	
			sPaterno		= request.getParameter("Paterno");
			sMaterno		= request.getParameter("Materno");
			if (sNombre == null) sNombre = "";
			if (sPaterno== null) sPaterno = "";
			if (sMaterno== null) sMaterno = "";
			lisAlumno = alumnoU.getLista(conEnoc, sNombre, sPaterno, sMaterno,"ORDER BY 2,3,4");
			if (lisAlumno.size() > 0){
				sResultado	= "Click on the student";
			}else{
				sResultado = "Not found";
			}
			break;
		} 
		case 3:{
			alumno.setCodigoPersonal(request.getParameter("CodigoPersonal"));
			//System.out.println("Codigo2="+alumno.getCodigoPersonal());
			if (alumnoU.existeReg(conEnoc, request.getParameter("CodigoPersonal")) == true){
				alumno = alumnoU.mapeaRegId(conEnoc, request.getParameter("CodigoPersonal"));
				sResultado = "Click on the student";
			}else{
				sResultado = "Not found: "+alumno.getCodigoPersonal();
			}
			break;			
		}
	}
%>

<table style='margin:0 auto;'>
	<tr>
    	<th align="CENTER">Search by Name</th>
  	</tr>
	<form name="frmnombre" method="POST" action="buscar">
		<input name="Accion" type="hidden">
  		<tr align='CENTER'>
    		<td>Name.  
				<input type="Text" class="text" name="Nombre" size="3" maxlength="15">
				Sur. 
				<input type="Text" class="text" name="Paterno" size="3" maxlength="15">
				MN.
				<input type="Text" class="text" name="Materno" size="3" maxlength="15">
			</td>
  		</tr>
  		<tr align='CENTER'>
    		<td><a href="javascript:BuscarNombre()">Search</a></td>
  		</tr>
	</form>
  	<tr>
    	<th align="CENTER">Search by ID</th>
  	</tr>
	<form name="frmcodigo" method="POST" action="buscar">
		<input name="Accion" type="hidden">
		<tr align='CENTER'> 		
    		<td> ID: 
      		<input type="Text" class="text" name="CodigoPersonal" id="CodigoPersonal" size="8" maxlength="7">
			</td>
  		</tr>
  		<tr align='CENTER'>
    		<td><a href="javascript:BuscarCodigo()">Search</a></td>
  		</tr>
	</form>
</table>
<br>
<table style="width:50%"  align="center">
	<tr>
    	<td width="7%" align="center" colspan="3"><font size="2"><strong>Message: 
      	</strong> <%=sResultado%></font></td>
    </tr>
	<tr>
    	<th width="7%" height="23" align="center"><spring:message code="aca.Numero"/></th>
  		<th width="18%" align="center"><spring:message code="aca.Codigo"/></th>
  		<th width="75%" align="center"><spring:message code="aca.Nombre"/></th>
	</tr>
<%	switch (nAccion){
		case 1:{
%>		
  	<tr>
    	<td align="center">&nbsp;</td>
    	<td align="center"><%=alumno.getCodigoPersonal()%></td>
    	<td><a href="javascript:Regresar('<%=alumno.getCodigoPersonal()%>')">
	  	<%=alumno.getNombre()%>&nbsp;<%=alumno.getApellidoPaterno()%>&nbsp;<%=alumno.getApellidoMaterno()%></a>
		</td>
  	</tr>  	
<%			break;
		}
		case 2:{
			for (int i=0; i<lisAlumno.size(); i++){
				 alumno = (AlumPersonal) lisAlumno.get(i);
				if ((i % 2) == 0 ){ sBgcolor = sColor; } else { sBgcolor = ""; }
%>				
  	<tr <%=sBgcolor%>>
    	<td align="center"> <%=i+1%></td>
    	<td align="center"><%=alumno.getCodigoPersonal()%></td>
    	<td><a href="javascript:regresar('<%=alumno.getCodigoPersonal()%>')">
	  	<%=alumno.getNombre()%>&nbsp;<%=alumno.getApellidoPaterno()%>&nbsp;<%=alumno.getApellidoMaterno()%></a>
		</td>
  	</tr>  
<%			}
			break;
		} 
		case 3:{
			alumno.setCodigoPersonal(request.getParameter("CodigoPersonal"));
%>		
  <tr> 
    <td align="center">	  
<%			out.print("1"); 		%>
	</td>
    <td align="center"><%=alumno.getCodigoPersonal()%></td>
    <td>
	  <a href="javascript:regresar('<%=alumno.getCodigoPersonal()%>')">
	  	<%=alumno.getNombre()%>&nbsp;<%=alumno.getApellidoPaterno()%>&nbsp;<%=alumno.getApellidoMaterno()%>
	  </a>
	</td>
  </tr>	
<%  		break;
		}
	}
%>  
</table> 
<%	lisAlumno 	= null;%>

<%@ include file= "../../cierra_enoc.jsp" %>