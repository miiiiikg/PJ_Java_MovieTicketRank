package mybatis;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlMapConfig {
	private static SqlSessionFactory sqlSessionFactory;
	
	static { // ? •? ë¸”ëŸ­, ?´?˜?Š¤ ë¡œë”©?‹œ 1?šŒë§? ?‹¤?–‰?¨
		String resource = "mybatis/Configuration.xml";
		try {
			Reader reader = Resources.getResourceAsReader( resource); // resourceë³??ˆ˜ ê²½ë¡œ?— ?ˆ?Š” ?ŒŒ?¼?„ ?•œ ì¤„ì”© ?½?–´?„œ reader?— ?„£?Œ
			
			if(sqlSessionFactory == null) {
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader); // ë¹Œë“œ ?Œ¨?„´
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static SqlSessionFactory getSqlSession() {
		return sqlSessionFactory;
	}
}
