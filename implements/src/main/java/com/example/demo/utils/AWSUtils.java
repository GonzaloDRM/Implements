package com.example.demo.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.tika.mime.MimeTypeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;

import cl.apptec.mobysuite.constants.TipoImagen;
import cl.apptec.mobysuite.dto.project.amc.AMCImageDTO;
import cl.apptec.mobysuite.model.proyecto.Project;
import cl.apptec.mobysuite.utils.MimeUtils;
import cl.apptec.mobysuite.utils.project.amc.AMCUploadFile;

public class AWSUtils {

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.customer}")
    private String customer;
    
    public AMCImageDTO tempPhoto(Integer projectId, AMCImageDTO dtoImage, MultipartFile photo) throws MimeTypeException, IOException {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                        properties.getAwsCredentialsAccessKey(), properties.getAwsCredentialsSecretKey())))
                .withRegion(properties.getAwsCredentialsRegion()).build();
        
        String extension = MimeUtils.getExtensionFromMime(photo.getContentType());
        String fileName = this.getFileName(dtoImage, projectId, extension);
        
        Path path = Files.createTempFile("", fileName);
        Files.write(path, photo.getBytes());
        File file = path.toFile();

        String url = "tmp/project/" + projectId + "/imagenes/" + dtoImage.getTipo().toLowerCase() + dtoImage.getOrden() + extension;
        
        dtoImage.setExtension(extension);
        dtoImage.setUrl(url);

        s3.putObject(properties.getAwsS3BucketName(), url, file);
        AccessControlList acl = s3.getObjectAcl(properties.getAwsS3BucketName(), url);
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        s3.setObjectAcl(properties.getAwsS3BucketName(), url, acl);

        return dtoImage;
    }

    public AMCUploadFile upload(String extension, Integer projectId, AMCImageDTO dto, MultipartFile photo)
            throws IOException {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                        properties.getAwsCredentialsAccessKey(), properties.getAwsCredentialsSecretKey())))
                .withRegion(properties.getAwsCredentialsRegion()).build();

        Project project = projectService.getProjectById(projectId);
        String fileName = this.getFileName(dto, projectId, extension);

        Path path = Files.createTempFile("", fileName);
        Files.write(path, photo.getBytes());
        File file = path.toFile();

        String url = customer + "/project/" + project.getNombreProyecto().toLowerCase() + "/imagenes/"
                + dto.getTipo().toLowerCase() + dto.getOrden() + extension;

        s3.putObject(properties.getAwsS3BucketName(), url, file);
        AccessControlList acl = s3.getObjectAcl(properties.getAwsS3BucketName(), url);
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        s3.setObjectAcl(properties.getAwsS3BucketName(), url, acl);

        AMCUploadFile result = new AMCUploadFile();
        result.setUrl(url);
        return result;
    }

    private String getFileName(AMCImageDTO dto, Integer projectId, String extension) {
        String fileName = "";
        if (dto.getId() == null) {
            if (TipoImagen.CARRUSEL.name().equals(dto.getTipo()))
                fileName += "_promesaBlanco";
            else if (TipoImagen.FOTO.name().equals(dto.getTipo()))
                fileName += "_promesaVerde";
            else if (TipoImagen.LOGO.name().equals(dto.getTipo()))
                fileName += "_promesaAnexo";
            else if (TipoImagen.PORTADA.name().equals(dto.getTipo()))
                fileName += "_promesaCesantia";
            else if (TipoImagen.PRESENTACION.name().equals(dto.getTipo()))
                fileName += "_promesaEntregaInmediata";
            else if (TipoImagen.PROMOCION.name().equals(dto.getTipo()))
                fileName += "_promesaSinPomSinConstruccion";

            fileName += ("_" + customer + "_" + projectId + extension);
        } else {
            return dto.getUrl().substring(dto.getUrl().lastIndexOf('/') + 1);
        }
        return fileName;
    }
    
    public AMCImageDTO movePhoto (AMCImageDTO dto, Integer projectId, String extension) {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                        properties.getAwsCredentialsAccessKey(), properties.getAwsCredentialsSecretKey())))
                .withRegion(properties.getAwsCredentialsRegion()).build();

        Project project = projectService.getProjectById(projectId);
        
            String url = customer + "/project/" + project.getNombreProyecto().toLowerCase() + "/imagenes/"
                    + dto.getTipo().toLowerCase() + dto.getOrden() + extension;
            
            s3.copyObject(properties.getAwsS3BucketName(), dto.getUrl(), properties.getAwsS3BucketName(), url);
            
            dto.setUrl(url);
            
            AccessControlList acl = s3.getObjectAcl(properties.getAwsS3BucketName(), url);
            acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
            s3.setObjectAcl(properties.getAwsS3BucketName(), url, acl);
            
        return dto;
    }
    
    
}
