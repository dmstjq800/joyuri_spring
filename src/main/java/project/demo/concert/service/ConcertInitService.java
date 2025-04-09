package project.demo.concert.service;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import project.demo.concert.entity.Concert;
import project.demo.concert.repository.ConcertRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertInitService {
    private final ConcertRepository concertRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void concertInit() {
        if(concertRepository.count() > 0){ return;}
        List<Concert> concertsToSave = new ArrayList<>();
        concertsToSave.add(new Concert("2022 UNI-KON", "올림픽공원 SK올림픽핸드볼경기장", LocalDate.of(2022, 7, 3)));
        concertsToSave.add(new Concert("Soundberry Festa '22", "COEX", LocalDate.of(2022, 7, 23)));
        concertsToSave.add(new Concert("광복 77주년 - 600년의 길이 열리다", "청와대 본관 야외 특설무대", LocalDate.of(2022, 8, 13)));
        concertsToSave.add(new Concert("Someday Festival 2022", "난지한강공원", LocalDate.of(2022, 9, 3)));
        concertsToSave.add(new Concert("2022 INK INCHEON K-POP CONCERT", "인천 문학경기장 주경기장", LocalDate.of(2022, 10, 1)));
        concertsToSave.add(new Concert("2022 Seoul Music Festival", "노들섬", LocalDate.of(2022, 10, 13)));
        concertsToSave.add(new Concert("KCON 2022 JAPAN", "도쿄 아리아케 아레나", LocalDate.of(2022, 10, 16)));
        concertsToSave.add(new Concert("Mnet Japan Fan's Choice Awards", "시나가와 프린스 스텔라볼", LocalDate.of(2022, 12, 19)));

        concertsToSave.add(new Concert("2023 석림 태울제", "KAIST", LocalDate.of(2023, 5, 18)));
        concertsToSave.add(new Concert("2023 경희대학교 서울캠퍼스 봄 대동제 MASTERPEACE", "경희대학교", LocalDate.of(2023, 5, 25)));
        concertsToSave.add(new Concert("2023 국방TV 위문열차", "육군 군수사령부", LocalDate.of(2023, 6, 20)));
        concertsToSave.add(new Concert("2023 울산 서머 페스티벌", "울산 종합운동장 보조경기장", LocalDate.of(2023, 8, 7)));
        concertsToSave.add(new Concert("2023 새만금 세계스카우트 잼버리 K-POP SUPER LIVE", "서울 월드컵 경기장", LocalDate.of(2023, 8, 11)));
        concertsToSave.add(new Concert("2023 천안 K-컬처 박람회", "독립기념관", LocalDate.of(2023, 8, 14)));
        concertsToSave.add(new Concert("2023 대백제전", "백제문화단지", LocalDate.of(2023, 9, 23)));

        concertsToSave.add(new Concert("덕성여자대학교 2024 근화제", "덕성여자대학교", LocalDate.of(2024, 5, 28)));
        concertsToSave.add(new Concert("조선대학교 2024 GRACIA", "조선대학교", LocalDate.of(2024, 5, 28)));
        concertsToSave.add(new Concert("Seoul Park Music Festival", "올림픽공원", LocalDate.of(2024, 6, 30)));
        concertsToSave.add(new Concert("KCON LA 2024 (Day 1)", "LA 컨벤션센터", LocalDate.of(2024, 7, 26)));
        concertsToSave.add(new Concert("KCON LA 2024 (Day 2)", "크립토닷컴 아레나", LocalDate.of(2024, 7, 27)));
        concertsToSave.add(new Concert("안산대학교 2024년도 석학제", "안산대학교", LocalDate.of(2024, 9, 27)));
        concertsToSave.add(new Concert("경상국립대학교 2024 (PIESTA)", "경상국립대학교", LocalDate.of(2024, 10, 17)));
        concertsToSave.add(new Concert("충남대학교 2024 백마 대동제 <ACCESSIO: 비상>", "충남대학교", LocalDate.of(2024, 10, 29)));
        concertsToSave.add(new Concert("2024 MAMA AWARDS", "교세라 돔 오사카", LocalDate.of(2024, 11, 22)));
        concertsToSave.add(new Concert("제9회 아시아 아티스트 어워즈", "태국 방콕 임팩트 챌린저 홀 1-2", LocalDate.of(2024, 12, 27)));

        concertsToSave.add(new Concert("2025 JOYURI FAN-CON ‘Episode 25’", "블루스퀘어 SOL트래블홀", LocalDate.of(2025, 4, 27)));
        concertRepository.saveAll(concertsToSave);

    }




}
