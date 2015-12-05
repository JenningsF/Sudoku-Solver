(define (domain sudoku)
	(:requirements :strips :fluents)
	(:constants one two three four five six seven eight nine)
	(:predicates
		(value_assigned ?row ?col)
		(value_of ?number)
	)
	(:functions
		(value_of ?number)
		(cell_value ?row ?col)
	)

	(:action ASSIGN_1-1
		:parameters (?row ?col ?number)
		:precondition (and
			(not (value_assigned ?row ?col))
			(< (value_of ?row) 4)
			(< (value_of ?col) 4)

			(<> (value_of ?number) (cell_value ?row one))
			(<> (value_of ?number) (cell_value ?row two))
			(<> (value_of ?number) (cell_value ?row three))
			(<> (value_of ?number) (cell_value ?row four))
			(<> (value_of ?number) (cell_value ?row five))
			(<> (value_of ?number) (cell_value ?row six))
			(<> (value_of ?number) (cell_value ?row seven))
			(<> (value_of ?number) (cell_value ?row eight))
			(<> (value_of ?number) (cell_value ?row nine))

			(<> (value_of ?number) (cell_value one ?col))
			(<> (value_of ?number) (cell_value two ?col))
			(<> (value_of ?number) (cell_value three ?col))
			(<> (value_of ?number) (cell_value four ?col))
			(<> (value_of ?number) (cell_value five ?col))
			(<> (value_of ?number) (cell_value six ?col))
			(<> (value_of ?number) (cell_value seven ?col))
			(<> (value_of ?number) (cell_value eight ?col))
			(<> (value_of ?number) (cell_value nine ?col))

			(<> (value_of ?number) (cell_value one one))
			(<> (value_of ?number) (cell_value one two))
			(<> (value_of ?number) (cell_value one three))
			(<> (value_of ?number) (cell_value two one))
			(<> (value_of ?number) (cell_value two two))
			(<> (value_of ?number) (cell_value two three))
			(<> (value_of ?number) (cell_value three one))
			(<> (value_of ?number) (cell_value three two))
			(<> (value_of ?number) (cell_value three three)))
		:effect (and
			(value_assigned ?row ?col)
			(assign
				(cell_value ?row ?col)
				(value_of ?number)))
	)

	(:action ASSIGN_2-1
		:parameters (?row ?col ?number)
		:precondition (and
			(not (value_assigned ?row ?col))
			(> (value_of ?row) 3)
			(< (value_of ?row) 7)
			(< (value_of ?col) 4)

			(<> (value_of ?number) (cell_value ?row one))
			(<> (value_of ?number) (cell_value ?row two))
			(<> (value_of ?number) (cell_value ?row three))
			(<> (value_of ?number) (cell_value ?row four))
			(<> (value_of ?number) (cell_value ?row five))
			(<> (value_of ?number) (cell_value ?row six))
			(<> (value_of ?number) (cell_value ?row seven))
			(<> (value_of ?number) (cell_value ?row eight))
			(<> (value_of ?number) (cell_value ?row nine))

			(<> (value_of ?number) (cell_value one ?col))
			(<> (value_of ?number) (cell_value two ?col))
			(<> (value_of ?number) (cell_value three ?col))
			(<> (value_of ?number) (cell_value four ?col))
			(<> (value_of ?number) (cell_value five ?col))
			(<> (value_of ?number) (cell_value six ?col))
			(<> (value_of ?number) (cell_value seven ?col))
			(<> (value_of ?number) (cell_value eight ?col))
			(<> (value_of ?number) (cell_value nine ?col))

			(<> (value_of ?number) (cell_value four one))
			(<> (value_of ?number) (cell_value four two))
			(<> (value_of ?number) (cell_value four three))
			(<> (value_of ?number) (cell_value five one))
			(<> (value_of ?number) (cell_value five two))
			(<> (value_of ?number) (cell_value five three))
			(<> (value_of ?number) (cell_value six one))
			(<> (value_of ?number) (cell_value six two))
			(<> (value_of ?number) (cell_value six three)))
		:effect (and
			(value_assigned ?row ?col)
			(assign
				(cell_value ?row ?col)
				(value_of ?number)))
	)

	(:action ASSIGN_3-1
		:parameters (?row ?col ?number)
		:precondition (and
			(not (value_assigned ?row ?col))
			(> (value_of ?row) 6)
			(< (value_of ?col) 4)

			(<> (value_of ?number) (cell_value ?row one))
			(<> (value_of ?number) (cell_value ?row two))
			(<> (value_of ?number) (cell_value ?row three))
			(<> (value_of ?number) (cell_value ?row four))
			(<> (value_of ?number) (cell_value ?row five))
			(<> (value_of ?number) (cell_value ?row six))
			(<> (value_of ?number) (cell_value ?row seven))
			(<> (value_of ?number) (cell_value ?row eight))
			(<> (value_of ?number) (cell_value ?row nine))

			(<> (value_of ?number) (cell_value one ?col))
			(<> (value_of ?number) (cell_value two ?col))
			(<> (value_of ?number) (cell_value three ?col))
			(<> (value_of ?number) (cell_value four ?col))
			(<> (value_of ?number) (cell_value five ?col))
			(<> (value_of ?number) (cell_value six ?col))
			(<> (value_of ?number) (cell_value seven ?col))
			(<> (value_of ?number) (cell_value eight ?col))
			(<> (value_of ?number) (cell_value nine ?col))

			(<> (value_of ?number) (cell_value seven one))
			(<> (value_of ?number) (cell_value seven two))
			(<> (value_of ?number) (cell_value seven three))
			(<> (value_of ?number) (cell_value eight one))
			(<> (value_of ?number) (cell_value eight two))
			(<> (value_of ?number) (cell_value eight three))
			(<> (value_of ?number) (cell_value nine one))
			(<> (value_of ?number) (cell_value nine two))
			(<> (value_of ?number) (cell_value nine three)))
		:effect (and
			(value_assigned ?row ?col)
			(assign
				(cell_value ?row ?col)
				(value_of ?number)))
	)

	(:action ASSIGN_1-2
		:parameters (?row ?col ?number)
		:precondition (and
			(not (value_assigned ?row ?col))
			(< (value_of ?row) 4)
			(> (value_of ?col) 3)
			(< (value_of ?col) 7)

			(<> (value_of ?number) (cell_value ?row one))
			(<> (value_of ?number) (cell_value ?row two))
			(<> (value_of ?number) (cell_value ?row three))
			(<> (value_of ?number) (cell_value ?row four))
			(<> (value_of ?number) (cell_value ?row five))
			(<> (value_of ?number) (cell_value ?row six))
			(<> (value_of ?number) (cell_value ?row seven))
			(<> (value_of ?number) (cell_value ?row eight))
			(<> (value_of ?number) (cell_value ?row nine))

			(<> (value_of ?number) (cell_value one ?col))
			(<> (value_of ?number) (cell_value two ?col))
			(<> (value_of ?number) (cell_value three ?col))
			(<> (value_of ?number) (cell_value four ?col))
			(<> (value_of ?number) (cell_value five ?col))
			(<> (value_of ?number) (cell_value six ?col))
			(<> (value_of ?number) (cell_value seven ?col))
			(<> (value_of ?number) (cell_value eight ?col))
			(<> (value_of ?number) (cell_value nine ?col))

			(<> (value_of ?number) (cell_value one four))
			(<> (value_of ?number) (cell_value one five))
			(<> (value_of ?number) (cell_value one six))
			(<> (value_of ?number) (cell_value two four))
			(<> (value_of ?number) (cell_value two five))
			(<> (value_of ?number) (cell_value two six))
			(<> (value_of ?number) (cell_value three four))
			(<> (value_of ?number) (cell_value three five))
			(<> (value_of ?number) (cell_value three six)))
		:effect (and
			(value_assigned ?row ?col)
			(assign
				(cell_value ?row ?col)
				(value_of ?number)))
	)

	(:action ASSIGN_2-2
		:parameters (?row ?col ?number)
		:precondition (and
			(not (value_assigned ?row ?col))
			(> (value_of ?row) 3)
			(< (value_of ?row) 7)
			(> (value_of ?col) 3)
			(< (value_of ?col) 7)

			(<> (value_of ?number) (cell_value ?row one))
			(<> (value_of ?number) (cell_value ?row two))
			(<> (value_of ?number) (cell_value ?row three))
			(<> (value_of ?number) (cell_value ?row four))
			(<> (value_of ?number) (cell_value ?row five))
			(<> (value_of ?number) (cell_value ?row six))
			(<> (value_of ?number) (cell_value ?row seven))
			(<> (value_of ?number) (cell_value ?row eight))
			(<> (value_of ?number) (cell_value ?row nine))

			(<> (value_of ?number) (cell_value one ?col))
			(<> (value_of ?number) (cell_value two ?col))
			(<> (value_of ?number) (cell_value three ?col))
			(<> (value_of ?number) (cell_value four ?col))
			(<> (value_of ?number) (cell_value five ?col))
			(<> (value_of ?number) (cell_value six ?col))
			(<> (value_of ?number) (cell_value seven ?col))
			(<> (value_of ?number) (cell_value eight ?col))
			(<> (value_of ?number) (cell_value nine ?col))

			(<> (value_of ?number) (cell_value four four))
			(<> (value_of ?number) (cell_value four five))
			(<> (value_of ?number) (cell_value four six))
			(<> (value_of ?number) (cell_value five four))
			(<> (value_of ?number) (cell_value five five))
			(<> (value_of ?number) (cell_value five six))
			(<> (value_of ?number) (cell_value six four))
			(<> (value_of ?number) (cell_value six five))
			(<> (value_of ?number) (cell_value six six)))
		:effect (and
			(value_assigned ?row ?col)
			(assign
				(cell_value ?row ?col)
				(value_of ?number)))
	)

	(:action ASSIGN_3-2
		:parameters (?row ?col ?number)
		:precondition (and
			(not (value_assigned ?row ?col))
			(> (value_of ?row) 6)
			(> (value_of ?col) 3)
			(< (value_of ?col) 7)

			(<> (value_of ?number) (cell_value ?row one))
			(<> (value_of ?number) (cell_value ?row two))
			(<> (value_of ?number) (cell_value ?row three))
			(<> (value_of ?number) (cell_value ?row four))
			(<> (value_of ?number) (cell_value ?row five))
			(<> (value_of ?number) (cell_value ?row six))
			(<> (value_of ?number) (cell_value ?row seven))
			(<> (value_of ?number) (cell_value ?row eight))
			(<> (value_of ?number) (cell_value ?row nine))

			(<> (value_of ?number) (cell_value one ?col))
			(<> (value_of ?number) (cell_value two ?col))
			(<> (value_of ?number) (cell_value three ?col))
			(<> (value_of ?number) (cell_value four ?col))
			(<> (value_of ?number) (cell_value five ?col))
			(<> (value_of ?number) (cell_value six ?col))
			(<> (value_of ?number) (cell_value seven ?col))
			(<> (value_of ?number) (cell_value eight ?col))
			(<> (value_of ?number) (cell_value nine ?col))

			(<> (value_of ?number) (cell_value seven four))
			(<> (value_of ?number) (cell_value seven five))
			(<> (value_of ?number) (cell_value seven six))
			(<> (value_of ?number) (cell_value eight four))
			(<> (value_of ?number) (cell_value eight five))
			(<> (value_of ?number) (cell_value eight six))
			(<> (value_of ?number) (cell_value nine four))
			(<> (value_of ?number) (cell_value nine five))
			(<> (value_of ?number) (cell_value nine six)))
		:effect (and
			(value_assigned ?row ?col)
			(assign
				(cell_value ?row ?col)
				(value_of ?number)))
	)

	(:action ASSIGN_1-3
		:parameters (?row ?col ?number)
		:precondition (and
			(not (value_assigned ?row ?col))
			(< (value_of ?row) 4)
			(> (value_of ?col) 6)

			(<> (value_of ?number) (cell_value ?row one))
			(<> (value_of ?number) (cell_value ?row two))
			(<> (value_of ?number) (cell_value ?row three))
			(<> (value_of ?number) (cell_value ?row four))
			(<> (value_of ?number) (cell_value ?row five))
			(<> (value_of ?number) (cell_value ?row six))
			(<> (value_of ?number) (cell_value ?row seven))
			(<> (value_of ?number) (cell_value ?row eight))
			(<> (value_of ?number) (cell_value ?row nine))

			(<> (value_of ?number) (cell_value one ?col))
			(<> (value_of ?number) (cell_value two ?col))
			(<> (value_of ?number) (cell_value three ?col))
			(<> (value_of ?number) (cell_value four ?col))
			(<> (value_of ?number) (cell_value five ?col))
			(<> (value_of ?number) (cell_value six ?col))
			(<> (value_of ?number) (cell_value seven ?col))
			(<> (value_of ?number) (cell_value eight ?col))
			(<> (value_of ?number) (cell_value nine ?col))

			(<> (value_of ?number) (cell_value one seven))
			(<> (value_of ?number) (cell_value one eight))
			(<> (value_of ?number) (cell_value one nine))
			(<> (value_of ?number) (cell_value two seven))
			(<> (value_of ?number) (cell_value two eight))
			(<> (value_of ?number) (cell_value two nine))
			(<> (value_of ?number) (cell_value three seven))
			(<> (value_of ?number) (cell_value three eight))
			(<> (value_of ?number) (cell_value three nine)))
		:effect (and
			(value_assigned ?row ?col)
			(assign
				(cell_value ?row ?col)
				(value_of ?number)))
	)

	(:action ASSIGN_2-3
		:parameters (?row ?col ?number)
		:precondition (and
			(not (value_assigned ?row ?col))
			(> (value_of ?row) 3)
			(< (value_of ?row) 7)
			(> (value_of ?col) 6)

			(<> (value_of ?number) (cell_value ?row one))
			(<> (value_of ?number) (cell_value ?row two))
			(<> (value_of ?number) (cell_value ?row three))
			(<> (value_of ?number) (cell_value ?row four))
			(<> (value_of ?number) (cell_value ?row five))
			(<> (value_of ?number) (cell_value ?row six))
			(<> (value_of ?number) (cell_value ?row seven))
			(<> (value_of ?number) (cell_value ?row eight))
			(<> (value_of ?number) (cell_value ?row nine))

			(<> (value_of ?number) (cell_value one ?col))
			(<> (value_of ?number) (cell_value two ?col))
			(<> (value_of ?number) (cell_value three ?col))
			(<> (value_of ?number) (cell_value four ?col))
			(<> (value_of ?number) (cell_value five ?col))
			(<> (value_of ?number) (cell_value six ?col))
			(<> (value_of ?number) (cell_value seven ?col))
			(<> (value_of ?number) (cell_value eight ?col))
			(<> (value_of ?number) (cell_value nine ?col))

			(<> (value_of ?number) (cell_value four seven))
			(<> (value_of ?number) (cell_value four eight))
			(<> (value_of ?number) (cell_value four nine))
			(<> (value_of ?number) (cell_value five seven))
			(<> (value_of ?number) (cell_value five eight))
			(<> (value_of ?number) (cell_value five nine))
			(<> (value_of ?number) (cell_value six seven))
			(<> (value_of ?number) (cell_value six eight))
			(<> (value_of ?number) (cell_value six nine)))
		:effect (and
			(value_assigned ?row ?col)
			(assign
				(cell_value ?row ?col)
				(value_of ?number)))
	)

	(:action ASSIGN_3-3
		:parameters (?row ?col ?number)
		:precondition (and
			(not (value_assigned ?row ?col))
			(> (value_of ?row) 6)
			(> (value_of ?col) 6)

			(<> (value_of ?number) (cell_value ?row one))
			(<> (value_of ?number) (cell_value ?row two))
			(<> (value_of ?number) (cell_value ?row three))
			(<> (value_of ?number) (cell_value ?row four))
			(<> (value_of ?number) (cell_value ?row five))
			(<> (value_of ?number) (cell_value ?row six))
			(<> (value_of ?number) (cell_value ?row seven))
			(<> (value_of ?number) (cell_value ?row eight))
			(<> (value_of ?number) (cell_value ?row nine))

			(<> (value_of ?number) (cell_value one ?col))
			(<> (value_of ?number) (cell_value two ?col))
			(<> (value_of ?number) (cell_value three ?col))
			(<> (value_of ?number) (cell_value four ?col))
			(<> (value_of ?number) (cell_value five ?col))
			(<> (value_of ?number) (cell_value six ?col))
			(<> (value_of ?number) (cell_value seven ?col))
			(<> (value_of ?number) (cell_value eight ?col))
			(<> (value_of ?number) (cell_value nine ?col))

			(<> (value_of ?number) (cell_value seven seven))
			(<> (value_of ?number) (cell_value seven eight))
			(<> (value_of ?number) (cell_value seven nine))
			(<> (value_of ?number) (cell_value eight seven))
			(<> (value_of ?number) (cell_value eight eight))
			(<> (value_of ?number) (cell_value eight nine))
			(<> (value_of ?number) (cell_value nine seven))
			(<> (value_of ?number) (cell_value nine eight))
			(<> (value_of ?number) (cell_value nine nine)))
		:effect (and
			(value_assigned ?row ?col)
			(assign
				(cell_value ?row ?col)
				(value_of ?number)))
	)
)
