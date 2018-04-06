# shapley-value-core
Shapley value calculation in Java

# Shapley value introduction


## Fair distribution 
* Efficiency
* Null player 
* Symetry
* Additivity

## Application example share a taxi 
How to share a taxi travel ?
Amy, Bob and Clare are sharing a taxi. We imagine they are going to the same direction.

- Amy must pay 6 to go home
- Bob must pay 12 to go home
- Clare must pay 42 to go home

If they share the taxi, the Shapley value will give :
- Amy must pay 2
- Bob must pay 5
- Clare must pay 35
- The total 2+5+35 = 42

With this example, we can see that Shapley value is for everybody efficient. Because Amy, Bob, and Clare pay less when they share than when they travel alone. 

### Code example

```java
	@Test
	public void testCalculateThreeParticipants() {		
		TaxiCalculation taxiCalculation = 
				new TaxiCalculation.TaxiCalculationBuilder(3)
				.addUser(6.0, "A")
				.addUser(12.0, "B")
				.addUser(42.0, "C")
				.build();

		Map<String,Double> output = taxiCalculation.calculate();
		double phiA = output.get("A");
		double phiB = output.get("B");
		double phiC = output.get("C");
				
		assertEquals(phiA, 2.0, 0.01);
		assertEquals(phiB, 5.0, 0.01);
		assertEquals(phiC, 35.0, 0.01);	
	}
```



# Calculation examples

Here is several examples with a few participants. You can imagine that with big groups the calculation becomes very difficult.

## One element
### Input 
characteristic function : (2^N -> R)
- v({1})=1.0
### Output
shapley value (N -> R)
phi1 =1.0

### Code example

```java
	@Test
	public void testCalculateOneParticipant() {
		
		CharacteristicFunction cfunction = 
				new CharacteristicFunction.CharacteristicFunctionBuilder(1)
				.addCoalition(1.0, 1).build();
	
		ShapleyValue s = new ShapleyValue(cfunction);
		
		Map<Integer,Double> output =s.calculate();
		double phi1 = output.get(1);
		
		assertEquals(phi1, 1.0, 0.01);
	}
```

## Two elements
### Input
N = {1,2}
characteristic function : (2^N -> R)
- v({1})   = 1
- v({2})   = 2
- v({1,2}) = 4

### Calculation

|coalition | marginal contribution 1  | marginal contribution 2 |
| -------- | ------------------------ | ----------------------- |
| 1 12     | v({1})=1                 | v({1,2})-v({1}) =3      |
| 2 12     | v({1,2})-v({2})=2        | v({2})= 2               |

### Output
- phi1 = (1+2)/2=1.5
- phi2 = (3+2)/2=2.5

### Code example

```java
	@Test
	public void testCalculateTwoParticipants() {
		CharacteristicFunction cfunction = 
				new CharacteristicFunction.CharacteristicFunctionBuilder(2)
				.addCoalition(1.0, 1)		
				.addCoalition(2.0, 2)
				.addCoalition(4.0, 1, 2).build();	
			
		ShapleyValue s = new ShapleyValue(cfunction);
		
		Map<Integer,Double> output =s.calculate();
		double phi1 = output.get(1);
		double phi2 = output.get(2);
		
		assertEquals(phi1, 1.5, 0.01);
		assertEquals(phi2, 2.5, 0.01);
		
	}
```

## Three elements
### Input
characteristic function : (2^N -> R)
- v({1})     = 80
- v({2})     = 56
- v({3})     = 70
- v({1,2})   = 80
- v({1,3})   = 85
- v({2,3})   = 72
- v({1,2,3}) = 90

### Output
- phi1 = 39.2
- phi2 = 20.7
- phi3 = 30.2

### Code example

```java
	@Test
	public void testCalculateThreeParticipants() {		
		CharacteristicFunction cfunction = 
				new CharacteristicFunction.CharacteristicFunctionBuilder(3)
				.addCoalition(80.0, 1)	
				.addCoalition(56.0, 2)
				.addCoalition(70.0, 3)	
				.addCoalition(80.0, 1, 2)			
				.addCoalition(85.0, 1, 3)		
				.addCoalition(72.0, 2, 3)			
				.addCoalition(90.0, 1, 2, 3).build();	
	
		ShapleyValue s = new ShapleyValue(cfunction);
		
		Map<Integer,Double> output =s.calculate();
		double v1 = output.get(1);
		double v2 = output.get(2);
		double v3 = output.get(3);
		
		assertEquals(v1, 39.2, 0.1);
		assertEquals(v2, 20.7, 0.1);
		assertEquals(v3, 30.2, 0.1);
		
	}
```

# resources
## video
* how to share a taxi https://www.youtube.com/watch?v=aThG4YAFErw
* course about game theory https://www.youtube.com/watch?v=qcLZMYPdpH4

## other project in java
* http://bitsbytesnwords.blogspot.be/2013/02/shapley-value.html
