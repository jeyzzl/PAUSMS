<%@ page import="java.util.List"%>
<%@ page import="aca.catalogo.spring.CatNivel"%>
<%@ page import="aca.catalogo.spring.CatModalidad"%>
<%@ page import="aca.alumno.spring.AlumVacacion"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<%
	// Declaracion de variables	
	String nivelId				= request.getParameter("NivelId")==null?"0":request.getParameter("NivelId");
	String modalidadId			= request.getParameter("ModalidadId")==null?"0":request.getParameter("ModalidadId");
	AlumVacacion vacacion		= (AlumVacacion)request.getAttribute("vacacion");
	String mensaje 				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	List<CatNivel> listaNivel							= (List<CatNivel>)request.getAttribute("listaNivel");
	List<CatModalidad> listaModalidad					= (List<CatModalidad>)request.getAttribute("listaModalidad");
%>
<!-- inicio de estructura -->
<html>
<div class="container-fluid">
	<h2>Editar Periodo</h2>
	<div class="alert alert-info">
		<a href="vacaciones?NivelId=<%=nivelId%>&ModalidadId=<%=modalidadId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i> <spring:message code="aca.Regresar"/></a>
	</div>
	<table style="width:50%">
	<tr>
		<td>
		<font>
		* Examen = Fecha del examen final<font color="blue"></font>
		* Inicio = Fecha en que inicia vacaciones<font color="blue"></font>
		* Final = Fecha en que termina vacaciones<font color="blue"></</font>
		</font>
		</td>
	</tr>
	</table>	
	<form name="frmVacaciones" action="grabar" method="post">
	<table style="width:50%" class="table table-fullcondensed">
	<tr>
  		<th align="center" colspan="4"><font size="3">Vacaciones</font></th>
  	</tr>
  	<tr>
  		<td width="14%" height="26"><strong>Nivel:</strong></td>
        <td>
           	<select name="NivelId" id="NivelId">
<%
			for(CatNivel nivel : listaNivel){
%>            	
           		<option value="<%=nivel.getNivelId()%>" <%if(nivel.getNivelId().equals(nivelId)) out.print("selected");%>><%=nivel.getNombreNivel()%></option>
<%}%>
           	</select> &nbsp; &nbsp;
           	<select name="ModalidadId" id="ModalidadId">
<%
			for(CatModalidad modalidad : listaModalidad){
%>            	
           		<option value="<%=modalidad.getModalidadId()%>" <%if(modalidad.getModalidadId().equals(modalidadId)) out.print("selected");%>><%=modalidad.getNombreModalidad()%></option>
<%}%>
           	</select>
		</td>            
	<tr>	  		
		<td height="20"><strong>Examen:</strong></td>
		<td>
			<input type="text" class="text" data-date-format="dd/mm/yyyy" name="FExamen" id="dateTest" value="<%=vacacion.getfExamen()%>"/>
			(DD/MM/AAAA)
		</td>
	</tr>
	<tr>
		<td height="28"><strong>Inicio:</strong></td>
		<td><input type="text" class="text" data-date-format="dd/mm/yyyy" name="FInicio" id="dateStart" value="<%=vacacion.getfInicio()%>"/>
			(DD/MM/AAAA)
		</td>
	</tr>
	<tr>
		<td height="28"><strong>Final:</strong></td>
		<td>
			<input type="text" class="text" data-date-format="dd/mm/yyyy" name="FFinal" id="dateEnd" value="<%=vacacion.getfFinal()%>"/>
			(DD/MM/AAAA)
		</td>
	</tr>
	<tr> 
		<td colspan="2" align="center"><%=mensaje%></td>
    </tr>		  	
    <tr> 
		<td colspan="4" align="center" style="text-align:center">       	
            <input type="button" value="Grabar" id="grabar" onclick="javascript:Grabar()" style="cursor:pointer" class="btn btn-primary"/>
        </td>
    </tr>
	</table>
	</form>
</div>
</body>
<script type="text/javascript">
	Calendar.setup({
	    inputField     :    "dateTest",     // id del campo de texto
	     ifFormat     :     "%d/%m/%Y",     // formato de la fecha que se escriba en el campo de texto
	     button     :    "lanzador1"     // el id del botón que lanzará el calendario
	});
	
	 Calendar.setup({
	    inputField     :    "dateStart",     // id del campo de texto
	     ifFormat     :     "%d/%m/%Y",     // formato de la fecha que se escriba en el campo de texto
	     button     :    "lanzador2"     // el id del botón que lanzará el calendario
	});
	
	Calendar.setup({
	    inputField     :    "dateEnd",     // id del campo de texto
	     ifFormat     :     "%d/%m/%Y",     // formato de la fecha que se escriba en el campo de texto
	     button     :    "lanzador3"     // el id del botón que lanzará el calendario
	});
</script>
<script type="text/javascript">
	function Grabar(){		
		if(document.frmVacaciones.NivelId.value	!= "" ){		
			document.frmVacaciones.submit();
		}else{
			alert('Ingresa todos los datos');
		}
	}
	
	jQuery('#dateTest').datepicker();
	jQuery('#dateStart').datepicker();
	jQuery('#dateEnd').datepicker();
</script>
</html>