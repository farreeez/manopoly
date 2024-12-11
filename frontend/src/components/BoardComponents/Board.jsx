import BoardSquare from "./BoardSquare";
import { useEffect, useState } from "react";
import "./css/Board.css";
import ColourSelection from "./ColourSelection";
import PlayerMovement from "./PlayerMovement";

let boardId = -1;

async function getPlayerJson(playerId) {
  try {
    const response = await fetch(
      `http://localhost:8080/players/getPlayer/${playerId}`,
      {
        method: "GET",
      }
    );

    if (!response.ok) {
      throw new Error("player id does not exist in function getPlayerJson.");
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error(error);
    throw error; // Re-throw the error if you want the caller to handle it
  }
}

async function updatePositions(playerIds) {
  for (let i = 0; i < 40; i++) {
    const playerSquare = document.getElementById(i);

    if (playerSquare) {
      playerSquare.querySelector(".circleContainer").innerHTML = "";
    }
  }

  for (let i = 0; i < playerIds.length; i++) {
    const playerJson = await getPlayerJson(playerIds[i]);

    console.log(playerJson.position);

    const playerSquare = document.getElementById(playerJson.position);
    const existingCircle = document.getElementById("colour" + i);

    if (playerSquare && playerJson.colour && !existingCircle) {
      // Create a new div element for the circle
      const circleOverlay = document.createElement("div");

      circleOverlay.id = "colour" + i;

      Object.assign(circleOverlay.style, {
        width: "15px",
        height: "15px",
        backgroundColor: `rgb(${playerJson.colour.red},${playerJson.colour.green},${playerJson.colour.blue})`,
        borderRadius: "50%",
        pointerEvents: "none",
        outline: "1px solid black",
        margin: "0px 2px",
      });

      // Add the circle div to the playerSquare
      let child = playerSquare.querySelector(".circleContainer");
      child.appendChild(circleOverlay);
    }
  }
}


function subscribe(player, setBoard, resubscribe, setResubscribe) {
  fetch("http://localhost:8080/board/subscribeToBoard/" + player.boardId, {
    method: "GET",
  })
    .then(async (response) => {
      if (!response.ok) {
        const message = await response.text();
        console.error("Error message from response:", message);
      }
      setResubscribe(-1 * resubscribe);

      return response.text();
    })
    .then((data) => {
      let board = JSON.parse(data);
      setBoard(board);
      console.log("working");
    })
    .catch((error) => {
      console.error(error);
    });
}

function process(player) {
  fetch("http://localhost:8080/board/processSubs/" + player.boardId, {
    method: "GET",
  })
    .then((response) => {
      if (!response.ok) {

        throw new Error("unable to process board.");
      }
    })
    .then(() => {})
    .catch((error) => console.error(error));
}

function leaveBoard(player, setPlayer, setBoard) {
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

      setBoard(null);
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
  const [resubscribe, setResubscribe] = useState(-1);

  useEffect(() => {
    subscribe(player, setBoard);
  }, []);

  useEffect(() => {
    subscribe(player, setBoard, resubscribe, setResubscribe);
  }, [resubscribe]);

  useEffect(() => {
    if (board) {
      updatePositions(board.playerIds);
    }
  }, [board]);

  useEffect(() => {
    boardId = player.boardId;
    getBoard(player, setSquares, setBoard);
  }, [player]);

  // TODO: make a better solution to draw the board
  return (
    <div>
      <div id="headerContainer">
        <button
          id="leaveButton"
          className="button"
          onClick={() => leaveBoard(player, setPlayer, setBoard)}
        >
          Leave Board
        </button>

        <button
          className="button"
          onClick={() =>
            subscribe(player, setBoard, resubscribe, setResubscribe)
          }
        >
          subscribe
        </button>
        <button className="button" onClick={() => process(player)}>
          processSubs
        </button>
        <h1 id="boardId">Board Code: {player.boardId}</h1>
      </div>
      <PlayerMovement board={board}/>
      {squares.length ? (
        <div>
          <ul id="topRowBoardSquares">
            <li key={0} id="0">
              <BoardSquare
                width={"90px"}
                height={"90px"}
                squareId={squares[0]}
              />
            </li>
            {squares.slice(1, 10).map((square, index) => (
              <li key={index} id={index + 1}>
                <BoardSquare width={"70px"} height={"90px"} squareId={square} />
              </li>
            ))}
            <li key={10} id="10">
              <BoardSquare
                width={"90px"}
                height={"90px"}
                squareId={squares[10]}
              />
            </li>
          </ul>

          <ul id="rightColBoardSquares">
            {squares.slice(11, 20).map((square, index) => (
              <li key={index} id={index + 11}>
                <BoardSquare width={"90px"} height={"70px"} squareId={square} />
              </li>
            ))}
          </ul>

          <ul id="bottomRowBoardSquares">
            <li key={30} id="30">
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
                <li key={index} id={8 - index + 21}>
                  <BoardSquare
                    width={"70px"}
                    height={"90px"}
                    squareId={square}
                  />
                </li>
              ))}
            <li key={20} id="20">
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
                <li key={index} id={8 - index + 31}>
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

      {board && !player.colour && (
        <ColourSelection
          possibleColours={board.possibleColours}
          takenColours={board.takenColours}
          setPlayer={setPlayer}
        />
      )}
    </div>
  );
}

export default Board;
