<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatPais"%>
<%@ page import="aca.catalogo.spring.CatEstado"%>
<%@ page import="aca.catalogo.spring.CatCiudad"%>
<%@ page import="aca.catalogo.spring.CatEscuela"%>

<script type="text/javascript">
	
	function Nuevo()	{		
		document.frmescuela.EscuelaId.value 		= " ";
		document.frmescuela.NombreEscuela.value 	= " ";
		document.frmescuela.PaisId.value 			= " ";
		document.frmescuela.EstadoId.value 			= " ";
		document.frmescuela.CiudadId.value 			= " ";
		document.frmescuela.Accion.value				="1";
		document.frmescuela.submit();		
	}
	
	function Grabar(){
		if(document.frmescuela.EscuelaId.value!="" && document.frmescuela.NombreEscuela.value!="" ){			
			document.frmescuela.Accion.value="2";
			document.frmescuela.submit();			
		}else{
			alert("<spring:message code='aca.JSCompletar'/>");
		}
	}
	
	function Borrar( ){
		if(document.frmescuela.EscuelaId.value!=""){
			if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
	  			document.frmescuela.Accion.value="4";
				document.frmescuela.submit();
			}			
		}else{
			alert("<spring:message code="aca.JSEscribirClave"/>");
			document.frmescuela.EscuelaId.focus(); 
	  	}
	}
	
	function PEC( Pec){		
		document.frmescuela.Accion.value="6";
		document.frmescuela.Pec.value 	= Pec;
		document.frmescuela.submit();
	}	
</script>
<%
	System.out.println("ENTRE1");

	CatEscuela catEscuela 	= (CatEscuela) request.getAttribute("catEscuela");
	String sResultado 		= (String) request.getAttribute("sResultado");
	int accionFmt 			= (int) request.getAttribute("accionFmt");
	
	List<CatPais> lisPais 		= (List<CatPais>) request.getAttribute("lisPais");
	List<CatEstado> lisEstado 	= (List<CatEstado>) request.getAttribute("lisEstado");
	List<CatCiudad> lisCiudad 	= (List<CatCiudad>) request.getAttribute("lisCiudad");

%>
<div class="container-fluid">
	<h1><spring:message code="catalogos.escuelas.Titulo2"/></h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="escuela"><i class="fas fa-arrow-left"></i></a>
	</div>
	<form action="accion_e" method="post" name="frmescuela" target="_self">
		<input type="hidden" name="Accion">
		<input name="Pec" type="hidden">
	<div class="row container-fluid">
		
		<div class="span2 col"> 
	    	<label for="Clave"><spring:message code="aca.Clave"/>:</label>
	       	<input name="EscuelaId" type="text" class="input input-mini form-control" id="EscuelaId" size="3" maxlength="3" required value="<%=catEscuela.getEscuelaId()%>" readonly>
			<br>       
	       	<label for="Nombre"><spring:message code="aca.Nombre"/>:</label>
	       	<input name="NombreEscuela" type="text" class="input input-xlarge form-control" id="NombreEscuela" required value="<%=catEscuela.getNombreEscuela()%>" size="40" maxlength="40">
	       	<br>
	       	<label for="aca.Pais"><spring:message code="aca.Pais"/>:</label>
	       	<select name="PaisId" class="input input-xlarge form-control" id="PaisId" onchange = "PEC('1')">
<%			for(CatPais pais : lisPais){%>
				<option value="<%=pais.getPaisId()%>" <%=pais.getPaisId().equals(catEscuela.getPaisId()) ? "selected" : ""%>><%=pais.getNombrePais()%></option>						
<%			}%>
      		</select>
       		<br> 
       	</div>
       	<div class="span2 col">
       		<label for="aca.Estado"><spring:message code="catalogos.division.estado"/>:</label>
        	<select name="EstadoId" class="input input-xlarge form-select" id="EstadoId" onChange= "javascript:PEC('2')">
<%			for(CatEstado estado : lisEstado){%>
				<option value="<%=estado.getEstadoId()%>" <%=estado.getEstadoId().equals(catEscuela.getEstadoId()) ? "selected" : ""%>><%=estado.getNombreEstado()%></option>						
<%			}%>
			</select>
			<br>
	        <label for="aca.Cuidad"><spring:message code="aca.Ciudad"/>:</label>
	      	<select name="CiudadId" class="input input-xlarge form-select" id="CiudadId">
<%			for(CatCiudad ciudad : lisCiudad){%>
				<option value="<%=ciudad.getCiudadId()%>" <%=ciudad.getCiudadId().equals(catEscuela.getCiudadId()) ? "selected" : ""%>><%=ciudad.getNombreCiudad()%></option>						
<%			}%>
		  	</select>
		  	<br>
		 </div>
	   </div>
<%	if(accionFmt==1){%>
		<div class="alert alert-success">
			<spring:message code="aca.Guardado"/><%=sResultado%>
		</div>
<%	}else if(accionFmt==2){%>
		<div class="alert alert-danger">
			<spring:message code="aca.NoGuardado"/><%=sResultado%>
		</div>
<%	}else if(accionFmt==3){%>
		<div class="alert alert-success">
			<spring:message code="aca.Modificado"/><%=sResultado%>
		</div>
<%	}else if(accionFmt==4){%>
		<div class="alert alert-danger">
			<spring:message code="aca.NoModificado"/><%=sResultado%>
		</div>
<%	}else if(accionFmt==5){%>
		<div class="alert alert-success">
			<spring:message code="aca.Eliminado"/><%=sResultado%>
		</div>	
<%	}else if(accionFmt==6){%>
		<div class="alert alert-danger">
			<spring:message code="aca.NoEliminado"/><%=sResultado%>
		</div>
<%	}else if(accionFmt==7){%>
		<div class="alert alert-info">
			<spring:message code="aca.NoExiste"/><%=sResultado%>
		</div>
<%	}else if(accionFmt==8){%>
		<div class="alert alert-success">
			<spring:message code="aca.YaExiste"/><%=sResultado%>
		</div>
<%	}else if(accionFmt==9){%>
		<div class="alert alert-success">		    		
			<spring:message code="aca.Consulta"/><%=sResultado%>
		</div>
<%	}%>
       	<div class="alert alert-info">
<%--           	<a href="javascript:Nuevo()" class="btn btn-primary"><spring:message code="aca.Anadir"/></a>&nbsp; --%>
          	<a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>&nbsp;  
          	<a href="javascript:Borrar()" class="btn btn-primary"><spring:message code="aca.Eliminar"/></a>
   	  	</div>
	</form>
</div>
