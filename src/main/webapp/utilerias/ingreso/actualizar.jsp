<%
	int rows = (Integer) request.getAttribute("rows");
	if (rows>0){
%>
		bien = true;
<%		
	}
%>
	if(bien) 
		muestraImagen('2');
	else 
		muestraImagen('3');
		
	setTimeout("muestraImagen('4');", 3500);