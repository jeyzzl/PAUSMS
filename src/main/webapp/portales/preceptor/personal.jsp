<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.internado.spring.IntDormitorio"%>
<%@ page import= "aca.internado.spring.IntAcceso"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp"%>
<%	
	IntDormitorio intDormitorio	= (IntDormitorio)request.getAttribute("intDormitorio");
	boolean esAdmin				= (boolean)request.getAttribute("esAdmin");
	boolean esPreceptor			= (boolean)request.getAttribute("esPreceptor");
	String accion				= (String)request.getAttribute("accion");
	String cP					= (String)request.getAttribute("cP");
	String nombreMaestro		= (String)request.getAttribute("nombreMaestro");	
	String dormitorioId			= (String)request.getAttribute("dormitorioId");	
	String mensaje				= (String)request.getAttribute("mensaje");
	
	List<IntAcceso> vPersonal				= (List<IntAcceso>)request.getAttribute("vPersonal");	
	HashMap<String,String> mapaUsuarios		= (HashMap<String,String>)request.getAttribute("mapaUsuarios");	
%>
<%@ include file="portal.jsp" %>
<script>
	function Agregar(){
		document.forma.accion.value = "agregar";
		document.forma.submit();
	}
	function eliminar(cp,r){
		var tipo = "residential dean";
		if (r =="M") tipo = "dorm staff";
		if (confirm("Are you sure you want to delete the "+tipo+" with ID "+cp+"?")){
			document.forma.cP.value = cp;
			document.forma.r.value = r;
			document.forma.accion.value = "eliminar";
			document.forma.submit();
		}
	}
	function modificar(cp,r){
		document.forma.cP.value = cp;
		document.forma.r.value = r;
		document.forma.accion.value = "modificar";
		document.forma.submit();
	}
	function update(cp,r){
		document.forma.cP.value = cp;
		document.forma.r.value = r;
		document.forma.accion.value = "update";
		document.forma.submit();
	}
	function guardar(){
		if (document.forma.codigo.value == "")
			alert("Input the Student/Employee ID");
		else{
			if (document.forma.rol.value == "M" && document.forma.pasillo.value == "-")
				alert("Input the hallway manager");
			else{
				document.forma.accion.value="guardar";
				document.forma.submit();
			}
		}
	}
	
	function buscar(){
		abrirVentana("bem",600,500,100,250,"no","yes","yes","no","no","buscar",false);
	}
	function abrirVentana(strName,iW,iH,TOP,LEFT,R,S,SC,T,TB,URL,modal){
		var sF="";
		if (navigator.appName=="Microsoft Internet Explorer" && modal){
			sF+=T?'unadorned:'+T+';':'';
			sF+=TB?'help:'+TB+';':'';
			sF+=S?'status:'+S+';':'';
			sF+=SC?'scroll:'+SC+';':'';
			sF+=R?'resizable:'+R+';':'';
			sF+=iW?'dialogWidth:'+iW+'px;':'';
			sF+=iH?'dialogHeight:'+(parseInt(iH)+(S?42:0))+'px;':'';
			sF+=TOP?'dialogTop:'+TOP+'px;':'';
			sF+=LEFT?'dialogLeft:'+LEFT+'px;':'';	
			return window.showModalDialog(URL,"",sF);
		}else{
			sF+=iW?'width='+iW+',':'';
			sF+=iH?'height='+iH+',':'';
			sF+=R?'resizable='+R+',':'';
			sF+=S?'status='+S+',':'';
			sF+=SC?'scrollbars='+SC+',':'';
			sF+=T?'titlebar='+T+',':'';
			sF+=TB?'toolbar='+TB+',':'';
			sF+=TB?'menubar='+TB+',':'';
			sF+=TOP?'top='+TOP+',':'';
			sF+=LEFT?'left='+LEFT+',':'';
			return window.open(URL,strName?strName:'',sF).focus()
		}
	}			
	
</script>
<body>
<div class="container-fluid">
	<h2>		
		<%=intDormitorio.getNombre().equals("-")?"":intDormitorio.getNombre()%> Staff <small class="text-muted fs-4">(Residential Dean: <%=nombreMaestro%>)</small>
	</h2>
	<div class="alert alert-info">
	<% if(esAdmin || esPreceptor){%>
		<a href='../../internado/dormitorios/dormitorios' class='btn btn-info'><i class='icon-white icon-arrow-left'></i>Menu</a>&nbsp;
	<% }%>
		<a href="javascript:Agregar()" class="btn btn-primary">Add Staff</a>
		
		<% 
			if (!mensaje.equals("-")) { out.print(mensaje); }
		%>
	</div>
	<form name = "forma" method = "post" action = "personal">
	<input type = "hidden" name = "accion">
	<input type = "hidden" name = "cP">
	<input type = "hidden" name = "r">
	<input type = "hidden" name = "dormitorioId" value="<%=dormitorioId%>">
	<table class="table">	
		<%-- <tr><td colspan='5'><hr></td></tr> --%>
