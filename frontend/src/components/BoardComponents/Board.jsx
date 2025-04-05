import BoardSquare from "./BoardSquare";
import { useEffect, useState } from "react";
import "./css/Board.css";
import ColourSelection from "./ColourSelection";
import DiceRoll from "./DiceRoll";

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

// update it so that it does not refresh the entire board.
// make the dice roll in sync with the player movement.
// animate the player movement.
export async function updatePositions(playerIds) {
  // remove this later
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

function playerJoined(oldBoard, board) {
  if (oldBoard && oldBoard.playerIds.length != board.playerIds.length) {
    return true;
  }

  return false;
}


function subscribe(player, oldBoard, setBoard, resubscribe, setResubscribe) {
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
      console.log("resetting board")

      if (playerJoined(oldBoard, board)) {
        updatePositions(board.playerIds);
      }
    })
    .catch((error) => {
      console.log("error with resubscribing")
      console.log(error);
    });
}

// function process(player) {
//   fetch("http://localhost:8080/board/processSubs/" + player.boardId, {
//     method: "GET",
//   })
//     .then((response) => {
//       if (!response.ok) {

//         throw new Error("unable to process board.");
//       }
//     })
//     .then(() => {})
//     .catch((error) => console.error(error));
// }

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
      updatePositions(data.playerIds);
    })
    .catch((error) => console.error(error));
}

function Board({ player, setPlayer }) {
  const [squares, setSquares] = useState([]);
  const [board, setBoard] = useState();
  const [resubscribe, setResubscribe] = useState(-1);

  // this is different from player object in the app state as it has all of the player dto elements
  const [playerDTO, setPlayerDTO] = useState({ money: 0 });

  useEffect(() => {
    getBoard(player, setSquares, setBoard);
    subscribe(player, board, setBoard, resubscribe);
  }, []);

  useEffect(() => {
    subscribe(player, board, setBoard, resubscribe, setResubscribe);
  }, [resubscribe]);

  useEffect(() => {
    const fetchData = async () => {
      if (board) {
        try {
          const playerData = await getPlayerJson(player.id);
          setPlayerDTO(playerData);
        } catch (error) {
          console.error("Error fetching player data:", error);
        }
      }
    };

    fetchData();
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

        {/* <button
          className="button"
          onClick={() =>
            subscribe(player, setBoard, resubscribe, setResubscribe)
          }
        >
          subscribe
        </button>
        <button className="button" onClick={() => process(player)}>
          processSubs
        </button> */}
        <h1 className="boardHeaders">Board Code: {player.boardId}</h1>

        <h2 className="boardHeaders">Player Money: {playerDTO.money}</h2>
      </div>
      <DiceRoll board={board} player={player} />
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
