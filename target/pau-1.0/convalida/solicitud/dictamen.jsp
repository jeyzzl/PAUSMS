<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.util.*"%>

<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.conva.spring.ConvEvento"%>
<%@page import="aca.conva.spring.ConvMateria"%>
<%@page import="aca.conva.spring.ConvHistorial"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.kardex.spring.KrdxCursoImp"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
		
	<script type="text/javascript">
		function GrabarConvalidaciones(){
			document.frmmaterias.Accion.value = 1;			
			document.frmmaterias.submit();
		}
		
		function GrabarKardex(cantidad){
			if(confirm("¿Estás seguro de autorizar las convalidaciones para el kardex?")){
				document.frmmaterias.Accion.value = 2;
			}			
			document.frmmaterias.submit();
		}
		
		function Borrar(convalidacionId, folio, cursoId){
			if(confirm("¿Estás seguro de eliminar la nota registrada en el kardex del alumno?")){
				document.location.href = "borrarDictamen?ConvalidacionId="+convalidacionId+"&Folio="+folio+"&CursoId="+cursoId;
			}		
		}
				
		function checaCuantasLetras(i, txtarea){
			var txt = txtarea.value;
			var n = txt.length;
			if (n>i)
				txtarea.value=txt.substring(0,i);
		}	
	</script>
</head>	
<%	
	String codigoPersonal 		= (String)session.getAttribute("codigoPersonal");
	String codigoAlumno 		= (String)session.getAttribute("codigoAlumno");
	
	String convalidacionId		= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");
	String accion				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String checked				= request.getParameter("check")==null?"":"checked";
	
	ConvEvento convEvento 		= (ConvEvento)request.getAttribute("convEvento");	
	String carreraId			= (String) request.getAttribute("carreraId");
	String carreraNombre		= (String) request.getAttribute("carreraNombre");	
	String alumnoNombre 		= (String) request.getAttribute("alumnoNombre");
	
	String curso				= "";
	String escribir				= "";
	String convalida			= "";

	int ciclo					= 0;
	int cicloTem				= 0;			
	
	boolean guardo				= true;	
	
	List<ConvMateria> lisMaterias 				= (List<ConvMateria>)request.getAttribute("lisMaterias");	
	HashMap<String,MapaCurso> mapaMaterias		= (HashMap<String,MapaCurso>)request.getAttribute("mapaMaterias");
	HashMap<String,KrdxCursoImp> mapaImportados	= (HashMap<String,KrdxCursoImp>)request.getAttribute("mapaImportados");		
%>
<body>
<div id='content'>
	<h3>Materias convalidadas <small class="text-muted fs-5">( <%=codigoAlumno %> - <%=alumnoNombre%> - <%=convEvento.getPlanId()%> - <%=carreraNombre%>]</small></h3>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="solicitud"><spring:message code="aca.Regresar"/></a>
	</div>
	<form name="frmmaterias" method="post" action="grabarDictamen?ConvalidacionId=<%=convalidacionId %>">
		<input name="CursoId" type="hidden" value=""/>
		<input name="Folio" type="hidden" value=""/>
		<input name="Accion" type="hidden" value=""/>
		<input name="PlanId" type="hidden" value=""/>
		<input name="convalidacionId" type="hidden" value="<%=convalidacionId%>"/>
		<input name="cantidadMaterias" type="hidden" value=""/>
		<table style="width:100%; margin:0 auto">
<%	if(!escribir.equals("")){ %>
		<tr align="center">
			<td width="100%" colspan="10">
				<table style="width:70%"   align="center">
				<tr>
					<td colspan="2" align="center"><font color="#AE2113"><b><%=escribir%></b></font></td>
				</tr>
				</table>
			</td>
		</tr>
<%	} %>
		</table>
		<table class="table table-nohover table-condensed table-fontsmall">
		<tr>
	  		<td align="center" colspan="9">
	  			<a href="dictamen?ConvalidacionId=<%=convalidacionId %>&carreraId=<%=carreraId %>&check=1">Todo</a>
  			</td>
	  	</tr>
