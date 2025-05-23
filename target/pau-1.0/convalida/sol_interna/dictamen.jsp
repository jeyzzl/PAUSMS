<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@ page import= "aca.util.*"%>
<%@ page import= "aca.conva.spring.ConvMateria"%>
<%@ page import= "aca.conva.spring.ConvEvento"%>
<%@ page import= "aca.kardex.spring.KrdxCursoImp"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
</head>	
	<script type="text/javascript">
		function grabar(cantidad){
			document.frmmaterias.Accion.value = 1;
			document.frmmaterias.cantidadMaterias.value = cantidad
			document.frmmaterias.submit();
		}
		
		function kardex(cantidad){
			if(confirm("¿Estas seguro de autorizar las convalidaciones para el kardex?")){
				document.frmmaterias.Accion.value = 2;
			}
			document.frmmaterias.cantidadMaterias.value = cantidad
			document.frmmaterias.submit();
		}
		
		function Borrar(folio, cursoId){
			if(confirm("¿Estas seguro de eliminar la nota registrada en el kardex del alumno?")){
				document.frmmaterias.Accion.value = 3;
			}
			document.frmmaterias.Folio.value = folio;
			document.frmmaterias.CursoId.value = cursoId;
			document.frmmaterias.submit();
		}
		
		function checaCuantasLetras(i, txtarea){
			var txt=txtarea.value;
			var n=txt.length;
			if (n>i)
				txtarea.value=txt.substring(0,i);
		}	
	</script>
<%	
	String codigoPersonal 		= (String)request.getAttribute("codigoPersonal");	
	String convalidacionId		= (String)request.getAttribute("convalidacionId");	

	String codigoAlumno			= (String)request.getAttribute("codigoAlumno");	
	String checked				= (String)request.getAttribute("checked");	
	String carreraId			= (String)request.getAttribute("carreraId");	
	String nombreCarrera		= (String)request.getAttribute("nombreCarrera");	
	String escribir				= (String)request.getAttribute("escribir");	
	String planActivo			= (String)request.getAttribute("planActivo");	

	int ciclo	 = 0;
	int cicloTem = 0;
	int i		 = 0;
	
	ConvEvento convEvento 		= (ConvEvento)request.getAttribute("convEvento");	
	KrdxCursoImp cursoImp 		= new KrdxCursoImp();
	AlumPersonal alumPersonal 	= (AlumPersonal)request.getAttribute("alumPersonal");
	
	List<ConvMateria> lisMaterias = (List<ConvMateria>)request.getAttribute("lisMaterias");	
	
	HashMap<String, KrdxCursoImp> mapaCursosAlumno 	= (HashMap<String, KrdxCursoImp>)request.getAttribute("mapaCursosAlumno");	
	HashMap<String,MapaCurso> mapaCursosPlan 		= (HashMap<String, MapaCurso>)request.getAttribute("mapaCursosPlan");	
