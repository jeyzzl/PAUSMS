package aca;

import com.jcraft.jsch.*;

public class SftpClientUtil {

    public static void uploadFile(String host, int port, String username, String password,
                                 String localFilePath, String remoteFilePath) throws JSchException, SftpException {
        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp channelSftp = null;

        // Enable debugging
        JSch.setLogger(new com.jcraft.jsch.Logger() {
            public void log(int level, String message) {
                System.out.println("JSch: " + message);
            }
            public boolean isEnabled(int level) {
                return true;
            }
        });

        try {
            // Create a session
            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no"); // Disable host key checking
            session.connect();

            // Open an SFTP channel
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            // Upload the file
            channelSftp.put(localFilePath, remoteFilePath);
            System.out.println("File uploaded successfully.");
        } finally {
            // Disconnect the channel and session
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }
}