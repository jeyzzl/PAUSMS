<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.musica.MusiAlumno"%>
<%@ page import= "aca.musica.MusiAlumnoUtil"%>

<script type="text/javascript">	
	function BuscarNombre( ){
		if (frmNombre.Nombre.value!="" || frmNombre.Paterno.value!="" || frmNombre.Materno.value!=""){
			frmNombre.Accion.value="1";
			frmNombre.submit();
		}else{
			alert(" Es necesario por lo menos un dato..¡¡");
			document.frmNombre.Nombre.focus();
		}
	}
	
	function BuscarCodigo(){
		if(frmCodigo.CodigoId.value!=""){
				frmCodigo.Accion.value="2";
				frmCodigo.submit();
		}else{
			alert("Ingrese el Código..! ");
			document.frmCodigo.CodigoId.focus();
		}
	}	
	
</script>

<%
	ArrayList<aca.musica.MusiAlumno> lisAlumno	 		= new ArrayList<aca.musica.MusiAlumno>();
	MusiAlumnoUtil alumnoU		= new MusiAlumnoUtil();
	MusiAlumno alumno			= new MusiAlumno();
	
	String sAccion				= request.getParameter("Accion");
	if (sAccion == null) sAccion = "0";
	int nAccion 				= Integer.parseInt(sAccion);	
	String sResultado			= "Elija la opción de Consulta";
	String sBgcolor			= "";
	String sNombre 			= ""; 
	String sPaterno			= "";
	String sMaterno			= "";
	boolean bEnviar			= false;	
	
	switch (nAccion){
		case 1:{
			sNombre				= request.getParameter("Nombre");	
			sPaterno			= request.getParameter("Paterno");
			sMaterno			= request.getParameter("Materno");
			if (sNombre == null) sNombre = "";
			if (sPaterno== null) sPaterno = "";
			if (sMaterno== null) sMaterno = "";
			
			lisAlumno = alumnoU.getLista(conEnoc, sNombre, sPaterno, sMaterno,"ORDER BY 2,3,4");
			if (lisAlumno.size() > 0)
				sResultado = "Click sobre el nombre del Usuario";
			else
				sResultado = "No encontro..¡¡";			
			break;
		} 
		case 2:{
			alumno.setCodigoId(request.getParameter("CodigoId"));
			if (alumno.existeReg(conEnoc) == true){ 
				bEnviar = true;
				alumno.mapeaRegId(conEnoc, request.getParameter("CodigoId"));
				sResultado = "Click sobre el nombre del Usuario";
			}else{
				sResultado = "No existe: "+alumno.getCodigoId();
			}
			break;			
		}
		case 3:{
			alumno.setCodigoId(request.getParameter("CodigoId"));
			if (alumno.existeReg(conEnoc) == true){ 
				bEnviar = true;
				alumno.mapeaRegId(conEnoc, request.getParameter("CodigoId"));
				sResultado = "Click sobre el nombre del Usuario";
			}else{
				sResultado = "No existe: "+alumno.getCodigoId();
			}
			break;			
		}		
	}	
%>
<div class="container-fluid">
	<h1>Buscar Alumno</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="captura"><spring:message code="aca.Regresar"/></a> 
	</div>
	
	<form name="frmNombre" method="POST" action="buscar">
	<input name="Accion" type="hidden">	
	<div class="alert alert-success">
		B&uacute;squeda por: &nbsp;&nbsp;
		Nom.  
			<input type="Text" class="text" name="Nombre" size="3" maxlength="15">
		Pat. 
			<input type="Text" class="text" name="Paterno" size="3" maxlength="15">
		Mat.
			<input type="Text" class="text" name="Materno" size="3" maxlength="15">&nbsp;
		<a class="btn btn-success" href="javascript:BuscarNombre()">Buscar</a>		
	</div>	
	</form>
	
	<form name="frmCodigo" method="POST" action="buscar">
	<input name="Accion" type="hidden">
	<div class="alert alert-success">
		B&uacute;squeda por C&oacute;digo&nbsp;
		<input type="Text" class="text" name="CodigoId" size="8" maxlength="7" >
		&nbsp;
        <a class="btn btn-success" href="javascript:BuscarCodigo()">Buscar</a>
	</div>
	</form>
		
	<table class="table table-fullcondensed">
	<tr>
  		<td width="7%" align="center" colspan="3"><font size="2"><strong>Mensaje: </strong> <%=sResultado%></font></tr>
	<tr>
	  	<th width="7%" align="center"><spring:message code="aca.Numero"/></th>
	  	<th width="18%" align="center"><spring:message code="aca.Codigo"/></th>
	  	<th width="75%" align="center"><spring:message code="aca.Nombre"/></th>
	</tr>
<%
	switch (nAccion){
		case 1:{ 	
			for (int i=0; i< lisAlumno.size(); i++){
				alumno = (MusiAlumno) lisAlumno.get(i);
				if ((i % 2) == 0 ) sBgcolor = sColor; else sBgcolor = "";
%>				
	<tr <%=sBgcolor%>> 
		<td align="center">
		  <%=i+1%>
		</td>
	    <td align="center"><%=alumno.getCodigoId()%></td>
	    <td>
		  <a href="captura?Codigo=<%=alumno.getCodigoId()%>">
		  	<%=alumno.getNombre()%>&nbsp;<%=alumno.getApellidoPaterno()%>&nbsp;<%=alumno.getApellidoMaterno()%>
		  </a>
		</td>
	</tr>  
<%  
			}
			if (lisAlumno.size()==1){			
				response.sendRedirect("captura?Codigo="+alumno.getCodigoId());
			}	
			break;
		} 
		case 2:{
			if (bEnviar){
				response.sendRedirect("captura?Codigo="+alumno.getCodigoId());
			}
			break;			
		}		
	} 	
%>  
	</table> 
</div>
<%
	lisAlumno 	= null;
	alumno 		= null;
	alumnoU	= null;
%>
<!-- fin de estructura -->
<%@ include file= "../../cierra_enoc.jsp" %>