%>
<body>
<div class='container-fluid'>
	<h3>Registrar materias<small class="text-muted fs-5"> (<%=codigoAlumno %> - <%=alumPersonal.getNombreLegal() %> - <%=planActivo%> - <%=nombreCarrera%> )</small></h3>
	<div class='alert alert-info'>
		<a class="btn btn-primary" href="interna"><spring:message code="aca.Regresar"/></a>&nbsp; &nbsp;
		<%=escribir.equals("")?"":escribir%>
	</div>
	<form name="frmmaterias" method="post" action="dictamen?ConvalidacionId=<%=convalidacionId %>">
		<input name="CursoId" type="hidden" value=""/>
		<input name="Folio" type="hidden" value=""/>
		<input name="Accion" type="hidden" value=""/>
		<input name="PlanId" type="hidden" value=""/>
		<input name="convalidacionId" type="hidden" value="<%=convalidacionId%>"/>
		<input name="cantidadMaterias" type="hidden" value=""/>		
		<table  class="table table-nohover table-condensed" style="width:80%">
		  	<tr>
		  		<td colspan="9">
		  			<a class="btn btn-primary btn-sm" href="dictamen?ConvalidacionId=<%=convalidacionId %>&carreraId=<%=carreraId %>&check=1">Todo</a>
		  			<a class="btn btn-primary btn-sm" href="dictamen?ConvalidacionId=<%=convalidacionId %>&carreraId=<%=carreraId %>&check=0">Ninguna</a>
				</td>
		  	</tr>
	<%	for(i = 0; i < lisMaterias.size(); i++){
		ConvMateria convMateria = lisMaterias.get(i);
			ciclo = Integer.parseInt(convMateria.getConvalidacionId().substring(0,2).trim());
			if(ciclo != cicloTem){
				cicloTem = ciclo; %>
	  			<tr>
	    			<td colspan="9"><strong>Semestre <%=ciclo%></strong></td>
	  			</tr>
	  			<tr>
			    	<th width="1%"><span class="Estilo5"></span></th>
				    <th width="20%" height="16"><span class="Estilo5"><spring:message code="aca.Nombre"/> Materia</span></th>
				    <th width="7%"><span class="Estilo5"><spring:message code="aca.Estado"/></span></th>
				    <th width="7%"><span class="Estilo5">Créditos</span></th>
				    <th width="7%"><span class="Estilo5"><spring:message code="aca.Nota"/></span></th>
				    <th width="7%"><span class="Estilo5"><spring:message code="aca.Fecha"/></span></th>
				    <th width="7%"><span class="Estilo5">Observaciones</span></th>
				    <th width="7%"><span class="Estilo5">Trámite</span></th>
	  			</tr>
		<%	}
			String msj = "";
			String tramite	= "Revisión";
			if(!convMateria.getFolio().equals("0")){	
				if(mapaCursosAlumno.containsKey(convMateria.getFolio())){
					cursoImp = mapaCursosAlumno.get(convMateria.getFolio());
					msj = cursoImp.getObservaciones()==null?"":cursoImp.getObservaciones();
					tramite = "Registrado";
				}
			}
			
			String nombreCurso = "";
			if(mapaCursosPlan.containsKey(convEvento.getPlanId())){
				nombreCurso = mapaCursosPlan.get(convEvento.getPlanId()).getNombreCurso();
			}
			
			boolean tieneKrdx = !convMateria.getFolio().equals("0");
			if(tieneKrdx){ %>
				<input name="check<%=i%>" id="check<%=i%>" type="hidden"  value="<%=convMateria.getCursoId() %>">
	      		<input type="hidden" id="Tipo<%=i%>" name="Tipo<%=i%>" value="<%=convMateria.getTipo()%>">
	      		<input type="hidden" id="Cred<%=i%>" name="Cred<%=i%>" value="<%=convMateria.getCreditos_O()==null?"0":convMateria.getCreditos_O()%>">
	      		<input type="hidden" id="FCal<%=i%>" name="FCal<%=i%>" value="<%=convMateria.getNota_O()%>">
	      		<input type="hidden" id="FFinal<%=i%>" name="FFinal<%=i%>" value="<%=convMateria.getfNota()%>">
	      		<input type="hidden" id="FFinal<%=i%>" name="FFinal<%=i%>" value="<%=convMateria.getfNota()%>">
	      		<input type="hidden" id="observaciones<%=i%>" name="observaciones<%=i%>" value="<%=msj%>">
		<%	} %>
	  		<tr>
	    		<td>
    			<%	if(!convMateria.getFolio().equals("0")){%>
		    			<a class="fas fa-trash-alt" href="javascript:Borrar('<%=convMateria.getFolio()%>', '<%=convMateria.getCursoId() %>')"></a>
				<%	} %>
				</td>		
			    <td><%=nombreCurso%></td>
			    <td align="center">					  
			    	<input <%=tieneKrdx?"disabled":""%> name="check<%=i%>" id="check<%=i%>" type="checkbox"  value="<%=convMateria.getCursoId() %>" <%if(convMateria.getEstado().equals("S")) out.print("checked"); else out.print(checked); %>/>
			    </td>
		     	<td align="center" <%=tieneKrdx?"disabled":""%>>
		     		<input name="Cred<%=i%>" type="hidden" id="Cred<%=i%>" value="<%=convMateria.getCreditos_O()==null?"0":convMateria.getCreditos_O()%>">
		     		<%=convMateria.getCreditos_O()==null?"0":convMateria.getCreditos_O()%>				     		
		 		</td>
		    	<td align="center" <%=tieneKrdx?"disabled":""%> name="FCal<%=i%>"  type="text" class="text" id="FCal" style="width:50px;">
		    		<input name="FCal<%=i%>" type="hidden" id="FCal<%=i%>" value="<%=convMateria.getNota_O()%>">
		    		<%=convMateria.getNota_O() %>
		    	</td>
	    		<td align="left" <%=tieneKrdx?"disabled":""%>>
	    			<input name="FFinal<%=i%>" type="hidden" id="FFinal<%=i%>" value="<%=convMateria.getfNota()!=null?convMateria.getfNota().trim():convMateria.getfNota()%>">	    		
		      		<%=convMateria.getfNota()!=null?convMateria.getfNota().trim():convMateria.getfNota() %>
		    	</td>
			    <td align="center">
					<textarea <%=tieneKrdx?"disabled":""%> cols='40' name='observaciones<%=i%>' id='observaciones<%=i%>' onkeyup='checaCuantasLetras(200, this);' style="resize:vertical;"><%=msj%></textarea>
			    </td>
			    <td align="center"><%=tramite %></td>
		  	</tr>
	<% 	}				
	%>
	  	<tr align="center">
	    	<td colspan="9" style="text-align:center;">
	    		Guardar en:&nbsp;
				<input class="btn btn-primary" type="button" name="Submit" value="Convalidación" onClick="grabar(<%=i%>);">&nbsp;
				<input class="btn btn-primary" type="button" name="Submit" value="Kardex" onClick="kardex(<%=i%>);">
		      	<input name="planId" type="hidden" id="planId" value="">
		      	<input name="carreraId" type="hidden" id="carreraId" value="<%=carreraId%>">
	      	</td>
	  	</tr>
		</table>
	</form>
</div>
</body>
</html>
<script>	
	jQuery('.fecha').datepicker();
</script>