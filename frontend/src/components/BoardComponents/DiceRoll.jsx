import { useState, useEffect } from "react";
import "./css/DiceRoll.css";
import { updatePositions } from "./Board.jsx";
import CardAction from "./CardAction";
import { endTurn, fetchDiceData } from "../../services/BoardServices";

// Function to get random dice values during animation
function getRandomDice() {
  return [Math.floor(Math.random() * 6) + 1, Math.floor(Math.random() * 6) + 1];
}

const DiceRoll = ({ rollDiceAction, diceRolls, board, player ,setRefreshSquares}) => {
  const [animating, setAnimating] = useState(false);
  const [currentDice, setCurrentDice] = useState([1, 1]);
  const [rerender, setRerender] = useState(false);
  const [cardActionData, setCardActionData] = useState(false);

  useEffect(() => {
    if (diceRolls.length > 0 && rollDiceAction) {
      rollDice(board.diceRolls);
    }
  }, [rollDiceAction, diceRolls]);

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
                onClick={() => fetchDiceData(setCardActionData)}
                disabled={animating}
              >
                Roll Dice
              </button>
            ) : (
              <button className="roll-button" onClick={() => endTurn(setRerender)}>
                End Turn.
              </button>
            )}
            <CardAction
              board={board}
              player={player}
              cardActionData={cardActionData}
              setCardActionData={setCardActionData}
              setRefreshSquares={setRefreshSquares}
            />
          </div>
        )}
      </div>
    </div>
  );
};

export default DiceRoll;
