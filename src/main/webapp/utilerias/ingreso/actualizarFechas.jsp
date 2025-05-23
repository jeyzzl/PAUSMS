<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
	<script>
		function actualizar(){
			var bien = true;
			muestraImagen('1');
			$("btn").style.visibility = "hidden";
			var url = "actualizar";
			
			/*new Ajax.PeriodicalUpdater('resultado', url, {
				asynchronous:true, method: 'post', frequency: 1, decay: 2
		   	});*/
			
			new Ajax.Request(url, {
				method: "get",
				onSuccess: function(req){
					eval(req.responseText);
				},
				onFailure: function(req){
					errorMessage("Ocurri&oacute; un error al actualizar","Int&eacute;ntelo de nuevo");
				}
			});
		}
		
		function muestraImagen(num){				
			var img = $("img");
			if(num==1){
				img.src="../../imagenes/cargando.gif";
				img.width="50";
			}
			else if(num==2){
				img.src="../../imagenes/activa.png";
				img.width="50";
			}
			else if(num==3){
				img.src="../../imagenes/no.png";
				img.width="30";
			}
			else if(num==4){
				img.src="";
				img.width="0";
				$("btn").style.visibility = "";
			}
		}
		
		function muestraTexto(texto){
			$("resultado").innerHTML = texto;
		}
	</script>
</head>
<body>
<div class="container-fluid">
<h2>Actualizar Ingreso</h2>
<div class="alert alert-info">	
	<td>
		<input id="btn" onClick="actualizar();" type="button" class="btn btn-primary" style="cursor:pointer; width:100px;" value="Actualizar">&nbsp;
		<a href="prueba" class="btn btn-success">Rest</a>&nbsp; 
		<a href="consulta" class="btn btn-success">Consulta</a>
	</td>
</div>
	<table>
		<tr><td align="center"><img src="" name="img" id="img" width="0px"></td></tr>
	</table>
	<br>
	<table>
		<tr>
			<td>
				<div id='resultado' style="font-size:24px; color:green; text-align:center; background-color:transparent; border-width:0; font-family:Helvetica;"></div>
			</td>
		</tr>
	</table>
	<br>
</div>
	</body>
</html>