<%@ page import="java.text.*" %>
<%@ page import="aca.carga.spring.CargaGrupo" %>
<%@ page import= "aca.carga.spring.CargaGrupoActividad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../head.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<script type="text/javascript">		
	function Nuevo()	{		
		document.frmactividad.Nombre.value 	= "-";
		document.frmactividad.Valor.value	= "0";		
		document.frmactividad.submit();		
	}
		
	function Grabar(){
		if(document.frmactividad.Fecha.value!= "" && document.frmactividad.Nombre.value!="" && document.frmactividad.Valor.value!=""){			
			document.frmactividad.submit();			
		}else{
			alert("Fill out the entire form");
		}
	}	
</script>
<% 	//System.out.println("Entro en actividad.jsp");
	DecimalFormat getformato= new DecimalFormat("##0.00;(##0.00)");

 	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
 	
	String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	String evaluacionId		= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");
	String actividadId		= request.getParameter("ActividadId")==null?"0":request.getParameter("ActividadId");
	String resultado		= request.getParameter("Resultado")==null?"-":request.getParameter("Resultado");
	
	String maestro	 		= (String)request.getAttribute("maestroNombre");
	String materia 			= (String)request.getAttribute("materiaNombre");	
	String metodo			= (String)request.getAttribute("metodo");
	
	CargaGrupo cargaGrupo	= (CargaGrupo)request.getAttribute("cargaGrupo");
	CargaGrupoActividad cga	= (CargaGrupoActividad)request.getAttribute("cga");
	
	String hora				= "07";
	String minuto			= "00";
	
	// Variables de estudio
	String promedio 		= "";
	String avance			= "";
	int nEst=0,nAvance=0,nEval=0;
	double promGrupo=0, sumaEst=0;	
	
	int j=0, i=0;	
	
	if (cga.getFecha().length()>10){
		hora 	= cga.getFecha().split(" ")[1];
		minuto 	= hora.split(":")[1];
		hora 	= hora.split(":")[0];
	}
%>
<div class="container-fluid">
	<h2>Activities <small class="text-muted fs-5">( <%=codigoPersonal%> - <%=maestro%> )</small></h2>
	<div class="alert alert-info">
		<a href="metodo?idEvaluacion=1&CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestro%>&Materia=<%=materia%>" class="btn btn-primary">
			Return
		</a>
	</div>
	<div class="alert alert-info">
		Subject: [ <%=materia%> ] &nbsp; &nbsp;
		Strategy: [ <%=metodo%> ] &nbsp; &nbsp;
		
	</div>
	<form name="frmactividad" action="grabarActividad?CursoCargaId=<%=cursoCargaId%>" method="post">
	<input type="hidden" name="EvaluacionId" value="<%=evaluacionId%>">
	<input type="hidden" name="ActividadId" value="<%=actividadId%>">		
	<table class="table">        
   	<tr> 
       	<td width="15%"><strong>Activity:</strong></td>
        <td width="85%"><input name="Nombre" type="text" class="text" id="Nombre" value="<%=cga.getNombre()%>" size="40" maxlength="30"></td>
    </tr>
    <tr>
    	<td><strong><spring:message code="aca.Fecha"/>:</strong></td>
        <td><input name="Fecha" type="text" class="input input-medium" id="Fecha" data-date-format="dd/mm/yyyy" value="<%=cga.getFecha() %>" size="15" maxlength="10">(DD/MM/AAAA)</td>
    </tr>
    <tr>
    	<td><strong>Time:</strong></td>
        <td>Hour:
         	<select name="Hora" id="Hora" style="width:50px;">
         		<option <%=hora.equals("05")?"selected":""%>>05</option>
         		<option <%=hora.equals("06")?"selected":""%>>06</option>
         		<option <%=hora.equals("07")?"selected":""%>>07</option>
         		<option <%=hora.equals("08")?"selected":""%>>08</option>
         		<option <%=hora.equals("09")?"selected":""%>>09</option>
         		<option <%=hora.equals("10")?"selected":""%>>10</option>
         		<option <%=hora.equals("11")?"selected":""%>>11</option>         		
         		<option <%=hora.equals("12")?"selected":""%>>12</option>
         		<option <%=hora.equals("13")?"selected":""%>>13</option>
         		<option <%=hora.equals("14")?"selected":""%>>14</option>
         		<option <%=hora.equals("15")?"selected":""%>>15</option>
         		<option <%=hora.equals("16")?"selected":""%>>16</option>
         		<option <%=hora.equals("17")?"selected":""%>>17</option>         		
         		<option <%=hora.equals("18")?"selected":""%>>18</option>
         		<option <%=hora.equals("19")?"selected":""%>>19</option>
         		<option <%=hora.equals("20")?"selected":""%>>20</option>
         		<option <%=hora.equals("21")?"selected":""%>>21</option>
         		<option <%=hora.equals("22")?"selected":""%>>22</option>
         		<option <%=hora.equals("23")?"selected":""%>>23</option>         		         		
         	</select>
         	&nbsp;
         	Minutes:
         	<select name="Minuto" id="Minuto" style="width:50px;">
         		<option <%=minuto.equals("00")?"selected":""%>>00</option>
         		<option <%=minuto.equals("05")?"selected":""%>>05</option>
         		<option <%=minuto.equals("10")?"selected":""%>>10</option>
         		<option <%=minuto.equals("15")?"selected":""%>>15</option>
         		<option <%=minuto.equals("20")?"selected":""%>>20</option>
         		<option <%=minuto.equals("25")?"selected":""%>>25</option>
         		<option <%=minuto.equals("30")?"selected":""%>>30</option>         		
         		<option <%=minuto.equals("35")?"selected":""%>>35</option>
         		<option <%=minuto.equals("40")?"selected":""%>>40</option>
         		<option <%=minuto.equals("45")?"selected":""%>>45</option>
         		<option <%=minuto.equals("50")?"selected":""%>>50</option>
         		<option <%=minuto.equals("55")?"selected":""%>>55</option>         		         		
         	</select>
        </td>
    </tr>    
    <tr> 
    	<td><strong>Weight:</strong></td>
        <td><input name="Valor" type="text" class="text" id="Valor" value="<%=cga.getValor()%>" size="6" maxlength="6"></td>
    </tr>
    <tr>
    	<td><strong>Submit:</strong></td>
      	<td>
			<select name="Enviar" id="Enviar">
            	<option value="N" <%=cga.getEnviar().equals("N")?"selected":""%>>None</option>
                <option value="A" <%=cga.getEnviar().equals("A")?"selected":""%>>File</option>
                <option value="R" <%=cga.getEnviar().equals("R")?"selected":""%>>Comment</option>
			</select>
        </td>
	</tr>	
    <tr> 
        <td colspan="2" align="center"><%=resultado%></td>
    </tr>
    <tr> 
    	<th colspan="2" align="center">
    <%	if ( cargaGrupo.getEstado().equals("1") || cargaGrupo.getEstado().equals("2")){
        	if(actividadId.equals("0")){%>
        	<a class="btn btn-primary" href="javascript:Nuevo()">New</a> 
    <%		}%>
        &nbsp;<a class="btn btn-primary" href="javascript:Grabar()">Save</a>
    <%	}else{/*Buscar la forma de pasar la actividadId y evaluacionId a la hora de grabar*/%>
    	<font color="#000000">¡ Subject close !</font>
    <%	}%>
        </th>
    </tr>
	</table>	
</form>
</div>
<script>
	jQuery('#Fecha').datepicker();
</script>
<!-- fin de estructura -->