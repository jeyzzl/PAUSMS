<%@ page import= "java.util.List"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.catalogo.spring.CatPeriodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<head>
<script type="text/javascript">
	
	function Grabar(){
		if(document.frmcarga.CargaId.value!="" && document.frmcarga.NombreCarga!=""){
			if(VerificarFechas()){				
				document.frmcarga.submit();
			}
		}else{
			alert("<spring:message code='aca.JSCompletar'/>");
		}
	}
	
	function VerificarFechas(){
		var fInicio = document.frmcarga.fInicio.value;
		var fFinal 	= document.frmcarga.fFinal.value;
		var fExtra 	= document.frmcarga.fExtra.value;
		if(fInicio!="" && fFinal!="" && fExtra!=""){
			var arrIni = fInicio.split('/');
			var fIni = new Date(arrIni[1]+"/"+arrIni[0]+"/"+arrIni[2]);
			
			var arrFin = fFinal.split('/');
			var fFin = new Date(arrFin[1]+"/"+arrFin[0]+"/"+arrFin[2]);
			
			var arrExt = fExtra.split('/');
			var fExt = new Date(arrExt[1]+"/"+arrExt[0]+"/"+arrExt[2]);
			
			if(fIni=='Invalid Date' || fFin=='Invalid Date' || fExt=='Invalid Date'){
				alert("<spring:message code="aca.JSVerificarFechas"/>");
				return false;
			}
			else{
				if((fIni<fFin ) || (fInicio==fFinal)){
					return true;
				}
				else{
					alert("<spring:message code="cargasGrupo.cargas.JSMensaje"/>");
					return false;
				}				
			}
		}else{
			alert("<spring:message code='aca.JSCompletar'/>");
			return false;
		}
		return true;
	}	
</script>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

</head>
<%
	// Declaracion de variables
	Carga carga 		= (Carga)request.getAttribute("carga");
	String mensaje 		= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	if (!mensaje.equals("-")){ 
		if (mensaje.equals("1")) mensaje = "Saved"; else mensaje = "Error Saving";
	}	
	List<CatPeriodo> lisPeriodos 	= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
