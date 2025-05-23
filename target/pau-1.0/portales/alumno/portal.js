	function get_xmlhttp() {
		try {
  			xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
 		} catch (e) {
  			try {
   				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
  			} catch (E) {
   				xmlhttp = false;
  			}
 		}
		if (!xmlhttp && typeof XMLHttpRequest!='undefined') {
 			 xmlhttp = new XMLHttpRequest();
		}
		return xmlhttp
	}

	function enviar(xmlhttp,metodo,url,async,callback) {
 		xmlhttp.open(metodo, url,async);
 		xmlhttp.onreadystatechange=function() {
  			if (xmlhttp.readyState==4) {
   				callback(xmlhttp);
  			}
 		}
 		xmlhttp.send(null);
	}
	
	function verificaArchivo() {
		xmlhttp=get_xmlhttp();
		var archivo=document.getElementById("archivo").value;
		enviar(xmlhttp,"POST","verificaArchivo?archivo="+archivo,true,respuesta);
	}
	
	function respuesta(xmlhttp) {
		val=""
		for(i in xmlhttp) {
			try {
				val+="xmlhttp."+i+"="+xmlhttp[i]+"\n";
			} catch(e) {}
		}
		if(xmlhttp.status==404) {
			alert("esta pagina no existe");
		}
		document.getElementById("temp").innerHTML=xmlhttp.responseText;
		var msg=trim(document.getElementById("temp").innerHTML);
		if(msg=="Enviando..."){
			document.forma.submit();
		}
	}
	
	function trim(texto){
		return texto.replace(/^\s*|\s*$/g,"")
	}
