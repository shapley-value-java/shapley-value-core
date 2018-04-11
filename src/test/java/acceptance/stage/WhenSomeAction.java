package acceptance.stage;

import java.util.Map;

import org.shapleyvalue.core.CharacteristicFunction;
import org.shapleyvalue.core.ShapleyValue;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.Hidden;

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
