<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="Mensaje" scope="page" class="aca.financiero.FinMensaje"/>
<jsp:useBean id="MensajeU" scope="page" class="aca.financiero.FinMensajeUtil"/>

<head>
<script type="text/javascript">
	
	function Grabar(){
		if(document.frmreligion.Comentario.value!=""){			
			document.frmreligion.Accion.value="2";
			document.frmreligion.submit();			
		}else{
			alert("Complete el formulario ..! ");
		}
	}
	function maximaLongitud(texto,maxlong){
		var tecla, int_value, out_value;
		
		if (texto.value.length > maxlong){
			/*con estas 3 sentencias se consigue que el texto se reduzca
			al tamaño maximo permitido, sustituyendo lo que se haya
			introducido, por los primeros caracteres hasta dicho limite*/
			
			in_value = texto.value;
			out_value = in_value.substring(0,maxlong);
			texto.value = out_value;
			alert("La longitud máxima es de " + maxlong + " caractéres");
			return false;
		}
		return true;
	}
</script>
</head>
<%
	// Declaracion de variables	
	String Accion  			= request.getParameter("Accion")==null?"1":request.getParameter("Accion");
	String sResultado		= "";
	
	ArrayList mensaje 		= null;
	
	
	if(Accion.equals("1")){
		Mensaje.setId(MensajeU.maximoReg(conEnoc));
	}else{
		Mensaje.setId(request.getParameter("Id"));
	}
	
	
	// Operaciones a realizar en la pantalla	
	if(Accion.equals("2")){ // Grabar
			Mensaje.setId(request.getParameter("Id"));
			Mensaje.setComentario(request.getParameter("Comentario"));
			Mensaje.setTipo(request.getParameter("Tipo"));
			Mensaje.setEstado(request.getParameter("Estado"));		
	
			if (MensajeU.existeReg(conEnoc, request.getParameter("Id")) == false){
				if (MensajeU.insertReg(conEnoc, Mensaje)){
					sResultado = "Grabado: "+Mensaje.getId();					
				}else{
					sResultado = "No Grabó: "+Mensaje.getId();
				}
			}else{
				if (MensajeU.updateReg(conEnoc, Mensaje)){
					sResultado = "Modificado: "+Mensaje.getId();					
				}else{
					sResultado = "No Modificó: "+Mensaje.getId();
				}
			}
	}
	
	if(Accion.equals("3")){//Consultar
		if(MensajeU.existeReg(conEnoc, request.getParameter("Id"))){
			Mensaje.mapeaRegId(conEnoc, request.getParameter("Id"));
			sResultado = "Consulta";
		}else{
			sResultado = "No existe";
		}
	}
	
	mensaje = MensajeU.getListAll(conEnoc,"ORDER BY ID");
%>
<div class="container-fluid">
<h1>Mensaje</h1>
<form action="mensaje" method="post" name="frmreligion" target="_self">
<input type="hidden" name="Accion">
<table id="table" class="table table-sm table-bordered">
  <tr>
    <td>
	  <table style="width:100%" >
	    <tr>
	      <td><b>Id</b></td>
	      <td><input type="text" class="form-control" style="width:400px;" name="Id" id="Id" size="3"  maxlength="2" value="<%= Mensaje.getId()%>" readonly></td>
	    </tr>
        <tr>
          <td colspan="1"><b><spring:message code="aca.Comentario"/>:</b></td>
          <td>
            <textarea onKeyUp="return maximaLongitud(this,400)" id="Comentario"  class="form-control" style="width:400px;"name="Comentario" cols="60" rows="4"><%=Mensaje.getComentario() %></textarea> 
          </td>
        </tr>
        <tr> 
          <td><strong><spring:message code="aca.Tipo"/>:</strong></td>
          <td><select name="Tipo"  class="form-select" style="width:400px;" id="Tipo">
				<option  <%if(Mensaje.getTipo().equals("C")) out.print(" Selected ");%> value="C">Deuda</option>
				<option  <%if(Mensaje.getTipo().equals("W")) out.print(" Selected ");%> value="W">Aviso</option>
            </select></td>
        </tr>
        <tr> 
          <td><strong><spring:message code="aca.Estado"/>:</strong></td>
<td>
			<select name="Estado"  id="Estado" class="form-select" style="width:400px;">
				<option  <%if(Mensaje.getEstado().equals("A")) out.print(" Selected ");%> value="A"><spring:message code='aca.Activo'/></option>
				<option  <%if(Mensaje.getEstado().equals("I")) out.print(" Selected ");%> value="I"><spring:message code='aca.Inactivo'/></option>
            </select>
		  </td>        	
        </tr>
        <tr> 
          <td colspan="2" align="center"><%=sResultado%></td>
        </tr>
        <tr>
          <th colspan="2" style="text-align:center;"> 
		  <a class="btn btn-primary"href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a>
		  </th>
        </tr>
      </table>
	</td>
  </tr>
</table>
<table>
</table>
<table id="table" class="table table-sm table-bordered">
<thead class="table-info">	 
  <tr>
    <th width="2%"><spring:message code="aca.Numero"/></th>
    <th width="30%"><spring:message code="aca.Comentario"/></th>
  </tr>
</thead>
<%  
	for(int i=0; i<mensaje.size();i++){
		aca.financiero.FinMensaje msj = (aca.financiero.FinMensaje) mensaje.get(i);

%>  
  <tr>
    <td align="center"><a class="fas fa-edit" href="mensaje?Accion=3&Id=<%=msj.getId()%>"> 
      </a><%= msj.getId() %></td>
    <td align="justify"><%= msj.getComentario() %></td>
  </tr>
<% }%>
</table>
</form>
</body>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>