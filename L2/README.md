# Simulated annealing
### aka Metropolis algorithm

```
simulated-annealing(initial solution)
let solution be initial
let t be an initial temperature
until t is almost zero
    let neighbor be a random neighbor of solution
    if the cost of neighbor is less than the cost of solution
        let solution be neighbor
        stop if the cost is now 0
    otherwise
        let c be the cost increase
        compute p = e^(-c/t)
        with probability p, let solution be neighbor
    multiply t by a decay rate
return solution
```
де:

* **initial** - випадкове розташування кожного з n ферзів у своїй колонці дошки
* **t** - "температура", параметр можливості переходу до “гірших” рішень
* **neighbor** - "сусідній" стан дошки за графом станів, містить переміщення _одного_ ферзя
* **cost** - цільова функція, для даної задачі - кількість конфліктів
* **decay rate** - параметр зниження температури

# Preview

```
Initial board:
- Q - - - - - - 
- - - - - - - - 
- - Q - - - - - 
Q - - - - Q Q - 
- - - - Q - - - 
- - - - - - - - 
- - - Q - - - - 
- - - - - - - Q 

Iteration 0
updated board, cost: 11 (greater, t = 30.000000)
- - - - - - - - 
- - - - - - - - 
- Q Q - - - - - 
Q - - - - Q Q - 
- - - - Q - - - 
- - - - - - - - 
- - - Q - - - - 
- - - - - - - Q 

Iteration 1
updated board, cost: 9 (lower)
- - - - - - - - 
- - - - - - - - 
Q Q Q - - - - - 
- - - - - Q Q - 
- - - - Q - - - 
- - - - - - - - 
- - - Q - - - - 
- - - - - - - Q


...


Iteration 336
updated board, cost: 1 (greater, t = 0.033813)
- - - - - - - - 
- - - Q - - - - 
- Q - - - - - - 
- - - - - - - Q 
- - - - - Q - - 
Q - - - - - - - 
- - Q - - - Q - 
- - - - Q - - - 

Iteration 337
Iteration 338
updated board, cost: 1 (greater, t = 0.032474)
- - - - - - - - 
- - - Q - - Q - 
- Q - - - - - - 
- - - - - - - Q 
- - - - - Q - - 
Q - - - - - - - 
- - Q - - - - - 
- - - - Q - - - 

Iteration 339
Iteration 340
Iteration 341
updated board, cost: 0 (lower)
- - - - - - Q - 
- - - Q - - - - 
- Q - - - - - - 
- - - - - - - Q 
- - - - - Q - - 
Q - - - - - - - 
- - Q - - - - - 
- - - - Q - - - 

DONE!
```

## References
http://modelai.gettysburg.edu/2016/pyconsole/ex3/index.html