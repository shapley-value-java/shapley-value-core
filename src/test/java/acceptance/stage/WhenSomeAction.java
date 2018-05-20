package acceptance.stage;

import java.util.List;

import org.shapleyvalue.core.CharacteristicFunction;
import org.shapleyvalue.core.ShapleyValue;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.Hidden;

public class WhenSomeAction extends Stage<WhenSomeAction> {



	public List<Double> the_shapley_value_is_calculated(@Hidden CharacteristicFunction v) {
		
		ShapleyValue s = new ShapleyValue(v);	
		s.calculate();
		return s.getResult();
	}

	public WhenSomeAction a_dummy_user_is_added(@Hidden CharacteristicFunction v) {
		v.addDummyUser();

		return self();
	}

}
