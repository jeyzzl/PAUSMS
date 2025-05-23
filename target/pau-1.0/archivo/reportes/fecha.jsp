<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<head>
	<script type="text/javascript" src="../../js/popcalendar.js"></script>
	<script type="text/javascript">
		function revisaFecha(){
			if(document.frmpersonal.Fecha.value != ""){
				return true;
			}else
				alert("Enter the date of the permit to view it");
			return false
		}
	</script>
</head>
<div class="container-fluid">
	<h1>Students with pending documents</h1>
	<div class="alert alert-info d-flex align-items-center">
		<form name="frmpersonal" action="reporte" method="post">
		<a href="menu" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		Date of Permit:
		<input name="Fecha" type="text" id="Fecha" class="form-contol" size="12" maxlength="10" data-date-format="dd/mm/yyyy" value="" style="width:200px;">&nbsp;
		<img class="button" alt="calendario" id="fotoFecha" src="../../imagenes/calendario.gif" onClick="javascript:showCalendar(this, document.getElementById('Fecha'), 'dd/mm/yyyy',null,1,-1,-1);"> (DD/MM/YYYY)&nbsp;&nbsp;
		<input type="submit" class="btn btn-primary" value="Accept" onclick="return revisaFecha();">
		</form>		
	</div>	
</div>
<script>
	jQuery('#Fecha').datepicker();
</script>