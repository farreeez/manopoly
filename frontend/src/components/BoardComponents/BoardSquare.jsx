import { useEffect, useState } from "react";
import "./css/BoardSquare.css";

function getProperty(propertyId, setProperty) {
  fetch("http://localhost:8080/cardActions/getProperty/" + propertyId, {
    method: "get",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("board does not exist.");
      }
      return response.json();
    })
    .then((data) => {
      setProperty(data);
      console.log("hello");

      if (data.colour) {
        console.log(data.colour);
      }
    })
    .catch((error) => console.error(error));
}

function getsquare(squareId, setSquare, setProperty, getProperty) {
  fetch("http://localhost:8080/board/getBoardSquare/" + squareId, {
    method: "GET",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Board does not exist.");
      }
      return response.json();
    })
    .then((data) => {
      setSquare(data);

      if (data.property) {
        getProperty(data.id, setProperty);
      }
    })
    .catch((error) => console.error(error));
}

function BoardSquare({ player, setPlayer, width, height, squareId }) {
  const [square, setSquare] = useState();
  const [property, setProperty] = useState();

  useEffect(() => {
    if (squareId) {
      getsquare(squareId, setSquare, setProperty, getProperty);
    }
  }, [squareId]);

  return (
    <div className="boardSquare" style={{ width: width, height: height }}>
      {square && square.property && property && property.colour ? (
        <div
          className="boardSquareColour"
          style={{
            backgroundColor: `rgb(${property.colour.red},${property.colour.green},${property.colour.blue})`,
          }}
        ></div>
      ) : (
        <div>
          <br></br>
          <br></br>
        </div>
      )}
      {square && square.name && <span>{square.name}</span>}
      {height > width && <br></br>}
      {square && Number(square.price) > 0 && <span>{square.price}</span>}
      <div className="circleContainer" style={{ width: width }}></div>
    </div>
  );
}

export default BoardSquare;
