import BoardSquare from "./BoardSquare";
import { useContext, useEffect, useState } from "react";
import "./css/Board.css";
import ColourSelection from "./ColourSelection";
import DiceRoll from "./DiceRoll";
import { getPlayerJson } from "../../services/PlayerServices";
import { getBoard, leaveBoard } from "../../services/BoardServices";
import { AppContext } from "../../context/AppContextProvider";
import { DiceRollContextProvider } from "../../context/DiceRollContextProvider";
import PropertyCard from "./PropertyCard";

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

function Board() {
  const [squares, setSquares] = useState([]);
  const [resubscribe, setResubscribe] = useState(-1);
  const [refreshSquares, setRefreshSquares] = useState(new Array(40).fill(0));
  const [displayProperty, setDisplayProperty] = useState(false);
  // this is different from player object in the app state as it has all of the player dto elements

  const { player, setPlayer, board, setBoard, playerDTO, setPlayerDTO } =
    useContext(AppContext);

  useEffect(() => {
    getBoard(player, setSquares, setBoard);
    subscribe();
  }, []);

  useEffect(() => {
    subscribe();
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

    if (board && board.lastBoughtPosition !== -1) {
      setRefreshSquares((prev) => {
        const newRefreshSquares = [...prev];
        newRefreshSquares[board.lastBoughtPosition] =
          (prev[board.lastBoughtPosition] || 0) + 1;
        return newRefreshSquares;
      });
    }
  }, [board]);

  useEffect(() => {
    getBoard(player, setSquares, setBoard);
  }, [player]);

  function subscribe() {
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
        let newBoard = JSON.parse(data);

        //if true change some sort of state value that then runs a hook in the DiceRoll.jsx to update the UI
        if (playerJoined(board, newBoard)) {
          updatePositions(newBoard.playerIds);
        }

        setBoard(newBoard);
      })
      .catch((error) => {
        console.log("error with resubscribing");
        console.log(error);
      });
  }

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
        <h1 className="boardHeaders">Board Code: {player.boardId}</h1>

        <h2 className="boardHeaders">Player Money: {playerDTO.money}</h2>
      </div>
      <DiceRollContextProvider>
        <DiceRoll
          rollDiceAction={board ? board.rollDiceAction : false}
          diceRolls={board ? board.diceRolls : []}
          setRefreshSquares={setRefreshSquares}
        />
      </DiceRollContextProvider>

      {squares.length ? (
        <div>
          <ul id="topRowBoardSquares">
            <li key={0} id="0">
              <BoardSquare
                width={"90px"}
                height={"90px"}
                squareId={squares[0]}
                refreshSquare={refreshSquares[0]}
                setDisplayProperty={setDisplayProperty}
              />
            </li>
            {squares.slice(1, 10).map((square, index) => (
              <li key={index} id={index + 1}>
                <BoardSquare
                  width={"70px"}
                  height={"90px"}
                  squareId={square}
                  refreshSquare={refreshSquares[index + 1]}
                  setDisplayProperty={setDisplayProperty}
                />
              </li>
            ))}
            <li key={10} id="10">
              <BoardSquare
                width={"90px"}
                height={"90px"}
                squareId={squares[10]}
                refreshSquare={refreshSquares[10]}
                setDisplayProperty={setDisplayProperty}
              />
            </li>
          </ul>

          <ul id="rightColBoardSquares">
            {squares.slice(11, 20).map((square, index) => (
              <li key={index} id={index + 11}>
                <BoardSquare
                  width={"90px"}
                  height={"70px"}
                  squareId={square}
                  refreshSquare={refreshSquares[index + 11]}
                  setDisplayProperty={setDisplayProperty}
                />
              </li>
            ))}
          </ul>

          <ul id="bottomRowBoardSquares">
            <li key={30} id="30">
              <BoardSquare
                width={"90px"}
                height={"90px"}
                squareId={squares[30]}
                refreshSquare={refreshSquares[30]}
                setDisplayProperty={setDisplayProperty}
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
                    refreshSquare={refreshSquares[8 - index + 21]}
                    setDisplayProperty={setDisplayProperty}
                  />
                </li>
              ))}
            <li key={20} id="20">
              <BoardSquare
                width={"90px"}
                height={"90px"}
                squareId={squares[20]}
                refreshSquare={refreshSquares[20]}
                setDisplayProperty={setDisplayProperty}
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
                    refreshSquare={refreshSquares[8 - index + 31]}
                    setDisplayProperty={setDisplayProperty}
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
        />
      )}

      {board && displayProperty && <PropertyCard setDisplayProperty={setDisplayProperty} />}
    </div>
  );
}

export default Board;
