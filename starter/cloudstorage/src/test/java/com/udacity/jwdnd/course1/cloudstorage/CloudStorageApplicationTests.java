package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	private static final String FIRST_NAME = "Test";
	private static final String LAST_NAME = "User";
	private static final String USERNAME = "testuser";
	private static final String PASSWORD = "password";

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testLoginPage() throws InterruptedException {
		driver.get(String.format("http://localhost:%d/login", this.port));
		Thread.sleep(1000);
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testUnauthorizedAccessToHomePage() throws InterruptedException {
		driver.get(String.format("http://localhost:%d/home", this.port));
		Thread.sleep(1000);
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testNewUserSignUpLoginLogout() throws InterruptedException {
		signUp();
		login();

		Assertions.assertEquals("Home", driver.getTitle());

		WebElement element = driver.findElement(By.id("logout"));
		element.click();
		Thread.sleep(1000);

		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(String.format("http://localhost:%d/home", this.port));
		Thread.sleep(1000);
		Assertions.assertEquals("Login", driver.getTitle());
	}

	private void signUp() throws InterruptedException {
		driver.get(String.format("http://localhost:%d/signup", this.port));
		Thread.sleep(1000);

		WebElement element = driver.findElement(By.id("inputFirstName"));
		element.sendKeys(FIRST_NAME);

		element = driver.findElement(By.id("inputLastName"));
		element.sendKeys(LAST_NAME);

		element = driver.findElement(By.id("inputUsername"));
		element.sendKeys(USERNAME);

		element = driver.findElement(By.id("inputPassword"));
		element.sendKeys(PASSWORD);

		element = driver.findElement(By.id("signup"));
		element.click();
		Thread.sleep(1000);
	}

	private void login() throws InterruptedException {
		driver.get(String.format("http://localhost:%d/login", this.port));
		Thread.sleep(1000);

		WebElement element = driver.findElement(By.id("inputUsername"));
		element.sendKeys(USERNAME);

		element = driver.findElement(By.id("inputPassword"));
		element.sendKeys(PASSWORD);

		element = driver.findElement(By.id("login"));
		element.click();
		Thread.sleep(1000);
	}

	@Test
	public void testNoteFunctionality() throws InterruptedException {
		signUp();
		login();

		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();
		Thread.sleep(500);

		WebElement showNoteModel = driver.findElement(By.id("show-note-model"));
		showNoteModel.click();
		Thread.sleep(500);

		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.sendKeys("TestNoteTitle");

		WebElement noteDescription = driver.findElement(By.id("note-description"));
		noteDescription.sendKeys("TestNoteDescription");

		WebElement saveNote = driver.findElement(By.id("save-note"));
		saveNote.click();
		Thread.sleep(1000);

		WebElement navigateHomeButton = driver.findElement(By.id("navigate-home"));
		navigateHomeButton.click();
		Thread.sleep(500);

		notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();
		Thread.sleep(500);

		WebElement savedNote = driver.findElement(By.id("note-title-display"));
		Assertions.assertEquals("TestNoteTitle", savedNote.getText());
		Thread.sleep(500);

		WebElement editNoteButton = driver.findElement(By.id("edit-note-btn"));
		editNoteButton.click();
		Thread.sleep(500);

		WebElement noteTitle1 = driver.findElement(By.id("note-title"));
		noteTitle1.sendKeys("Edit");
		WebElement saveNote1 = driver.findElement(By.id("save-note"));
		saveNote1.click();
		Thread.sleep(1000);


		navigateHomeButton = driver.findElement(By.id("navigate-home"));
		navigateHomeButton.click();
		Thread.sleep(1000);

		notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();
		Thread.sleep(500);

		savedNote = driver.findElement(By.id("note-title-display"));
		Assertions.assertEquals("TestNoteTitleEdit", savedNote.getText());

		WebElement deleteNoteBtn = driver.findElement(By.id("delete-note-btn"));
		deleteNoteBtn.click();
		Thread.sleep(1000);

		navigateHomeButton = driver.findElement(By.id("navigate-home"));
		navigateHomeButton.click();
		Thread.sleep(1000);

		notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();
		Thread.sleep(500);

		boolean ifNoteListEmpty = driver.findElements(By.id("note-title-display")).isEmpty();
		Assertions.assertTrue(ifNoteListEmpty);
	}

	@Test
	public void testCredentialFunctionality() throws InterruptedException {
		signUp();
		login();

		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();
		Thread.sleep(500);

		WebElement showCredentialsModel = driver.findElement(By.id("show-credentials-model"));
		showCredentialsModel.click();
		Thread.sleep(500);

		WebElement credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.sendKeys("www.test.co");

		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.sendKeys("testUsername");

		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.sendKeys("testPassword");

		WebElement credentialSubmit = driver.findElement(By.id("save-credentials"));
		credentialSubmit.click();
		Thread.sleep(1000);

		WebElement navigateHomeButton = driver.findElement(By.id("navigate-home"));
		navigateHomeButton.click();
		Thread.sleep(500);

		credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();
		Thread.sleep(500);

		WebElement saveCredentials = driver.findElement(By.id("credential-title-display"));
		Assertions.assertEquals("www.test.co", saveCredentials.getText());
		Thread.sleep(500);

		WebElement editCredentialsButton = driver.findElement(By.id("edit-credentials-btn"));
		editCredentialsButton.click();
		Thread.sleep(500);

		credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.sendKeys(".in");

		WebElement saveCredential = driver.findElement(By.id("save-credentials"));
		saveCredential.click();
		Thread.sleep(1000);


		navigateHomeButton = driver.findElement(By.id("navigate-home"));
		navigateHomeButton.click();
		Thread.sleep(1000);

		credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();
		Thread.sleep(500);

		saveCredentials = driver.findElement(By.id("credential-title-display"));
		Assertions.assertEquals("www.test.co.in", saveCredentials.getText());

		WebElement deleteCredentialButton = driver.findElement(By.id("delete-credentials-btn"));
		deleteCredentialButton.click();
		Thread.sleep(1000);

		navigateHomeButton = driver.findElement(By.id("navigate-home"));
		navigateHomeButton.click();
		Thread.sleep(1000);

		credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();
		Thread.sleep(500);

		boolean ifCredentialPresent = driver.findElements(By.id("credential-title-display"))
				.isEmpty();
		Assertions.assertTrue(ifCredentialPresent);
	}
}
