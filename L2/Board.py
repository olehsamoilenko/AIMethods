import random
import math

class Board(object):
    """An N-queens solution attempt."""

    def __init__(self, queens):
        """Instances differ by their queen placements."""
        self._queens = queens.copy()


    def display(self):
        """Print the board."""
        for r in range(len(self._queens)):
            for c in range(len(self._queens)):
                if self._queens[c] == r:
                    print('Q ', end=''),
                else:
                    print('- ', end=''),
            print()
        print()


    def neighbor(self):
        """Return a Board instance like this one but with one move made."""
        tmp_queens = self._queens.copy()
        random_col = random.choice(range(len(self._queens)))
        random_row = random.choice(range(len(self._queens)))
        tmp_queens[random_col] = random_row
        return Board(tmp_queens)


    def cost(self) -> int:
        """Compute the cost of this solution."""
        number_of_conflicts = 0

        # increment cost if two queens are in same row or in same diagonal.
        for i in range(0, len(self._queens)):
            for j in range(i + 1, len(self._queens)):
                if self._queens[i] == self._queens[j] or abs(self._queens[i] - self._queens[j]) == j - i:
                    number_of_conflicts += 1

        return number_of_conflicts

class Agent(object):
    """Knows how to solve an n-queens problem with simulated annealing."""

    def solve(self, board: Board):
        j = 0
        i = 0
        while self._t > self._t_stop:
            i += 1
            print("Iteration " + str(i))
            
            neighbor = board.neighbor()
            board_cost = board.cost()
            neighbor_cost = neighbor.cost()

            if neighbor_cost < board_cost:
                board = neighbor
                print("updated board, cost: %d (lower)" % neighbor_cost)
                board.display()
                if neighbor_cost == 0:
                    print("DONE!")
                    break
            else:
                c = neighbor_cost - board_cost
                p = math.exp(-c / self._t)
                rnd = random.random()
                if rnd < p:
                    board = neighbor
                    print("updated board, cost: %d (greater, t = %f)" % (neighbor_cost, self._t))
                    board.display()

            j += 1
            if j == self._iterations_to_decay:
                self._t *= self._decay_rate
                j = 0

        print("Final temperature: %f" % self._t)


    def __init__(self, t=30.0, t_stop=0.5, decay_rate=0.98, iterations_to_decay=100):
        self._t = t
        self._t_stop = t_stop
        self._decay_rate = decay_rate
        self._iterations_to_decay = iterations_to_decay


def generate_queens(n: int) -> dict:
    queens = dict()
    for col in range(n):
        row = random.choice(range(n))
        queens[col] = row
    return queens


if __name__ == '__main__':
    # queens = {0: 3, 1: 0, 2: 2, 3: 6, 4: 4, 5: 3, 6: 3, 7: 7}
    queens = generate_queens(8)
    board = Board(queens)
    print("Initial board:")
    board.display()
    
    agent = Agent(t=30.0, t_stop=0.5, decay_rate=0.98, iterations_to_decay=100)
    agent.solve(board)
