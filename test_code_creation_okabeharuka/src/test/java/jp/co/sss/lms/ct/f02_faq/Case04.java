package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		
		goTo("http://localhost:8080/lms");		
		
		assertEquals("ログイン | LMS", webDriver.getTitle());
		
		getEvidence(new Object(){});
		
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		
		//初回ログイン済みの受講生ユーザーを入力してテスト
		webDriver.findElement(By.name("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.name("password")).sendKeys("StudentAA001");		
		webDriver.findElement(By.className("btn-primary")).click();		
		
		//タイトルタグの記述が一致しているか確認
		assertEquals("コース詳細 | LMS", webDriver.getTitle());	
		
		getEvidence(new Object(){});			
		
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		
		//ヘッダー内にある「機能」リンクをクリック
		webDriver.findElement(By.linkText("機能")).click();	
		
		//「ヘルプ」リンクが出てくるまで待ち処理
		visibilityTimeout(By.linkText("ヘルプ"), 10);
		
		//「ヘルプ」リンクをクリック
		webDriver.findElement(By.linkText("ヘルプ")).click();	
		
		//ページロードのタイムアウト処理
		pageLoadTimeout(10);
		
		//タイトルタグの記述が一致しているか確認
		assertEquals("ヘルプ | LMS", webDriver.getTitle());	
		
		getEvidence(new Object(){});
		
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		
		//「よくある質問」リンクをクリック
		webDriver.findElement(By.linkText("よくある質問")).click();	
		
		//現在の各タブの番号をすべて取得
		List<String> handleList = new ArrayList<>(webDriver.getWindowHandles());
		
		//もとから開いていた1番目のタブから後から開いた2番目のタブへ切り替え
		webDriver.switchTo().window(handleList.get(1));		
		
		//2番目のタブのh2が表示されるまでの待ち処理
		visibilityTimeout(By.cssSelector("h2"), 10);
		
		//タイトルタグの記述が一致しているか確認
		assertEquals("よくある質問 | LMS", webDriver.getTitle());	
		
		getEvidence(new Object(){});
		
	}

}
