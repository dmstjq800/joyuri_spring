package project.demo.image.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import project.demo.album.entity.Album;
import project.demo.album.entity.AlbumImage;
import project.demo.album.repository.AlbumImageRepository;
import project.demo.image.service.ImageService;
import project.demo.album.repository.AlbumRepository;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private final AlbumRepository albumRepository;
    private final AlbumImageRepository albumImageRepository;
    @GetMapping("/upload")
    public String fileupload() {
        return "file/fileupload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<?> FileUpload(@RequestParam("file") MultipartFile file) {

        String url = imageService.ImageUpload(file, "album/");
        Album album = Album.builder().title("hi").description("hi2").albumImages(new ArrayList<>()).build();
        albumRepository.save(album);
        AlbumImage albumImage = AlbumImage.builder().album(album).url(url).build();
        albumImageRepository.save(albumImage);
        album.getAlbumImages().add(albumImage);

        return ResponseEntity.status(HttpStatus.OK).body("file successfully uploaded");
    }

    @Value("${image.upload-dir}")
    private String uploadDir;






}
