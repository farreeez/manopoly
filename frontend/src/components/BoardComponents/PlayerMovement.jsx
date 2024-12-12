function endTurn() {
  fetch("http://localhost:8080/board/endTurn", {
    method: "POST",
    credentials: "include",
  })
    .then(async (response) => {
      if (!response.ok) {
        const message = await response.text();
        console.error("Error message from response:", message);
      }
    })
    .then((data) => {
      console.log("should be ended.");
    })
    .catch((error) => console.error(error));
}

function rollDice(board, player) {
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
    })
    .catch((error) => console.error(error));
}

function PlayerMovement({ board, player }) {
  return (
    <div>
      {board && board.currentPlayerTurn.id === player.id && (
        <div>
          {!board.diceRolled ? (
            <button className="button" onClick={() => rollDice(board, player)}>
              Roll Dice.
            </button>
          ) : (
            <button className="button" onClick={() => endTurn()}>
              End Turn.
            </button>
          )}
        </div>
      )}
    </div>
  );
}

export default PlayerMovement;
