import { useEffect, useState } from "react";

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
      console.log(data)
    })
    .catch((error) => console.error(error));
}

function BoardSquare({ player, setPlayer, width, height, squareId }) {
  const [square, setSquare] = useState();

  useEffect(() => {
    if (squareId) {
      getSquare(squareId, setSquare);
    }
  }, []);

  return (
    <div className="boardSquare" style={{ width: width, height: height }}>
      {square && square.name && <span>{square.name}</span>}
      <br></br>
      <br></br>
      {square && Number(square.price) > 0 && <span>{square.price}</span>}
    </div>
  );
}

export default BoardSquare;
