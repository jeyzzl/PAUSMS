<%@ page import= "java.util.List"%>
<%@page import= "aca.alumno.spring.AlumEstudio"%>
<%@page import= "aca.alumno.spring.AlumBanco"%>
<%@page import= "aca.catalogo.spring.CatBanco"%>
<%@page import="aca.log.spring.LogAlumno"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%-- <%	idJsp = "070_e"; %> --%>
<%@ include file="../../idioma.jsp"%>

<script type = "text/javascript">	
	function Grabar(){
		if(document.frmbanco.codigoPersonal.value!=""){			
			document.frmbanco.submit();
		}else{
			alert(" Fill out all the required fields! (*) ");
		}
	}
		
	function Borrar( ){		
		if(confirm("You are sure to delete the record?")==true){
			document.location.href="borrarBanco";		
		}		
	}
</script>

<%
	String usuario 						= (String)session.getAttribute("codigoPersonal");	
	String codigoAlumno					= (String)session.getAttribute("codigoAlumno"); 
	
	AlumBanco alumBanco					= (AlumBanco)request.getAttribute("alumBanco");
	CatBanco catBanco						= (CatBanco)request.getAttribute("catBanco");

	List<CatBanco> lisBancos 			= (List<CatBanco>)request.getAttribute("lisBancos");
	
	LogAlumno logAlumno					= (LogAlumno)request.getAttribute("logAlumno");
	boolean existeBanco					= (boolean)request.getAttribute("existeBanco");
	String maxUpdate					= (String)request.getAttribute("maxUpdate");
	String cotejado						= (String)request.getAttribute("cotejado");
	String nombreAlumno					= (String)request.getAttribute("nombreAlumno");
	String usuarioActualizo				= (String)request.getAttribute("usuarioActualizo");
	String mensaje						= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>

<div class="container-fluid">
	<h2>Bank Data <small class="text-muted fs-5">( Student: <%=codigoAlumno%> - <%=nombreAlumno%> )</small></h2>
	<div class="alert alert-info">
		<a href="alumno" title="Return"><i class="fas fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="alumno" title="Personal Data"><i class="fas fa-user fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_u" title="Provenance Data"><i class="fas fa-globe fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_a" title="Academic Data"><i class="fas fa-book fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_e" title="Background Data"><i class="fas fa-file-signature fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_o" title="Bank Data"><i class="fas fa-credit-card fa-lg"></i></a>
	</div>
<%	if (!mensaje.equals("-")){ %>	
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>
<form action="grabarBanco" method="post" name="frmbanco" target="_self">
		<input name="codigoPersonal" type="hidden" value="<%=codigoAlumno %>">
	<table class="table table-sm">
		<tr> 
	       	<td><strong>Bank*</strong></td>
	        <td>
			<%-- <input name="banco" type="text" class="form-control text" id="banco" value="<%=alumBanco.getBanco() %>"> --%>
			<select id="banco" name="banco" class="form-select " >
<%			for(CatBanco banco : lisBancos){%>
				<option value="<%=banco.getBancoId()%>" <%=banco.getBancoId().equals(catBanco.getBancoId())?"selected":""%>><%=banco.getNombre()%></option>
<%			}%>	
			</select>
			</td>
	        <td><strong>Branch *</strong></td>
	        <td><input name="bancoRama" type="text" class=" form-control text" id="bancoRama" value="<%=alumBanco.getBancoRama() %>"></td>
		</tr>
		<tr> 
	    	<td><strong><font color="#000099">Account Name *</font></strong></td>
	        <td><input name="cuentaNombre" type="text" class="form-control text" id="cuentaNombre" value="<%=alumBanco.getCuentaNombre() %>"></td>
	        <td><strong>Account Number *</strong></td>
	        <td><input name="cuentaNumero" type="text" class="form-control text" id="cuentaNumero" value="<%=alumBanco.getCuentaNumero() %>"></td>					
		</tr>
		<tr> 
	       	<td><strong>Account Type</strong></td>
	        <td><input name="cuentaTipo" type="text" class="form-control text" id="cuentaTipo" value="<%=alumBanco.getCuentaTipo() %>"></td>
	        <td><strong>BSB Number</strong></td>
	        <td><input name="numeroBbs" type="text" class=" form-control text" id="numeroBbs" value="<%=alumBanco.getNumeroBBS() %>"></td>
		</tr>
		<tr> 
	       	<td><strong>Swift Code</strong></td>
	        <td><input name="codigoSwift" type="text" class="form-control text" id="codigoSwift" value="<%=alumBanco.getCodigoSwift() %>"></td>
		</tr>
	</table>
	<div class="alert alert-info">
		<a href="javascript:Grabar()" class="btn btn-primary">Save</a>&nbsp;
<%	if(existeBanco){ %>
		<a href="javascript:Borrar()" class="btn btn-primary">Delete</a>&nbsp;&nbsp;
<%	if (!maxUpdate.equals("0")){ %>
		<span style="font-size:10px">Updated: <%=logAlumno.getFecha()%> by <%=usuarioActualizo%></span>
<%		}else{
  			out.print("<span style='font-size:10px'> Not updated </span>");
  		}
  	}else{
  		out.print("Bank details not registered");
  	}
%>	
	</div>
</form>
</div>