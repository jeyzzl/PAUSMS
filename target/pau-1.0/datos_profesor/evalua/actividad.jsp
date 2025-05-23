<%@ page import="java.text.*" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.carga.spring.CargaGrupoActividad"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">
		
	function Grabar(){
		if(document.frmActividad.Fecha.value!= "" && document.frmActividad.Nombre.value!="" && document.frmActividad.Valor.value!=""){
			document.frmActividad.submit();			
		}else{
			alert("Fill out the entire form...");
		}
	}	   
</script>
<% 	//System.out.println("Entro en actividad.jsp");
	DecimalFormat getformato= new DecimalFormat("##0.00;(##0.00)");

 	String codigoPersonal		= (String) session.getAttribute("codigoPersonal"); 	
	String cursoCargaId			= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	String evaluacionId			= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");
	String actividadId			= request.getParameter("ActividadId")==null?"0":request.getParameter("ActividadId");
	String estadoGrupo 			= (String)request.getAttribute("estadoGrupo");
	String maestroNombre 		= (String)request.getAttribute("maestroNombre");
	String cursoNombre			= (String)request.getAttribute("cursoNombre");
	String evaluacionNombre		= (String)request.getAttribute("evaluacionNombre");
	CargaGrupoActividad cga		= (CargaGrupoActividad)request.getAttribute("cga");
	
	String hora 				= cga.getFecha().substring(10).split(":")[0].trim();
	String minuto 				= cga.getFecha().substring(10).split(":")[1].trim();
	
	String resultado			= request.getParameter("Resultado")==null?"-":request.getParameter("Resultado");	
	int j=0, i=0;	
%>
<body>
<div class="container-fluid">
	<h2>Edit Activity <small class="text-muted fs-6">( <%=codigoPersonal%> - <%=maestroNombre%> - <%=cursoNombre%> - Método: <%=evaluacionNombre%> )</small></h2>
	<div class="alert alert-info">
		<a href="metodo?CursoCargaId=<%=cursoCargaId%>" class="btn btn-primary">Return</a>&nbsp;
	</div>	
	<form name="frmActividad" action="grabarActividad" method="post">	
	<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
	<input type="hidden" name="EvaluacionId" value="<%=evaluacionId%>">
	<input type="hidden" name="ActividadId" value="<%=actividadId%>">
	<input type="hidden" name="ActividadE42" value="<%=cga.getActividadE42()%>">
 	<table class="table table-sm table-bordered" style="width:50%">          
    <tr> 
    	<td width="15%"><strong>Activity:</strong></td>
        <td width="85%">
			<input name="Nombre" type="text" class="text form-control" id="Nombre" value="<%=cga.getNombre()%>" size="40" maxlength="30">
		</td>
	</tr>
    <tr>
    	<td><strong>Date:</strong></td>
        <td>
        	<div class="d-flex align-items-center">
        		<input name="Fecha" data-date-format="dd/mm/yyyy" type="text" class="text form-control" id="Fecha" value="<%=cga.getFecha()%>" onfocus="focusFecha(this);" size="10" maxlength="10">
         		 &nbsp;(YYYY/MM/DD)
          	</div>
        </td>
	</tr>
	<tr> 
      	<td><strong>Hour:</strong></td>
        <td>        	
        	<select name="Hora" class="form-select" style="width:100px">
        		<option value="05" <%=hora.equals("05")?"selected":""%>>05</option>
        		<option value="06" <%=hora.equals("06")?"selected":""%>>06</option>
        		<option value="07" <%=hora.equals("07")?"selected":""%>>07</option>
        		<option value="08" <%=hora.equals("08")?"selected":""%>>08</option>
        		<option value="09" <%=hora.equals("09")?"selected":""%>>09</option>
        		<option value="10" <%=hora.equals("10")?"selected":""%>>10</option>
        		<option value="11" <%=hora.equals("11")?"selected":""%>>11</option>
        		<option value="12" <%=hora.equals("12")?"selected":""%>>12</option>
        		<option value="13" <%=hora.equals("13")?"selected":""%>>13</option>
        		<option value="14" <%=hora.equals("14")?"selected":""%>>14</option>
        		<option value="15" <%=hora.equals("15")?"selected":""%>>15</option>
        		<option value="16" <%=hora.equals("16")?"selected":""%>>16</option>
        		<option value="17" <%=hora.equals("17")?"selected":""%>>17</option>
        		<option value="18" <%=hora.equals("18")?"selected":""%>>18</option>
        		<option value="19" <%=hora.equals("19")?"selected":""%>>19</option>
        		<option value="20" <%=hora.equals("20")?"selected":""%>>20</option>
        		<option value="21" <%=hora.equals("21")?"selected":""%>>21</option>
        		<option value="22" <%=hora.equals("22")?"selected":""%>>22</option>
        		<option value="23" <%=hora.equals("23")?"selected":""%>>23</option>       		
        	</select>        	
        </td>        
    </tr>
    <tr> 
      	<td><strong>Minute:</strong></td>
        <td>        	
        	<select name="Minuto" class="form-select" style="width:100px">
        		<option value="00" <%=minuto.equals("00")?"selected":""%>>00</option>
        		<option value="05" <%=minuto.equals("05")?"selected":""%>>05</option>
        		<option value="10" <%=minuto.equals("10")?"selected":""%>>10</option>
        		<option value="15" <%=minuto.equals("15")?"selected":""%>>15</option>
        		<option value="20" <%=minuto.equals("20")?"selected":""%>>20</option>
        		<option value="25" <%=minuto.equals("25")?"selected":""%>>25</option>
        		<option value="30" <%=minuto.equals("30")?"selected":""%>>30</option>
        		<option value="35" <%=minuto.equals("35")?"selected":""%>>35</option>
        		<option value="40" <%=minuto.equals("40")?"selected":""%>>40</option>
        		<option value="45" <%=minuto.equals("45")?"selected":""%>>45</option>
        		<option value="50" <%=minuto.equals("50")?"selected":""%>>50</option>
        		<option value="55" <%=minuto.equals("55")?"selected":""%>>55</option>       		       		
        	</select>        	
        </td>        
    </tr>
    <tr> 
    	<td><strong>Value:</strong></td>
        <td><input name="Valor" type="text" class="text form-control" id="Valor" value="<%=cga.getValor()%>" size="6" maxlength="6"></td>
    </tr> 	
    <tr> 
    	<td colspan="2" align="center"><%=resultado%></td>
	</tr>
    <tr> 
    	<th colspan="2" align="center">
<%	if ( estadoGrupo.equals("1") || estadoGrupo.equals("2")){%>
       	&nbsp;<a href="javascript:Grabar()" class="btn btn-primary">Save</a>
<%	}else{/*Buscar la forma de pasar la actividadId y evaluacionId a la hora de grabar*/%>
   		<font color="#000000">Disabled</font>
<%	}%>
    </th>
    </tr>
	</table>	
	</form>
</div>
</body>
<!-- fin de estructura -->
<script>
	jQuery('#Fecha').datepicker();
</script>