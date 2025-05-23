<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<% 	
	String matricula 			= (String) session.getAttribute("codigoAlumno");
	String documentoId			= request.getParameter("DocumentoId")==null?"1":request.getParameter("DocumentoId");	
	String hoja					= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");	
	String origen				= request.getParameter("Origen")==null?"O":request.getParameter("Origen");
	String documentoNombre		= (String) request.getAttribute("documentoNombre");
	String carreraAlumno		= (String) request.getAttribute("carreraAlumno");
	boolean existeImagen		= (boolean) request.getAttribute("existeImagen");
	AlumPersonal alumPersonal	= (AlumPersonal)request.getAttribute("alumPersonal");
	AlumPlan alumPlan			= (AlumPlan)request.getAttribute("alumPlan");
	String mensaje 				= (String)request.getParameter("Mensaje")==null?"":request.getParameter("Mensaje");			
%>
<div class="container-fluid">
	<h2>Upload Document <small class="text-muted fs-6">( <%=matricula%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%> - <%=documentoNombre%> )</small></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a href="documentos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		&nbsp;
		<form name = "form2" target="_self" method="post" action="addimg?Origen=<%=origen%>&DocumentoId=<%=documentoId%>&Hoja=<%=hoja%>">
		<b>Page:</b>&nbsp;
		<select name="Hoja" size="1" onChange="if (this.options[this.selectedIndex].value){window.open(this.options[this.selectedIndex].value,'_self')}">
 <%		for(int i=1; i<=30; i++){ %>
        	<option value="addimg?Origen=<%=origen%>&DocumentoId=<%=documentoId%>&Hoja=<%=i%>" <% if (hoja.equals(i+"")) out.print(" selected" );%>>Page <%=i%></option>
 <%		} %>
        </select>
        &nbsp;
        <b>Source:</b>
        <select name="Origen" size="1" onChange="if (this.options[this.selectedIndex].value){window.open(this.options[this.selectedIndex].value,'_self')}">
	    	<option value="addimg?Origen=O&DocumentoId=<%=documentoId%>&Prev=1&Hoja=<%=hoja%>" <% if (origen.equals("O")) out.print(" selected" );%>>Original</option>
    	    <option value="addimg?Origen=C&DocumentoId=<%=documentoId%>&Prev=1&Hoja=<%=hoja%>" <% if (origen.equals("C")) out.print(" selected" );%>>Copy</option>
        </select>
        </form>
        <form name = "form" enctype="multipart/form-data" action="subirImagen?Origen=<%=origen%>&DocumentoId=<%=documentoId%>&Hoja=<%=hoja%>" method="post">
         &nbsp;
        <b>File:</b>       
        <input type="file" class="text" name="imagen" id="imagen">
        <input type="submit" class="btn btn-primary" name="Submit2" value="Upload" onclick="return CT(this.form.imagen.value)">
        </form>
	</div>	
	<%=mensaje%>
	<br>
<% 	if(existeImagen){ %>
	<span onclick="Eliminar('<%=matricula%>','<%=documentoId%>','<%=hoja%>');" class="btn btn-danger"> Delete</span><br>
	<img name="imagen" id="imagen" src="../../fotoArchivo?matricula=<%=matricula%>&documento=<%=documentoId%>&hoja=<%=hoja%>" width="777" nosave>
<%	}else{
		out.print("<span>Image not captured</span>");
	}
%>	 
</div>
<script type="text/javascript">
	function CT(cadena){
		if (cadena ==""){
        	alert ("Select an image!");
         	return false;
	  	}
	}
	
	function Eliminar(matricula,documento,hoja){
		if (confirm("This digitized document will be deleted. Do you want to continue?")){
			location.href= "borrarHoja?matricula="+matricula+"&documento="+documento+"&hoja="+hoja;
		}
	}
</script>