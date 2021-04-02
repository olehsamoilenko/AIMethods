# A*
### aka extended Dijkstra's algorithm

![Picture](https://github.com/olehsamoilenko/resources/blob/master/AIMethods/Flow-chart-of-A-star-algorithm.png)
де:

* **n** (sector 'n') - вузол графу станів, для даної задачі - стан пазлу з певним розміщенням фішок
* **g(n)** - оцінка довжини найкоротшого шляху від початкової вершини до n-ї, для даної задачі - кількість зроблених ходів
* **h(n)** - евристична функція, яка задає оцінку довжини найкоротшого шляху від n-ї вершини до цільової (target sector); у роботі розглянуті еврістичні функції: [Manhattan distance](https://xlinux.nist.gov/dads/HTML/manhattanDistance.html), кількість фішок не на своїх місцях, нульова функція (зводить алгоритм до алгоритму Дейкстри)

# Preview
```
Initial puzzle:
[1, 2, 3]
[4, 5, 0]
[6, 7, 8]

Dijkstra heuristic function
Open nodes: 3464
Steps to solve: 13
[1, 2, 3]
[4, 5, 6]
[7, 8, 0]

Amount of wrong positioned items heuristic function
Open nodes: 322
Steps to solve: 13
[1, 2, 3]
[4, 5, 6]
[7, 8, 0]

Manhattan heuristic function
Open nodes: 137
Steps to solve: 13
[1, 2, 3]
[4, 5, 6]
[7, 8, 0]
```

## References
https://www.youtube.com/watch?v=GuCzYxHa7iA<br>
https://blog.goodaudience.com/solving-8-puzzle-using-a-algorithm-7b509c331288
