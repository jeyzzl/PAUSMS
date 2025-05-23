<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function checa(){
		if(document.forma.dir.value=="")
			alert('Seleccione la carpeta donde se encuentran las fotos');
		else{
			var strText = new String() ;
			strText = document.forma.dir.value;
			strText = strText.replace(new RegExp(/\\/gi),'@@');
			document.forma.action="javascript:abrir('transferir.jsp?accion=0&direc="+strText+"')";
			document.forma.submit();
		}
	}
	function abrir(open){
		cy=(screen.height-120)/2;
		cx=(screen.width-400)/2;
		window.open(open,"Popup","toolbar=0, location=0, directories=0, status=0, menubar=0, scrollbars=0, resizable=1, width=400, height=120, top="+cy+", left="+cx)
	}
</script>
<body>
<br>
<div class="container-fluid">
	<h2>Subir Fotos de los Alumnos al Sistema</h2>
	<div class="alert alert-info"></div>
<table style="width:70%" class="table table-sm table-bordered">
  <tr>
    <th align='center'></th>
  </tr>
  <tr><td>
	<form enctype="multipart/form-data" action="subir" name="forma"  method="post">
        <table style="width:100%">
          <tr>
		  	<td>
				<table ><tr>
          	<td align="justify"><font face="Verdana" size="1">
				<font size="2"><br><h4>Instrucciones:</h4></font><br><br> 1. Vaya al menú <b>Herramientas</b>, Seleccione <b>Opciones de Internet</b>,
				Vaya a la ficha <b>Seguridad</b>, de clic en el boton <b>Nivel Personalizado</b>,
				Recorrase a la seccion  de <b>Controles y Complementos ActiveX</b>,
				en la opcion <b>Inicializar y activar la secuecia de comandos de los controles de ActiveX no marcados como seguros</b>
				seleccione <b>Activar</b>.<br><br>
				2. Con el boton Examinar,<b> Seleccione un solo archivo</b> de la carpeta donde se encuentran las fotos.<br><br>
          		3. Cuando este listo, presione el boton <b>Transferir</b>.	
			</font></td>
			</tr></table>
			</td>
          </tr>
          <tr>
          	<td align="center"><br><br><input size="40" accept="image/jpeg" type='file' class="form-control" id='dir' name='dir'/>
          	</td>
          </tr>
		   <tr> 
		  <th style="text-align:center;"><input class="btn btn-primary" onclick='checa()' type="button" name="bRespaldar2" value="Transferir"/></th>
		  </tr>
      </table>
</form>
</td></tr></table>	
<table style="width:70%">
<tr><td align="justify"><font face="Verdana" size="2" color="#AE2113">
		  			<br>Nota: Siga las Instrucciones como se pide. Cuando se esten transfiriendo las fotos 
		  			<b>no de clic en otra ventana! </b>de lo contrario la transferencia será interrumpida.
					</font>
</td></tr>
</table>
</div>
</body>
