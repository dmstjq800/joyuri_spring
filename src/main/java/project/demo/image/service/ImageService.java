package project.demo.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.demo.album.repository.AlbumImageRepository;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private String path;
    private final AlbumImageRepository albumImageRepository;

    @Value("${image.upload-dir}")
    String url;

    public String ImageUpload(MultipartFile file, String path) {
        String filePath = url.concat(path);

        if(file.isEmpty()){return null;}
        try {
            String oriFilename = file.getOriginalFilename();
            String newFilename = UUID.randomUUID().toString() + oriFilename.substring(oriFilename.lastIndexOf("."));
            File dest = new File(filePath + newFilename);
            file.transferTo(dest);

//            AlbumImage albumImage = AlbumImage.builder().url(newFilename).album(album).build();
//            imageRepository.save(albumImage);
//            album.getAlbumImages().add(albumImage);
            return "/images/" + path + newFilename;
        }catch (Exception e){
            return e.getMessage();
        }
    }

}
