package cf.spacetaste.backend.service;

import cf.spacetaste.backend.mapper.PhotoMapper;
import cf.spacetaste.backend.model.PhotoModel;
import cf.spacetaste.backend.model.UserModel;
import cf.spacetaste.backend.properties.ServerProperties;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class PhotoService {

    private final PhotoMapper photoMapper;
    private final ServerProperties serverProperties;
    private final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
    private final String dataDir = System.getenv("DATA_DIR");

    public PhotoModel createPhotoWithURL(String url) {
        byte[] photoData;

        try {
            var req = new Request.Builder()
                    .url(url)
                    .build();
            var res = okHttpClient.newCall(req).execute();
            if (!res.isSuccessful() || res.body() == null)
                return null;

            photoData = res.body().bytes();
            res.body().close();
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }

        return createPhotoWithBytes(photoData);
    }

    public PhotoModel createPhotoWithBytes(byte[] data) {
        PhotoModel photo = new PhotoModel();
        if (photoMapper.create(photo) <= 0)
            return null;

        try {
            File outerDir = new File(dataDir, "photo");
            if (!outerDir.isDirectory()) {
                if (!outerDir.mkdirs())
                    throw new IOException("Failed to create outer directory");
            }

            File f = new File(outerDir, Integer.toString(photo.getPhotoId()));
            try (FileOutputStream fout = new FileOutputStream(f)) {
                fout.write(data);
            }
        } catch(IOException e) {
            e.printStackTrace();
            photoMapper.delete(photo);
            return null;
        }

        return photo;
    }

    public byte[] readPhoto(PhotoModel photo) {
        File f = new File(new File(dataDir, "photo"), Integer.toString(photo.getPhotoId()));

        try (FileInputStream fin = new FileInputStream(f)) {
            return fin.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PhotoModel getFromId(int id) {
        if (id <= 0)
            return null;
        return new PhotoModel(id);
    }

    public String makeUrl(PhotoModel photo) {
        if (photo == null || photo.getPhotoId() <= 0)
            return null;

        return serverProperties.getBaseUrl() + "/photo/" + photo.getPhotoId();
    }
}
