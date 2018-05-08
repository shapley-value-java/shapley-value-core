package architecture;

import org.junit.runner.RunWith;
import org.shapleyvalue.application.facade.ShapleyApplication;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "org.shapleyvalue.application", importOptions = ImportOption.DontIncludeTests.class)
public class Arch01ApplicationRules {
	


	@ArchTest
	public static final ArchRule RULES1_APPLICATION_SUB_PACKAGES_SHOULD_NOT_CONTAIN_INTEFACES =			
	classes().that().resideInAPackage("..impl..").should().notBeInterfaces();	
	
	@ArchTest
	public static final ArchRule RULES2_APPLICATION_SHOULD_IMPLEMENTS_SHAPLEY_APPLICATION_INTEFACE =			
	classes().that().resideInAPackage("..impl..").and().haveNameMatching(".*Calculation$").should().implement(ShapleyApplication.class);

	
	@ArchTest
	public static final ArchRule RULES3_LAYERS_IMPL_IS_NOT_ACCESSED_BY_FACADE = layeredArchitecture()
	            .layer("applicationFacade").definedBy("org.shapleyvalue.application.facade..")
	            .layer("applicationImpl").definedBy("org.shapleyvalue.application.impl..")
	            .whereLayer("applicationImpl").mayNotBeAccessedByAnyLayer();


}
