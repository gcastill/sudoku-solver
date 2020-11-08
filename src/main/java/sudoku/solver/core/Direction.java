package sudoku.solver.core;

public enum Direction {

    NORTH {
        @Override
        public Coordinate getNext(Coordinate coordinate) {
            return new Coordinate(coordinate.getX(),  coordinate.getY() +1);
        }
    },
    SOUTH {
        @Override
        public Coordinate getNext(Coordinate coordinate) {
            return new Coordinate(coordinate.getX(), coordinate.getY() - 1);
        }
    },
    EAST {
        @Override
        public Coordinate getNext(Coordinate coordinate) {
            return new Coordinate(coordinate.getX() + 1, coordinate.getY());
        }
    },
    WEST {
        @Override
        public Coordinate getNext(Coordinate coordinate) {
            return new Coordinate(coordinate.getX() - 1, coordinate.getY());
        }
    },
    ;

    public Coordinate getNext(Coordinate coordinate) {
        throw new AbstractMethodError();
    }


}
