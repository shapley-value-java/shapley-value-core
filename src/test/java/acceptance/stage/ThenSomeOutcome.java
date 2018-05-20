package acceptance.stage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.shapleyvalue.core.CharacteristicFunction;

import com.tngtech.jgiven.annotation.Hidden;

public class ThenSomeOutcome {



	public void the_shapley_value_is_efficient_for_the_$_players(int nbPlayers, 
			@Hidden CharacteristicFunction v,
			@Hidden List<Double> output) {
		
		for(int player=1; player<=nbPlayers; player++) {
			assertTrue(v.getValue(player)>=output.get(player));
		}
	}

	public void the_dummy_user_receives_nothing(@Hidden List<Double> output,  @Hidden int rangeDummyUser) {
		

		assertEquals(output.get(rangeDummyUser), 0.0, 0.1);
		
	}

}
