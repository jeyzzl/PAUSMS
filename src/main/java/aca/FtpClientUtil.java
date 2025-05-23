package aca;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class FtpClientUtil {
    
    private FTPClient ftpClient;

    public FtpClientUtil() {
        ftpClient = new FTPClient();
    }

    public boolean connect(String server, int port, String user, String password) throws IOException {
        ftpClient.connect(server, port);
        return ftpClient.login(user, password);
    }

    public void disconnect() throws IOException {
        if (ftpClient.isConnected()) {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }

    public boolean uploadFile(String remoteFilePath, File localFile) throws IOException {
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        try (FileInputStream inputStream = new FileInputStream(localFile)) {
            return ftpClient.storeFile(remoteFilePath, inputStream);
        }
    }

    public boolean updateFile(String remoteFilePath, File localFile) throws IOException {
        // Deleting the existing file before uploading the new one
        ftpClient.deleteFile(remoteFilePath);
        return uploadFile(remoteFilePath, localFile);
    }

}
