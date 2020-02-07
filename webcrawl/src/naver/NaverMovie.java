package naver;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.text.AbstractDocument.Content;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NaverMovie {
	public static void main(String[] args) throws IOException {
		
	String base = "https://movie.naver.com/movie/bi/mi/pointWriteFormList.nhn?code=191431&type=after&isActualPointWriteExecute=false&isMileageSubscriptionAlready=false&isMileageSubscriptionReject=false&page=";
	
	int page = 1;
	String url = base + page;
	
	int score = 0, regdate = 0, count = 0;
	String title = "", writer, content, basedate, subdate = "";
	
	int total = 0;
	double scoreAvg = 0.0;
	
	while(true) {
		Document doc = Jsoup.connect(url).get();
		Elements reply = doc.select("div.score_result li"); 
	
		
		if(reply.isEmpty()) {
			break;
		}
		// for(int i = 0; i<reply.size(); i++
		// content = reply.get(i).select
		for(Element one : reply) {
			count++;
			// Elements movieName = doc.select("");
			// title = movieName.text();
			writer = one.select("div.score_reple dl span").text(); // 작성자
			score = Integer.parseInt(one.select("div.star_score > em").text()); // 점수
			content = one.select("div.score_reple > p").text(); // 내용
			basedate = one.select("div.score_reple dt em:last-child").text();
			subdate = basedate.substring(0,10); 
			regdate = Integer.parseInt(subdate.replace(".","")); // 날짜입력
			
			total += score;
		
			System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒"+count+"건 ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
			System.out.println("영화: "+title);
			System.out.println("평점: "+score);
			System.out.println("작성자 : "+ writer);
			System.out.println("내용: " + content);
			System.out.println("작성일자: " +regdate);
			
		}// for끝
		
		page = page + 1;
		url = base + page;
	
	}// while 끝
	
	scoreAvg = (double)total / count;
	// 소수점 첫번째 자리까지 출력(버림)
	double result = scoreAvg;
	DecimalFormat df = new DecimalFormat("0.0");
	
	System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒ end ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
	System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
	System.out.println("▒▒▒▒ "+ title +"Naver 영화 평점 수집 결과 ");
	System.out.println("▒▒▒▒ "+ (page-1) + "페이지에서");
	System.out.println("▒▒▒▒ "+ count +"건의 평점을 수집 완료");
	System.out.println("▒▒▒▒ 평균평점은 " + df.format(result) +"점 :)");
	System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
	
	}// 메인
	
	
	
}
