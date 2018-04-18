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

# Extension
## Share taxi
### Example
This example is :
Amy, Bob and Clare are sharing a taxi. We imagine they are going to the same direction.

- Amy must pay 6 to go home
- Bob must pay 12 to go home
- Clare must pay 42 to go home 

### Code example

```java
	@Test
	public void testCalculateThreeParticipants() {		
		TaxiCalculation taxiCalculation = 
				new TaxiCalculation.TaxiCalculationBuilder()
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
## Fraud rules evaluation
Example:
There are four fraudulent transactions T1, T2, T3, T4.
There are four rules trying to detect the fraud events.
- Rule1 detects T1, T2, T3
- Rule2 detects T1, T2, T3
- Rule3 detects T1, T2, T3
- Rule4 detects T4

The Shapley value evaluates the contribution of each rules (the sum will be normalized to 1)
The Rules4 detects 1/4 of the event (alone) so we can expect phiRule4=0.25
The rules 1, 2, 3 detect the same events and should have the same Shapley value.
phiRule1=phiRule2=phiRule2=0.25

### Code example

```java
	@Test
	public void testEvaluationFourRules() {
		
		FraudRuleEvaluation evaluation = 
				new FraudRuleEvaluation.FraudRuleEvaluationBuilder()
				.addRule("Rule1", 1,2,3)
				.addRule("Rule2", 1,2,3)
				.addRule("Rule3", 1,2,3)
				.addRule("Rule4", 4)
				.build();
		
		Map<String,Double> output = evaluation.calculate();
		double phiRule1 = output.get("Rule1");
		double phiRule2 = output.get("Rule2");
		double phiRule3 = output.get("Rule3");
		double phiRule4 = output.get("Rule4");
		
		assertEquals(phiRule1, 0.25, 0.01);
		assertEquals(phiRule2, 0.25, 0.01);
		assertEquals(phiRule3, 0.25, 0.01);
		assertEquals(phiRule4, 0.25, 0.01);
	}
```

## Parlement
### First example
Question: The parliament of Micronesia is made up of four political parties, A, B, C, and D, which have 45, 25, 15, and 15 representatives, respectively.
If a coalition has the majority it receives 1, if a coalition has no majority it receives 0.

the solution is 
- phiA=0.5 
- phiB=1/6
- phiC=1/6
- phiD=1/6
B, C, and D have the same Shapley value, so the same influence.


# resources
## video
* how to share a taxi https://www.youtube.com/watch?v=aThG4YAFErw
* course about game theory https://www.youtube.com/watch?v=qcLZMYPdpH4

## example parlement application
https://math.stackexchange.com/questions/1310344/calculating-shapley-value-on-voting-game

## other project in java
* http://bitsbytesnwords.blogspot.be/2013/02/shapley-value.html
