package project.demo.album.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import project.demo.album.entity.Album;

import java.util.List;

@Getter
@Setter
public class PageResponseDTO {
    List<AlbumResponseDTO> albumResponseDTOList;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private boolean last;

    public PageResponseDTO(List<AlbumResponseDTO> albumResponseDTOList, Page<Album> page){
        this.albumResponseDTOList = albumResponseDTOList;
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.first = page.isFirst();
        this.last = page.isLast();
    }
}
