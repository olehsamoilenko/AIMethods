import time
import random
import math

class Board(object):
    """An N-queens solution attempt."""

    def __init__(self, queens):
        """Instances differ by their queen placements."""
        self.queens = queens.copy() # No aliasing!

    def display(self):
        """Print the board."""
        for r in range(len(self.queens)):
            for c in range(len(self.queens)):
                if self.queens[c] == r:
                    print('Q ', end=''),
                else:
                    print('- ', end=''),
            print()
        print()
    
    
    def moves(self):
        """Return a list of possible moves given the current placements.""" 
        

    def neighbor(self):
        """Return a Board instance like this one but with one move made.""" # TODO: rename
        tmp_queens = self.queens.copy()
        random_col = random.choice(range(8))
        random_row = random.choice(range(8))
        tmp_queens[random_col] = random_row
        return Board(tmp_queens)

    def cost(self) -> int:
        """Compute the cost of this solution."""
        number_of_conflicts = 0

        # increment cost if two queens are in same row or in same diagonal.
        for i in range(0, len(self.queens)):
            for j in range(i + 1, len(self.queens)):
                if self.queens[i] == self.queens[j] or abs(self.queens[i] - self.queens[j]) == j - i:
                    # print(i, j)
                    number_of_conflicts += 1

        return number_of_conflicts

class Agent(object):
    """Knows how to solve an n-queens problem with simulated annealing."""

    def anneal(self, board):
        """Return a list of moves to adjust queen placements."""
        # YOU FILL THIS IN

def main():
    """Create a problem, solve it with simulated anealing, and console-animate."""

    queens = {0: 3, 1: 0, 2: 2, 3: 6, 4: 4, 5: 3, 6: 3, 7: 7}#dict()
    # for col in range(8):
    #     row = random.choice(range(8))
    #     queens[col] = row
    # print(queens)

    board = Board(queens)
    # board.display()

    t = 30
    decay_rate = 0.98
    
    # agent = Agent()
    # path = agent.anneal(board)
    
    print("Initial board:")
    board.display()
    # while path:
    # move = path.pop(0)
    
    for i in range(1000):
        print("Iteration " + str(i))
        neighbor = board.neighbor()
        # neighbor.display()

        board_cost = board.cost()
        neighbor_cost = neighbor.cost()
        # print(board.cost())
        # print(neighbor.cost())

        if neighbor_cost < board_cost:
            board = neighbor
            print("updated board, cost: %d (lower)" % neighbor_cost)
            board.display()
            if neighbor_cost == 0:
                print("DONE!")
                break
        else:
            c = neighbor_cost - board_cost
            p = math.exp(-c / t)
            # print(p)
            rnd = random.random()
            # print(rnd)
            if rnd < p:
                board = neighbor
                print("updated board, cost: %d (greater, t = %f)" % (neighbor_cost, t))
                board.display()

        # TODO: every 100 iterations
        t *= decay_rate

    # board = board.neighbor(move)
    # time.sleep(0.1)
    # board.display()

if __name__ == '__main__':
    main()
