<%@ page import="java.text.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../idioma.jsp"%>

<%@ page import= "aca.catalogo.spring.CatEstrategia"%>
<%@ page import= "aca.carga.spring.CargaGrupoEvaluacion"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">		
	function Grabar(){
		if(document.frmEvaluacion.EvaluacionId.value!= "" && document.frmEvaluacion.Fecha.value!= "" 
		&& document.frmEvaluacion.Tipo.value!="" && document.frmEvaluacion.Valor.value!=""){			
			document.frmEvaluacion.submit();			
		}else{
			alert("Fill out the entire form...");
		}
	}	
</script>

<%
	DecimalFormat getformato= new DecimalFormat("##0.00;(##0.00)");

 	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
 	String codigoEmpleado		= (String) session.getAttribute("codigoEmpleado");
 	
	String cursoCargaId			= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	String evaluacionE42		= request.getParameter("EvaluacionE42")==null?"0":request.getParameter("EvaluacionE42");
	String resultado			= request.getParameter("Resultado")==null?"-":request.getParameter("Resultado");
	CargaGrupoEvaluacion cge 	= (CargaGrupoEvaluacion)request.getAttribute("cge");
	String maestroNombre 		= (String)request.getAttribute("maestroNombre");
	String cursoNombre 			= (String)request.getAttribute("cursoNombre");	
	String estadoGrupo 			= (String)request.getAttribute("estadoGrupo");
	String estadoNombre 		= "-";	
	String hora 				= cge.getFecha().substring(10).split(":")[0].trim();
	String minuto 				= cge.getFecha().substring(10).split(":")[1].trim();

	if (estadoGrupo.equals("1")) estadoNombre = "<span style='color:gray; font-weight: bold;'>Subject created</span>"; 
	if (estadoGrupo.equals("2")) estadoNombre = "<span style='color:blue; font-weight: bold;'>Ordinary Subject</span>";
	if (estadoGrupo.equals("3")) estadoNombre = "<span style='color:green; font-weight: bold;'>Extraordinary Subject</span>";
	if (estadoGrupo.equals("4")) estadoNombre = "<span style='color:black; font-weight: bold;'>Closed Subject</span>";
	if (estadoGrupo.equals("5")) estadoNombre = "<span style='color:cherry; font-weight: bold;'>Registered Subject</span>";			
	
	List<CatEstrategia> lisEstrategias				= (List<CatEstrategia>)request.getAttribute("lisEstrategias");		

%>
<div class="container-fluid">
	<h2>Edit Evaluation <small class="text-muted fs-5">( <%=codigoEmpleado%> - <%=maestroNombre%> - <%=cursoNombre%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="metodo?CursoCargaId=<%=cursoCargaId%>"><i class="fas fa-arrow-left"></i></a>
		&nbsp;
		<%=resultado.equals("-")?"":resultado%>
	</div>	
	<form name="frmEvaluacion" action="grabarMetodo" method="post" >
	<input type="hidden" name="CursoCargaId" id="CursoCargaId" value="<%=cursoCargaId%>">			
	<input type="hidden" name="EvaluacionE42" value="<%=evaluacionE42%>">	
	<table class="table table-sm table-bordered" style="width:45%">
	<tr class="table-info">
    	<th colspan="2" align="center"> <h4>Evaluation Strategy</h4></th>
	</tr>
	<tr>              
		<td width="27%"><strong>No. Evaluation:</strong></td>
        <td width="73%">
			<input name="EvaluacionId" type="text" class="form-control" id="EvaluacionId" value="<%=cge.getEvaluacionId()%>" size="2" maxlength="2" readonly>            
		</td>
    </tr>
    <tr> 
    	<td width="27%"><strong>Strategy:</strong></td>
        <td width="73%">
			<select name="EstrategiaId" id="EstrategiaId" class="form-select" style="width:400px;">
<%				
		for( CatEstrategia estrategia : lisEstrategias){					
			if (estrategia.getEstrategiaId().equals(cge.getEstrategiaId())){
				out.print(" <option value='"+estrategia.getEstrategiaId()+"' Selected>"+ estrategia.getNombreEstrategia()+"</option>");
			}else{
				out.print(" <option value='"+estrategia.getEstrategiaId()+"'>"+ estrategia.getNombreEstrategia()+"</option>");
			}				
		}		
 %>
        	</select>
		</td>
	</tr>
    <tr>
    	<td width="27%"><strong>Description:</strong></td>
        <td width="73%"><input name="NombreEvaluacion" type="text" class="form-control" id="NombreEvaluacion" value="<%=cge.getNombreEvaluacion()%>" size="80" maxlength="100"></td>
    </tr>
    <tr>
    	<td width="27%"><strong>Date:</strong></td>
        <td width="73%"><div class="d-flex align-items-center">
        	<input name="Fecha" type="text" class="form-control" id="Fecha" data-date-format="yyyy/mm/dd" value="<%=cge.getFecha()%>" size="10" style="width:180px;" />&nbsp;(YYYY/MM/DD)</div>
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
        <td><input name="Valor" type="text" class="form-control" id="Valor" value="<%=cge.getValor()%>" size="6" maxlength="6" style="width:120px;"></td>
    </tr>
	<tr> 
       	<td><strong>Type:</strong></td>
        <td><select name="Tipo" id="Tipo" class="form-select" style="width:200px;">
            <option value="%" <% if (cge.getTipo().equals("%")) out.print("selected");%> >Percentage</option>
            <option value="P" <% if (cge.getTipo().equals("P")) out.print("selected");%>>Points</option>
             <option value="E" <% if (cge.getTipo().equals("E")) out.print("selected");%> >Extra Points</option>
              </select></td>
    </tr>    
    <tr> 
    	<th colspan="2" align="center" style="text-align:center">
            <% if ( estadoGrupo.equals("1") || estadoGrupo.equals("2")){%>               
              <a href="javascript:Grabar()" class="btn btn-primary">Save</a>&nbsp;             
            <% }else{ %>
            	<font color="#000000"> Closed Subject !</font>
            <% } %>
        </th>
    </tr>
	</table>
	</form>	
</div>
<script>
	jQuery('#Fecha').datepicker();
</script>
<!-- fin de estructura -->