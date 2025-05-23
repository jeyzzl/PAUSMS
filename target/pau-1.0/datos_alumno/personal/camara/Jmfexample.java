
package camara;

public class Jmfexample {
	public Jmfexample() {
        
        camDataSource dataSource = new camDataSource(null);
        dataSource.setMainSource();
        dataSource.makeDataSourceCloneable();
        dataSource.startProcessing();
        mainFrame frame = new mainFrame(dataSource);
        frame.setSize(340,340);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    
    public static void main(String[] args) {
        
        Jmfexample jmf = new Jmfexample();
        
    }

}