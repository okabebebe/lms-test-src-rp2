package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

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
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.cssSelector("h2"), 10);
		
		//タイトルタグの記述が一致しているか確認
		assertEquals("コース詳細 | LMS", webDriver.getTitle());	
		
		getEvidence(new Object(){});	
		
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		
		//「ようこそ受講生ＡＡ１さん」リンクをクリック
		webDriver.findElement(By.linkText("ようこそ受講生ＡＡ１さん")).click();	
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.cssSelector("h2"), 10);
		
		//目的の画面遷移ができているかチェック
		assertEquals("ユーザー詳細", webDriver.getTitle());
		
		getEvidence(new Object(){});
		
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		
		//指定位置までスクロール
		scrollTo("500");
		
		//すべての.btn-defaultのボタンを取得し、10番目のボタン＝週報レポートの修正するボタンをクリック
		List<WebElement> reportDetails = webDriver.findElements(By.className("btn-default"));	
		reportDetails.get(10).click();
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.cssSelector("h2"), 10);
		
		//目的の画面遷移ができているかチェック
		WebElement updateReport = webDriver.findElement(By.cssSelector("h2"));		
		assertEquals("週報【デモ】 2022年10月2日", updateReport.getText());
		
		getEvidence(new Object(){});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		
		//学習項目の要素を取得し空にする
		WebElement curriculum = webDriver.findElement(By.name("intFieldNameArray[0]"));		
		curriculum.clear();

		scrollTo("500");
		
		//「提出する」ボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();		
		
		//要素の可視性タイムアウト設定		
		visibilityTimeout(By.className("errorInput"), 10);
		
		//学習項目の要素を再取得
		WebElement erroCurriculum = webDriver.findElement(By.name("intFieldNameArray[0]"));
		
		//エラー時のクラス名が一致しているか確認
		assertEquals("form-control errorInput", erroCurriculum.getAttribute("class"));
		
		getEvidence(new Object(){});
				
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		
		//学習項目と理解度の要素を取得
		WebElement curriculum = webDriver.findElement(By.name("intFieldNameArray[0]"));
		Select graspLevel = new Select(webDriver.findElement(By.name("intFieldValueArray[0]")));

		//学習項目の要素に値を入力
		curriculum.sendKeys("ITリテラシー①");
		//理解度の要素に空選択の値を入力
		graspLevel.selectByIndex(0);
		
		scrollTo("500");
		
		//「提出する」ボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.className("errorInput"), 10);
		
		//理解度の要素を再取得
		WebElement errGraspLevel = webDriver.findElement(By.name("intFieldValueArray[0]"));
	
		//エラー時のクラス名が一致しているか確認
		assertEquals("form-control errorInput", errGraspLevel.getAttribute("class"));
		
		getEvidence(new Object(){});
		
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		
		//理解度と目標の達成度の要素を取得
		Select graspLevel = new Select(webDriver.findElement(By.name("intFieldValueArray[0]")));
		WebElement goalLevel = webDriver.findElement(By.name("contentArray[0]"));
		
		//理解度の要素に選択の値を入力
		graspLevel.selectByIndex(2);
		//目標の達成度の要素を空にする
		goalLevel.clear();
		
		//目標の達成度の要素に値を送る
		goalLevel.sendKeys("五");
		
		scrollTo("500");
		
		//「提出する」ボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.className("errorInput"), 10);
		
		//目標の達成度の要素を再取得
		WebElement errGoalLevel = webDriver.findElement(By.name("contentArray[0]"));
	
		//エラー時のクラス名が一致しているか確認
		assertEquals("form-control errorInput", errGoalLevel.getAttribute("class"));
		
		getEvidence(new Object(){});
		
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		
		//目標の達成度の要素を取得
		WebElement goalLevel = webDriver.findElement(By.name("contentArray[0]"));
		
		//目標の達成度の要素を空して0を渡す
		goalLevel.clear();		
		goalLevel.sendKeys("0");
		
		scrollTo("500");
		
		//「提出する」ボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.className("errorInput"), 10);
		
		//目標の達成度の要素を再取得
		WebElement LowErrGoalLevel = webDriver.findElement(By.name("contentArray[0]"));
	
		//エラー時のクラス名が一致しているか確認
		assertEquals("form-control errorInput", LowErrGoalLevel.getAttribute("class"));
		
		getEvidence(new Object(){}, "lowerValue");
		
		//目標の達成度の要素を空して11を渡す
		LowErrGoalLevel.clear();
		LowErrGoalLevel.sendKeys("11");
		
		scrollTo("500");
		
		//「提出する」ボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.className("errorInput"), 10);
		
		//目標の達成度の要素を再取得
		WebElement highErrGoalLevel = webDriver.findElement(By.name("contentArray[0]"));
		
		//エラー時のクラス名が一致しているか確認
		assertEquals("form-control errorInput", highErrGoalLevel.getAttribute("class"));
		
		getEvidence(new Object(){}, "higherValue");
		
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		
		//目標の達成度と所感の要素を取得
		WebElement goalLevel = webDriver.findElement(By.name("contentArray[0]"));
		WebElement impression = webDriver.findElement(By.name("contentArray[1]"));
		
		//目標の達成度と所感の要素を空にする
		goalLevel.clear();
		impression.clear();
		
		scrollTo("500");
		
		//「提出する」ボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.className("errorInput"), 10);
		
		//目標の達成度と所感の要素を再取得
		WebElement errGoalLevel = webDriver.findElement(By.name("contentArray[0]"));
		WebElement errImpression = webDriver.findElement(By.name("contentArray[1]"));
	
		//エラー時のクラス名が一致しているか確認
		assertEquals("form-control errorInput", errGoalLevel.getAttribute("class"));
		assertEquals("form-control errorInput", errImpression.getAttribute("class"));		
		
		getEvidence(new Object(){});
		
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		
		//目標の達成度と所感と一週間の振り返りの要素を取得
		WebElement goalLevel = webDriver.findElement(By.name("contentArray[0]"));
		WebElement impression = webDriver.findElement(By.name("contentArray[1]"));
		WebElement reflect = webDriver.findElement(By.name("contentArray[2]"));
		
		//目標の達成度と所感と一週間の振り返りの要素を空にする
		goalLevel.clear();
		impression.clear();
		reflect.clear();
		
		//aを2001文字分用意
		String text = "a".repeat(2001);
		
		//目標の達成度の要素に5を渡す
		goalLevel.sendKeys("5");
		//2001文字のaを所感と一週間の振り返りの要素に渡す
		impression.sendKeys(text);
		reflect.sendKeys(text);
		
		scrollTo("500");
		
		//「提出する」ボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.className("errorInput"), 10);
		
		//所感と一週間の振り返りの要素を再取得
		WebElement errImpression = webDriver.findElement(By.name("contentArray[1]"));
		WebElement errReflect = webDriver.findElement(By.name("contentArray[2]"));
	
		//エラー時のクラス名が一致しているか確認
		assertEquals("form-control errorInput", errImpression.getAttribute("class"));
		assertEquals("form-control errorInput", errReflect.getAttribute("class"));
		
		scrollTo("500");
		
		getEvidence(new Object(){});
		
	}

}
