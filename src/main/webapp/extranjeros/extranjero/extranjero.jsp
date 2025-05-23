<%@ page import= "java.util.List"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.leg.spring.LegExtranjero"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Grabar(){		
		if(document.frmExt.Rne.value!="" && document.frmExt.Comentario.value!="" && document.frmExt.Telefono.value!=""){
			document.frmExt.submit();
		}else{	
			alert("Complete the fields..!");
		}	
	}
</script>

<%	// Variables
	String codigoPersonal			= (String) session.getAttribute("codigoAlumno");
	String paisNombre				= (String) request.getAttribute("paisNombre");
	String planActual				= (String) request.getAttribute("planActual");
	String carreraNombre			= (String) request.getAttribute("carreraNombre");
	String carreraId				= (String) request.getAttribute("carreraId");
	boolean inscrito 				= (boolean) request.getAttribute("inscrito");
	AlumPersonal alumPersonal		= (AlumPersonal) request.getAttribute("alumPersonal");
	LegExtranjero legExtranjero		= (LegExtranjero) request.getAttribute("legExtranjero");
	String mensaje 					= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	List<CatCarrera> lisCarreras	= (List<CatCarrera>) request.getAttribute("lisCarreras");
	
	boolean	esExtranjero			= false;
	if ( !alumPersonal.getNacionalidad().equals("91") ) esExtranjero = true;
	
	String carreraDiferente = "";
	
	if(!legExtranjero.getCarrera().equals(carreraId)){
		carreraDiferente = "class='badge bg-warning'";
	}
	
%>
<div class="container-fluid">
	<h2>Foreigner Data<small class="text-muted fs-4"> ( <%=codigoPersonal%> - <%= alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno()%>)</small> </h2>
	<div class="alert alert-info">
		<strong>&nbsp;&nbsp;Plan:&nbsp;</strong>&nbsp; <%=planActual%>
		<strong>&nbsp;&nbsp;Degree:&nbsp;</strong>&nbsp; <span <%=carreraDiferente%>><%=carreraId%></span> - <%=carreraNombre%>
		<strong>&nbsp;&nbsp;Country:</strong>&nbsp;&nbsp;<%=paisNombre%>
    	<strong>&nbsp;&nbsp;Status:&nbsp;&nbsp;</strong><%=inscrito?"Enrolled":"Not Enrolled"%>
	</div>	
<%	if (!mensaje.equals("-")){%>	
	<div class="alert alert-success"><%=mensaje%></div>
<%	} %>	
<%	if( esExtranjero ){	
%>
	<form name="frmExt" method="post" action="grabar">
	<div class="row">		
		<!-------------------- SEPARACION ENTRE COLUMNAS -------------------->
		<div class="col-4">
			<div class="form-group">
				<label for="NUE"><b>NUE:</b></label>
				<input type="text" class="form-control" name="Rne" value="<%=legExtranjero.getRne()%>" maxlength="22" size="19" align="top"></td>
	    		<br>
	    		<label for="Telefono"><b>Phone Number:</b></label>
	    		<input type="text" class="form-control" name="Telefono" value="<%=legExtranjero.getTelefono()%>" maxlength="30" size="30"></td>
	    		<br>
	    		<label for="Carrera"><b>Degree:</b></label>
	    		<select name="Carrera" style="width:377px;" class="form-select">
<%		for(CatCarrera carrera : lisCarreras){ %>		
					<option value="<%=carrera.getCarreraId()%>" <%=carrera.getCarreraId().equals(legExtranjero.getCarrera())?"Selected":""%>>
				<%=carrera.getCarreraId()+":"+carrera.getNombreCarrera()%>
					</option>			
<%		}%>
      			</select>
	    		<br>
	    		<label for="Comentario"><b>Comments:</b></label>
	    		<textarea id="Comentario" name="Comentario" rows="2" cols="40" class="form-control" maxlength="500"><%=legExtranjero.getComentario()%></textarea>     
	    		<br>    		
			</div>    		
    	</div>
    	<div class="col-3">
			<div class="control-group">		
				<div id="sombra">					
					<img class="img-thumbnail rounded mx-auto d-block" src="../../foto?Codigo=<%=codigoPersonal%>&Tipo=O" width="240">
				</div>				
			</div>  
		</div>
    </div>    
    <div class="alert alert-info"><a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a></div>
    </form>
	
<%	}else{%>
	<div class="alert alert-info"><h4>Este alumno no es extranjero..!</h4></div>
<%	}	%>
</div>