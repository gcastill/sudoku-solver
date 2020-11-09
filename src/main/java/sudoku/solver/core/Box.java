package sudoku.solver.core;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Value
public class Box {

   private final Coordinate coordinate;
   private final Map<Coordinate, Cell> cells =new HashMap<>();

   public Cell getCell(Coordinate coordinate){
      return cells.get(coordinate);
   }

}
