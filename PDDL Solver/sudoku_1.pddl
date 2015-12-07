(define
	(problem sudoku_1)
	(:domain sudoku)
	(:init
		(= (value_of one) 1)
		(= (value_of two) 2)
		(= (value_of three) 3)
		(= (value_of four) 4)
		(= (value_of five) 5)
		(= (value_of six) 6)
		(= (value_of seven) 7)
		(= (value_of eight) 8)
		(= (value_of nine) 9)

		(= (cell_value one one) 1)
		(= (cell_value one two) 2)
		(= (cell_value one three) 0)
		(= (cell_value one four) 0)

		(= (cell_value two one) 3)
		(= (cell_value two two) 0)
		(= (cell_value two three) 0)
		(= (cell_value two four) 0)

		(= (cell_value three one) 0)
		(= (cell_value three two) 0)
		(= (cell_value three three) 0)
		(= (cell_value three four) 1)


		(value_assigned two one)
		(value_assigned two two)

		(value_assigned three one)

		(value_assigned four four)

	)

	(:goal (and
		(value_assigned one one)
		(value_assigned one two)
		(value_assigned one three)
		(value_assigned one four)
		(value_assigned two one)
		(value_assigned two two)
		(value_assigned two three)
		(value_assigned two four)
		(value_assigned three one)
		(value_assigned three two)
		(value_assigned three three)
		(value_assigned three four)
		(value_assigned four one)
		(value_assigned four two)
		(value_assigned four three)
		(value_assigned four four)
	)
)
