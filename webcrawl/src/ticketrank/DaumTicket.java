package ticketrank;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import domain.MovieDTO;
import persistence.MovieDAO;

public class DaumTicket {
	String base = "";
	int page = 1;
	String url = "";
	int count = 0; // ��ü ��ۼ�
	int total = 0; // ������ ��� ���ϴ� ����
	MovieDAO mDao = new MovieDAO();
	public TicketDTO daumCrawler(String movie, String code) throws IOException {
		base = "https://movie.daum.net/moviedb/grade?movieId="+code+"&type=netizen&page=";
		url = base+page;
		System.out.println("�ƢƢƢ�DAUM START�ƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢ�");
		
		int score =0, regdate = 0, count=0;
		String title = "", writer, content, basedate, subdate = "";
		
		while(true) {
			// 1�������� ���� 10�� ����	
			Document doc = Jsoup.connect(url).get();
			Elements reply = doc.select("ul.list_netizen div.review_info");
			
						
			if(reply.isEmpty()) {
				break;
			} 
			
				// ���� 1�� ����
				for (Element one : reply) { 
					count++;
					writer = one.select("em.link_profile").text();
					score = Integer.parseInt(one.select("em.emph_grade").text());
					content = one.select("p.desc_review").text();
					basedate = one.select("span.info_append").text();
					subdate = basedate.substring(0,10);
					regdate = Integer.parseInt(subdate.replace(".", ""));
					
					// ���� ������ ���
					total += score; //total = total+ score;
					
					MovieDTO mDto = new MovieDTO(movie, content, writer, score, "daum", regdate);
					
					// DB�� ����!
					mDao.addMovie(mDto);
					System.out.println("�ƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢ�"+count+"�� �ƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢ�");
					System.out.println("��ȭ :" + movie);
					System.out.println("���� :" + score);
					System.out.println("�ۼ��� :" + writer);
					System.out.println("���� : "+ content);
					System.out.println("�ۼ����� :" + regdate);
					
				}// for ��
				page = page +1;
				url= base + page;
			}
		System.out.println("�ƢƢƢ�DAUM END�ƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢ�");
		TicketDTO tDto = new TicketDTO(count, total);	
		return tDto;
		
	}

}
