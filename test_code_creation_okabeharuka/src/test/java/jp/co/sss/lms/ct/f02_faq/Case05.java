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
import org.openqa.selenium.WebElement;


/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	
	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		
		//「th:field」のkeywordの値をname属性として取得
		WebElement keyword = webDriver.findElement(By.name("keyword"));
		
		//keywordの値を一旦消す
		keyword.clear();
		
		//keywordの値に「キャンセル」を入れる
		keyword.sendKeys("キャンセル");
		
		//検索ボタンをクリック
		webDriver.findElement(By.cssSelector("input[value='検索']")).click();
		
		//ページロードのタイムアウト処理
		pageLoadTimeout(10);
		
		//検索結果を取得
		WebElement result = webDriver.findElement(By.className("mb10"));
				
		//検索結果と期待する結果にて表示される文言が同一かチェック
		assertEquals("Q.キャンセル料・途中退校について",result.getText());
		
		getEvidence(new Object(){});		
		
	}
	
	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		
		//クリアボタンをクリック
		webDriver.findElement(By.cssSelector("input[value='クリア']")).click();
			
		//「th:field」のkeywordの値をname属性として取得
		WebElement keyword = webDriver.findElement(By.name("keyword"));
		
		//「th:field」のkeywordの値が空文字になっているかチェック
		assertEquals("", keyword.getText());
		
		getEvidence(new Object(){});	
	}

}
