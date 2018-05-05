package architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import org.junit.runner.RunWith;
import org.shapleyvalue.application.ShapleyApplication;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchIgnore;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "org.shapleyvalue.application", importOptions = ImportOption.DontIncludeTests.class)
public class Arch01ApplicationRules {
	


	@ArchTest
	public static final ArchRule RULES1_APPLICATION_SUB_PACKAGES_SHOULD_NOT_CONTAIN_INTEFACES =			
	classes().that().resideInAnyPackage("..taxi..","..parliament", "..fraud..").should().notBeInterfaces();	
	
	//@ArchIgnore
	@ArchTest
	public static final ArchRule RULES2_APPLICATION_SHOULD_IMPLEMENTS_SHAPLEY_APPLICATION_INTEFACE =			
	classes().that().resideInAnyPackage("..taxi..","..parliament", "..fraud..").and().haveNameMatching(".*Calculation$").should().implement(ShapleyApplication.class);

	


}
