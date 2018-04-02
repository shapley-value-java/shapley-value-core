package acceptance.stage;

import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.Hidden;

import org.shapleyvalue.CharacteristicFunction;
import org.shapleyvalue.ShapleyValue;

public class WhenSomeAction extends Stage<WhenSomeAction> {



	public Map<Integer,Double> the_shapley_value_is_calculated(@Hidden CharacteristicFunction v) {
		
		ShapleyValue s = new ShapleyValue(v);	
		return s.calculate();
		
	}

	public WhenSomeAction a_dummy_user_is_added(@Hidden CharacteristicFunction v) {
		v.addDummyUser();

		return self();
	}

}
