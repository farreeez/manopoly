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

function rollDice(board) {
  console.log(board)
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
      console.log(data);
    })
    .catch((error) => console.error(error));
}

function PlayerMovement({board}) {
  return (
    <div>
      <button className="button" onClick={() => rollDice(board)}>
        Roll Dice.
      </button>

      <button className="button" onClick={() => endTurn()}>
        End Turn.
      </button>
    </div>
  );
}

export default PlayerMovement;
