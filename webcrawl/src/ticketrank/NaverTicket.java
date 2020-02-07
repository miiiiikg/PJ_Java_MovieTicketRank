package ticketrank;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import domain.MovieDTO;
import persistence.MovieDAO;

public class NaverTicket {
	String base = "";
	int page = 1; // 수집한 페이지수(1page = 댓글 10개)
	String url = "";
	int count = 0;
	int total = 0;
	String compare = "";
	MovieDAO mDao = new MovieDAO();
	
	// 네이버 영화 댓글(평점)을 수집하는 메서드
	public TicketDTO naverCrawler(String movie, String code) throws IOException {
		base = "https://movie.naver.com/movie/bi/mi/pointWriteFormList.nhn?code="+code+"&type=after&isActualPointWriteExecute=false&isMileageSubscriptionAlready=false&isMileageSubscriptionReject=false&page=";
		url = base +page;
		
		int score = 0, regdate = 0, count = 0;
		String title = "", writer ="", content = "", basedate="", compare ="", subdate = "";
		
		System.out.println("▒▒▒▒NAVER START▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
		
		label:while(true) {
			Document doc = Jsoup.connect(url).get();
			Elements reply = doc.select("div.score_result li");
		
			
			
			if(reply.equals(writer)) {
				break label;
			}
			// for(int i = 0; i<reply.size(); i++
			// content = reply.get(i).select
			for(int i = 0; i< reply.size(); i++) {
				count++;
				// Elements movieName = doc.select("");
				// title = movieName.text();
				writer = reply.select("div.score_reple dl span").text(); // 작성자
				score = Integer.parseInt(reply.get(i).select("div.star_score > em").text()); // 점수
				content = reply.select("div.score_reple > p").text(); // 내용
				basedate =reply.select("div.score_reple dt em:last-child").text();
//				compare = Integer(reply.get(i).select(".score_reple dt em:nth-child()").text());
				
				subdate = basedate.substring(0,10); 
				regdate = Integer.parseInt(subdate.replace(".","")); // 날짜입력
				
				if(i == 0) {
					if(compare.equals(writer)){
						break label;
					} else {
						compare=writer;
					}
					
				} 
				total += score;
				
				MovieDTO mDto = new MovieDTO(movie, content, writer, score, "naver", regdate);
				
				mDao.addMovie(mDto);
				System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒"+count+"건 ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
				System.out.println("영화: " + movie);
				System.out.println("평점: " + score);
				System.out.println("작성자: " + writer);
				System.out.println("내용: " + content);
				System.out.println("작성일자: " +regdate);
				
			}// for끝
			
			page = page + 1;
			url = base + page;
		
		}// while 끝
		
		System.out.println("▒▒▒▒NAVER END▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
		// TicketMain으로 댓글수, 평점의 합을 return
		TicketDTO tDto = new TicketDTO(count, total);	
		return tDto;
	}



}
