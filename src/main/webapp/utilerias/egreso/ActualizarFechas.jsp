<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
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
		<table style="margin: 0 auto;">
			<tr><td><font size="5">Actualizar ingreso</font></td></tr>
		</table>
		<br>
		<br>
		<table style="margin: 0 auto;">
			<tr><td align="center"><img src="" name="img" id="img" width="0px"></td></tr>
		</table>
		<br>
		<table style="margin: 0 auto;">
			<tr>
				<td>
					<div id='resultado' style="font-size:24px; color:green; text-align:center; background-color:transparent; border-width:0; font-family:Helvetica;"></div>
				</td>
			</tr>
		</table>
		<br>
		<br>
		<table style="margin: 0 auto;">
			<tr><td><input id="btn" onClick="actualizar();" type="button" class="btn btn-primary btn-larger" style="cursor:pointer;height:50px;width:150px;" value="Actualizar" ></td></tr>
		</table>
	</body>
</html>