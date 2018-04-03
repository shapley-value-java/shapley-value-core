# shapley-value-core
Shapley value calculation in Java

# Shapley value introduction


## Fair distribution 
* Efficiency
* null player 
* symetry
* additivity

## Application example share a taxi 
How to share a taxi travel ?
Amy, Bob and Clare are sharing a taxi. We imagine they are going to the same direction.

- Amy must pay 6 to go home
- Bob must pay 12 to go home
- Clare must pay 42 to go home

# Calculation examples
## one element
### input 
characteristic function : (2^N -> R)
- v({1})=1.0
### output
shapley value (N -> R)
phi1 =1.0

## two elements
### input
N = {1,2}
characteristic function : (2^N -> R)
- v({1})   = 1
- v({2})   = 2
- v({1,2}) = 4

### calculation

|coalition | marginal contribution 1  | marginal contribution 2 |
| -------- | ------------------------ | ----------------------- |
| 1 12     | v({1})=1                 | v({1,2})-v({1}) =3      |
| 2 12     | v({1,2})-v({2})=2        | v({2})= 2               |

### output
- phi1 = (1+2)/2=1.5
- phi2 = (3+2)/2=2.5

### three elements
## input
characteristic function : (2^N -> R)
- v({1})     = 80
- v({2})     = 56
- v({3})     = 70
- v({1,2})   = 80
- v({1,3})   = 85
- v({2,3})   = 72
- v({1,2,3}) = 90

### output
- phi1 = 39.2
- phi2 = 20.7
- phi3 = 30.2

# resources
## video
* how to share a taxi https://www.youtube.com/watch?v=aThG4YAFErw
* course about game theory https://www.youtube.com/watch?v=qcLZMYPdpH4

## other project in java
* http://bitsbytesnwords.blogspot.be/2013/02/shapley-value.html
