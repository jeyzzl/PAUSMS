<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->

<%	
%>

<script>
	 function valida()
	 { 
		var texto1 = document.form1.f_descripcion.value.toUpperCase();
		var texto2 = document.form1.f_imagen.value.toUpperCase();		
		var texto3 = document.form1.f_documento.value;		
		if((texto1=="")||(texto3=="")){		  
		  alert('Complete formulario');
		  return true;
		}else{
		  document.form1.submit();
		}	
	 }
	
</script>
<div class="container-fluid">
	<h2>Documents Catalog</h2>
	<form name="form1" method="post" action="add_documento">
	<div class="alert alert-info">
		<a href="documentos" class="btn btn-primary"><font size="3"><spring:message code="aca.Regresar"/></font></a>
	</div>
  <table style="width:46%"  class="table">
    <tr> 
      <td width="21%"><b>Document</b></td>
      <td width="79%"> 
        <input type="text" class="text" name="f_documento" value="" maxlength="2" size="3">
        <input type="checkbox" name="f_chek" value="SI">
        <font size="1" face="Arial, Helvetica, sans-serif">Force</font> </td>
    </tr>
    <tr> 
      <td width="21%"><b>Description </b></td>
      <td width="79%"> 
        <input type="text" class="text" name="f_descripcion" value="" maxlength="60" size="60">
      </td>
    </tr>
    <tr> 
      <td width="21%"><b>Image </b></td>
      <td width="79%"> 
        <input type="text" class="text" name="f_imagen" value="">
      </td>
    </tr>
    <tr>
      <td width="21%">&nbsp;</td>
      <td width="79%"> 
        <input class="btn btn-primary"type="Button" name="Guardar" value="Save" onclick="valida()">		
      </td>
    </tr>
  </table>
</form>
</div>
