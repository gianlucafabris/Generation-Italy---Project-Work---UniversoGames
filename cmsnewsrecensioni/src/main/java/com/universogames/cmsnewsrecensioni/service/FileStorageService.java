package com.universogames.cmsnewsrecensioni.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Questo servizio ha il compito di salvare e caricare i file dall'disco fisso
 */
@Service
public class FileStorageService {
    /**
     * Percorso dove andremo a salvare i file
     */
    private final Path fileStorageLocationSrc;
    private final Path fileStorageLocationTarget;

    /**
     *
     * @param uploadDir corrisponde alla stringa che descrive il percorso della cartella
     *                  Lo mettiamo in application.properties per comodità
     */
    public FileStorageService(@Value("${file.uploadsrc}") String uploadDirSrc, @Value("${file.uploadtarget}") String uploadDirTarget) {
		fileStorageLocationSrc = Paths.get(uploadDirSrc).toAbsolutePath().normalize();
		fileStorageLocationTarget = Paths.get(uploadDirTarget).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocationSrc);
            Files.createDirectories(this.fileStorageLocationTarget);
        } catch (Exception e) {
            System.err.println("Non è stato possibile creare la cartella dove uploadare i file");
        }
    }

    /**
     * Metodo che salva nel percorso definito il file che arriva da parametro
     * @param file Dal controller arriverà un oggetto di tipo MultiPartFile
     * @return Il nome del file (Il nome può essere poi utilizzato per essere, ad esempio, salvato nel DB
     */
    public boolean salvaFile(MultipartFile file) {
		
		if (file != null) {
	        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	        try {
	            // A partire dal percorso dove salvo i file ricavo il percorso che avrà il file
	            Path targetLocationSrc = this.fileStorageLocationSrc.resolve(fileName);
	            Path targetLocationTarget = this.fileStorageLocationTarget.resolve(fileName);
	            // Utilizzo Files per copiare il file arrivato da parametro nella specifica cartella
	            // vado a sovrascrivere se esiste già
	            Files.copy(file.getInputStream(), targetLocationSrc, StandardCopyOption.REPLACE_EXISTING);
	            Files.copy(file.getInputStream(), targetLocationTarget, StandardCopyOption.REPLACE_EXISTING);
	            
	            return true;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
		}
		
		return false;
    }
    
    /**
     * A partire dal nome del file carico, se esiste dal disco fisso
     * @param fileName
     * @return
     */
//    public Resource loadFile(String fileName) {
//        try {
//            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//
//            if (resource.exists()) {
//                return resource;
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
