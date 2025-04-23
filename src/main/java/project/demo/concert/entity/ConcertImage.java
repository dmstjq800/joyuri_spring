//package project.demo.concert.entity;
//
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import project.demo.album.entity.Album;
//
//@Entity
//@Getter
//@Setter
//public class ConcertImage {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String url;
//    @ManyToOne
//    @JoinColumn(name = "concert_id")
//    @JsonIgnore
//    private Concert concert;
//    public void updateUrl(String url) {
//        this.url = url;
//    }
//}
