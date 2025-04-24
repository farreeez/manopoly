import { useContext, useEffect, useState } from "react";
import { getProperty } from "../../services/CardActionServices";
import { getsquare } from "../../services/BoardServices";
import "./css/BoardSquare.css";
import { BoardContext } from "../../context/BoardContextProvider";

function BoardSquare({
  width,
  height,
  squareId,
  refreshSquare,
  setDisplayProperty,
}) {
  const [square, setSquare] = useState();
  const [property, setProperty] = useState();
  const { setModalProperty } = useContext(BoardContext);

  useEffect(() => {
    if (squareId) {
      getsquare(squareId, setSquare, setProperty, getProperty);
    }
  }, [squareId, refreshSquare]);

  return (
    <div
      className="boardSquare"
      style={{ width: width, height: height }}
      onClick={
        square && square.property && property
          ? () => {
              setDisplayProperty(true);
              setModalProperty(property);
            }
          : undefined
      }
    >
      {square && square.name && <span>{square.name}</span>}
      {height > width && <br></br>}
      <div>
        <br></br>
      </div>
      {square && square.property && property && property.playerColour ? (
        <div
          className="boardSquareColour"
          style={{
            backgroundColor: `rgb(${property.playerColour.red},${property.playerColour.green},${property.playerColour.blue})`,
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
