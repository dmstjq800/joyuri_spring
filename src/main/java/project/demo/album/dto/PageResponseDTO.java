package project.demo.album.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import project.demo.album.entity.Album;

import java.util.List;

@Getter
@Setter
public class PageResponseDTO {
    List<AlbumDTO> albumDTOList;
    private long totalElements;
    private int totalPages;

    public PageResponseDTO(List<AlbumDTO> albumDTOList, Page<Album>  page){
        this.albumDTOList = albumDTOList;
        totalElements = page.getTotalElements();
        totalPages = page.getTotalPages();
    }
}
