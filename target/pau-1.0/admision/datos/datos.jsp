<%@ page import= "java.util.List"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type = "text/javascript">	
	function Buscar(){
		if(document.frmDatos.Nombre.value != ""){
			document.frmDatos.Accion.value="1";			
			document.frmDatos.submit();
		}else{
			alert('Enter the student´s name');				
		}		
	}
			
	function Select( codigoAlumno ){
		document.location.href="datos?Accion=3&CodigoAlumno="+codigoAlumno;
	}		
</script>

<style>
	.espacio{
		padding: 10px;
	}

</style>

<%
	String codigoAlumno		= (String)session.getAttribute("codigoAlumno");

	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	 
	String nombre			= request.getParameter("Nombre")==null?"":request.getParameter("Nombre");
	String paterno			= request.getParameter("Paterno")==null?"":request.getParameter("Paterno");
	String materno			= request.getParameter("Materno")==null?"":request.getParameter("Materno");
	String mensaje 			= (String)request.getAttribute("mensaje");
	//String codigoAlumno		= request.getParameter("CodigoAlumno")==null?"":request.getParameter("CodigoAlumno");
	
	List<AlumPersonal> lisAlumnos		= (List<AlumPersonal>) request.getAttribute("lisAlumnos");
%>
<div class="container-fluid mb-3">
	<h2>Create New Student <small class="text-muted fs-5"> ( Saved to the session: <%=codigoAlumno%> ) </small></h2>
	<div class="alert alert-info">
		<a href="alumno" title="Return"><i class="fas fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="alumno" title="Personal Data"><i class="fas fa-user fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_u" title="Provenance Data"><i class="fas fa-globe fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_a" title="Academic Data"><i class="fas fa-book fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<%=mensaje.equals("-")?"":mensaje%>
	</div>
	<div class="mb-3">
		<span><b>Note:</b> Enter the student's name to check if he/she has already been assigned an ID number. Make sure to enter the student's full name, last names may vary.&nbsp;&nbsp; <b>*</b> <i>Required Data</i></span>
	</div>
	<form name="frmDatos" action="datos" method="post">
	<input type="hidden" name="Accion">	
	<table class="table table-sm table-bordered" style="width:100%">	
	<tr class="table-dark">		
		<th class="text-center" style="padding:10px 15px;">* Name</th>
		<th class="text-center" style="padding:10px 15px;">* Surname</th>
		<th class="text-center" style="padding:10px 15px;">Maiden Name</th>		
	</tr>
	<tr>
		<td align="center"><input type="text" class="form-control" size="20" name="Nombre" value="<%=nombre%>"></td>
		<td align="center"><input type="text" class="form-control" size="20" name="Paterno" value="<%=paterno%>"></td>
		<td align="center"><input type="text" class="form-control" size="20" name="Materno" value="<%=materno%>"></td>
	</tr>	
	<tr>
		<td align="center" colspan="3" style="text-align:center;">
			<div class="d-flex justify-content-center">
				<a href="javascript:Buscar();" class="btn btn-info"><i class="fas fa-search"></i> Find Similar</a>				
<%	if(accion.equals("1")){ %>
				&nbsp;&nbsp;				
				<a href="datos?Nombre=<%=nombre%>&ApellidoPaterno=<%=paterno%>&ApellidoMaterno=<%=materno%>&Tipo=<%="UM"%>&Accion=4" class="btn btn-primary"><i class="fas fa-save"></i> Student</a>
				&nbsp;&nbsp;
				<div class="input-append" style="float:right;">
					<button onclick="nuevoCodigo();" class="btn btn-success" type="button"><i class="fas fa-save"></i> Use ID</button>
					&nbsp;&nbsp;
                	<input onkeypress="validar(event)" class="codigo" id="codigo" style="border-radius: 10px; padding: 2px;" size="6" maxlength="7" type="text">
                	&nbsp;&nbsp;
                	<a href="javascript:Siguiente();" class="btn btn-success"><i class="fas fa-sync-alt"></i></a>                	
                </div>
<%	} %>                
<%-- 			<a href="datos?Nombre=<%=nombre%>&ApellidoPaterno=<%=paterno%>&ApellidoMaterno=<%=materno%>&Tipo=<%="Tabasco"%>&Accion=4" class="btn btn-primary"><b>Nuevo Alumno Tabasco</b></a> --%>             	
           	</div>	
		</td>
	</tr>
	</table>
	</form>
	<script type="text/javascript">
		document.frmDatos.Nombre.focus();
	</script>
<%	if(accion.equals("1")){ %>
	<form action="datos" name="frmLista" method="post" target="_self">
	<table class="table table-sm table-bordered" style="width:100%">
	<tr>
		<td colspan="4" align="center" style="padding:10px 15px;">
			<h5><strong><font>Registered students with similar name</font></strong></h5>
		</td>
	</tr>
	<tr>
		<th class="text-center">#</th>
		<th width="10%" class="text-center">Check</th>		
		<th width="10%" class="text-center">ID</th>
		<th width="80%">Name</th>		
	</tr>
<%	
		int row = 0;
		for(AlumPersonal alumno : lisAlumnos ){
			row++;
%>
		<tr>
			<td style="padding: 12px 17px;"><%=row%></td>
			<td class="text-center" style="padding: 12px 17px;">
				<a href="javascript:Select('<%=alumno.getCodigoPersonal()%>');" title="Select student"><i class="fas fa-search"></i></a>
			</td>
			<td align="center" style="padding: 12px 17px;"><%=alumno.getCodigoPersonal()%>&nbsp;</td>
			<td style="padding: 12px 17px;">
				<%=alumno.getNombre()%>&nbsp;<%=alumno.getApellidoPaterno()%>&nbsp;<%=alumno.getApellidoMaterno()%>
			</td>
		</tr>
<%		}%>
	</table>
	</form>
<%	} %>
<script>
	function validar(e){
		tecla = (document.all) ? e.keyCode : e.which;
		  if (tecla==13) {
			  e.preventDefault();
			  nuevoCodigo();
		  }
	}

	function nuevoCodigo(){
		codigo = jQuery('.codigo');
		if(codigo.val().length===7){
			if(jQuery.isNumeric(codigo.val())){
				jQuery.get('existeMatricula?CodigoPersonal='+codigo.val(), function(r){
					if(jQuery.trim(r)=='existe'){
						alert('The ID already exists');
					}else{
						document.location.href="datos?Codigo="+codigo.val()+"&Nombre="+jQuery('input[name="Nombre"]').val()+"&ApellidoPaterno="+jQuery('input[name="Paterno"]').val()+"&ApellidoMaterno="+jQuery('input[name="Materno"]').val()+"&Accion=5";
					}
				});
			}else{
				alert('The ID must be numeric');
				codigo.focus();
			}
		}else{
			alert('The ID must be a 7 digit number');
			codigo.focus();
		}
	}
	
	function Siguiente(){		
		jQuery.get('siguienteMatricula',function(r){			
			jQuery("#codigo").val(r);
		});	
	}
</script>
</div> 