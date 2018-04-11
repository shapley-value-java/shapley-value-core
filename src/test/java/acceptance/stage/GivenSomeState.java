package acceptance.stage;

import org.shapleyvalue.core.CharacteristicFunction;

public class GivenSomeState {



	public CharacteristicFunction a_charateristic_funtion_for_$_players(int nbPlayers) {
		
		CharacteristicFunction v = null;
		if(nbPlayers ==2) {
			v = new CharacteristicFunction.CharacteristicFunctionBuilder(nbPlayers)
					.addCoalition(1.0, 1)	
					.addCoalition(2.0, 2)
					.addCoalition(4.0, 1, 2).build();	
		}
 		if(nbPlayers ==3) {
			v = new CharacteristicFunction.CharacteristicFunctionBuilder(nbPlayers)
				.addCoalition(80.0, 1)	
				.addCoalition(56.0, 2)
				.addCoalition(70.0, 3)	
				.addCoalition(80.0, 1, 2)			
				.addCoalition(85.0, 1, 3)		
				.addCoalition(72.0, 2, 3)			
				.addCoalition(90.0, 1, 2, 3).build();	
		}
		return v;
		
	}

}
