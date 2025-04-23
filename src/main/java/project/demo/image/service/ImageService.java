package project.demo.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.demo.album.repository.AlbumImageRepository;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final S3Client s3Client;



    @Value("${aws.s3.bucket}")
    private String bucketName;

    public String ImageUpload(MultipartFile file, String path, long id) throws IOException {

        String name = path.replace("/", "");

        if(file.isEmpty()){return null;}
//        try {
//            String oriFilename = file.getOriginalFilename();
//            String newFilename = name + id + oriFilename.substring(oriFilename.lastIndexOf("."));
//            File dest = new File(filePath + newFilename);
//            file.transferTo(dest);
//            return "/images/" + path + newFilename;
//        }catch (Exception e){
//            return e.getMessage();
//        }
        try {
            String oriFilename = file.getOriginalFilename();
            String newFilename = name + id + oriFilename.substring(oriFilename.lastIndexOf("."));
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key("images/" + path + newFilename)
                    .acl("public-read")
                    .contentType(file.getContentType())
                    .build();
            s3Client.putObject(putOb, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            return "/images/" + path + newFilename;
        }catch (Exception e){
            return e.getMessage();
        }
    }

}
