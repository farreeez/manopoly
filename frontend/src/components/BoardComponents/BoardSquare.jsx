import { useEffect, useState } from "react";
import "./css/BoardSquare.css"

function getSquare(squareId, setSquare) {
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
    })
    .catch((error) => console.error(error));
}

function BoardSquare({ player, setPlayer, width, height, squareId }) {
  const [square, setSquare] = useState();

  useEffect(() => {
    if (squareId) {
      getSquare(squareId, setSquare);
    }
  }, [squareId]);

  return (
    <div className="boardSquare" style={{ width: width, height: height }}>
      {square && square.name && <span>{square.name}</span>}
      <br></br>
      <br></br>
      {height > width && <br></br>}
      {square && Number(square.price) > 0 && <span>{square.price}</span>}
      <div className="circleContainer" style={{ width: width }}></div>
    </div>
  );
}

export default BoardSquare;
