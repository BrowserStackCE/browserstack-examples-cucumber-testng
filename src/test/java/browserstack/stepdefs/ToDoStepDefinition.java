package browserstack.stepdefs;

import org.openqa.selenium.By;
import org.testng.Assert;

import browserstack.TestRunner;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ToDoStepDefinition extends TestRunner {

	
	@Given("^user is on home Page$")
	public void user_already_on_home_page() {
		//System.out.println(driver.get().getCapabilities());
		driver.get().get("https://google.com");

	}

	@When("^select First Item$")
	public void select_first_item() {
		driver.get().findElement(By.name("li1")).click();
	}

	@Then("^select second item$")
	public void select_second_item() {
		driver.get().findElement(By.name("li2")).click();
	}

	@Then("^add new item$")
	public void add_new_item() {
		driver.get().findElement(By.id("sampletodotext")).clear();
		driver.get().findElement(By.id("sampletodotext")).sendKeys("Yey, Let's add it to list");
		driver.get().findElement(By.id("addbutton")).click();
	}

	@Then("^verify added item$")
	public void verify_added_item() {
		String item = driver.get().findElement(By.xpath("/html/body/div/div/div/ul/li[6]/span")).getText();
		Assert.assertTrue(item.contains("Yey, Let's add it to list"));
	}

	

}