<%
	if (accion.equals("agregar")){
%>		<tr>
		<td colspan = '5'>
			<table>
				<tr>
				<td>Role:</td>
					<td>
						<select  class="form-select"name ='rol' >
	<% if(esAdmin || esPreceptor){%>
							<option value='P'>Residential Dean</option>
	<% }%>
							<option value='A'>Assistant</option>
							<option value='M'>Hallway manager</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>Employee ID:</td>
			    <td><input type='text' name='codigo' class="form-control"  style="width:120px" size='6' maxlength='7'></td>
						<i class="fas fa-search" type='button' name='boton'   class="btn btn-primary" value='Search' onclick='buscar()'></i> 
					</td>
				</tr>			
				<tr>
					<td width='100'>Hallway:</td>
					<td>
						<select class="form-select" name ='pasillo'>
							<option value='-'>N/A</option>
							<option value='A'>A</option>
							<option value='B'>B</option>
							<option value='C'>C</option>
							<option value='D'>D</option>
							<option value='E'>E</option>
							<option value='F'>F</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type='button' onclick='guardar()' class="btn btn-primary" value='Save'>&nbsp;&nbsp;
						<input type='button' onclick='document.forma.submit()' class="btn btn-primary" value ='Cancel'>
					</td>					
				</tr>
			</table>
		<td>
		</tr>
		<%-- <tr><td colspan='5'><hr></td></tr> --%>
<%
	}
		String rol = "";
		String nombreRol = "";
		for (IntAcceso dormiAcceso : vPersonal){
	if (!dormiAcceso.getRol().equals(rol)){
		rol = dormiAcceso.getRol();
		if (rol.equals("P")) nombreRol = "Reisdential Dean";
		if (rol.equals("A")) nombreRol = "Assistant";
		if (rol.equals("M")) nombreRol = "Hallway manager";
		
%>		<tr>
			<td width='10%'><b><%=nombreRol%>:</b></td>
			<td width='20%'>&nbsp;</td>
			<td width='60%'>&nbsp;</td>
			<td width='10%'>&nbsp;</td>
		<tr>
<%			}
			String nombre = "";
			if(mapaUsuarios.containsKey(dormiAcceso.getCodigoPersonal())){
				nombre = mapaUsuarios.get(dormiAcceso.getCodigoPersonal());
			}
%>		<tr class="align-middle">			
			<td><a title='Delete' href="javascript:eliminar('<%=dormiAcceso.getCodigoPersonal()%>','<%=rol%>')" class="fas fa-trash-alt"></a></td>
			<td><%=dormiAcceso.getCodigoPersonal()%></td>
			<td><%=nombre%></td>
<%			if (accion.equals("modificar") && cP.equals(dormiAcceso.getCodigoPersonal())){
%>			<td>Hallway: 
				<select name ='pasillo' onchange="update('<%=dormiAcceso.getCodigoPersonal()%>','<%=rol%>')">
					<option value='A' <% if(dormiAcceso.getPasillo().equals("A")){out.print("selected");}%>>A</option>
					<option value='B' <% if(dormiAcceso.getPasillo().equals("B")){out.print("selected");}%>>B</option>
					<option value='C' <% if(dormiAcceso.getPasillo().equals("C")){out.print("selected");}%>>C</option>
					<option value='D' <% if(dormiAcceso.getPasillo().equals("D")){out.print("selected");}%>>D</option>
					<option value='E' <% if(dormiAcceso.getPasillo().equals("E")){out.print("selected");}%>>E</option>
					<option value='F' <% if(dormiAcceso.getPasillo().equals("F")){out.print("selected");}%>>F</option>
				</select>
			</td>
<%			}else{
%>			<td>
<%			if (rol.equals("M")){
%>
				<a class="btn btn-primary" href="javascript:modificar('<%=dormiAcceso.getCodigoPersonal()%>','<%=rol%>')">
					<i class="fas fa-pencil-alt"></i>&nbsp;Hallway <b><%=dormiAcceso.getPasillo()%></b>
<%			}		%>
				</a>
			</td>
<%			
			}
%>			
		</tr>
<%
		}
%>						
	</table>	  
	<%-- <table style="width:100%; margin:0 auto;"><tr><td background="../../imagenes/shadow.gif" height="4"></td></tr></table> --%>
	</form>
</div>	

<script>
	jQuery('.datos').addClass('active');
</script>