<%	
	int row = -1;
	for( ConvMateria  convMateria : lisMaterias){
		row++;		
					
		String cursoNombre = "-";		
		if (mapaMaterias.containsKey(convMateria.getCursoId())){
			cursoNombre = mapaMaterias.get(convMateria.getCursoId()).getNombreCurso();
			ciclo 		= Integer.parseInt(mapaMaterias.get(convMateria.getCursoId()).getCiclo());
		}		
		if(ciclo != cicloTem){
			cicloTem = ciclo; 
%>
 		<tr>
   			<td colspan="9"><strong>Semestre <%=ciclo%></strong></td>
 		</tr>
 		<tr>
	    	<th width="1%"><span class="Estilo5">&nbsp;</span></th>
		    <th width="20%" height="16"><span class="Estilo5"><spring:message code="aca.Nombre"/> Materia</span></th>
		    <th width="7%"><span class="Estilo5"><spring:message code="aca.Estado"/></span></th>
		    <th width="7%"><span class="Estilo5">Conv.</span></th>
		    <th width="7%"><span class="Estilo5">Créditos</span></th>
		    <th width="7%"><span class="Estilo5"><spring:message code="aca.Nota"/></span></th>
		    <th width="7%"><span class="Estilo5"><spring:message code="aca.Fecha"/></span></th>						    
		    <th width="7%"><span class="Estilo5">Trámite</span></th>
 		</tr>
<%		}					
		String tramite	= "Revisión";
		String folio	= convMateria.getFolio();
		if(!folio.equals("0")){
			if (mapaImportados.containsKey(folio)){
				tramite = "Registrado";
			}						
		}
		
		boolean tieneKrdx = !folio.equals("0");
		if(tieneKrdx){
%>
			<input name="check<%=row%>" id="check<%=row%>" type="hidden"  value="<%=convMateria.getCursoId() %>">
      		<input type="hidden" id="Tipo<%=row%>" name="Tipo<%=row%>" value="<%=convMateria.getTipo()%>">
      		<input type="hidden" id="Cred<%=row%>" name="Cred<%=row%>" value="<%=convMateria.getCreditos_O()==null?"0":convMateria.getCreditos_O()%>">
      		<input type="hidden" id="FCal<%=row%>" name="FCal<%=row%>" value="<%=convMateria.getNota_O()%>">
      		<input type="hidden" id="FFinal<%=row%>" name="FFinal<%=row%>" value="<%=convMateria.getfNota()%>">      					      		
<%		} %>
  		<tr>
    		<td>
   			<%	if(!folio.equals("0")){ %>
	    			<a class="fas fa-trash-alt" href="javascript:Borrar('<%=convalidacionId%>','<%=folio%>','<%=convMateria.getCursoId()%>')"></a>
			<%	} %>
			</td>		
		    <td><%=cursoNombre%></td>
		    <td align="center">
		    	<input <%=tieneKrdx?"disabled":""%> name="check<%=row%>" id="check<%=row%>" type="checkbox"  value="<%=convMateria.getCursoId() %>" <%if(convMateria.getEstado().equals("S")) out.print("checked"); else out.print(checked); %>/>
		    </td>
	    	<td align="center">
	      		<select <%=tieneKrdx?"disabled":""%> id="Tipo<%=row%>" name="Tipo<%=row%>">
    	  		<%	String tipo = convMateria.getTipo()==null?"":convMateria.getTipo(); %>
         			<option value="I" <%=tipo.equals("I")?"Selected":"" %>>Interna</option>
         			<option value="E" <%=tipo.equals("E")?"Selected":"" %>>Externa</option>
          		</select>
	    	</td>
	     	<td align="center">
	       		<input <%=tieneKrdx?"disabled":""%> name="Cred<%=row%>"  type="text" class="text" id="Cred" style="width:50px;" maxlength="3" value="<%=convMateria.getCreditos_O()==null?"0":convMateria.getCreditos_O()%>">
	    	</td>
	    	<td align="center">
	      		<input <%=tieneKrdx?"disabled":""%> name="FCal<%=row%>"  type="text" class="text" id="FCal" style="width:50px;" size="3" maxlength="3" value="<%=convMateria.getNota_O() %>">
	    	</td>
    		<td align="left">
	      		<input <%=tieneKrdx?"disabled":""%> name="FFinal<%=row%>" type="text" class="text fecha" id="FFinal<%=row%>" size="12" maxlength="10" onfocus="focusFecha(this);" value="<%=convMateria.getfNota()!=null?convMateria.getfNota().trim():convMateria.getfNota() %>" data-date-format="dd/mm/yyyy">				      		
	    	</td>					    
		    <td align="center"><%=tramite %></td>
	  	</tr>
<%	} %>
		<tr align="center">
	    	<td colspan="9" style="text-align:center;">
	    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		Guardar en:<br>
				<input class="btn btn-primary" type="button" name="Submit" value="Convalidación" onClick="javascript:GrabarConvalidaciones();">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="btn btn-primary" type="button" name="Submit" value="Kardex" onClick="javascript:GrabarKardex();">
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