import BoardSquare from "./BoardSquare";
import { useEffect, useState } from "react";
import "./css/Board.css";
import ColourSelection from "./ColourSelection";

let boardId = -1;
let counter = 0;
let oldNum = 0;

function subscribe(player, setBoard) {
  console.log("ran");
  oldNum = counter;
  fetch("http://localhost:8080/board/subscribeToBoard/" + player.boardId, {
    method: "GET",
  })
    .then((response) => {
      if (!response.ok) {
        counter++;
        throw new Error("Issue with subscribing to board.");
      }

      return response.text();
    })
    .then((data) => {
      let board = JSON.parse(data);
      console.log(board);
      setBoard(board);
      counter++;
    })
    .catch((error) => console.error(error));
}

function process(player) {
  fetch("http://localhost:8080/board/processSubs/" + player.boardId, {
    method: "GET",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("unable to process board.");
      }

      console.log("processed");
    })
    .then(() => {})
    .catch((error) => console.error(error));
}

function leaveBoard(player, setPlayer) {
  fetch("http://localhost:8080/board/leaveBoard", {
    method: "DELETE",
    credentials: "include",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Player does not exist (cannot leave board).");
      }
      return response;
    })
    .then(() => {
      boardId = -1;
      setPlayer({
        name: player.name,
        id: Number(player.id),
        isLoggedIn: true,
        boardId: -1,
        colour: undefined,
      });
    })
    .catch((error) => console.error(error));
}

function getBoard(player, setSquares, setBoard) {
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
      setBoard(data);
    })
    .catch((error) => console.error(error));
}

function Board({ player, setPlayer }) {
  const [squares, setSquares] = useState([]);
  const [board, setBoard] = useState();

  useEffect(() => {
    if (boardId !== -1 && counter > oldNum) {
      subscribe(player, setBoard);
    }
  });

  useEffect(() => {
    boardId = player.boardId;
    getBoard(player, setSquares, setBoard);
    subscribe(player, setBoard);
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

        <button className="button" onClick={() => subscribe(player, setBoard)}>
          subscribe
        </button>
        <button className="button" onClick={() => process(player)}>
          processSubs
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
            <li key={30}>
              <BoardSquare
                width={"90px"}
                height={"90px"}
                squareId={squares[30]}
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
            <li key={20}>
              <BoardSquare
                width={"90px"}
                height={"90px"}
                squareId={squares[20]}
              />
            </li>
          </ul>

          <ul id="leftColBoardSquares">
            {squares
              .slice(31, 40)
              .reverse()
              .map((square, index) => (
                <li key={index}>
                  <BoardSquare
                    width={"90px"}
                    height={"70px"}
                    squareId={square}
                  />
                </li>
              ))}
          </ul>
        </div>
      ) : (
        <div></div>
      )}

      {board && !player.colour && <ColourSelection possibleColours={board.possibleColours} takenColours={board.takenColours} setPlayer={setPlayer} />}
    </div>
  );
}

export default Board;
