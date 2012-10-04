package org.isatools.isacreator.gs;

import org.isatools.isacreator.io.DataManager;

import org.genomespace.client.DataManagerClient;
import org.genomespace.client.GsSession;
import org.genomespace.datamanager.core.GSDirectoryListing;
import org.genomespace.datamanager.core.GSFileMetadata;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by the ISATeam.
 * User: agbeltran
 * Date: 26/09/2012
 * Time: 14:41
 *
 * Data Manager for GenomeSpace
 *
 * @author <a href="mailto:alejandra.gonzalez.beltran@gmail.com">Alejandra Gonzalez-Beltran</a>
 */
public class GSDataManager {

    private GsSession gsSession = null;

    public GSDataManager(GsSession session){
        gsSession = session;
    }
    /**
     * List files in given directory
     *
     * @param dirPath
     */
    public List<String> ls(String dirPath){
        DataManagerClient dmClient = gsSession.getDataManagerClient();
        GSDirectoryListing dirListing = dmClient.list(dirPath);
        List<GSFileMetadata> fileMetadataList = dirListing.getContents();
        List<String> listing = new ArrayList<String>();
        for(GSFileMetadata fileMetadata:fileMetadataList){
            listing.add(fileMetadata.getName());
        }
        return listing;
    }

    /**
     * Get InputStreams for all the files in a directory
     *
     * This doesn't work at the moment because there is a restriction of two open concurrent connections at a time in GS.
     *
     * @param dirPath
     * @return
     */
    public List<InputStream> lsInputStreams(String dirPath) {
        DataManagerClient dmClient = gsSession.getDataManagerClient();
        GSDirectoryListing dirListing = dmClient.list(dirPath);
        List<GSFileMetadata> fileMetadataList = dirListing.getContents();
        List<InputStream> listing = new ArrayList<InputStream>();
        for(GSFileMetadata fileMetadata:fileMetadataList){
            System.out.println("fileMetadata="+fileMetadata);
            InputStream is = dmClient.getInputStream(fileMetadata);
            listing.add(is);
        }
        return listing;
    }


    /**
     * List files in home directory
     * @param username
     */
    public void lsHome(String username){
        DataManagerClient dmClient = gsSession.getDataManagerClient();
        GSDirectoryListing homeDirInfo = dmClient.listDefaultDirectory();
    }


    public GSFileMetadata getFileMetadata(String filePath){
        DataManagerClient dmClient = gsSession.getDataManagerClient();
        GSFileMetadata fileMetadata = dmClient.getMetadata(filePath);
        return fileMetadata;
    }

    public boolean uploadFiles() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean downloadFiles(GSFileMetadata fileToDownload, File localTargetFile) {
        DataManagerClient dmClient = gsSession.getDataManagerClient();

        GSDirectoryListing dirListing = dmClient.listDefaultDirectory();

        dmClient.downloadFile(fileToDownload, localTargetFile, true);
        return true;
    }

    public boolean downloadAllFilesFromDirectory(String dirPath, String localDirPath) {

        DataManagerClient dmClient = gsSession.getDataManagerClient();
        GSDirectoryListing dirListing = dmClient.list(dirPath);
        List<GSFileMetadata> fileMetadataList = dirListing.getContents();
        for(GSFileMetadata fileToDownload: fileMetadataList){
            String localFilePath = localDirPath+fileToDownload.getName();
            File localTargetFile = new File(localFilePath);
            dmClient.downloadFile(fileToDownload, localTargetFile,true);
        }
        return true;

    }

    public boolean mkDir() {
        return false;
    }

    public void ls() {

    }

}
