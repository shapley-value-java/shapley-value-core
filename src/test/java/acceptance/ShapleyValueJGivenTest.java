package acceptance;

import java.util.Map;

import org.junit.Test;
import org.shapleyvalue.core.CharacteristicFunction;

import com.tngtech.jgiven.junit.ScenarioTest;

import acceptance.stage.GivenSomeState;
import acceptance.stage.ThenSomeOutcome;
import acceptance.stage.WhenSomeAction;

public class ShapleyValueJGivenTest extends ScenarioTest<GivenSomeState, WhenSomeAction, ThenSomeOutcome> {

	@Test
	public void the_Shapley_value_is_efficient() {
		
		int nbPlayers =3;
		CharacteristicFunction v = null;
		
		v = given().a_charateristic_funtion_for_$_players(nbPlayers);
		Map<Integer,Double> output = when().the_shapley_value_is_calculated(v);
		then().the_shapley_value_is_efficient_for_the_$_players(nbPlayers,v, output);
		
	}
	
	@Test
	public void in_Shapley_value_a_dummy_user_receive_nothing() {
		
		int nbPlayers =2;
		CharacteristicFunction v = null;
		
		v = given().a_charateristic_funtion_for_$_players(nbPlayers);
		Map<Integer,Double> output = when().a_dummy_user_is_added(v).
		and().the_shapley_value_is_calculated(v);
		then().the_dummy_user_receives_nothing(output, nbPlayers+1);
		
	}
}
