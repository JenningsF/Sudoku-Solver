(define
	(problem sudoku_2)
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

		(= (cell_value one one) 0)
		(= (cell_value one two) 3)
		(= (cell_value one three) 0)
		(= (cell_value one four) 0)
		(= (cell_value one five) 0)
		(= (cell_value one six) 0)
		(= (cell_value one seven) 0)
		(= (cell_value one eight) 1)
		(= (cell_value one nine) 0)

		(= (cell_value two one) 0)
		(= (cell_value two two) 9)
		(= (cell_value two three) 7)
		(= (cell_value two four) 5)
		(= (cell_value two five) 0)
		(= (cell_value two six) 0)
		(= (cell_value two seven) 3)
		(= (cell_value two eight) 8)
		(= (cell_value two nine) 0)

		(= (cell_value three one) 0)
		(= (cell_value three two) 0)
		(= (cell_value three three) 0)
		(= (cell_value three four) 0)
		(= (cell_value three five) 9)
		(= (cell_value three six) 0)
		(= (cell_value three seven) 2)
		(= (cell_value three eight) 0)
		(= (cell_value three nine) 0)

		(= (cell_value four one) 0)
		(= (cell_value four two) 0)
		(= (cell_value four three) 8)
		(= (cell_value four four) 0)
		(= (cell_value four five) 7)
		(= (cell_value four six) 0)
		(= (cell_value four seven) 4)
		(= (cell_value four eight) 0)
		(= (cell_value four nine) 0)

		(= (cell_value five one) 0)
		(= (cell_value five two) 0)
		(= (cell_value five three) 3)
		(= (cell_value five four) 0)
		(= (cell_value five five) 6)
		(= (cell_value five six) 0)
		(= (cell_value five seven) 0)
		(= (cell_value five eight) 0)
		(= (cell_value five nine) 0)

		(= (cell_value six one) 0)
		(= (cell_value six two) 1)
		(= (cell_value six three) 6)
		(= (cell_value six four) 0)
		(= (cell_value six five) 0)
		(= (cell_value six six) 2)
		(= (cell_value six seven) 8)
		(= (cell_value six eight) 9)
		(= (cell_value six nine) 0)

		(= (cell_value seven one) 0)
		(= (cell_value seven two) 4)
		(= (cell_value seven three) 9)
		(= (cell_value seven four) 0)
		(= (cell_value seven five) 0)
		(= (cell_value seven six) 0)
		(= (cell_value seven seven) 0)
		(= (cell_value seven eight) 2)
		(= (cell_value seven nine) 0)

		(= (cell_value eight one) 0)
		(= (cell_value eight two) 5)
		(= (cell_value eight three) 2)
		(= (cell_value eight four) 1)
		(= (cell_value eight five) 0)
		(= (cell_value eight six) 9)
		(= (cell_value eight seven) 6)
		(= (cell_value eight eight) 4)
		(= (cell_value eight nine) 0)

		(value_assigned one two)
		(value_assigned one six)
		(value_assigned one eight)

		(value_assigned two two)
		(value_assigned two eight)

		(value_assigned three two)
		(value_assigned three three)
		(value_assigned three four)
		(value_assigned three seven)
		(value_assigned three eight)

		(value_assigned four five)
		(value_assigned four seven)

		(value_assigned five three)
		(value_assigned five five)
		(value_assigned five seven)

		(value_assigned six three)
		(value_assigned six five)

		(value_assigned seven two)
		(value_assigned seven three)
		(value_assigned seven six)
		(value_assigned seven seven)
		(value_assigned seven eight)

		(value_assigned eight two)
		(value_assigned eight three)
		(value_assigned eight eight)

		(value_assigned nine two)
		(value_assigned nine three)
		(value_assigned nine four)
		(value_assigned nine six)
		(value_assigned nine seven)
		(value_assigned nine eight)

	)

	(:goal (and
		(value_assigned one one)
		(value_assigned one two)
		(value_assigned one three)
		(value_assigned one four)
		(value_assigned one five)
		(value_assigned one six)
		(value_assigned one seven)
		(value_assigned one eight)
		(value_assigned one nine)

		(value_assigned two one)
		(value_assigned two two)
		(value_assigned two three)
		(value_assigned two four)
		(value_assigned two five)
		(value_assigned two six)
		(value_assigned two seven)
		(value_assigned two eight)
		(value_assigned two nine)

		(value_assigned three one)
		(value_assigned three two)
		(value_assigned three three)
		(value_assigned three four)
		(value_assigned three five)
		(value_assigned three six)
		(value_assigned three seven)
		(value_assigned three eight)
		(value_assigned three nine)

		(value_assigned four one)
		(value_assigned four two)
		(value_assigned four three)
		(value_assigned four four)
		(value_assigned four five)
		(value_assigned four six)
		(value_assigned four seven)
		(value_assigned four eight)
		(value_assigned four nine)

		(value_assigned five one)
		(value_assigned five two)
		(value_assigned five three)
		(value_assigned five four)
		(value_assigned five five)
		(value_assigned five six)
		(value_assigned five seven)
		(value_assigned five eight)
		(value_assigned five nine)

		(value_assigned six one)
		(value_assigned six two)
		(value_assigned six three)
		(value_assigned six four)
		(value_assigned six five)
		(value_assigned six six)
		(value_assigned six seven)
		(value_assigned six eight)
		(value_assigned six nine)

		(value_assigned seven one)
		(value_assigned seven two)
		(value_assigned seven three)
		(value_assigned seven four)
		(value_assigned seven five)
		(value_assigned seven six)
		(value_assigned seven seven)
		(value_assigned seven eight)
		(value_assigned seven nine)

		(value_assigned eight one)
		(value_assigned eight two)
		(value_assigned eight three)
		(value_assigned eight four)
		(value_assigned eight five)
		(value_assigned eight six)
		(value_assigned eight seven)
		(value_assigned eight eight)
		(value_assigned eight nine)

		(value_assigned nine one)
		(value_assigned nine two)
		(value_assigned nine three)
		(value_assigned nine four)
		(value_assigned nine five)
		(value_assigned nine six)
		(value_assigned nine seven)
		(value_assigned nine eight)
		(value_assigned nine nine))
	)
)
