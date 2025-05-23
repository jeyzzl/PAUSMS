<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<title>TORTA DE GOOGLE.</title>
<script type="text/javascript">
function TMP(){
var tiempo=new Date();
var yapaso = Math.floor(((tiempo.getHours() * 60) + tiempo.getMinutes()) * 100 / 1440);
var aunfalta = 100 - yapaso;

var relleno = '<object data="http://chart.apis.google.com/chart?cht=p3&chd=t:';

relleno += yapaso +','+ aunfalta;

relleno += '&chs=250x100&chl=Pasaron|Faltan&chco=333333" type="text/html"></object></body>';

document.getElementById("torta").innerHTML = relleno;
}
onload = TMP;
</script>
<style type="text/css">
body{color:#ffffff; background-color:#000000;}
div{float:left;}
object{height:150px; width:300px;}
h3{font-size:300%; font-weight:500;}
p{font-weight:bold; clear:both;}
strong{font-size:150%; font-weight:500;}
</style>
</head>
<body>
<h2>Lo que queda del d&iacute;a.</h2>
<p>Muestra un gr&aacute;fico de torta con el tiempo transcurrido desde las 0 horas y 
el que falta hasta la medianoche.</p>

<div id=torta></div><h3>*</h3>

<p><strong>*</strong> Homenaje a "<em>The Remains of the Day</em>" (1993) de James Ivory.</p>
</body>
</html>