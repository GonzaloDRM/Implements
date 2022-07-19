package com.example.demo.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;

public class MimeUtils {

    public static String getMimeFromInputStream(InputStream is) throws IOException {
        TikaConfig config = TikaConfig.getDefaultConfig();
        Metadata metadata = new Metadata();
        MediaType mediaType = config.getDetector().detect(is, metadata);
        return mediaType.getType() + "/" + mediaType.getSubtype();
    }
    public static String getExtensionFromMime(String mime) throws MimeTypeException {
        TikaConfig config = TikaConfig.getDefaultConfig();
        MimeType mimeType = config.getMimeRepository().forName(mime);
        return mimeType.getExtension();
    }
}
