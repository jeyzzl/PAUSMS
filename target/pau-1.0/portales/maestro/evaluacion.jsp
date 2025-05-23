<%@ page import="java.text.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="aca.carga.spring.CargaGrupoEvaluacion" %>
<%@ page import="aca.log.spring.LogOperacion" %>
<%@ page import="aca.catalogo.spring.CatEstrategia" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<script type="text/javascript">	
	function Nuevo()	{		
		document.frmEvaluacion.EvaluacionId.value 		= "0";
		document.frmEvaluacion.NombreEvaluacion.value 	= "";
		document.frmEvaluacion.Valor.value				= "";		
		document.frmEvaluacion.Accion.value				= "1";
		document.frmEvaluacion.submit();		
	}
		
	function Grabar(){
		if(document.frmEvaluacion.EvaluacionId.value!= "" && document.frmEvaluacion.Fecha.value!= "" 
		&& document.frmEvaluacion.Tipo.value!="" && document.frmEvaluacion.Valor.value!=""){			
			document.frmEvaluacion.submit();			
		}else{
			alert("Fill out the entire form!");
		}
	}	
	
	function Modificar(a,b,c,d,e){
		document.location.href='actividad?ActividadId='+a+'&accion=1&CursoCargaId='+b+'&idEvaluacion='+c+'&Maestro='+d+'&Materia='+e
	}
		
</script>

<%
	DecimalFormat getformato= new DecimalFormat("##0.00;(##0.00)");

 	String codigoPersonal		= (String) session.getAttribute("codigoPersonal"); 	
	String cursoCargaId			= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");	
	String evaluacionId			= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	String maestroNombre		= (String)request.getAttribute("maestroNombre");
	String materiaNombre		= (String)request.getAttribute("materiaNombre");
	String grupoEstado			= (String)request.getAttribute("grupoEstado");
	String hora					= "07";
	String minuto				= "00";	
	CargaGrupoEvaluacion cge	= (CargaGrupoEvaluacion)request.getAttribute("cargaGrupoEvaluacion");
	
	if (cge.getFecha().length()>10){
		hora 	= cge.getFecha().split(" ")[1];
		minuto 	= hora.split(":")[1];
		hora 	= hora.split(":")[0];
	}
	
	String sResultado	= "";
	
	List<CatEstrategia> lisEstrategias		= (List<CatEstrategia>)request.getAttribute("lisEstrategias");
	
	int j=0, i=0;	
%>
<div class="container-fluid">
	<h2>Evaluations <small class="text-muted fs-6">( <%=codigoPersonal%> -  <%=maestroNombre%> - <%=materiaNombre%> )<small></h2>
	<form name="frmEvaluacion" action="grabarEvaluacion" method="post" target="_self">		
	<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">	
	<div class="alert alert-info">		
		<a class="btn btn-primary" href="metodo?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestroNombre%>&Materia=<%=materiaNombre%>"><spring:message code="aca.Regresar"/></a>		
	</div>
<% if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>
<%	}%>	
	<table class="table table-sm table-bordered" style="width:50%">  
  	<tr>
		<td width="20%"><strong>No. Evaluation</strong></td>
        <td width="80%">
        	<input name="EvaluacionId" type="text" class="form-control" id="EvaluacionId" value="<%=cge.getEvaluacionId()%>" size="3" maxlength="3" style="width:70px" readonly>		
		</td>
	</tr>
	<tr>
    	<td><b>Strategy:</b></td>
        <td>
       		<select name="EstrategiaId" id="EstrategiaId" class="form-select">
<%				
				for( i=0;i<lisEstrategias.size();i++){
					CatEstrategia estrategia = (CatEstrategia) lisEstrategias.get(i);
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
    	<td><strong>Description:</strong></td>
        <td><input name="NombreEvaluacion" type="text" class="form-control" id="NombreEvaluacion" value="<%=cge.getNombreEvaluacion()%>" size="80" maxlength="100"></td>
    </tr>
    <tr>
        <td><strong><spring:message code="aca.Fecha"/>:</strong></td>
        <td class="d-flex align-items-center">
         	<input name="Fecha" type="text" class="form-control" id="Fecha" data-date-format="yyyy/mm/dd" value="<%=cge.getFecha().substring(0,10)%>" size="10" maxlength="10" style="width:12rem">
         	&nbsp;<small class="text-muted fs-6">(YYYY/MM/DD)</small>           	
        </td>
    </tr>
    <tr>
        <td><strong>Time:</strong></td>
        <td class="d-flex align-items-center">
        	Hour:&nbsp;
         	<select name="Hora" id="Hora" class="form-select" style="width:70px;">
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
         	Minutes:&nbsp;
         	<select name="Minuto" id="Minuto" class="form-select" style="width:70px;">
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
        <td><input name="Valor" type="text" class="form-control" id="Valor" value="<%=cge.getValor()%>" size="6" maxlength="6" style="width:70px"></td>
    </tr>
    <tr>
      	<td><strong><spring:message code="aca.Tipo"/>:</strong></td>
      	<td>
			<select name="Tipo" id="Tipo" class="form-select" style="width:140px">
            	<option value="%" <%if (cge.getTipo().equals("%")) out.print("selected");%> >Percentage</option>
                <option value="P" <%if (cge.getTipo().equals("P")) out.print("selected");%>>Points</option>
                <option value="E" <%if (cge.getTipo().equals("E")) out.print("selected");%> >Extra Points</option>
			</select>
        </td>
	</tr>          
	<tr>
      	<td><strong>Submit:</strong></td>
      	<td>
			<select name="Enviar" id="Enviar" class="form-select" style="width:140px">
            	<option value="N" <%if (cge.getEnviar().equals("N")) out.print("selected");%> >None</option>
                <option value="A" <%if (cge.getEnviar().equals("A")) out.print("selected");%>>File</option>
                <option value="R" <%if (cge.getEnviar().equals("R")) out.print("selected");%> >Comment</option>
			</select>
        </td>
	</tr>    
	</table>	
    <div class="alert alert-info">    	
<% 	if ( grupoEstado.equals("1") || grupoEstado.equals("2")){%>
         <a class="btn btn-primary" href="javascript:Nuevo()"><b>New</b></a> 
         &nbsp;<a class="btn btn-primary" href="javascript:Grabar()"><b><spring:message code="aca.Grabar"/></b></a>                  
<%	}else{%>
         <font color="#000000">� Closed Subject !</font>
<%	}%>		
	</div>
	</form>	
</div>
<!-- fin de estructura -->
<script>
	jQuery('#Fecha').datepicker();
</script>