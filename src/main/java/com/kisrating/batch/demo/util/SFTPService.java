package com.kisrating.batch.demo.util;

import com.jcraft.jsch.*;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@UtilityClass
public class SFTPService {

    /**
     * 파일 업로드
     * @param serverAddress
     * @param serverPort
     * @param username
     * @param password
     * @param file
     * @param destinationPath
     * @throws JSchException
     * @throws SftpException
     * @throws IOException
     */
    public void uploadFile(final String serverAddress,
                           final Integer serverPort,
                           final String username,
                           final String password,
                           final File file,
                           final String destinationPath
    ) throws JSchException, SftpException, IOException {
        Session session = null;
        ChannelSftp channelSftp = null;
        try {

            JSch jsch = new JSch();
            session = jsch.getSession(username, serverAddress, serverPort);
            session.setPassword(password);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            channelSftp.cd(destinationPath);
            channelSftp.put(new FileInputStream(file), file.getName(), ChannelSftp.OVERWRITE);

            channelSftp.disconnect();
            session.disconnect();
        } finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }

            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    /**
     *
     * @param serverAddress
     * @param serverPort
     * @param username
     * @param password
     * @param sourcePath
     * @param downloadPath
     * @throws JSchException
     * @throws SftpException
     * @throws IOException
     */
    public void downloadFile(  final String serverAddress,
                               final Integer serverPort,
                               final String username,
                               final String password,
                               final String sourcePath,
                               final String downloadPath
    ) throws JSchException, SftpException, IOException {
        Session session = null;
        ChannelSftp channelSftp = null;
        try {

            JSch jsch = new JSch();
            session = jsch.getSession(username, serverAddress, serverPort);
            session.setPassword(password);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            channelSftp.cd(sourcePath);
            channelSftp.get(sourcePath , downloadPath);

            channelSftp.disconnect();
            session.disconnect();
        } finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }

            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }
}
