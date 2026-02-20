package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

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
		
		WebDriverWait wait = new WebDriverWait(webDriver,Duration.ofSeconds(10)); 
		
		webDriver.get("http://localhost:8080/lms");

		wait.until(ExpectedConditions.titleIs("ログイン | LMS"));
		
		assertEquals("ログイン | LMS", webDriver.getTitle());
		
		getEvidence(new Object(){});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {
		
		WebDriverWait wait = new WebDriverWait(webDriver,Duration.ofSeconds(10)); 
		
		webDriver.get("http://localhost:8080/lms");

		wait.until(ExpectedConditions.titleIs("ログイン | LMS"));
		
		//DBに存在しないIDとパスワードを入力してテスト
		webDriver.findElement(By.name("loginId")).sendKeys("1111111111");
		webDriver.findElement(By.name("password")).sendKeys("1111111111");
		webDriver.findElement(By.className("btn-primary")).click();
		
		getEvidence(new Object(){}, "NotUser");
		
		//DBに存在するIDと存在しないパスワードを入力してテスト
		webDriver.findElement(By.name("loginId")).clear();
		webDriver.findElement(By.name("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.name("password")).clear();
		webDriver.findElement(By.name("password")).sendKeys("1111111111");
		webDriver.findElement(By.className("btn-primary")).click();
		
		getEvidence(new Object(){}, "NotFoundPassword");
		
		//DBに存在しないIDと存在するパスワードを入力してテスト
		webDriver.findElement(By.name("loginId")).clear();
		webDriver.findElement(By.name("loginId")).sendKeys("1111111111");
		webDriver.findElement(By.name("password")).clear();
		webDriver.findElement(By.name("password")).sendKeys("StudentAA01");
		webDriver.findElement(By.className("btn-primary")).click();
		
		getEvidence(new Object(){}, "NotFoundId");
		
	}

}
