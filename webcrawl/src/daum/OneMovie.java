package daum;

import java.io.IOException;
import java.text.DecimalFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import domain.MovieDTO;
import persistence.MovieDAO;

public class OneMovie {                        
		public static void main(String[] args) throws IOException {
		
		String base = "https://movie.daum.net/moviedb/grade?movieId=134091&type=netizen&page=";
		int page = 1; // 수집한 페이지수(1page = 댓글 10개)
		String url =  base + page; 
		
		int score =0, regdate = 0, count=0;
		String title = "", writer, content, basedate, subdate = "";
		
		int total = 0; // 평점을 모두 더하는 변수
		Double scoreAvg = 0.0; // 평균 평점
		
		MovieDAO mDao = new MovieDAO();
		
		// 페이지를 돌면서 댓글을 수집!
		while(true) {
			// 1페이지의 평점 10건 수집	
			Document doc = Jsoup.connect(url).get();
			Elements reply = doc.select("ul.list_netizen div.review_info");
			
						
			if(reply.isEmpty()) {
				break;
			}
			
				// 평점 1건 수집
				for (Element one : reply) {  
					count++;
					Elements movieName = doc.select("h2.tit_rel");
					title = movieName.text();
					writer = one.select("em.link_profile").text();
					score = Integer.parseInt(one.select("em.emph_grade").text());
					content = one.select("p.desc_review").text();
					
					basedate = one.select("span.info_append").text();
					subdate = basedate.substring(0,10);
					regdate = Integer.parseInt(subdate.replace(".", ""));
					
					// 누적 평점을 계산
					total += score; //total = total+ score;
					
					MovieDTO mDto = new MovieDTO(title, content, writer, score, "daum", regdate);
					
					// DB에 저장!
					mDao.addMovie(mDto);
					System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒"+count+"건 ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
					System.out.println("영화 :" + title);
					System.out.println("평점 :" + score);
					System.out.println("작성자 :" + writer);
					System.out.println("내용 : "+ content);
					System.out.println("작성일자 :" + regdate);
					
				}// for 끝
				page = page +1;
				url= base + page;
			}// while 끝
			// 평균 평점 계산
			scoreAvg = (double)total / count;
			// 소수점 첫번째 자리까지 출력(버림)
			double result = scoreAvg;
			DecimalFormat df = new DecimalFormat("0.0");
	
		
			// 수집 및 분석결과 출력
			System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒ end ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
			System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
			System.out.println("▒▒▒▒ "+ title +"Daum 영화 평점 수집 결과 ");
			System.out.println("▒▒▒▒ "+ (page-1) + "페이지에서");
			System.out.println("▒▒▒▒ "+ count +"건의 평점을 수집 완료");
			System.out.println("▒▒▒▒ 평균평점은 " + df.format(result) +"점 :)");
			System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
		
	} //메인

		
}

