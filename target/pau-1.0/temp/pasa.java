package temp;
import java.io.FileReader;
import java.io.IOException;

public class pasa{
	public static void main(String a[]){
		FileReader entrada=null;
        StringBuffer str=new StringBuffer();
        try  {
           entrada=new FileReader("Prerre.txt");
           int c;
		   char b;
		   int contador=0;
           while((c=entrada.read())!=-1){
			   b = (char)c;
			   if(b == '\n'){
				   int cc,ccc;
				   char bb,bbb;
				   contador = 0;
				   while((cc=entrada.read())!=-1){
					   bb = (char)cc;
					   if(bb == '"'){
						   contador +=1;
						   for(int i = 1; i <= 8; i++){
							   if((ccc=entrada.read()) != -1){
								   bbb = (char)ccc;
								   if(bbb != '"')
									   str.append((char)c);
								   else
									   if(i < 8 && contador != 2){
										   for(int j = 1; j <= (8-i); j++){
											   str.append('*');
										   }
										   i = 9;
									   }
							   }
						   }
					   }
					   if(contador == 2){
						   //hay que grabar en la BD el string
						   while((ccc=entrada.read())!=-1){
							   bbb = (char)ccc;
							   //hay que leer la tercer columna
							   //y grabarla en un string y guardarla en la BD
						   }
					   }
					   
					   
				   }
			   }			   
           }
		   /*
           System.out.println(str);
           System.out.println("--------------------------------------");
           */	
		
		}catch (IOException ex) {
           System.out.println(ex);
      }

	}
}