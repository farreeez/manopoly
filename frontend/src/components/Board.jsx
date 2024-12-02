import BoardSquare from "./BoardSquare";
import { useEffect, useState } from "react";

function leaveBoard(player, setPlayer) {
  fetch("http://localhost:8080/board/leaveBoard", {
    method: "DELETE",
    credentials: "include",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Player does not exist.");
      }
      return response;
    })
    .then(() => {
      setPlayer({
        name: player.name,
        id: Number(player.id),
        isLoggedIn: true,
        boardId: -1,
      });
    })
    .catch((error) => console.error(error));
}

function getBoard(player, setSquares) {
  fetch("http://localhost:8080/board/getBoard/" + player.boardId, {
    method: "GET",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Board does not exist.");
      }
      return response.json();
    })
    .then((data) => {
      setSquares(data.squareIds);
      console.log("happened");
    })
    .catch((error) => console.error(error));
}

function Board({ player, setPlayer }) {
  const [squares, setSquares] = useState([]);

  useEffect(() => {
    getBoard(player, setSquares);
  }, [player]);

  // TODO: make a better solution to draw the board
  return (
    <div>
      <div id="headerContainer">
        <button
          id="leaveButton"
          className="button"
          onClick={() => leaveBoard(player, setPlayer)}
        >
          Leave Board
        </button>
        <h1 id="boardId">Board Code: {player.boardId}</h1>
      </div>
      {squares.length ? (
        <div>
          <ul id="topRowBoardSquares">
            <li key={0}>
              <BoardSquare
                width={"90px"}
                height={"90px"}
                squareId={squares[0]}
              />
            </li>
            {squares.slice(1, 10).map((square, index) => (
              <li key={index}>
                <BoardSquare width={"70px"} height={"90px"} squareId={square} />
              </li>
            ))}
            <li key={10}>
              <BoardSquare
                width={"90px"}
                height={"90px"}
                squareId={squares[10]}
              />
            </li>
          </ul>

          <ul id="rightColBoardSquares">
            {squares.slice(11, 20).map((square, index) => (
              <li key={index}>
                <BoardSquare width={"90px"} height={"70px"} squareId={square} />
              </li>
            ))}
          </ul>

          <ul id="bottomRowBoardSquares">
            <li key={20}>
              <BoardSquare
                width={"90px"}
                height={"90px"}
                squareId={squares[20]}
              />
            </li>
            {squares
              .slice(21, 30)
              .reverse()
              .map((square, index) => (
                <li key={index}>
                  <BoardSquare
                    width={"70px"}
                    height={"90px"}
                    squareId={square}
                  />
                </li>
              ))}
            <li key={30}>
              <BoardSquare
                width={"90px"}
                height={"90px"}
                squareId={squares[30]}
              />
            </li>
          </ul>

          <ul id="leftColBoardSquares">
            {squares.slice(31, 40).map((square, index) => (
              <li key={index}>
                <BoardSquare width={"90px"} height={"70px"} squareId={square} />
              </li>
            ))}
          </ul>
        </div>
      ) : (
        <div></div>
      )}
    </div>
  );
}

export default Board;
