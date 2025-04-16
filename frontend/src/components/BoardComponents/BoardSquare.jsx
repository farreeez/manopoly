import { useEffect, useState } from "react";
import { getProperty } from "../../services/CardActionServices";
import { getsquare } from "../../services/BoardServices";
import "./css/BoardSquare.css";

function BoardSquare({ player, setPlayer, width, height, squareId , refreshSquare}) {
  const [square, setSquare] = useState();
  const [property, setProperty] = useState();

  useEffect(() => {
    if (squareId) {
      getsquare(squareId, setSquare, setProperty, getProperty);
    }
  }, [squareId, refreshSquare]);

  return (
    <div className="boardSquare" style={{ width: width, height: height }}>
      {square && square.name && <span>{square.name}</span>}
      {height > width && <br></br>}
      <div>
        <br></br>
      </div>
      {square && square.property && property && property.colour ? (
        <div
          className="boardSquareColour"
          style={{
            backgroundColor: `rgb(${property.colour.red},${property.colour.green},${property.colour.blue})`,
          }}
        ></div>
      ) : (
        square && Number(square.price) > 0 && <span>{square.price}</span>
      )}
      <div className="circleContainer" style={{ width: width }}></div>
    </div>
  );
}

export default BoardSquare;
