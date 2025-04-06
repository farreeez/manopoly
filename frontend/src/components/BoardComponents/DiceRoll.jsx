import { useState, useEffect } from "react";
import "./css/DiceRoll.css";
import { updatePositions } from "./Board.jsx";
import CardAction from "./CardAction";

// Function to get random dice values during animation
function getRandomDice() {
  return [Math.floor(Math.random() * 6) + 1, Math.floor(Math.random() * 6) + 1];
}

const DiceRoll = ({ rollDiceAction, board, player }) => {
  const [animating, setAnimating] = useState(false);
  const [currentDice, setCurrentDice] = useState([1, 1]);
  const [rerender, setRerender] = useState(false);
  const [cardActionData, setCardActionData] = useState();

  useEffect(() => {
    if (rollDiceAction) {
      rollDice(board.diceRolls);
    }
  }, [rollDiceAction, board.diceRolls]);

  function fetchDiceData() {
    fetch("http://localhost:8080/board/rollDice", {
      method: "POST",
      credentials: "include",
    })
      .then(async (response) => {
        if (!response.ok) {
          const message = await response.text();
          console.error("Error message from response:", message);
        }

        return response.json();
      })
      .then((data) => {
        setCardActionData(data);
      })
      .catch((error) => console.error(error));
  }

  //find and fix the bug where the end turn button does not change correctly
  function endTurn() {
    fetch("http://localhost:8080/board/endTurn", {
      method: "POST",
      credentials: "include",
    })
      .then(async (response) => {
        if (!response.ok) {
          const message = await response.text();
          console.error("Error message from response:", message);

          // changes the rerender prop to update the scene because the end turn button is there when it shouldn't be
          setRerender(!rerender);
        }
      })
      .then((data) => {
        console.log("should be ended.");
      })
      .catch((error) => console.error(error));
  }

  // Start a new dice roll animation
  function rollDice(diceValues) {
    setAnimating(true);

    // Animation frames for dice rolling
    let frames = 0;
    const totalFrames = 20;
    const animationInterval = setInterval(() => {
      frames++;
      // Generate random dice values during animation
      setCurrentDice(getRandomDice());

      // End the animation
      if (frames >= totalFrames) {
        clearInterval(animationInterval);
        setCurrentDice(diceValues);
        setAnimating(false);
        updatePositions(board.playerIds);
      }
    }, 50);
  }

  // Render each die face
  function renderDieFace(value) {
    return (
      <div className={`die ${animating ? "rolling" : ""}`}>
        {[...Array(value)].map((_, i) => (
          <div
            key={i}
            className={`dot dot-${i + 1} ${getDotPositionClass(value, i + 1)}`}
          ></div>
        ))}
      </div>
    );
  }

  // Helper to position dots based on die value and dot number
  function getDotPositionClass(value, dotNum) {
    switch (value) {
      case 1:
        return "center";
      case 2:
        return dotNum === 1 ? "top-left" : "bottom-right";
      case 3:
        return dotNum === 1
          ? "top-left"
          : dotNum === 2
          ? "center"
          : "bottom-right";
      case 4:
        return dotNum === 1
          ? "top-left"
          : dotNum === 2
          ? "top-right"
          : dotNum === 3
          ? "bottom-left"
          : "bottom-right";
      case 5:
        return dotNum === 1
          ? "top-left"
          : dotNum === 2
          ? "top-right"
          : dotNum === 3
          ? "center"
          : dotNum === 4
          ? "bottom-left"
          : "bottom-right";
      case 6:
        return dotNum === 1
          ? "top-left"
          : dotNum === 2
          ? "top-right"
          : dotNum === 3
          ? "middle-left"
          : dotNum === 4
          ? "middle-right"
          : dotNum === 5
          ? "bottom-left"
          : "bottom-right";
      default:
        return "";
    }
  }

  return (
    <div className="dice-container">
      <div className="dice-area">
        {renderDieFace(currentDice[0])}
        {renderDieFace(currentDice[1])}
      </div>

      <div>
        {board && board.currentPlayerTurn.id === player.id && (
          <div className="diceRollButtons">
            {!board.diceRolled ? (
              <button
                className="roll-button"
                onClick={() => fetchDiceData()}
                disabled={animating}
              >
                Roll Dice
              </button>
            ) : (
              <button className="roll-button" onClick={() => endTurn()}>
                End Turn.
              </button>
            )}
            <CardAction
              board={board}
              player={player}
              cardActionData={cardActionData}
            />
          </div>
        )}
      </div>
    </div>
  );
};

export default DiceRoll;
