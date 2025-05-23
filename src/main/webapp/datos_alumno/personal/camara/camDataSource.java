package camara;

import java.io.*;
import java.util.*;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.media.*;
import javax.media.protocol.*;
import javax.media.control.FormatControl;
import javax.media.format.RGBFormat;
import javax.media.format.VideoFormat;

public class camDataSource {
    
    private Component parent;
    
    private DataSource mainCamSource;
    private MediaLocator ml;
    private Processor processor;
    private boolean processing;
    
    public camDataSource(Component parent) {
        this.parent = parent;
        setProcessing(false);
    }
    
    public void setMainSource(){
        setProcessing(false);
        VideoFormat vidformat = new VideoFormat(VideoFormat.YUV);
        ArrayList devices = CaptureDeviceManager.getDeviceList(vidformat);
        CaptureDeviceInfo di = null;
        FormatControl       formatControls [];
        Format              formats [];

        if (devices.size() > 0) di = (CaptureDeviceInfo) devices.get(0);
        else {
            JOptionPane.showMessageDialog(parent, 
               "Your camera is not connected", "No webcam found", JOptionPane.WARNING_MESSAGE);
            return;
        }
       
        try {
            ml = di.getLocator();
            setMainCamSource(Manager.createDataSource(ml));
            formatControls = ((CaptureDevice) mainCamSource).getFormatControls();
            formats = formatControls[0].getSupportedFormats();
            String formato = "";
            int numero=-1;
            for (int i=0;i<formats.length;i++){
            	if (formats[i].toString().equals("RGB, 640x480, Length=921600, 24-bit, Masks=3:2:1, PixelStride=3, LineStride=1920, Flipped")){
            		System.out.println("Formato encontrado en "+String.valueOf(i));
            		numero=i;
            	}
            }
            if (numero==-1){
            	System.out.println("Formato no encontrado...");
            	numero=0;
            }
            System.out.println(formatControls[0].setFormat(formats[numero]));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, 
               "Exception locating media: " + e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
    }

    public void makeDataSourceCloneable(){
        // turn our data source to a cloneable data source
        setMainCamSource(Manager.createCloneableDataSource(getMainCamSource()));
        
    }
    
    public void startProcessing(){
        
        try{
            processor = Manager.createProcessor(getMainCamSource());
        
        }catch (IOException e) {
            JOptionPane.showMessageDialog(parent, 
               "IO Exception creating processor: " + e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }catch (NoProcessorException e) {
            JOptionPane.showMessageDialog(parent, 
               "Exception creating processor: " + e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        camStateHelper playhelper = new camStateHelper(processor);
        if(!playhelper.configure(10000)){
            JOptionPane.showMessageDialog(parent, 
               "cannot configure processor", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        processor.setContentDescriptor(null);
        if(!playhelper.realize(10000)){
            JOptionPane.showMessageDialog(parent, 
               "cannot realize processor", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
       // In order for or your clones to start, you must start the original source
        
        processor.start();
        setProcessing(true);
    }
    public void stop(){
    	System.out.println("...Deteniendo PROCESSOR");
    	processor.stop();
    	System.out.println(": parado");
    	setProcessing(false);
    	processor.close();
    	System.out.println(": cerrado");
    	processor.deallocate();
    	System.out.println(": desalojado");
    	mainCamSource.disconnect();
    	System.out.println(": desconectado");
    	
    }
    public DataSource cloneCamSource(){
        if(!getProcessing()) setMainSource();
        return ((SourceCloneable)getMainCamSource()).createClone();
    }
    
    public DataSource getMainCamSource(){
        return mainCamSource;
    }
    
    public void setMainCamSource(DataSource mainCamSource){
        this.mainCamSource = mainCamSource;
    }
    
    public void setMl(MediaLocator ml){
        this.ml = ml;
    }
    
    public MediaLocator getMl(){
        return ml;
    }
    
    public boolean getProcessing(){
        return processing;
    }
    
    public void setProcessing(boolean processing){
        this.processing = processing;
        
    }
    
    public void setParent(Component parent){
        this.parent = parent;
    }
    
    public Component getParent(){
        return parent;
    }
}