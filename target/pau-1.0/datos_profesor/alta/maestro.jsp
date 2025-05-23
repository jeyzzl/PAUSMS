<%@page import="aca.emp.spring.EmpMaestro"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>


<link rel="stylesheet" href="../../js/datepicker/datepicker.css"/>
<script type="text/javascript">
	
	function nuevo()	{		
		document.frmAlta.Nombre.value 		   = " ";
		document.frmAlta.FechaNac.value 	   = " ";
		document.frmAlta.APaterno.value 	   = " ";
		document.frmAlta.AMaterno.value  	   = " ";
		document.frmAlta.Telefono.value 	   = " ";
		document.frmAlta.Email.value 		   = " ";
		document.frmAlta.CodigoPersonal.value  = " ";
		document.frmAlta.Accion.value="1";
		document.frmAlta.submit();		
	}
	
	function grabar(){		
		if(document.frmAlta.Nombre.value!="" && document.frmAlta.FechaNac!="" && document.frmAlta.APaterno != ""
		 && document.frmAlta.AMaterno!="" && document.frmAlta.Telefono!="" && document.frmAlta.Email!=""  
		 && document.frmAlta.CodigoPersonal!="" && document.frmAlta.CodigoPersonal.value.length === 7){			
			document.frmAlta.Accion.value="2";
			document.frmAlta.submit();			
		}else{
			if (document.frmAlta.CodigoPersonal.value.length === 7){
				alert("Fill out the entire form!");
			}else{
				alert("The code must be 7 characters!");
			}	
		}
	}
	
	function borrar( ){
		if(document.frmAlta.CodigoPersonal.value!=""){
			if(confirm("Do you want to delete this record?")==true){
	  			document.frmAlta.Accion.value="3";
				document.frmAlta.submit();
			}			
		}else{
			alert("Type in the code");
			document.frmAlta.CodigoPersonal.focus(); 
	  	}
	}	
</script>
<% 
	EmpMaestro empMaestro 	= (EmpMaestro) request.getAttribute("empMaestro");
	String resultado		= (String)request.getAttribute("resultado");
%>
<body>
<div class="container-fluid">
	<h2>Employee Registration</h2>
	<div class="alert alert-info">
		<a href="listado" class="btn btn-primary" title="Return"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;<%=resultado.equals("-")?"":resultado%>
	</div>
	<form name="frmAlta" method="post" action="maestro">  
  	<input name="Accion" type="hidden">
  	<table style="width:73%"  class="table table-sm">  	    
  	    <tr> 
           <td width="10%"><strong><spring:message code="aca.Clave"/></strong></td>
          <td width="43%">         
            <input name="CodigoPersonal" type="text" class="text" id="CodigoPersonal" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=empMaestro.getCodigoPersonal()%>" size="30" maxlength="7">        
          </td>          
          <td width="20%"><strong>Birth date</strong></td>
          <td width="27%"> 	
            <input name="FechaNac" type="text" class="text" id="FechaNac" value="<%=empMaestro.getFNacimiento()%>" size="12" maxlength="10">
<!--             <br><img class="button" alt="calendario" id="fotoFecha" src="../../imagenes/calendario.gif" onClick="javascript:showCalendar(this, document.getElementById('FechaNac'), 'dd/mm/yyyy',null,1,-1,-1);"> (DD/MM/YYYY)          -->
          </td>
        </tr>
        <tr> 
          <td width="10%"><strong><spring:message code="aca.Nombre"/>:</strong></td>
          <td width="43%">         
            <input name="Nombre" type="text" class="text" id="Nombre" value="<%=empMaestro.getNombre()%>" size="30" maxlength="30">        
          </td>          
          <td width="20%"><strong>Gender</strong></td>
          <td width="27%"> 
            <select name="Genero" id="Genero">
			  <option value='M' <%if(empMaestro.getGenero().equals("M")){out.print("selected");}%>>Male</option>
			  <option value='F' <%if(empMaestro.getGenero().equals("F")){out.print("selected");}%>>Female</option>
  		    </select>
          </td>
        </tr>
        <tr> 
          <td width="10%"><strong>Surname</strong></td>
          <td width="43%">         
            <input name="APaterno" type="text" class="text" id="APaterno" value="<%=empMaestro.getApellidoPaterno()%>" size="30" maxlength="30">        
          </td>          
          <td width="20%"><strong>Phone</strong></td>
          <td width="27%"> 
            <input name="Telefono" type="text" class="text" id="Telefono" value="<%=empMaestro.getTelefono()%>" size="10" maxlength="30">
          </td>
        </tr>
        <tr> 
          <td width="10%"><strong>Maiden Name</strong></td>
          <td width="43%">         
            <input name="AMaterno" type="text" class="text" id="AMaterno" value="<%=empMaestro.getApellidoMaterno()==null ? " " : empMaestro.getApellidoMaterno()%>" size="30" maxlength="30" tabindex="4">        
          </td>          
          <td width="20%"><strong><spring:message code="aca.Email"/></strong></td>
          <td width="27%"> 
            <input name="Email" type="text" class="text" id="Email" value="<%=empMaestro.getEmail()%>" size="10" maxlength="40">
          </td>
        </tr>
        <tr> 
           <td width="10%"><strong>Civil Status</strong></td>
          <td width="43%">         
            <select name="EdoCivil" id="EdoCivil">
			  <option value='S' <%if(empMaestro.getEstadoCivil().equals("S")){out.print("selected");}%>>Single</option>
			  <option value='C' <%if(empMaestro.getEstadoCivil().equals("C")){out.print("selected");}%>>Married</option>
			  <option value='V' <%if(empMaestro.getEstadoCivil().equals("V")){out.print("selected");}%>>Widow</option>
			  <option value='D' <%if(empMaestro.getEstadoCivil().equals("D")){out.print("selected");}%>>Divorced</option>
  		    </select>        
          </td>          
          <td width="20%"><strong>Status</strong></td>
          <td width="27%"> 
            <select name="Estado" id="Estado">
			  <option value='A' <%if(empMaestro.getEstado().equals("A")){out.print("selected");}%>><spring:message code='aca.Activo'/></option>
			  <option value='I' <%if(empMaestro.getEstado().equals("I")){out.print("selected");}%>><spring:message code='aca.Inactivo'/></option>
  		    </select>
          </td>
        </tr>           
	</table>
	</form>
	<div class="alert alert-info">          
    	<a href="javascript:nuevo()" class="btn btn-primary">New</a>&nbsp;&nbsp;        
        <a href="javascript:grabar()" class="btn btn-primary"><spring:message code="aca.Grabar"/></a>&nbsp;&nbsp;
        <a href="javascript:borrar()" class="btn btn-primary">Delete</a>
	</div>		
</div>	
</body>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>	
$('#FechaNac').datepicker({
    format: 'dd/mm/yyyy',
  });	
</script>