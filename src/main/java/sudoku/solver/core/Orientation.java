package sudoku.solver.core;

public enum Orientation {

    VERTICAL() {
        @Override
        public int indexFor(Coordinate coordinate) {
            return coordinate.getX();
        }
    },
    HORIZONTAL() {
        @Override
        public int indexFor(Coordinate coordinate) {
            return coordinate.getY();
        }
    };


    public Orientation opposite() {
        return Orientation.values()[(ordinal() + 1) % 2];
    }

    public LineAddress lineAddressFor(Coordinate coordinate) {
        return new LineAddress(indexFor(coordinate), this);
    }
    
    public int indexFor(Coordinate coordinate) {
        throw new AbstractMethodError();
    }

}