%>
<body>
<div class="container-fluid">	
	<h2>Academic Loads</h2>
	<form action="grabarCarga" method="post" name="frmcarga" target="_self">	
		<div class="alert alert-info">
			<a class="btn btn-primary" href="carga"><i class="fas fa-arrow-left"></i></a>&nbsp;
			<%=mensaje.equals("-")?" ":" "+mensaje%>	
		</div>	
		<table style="width:50%"  class="table table-condensed">  	
		<tr> 
		    <td><strong><spring:message code="aca.Clave"/></strong></td>
		    <td>
				<input name="CargaId" type="text" class="text form-control w-25" id="CargaId" size="7" maxlength="6" value="<%=carga.getCargaId()%>">
				<label for="CargaId"><small class="text-muted">( Must be 6 characters )</small></label>
			</td>		
		</tr>
		<tr> 
		    <td><strong><spring:message code="aca.Nombre"/></strong></td>
		    <td><input name="NombreCarga" type="text" class="text form-control w-75" id="NombreCarga"  maxlength="30" value="<%=carga.getNombreCarga()%>"></td>
		</tr>
		<tr> 
			<td><strong>Date Created</strong></td>
		    <td><input name="FCreada" type="text" class="text form-control w-25" id="FCreada" maxlength="10" value="<%=carga.getFCreada()%>">
		    </td>
		</tr>
		<tr> 
			<td><strong><spring:message code="aca.Ciclo"/></strong></td>
		    <td>
		    <select name="Periodo" class="form-select w-25" style="width:250px;">
	<%	for(CatPeriodo periodo : lisPeriodos){%>		
		    	<option value='<%=periodo.getPeriodoId()%>' <%=carga.getPeriodo().equals(periodo.getPeriodoId())?"selected":""%>><%=periodo.getPeriodoId()%> - <%=periodo.getNombre()%></option>	   
	<%	}%>	        	    
		    </select>
		    </td>
		</tr>
		<tr> 
			<td><strong>Semester</strong></td>
		    <td><input name="Ciclo" type="text" class="text form-control w-25" id="Ciclo" size="2" maxlength="2" value="<%=carga.getCiclo()%>"></td>
		</tr>
		<tr> 
			<td><strong><spring:message code="aca.FechaInicio"/></strong></td>
		    <td>
		    	<input type="text" class="text form-control w-25" id="fInicio" name="fInicio" value="<%=carga.getFInicio() %>" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" size="10" maxlength="10" />
		    	<label for="fInicio"><small class="text-muted">(DD/MM/YYYY)</small></label>
		    </td>
		</tr>	        
		<tr> 
		     <td><strong><spring:message code="aca.FechaFinal"/></strong></td>
		     <td>
		     	<input type="text" class="text form-control w-25" id="fFinal" name="fFinal" value="<%=carga.getFFinal() %>" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" size="10"  maxlength="10" />
		     	<label for="fFinal"><small class="text-muted">(DD/MM/YYYY)</small></label>
		     </td>
		</tr>	        
		<tr> 
		     <td><strong>Remedial <spring:message code="aca.Fecha"/></strong></td>
		     <td>
		     	<input type="text" class="text form-control w-25" id="fExtra" name="fExtra" value="<%=carga.getFExtra()%>" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" maxlength="10" />
		     	<label for="fExtra"><small class="text-muted">(DD/MM/YYYY)</small></label>
		     </td>
		</tr>
		<tr> 
		     <td><strong>Subjects</strong></td>
		     <td><input name="NumCursos" type="text" class="text form-control w-25" id="NumCursos" size="4" maxlength="4" value="<%=carga.getNumCursos()%>" readonly="YES"></td>
			 
		</tr>
		<tr> 
			<td><strong><spring:message code="aca.Status"/></strong></td>
		    <td>
		    	<select name="Estado" class="form-select w-25" style="width:150px;">
			<% if(carga.getEstado().equals("1")){%>
		    		<option value='1' selected><spring:message code="aca.Activo"/></option>
		        	<option value='0' ><spring:message code="aca.Inactivo"/></option>
		          <% }else{ %>
		            <option value='1'><spring:message code="aca.Activo"/></option>
		            <option value='0' selected><spring:message code="aca.Inactivo"/></option>
		          <% } %>
		    	</select>
			</td>
		</tr>
		<tr> 
			<td><strong><spring:message code="aca.Tipo"/></strong></td>
		    <td>
		    	<select name="TipoCarga" class="form-select w-25" style="width:150px;">
			<% if(carga.getTipoCarga().equals("O")){%>
		        	<option value='O' selected><spring:message code="aca.Oficial"/></option>
		            <option value='V' ><spring:message code="aca.Verano"/></option>
		            <option value='I' ><spring:message code="aca.Invierno"/></option>
		          <% }else if(carga.getTipoCarga().equals("V")){ %>
		            <option value='O' ><spring:message code="aca.Oficial"/></option>
		            <option value='V' selected><spring:message code="aca.Verano"/></option>
		            <option value='I' ><spring:message code="aca.Invierno"/></option>
		          <% }else{ %>
		          	<option value='O' ><spring:message code="aca.Oficial"/></option>
		            <option value='V' ><spring:message code="aca.Verano"/></option>
		            <option value='I' selected><spring:message code="aca.Invierno"/></option>
		          <% }%>
		        </select>
			</td>
		</tr>
		<tr> 
			<td><strong>Weeks</strong></td>
		    <td><input name="Semanas" type="text" class="text form-control w-25" id="Semanas" size="2" maxlength="2" value="<%=carga.getSemanas()%>"></td>
		</tr>
		<tr> 
			<td><strong>Evaluated</strong></td>
		    <td>
		    	<select name="Evalua" class="form-select w-25" style="width:150px;">
		        	<option value='S' <%=carga.getEvalua().equals("S")?"Selected":""%>>YES</option>
		        	<option value='N' <%=carga.getEvalua().equals("N")?"Selected":""%>>NO</option>            
		        </select>
			</td>
		</tr>
		<tr> 
			<td><strong>Student Access</strong></td>
		    <td>
		    	<select name="Bloqueo" class="form-select w-25" style="width:150px;">
		        	<option value='0' <%=carga.getBloqueo().equals("0")?"Selected":""%>>OPEN</option>
		        	<option value='1' <%=carga.getBloqueo().equals("1")?"Selected":""%>>RESTRICTED</option>            
		        </select>
			</td>
		</tr>
		</table>
		<div class="alert alert-info"> 		
			<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Guardar"/></a>
		</div>
	</form>
</div>
</body>
<script>
	jQuery('#fInicio').datepicker();
	jQuery('#fFinal').datepicker();
	jQuery('#fExtra').datepicker();
</script>