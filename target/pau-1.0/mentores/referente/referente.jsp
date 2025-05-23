<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.mentores.spring.MentAcceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<script type="text/javascript">
	function privilegios(codigo){
		document.location.href="privilegios?Codigo="+codigo;
	}
	
	function Grabar(){		
		document.frmReferente.submit();
	}
	
	function Borrar(codigo){
		if ( confirm("Are you sure you want to delete this record?")){			
			document.location.href="borrar?CodigoPersonal="+codigo;
		}
	}
	
</script>
<%		
	String codigoEmpleado	= (String) session.getAttribute("codigoEmpleado");
	
	List<MentAcceso> lisAccesos				= (List<MentAcceso>) request.getAttribute("lisAccesos");
	HashMap<String,String> mapaEmpleados	= (HashMap<String,String>) request.getAttribute("mapaEmpleados");	
		
	String mensaje			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");		
	
	/*
	if (request.getParameter("Accion")!=null & request.getParameter("Accion")!="")
		numAccion = Integer.parseInt(request.getParameter("Accion"));	
	
	switch (numAccion){
		case 1:{			
			acceso.setCodigoPersonal(request.getParameter("Codigo"));
			if (!acceso.existeReg(conEnoc)){
				acceso.setAcceso("X");
				if (acceso.insertReg(conEnoc)){					
					strResultado= "Se grabó el referente: "+acceso.getCodigoPersonal();
				}else{
					strResultado= "Error al insertar..¡¡";
				}
			}else{
				strResultado= "El referente "+acceso.getCodigoPersonal()+" ya esta registrado..¡¡";
			}
			break;
		}
		case 2:{
			acceso.setCodigoPersonal(request.getParameter("Codigo"));
			if (acceso.existeReg(conEnoc)){
				if (acceso.deleteReg(conEnoc)){					
					strResultado= "Se borró el referente: "+acceso.getCodigoPersonal();
				}else{
					strResultado= "Error al borrar..¡¡";
				}
			}else{
				strResultado= "El referente "+acceso.getCodigoPersonal()+" no esta registrado..¡¡";
			}	
			break;
		}
	}
	*/
%>
<div class="container-fluid">
	<h2>Referee Catalog</h2>
	<form name="frmReferente" method="post" action="grabar">
	<div class="alert alert-info">
		Referee:
		<input type="text" class="text" name="CodigoPersonal" value="<%=codigoEmpleado%>" size="8" maxlength="7">
		<a class="btn btn-primary" onclick="javascript:Grabar()"><i class="fas fa-save"></i></a>		
	</div>        
<%
	if (!mensaje.equals("-")) out.print("<div class='alert alert-info'>"+mensaje+"</div>");
%>	
	<table class="table table-condensed table-bordered">
	    <thead class="table-info">	 
			<tr> 
				<th width="5%"><h4>Operation</h4></th>
			    <th width="15%"><h4>Enrollment</h4></th>
			    <th width="45%"><h4>Name</h4></th>
			    <th width="40%"><h4>Privileges</h4></th>	    
			</tr>
	    </thead>
    <tbody>
<%	
	for(MentAcceso acceso : lisAccesos){	
		
		String empleadoNombre = "-";
		if (mapaEmpleados.containsKey(acceso.getCodigoPersonal())){
			empleadoNombre = mapaEmpleados.get(acceso.getCodigoPersonal());
		}
%>
	<tr> 
    	<td align="center">
      		<a title="Privileges" href="javascript:privilegios('<%=acceso.getCodigoPersonal()%>')"><i class="fas fa-plus"></i></a>&nbsp;
            <a title="Delete" href="javascript:Borrar('<%=acceso.getCodigoPersonal()%>')"><i class="far fa-trash-alt"></i></a> 
    	</td>
    	<td> <div align="center"><%=acceso.getCodigoPersonal()%></div></td>
    	<td><%=empleadoNombre%></td>
    	<td><%=acceso.getAcceso()%></td>
  	</tr>  		
<%	}	%>
	</tbody>
	</table>
	</form>
</div>